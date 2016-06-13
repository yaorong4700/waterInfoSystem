package com.clarion.worksys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.ManhourPersonalQueryParam;
import com.clarion.worksys.entity.ManhourPersonalQueryView;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.ProjectTime;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.Stage;
import com.clarion.worksys.entity.TotalHourManager;
import com.clarion.worksys.entity.TotalHourManagerSum;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.entity.departmentBranch;
import com.clarion.worksys.httpentity.ManhourPersonalResponse;
import com.clarion.worksys.httpentity.ManhourPersonalResponseRows;
import com.clarion.worksys.httpentity.TotalHourManageReqParam;
import com.clarion.worksys.service.CalendarManhourService;
import com.clarion.worksys.service.ManHourService;
import com.clarion.worksys.service.ProjectService;
import com.clarion.worksys.service.Project_taskService;
import com.clarion.worksys.service.StaffSelectedService;
import com.clarion.worksys.service.StaffService;
import com.clarion.worksys.service.TotalHourManagerListService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Arith;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.DataToExcle;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

import jxl.write.WriteException;

/**
 * @author weng_zhangchu
 * @20140723  chen_weijun
 *
 */

@Controller
@RequestMapping(value = "/totalHourManage")
public class TotalManHourManageController {

    @Autowired
    private CalendarManhourService calendarManhourService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ManHourService manHourService;

    @Autowired
    private Project_taskService projectTaskService;

    @Autowired
    private StaffSelectedService staffSelectedService;
    
    @Autowired
    private StaffService staffService;
    
    @Autowired
    private TotalHourManagerListService totalHourManagerListService;

    @Autowired
	private UserRoleManageService userRoleManageService;

    @RequestMapping
    public ModelAndView showDefault(HttpSession session) {
    	//获得当前登录用户
    	Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
    	
    	//获取当前时间
    	Date date = new Date();
    	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
    	String currentYearDay = ym.format(date);
    	String currentYear = currentYearDay.substring(0,7);
    	List<Map<String, Object>> staffposition = staffSelectedService.getPositionListCXEE();
    	List<Map<String, Object>> staffpositionCT = staffSelectedService.getPositionListCT();
    	List<String> staffSort = staffService.getAllSort();
    	List<String> staffSortCT = staffService.getAllSortCT();
    	List<Staff> jobCategory = staffService.getJobCategory();
    	List<Staff> newJbCategy = new ArrayList<Staff>();
    	for(int i=0;i<jobCategory.size();i++){
    		if(!jobCategory.get(i).getJobCategory().trim().equals("") ){
    			newJbCategy.add(jobCategory.get(i));
    		}
    	}
    	
    	
    	List<Map<String, Object>> staffpositionCT2=new ArrayList<Map<String, Object>>();
    	staffpositionCT2.addAll(staffpositionCT);
    	for(int i=staffpositionCT2.size()-1;i>=0;i--){
    		Map<String, Object> s= staffpositionCT2.get(i);
    		boolean flag=false;
    		for (Map.Entry<String, Object> entry:s.entrySet()){
    			System.out.println(entry.getKey()+"--------"+entry.getValue());
    			if(entry.getValue().toString().indexOf("本部長")>=0||
    					entry.getValue().toString().indexOf("担当本部長")>=0||
    					entry.getValue().toString().indexOf("技師長")>=0||
    					entry.getValue().toString().indexOf("主管技師長")>=0||
    					entry.getValue().toString().indexOf("部長")>=0||
    					entry.getValue().toString().indexOf("担当部長")>=0){
    				flag=true;
    				break;
    			}
    		}
			if(flag){
				staffpositionCT.remove(i);
			}
    	}
    	
    	
    	List<Staff> jobCategoryCT = staffService.getJobCategoryCT();
    	List<Staff> newJbCategyCT = new ArrayList<Staff>();
    	for(int i=0;i<jobCategoryCT.size();i++){
    		if(!jobCategoryCT.get(i).getJobCategory().trim().equals("") ){
    			newJbCategyCT.add(jobCategoryCT.get(i));
    		}
    	}
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"TotalManhour",userRoleManageService);
    	//List<String> ability = staffService.getAbility();
    	List<Project> projectClientName = projectService.getAllProClientName();
    	List<Project> projectFunction = projectService.getAllProFunction();
    	
    	List<Project> projectFunctionCT = projectService.getAllProFunctionCT();
    	List<Project> projectCarMaker = projectService.getAllProCarMaker();
    	List<Project> projectCarMakerCT = projectService.getAllProCarMakerCT();
    	List<Stage> category = projectService.getAllStage();
    	List<Stage> categoryCT = projectService.getAllCategoryCT();
    	List<Project> PJNo = projectService.getAllPJNo();
    	List<Project> tempPJNo = projectService.getAllTempPJNo();
    	//部门的下拉框的取得
    	List<departmentBranch> departList = calendarManhourService.listDepartment();
    	List<departmentBranch> departListCT = calendarManhourService.listDepartmentCT();
        List<String> clientList   = projectService.listProjectClientSelect();
        List<String> functionList = projectService.listFunctionListSelect();
        List<String> functionListCT = projectService.listFunctionListSelectCT();
        ModelAndView mv = new ModelAndView();
        mv.addObject("staff",staff);
        
        mv.addObject("currentYear",currentYear);
        mv.addObject("departList", departList);//部门列表
        mv.addObject("departListCT", departListCT);
        //mv.addObject("branchSelect", branchSelect);//课别列表
        mv.addObject("clientList", clientList);
        mv.addObject("functionList", functionList);
        mv.addObject("functionListCT", functionListCT);
        mv.addObject("staffposition", staffposition);//职位
        mv.addObject("staffpositionCT", staffpositionCT);//职位
        //mv.addObject("abilitys", abilitys);//能力
        mv.addObject("staffSort", staffSort);//员工分类
        mv.addObject("jobCategory",newJbCategy);
        mv.addObject("staffSortCT", staffSortCT);//员工分类
        mv.addObject("jobCategoryCT",newJbCategyCT);
        mv.addObject("projectClientName", projectClientName);
        mv.addObject("projectFunction", projectFunction);
        mv.addObject("projectFunctionCT", projectFunctionCT);
        mv.addObject("projectCarMaker", projectCarMaker);
        mv.addObject("projectCarMakerCT", projectCarMakerCT);
        mv.addObject("PJNo",PJNo);
        mv.addObject("TempPJNo",tempPJNo);
        mv.addObject("category", category);
        mv.addObject("categoryCT", categoryCT);
        mv.addObject("QueryRoleFlag", userRole.getQueryRoleFlag());//検索
        mv.addObject("DownloadRoleFlag", userRole.getDownloadRoleFlag());//D/L
        mv.addObject("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
        mv.addObject("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
        mv.addObject("DepartmentFlag", userRole.getDepartmentFlag());//部门权限
        mv.setViewName("admin/hourManage/totalHourManage");
        return mv;
    }
    
    @RequestMapping(value = "/departmentChanged", method = RequestMethod.POST)
    public void getByDepartment(String department, HttpServletResponse response,HttpSession session) throws IOException {
    	
    		//List<Map<String, String>> branchselectList = calendarManhourService.outputbranchselect(departmentID);
    		List<departmentBranch> branchselectList = calendarManhourService.branchSelect(department);
    		Gson gson = new Gson();
    		String resultString = gson.toJson(branchselectList);
    		String result ="{\"branch\":"+ resultString +"}";
    		response.setCharacterEncoding("UTF-8");
    		PrintWriter out = response.getWriter();
    		out.write(result);
    		out.close();
    		//request.setAttribute("branchSelect", branchselectList);
    
		}
    @RequestMapping(value = "/departmentChangedCT", method = RequestMethod.POST)
    public void getByDepartmentCT(String department, HttpServletResponse response,HttpSession session) throws IOException {
    		//List<Map<String, String>> branchselectList = calendarManhourService.outputbranchselect(departmentID);
    		List<departmentBranch> branchselectListCT = calendarManhourService.branchSelectCT(department);
    		Gson gson = new Gson();
    		String resultString = gson.toJson(branchselectListCT);
    		String result ="{\"branch\":"+ resultString +"}";
    		response.setCharacterEncoding("UTF-8");
    		PrintWriter out = response.getWriter();
    		out.write(result);
    		out.close();
    		//request.setAttribute("branchSelect", branchselectList);
    
		}
		// mv.addObject("branchSelect", branchselectList);
    @RequestMapping(value = "/PJNoChanged", method = RequestMethod.POST)
    public void getByPJNo(String PJNo,HttpServletResponse response,HttpSession session) throws IOException {
    		//List<Map<String, String>> branchselectList = calendarManhourService.outputbranchselect(departmentID);
    		List<Project> projectName = projectService.projectNameSelect(PJNo);
    		Gson gson = new Gson();
    		String resultString = gson.toJson(projectName);
    		String result ="{\"branch\":"+ resultString +"}";
    		response.setCharacterEncoding("UTF-8");
    		PrintWriter out = response.getWriter();
    		out.write(result);
    		out.close();
    		//request.setAttribute("branchSelect", branchselectList);
    
		}

    @RequestMapping(value = "/tempPJNoChanged", method = RequestMethod.POST)
    public void getByTempPJNo(String tempPJNo, HttpServletResponse response,HttpSession session) throws IOException {
    		//List<Map<String, String>> branchselectList = calendarManhourService.outputbranchselect(departmentID);
    		List<Project> projectName = projectService.projectNameSelectByTempPJNo(tempPJNo);
    		Gson gson = new Gson();
    		String resultString = gson.toJson(projectName);
    		String result ="{\"branch\":"+ resultString +"}";
    		response.setCharacterEncoding("UTF-8");
    		PrintWriter out = response.getWriter();
    		out.write(result);
    		out.close();
    		//request.setAttribute("branchSelect", branchselectList);
    
		}
    @RequestMapping(value = "/listManHour")
	public void searchButtonClick(TotalHourManageReqParam totalHourManagerReqParam,HttpServletResponse response,HttpSession httpSession) throws IOException{
    	//获取当前时间
    	Date date = new Date();
    	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
    	String currentYearMD = ym.format(date);
    	String currentYearMonth = currentYearMD.substring(0,7);
    	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
    		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
		}else{
			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
		}
    	
    	
		//员工类别
		if("".equals(totalHourManagerReqParam.getSel_sort())){
			totalHourManagerReqParam.setSel_sort(null);
		}else{
			 totalHourManagerReqParam.setSel_sort(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_sort(),"UTF-8")); 
		}
		
