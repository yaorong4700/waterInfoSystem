package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数填写Controller
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-12-25
 * @histroy:
 * 	2012-12-25 GuoRenPeng
 *  # 初版
 *  
 *  2013-04-11 GuoRenPeng
 *  # 新增请求公司日历接口listHoliday
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.clarion.worksys.entity.CalendarManhour;
import com.clarion.worksys.entity.CategoryCT;
import com.clarion.worksys.entity.CategoryParamCT;
import com.clarion.worksys.entity.Component;
import com.clarion.worksys.entity.ComponentSelect;
import com.clarion.worksys.entity.ManHourDto;
import com.clarion.worksys.entity.ManHourDtoCT;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.ProjectNameCT;
import com.clarion.worksys.entity.RequestObject;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.Task;
import com.clarion.worksys.filter.DateEditor;
import com.clarion.worksys.service.ManHourService;
import com.clarion.worksys.util.Arith;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.GetCalendar;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/calendar")
public class CalendarController {
	@Autowired
	private ManHourService manHourService;
	
	private Map<String, CategoryParamCT> categoryMap = new HashMap<String, CategoryParamCT>();
    /**
     * 请求填写工数界面
     * @param session
     * @param ro
     * @return
     */
	@RequestMapping
	public String calendarmanhourList(HttpSession session, RequestObject ro) {
		Staff staff      = (Staff) session.getAttribute(Const.SESSION_USER);
		if (staff.getEmail().endsWith(".co.jp")){
			return "admin/calendar/calendarCT";
		} else {
			return "admin/calendar/customDemo";
		}
	}
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {  
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理   
	    binder.registerCustomEditor(Date.class, new DateEditor());  
	}  
    
