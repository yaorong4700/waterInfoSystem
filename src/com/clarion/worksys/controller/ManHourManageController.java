package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数管理Controller
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   2013-1-29 weng_zhangchu
 *  #修改
 *   2013-4-11 weng_zhangchu
 *  #修改
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.ManhourByProject;
import com.clarion.worksys.entity.ManhourPersonalQueryParam;
import com.clarion.worksys.entity.ManhourPersonalQueryView;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.httpentity.ManhourByProjectResponse;
import com.clarion.worksys.httpentity.ManhourByProjectResponseRows;
import com.clarion.worksys.httpentity.ManhourPersonalResponse;
import com.clarion.worksys.httpentity.ManhourPersonalResponseRows;
import com.clarion.worksys.service.CalendarManhourService;
import com.clarion.worksys.service.ManHourService;
import com.clarion.worksys.service.ProjectService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Arith;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Const.DepartmentEnum;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/hourManage")
public class ManHourManageController {

	@Autowired
	private ManHourService manHourService;
	
	@Autowired
	private CalendarManhourService calendarManhourService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRoleManageService userRoleManageService;
	
	/**
	 * 个人工数统计页面
	 * @param model
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(HttpSession session) {
		
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"PersonalManhour",userRoleManageService);
		mv.addObject("staff",staff);
		mv.addObject("QueryRoleFlag", userRole.getQueryRoleFlag());//検索
		mv.addObject("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
		mv.addObject("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
		mv.setViewName("admin/hourManage/hourManage");
		mv.addObject("mv",mv);
		return mv;
	}

	/**
	 * 请求个人工数查询LIST
	 * @param session
	 * @param queryParam
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/personalQuery")
	public void personalQuery(HttpServletResponse response,HttpSession session,ManhourPersonalQueryParam queryParam) throws IOException {
        
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);//获得当前登录用户的信息
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"PersonalManhour",userRoleManageService);
		int staffID = staff.getStaffID();//获得员工ID
		queryParam.setStaffID(staffID);
		queryParam.setPageSeq((queryParam.getPage()-1)*queryParam.getRp());
		//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(queryParam.getCategory()!=null &&queryParam.getCategory().trim().equals("")){
        	queryParam.setCategory(null);
		}
        if(queryParam.getQuery()!=null &&!queryParam.getQuery().trim().equals("")){
			if(queryParam.getQtype().trim().equals("category")){
				queryParam.setCategory(queryParam.getQuery().trim());
			}else if(queryParam.getQtype().trim().equals("projectName")){
				queryParam.setProjectName(queryParam.getQuery().trim());
			}
		}
		
		Calendar cal=Calendar.getInstance();//使用日历类获取当前时间
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		
		String startYear = queryParam.getStartYear();
		String startMonth = queryParam.getStartMonth();
		String endYear = queryParam.getEndYear();
		String endMonth = queryParam.getEndMonth();
		String January = new String("1");
		
		if(January.equals(startMonth)==true)//判断查询的起始月份是否为特殊的一月份
		{
			startMonth = "12";//特殊的一月份是从上一年度的十二月份起始查询
			int tempYear =Integer.parseInt(startYear)-1;//起始查询的年份应为上一年度
			startYear = tempYear+"";
			queryParam.setStartDate(startYear+'-'+startMonth+"-21");
			queryParam.setEndDate(endYear+'-'+endMonth+"-20");
		}
		else if(startMonth!=null){//
			int tempMonth =Integer.parseInt(startMonth)-1;
			startMonth = tempMonth+"";
			queryParam.setStartDate(startYear+'-'+startMonth+"-21");
			queryParam.setEndDate(endYear+'-'+endMonth+"-20");
		}
		else {//未输入查询区间
			//默认查询，即输出当前月的个人工数查询集合
			if(day<=20)//20为设定的工数查询临界点
			{
				if (month==1) {//1月份为特殊情况
					startMonth=12+"";
					startYear=year-1+"";
				}
				else {
					startMonth=month-1+"";
					startYear=year+"";
				}
				queryParam.setStartDate(startYear+'-'+startMonth+"-21");
				queryParam.setEndDate(year+""+'-'+month+"-20");//此处“+""”是为了避免错误
			}
			else {
				if (month==12) {//12月份为特殊情况
					endMonth=1+"";
					endYear=year+1+"";
				}
				else {
					endMonth=month+1+"";
					endYear=year+"";
				}
				queryParam.setStartDate(year+""+'-'+month+"-21");//若不加“+""”则2012年1月23显示为2059-21
				queryParam.setEndDate(endYear+'-'+endMonth+"-20");
			}
		}
		
		if ("CT".equals(staff.getKaisya())){
			//CT侧的按照开始终了日按照自然月处理
			if (queryParam.getStartMonth() != null) {
				queryParam.setStartDate(queryParam.getStartYear()+'-'+queryParam.getStartMonth()+"-01");
				cal.set(Calendar.YEAR, Integer.parseInt(queryParam.getEndYear()));  
				cal.set(Calendar.MONTH, Integer.parseInt(queryParam.getEndMonth()) - 1);
				int lastDay = cal.getActualMaximum(Calendar.DATE);
				queryParam.setEndDate(queryParam.getEndYear()+'-'+queryParam.getEndMonth()+"-"+lastDay);
			} else {
				queryParam.setStartDate(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-01");
				int lastDay = cal.getActualMaximum(Calendar.DATE);
				queryParam.setEndDate(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+lastDay);
			}
		}
		if(queryParam.getStartDate()!=null && queryParam.getEndDate()!=null /*&&ALL.equals(queryParam.getStartDate()) == false*/)
		{//当输入了查询区间时的时间段查询
			List<ManhourPersonalQueryView> queryList = null;
			int totalPageCountForPersonalQuery = 0;
			
			if ("1".equals(userRole.getQueryRoleFlag())) {
				if ("1".equals(userRole.getDepartmentFlag())) {
					queryParam.setDepartmentFlag("1");
				} else {
					queryParam.setDepartmentFlag("0");
				}
				queryParam.setDepartmentID(String.valueOf(staff.getDepartmentID()));
				if ("CT".equals(staff.getKaisya())){
					queryList = manHourService.ManhourPersonalQueryCT(queryParam);
					totalPageCountForPersonalQuery = manHourService.totalPageCountForPersonalQueryCT(queryParam);
				} else {
					queryList = manHourService.ManhourPersonalQuery(queryParam);
					totalPageCountForPersonalQuery = manHourService.totalPageCountForPersonalQuery(queryParam);
				}
			}
			//List<ManhourPersonalQueryView> queryList = manHourService.ManhourPersonalQuery(queryParam);	
			
			//int totalPageCountForPersonalQuery = manHourService.totalPageCountForPersonalQuery(queryParam);
			//以下统计工数总工时
			int tempPageSeq = queryParam.getPageSeq();//用于保存pageSeq值
			int tempRP = queryParam.getRp();//用于保存rp值
			queryParam.setPageSeq(0);
			queryParam.setRp(totalPageCountForPersonalQuery);
			double tempAllTime = 0.0;//--------------浮点型显示问题---------------
			//List<ManhourPersonalQueryView> queryListForAll = manHourService.ManhourPersonalQuery(queryParam);
			List<ManhourPersonalQueryView> queryListForAll = null;
			if ("CT".equals(staff.getKaisya())){
				queryListForAll = manHourService.ManhourPersonalQueryCT(queryParam);
			} else {
				queryListForAll = manHourService.ManhourPersonalQuery(queryParam);
			}
			for (int i = 0; i < queryListForAll.size(); i++) {
				double dTemp = queryListForAll.get(i).getTotalTimes();
				tempAllTime = Arith.add(tempAllTime, dTemp);//调用高精度算术类加方法
			}
			queryParam.setPageSeq(tempPageSeq);
			queryParam.setRp(tempRP);
			ManhourPersonalQueryView allTimeView = new ManhourPersonalQueryView();
			//allTimeView.setProjectName("总工数统计");
			allTimeView.setProjectName(Tools.getPropertiesValue(session,"hourManage_string_record_statistics_total"));
			allTimeView.setCategory(Tools.getPropertiesValue(session,"hourManage_string_record_statistics"));
			allTimeView.setTaskName("-");
			allTimeView.setTotalTimes(tempAllTime);
			ManhourPersonalResponseRows allTimeRow = new ManhourPersonalResponseRows(0, allTimeView);
					
			List<ManhourPersonalResponseRows> manhourPersonalRespongseList = new ArrayList<ManhourPersonalResponseRows>();
			manhourPersonalRespongseList.add(allTimeRow);
			for (int i=0;i<queryList.size();i++) {
				ManhourPersonalResponseRows manhourPersonalResponseRows = new ManhourPersonalResponseRows(i+1, queryList.get(i));//从第二条数据开始
				manhourPersonalRespongseList.add(manhourPersonalResponseRows);
			}
			ManhourPersonalResponse manhourPersonalResponse = new ManhourPersonalResponse();
			manhourPersonalResponse.setPage(queryParam.getPage());        //设置
			manhourPersonalResponse.setTotal(totalPageCountForPersonalQuery);
			manhourPersonalResponse.setRows(manhourPersonalRespongseList);
			
			Gson gson = new Gson();
			String resultString = gson.toJson(manhourPersonalResponse);
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(resultString);
			out.close();
		}
	}
	
	/**
	 * 总工数统计页面
	 * @return
	 */
	@RequestMapping(value="hourManagerByProjectDefault")
	public ModelAndView showDefault() {
		
		//List<String> departList   = calendarManhourService.listAllDepart();
		List<Map<String,String>> departList   = calendarManhourService.listAllDepart();
		List<String> categoryList = projectService.listCategorySelect();
		ModelAndView mv = new ModelAndView();
		mv.addObject("departList", departList);
		mv.addObject("categoryList",categoryList);
		mv.setViewName("admin/hourManage/hourManagerByProject");
		return mv;
	}
	
	/**
	 * 根据部门类别获得项目列表
	 * @param department
	 * @param category
	 * @param response
	 * @param httpSession
	 * @throws IOException
	 */
	@RequestMapping(value="selectProject")
	public  void getProjectSelect(String departmentID,String category,HttpServletResponse response,HttpSession httpSession)throws IOException {
		List<Project> projectList = new ArrayList<Project>();
		projectList = getProjectList(projectList, departmentID, category);
		Gson gson = new Gson();
		String resultString = gson.toJson(projectList);
		
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	
	/**
	 * 总工数统计
	 * @param response
	 * @param session
	 * @param queryParam
	 * @throws IOException
	 */
	@RequestMapping(value="hourManagerByProject")
	public void hourManagerByProject(HttpServletResponse response,HttpSession session,ManhourPersonalQueryParam queryParam) throws IOException {
		queryParam.setPageSeq((queryParam.getPage()-1)*queryParam.getRp());
		List<Project> projectList = new ArrayList<Project>();
		projectList = getProjectList(projectList, queryParam.getDepartmentID(), queryParam.getCategory());
		for (int i = 0; i < projectList.size(); i++) {
			if (queryParam.getName().equals(projectList.get(i).getProjectName()) == true) {
				queryParam.setStaffID(projectList.get(i).getProjectID());
				break;
			}
		}
		if ("公司所有部门".equals(queryParam.getDepartmentID())) {
			queryParam.setDepartmentID(null);
		}
		if (queryParam.getStartDate() != null && queryParam.getEndDate() !=null ) {
			
			int totalCount = manHourService.totalPageCountForGetHourByProject(queryParam);
			
			int tempPageSeq = queryParam.getPageSeq();//用于保存pageSeq值
			int tempRP = queryParam.getRp();//用于保存rp值
			queryParam.setPageSeq(0);
			if (totalCount == 0) {
				totalCount++;
			}
			queryParam.setRp(totalCount);
			double tempAllTime = 0.0;//--------------浮点型显示问题---------------
			double devTime = 0.0;
			double otherTime = 0.0;
			List<ManhourByProject> manhourByProjectsForAll = manHourService.getHourByProject(queryParam);
			for (int i = 0; i < manhourByProjectsForAll.size(); i++) {
				double dTemp = manhourByProjectsForAll.get(i).getTotalTimes();
				tempAllTime = Arith.add(tempAllTime, dTemp);//调用高精度算数类加方法
				devTime     = Arith.add(devTime, manhourByProjectsForAll.get(i).getDevTime());
				otherTime   = Arith.add(otherTime, manhourByProjectsForAll.get(i).getOtherTime());
			}
			queryParam.setPageSeq(tempPageSeq);
			queryParam.setRp(tempRP);
			ManhourByProject allTimeView = new ManhourByProject();
			allTimeView.setStaffID(0);
			allTimeView.setStaffName("--------");
			allTimeView.setDepartment("---------");
			allTimeView.setBranch("共计：");
			allTimeView.setNew_branch("共计：");
			allTimeView.setEmail("项目名："+queryParam.getName());
			allTimeView.setTotalTimes(tempAllTime);
			allTimeView.setDevTime(devTime);
			allTimeView.setOtherTime(otherTime);
			ManhourByProjectResponseRows allTimeRows = new ManhourByProjectResponseRows(0, allTimeView);
			
			List<ManhourByProjectResponseRows> manhourByProjectResponseRowsList = new ArrayList<ManhourByProjectResponseRows>();
			manhourByProjectResponseRowsList.add(allTimeRows);
			
			List<ManhourByProject> manhourByProjects = manHourService.getHourByProject(queryParam);
			for (int i=0;i<manhourByProjects.size();i++) {
				ManhourByProjectResponseRows manhourByProjectResponseRows = new ManhourByProjectResponseRows(i+1, manhourByProjects.get(i));
				manhourByProjectResponseRowsList.add(manhourByProjectResponseRows);
			}
			ManhourByProjectResponse manhourByProjectResponse = new ManhourByProjectResponse();
			manhourByProjectResponse.setPage(queryParam.getPage());        //设置
			manhourByProjectResponse.setTotal(totalCount);
			manhourByProjectResponse.setRows(manhourByProjectResponseRowsList);
			Gson gson = new Gson();
			String resultString = gson.toJson(manhourByProjectResponse);
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(resultString);
			out.close();
		}
	}

	/**
	 * 通过department，category得到所要的项目列表
	 * @param list
	 * @param department
	 * @param category
	 * @return
	 */
	public List<Project> getProjectList(List<Project> list,String departmentID,String category){
		if ("公司所有部门".equals(departmentID) || Integer.toString(DepartmentEnum.GCSJB.getId()).equals(departmentID)
				//||"开发管理部".equals(departmentID)||"设计开发部".equals(departmentID)) {
				|| Integer.toString(DepartmentEnum.JYGLB.getId()).equals(departmentID)
				|| Integer.toString(DepartmentEnum.KFZYBSJK.getId()).equals(departmentID)
				|| Integer.toString(DepartmentEnum.KFZYBKFGLK.getId()).equals(departmentID)) {
			list = projectService.getProjectByCategory(category);
		} else {
			list =  projectService.getProjectByDepartment(departmentID,category);
		}
		return list;
	}
}