		//项目号
		if("0".equals(totalHourManagerReqParam.getSel_PJNo())){
			totalHourManagerReqParam.setSel_PJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_PJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJNo(),"UTF-8")); 
		}
		//临时项目号
		if("0".equals(totalHourManagerReqParam.getSel_tempPJNo())){
			totalHourManagerReqParam.setSel_tempPJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_tempPJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_tempPJNo(),"UTF-8")); 
		}
				
		//PJ名
		if("".equals(totalHourManagerReqParam.getSel_PJName())){
			totalHourManagerReqParam.setSel_PJName(null);
		}else{
			totalHourManagerReqParam.setSel_PJName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJName(),"UTF-8")); 
		}
		//管理项番
		if("".equals(totalHourManagerReqParam.getSerach_transferNo())){
			totalHourManagerReqParam.setSerach_transferNo(null);
		}else{
			totalHourManagerReqParam.setSerach_transferNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_transferNo(),"UTF-8")); 
		}
		//开发阶段
		if("".equals(totalHourManagerReqParam.getSel_category())){
			totalHourManagerReqParam.setSel_category(null);
		}else{
			totalHourManagerReqParam.setSel_category(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_category(),"UTF-8")); 
		}
		
		//项目功能function
		if("".equals(totalHourManagerReqParam.getSel_projectFunction())){
			totalHourManagerReqParam.setSel_projectFunction(null);
		}else{
			totalHourManagerReqParam.setSel_projectFunction(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectFunction(), "UTF-8"));
		}
		//车厂
		if("".equals(totalHourManagerReqParam.getSel_carMaker())){
			totalHourManagerReqParam.setSel_carMaker(null);
		}else{
			totalHourManagerReqParam.setSel_carMaker(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_carMaker(),"UTF-8")); 
		}
		//归属
		if("0".equals(totalHourManagerReqParam.getSel_belong())){
			totalHourManagerReqParam.setSel_belong(null);
		}else{
			totalHourManagerReqParam.setSel_belong(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_belong(),"UTF-8")); 
		}
		totalHourManagerReqParam.setRp(totalHourManagerReqParam.getLimit());
		//设置数据库查询时从哪条数据开始
		totalHourManagerReqParam.setPageSeq((totalHourManagerReqParam.getPage()-1)*totalHourManagerReqParam.getRp());
		List<TotalHourManager> searchList = totalHourManagerListService.searchList(totalHourManagerReqParam);
		int totalPageCount = totalHourManagerListService.totalPageCount(totalHourManagerReqParam);
		String Staffname = "";
		int k = totalHourManagerReqParam.getPageSeq()+1;
		for (int i= 0;i<searchList.size();i++) {
			if (i== 0) {
				Staffname = searchList.get(0).getStaffName();
			}
			if(Staffname.equals(searchList.get(i).getStaffName())){
				searchList.get(i).setNo(String.valueOf(k));
			}else{
				++k;
				searchList.get(i).setNo(String.valueOf(k));
				Staffname = searchList.get(i).getStaffName();
			}
		}
		Gson gson = new Gson();
		String resultString = gson.toJson(searchList);
		
		String result ="{\"totalCount\":"+totalPageCount+",\"result\":"+resultString+"}";
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
    @RequestMapping(value = "/listManHourCXEE")
   	public void searchButtonClickCXEE(TotalHourManageReqParam totalHourManagerReqParam,HttpServletResponse response,HttpSession httpSession) throws IOException{
       	//获取当前时间
       	Date date = new Date();
       	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
       	String currentYearDay = ym.format(date);
       	String currentYearMonth = currentYearDay.substring(0,7);
       	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
       		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
   		}else{
   			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
   		}
       	
       	
       	//姓名转换 null和编码转换
       	if("".equals(totalHourManagerReqParam.getSerach_staffName())){
       		totalHourManagerReqParam.setSerach_staffName(null);
       	}else{
       		totalHourManagerReqParam.setSerach_staffName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_staffName(),"UTF-8")); 
       	}
       	//职则
       	if("".equals(totalHourManagerReqParam.getSel_position())){
       		totalHourManagerReqParam.setSel_position(null);
       	}else{
       		totalHourManagerReqParam.setSel_position(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_position(),"UTF-8"));
       	}
       	
       	//部门
   		if("".equals(totalHourManagerReqParam.getSel_department())){
   			totalHourManagerReqParam.setSel_department(null);
   		}
   		//课别
   		if("".equals(totalHourManagerReqParam.getSel_branch())){
   			totalHourManagerReqParam.setSel_branch(null);
   		}
   		//员工类别
   		if("".equals(totalHourManagerReqParam.getSel_sort())){
   			totalHourManagerReqParam.setSel_sort(null);
   		}else{
   			 totalHourManagerReqParam.setSel_sort(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_sort(),"UTF-8")); 
   		}
   		/*//机种
   		if("".equals(totalHourManagerReqParam.getSel_jobCategory())){
   			totalHourManagerReqParam.setSel_jobCategory(null);
   		}else{
   			 totalHourManagerReqParam.setSel_jobCategory(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_jobCategory(),"UTF-8")); 
   		}*/
   		//委托号
   		if("".equals(totalHourManagerReqParam.getSerach_projectClientNo())){
   			totalHourManagerReqParam.setSerach_projectClientNo(null);
   		}else{
   		    totalHourManagerReqParam.setSerach_projectClientNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_projectClientNo(),"UTF-8")); 
   		}
   		//项目号
   		if("0".equals(totalHourManagerReqParam.getSel_PJNo())){
   			totalHourManagerReqParam.setSel_PJNo(null);
   		}else{
   		    totalHourManagerReqParam.setSel_PJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJNo(),"UTF-8")); 
   		}
   		//临时项目号
   		if("0".equals(totalHourManagerReqParam.getSel_tempPJNo())){
   			totalHourManagerReqParam.setSel_tempPJNo(null);
   		}else{
   		    totalHourManagerReqParam.setSel_tempPJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_tempPJNo(),"UTF-8")); 
   		}
   				
   		//商权重别
   		/*if("".equals(selectStaffWNDetailHistoryReqParam.getSel_pjType())){
   			selectStaffWNDetailHistoryReqParam.setSel_pjType(null);
   		}*/
   		
   		//能力
   		/*if("".equals(selectStaffWNDetailHistoryReqParam.getSel_ability())){
   			selectStaffWNDetailHistoryReqParam.setSel_ability(null);
   		}*/
   		//PJ名
   		if("".equals(totalHourManagerReqParam.getSel_PJName())){
   			
   			totalHourManagerReqParam.setSel_PJName(null);
   		}else{
   		
   			totalHourManagerReqParam.setSel_PJName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJName(),"UTF-8")); 
   		}
   		//管理项番
   		if("".equals(totalHourManagerReqParam.getSerach_transferNo())){
   			totalHourManagerReqParam.setSerach_transferNo(null);
   		}else{
   			totalHourManagerReqParam.setSerach_transferNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_transferNo(),"UTF-8")); 
   		}
   		//开发阶段
   		if("".equals(totalHourManagerReqParam.getSel_category())){
   			totalHourManagerReqParam.setSel_category(null);
   		}else{
   			totalHourManagerReqParam.setSel_category(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_category(),"UTF-8")); 
   		}
   		//委托方
   		if("".equals(totalHourManagerReqParam.getSel_projectClientName())){
   			totalHourManagerReqParam.setSel_projectClientName(null);
   		}else{
   			
   			totalHourManagerReqParam.setSel_projectClientName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectClientName(), "UTF-8"));
   		}
   		//项目功能function
   		if("".equals(totalHourManagerReqParam.getSel_projectFunction())){
   			totalHourManagerReqParam.setSel_projectFunction(null);
   		}else{
   			totalHourManagerReqParam.setSel_projectFunction(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectFunction(), "UTF-8"));
   		}
   		//车厂
   		if("".equals(totalHourManagerReqParam.getSel_carMaker())){
   			totalHourManagerReqParam.setSel_carMaker(null);
   		}else{
   			totalHourManagerReqParam.setSel_carMaker(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_carMaker(),"UTF-8")); 
   		}
   		//归属
   		if("0".equals(totalHourManagerReqParam.getSel_belong())){
   			totalHourManagerReqParam.setSel_belong(null);
   		}else{
   			totalHourManagerReqParam.setSel_belong(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_belong(),"UTF-8")); 
   		}
   		
   		

   		totalHourManagerReqParam.setRp(totalHourManagerReqParam.getLimit());
   		//设置数据库查询时从哪条数据开始
   		totalHourManagerReqParam.setPageSeq((totalHourManagerReqParam.getPage()-1)*totalHourManagerReqParam.getRp());
   	
   		List<TotalHourManager> searchList = totalHourManagerListService.searchListCXEE(totalHourManagerReqParam);
   		
   		int totalPageCount = totalHourManagerListService.totalPageCountCXEE(totalHourManagerReqParam);
   		String Staffname = "";
   		int k = totalHourManagerReqParam.getPageSeq()+1;
   		for (int i= 0;i<searchList.size();i++) {
   			if (i== 0) {
   				Staffname = searchList.get(0).getStaffName();
   			}
   			if(Staffname.equals(searchList.get(i).getStaffName())){
   				searchList.get(i).setNo(String.valueOf(k));
   			}else{
   				++k;
   				searchList.get(i).setNo(String.valueOf(k));
   				Staffname = searchList.get(i).getStaffName();
   			}
   		}
   		Gson gson = new Gson();
   		String resultString = gson.toJson(searchList);
   		
   		String result ="{\"totalCount\":"+totalPageCount+",\"result\":"+resultString+"}";
   		response.setCharacterEncoding("UTF-8");
   		PrintWriter out = response.getWriter();
   		out.write(result);
   		out.close();
   	}
    
    @RequestMapping(value = "/listManHourCT")
   	public void searchButtonClickCT(TotalHourManageReqParam totalHourManagerReqParam,HttpServletResponse response,HttpSession httpSession) throws IOException{
       	//获取当前时间
       	Date date = new Date();
       	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
       	String currentYearDay = ym.format(date);
       	String currentYearMonth = currentYearDay.substring(0,7);
       	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
       		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
   		}else{
   			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
   		}
       	
       	
       	//姓名转换 null和编码转换
       	if("".equals(totalHourManagerReqParam.getSerach_staffName())){
       		totalHourManagerReqParam.setSerach_staffName(null);
       	}else{
       		totalHourManagerReqParam.setSerach_staffName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_staffName(),"UTF-8")); 
       	}
       	//职则
       	if("".equals(totalHourManagerReqParam.getSel_position())){
       		totalHourManagerReqParam.setSel_position(null);
       	}else{
       		totalHourManagerReqParam.setSel_position(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_position(),"UTF-8"));
       	}
       	
       	//部门
   		if("".equals(totalHourManagerReqParam.getSel_department())){
   			totalHourManagerReqParam.setSel_department(null);
   		}
   		//课别
   		if("".equals(totalHourManagerReqParam.getSel_branch())){
   			totalHourManagerReqParam.setSel_branch(null);
   		}
   		//员工类别
   		if("".equals(totalHourManagerReqParam.getSel_sort())){
   			totalHourManagerReqParam.setSel_sort(null);
   		}else{
   			 totalHourManagerReqParam.setSel_sort(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_sort(),"UTF-8")); 
   		}
   		//机种
   		if("".equals(totalHourManagerReqParam.getSel_jobCategory())){
   			totalHourManagerReqParam.setSel_jobCategory(null);
   		}else{
   			 totalHourManagerReqParam.setSel_jobCategory(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_jobCategory(),"UTF-8")); 
   		}
   		/*//委托号
   		if("".equals(totalHourManagerReqParam.getSerach_projectClientNo())){
   			totalHourManagerReqParam.setSerach_projectClientNo(null);
   		}else{
   		    totalHourManagerReqParam.setSerach_projectClientNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_projectClientNo(),"UTF-8")); 
   		}*/
   		//项目号
   		if("0".equals(totalHourManagerReqParam.getSel_PJNo())){
   			totalHourManagerReqParam.setSel_PJNo(null);
   		}else{
   		    totalHourManagerReqParam.setSel_PJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJNo(),"UTF-8")); 
   		}
   		//临时项目号
   		if("0".equals(totalHourManagerReqParam.getSel_tempPJNo())){
   			totalHourManagerReqParam.setSel_tempPJNo(null);
   		}else{
   		    totalHourManagerReqParam.setSel_tempPJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_tempPJNo(),"UTF-8")); 
   		}
   				
   		
   		//PJ名
   		if("".equals(totalHourManagerReqParam.getSel_PJName().trim())){
   			totalHourManagerReqParam.setSel_PJName(null);
   		}else{
   			totalHourManagerReqParam.setSel_PJName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJName(),"UTF-8")); 
   		}
   		/*//管理项番
   		if("".equals(totalHourManagerReqParam.getSerach_transferNo())){
   			totalHourManagerReqParam.setSerach_transferNo(null);
   		}else{
   			totalHourManagerReqParam.setSerach_transferNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_transferNo(),"UTF-8")); 
   		}*/
   		//开发阶段
   		if("".equals(totalHourManagerReqParam.getSel_category())){
   			totalHourManagerReqParam.setSel_category(null);
   		}else{
   			totalHourManagerReqParam.setSel_category(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_category(),"UTF-8")); 
   		}
   		/*//委托方
   		if("".equals(totalHourManagerReqParam.getSel_projectClientName())){
   			totalHourManagerReqParam.setSel_projectClientName(null);
   		}else{
   			
   			totalHourManagerReqParam.setSel_projectClientName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectClientName(), "UTF-8"));
   		}*/
   		//项目功能function
   		if("".equals(totalHourManagerReqParam.getSel_projectFunction())){
   			totalHourManagerReqParam.setSel_projectFunction(null);
   		}else{
   			totalHourManagerReqParam.setSel_projectFunction(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectFunction(), "UTF-8"));
   		}
   		//车厂
   		if("".equals(totalHourManagerReqParam.getSel_carMaker())){
   			totalHourManagerReqParam.setSel_carMaker(null);
   		}else{
   			totalHourManagerReqParam.setSel_carMaker(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_carMaker(),"UTF-8")); 
   		}
   		//归属
   		if("0".equals(totalHourManagerReqParam.getSel_belong())){
   			totalHourManagerReqParam.setSel_belong(null);
   		}else{
   			totalHourManagerReqParam.setSel_belong(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_belong(),"UTF-8")); 
   		}
   		
   		

   		totalHourManagerReqParam.setRp(totalHourManagerReqParam.getLimit());
   		//设置数据库查询时从哪条数据开始
   		totalHourManagerReqParam.setPageSeq((totalHourManagerReqParam.getPage()-1)*totalHourManagerReqParam.getRp());
   	
   		List<TotalHourManager> searchList = totalHourManagerListService.searchListCT(totalHourManagerReqParam);
   		int totalPageCount = totalHourManagerListService.totalPageCountCT(totalHourManagerReqParam);
   		String Staffname = "";
   		int k = totalHourManagerReqParam.getPageSeq()+1;
   		for (int i= 0;i<searchList.size();i++) {
   			if (i== 0) {
   				Staffname = searchList.get(0).getStaffName();
   			}
   			if(Staffname.equals(searchList.get(i).getStaffName())){
   				searchList.get(i).setNo(String.valueOf(k));
   			}else{
   				++k;
   				searchList.get(i).setNo(String.valueOf(k));
   				Staffname = searchList.get(i).getStaffName();
   			}
   		}
   		Gson gson = new Gson();
   		String resultString = gson.toJson(searchList);
   		
   		String result ="{\"totalCount\":"+totalPageCount+",\"result\":"+resultString+"}";
   		response.setCharacterEncoding("UTF-8");
   		PrintWriter out = response.getWriter();
   		out.write(result);
   		out.close();
   	}
    @RequestMapping(value = "/listSumManHour")
    public void sumSearch(TotalHourManageReqParam totalHourManagerReqParam,HttpServletResponse response,HttpSession httpSession) throws IOException{
    	
    	Date date = new Date();
    	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
    	String currentYearDay = ym.format(date);
    	String currentYearMonth = currentYearDay.substring(0,7);
    	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
    		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
		}else{
			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
		}
    	
    
		//员工类别
		if("".equals(totalHourManagerReqParam.getSel_sort())){
			totalHourManagerReqParam.setSel_sort(null);
		}else{
    		totalHourManagerReqParam.setSel_sort(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_sort(),"UTF-8"));
    	}
		
		//项目号
		if("0".equals(totalHourManagerReqParam.getSel_PJNo())){
			totalHourManagerReqParam.setSel_PJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_PJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJNo(),"UTF-8")); 
		}
		//临时项目号
		if("0".equals(totalHourManagerReqParam.getSel_tempPJNo())){
			totalHourManagerReqParam.setSel_tempPJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_tempPJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_tempPJNo(),"UTF-8")); 
		}
		//PJ名
		if("".equals(totalHourManagerReqParam.getSel_PJName())){
			totalHourManagerReqParam.setSel_PJName(null);
		}else{
			totalHourManagerReqParam.setSel_PJName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJName(),"UTF-8")); 
		}
		//管理项番
		if("".equals(totalHourManagerReqParam.getSerach_transferNo())){
			totalHourManagerReqParam.setSerach_transferNo(null);
		}else{
			totalHourManagerReqParam.setSerach_transferNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_transferNo(),"UTF-8")); 
		}
		//开发阶段
		if("".equals(totalHourManagerReqParam.getSel_category())){
			totalHourManagerReqParam.setSel_category(null);
		}else{
			totalHourManagerReqParam.setSel_category(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_category(),"UTF-8")); 
		}
		
		//项目功能function
		if("".equals(totalHourManagerReqParam.getSel_projectFunction())){
			totalHourManagerReqParam.setSel_projectFunction(null);
		}else{
			totalHourManagerReqParam.setSel_projectFunction(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectFunction(), "UTF-8"));
		}
		//车厂
		if("".equals(totalHourManagerReqParam.getSel_carMaker())){
			totalHourManagerReqParam.setSel_carMaker(null);
		}else{
			totalHourManagerReqParam.setSel_carMaker(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_carMaker(),"UTF-8"));
		}
		//归属
		if("0".equals(totalHourManagerReqParam.getSel_belong())){

			totalHourManagerReqParam.setSel_belong(null);
		}else{
			totalHourManagerReqParam.setSel_belong(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_belong(),"UTF-8")); 
		}
		TotalHourManagerSum sumResult = totalHourManagerListService.sumSearch(totalHourManagerReqParam);
		Gson gson = new Gson();
		String resultString = gson.toJson(sumResult);
		String result = "{\"result\":" + resultString +"}";
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
    }
    
    @RequestMapping(value = "/listSumManHourCT")
    public void sumSearchCT(TotalHourManageReqParam totalHourManagerReqParam,HttpServletResponse response,HttpSession httpSession) throws IOException{
    	
    	Date date = new Date();
    	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
    	String currentYearDay = ym.format(date);
    	String currentYearMonth = currentYearDay.substring(0,7);
    	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
    		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
		}else{
			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
		}
    	
    	
    	//姓名转换 null和编码转换
    	if("".equals(totalHourManagerReqParam.getSerach_staffName())){
    		totalHourManagerReqParam.setSerach_staffName(null);
    	}else{
    		
    		totalHourManagerReqParam.setSerach_staffName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_staffName(),"UTF-8")); 
    	}
    	//职则
    	if("".equals(totalHourManagerReqParam.getSel_position())){
    		totalHourManagerReqParam.setSel_position(null);
    	}else{
    		totalHourManagerReqParam.setSel_position(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_position(),"UTF-8"));
    	}
    	
    
    	//部门
		if("".equals(totalHourManagerReqParam.getSel_department())){
			totalHourManagerReqParam.setSel_department(null);
		}
		//课别
		if("".equals(totalHourManagerReqParam.getSel_branch())){
			totalHourManagerReqParam.setSel_branch(null);
		}
		//员工类别
		if("".equals(totalHourManagerReqParam.getSel_sort())){
			totalHourManagerReqParam.setSel_sort(null);
		}else{
    		totalHourManagerReqParam.setSel_sort(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_sort(),"UTF-8"));
    	}
		//机种
		if("".equals(totalHourManagerReqParam.getSel_jobCategory())){
			totalHourManagerReqParam.setSel_jobCategory(null);
		}else{
			 totalHourManagerReqParam.setSel_jobCategory(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_jobCategory(),"UTF-8")); 
		}
		/*//委托号
		if("".equals(totalHourManagerReqParam.getSerach_projectClientNo())){
			totalHourManagerReqParam.setSerach_projectClientNo(null);
		}else{
		    totalHourManagerReqParam.setSerach_projectClientNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_projectClientNo(),"UTF-8")); 
		}*/
		//项目号
		if("0".equals(totalHourManagerReqParam.getSel_PJNo())){
			totalHourManagerReqParam.setSel_PJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_PJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJNo(),"UTF-8")); 
		}
		//临时项目号
		if("0".equals(totalHourManagerReqParam.getSel_tempPJNo())){
			totalHourManagerReqParam.setSel_tempPJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_tempPJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_tempPJNo(),"UTF-8")); 
		}
		//PJ名
		if("".equals(totalHourManagerReqParam.getSel_PJName())){
			totalHourManagerReqParam.setSel_PJName(null);
		}else{
			totalHourManagerReqParam.setSel_PJName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJName(),"UTF-8")); 
		}
		/*//管理项番
		if("".equals(totalHourManagerReqParam.getSerach_transferNo())){
			totalHourManagerReqParam.setSerach_transferNo(null);
		}else{
			totalHourManagerReqParam.setSerach_transferNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_transferNo(),"UTF-8")); 
		}*/
		//开发阶段
		if("".equals(totalHourManagerReqParam.getSel_category())){
			totalHourManagerReqParam.setSel_category(null);
		}else{
			totalHourManagerReqParam.setSel_category(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_category(),"UTF-8")); 
		}
		/*//委托方
		if("".equals(totalHourManagerReqParam.getSel_projectClientName())){
			totalHourManagerReqParam.setSel_projectClientName(null);
		}else{
			totalHourManagerReqParam.setSel_projectClientName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectClientName(),"UTF-8")); 
		}*/
		//项目功能function
		if("".equals(totalHourManagerReqParam.getSel_projectFunction())){
			totalHourManagerReqParam.setSel_projectFunction(null);
		}else{
			totalHourManagerReqParam.setSel_projectFunction(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectFunction(), "UTF-8"));
		}
		//车厂
		if("".equals(totalHourManagerReqParam.getSel_carMaker())){
			totalHourManagerReqParam.setSel_carMaker(null);
		}else{
			totalHourManagerReqParam.setSel_carMaker(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_carMaker(),"UTF-8"));
		}
		//归属
		if("0".equals(totalHourManagerReqParam.getSel_belong())){

			totalHourManagerReqParam.setSel_belong(null);
		}else{
			totalHourManagerReqParam.setSel_belong(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_belong(),"UTF-8")); 
		}
		TotalHourManagerSum sumResult = totalHourManagerListService.sumSearchCT(totalHourManagerReqParam);
		Gson gson = new Gson();
		String resultString = gson.toJson(sumResult);
		String result = "{\"result\":" + resultString +"}";
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
    }
    
    @RequestMapping(value = "/listSumManHourCXEE")
    public void sumSearchCXEE(TotalHourManageReqParam totalHourManagerReqParam,HttpServletResponse response,HttpSession httpSession) throws IOException{
    	
    	Date date = new Date();
    	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
    	String currentYearDay = ym.format(date);
    	String currentYearMonth = currentYearDay.substring(0,7);
    	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
    		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
		}else{
			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
		}
    	
    	
    	//姓名转换 null和编码转换
    	if("".equals(totalHourManagerReqParam.getSerach_staffName())){
    		totalHourManagerReqParam.setSerach_staffName(null);
    	}else{
    		
    		totalHourManagerReqParam.setSerach_staffName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_staffName(),"UTF-8")); 
    	}
    	//职则
    	if("".equals(totalHourManagerReqParam.getSel_position())){
    		totalHourManagerReqParam.setSel_position(null);
    	}else{
    		totalHourManagerReqParam.setSel_position(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_position(),"UTF-8"));
    	}
    	
    
    	//部门
		if("".equals(totalHourManagerReqParam.getSel_department())){
			totalHourManagerReqParam.setSel_department(null);
		}
		//课别
		if("".equals(totalHourManagerReqParam.getSel_branch())){
			totalHourManagerReqParam.setSel_branch(null);
		}
		//员工类别
		if("".equals(totalHourManagerReqParam.getSel_sort())){
			totalHourManagerReqParam.setSel_sort(null);
		}else{
    		totalHourManagerReqParam.setSel_sort(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_sort(),"UTF-8"));
    	}
		/*//机种
		if("".equals(totalHourManagerReqParam.getSel_jobCategory())){
			totalHourManagerReqParam.setSel_jobCategory(null);
		}else{
			 totalHourManagerReqParam.setSel_jobCategory(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_jobCategory(),"UTF-8")); 
		}*/
		//委托号
		if("".equals(totalHourManagerReqParam.getSerach_projectClientNo())){
			totalHourManagerReqParam.setSerach_projectClientNo(null);
		}else{
		    totalHourManagerReqParam.setSerach_projectClientNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_projectClientNo(),"UTF-8")); 
		}
		//项目号
		if("0".equals(totalHourManagerReqParam.getSel_PJNo())){
			totalHourManagerReqParam.setSel_PJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_PJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJNo(),"UTF-8")); 
		}
		//临时项目号
		if("0".equals(totalHourManagerReqParam.getSel_tempPJNo())){
			totalHourManagerReqParam.setSel_tempPJNo(null);
		}else{
		    totalHourManagerReqParam.setSel_tempPJNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_tempPJNo(),"UTF-8")); 
		}
		//PJ名
		if("".equals(totalHourManagerReqParam.getSel_PJName())){
			totalHourManagerReqParam.setSel_PJName(null);
		}else{
			totalHourManagerReqParam.setSel_PJName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_PJName(),"UTF-8")); 
		}
		//管理项番
		if("".equals(totalHourManagerReqParam.getSerach_transferNo())){
			totalHourManagerReqParam.setSerach_transferNo(null);
		}else{
			totalHourManagerReqParam.setSerach_transferNo(java.net.URLDecoder.decode(totalHourManagerReqParam.getSerach_transferNo(),"UTF-8")); 
		}
		//开发阶段
		if("".equals(totalHourManagerReqParam.getSel_category())){
			totalHourManagerReqParam.setSel_category(null);
		}else{
			totalHourManagerReqParam.setSel_category(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_category(),"UTF-8")); 
		}
		//委托方
		if("".equals(totalHourManagerReqParam.getSel_projectClientName())){
			totalHourManagerReqParam.setSel_projectClientName(null);
		}else{
			totalHourManagerReqParam.setSel_projectClientName(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectClientName(),"UTF-8")); 
		}
		//项目功能function
		if("".equals(totalHourManagerReqParam.getSel_projectFunction())){
			totalHourManagerReqParam.setSel_projectFunction(null);
		}else{
			totalHourManagerReqParam.setSel_projectFunction(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_projectFunction(), "UTF-8"));
		}
		//车厂
		if("".equals(totalHourManagerReqParam.getSel_carMaker())){
			totalHourManagerReqParam.setSel_carMaker(null);
		}else{
			totalHourManagerReqParam.setSel_carMaker(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_carMaker(),"UTF-8"));
		}
		//归属
		if("0".equals(totalHourManagerReqParam.getSel_belong())){

			totalHourManagerReqParam.setSel_belong(null);
		}else{
			totalHourManagerReqParam.setSel_belong(java.net.URLDecoder.decode(totalHourManagerReqParam.getSel_belong(),"UTF-8")); 
		}
		TotalHourManagerSum sumResult = totalHourManagerListService.sumSearchCXEE(totalHourManagerReqParam);
		
		Gson gson = new Gson();
		String resultString = gson.toJson(sumResult);
		String result = "{\"result\":" + resultString +"}";
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
    }
    
    @RequestMapping(value ="/query")
    public void totalQuery(HttpServletResponse response,HttpSession session,ManhourPersonalQueryParam queryParam) throws IOException {

        //设置数据库查询时从哪条数据开始
        queryParam.setPageSeq((queryParam.getPage() - 1) * queryParam.getRp());

        //如果搜索词为空,则将值设为NULL, 便于Mybatis mapper使用
        if(queryParam.getCategory() != null && queryParam.getCategory().trim().equals("--请选择--")){

            queryParam.setCategory(null);
        }

        if(queryParam.getQuery() != null && queryParam.getQuery().trim().equals("--请选择--")){

            if(queryParam.getQtype().trim().equals("category")){

                queryParam.setCategory(queryParam.getQuery().trim());
            } else if(queryParam.getQtype().trim().equals("projectName")){

                queryParam.setProjectName(queryParam.getQuery().trim());
            }
        }

        if ("--请选择--".equals(queryParam.getDepartmentID())) {

            queryParam.setDepartmentID(null);
        }

        if ("--请选择--".equals(queryParam.getBranchID())) {

            queryParam.setBranchID(null);
        }

        if ("--请选择--".equals(queryParam.getName())) {

            queryParam.setName(null);
        }

        if ("--请选择--".equals(queryParam.getProjectClientName())) {

            queryParam.setProjectClientName(null);
        }

        if ("--请选择--".equals(queryParam.getFunction())) {

            queryParam.setFunction(null);
        }

        if ("--请选择--".equals(queryParam.getTask())) {

            queryParam.setTask(null);
        }

        if (queryParam.getStartDate() != null && queryParam.getEndDate() != null) {

            List<ManhourPersonalQueryView> queryList = manHourService.totalHourQuery(queryParam);
            int totalPageCount                       = manHourService.TotalPageCountForTotalHourQuery(queryParam);

            int tempPageSeq = queryParam.getPageSeq();//用于保存pageSeq值
            int tempRP      = queryParam.getRp();//用于保存rp值
            queryParam.setPageSeq(0);
            queryParam.setRp(totalPageCount);

            double tempAllTime = 0.0;//--------------浮点型显示问题---------------
            List<ManhourPersonalQueryView> queryListForAll = manHourService.totalHourQuery(queryParam);
            for (int i = 0; i < queryListForAll.size(); i++) {
                double dTemp = queryListForAll.get(i).getTotalTimes();
                //tempAllTime += dTemp;
                tempAllTime = Arith.add(tempAllTime, dTemp);//高精度算术类方法
            }
            queryParam.setPageSeq(tempPageSeq);
            queryParam.setRp(tempRP);
            ManhourPersonalQueryView allTimeView = new ManhourPersonalQueryView();
            allTimeView.setProjectName("总工数统计");
            allTimeView.setCategory("统计");
            allTimeView.setTotalTimes(tempAllTime);
            ManhourPersonalResponseRows allTimeRow = new ManhourPersonalResponseRows(0, allTimeView);

            List<ManhourPersonalResponseRows> manhourPersonalResponseList = new ArrayList<ManhourPersonalResponseRows>();
            manhourPersonalResponseList.add(allTimeRow);

            for (int i=0;i<queryList.size();i++) {
                ManhourPersonalResponseRows manhourPersonalResponseRows = new ManhourPersonalResponseRows(i+1, queryList.get(i));
                manhourPersonalResponseList.add(manhourPersonalResponseRows);
            }
            ManhourPersonalResponse manhourPersonalResponse = new ManhourPersonalResponse();
            manhourPersonalResponse.setPage(queryParam.getPage());        //设置
            manhourPersonalResponse.setTotal(totalPageCount);
            manhourPersonalResponse.setRows(manhourPersonalResponseList);
            Gson gson = new Gson();
            String resultString = gson.toJson(manhourPersonalResponse);

            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(resultString);
            out.close();
        }
    }

    

    @RequestMapping(value = "/companystaffselect")
    public void saveCompanystaffselect(String startDate
    		                          ,String endDate
    		                          ,String departmentID
    		                          ,HttpServletResponse response
    		                          ,HttpSession httpSession) throws IOException {

    	Map<String, Object> params = new HashMap<String, Object>();

    	if (startDate == null || (startDate != null && "".equals(startDate.trim()))) {
    		params.put("startDate", null);
    	} else {
    		params.put("startDate",startDate);
    	}

        if (endDate == null || (endDate != null && "".equals(endDate.trim()))) {
        	params.put("endDate", null);
    	} else {
    		params.put("endDate", endDate);
    	}
        params.put("departmentID",departmentID);

        List<String> staffselectList=calendarManhourService.listCompanystaffselect(params);
        Gson gson = new Gson();
        String resultString = gson.toJson(staffselectList);

        String result ="{\"result\":"+resultString+"}";
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(result);
        out.close();
    }

    @RequestMapping(value = "/staffselect")
    public void savestaffselect(String startDate
                               ,String endDate
                               ,String branchID
                               ,HttpServletResponse response
                               ,HttpSession httpSession) throws IOException {
    	Map<String, Object> params = new HashMap<String, Object>();

    	if (startDate == null || (startDate != null && "".equals(startDate.trim()))) {
    		params.put("startDate", null);
    	} else {
    		params.put("startDate",startDate);
    	}

        if (endDate == null || (endDate != null && "".equals(endDate.trim()))) {
        	params.put("endDate", null);
    	} else {
    		params.put("endDate", endDate);
    	}
        params.put("branchID",branchID);

        List<String> staffselectList=calendarManhourService.liststaffselect(params);
        Gson gson = new Gson();
        String resultString = gson.toJson(staffselectList);

        String result ="{\"result\":"+resultString+"}";
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(result);
        out.close();
    }

    @RequestMapping(value = "/taskselect")
    public void savetaskselect(String departmentID,HttpServletResponse response,HttpSession httpSession) throws IOException {

        List<String> taskselectList=projectTaskService.getTaskByDepartment(departmentID);
        Gson gson = new Gson();
        String resultString = gson.toJson(taskselectList);

        String result ="{\"result\":"+resultString+"}";
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(result);
        out.close();
    }

    @RequestMapping(value = "/detailManhour",method=RequestMethod.GET)
    public ModelAndView detailManhour(HttpServletResponse response
                                     ,HttpSession session
                                     ,String startDate
                                     ,String endDate
                                     ,String departmentID
                                     ,String department
                                     ,String branchID
                                     ,String branch
                                     ,String projectClientName
                                     ,String projectFunction
                                     ,String projectTask) throws IOException, WriteException{

    	department = java.net.URLDecoder.decode(java.net.URLDecoder.decode(department,"UTF-8"),"UTF-8");
    	branch = java.net.URLDecoder.decode(java.net.URLDecoder.decode(branch,"UTF-8"),"UTF-8");
    	projectClientName = java.net.URLDecoder.decode(java.net.URLDecoder.decode(projectClientName,"UTF-8"),"UTF-8");
    	projectFunction = java.net.URLDecoder.decode(java.net.URLDecoder.decode(projectFunction,"UTF-8"),"UTF-8");
    	projectTask = java.net.URLDecoder.decode(java.net.URLDecoder.decode(projectTask,"UTF-8"),"UTF-8");
    	
        if (branchID != null && branchID.equals("--请选择--")) {
            branchID = null;
        }
        if (projectClientName != null && projectClientName.equals("--请选择--")) {
            projectClientName = null;
        }
        if (projectFunction != null && projectFunction.equals("--请选择--")) {
            projectFunction = null;
        }
        if (projectTask != null && projectTask.equals("--请选择--")) {
            projectTask = null;
        }

        Map<String, Object> paramsDepart = new HashMap<String, Object>();

        paramsDepart.put("startDate", startDate);
        paramsDepart.put("endDate", endDate);
        paramsDepart.put("departmentID", departmentID);
        paramsDepart.put("branchID", branchID);
        paramsDepart.put("projectClientName", projectClientName);
        paramsDepart.put("projectFunction", projectFunction);
        paramsDepart.put("projectTask", projectTask);
        
        List<Integer> designStaffID            = manHourService.getDesignStaffID(paramsDepart); //设计要员数ID
        List<Integer> workNumProjectIdlist     = manHourService.getDepartWorkNumProjectId(paramsDepart); //有效录入项目ID
        List<ProjectTime> totalProjectTimeList = new ArrayList<ProjectTime>();

        for (int i = 0; i < designStaffID.size(); i++) {

            for (int m = 0; m < workNumProjectIdlist.size(); m++) {

                paramsDepart.put("staffID", designStaffID.get(i));
                paramsDepart.put("projectID", workNumProjectIdlist.get(m));
                ProjectTime projectTime=new ProjectTime();
                projectTime.setName(manHourService.getStaffName(designStaffID.get(i))); //设置员工姓名
                projectTime.setDepartment(department);//设置部门名称
                projectTime.setBranch(branch);//设置课别名称
                projectTime.setProjectName(manHourService.getProjectName(workNumProjectIdlist.get(m))); //设置项目名
                projectTime.setTotalTime(manHourService.getPersonTotalTime(paramsDepart)); //设置总工数
                if (projectTime.getTotalTime()!=0) {
                   totalProjectTimeList.add(projectTime);
                }
            }
        }
        DataToExcle dataToExcle = new DataToExcle();
        String realPathString   = session.getServletContext().getRealPath("temp");
        String contextpath      = session.getServletContext().getContextPath();
        String webadr           = contextpath + dataToExcle.toExcel(totalProjectTimeList,realPathString);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/manhour/download");
        mv.addObject("webadr", webadr);
        response.setCharacterEncoding("UTF-8");
        return mv;
    }
    
    
    @RequestMapping(value = "/downloadTotalHour")
	public ModelAndView outPutTotalHour(HttpServletRequest request , HttpSession session,HttpServletResponse response) throws IOException {

    	TotalHourManageReqParam totalHourManagerReqParam = new TotalHourManageReqParam();
        
    	Date date = new Date();
       	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
       	String currentYearDay = ym.format(date);
       	String currentYearMonth = currentYearDay.substring(0,7);
       	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
       		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
   		}else{
   			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
   		}
       	request.setCharacterEncoding("UTF-8");
       	
       	
       	String sort=request.getParameter("sort").trim();
       	sort = new String(sort.getBytes("ISO8859-1"),"UTF-8");
       	String PJNo=request.getParameter("PJNo").trim();
       	PJNo = new String(PJNo.getBytes("ISO8859-1"),"UTF-8");
       	String tempPJNo=request.getParameter("tempPJNo").trim();
       	tempPJNo = new String(tempPJNo.getBytes("ISO8859-1"),"UTF-8");
       	String transferNo=request.getParameter("transferNo").trim();
       	transferNo = new String(transferNo.getBytes("ISO8859-1"),"UTF-8");
       	String category=request.getParameter("category").trim();
       	category = new String(category.getBytes("ISO8859-1"),"UTF-8");
       	String carMaker=request.getParameter("carMaker").trim();
       	carMaker = new String(carMaker.getBytes("ISO8859-1"),"UTF-8");
       	String projectFunction=request.getParameter("projectFunction").trim();
       	projectFunction = new String(projectFunction.getBytes("ISO8859-1"),"UTF-8");
       	String PJName=request.getParameter("PJName").trim();
       	PJName = new String(PJName.getBytes("ISO8859-1"),"UTF-8");
       	
       
       	if("".equals(sort) ){
       		totalHourManagerReqParam.setSel_sort(null);
       	}else {
       		totalHourManagerReqParam.setSel_sort(sort);
       	}
       	if("0".equals(PJNo) ){
       		totalHourManagerReqParam.setSel_PJNo(null);
       	}else {
       		totalHourManagerReqParam.setSel_PJNo(PJNo);
       	}
       	if("0".equals(tempPJNo) ){
       		totalHourManagerReqParam.setSel_tempPJNo(null);
       	}else {
       		totalHourManagerReqParam.setSel_tempPJNo(tempPJNo);
       	}
       	if("".equals(transferNo) ){
       		totalHourManagerReqParam.setSerach_transferNo(null);
       	}else {
       		totalHourManagerReqParam.setSerach_transferNo(transferNo);
       	}
       	if("".equals(category) ){
       		totalHourManagerReqParam.setSel_category(null);
       	}else {
       		totalHourManagerReqParam.setSel_category(category);
       	}
       	if("".equals(carMaker) ){
       		totalHourManagerReqParam.setSel_carMaker(null);
       	}else {
       		totalHourManagerReqParam.setSel_carMaker(carMaker);
       	}
       	if("".equals(projectFunction) ){
       		totalHourManagerReqParam.setSel_projectFunction(null);
       	}else {
       		totalHourManagerReqParam.setSel_projectFunction(projectFunction);
       	}
       	if("".equals(PJName) ){
       		totalHourManagerReqParam.setSel_PJName(null);
       	}else {
       		totalHourManagerReqParam.setSel_PJName(PJName);
       	}
       	
       	
       
   		
   		List<TotalHourManager> searchList = totalHourManagerListService.searchListDownload(totalHourManagerReqParam);
    	
		String realPathString = session.getServletContext().getRealPath("temp");
		String contextpath = session.getServletContext().getContextPath(); 
		String webadr=contextpath+totalHourManagerListService.downloadTotalHour(searchList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
    @RequestMapping(value = "/downloadTotalHourCXEE")
	public ModelAndView outPutTotalHourCXCEE(HttpServletRequest request , HttpSession session,HttpServletResponse response) throws IOException {
    		
    	TotalHourManageReqParam totalHourManagerReqParam = new TotalHourManageReqParam();
    
    	Date date = new Date();
       	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
       	String currentYearDay = ym.format(date);
       	String currentYearMonth = currentYearDay.substring(0,7);
       	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
       		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
   		}else{
   			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
   		}
       	request.setCharacterEncoding("UTF-8");
       	String staffName=request.getParameter("staffName").trim();
       	staffName = new String(staffName.getBytes("ISO8859-1"),"UTF-8");
       	String position=request.getParameter("position").trim();
       	position = new String(position.getBytes("ISO8859-1"),"UTF-8");
       	String department=request.getParameter("department").trim();
       	department = new String(department.getBytes("ISO8859-1"),"UTF-8");
       	String branch=request.getParameter("branch").trim();
       	branch = new String(branch.getBytes("ISO8859-1"),"UTF-8");
       	String sort=request.getParameter("sort").trim();
       	sort = new String(sort.getBytes("ISO8859-1"),"UTF-8");
       	String jobCategory=request.getParameter("jobCategory").trim();
       	jobCategory = new String(jobCategory.getBytes("ISO8859-1"),"UTF-8");
       	String projectClientNo=request.getParameter("projectClientNo").trim();
       	projectClientNo = new String(projectClientNo.getBytes("ISO8859-1"),"UTF-8");
       	String PJNo=request.getParameter("PJNo").trim();
       	PJNo = new String(PJNo.getBytes("ISO8859-1"),"UTF-8");
       	String tempPJNo=request.getParameter("tempPJNo").trim();
       	tempPJNo = new String(tempPJNo.getBytes("ISO8859-1"),"UTF-8");
       	String transferNo=request.getParameter("transferNo").trim();
       	transferNo = new String(transferNo.getBytes("ISO8859-1"),"UTF-8");
       	String category=request.getParameter("category").trim();
       	category = new String(category.getBytes("ISO8859-1"),"UTF-8");
       	String projectClientName=request.getParameter("projectClientName").trim();
       	projectClientName = new String(projectClientName.getBytes("ISO8859-1"),"UTF-8");
       	String carMaker=request.getParameter("carMaker").trim();
       	carMaker = new String(carMaker.getBytes("ISO8859-1"),"UTF-8");
       	String projectFunction=request.getParameter("projectFunction").trim();
       	projectFunction = new String(projectFunction.getBytes("ISO8859-1"),"UTF-8");
       	String PJName=request.getParameter("PJName").trim();
       	PJName = new String(PJName.getBytes("ISO8859-1"),"UTF-8");
       	
       	if("".equals(staffName) ){
       		totalHourManagerReqParam.setSerach_staffName(null);
       	}else {
       		totalHourManagerReqParam.setSerach_staffName(java.net.URLDecoder.decode(staffName,"UTF-8"));
       	}
       	if("".equals(position) ){
       		totalHourManagerReqParam.setSel_position(null);
       	}else {
       		totalHourManagerReqParam.setSel_position(position);
       	}
       	if("".equals(department) ){
       		totalHourManagerReqParam.setSel_department(null);
       	}else {
       		totalHourManagerReqParam.setSel_department(department);
       	}
       	if("".equals(branch) ){
       		totalHourManagerReqParam.setSel_branch(null);
       	}else {
       		totalHourManagerReqParam.setSel_branch(branch);
       	}
       	if("".equals(sort) ){
       		totalHourManagerReqParam.setSel_sort(null);
       	}else {
       		totalHourManagerReqParam.setSel_sort(sort);
       	}
       	if("".equals(jobCategory) ){
       		totalHourManagerReqParam.setSel_jobCategory(null);
       	}else {
       		totalHourManagerReqParam.setSel_jobCategory(jobCategory);
       	}
       	if("".equals(projectClientNo) ){
       		totalHourManagerReqParam.setSerach_projectClientNo(null);
       	}else {
       		totalHourManagerReqParam.setSerach_projectClientNo(projectClientNo);
       	}
       	if("0".equals(PJNo) ){
       		totalHourManagerReqParam.setSel_PJNo(null);
       	}else {
       		totalHourManagerReqParam.setSel_PJNo(PJNo);
       	}
       	if("0".equals(tempPJNo) ){
       		totalHourManagerReqParam.setSel_tempPJNo(null);
       	}else {
       		totalHourManagerReqParam.setSel_tempPJNo(tempPJNo);
       	}
       	if("".equals(transferNo) ){
       		totalHourManagerReqParam.setSerach_transferNo(null);
       	}else {
       		totalHourManagerReqParam.setSerach_transferNo(transferNo);
       	}
       	if("".equals(category) ){
       		totalHourManagerReqParam.setSel_category(null);
       	}else {
       		totalHourManagerReqParam.setSel_category(category);
       	}
       	if("".equals(projectClientName) ){
       		totalHourManagerReqParam.setSel_projectClientName(null);
       	}else {
       		totalHourManagerReqParam.setSel_projectClientName(projectClientName);
       	}
       	if("".equals(carMaker) ){
       		totalHourManagerReqParam.setSel_carMaker(null);
       	}else {
       		totalHourManagerReqParam.setSel_carMaker(carMaker);
       	}
       	if("".equals(projectFunction) ){
       		totalHourManagerReqParam.setSel_projectFunction(null);
       	}else {
       		totalHourManagerReqParam.setSel_projectFunction(projectFunction);
       	}
       	if("".equals(PJName) ){
       		totalHourManagerReqParam.setSel_PJName(null);
       	}else {
       		totalHourManagerReqParam.setSel_PJName(PJName);
       	}
       	
   		List<TotalHourManager> searchList = totalHourManagerListService.searchListDownloadCXEE(totalHourManagerReqParam);
    	
		String realPathString = session.getServletContext().getRealPath("temp");
		String contextpath = session.getServletContext().getContextPath(); 
		String webadr=contextpath+totalHourManagerListService.downloadTotalHour(searchList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
    @RequestMapping(value = "/downloadTotalHourCT")
	public ModelAndView outPutTotalHourCT(HttpServletRequest request , HttpSession session,HttpServletResponse response) throws IOException {
    	
    	TotalHourManageReqParam totalHourManagerReqParam = new TotalHourManageReqParam();
        
    	Date date = new Date();
       	SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM-dd");
       	String currentYearDay = ym.format(date);
       	String currentYearMonth = currentYearDay.substring(0,7);
       	if("01".equals(currentYearMonth.substring(5, 7)) || "02".equals(currentYearMonth.substring(5, 7)) ||"03".equals(currentYearMonth.substring(5, 7))){
       		totalHourManagerReqParam.setCurrentYear( String.valueOf( (Integer.parseInt(currentYearMonth.substring(0, 4))-1) ));
   		}else{
   			totalHourManagerReqParam.setCurrentYear(currentYearMonth.substring(0, 4));
   		}
       	request.setCharacterEncoding("UTF-8");
       	
       	String staffName=request.getParameter("staffName").trim();
       	staffName = new String(staffName.getBytes("ISO8859-1"),"UTF-8");
       	String position=request.getParameter("position").trim();
       	position = new String(position.getBytes("ISO8859-1"),"UTF-8");
       	String department=request.getParameter("department").trim();
       	department = new String(department.getBytes("ISO8859-1"),"UTF-8");
       	String branch=request.getParameter("branch").trim();
       	branch = new String(branch.getBytes("ISO8859-1"),"UTF-8");
       	String sort=request.getParameter("sort").trim();
       	sort = new String(sort.getBytes("ISO8859-1"),"UTF-8");
       	String jobCategory=request.getParameter("jobCategory").trim();
       	jobCategory = new String(jobCategory.getBytes("ISO8859-1"),"UTF-8");
       	String PJNo=request.getParameter("PJNo").trim();
       	PJNo = new String(PJNo.getBytes("ISO8859-1"),"UTF-8");
       	String tempPJNo=request.getParameter("tempPJNo").trim();
       	tempPJNo = new String(tempPJNo.getBytes("ISO8859-1"),"UTF-8");
       	String category=request.getParameter("category").trim();
       	category = new String(category.getBytes("ISO8859-1"),"UTF-8");
       	String carMaker=request.getParameter("carMaker").trim();
       	carMaker = new String(carMaker.getBytes("ISO8859-1"),"UTF-8");
       	String projectFunction=request.getParameter("projectFunction").trim();
       	projectFunction = new String(projectFunction.getBytes("ISO8859-1"),"UTF-8");
       	String PJName=request.getParameter("PJName").trim();
       	PJName = new String(PJName.getBytes("ISO8859-1"),"UTF-8");
       	
       	if("".equals(staffName) ){
       		totalHourManagerReqParam.setSerach_staffName(null);
       	}else {
       		totalHourManagerReqParam.setSerach_staffName(java.net.URLDecoder.decode(staffName,"UTF-8"));
       	}
       	if("".equals(position) ){
       		totalHourManagerReqParam.setSel_position(null);
       	}else {
       		totalHourManagerReqParam.setSel_position(position);
       	}
       	if("".equals(department) ){
       		totalHourManagerReqParam.setSel_department(null);
       	}else {
       		totalHourManagerReqParam.setSel_department(department);
       	}
       	if("".equals(branch) ){
       		totalHourManagerReqParam.setSel_branch(null);
       	}else {
       		totalHourManagerReqParam.setSel_branch(branch);
       	}
       	if("".equals(sort) ){
       		totalHourManagerReqParam.setSel_sort(null);
       	}else {
       		totalHourManagerReqParam.setSel_sort(sort);
       	}
       	if("".equals(jobCategory) ){
       		totalHourManagerReqParam.setSel_jobCategory(null);
       	}else {
       		totalHourManagerReqParam.setSel_jobCategory(jobCategory);
       	}
       
       	if("0".equals(PJNo) ){
       		totalHourManagerReqParam.setSel_PJNo(null);
       	}else {
       		totalHourManagerReqParam.setSel_PJNo(PJNo);
       	}
       	if("0".equals(tempPJNo) ){
       		totalHourManagerReqParam.setSel_tempPJNo(null);
       	}else {
       		totalHourManagerReqParam.setSel_tempPJNo(tempPJNo);
       	}
       	if("".equals(category) ){
       		totalHourManagerReqParam.setSel_category(null);
       	}else {
       		totalHourManagerReqParam.setSel_category(category);
       	}
       	if("".equals(carMaker) ){
       		totalHourManagerReqParam.setSel_carMaker(null);
       	}else {
       		totalHourManagerReqParam.setSel_carMaker(carMaker);
       	}
       	if("".equals(projectFunction) ){
       		totalHourManagerReqParam.setSel_projectFunction(null);
       	}else {
       		totalHourManagerReqParam.setSel_projectFunction(projectFunction);
       	}
       	if("".equals(PJName) ){
       		totalHourManagerReqParam.setSel_PJName(null);
       	}else {
       		totalHourManagerReqParam.setSel_PJName(PJName);
       	}
       	
       	
       
   		List<TotalHourManager> searchList = totalHourManagerListService.searchListDownloadCT(totalHourManagerReqParam);
    	
		String realPathString = session.getServletContext().getRealPath("temp");
		String contextpath = session.getServletContext().getContextPath(); 
		String webadr=contextpath+totalHourManagerListService.downloadTotalHour(searchList, realPathString);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		
		return mv;
		
	}
    
}