	/**
	 * 请求节假日信息,用来生成日历
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/listHoliday")
	public void listHoliday(HttpServletResponse response) throws IOException {
		
		List<String> holidayList = manHourService.listMonthHoliday();
		Gson gson = new Gson();
		String resultString = gson.toJson(holidayList);
		String result ="{\"holiday\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

	/**
	 * 请求一个月的工数LIST
	 * @param session
	 * @param currentDate
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/list")
	public void list(HttpSession session, String currentDate, HttpServletResponse response) throws IOException {
		System.out.println("===========CalendarController list=====================");
		Staff staff      = (Staff) session.getAttribute(Const.SESSION_USER);
		GetCalendar getCalendar = new GetCalendar(currentDate);
		String startDate = getCalendar.getStartDate();
		String endDate   = getCalendar.getEndDate();
		List<ManHourDto> manHours = manHourService.getManHourByDate(staff.getStaffID().toString(), startDate, endDate);
		Gson gson = new Gson();
		String deadlineString = manHourService.getDeadline();
		String resultString = gson.toJson(manHours);
		String result ="{\"deadline\":\""+deadlineString+"\",\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	
	/**
	 * 请求一个月的工数LIST
	 * @param session
	 * @param currentDate
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/listCT")
	public void listCT(HttpSession session, String currentDate, HttpServletResponse response) throws IOException {
		Staff staff      = (Staff) session.getAttribute(Const.SESSION_USER);
		GetCalendar getCalendar = new GetCalendar(currentDate);
		String startDate = getCalendar.getStartDate();
		String endDate   = getCalendar.getEndDate();
		List<ManHourDtoCT> manHours = manHourService.getManHourCTByDate(staff.getStaffID().toString(), startDate, endDate);
		Gson gson = new Gson();
		String deadlineString = manHourService.getDeadline();
		String resultString = gson.toJson(manHours);
		GetCalendar getCalendar1 = new GetCalendar(deadlineString);
		String monthEndDate = getCalendar1.getMonthEndDate();
		String stopDate = getCalendar1.getEndtimeDate();
		Boolean monthEndFlag=false;
		if(monthEndDate.equals(deadlineString)){
			monthEndFlag=true;
		}
		String result ="{\"deadline\":\""+deadlineString+"\","+"\"stopDate\":\""+stopDate+"\","+
		"\"monthEndFlag\":\""+monthEndFlag+"\","+"\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	/**
	 * 保存修改工数之前例行检查
	 * @param session
	 * @param calendarmanhour
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkEditBeforeSave", method = RequestMethod.POST)
	public void checkEditBeforeSaveManhour(HttpSession session, CalendarManhour calendarmanhour, HttpServletResponse response) throws IOException {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		calendarmanhour.setStaffID(staff.getStaffID());
		BigDecimal bigdecimaltemp = new BigDecimal(String.valueOf(calendarmanhour.getTimes()));
		double inputTime = bigdecimaltemp.doubleValue();
		double clickManhour=Arith.add(manHourService.checkEditBeforeSave(calendarmanhour),inputTime);
		String msg = "{\"result\":\"success\",\"data\":"+clickManhour+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.close();
	}

	/**
	 * 保存新增工数之前例行检查
	 * @param session
	 * @param calendarmanhour
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkBeforeSave", method = RequestMethod.POST)
	public void checkBeforeSaveManhour(HttpSession session, CalendarManhour calendarmanhour, HttpServletResponse response) throws IOException {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		calendarmanhour.setStaffID(staff.getStaffID());
		BigDecimal bigdecimaltemp = new BigDecimal(String.valueOf(calendarmanhour.getTimes()));
		double inputTime = bigdecimaltemp.doubleValue();
		double clickManhour=Arith.add(manHourService.checkBeforeSave(calendarmanhour),inputTime);
		String msg = "{\"result\":\"success\",\"data\":"+clickManhour+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.close();
	}

	/**
	 *  保存工数信息
	 * @param session
	 * @param calendarmanhour
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void savecalendarManhour(HttpSession session, CalendarManhour calendarmanhour, HttpServletResponse response) throws IOException {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		calendarmanhour.setStaffID(staff.getStaffID());
		int departmentID = staff.getDepartmentID();
		int branchID     = staff.getBranchID();
		calendarmanhour.setBranchID(branchID);
		calendarmanhour.setDepartmentID(departmentID);
		System.out.println("===================CalendarController save===============");
		if(calendarmanhour.getTaskName().equals("null")){
			calendarmanhour.setTaskName("-");
		}
		String msg = "";
		if (manHourService.insertCalendarManhour(calendarmanhour)!=true) {
			msg ="{\"result\":\"failed\",\"id\":"+calendarmanhour.getId()+"}";
		} else {
			msg ="{\"result\":\"success\",\"id\":"+calendarmanhour.getId()+"}";
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.close();
	}

	/**
	 * 保存修改结果
	 * @param session
	 * @param calendarmanhour
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public void saveEdit(HttpSession session, CalendarManhour calendarmanhour, HttpServletResponse response) throws IOException {
		System.out.println("========== CalendarController saveEdit===============");
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		calendarmanhour.setStaffID(staff.getStaffID());
		int departmentID = staff.getDepartmentID();
		int branchID     = staff.getBranchID();
		calendarmanhour.setBranchID(branchID);
		calendarmanhour.setDepartmentID(departmentID);
		if(calendarmanhour.getTaskName().equals("null")){
			calendarmanhour.setTaskName("-");
		}
		manHourService.updateManHour(calendarmanhour);
		String msg ="{\"result\":\"success\"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.close();
	}

	/**
	 * 删除工数
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete")
	public void deleteManhour(@RequestParam int id, HttpServletResponse response) throws IOException {
		System.out.println("========== CalendarController delete==========");
		manHourService.deleteManHour(id);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	/**
	 * 请求CT员工项目信息
	 * @param session
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/listCTProjectInfo", method = RequestMethod.POST)
	public void listCTProjectInfo(HttpSession session,HttpServletResponse response) throws IOException {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		
		//特殊部门需要能查看所有项目信息
		List<Project> projectList = new ArrayList<Project>();
		List <Project> recentProjectList = new ArrayList<Project>();
		
		String staffDepartmentID = Integer.toString(staff.getDepartmentID());
		
		CategoryParamCT categoryParam = this.getGateCategoryParam(staffDepartmentID);
		
		projectList = manHourService.listAllProjectCT(staff.getStaffID(),staffDepartmentID);
		recentProjectList = manHourService.recentProjectCT(staff.getStaffID(),staffDepartmentID);

 
		for (Project project : projectList) {
			recentProjectList.add(project);
		}
		
		CategoryCT devCategory = new CategoryCT();
		CategoryCT rfirCategory = new CategoryCT();
		CategoryCT noAccordCategory = new CategoryCT();
		CategoryCT otherDevCategory = new CategoryCT();
		List<ProjectNameCT> devProjectNames = new ArrayList<ProjectNameCT>();
		List<ProjectNameCT> rfirProjectNames = new ArrayList<ProjectNameCT>();
		List<ProjectNameCT> noAccordProjectNames = new ArrayList<ProjectNameCT>();
		List<ProjectNameCT> otherProjectNames = new ArrayList<ProjectNameCT>();

		devCategory.setVal(categoryParam.getDev());
		rfirCategory.setVal(categoryParam.getRfir());
		noAccordCategory.setVal(categoryParam.getNoAccord());
		otherDevCategory.setVal(categoryParam.getOther());
		List<Task> devTaskList = new ArrayList<Task>();
		List<Task> rfirTaskList = new ArrayList<Task>();
		List<Task> advanceTaskList = new ArrayList<Task>();
		List<Task> noAccordTaskList = new ArrayList<Task>();
		
		for(int i=0;i<categoryParam.getDevTaskList().size();i++){
			Task task = new Task();
			task.setVal(categoryParam.getDevTaskList().get(i).get("taskID").toString());
			task.setTxt(categoryParam.getDevTaskList().get(i).get("task").toString());
			devTaskList.add(task);
		}
		for(int i=0;i<categoryParam.getOtherTaskList().size();i++){
			Task task = new Task();
			task.setVal(categoryParam.getOtherTaskList().get(i).get("taskID").toString());
			task.setTxt(categoryParam.getOtherTaskList().get(i).get("task").toString());
			rfirTaskList.add(task);
		}
		for(int i=0;i<categoryParam.getAdvanceTaskList().size();i++){
			Task task = new Task();
			task.setVal(categoryParam.getAdvanceTaskList().get(i).get("taskID").toString());
			task.setTxt(categoryParam.getAdvanceTaskList().get(i).get("task").toString());
			advanceTaskList.add(task);
		}
		for (int i = 0; i < categoryParam.getNoAccordTaskList().size(); i++) {
			Task task = new Task();
			task.setVal(categoryParam.getNoAccordTaskList().get(i).get("taskID").toString());
			task.setTxt(categoryParam.getNoAccordTaskList().get(i).get("task").toString());
			noAccordTaskList.add(task);
		}
		
		//コンポーネントList
		List<ComponentSelect> devComponentList = new ArrayList<ComponentSelect>();//开发工数
		List<ComponentSelect> rfirComponentList = new ArrayList<ComponentSelect>();//RFI和RFQ
		List<ComponentSelect> advanceComponentList = new ArrayList<ComponentSelect>();//先行开发
		List<ComponentSelect> noAccordComponentList = new ArrayList<ComponentSelect>();//量产后不具合对应
		List<Component> allComponentInfoList = manHourService.getComponentSelectInfo();
		ComponentSelect tempComponent = null;
		for(int i = 0; i < allComponentInfoList.size(); i++){
			tempComponent = new ComponentSelect();
			tempComponent.setVal(allComponentInfoList.get(i).getId().toString());
			tempComponent.setTxt(allComponentInfoList.get(i).getComponentName());
			
			if (allComponentInfoList.get(i).getCategoryID() == 1) {
				tempComponent.setMenu(advanceTaskList);
				advanceComponentList.add(tempComponent);
			} else if (allComponentInfoList.get(i).getCategoryID() == 2) {
				tempComponent.setMenu(rfirTaskList);
				rfirComponentList.add(tempComponent);
			} else if (allComponentInfoList.get(i).getCategoryID() == 3) {
				tempComponent.setMenu(devTaskList);
				devComponentList.add(tempComponent);
			} else if (allComponentInfoList.get(i).getCategoryID() == 4) {
				tempComponent.setMenu(noAccordTaskList);
				noAccordComponentList.add(tempComponent);
			}
		}	
		for (Project project : recentProjectList) {
			if(project.getCategory().equals(noAccordCategory.getVal())){
				ProjectNameCT projectName = new ProjectNameCT();
				projectName.setMenu(noAccordComponentList);
				projectName.setVal(project.getProjectID()+"");
				
				//PJNO.ある場合、PJNo.+PJ名,ない場合、仮PJNo.+PJ名
				if(project.getPJNo()!= null && !"".equals(project.getPJNo())){
					projectName.setTxt(project.getPJNo()+' '+project.getPJName());
				}else{
					projectName.setTxt(project.getTempPJNo()+' '+project.getPJName());
				}
				noAccordProjectNames.add(projectName);
			}
			if(project.getCategory().equals(devCategory.getVal())){
				ProjectNameCT projectName = new ProjectNameCT();
				projectName.setMenu(devComponentList);
				projectName.setVal(project.getProjectID()+"");
				
				//PJNO.ある場合、PJNo.+PJ名,ない場合、仮PJNo.+PJ名
				if(project.getPJNo()!= null && !"".equals(project.getPJNo())){
					projectName.setTxt(project.getPJNo()+' '+project.getPJName());
				}else{
					projectName.setTxt(project.getTempPJNo()+' '+project.getPJName());
				}

				devProjectNames.add(projectName);
                
			}
			if(project.getCategory().equals(rfirCategory.getVal())){
				ProjectNameCT projectName = new ProjectNameCT();
				projectName.setMenu(rfirComponentList);
				projectName.setVal(project.getProjectID()+"");
				//PJNO.ある場合、PJNo.+PJ名,ない場合、仮PJNo.+PJ名
				if(project.getPJNo()!= null && !"".equals(project.getPJNo())){
					projectName.setTxt(project.getPJNo()+' '+project.getPJName());
				}else{
					projectName.setTxt(project.getTempPJNo()+' '+project.getPJName());
				}
				rfirProjectNames.add(projectName);
			}
			if(project.getCategory().equals(otherDevCategory.getVal())){
				ProjectNameCT projectName = new ProjectNameCT();
				projectName.setMenu(advanceComponentList);
				projectName.setVal(project.getProjectID()+"");
				
				//PJNO.ある場合、PJNo.+PJ名,ない場合、仮PJNo.+PJ名
				if(project.getPJNo()!= null && !"".equals(project.getPJNo())){
					projectName.setTxt(project.getPJNo()+' '+project.getPJName());
				}else{
					projectName.setTxt(project.getTempPJNo()+' '+project.getPJName());
				}
				
				otherProjectNames.add(projectName);
			}
		}

		devCategory.setMenu(devProjectNames);
		otherDevCategory.setMenu(otherProjectNames);
		rfirCategory.setMenu(rfirProjectNames);
		noAccordCategory.setMenu(noAccordProjectNames);
		List<CategoryCT> resultList = new ArrayList<CategoryCT>();

		resultList.add(devCategory);
		if(otherProjectNames.size() != 0){
		    resultList.add(otherDevCategory);	
		}
		resultList.add(rfirCategory);
		resultList.add(noAccordCategory);

		Gson gson = new Gson();
		String result = gson.toJson(resultList);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	/**
	 * 从categoryMap获得某个部门的category param
	 * @param departmentName
	 * @return
	 */
	private CategoryParamCT getGateCategoryParam(String departmentID) {
		CategoryParamCT categoryParam = null;
		String department = departmentID;
		//if (!categoryMap.containsKey(department)) {
			categoryParam = new CategoryParamCT();
			List<Map<String,Object>> devTaskList = manHourService.listCTTask(categoryParam.getDev(), department);
			List<Map<String,Object>> otherTaskList = manHourService.listCTTask(categoryParam.getRfir(), department);
			List<Map<String,Object>> advanceTaskList = manHourService.listCTTask(categoryParam.getOther(), department);
			List<Map<String,Object>> noAccordTaskList = manHourService.listCTTask(categoryParam.getNoAccord(), department);
			categoryParam.setDevTaskList(devTaskList);
			categoryParam.setOtherTaskList(otherTaskList);
			categoryParam.setNoAccordTaskList(noAccordTaskList);
			//if(advanceTaskList.size() != 0){
				categoryParam.setAdvanceTaskList(advanceTaskList);
			//}
			categoryMap.put(department, categoryParam);
		//}
		return (CategoryParamCT) categoryMap.get(department);
	}
}
