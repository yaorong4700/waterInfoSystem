package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 项目信息Controller
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-12-25
 * @histroy:
 * 	2012-12-25 GuoRenPeng
 *  # 初版
 *  
 *  2013-3-21 GuoRenPeng
 *  # 项目管理界面增加权限控制,请求项目管理界面返回Staff
 *  
 *  2013-3-26 GuoRenPeng
 *  # 因为添加工数的时候有与project表外键关联,修改代码对应
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.Category;
import com.clarion.worksys.entity.CategoryParam;
import com.clarion.worksys.entity.CheckValue;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.ProjectName;
import com.clarion.worksys.entity.ProjectUploadResponse;
import com.clarion.worksys.entity.RequestObject;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.StaffUploadResponse;
import com.clarion.worksys.entity.Task;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.httpentity.ProjectReqParam;
import com.clarion.worksys.httpentity.ProjectResponse;
import com.clarion.worksys.httpentity.ProjectResponseRows;
import com.clarion.worksys.service.ProjectSelectedService;
import com.clarion.worksys.service.ProjectService;
import com.clarion.worksys.service.StaffSelectedService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Const.AbilityEnum;
import com.clarion.worksys.util.Const.DepartmentEnum;
import com.clarion.worksys.util.Const.ProjectStateEnum;
import com.clarion.worksys.util.Const.ProjectStateEnumCT;
import com.clarion.worksys.util.Const.StateEnum;
import com.clarion.worksys.util.Const.StateEnumCT;
import com.clarion.worksys.util.DataToExcle;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
    private StaffSelectedService staffSelectedService;
	@Autowired
	private ProjectSelectedService projectSelectedService;
	@Autowired
	private UserRoleManageService userRoleManageService;
	
	public final static Map<String, CategoryParam> categoryMap = new HashMap<String, CategoryParam>();

	//填工数时要求能查看所有项目得部门
	private final String KAIFA_GUANLI_BU = "经营管理部";//2014-05-26,变更       开发管理部->经营管理部
	private final String SHEJI_KAIFA_BU = "开发支援部设计课";
	private final String GONGCHENG_SHEJI_BU = "工程设计部";
	private final String SHEJI_KAIFAGUANLI = "开发支援部开发管理课";
	private final String KAIFA_TONGKUO_SHI = "开发统括室";
	private final String CHINA_ODM_ZHIPINKAIFABU = "中国ODM制品开发部";
	//private final int NUM_OF_DEPARTMENT = 12;

	@RequestMapping
	public String projectList(RequestObject ro, Model model,HttpSession session) {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		List<Map<String, Object>> projectCategoryList = null;
		List<Map<String, Object>> projectCategoryListCT = null;
		List<Map<String, Object>> projectDepartmentListCXEE = null;
		List<Map<String, Object>> carmake =null;
		List<Map<String, Object>> carmakeCT =null;
		try {
			projectCategoryList = projectSelectedService.getProjectCategoryList();
			projectCategoryListCT = projectSelectedService.getProjectCategoryListCT();
			projectDepartmentListCXEE = projectSelectedService.getProjectDepartmentListCXEE();
			carmake = projectSelectedService.getCarMakeList();
			carmakeCT = projectSelectedService.getCarMakeListCT();
		} catch (Exception e){
		}
		
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"ProjectManage",userRoleManageService);
		model.addAttribute("staff", staff);
		model.addAttribute("projectCategoryList", projectCategoryList);
		model.addAttribute("projectCategoryListCT", projectCategoryListCT);
		model.addAttribute("projectDepartmentListCXEE", projectDepartmentListCXEE);
		model.addAttribute("projectCarmake",carmake);
		model.addAttribute("projectCarmakeCT",carmakeCT);
		model.addAttribute("QueryRoleFlag", userRole.getQueryRoleFlag());//検索
		model.addAttribute("AlterRoleFlag", userRole.getAlterRoleFlag());//編集
		model.addAttribute("UploadRoleFlag", userRole.getUploadRoleFlag());//U/L
		model.addAttribute("DownloadRoleFlag", userRole.getDownloadRoleFlag());//D/L
		model.addAttribute("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
		model.addAttribute("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
		model.addAttribute("DepartmentFlag", userRole.getDepartmentFlag());//部门权限
		return "admin/project/project";
	}
	
	@RequestMapping(value = "/getBranchCXEE")
	public void getBranchCXEE(String departmentID,HttpServletResponse response,HttpSession httpSession) throws IOException {
		List<Map<String, Object>> branchselectList=projectSelectedService.getBranchListCXEE(departmentID);
		Gson gson = new Gson();
		String resultString = gson.toJson(branchselectList);
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	
	/**
	 * 显示所有项目CXEE
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value ="/showAll")
	public void displayAllDate(HttpSession session,HttpServletResponse response,ProjectReqParam projectReqParam)throws IOException {
		if("1".equals(projectReqParam.getQueryRoleFlag())){
			//设置数据库查询时从哪条数据开始
			projectReqParam.setPageSeq((projectReqParam.getPage()-1)*projectReqParam.getRp());
			//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
	        if(projectReqParam.getCategory()!=null &&projectReqParam.getCategory().trim().equals("")){
				projectReqParam.setCategory(null);
			}
	        if(projectReqParam.getProjectName()!=null &&projectReqParam.getProjectName().trim().equals("")){
				projectReqParam.setProjectName(null);
			}
	        if(projectReqParam.getProjectClientName()!=null &&projectReqParam.getProjectClientName().trim().equals("")){
				projectReqParam.setProjectClientName(null);
			}
	        if(projectReqParam.getFunction()!=null &&projectReqParam.getFunction().trim().equals("")){
				projectReqParam.setFunction(null);
			}
	        if(projectReqParam.getDepartment()!=null &&projectReqParam.getDepartment().trim().equals("")){
				projectReqParam.setDepartment(null);
			}
	        if(projectReqParam.getBranch()!=null &&projectReqParam.getBranch().trim().equals("")){
				projectReqParam.setBranch(null);
			}
	        if(projectReqParam.getProjectClientNo()!=null &&projectReqParam.getProjectClientNo().trim().equals("")){
				projectReqParam.setProjectClientNo(null);
			}
	        if(projectReqParam.getTransferNo()!=null &&projectReqParam.getTransferNo().trim().equals("")){
				projectReqParam.setTransferNo(null);
			}
	        if(projectReqParam.getQuery()!=null &&!projectReqParam.getQuery().trim().equals("")){
				if(projectReqParam.getQtype().trim().equals("category")){
					projectReqParam.setCategory(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("projectName")){
					projectReqParam.setProjectName(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("projectClientName")){
					projectReqParam.setProjectClientName(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("function")){
					projectReqParam.setFunction(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("department")) {
					projectReqParam.setDepartment(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("branch")) {
					projectReqParam.setBranch(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("projectClientNo")) {
					projectReqParam.setProjectClientNo(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("transferNo")) {
					projectReqParam.setTransferNo(projectReqParam.getQuery().trim());
				}
			}
			List<Project> projectList = null;
			int totalPageCount = 0;
			if (projectReqParam.getBranch() != null && !projectReqParam.getBranch().equals("")){
				totalPageCount = projectService.totalPageCountForBranch(projectReqParam);
				projectList = projectService.showAllForBranch(projectReqParam);
			}else if (projectReqParam.getDepartment()!=null && !projectReqParam.getDepartment().equals("")) {//判断是否按部门查询项目，调用对应的函数
				totalPageCount = projectService.totalPageCountForDepartment(projectReqParam);
				projectList = projectService.showAllForDepartment(projectReqParam);			
			} 
			else {
				totalPageCount = projectService.totalPageCount(projectReqParam);
				projectList = projectService.showAll(projectReqParam);
			}
			List<ProjectResponseRows> projectRespongseList = new ArrayList<ProjectResponseRows>();
			for (int i=0;i<projectList.size();i++) {
				ProjectResponseRows projectResponseRows = new ProjectResponseRows(i, projectList.get(i));
				List<Project> projectDepartmentList =projectService.getProjectDepartmentByProjectID(projectResponseRows.getCell().getProjectID());
				String tempString = "";
				for (int j = 0;j < projectDepartmentList.size();j++)
				{
					tempString += projectDepartmentList.get(j).getProjectDepartment();
					if (j < projectDepartmentList.size() - 1) {
						tempString += ";";
					}
				}
				
				projectResponseRows.getCell().setProjectDepartment(tempString);
				tempString = "";
				List<Project> projectBranchList =projectService.getProjectBranchByProjectID(projectResponseRows.getCell().getProjectID());
				for (int j = 0;j < projectBranchList.size();j++)
				{
					tempString += projectBranchList.get(j).getProjectBranch();
					if (j < projectBranchList.size() - 1) {
						tempString += ";";
					}
				}
				projectResponseRows.getCell().setProjectBranch(tempString);
				
				if (projectResponseRows.getCell().getProjectState() != null && !projectResponseRows.getCell().getProjectState().equals("")) {
					switch (Integer.parseInt(projectResponseRows.getCell().getProjectState())) {
					case 1:
						projectResponseRows.getCell().setProjectState("開発中");
						break;
					case 2:
						projectResponseRows.getCell().setProjectState("開発完了");
						break;
					case 3:
						projectResponseRows.getCell().setProjectState("開発中止");
						break;
					/*case 4:
						projectResponseRows.getCell().setProjectState("执行中");
						break;*/
					case 4:
						projectResponseRows.getCell().setProjectState("保留");
						break;
					default:
						break;
					}
				}
				projectRespongseList.add(projectResponseRows);
			}
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setPage(projectReqParam.getPage());        //设置
			projectResponse.setTotal(totalPageCount);
			projectResponse.setRows(projectRespongseList);
			Gson gson = new Gson();
			String resultString = gson.toJson(projectResponse);
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(resultString);
			out.close();
		}else{
			PrintWriter out = response.getWriter();
			out.write("");
			out.close();
		}
		
	}
	
	/**
	 * 显示所有项目CT
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value ="/showAllCT")
	public void displayAllDateCT(HttpSession session,HttpServletResponse response,ProjectReqParam projectReqParam)throws IOException {
		if("1".equals(projectReqParam.getQueryRoleFlag())){
			//设置数据库查询时从哪条数据开始
			projectReqParam.setPageSeq((projectReqParam.getPage()-1)*projectReqParam.getRp());
			//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
	        if(projectReqParam.getCategory()!=null &&projectReqParam.getCategory().trim().equals("")){
				projectReqParam.setCategory(null);
			}
	        if(projectReqParam.getPJName()!=null &&projectReqParam.getPJName().trim().equals("")){
				projectReqParam.setPJName(null);
			}
	        if(projectReqParam.getPJNo()!=null &&projectReqParam.getPJNo().trim().equals("")){
				projectReqParam.setPJNo(null);
			}
	        if(projectReqParam.getTempPJNo()!=null &&projectReqParam.getTempPJNo().trim().equals("")){
				projectReqParam.setTempPJNo(null);
			}
	        if(projectReqParam.getTransferNo()!=null &&projectReqParam.getTransferNo().trim().equals("")){
				projectReqParam.setTransferNo(null);
			}
	        if(projectReqParam.getCarMaker()!=null &&projectReqParam.getCarMaker().trim().equals("")){
				projectReqParam.setCarMaker(null);
			}
	        if(projectReqParam.getProjectState()!=null &&projectReqParam.getProjectState().trim().equals("")){
				projectReqParam.setProjectState(null);
			}
	        if(projectReqParam.getFunction()!=null &&projectReqParam.getFunction().trim().equals("")){
				projectReqParam.setFunction(null);
			}
	        if(projectReqParam.getQuery()!=null &&!projectReqParam.getQuery().trim().equals("")){
				if(projectReqParam.getQtype().trim().equals("PJName")){
					projectReqParam.setPJName(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("PJNo")){
					projectReqParam.setPJNo(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("tempPJNo")){
					projectReqParam.setTempPJNo(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("category")){
					projectReqParam.setCategory(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("transferNo")) {
					projectReqParam.setTransferNo(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("carMaker")) {
					projectReqParam.setCarMaker(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("function")) {
					projectReqParam.setFunction(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("projectState")) {
					projectReqParam.setProjectState(projectReqParam.getQuery().trim());
				}
			}
	        if (!"1".equals(projectReqParam.getDepartmentFlag())) {
				projectReqParam.setDepartmentFlag("0");
			}
	        if(projectReqParam.getDepartmentID() != null && projectReqParam.getDepartmentID().trim().equals("")){
				projectReqParam.setDepartmentID(null);
			}
			List<Project> projectList = null;
			int totalPageCount = 0;
			totalPageCount = projectService.totalPageCountCT(projectReqParam);
			projectList = projectService.showAllCT(projectReqParam);
			List<ProjectResponseRows> projectRespongseList = new ArrayList<ProjectResponseRows>();
			for (int i=0;i<projectList.size();i++) {
				ProjectResponseRows projectResponseRows = new ProjectResponseRows(i, projectList.get(i));
							
				if (projectResponseRows.getCell().getProjectState() != null && !projectResponseRows.getCell().getProjectState().equals("")) {
					switch (Integer.parseInt(projectResponseRows.getCell().getProjectState())) {
					case 1:
						projectResponseRows.getCell().setProjectState("開発中");
						break;
					case 2:
						projectResponseRows.getCell().setProjectState("開発完了");
						break;
					case 3:
						projectResponseRows.getCell().setProjectState("開発中止");
						break;
					/*case 4:
						projectResponseRows.getCell().setProjectState("执行中");
						break;*/
					case 4:
						projectResponseRows.getCell().setProjectState("保留");
						break;
					default:
						break;
					}
				}
				projectRespongseList.add(projectResponseRows);
			}
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setPage(projectReqParam.getPage());        //设置
			projectResponse.setTotal(totalPageCount);
			projectResponse.setRows(projectRespongseList);
			Gson gson = new Gson();
			String resultString = gson.toJson(projectResponse);
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(resultString);
			out.close();
		}else{
			PrintWriter out = response.getWriter();
			out.write("");
			out.close();
		}
		
		
	}
	
	/**
	 * 显示所有项目Commmon
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value ="/showAllCommon")
	public void displayAllDateCommon(HttpSession session,HttpServletResponse response,ProjectReqParam projectReqParam)throws IOException {
		if("1".equals(projectReqParam.getQueryRoleFlag())){
			//设置数据库查询时从哪条数据开始
			projectReqParam.setPageSeq((projectReqParam.getPage()-1)*projectReqParam.getRp());
			//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
			
	        if(projectReqParam.getCategory()!=null &&projectReqParam.getCategory().trim().equals("")){
				projectReqParam.setCategory(null);
			}
	        if(projectReqParam.getPJName()!=null &&projectReqParam.getPJName().trim().equals("")){
				projectReqParam.setPJName(null);
			}
	        if(projectReqParam.getPJNo()!=null &&projectReqParam.getPJNo().trim().equals("")){
				projectReqParam.setPJNo(null);
			}
	        if(projectReqParam.getTempPJNo()!=null &&projectReqParam.getTempPJNo().trim().equals("")){
				projectReqParam.setTempPJNo(null);
			}
	        if(projectReqParam.getTransferNo()!=null &&projectReqParam.getTransferNo().trim().equals("")){
				projectReqParam.setTransferNo(null);
			}
	        if(projectReqParam.getCarMaker()!=null &&projectReqParam.getCarMaker().trim().equals("")){
				projectReqParam.setCarMaker(null);
			}
	        if(projectReqParam.getProjectState()!=null &&projectReqParam.getProjectState().trim().equals("")){
				projectReqParam.setProjectState(null);
			}
	        if(projectReqParam.getFunction()!=null &&projectReqParam.getFunction().trim().equals("")){
				projectReqParam.setFunction(null);
			}
	        if(projectReqParam.getProjectClientNo()!=null &&projectReqParam.getProjectClientNo().trim().equals("")){
				projectReqParam.setProjectClientNo(null);
			}
	        if(projectReqParam.getProjectName()!=null &&projectReqParam.getProjectName().trim().equals("")){
				projectReqParam.setProjectName(null);
			}
	        if(projectReqParam.getProjectClientName()!=null &&projectReqParam.getProjectClientName().trim().equals("")){
				projectReqParam.setProjectClientName(null);
			}
	        if(projectReqParam.getQuery()!=null &&!projectReqParam.getQuery().trim().equals("")){
				if(projectReqParam.getQtype().trim().equals("PJName")){
					projectReqParam.setPJName(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("PJNo")){
					projectReqParam.setPJNo(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("tempPJNo")){
					projectReqParam.setTempPJNo(projectReqParam.getQuery().trim());
				}else if(projectReqParam.getQtype().trim().equals("category")){
					projectReqParam.setCategory(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("transferNo")) {
					projectReqParam.setTransferNo(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("carMaker")) {
					projectReqParam.setCarMaker(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("function")) {
					projectReqParam.setFunction(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("projectState")) {
					projectReqParam.setProjectState(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("projectClientNo")) {
					projectReqParam.setProjectClientNo(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("projectName")) {
					projectReqParam.setProjectName(projectReqParam.getQuery().trim());
				}else if (projectReqParam.getQtype().trim().equals("projectClientName")) {
					projectReqParam.setProjectClientName(projectReqParam.getQuery().trim());
				}
			}
			List<Project> projectList = null;
			//List<Project> projectListCT = null;
			int totalPageCount = 0;
			//int totalPageCounttemp = 0;
			//20160315zhuxiaoming
			
			if("1".equals(projectReqParam.getSpecialRole1Flag()) && "1".equals(projectReqParam.getSpecialRole2Flag())){
				//CT+CXEE
				totalPageCount = projectService.totalPageCountCTCXEE(projectReqParam);
				projectList = projectService.showAllCTCXEE(projectReqParam);
			}else if("1".equals(projectReqParam.getSpecialRole1Flag()) && "0".equals(projectReqParam.getSpecialRole2Flag())){
				//CT
				totalPageCount = projectService.totalPageCountCT(projectReqParam);
				projectList = projectService.showAllCT(projectReqParam);
			}else if("0".equals(projectReqParam.getSpecialRole1Flag()) && "1".equals(projectReqParam.getSpecialRole2Flag())){
				//CXEE
				totalPageCount = projectService.totalPageCount(projectReqParam);
				projectList = projectService.showAll(projectReqParam);
			}
			
			//totalPageCount = projectService.totalPageCountCommon(projectReqParam);
			//totalPageCounttemp = projectService.totalPageCountCommonCT(projectReqParam);
			//totalPageCount = totalPageCount+totalPageCounttemp;
			//projectList = projectService.showAllCommon(projectReqParam);
			//projectListCT = projectService.showAllCommonCT(projectReqParam);
			/*for(int i =0;i<projectListCT.size();i++){
				projectList.add(projectListCT.get(i));
			}*/
			List<ProjectResponseRows> projectRespongseList = new ArrayList<ProjectResponseRows>();
			for (int i=0;i<projectList.size();i++) {
				ProjectResponseRows projectResponseRows = new ProjectResponseRows(i, projectList.get(i));
							
				if (projectResponseRows.getCell().getProjectState() != null && !projectResponseRows.getCell().getProjectState().equals("")) {
					switch (Integer.parseInt(projectResponseRows.getCell().getProjectState())) {
					case 1:
						projectResponseRows.getCell().setProjectState("開発中");
						break;
					case 2:
						projectResponseRows.getCell().setProjectState("開発完了");
						break;
					case 3:
						projectResponseRows.getCell().setProjectState("開発中止");
						break;
					/*case 4:
						projectResponseRows.getCell().setProjectState("执行中");
						break;*/
					case 4:
						projectResponseRows.getCell().setProjectState("保留");
						break;
					default:
						break;
					}
				}
				projectRespongseList.add(projectResponseRows);
			}
			ProjectResponse projectResponse = new ProjectResponse();
			projectResponse.setPage(projectReqParam.getPage());        //设置
			projectResponse.setTotal(totalPageCount);
			projectResponse.setRows(projectRespongseList);
			Gson gson = new Gson();
			String resultString = gson.toJson(projectResponse);
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(resultString);
			out.close();
		}else{
			PrintWriter out = response.getWriter();
			out.write("");
			out.close();
		}
		
	}
	
	
	/**
	 * 新增项目页面CXEE
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ModelAndView toAdd() {
		List<String> categoryList = projectService.listCategorySelect();
		List<String> clientList = projectService.listProjectClientSelect();
		List<String> functionList = projectService.listFunctionListSelect();
	    List<Map<String, Object>> tempList = staffSelectedService.getDepartmentListCXEE();
	    Map<String, Object> departmentList = new HashMap<String, Object>();
	    List<Map<String, Object>> carmake =null;
	    carmake = projectSelectedService.getCarMakeList();
	    CheckValue tempDepartment;
	    for (Map<String, Object> map : tempList) {
	    	tempDepartment = new CheckValue();
	    	tempDepartment.setName(map.get("department").toString());
	    	tempDepartment.setChecked("false");
	    	departmentList.put(map.get("departmentID").toString(), tempDepartment);
	    }
	    //项目所属课别
	    //List<Map<String,Object>> tempBranchList = staffSelectedService.getBranchListOfDevelopDepartment();
	    List<Map<String,Object>> tempBranchList = projectService.getAllBranch();
	    Map<String, Object> branchList = new HashMap<String,Object>();
	    CheckValue tempBranch;
	    for (Map<String, Object> map : tempBranchList) {
	    	tempBranch = new CheckValue();
	    	tempBranch.setName(map.get("branch").toString());
	    	tempBranch.setChecked("false");
	    	branchList.put(map.get("branchID").toString(), tempBranch);
	    }
	    
		ModelAndView mv = new ModelAndView();
		mv.addObject("categoryList", categoryList);
	    mv.addObject("departmentList", departmentList);
		mv.addObject("clientList",clientList);
		mv.addObject("functionList", functionList);
		mv.addObject("branchList",branchList);
		mv.addObject("projectCarmake",carmake);
		mv.setViewName("admin/project/projectInfo");
		return mv;
	}
	
	/**
	 * 新增项目页面CT
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/addCT")
	public ModelAndView toAddCT() {
		List<String> categoryList = projectService.listCategorySelect();
		List<String> clientList = projectService.listProjectClientSelect();

		List<String> functionList = projectService.listFunctionListSelectCT();
	    List<Map<String, Object>> tempList = staffSelectedService.getDepartmentListCT();
	    List<Map<String, Object>> carmakeCT =null;
	    carmakeCT = projectSelectedService.getCarMakeListCT();

	    Map<String, Object> departmentList = new HashMap<String, Object>();
	    CheckValue tempDepartment;
	    for (Map<String, Object> map : tempList) {
	    	tempDepartment = new CheckValue();
	    	tempDepartment.setName(map.get("department").toString());
	    	tempDepartment.setChecked("false");
	    	departmentList.put(map.get("departmentID").toString(), tempDepartment);
	    }
	    //项目所属课别
	    /*
	    List<Map<String,Object>> tempBranchList = staffSelectedService.getBranchListOfDevelopDepartment();
	    Map<String, Object> branchList = new HashMap<String,Object>();
	    CheckValue tempBranch;
	    for (Map<String, Object> map : tempBranchList) {
	    	tempBranch = new CheckValue();
	    	tempBranch.setName(map.get("branch").toString());
	    	tempBranch.setChecked("false");
	    	branchList.put(map.get("branchID").toString(), tempBranch);
	    }
	    */
		ModelAndView mv = new ModelAndView();
		mv.addObject("categoryList", categoryList);
	    mv.addObject("departmentList", departmentList);
		//mv.addObject("clientList",clientList);
		mv.addObject("functionList", functionList);
		//mv.addObject("branchList",branchList);
		mv.addObject("projectCarmakeCT",carmakeCT);
		mv.setViewName("admin/project/projectInfoCT");
		return mv;
	}

	/**
	 * 保存项目信息CXEE
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveProject(Project project,String[] departmentList,String[] branchList) {
		/*for (String string : departmentList) {
			System.out.println(string);
		}*/
		ModelAndView mv = new ModelAndView();
		if ("".equals(project.getDirectProjectID())) {//直属项目待修改
			project.setDirectProjectID(null);
		}
		if ("".equals(project.getStartupDate())) {//启动时间
			project.setStartupDate(null);
		}
		if ("".equals(project.getFinishDate())) {//结束时间
			project.setFinishDate(null);
		}
		if(project.getProjectName().equals(project.getProjectNameOld())){
			projectService.updateProject(project);
			projectService.calldeletePDCXEE(project.getProjectName(),project.getCategory());
			if (departmentList != null && departmentList.length > 0){
				for (int i = 0; i < departmentList.length; i++) {
					projectService.callinsertPDCXEE(project.getProjectName(),project.getCategory(),departmentList[i]);
				}
			}
			//向项目课别表中登陆数据
			projectService.calldeletePBCXEE(project.getProjectName(),project.getCategory());
			if (branchList != null && branchList.length > 0){
				for (int i = 0; i < branchList.length; i++){
					projectService.callinsertProjectBranchCXEE(project.getProjectName(),project.getCategory(),branchList[i]);
				}
			}
		}else{
			if(projectService.getProjectIDByProjectName(project.getProjectName()) != 0){
				mv.addObject("msg", "failed");
			}else{
				if (project.getProjectID() == null || project.getProjectID().intValue() == 0 ) {
					if (projectService.insertNewProject(project) == false) {
						mv.addObject("msg", "failed");
					} else {
						mv.addObject("msg", "success");	
					}
				} else {
					projectService.updateProject(project);
				}
				projectService.calldeletePDCXEE(project.getProjectName(),project.getCategory());
				if (departmentList != null && departmentList.length > 0){
					for (int i = 0; i < departmentList.length; i++) {
						projectService.callinsertPDCXEE(project.getProjectName(),project.getCategory(),departmentList[i]);
					}
				}
				//向项目课别表中登陆数据
				projectService.calldeletePBCXEE(project.getProjectName(),project.getCategory());
				if (branchList != null && branchList.length > 0){
					for (int i = 0; i < branchList.length; i++){
						projectService.callinsertProjectBranchCXEE(project.getProjectName(),project.getCategory(),branchList[i]);
					}
				}
			}
		}
		mv.setViewName("admin/project/save_result");
		return mv;
	}	
	
	/**
	 * 保存项目信息CT
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveCT", method = RequestMethod.POST)
	public ModelAndView saveProjectCT(Project project,String[] departmentList,String[] branchList) {
		/*for (String string : departmentList) {
			System.out.println(string);
		}*/
		ModelAndView mv = new ModelAndView();
		if ("".equals(project.getDirectProjectID())) {//直属项目待修改
			project.setDirectProjectID(null);
		}
		if ("".equals(project.getStartupDate())) {//启动时间
			project.setStartupDate(null);
		}
		if ("".equals(project.getFinishDate())) {//结束时间
			project.setFinishDate(null);
		}
		if (project.getProjectID() == null || project.getProjectID().intValue() == 0 ) {
			if (projectService.insertNewProjectCT(project) == false) {
				mv.addObject("msg", "failed");
			} else {
				mv.addObject("msg", "success");	
			}
		} else {
			projectService.updateProjectCT(project);
		}
		int projectID= project.getProjectID();
		projectService.deletePDByProjectIDCT(project.getProjectID());
		
		if (departmentList != null && departmentList.length > 0){
			for (int i = 0; i < departmentList.length; i++) {
				projectService.insertPDByProjectIDCT(Integer.valueOf(projectID),Integer.valueOf(departmentList[i]));
			}
		}
		mv.setViewName("admin/project/save_result");
		return mv;
	}	
	
	
	/**
	 * 编辑项目
	 * 
	 * @param 项目id
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit(@RequestParam int id) {
		ModelAndView mv = new ModelAndView();
		Project project = projectService.getProjectByIdCXEE(id);
		List<Project> projectDepartmentList = projectService.getProjectDepartmentByProjectIDCXEE(id);
		List<String> categoryList = projectService.listCategorySelect();
		List<String> clientList = projectService.listProjectClientSelect();
		List<String> functionList = projectService.listFunctionListSelect();
	    List<Map<String, Object>> tempList = staffSelectedService.getDepartmentList();

	    boolean allSelect = false;
	    if(projectDepartmentList.size() == staffSelectedService.countDepartment() && projectDepartmentList.size() == tempList.size()){
	    	allSelect = true;
	    }		

	    List<Map<String, Object>> carmake =null;
	    carmake = projectSelectedService.getCarMakeList();

		Map<String, Object> departmentList = new HashMap<String, Object>();
		CheckValue tempDepartment;
		for (Map<String, Object> map : tempList) {
			tempDepartment = new CheckValue();
			tempDepartment.setName(map.get("department").toString());
			tempDepartment.setChecked("false");			
			departmentList.put(map.get("departmentID").toString(), tempDepartment);
		}
		for (Project projectTemp : projectDepartmentList) {
			tempDepartment = new CheckValue();
			tempDepartment.setName(projectTemp.getProjectDepartment());
			tempDepartment.setChecked("true");
			departmentList.put(projectTemp.getProjectDepartmentID(), tempDepartment);
		}
		//项目所属课别
		//List<Map<String,Object>> tempBranchList = staffSelectedService.getBranchListOfDevelopDepartment();
		
		//20160315新增
		List<Map<String,Object>> tempBranchList = projectService.getAllBranch();
		List<Project> projectBranchList = projectService.getProjectBranchByProjectID(id);
	    Map<String, Object> branchList = new HashMap<String,Object>();
	    CheckValue tempBranch;
	    for (Map<String, Object> map : tempBranchList) {
	    	tempBranch = new CheckValue();
	    	tempBranch.setName(map.get("branch").toString());
	    	tempBranch.setChecked("false");
	    	branchList.put(map.get("branchID").toString(), tempBranch);
	    }
	    for (Project projectTemp : projectBranchList ){
	    	tempBranch = new CheckValue();
	    	tempBranch.setName(projectTemp.getProjectBranch());
	    	tempBranch.setChecked("true");
	    	branchList.put(projectTemp.getProjectBranchID(), tempBranch);
	    }
	    
	    
		String tempString = "";
		for (int j = 0;j < projectDepartmentList.size();j++)
		{
			tempString += projectDepartmentList.get(j).getProjectDepartment();
			
			if (j < projectDepartmentList.size() - 1) {
				tempString += ";";
			}
		}
		project.setProjectDepartment(tempString);
		tempString = "";
		for (int j = 0;j < projectBranchList.size();j++)
		{
			tempString += projectBranchList.get(j).getProjectBranch();
			
			if (j < projectBranchList.size() - 1) {
				tempString += ";";
			}
		}
		project.setProjectBranch(tempString);
		mv.addObject("allSelect",allSelect);
		mv.addObject("categoryList", categoryList);
		mv.addObject("clientList",clientList);
		mv.addObject("functionList", functionList);
		mv.addObject("departmentList", departmentList);
		mv.addObject("branchList",branchList);
		mv.addObject("project", project);
		mv.addObject("projectCarmake",carmake);
		mv.setViewName("admin/project/projectInfo");
		return mv;
	}
	
	/**
	 * 编辑项目
	 * 
	 * @param 项目id
	 * @return
	 */
	@RequestMapping(value = "/editCT")
	public ModelAndView toEditCT(@RequestParam int id) {
		ModelAndView mv = new ModelAndView();
		Project project = projectService.getProjectByIdCT(id);
		List<Project> projectDepartmentList = projectService.getProjectDepartmentByProjectIDCT(id);
		List<String> categoryList = projectService.listCategorySelect();
		List<String> clientList = projectService.listProjectClientSelect();
		List<String> functionList = projectService.listFunctionListSelectCT();
	    List<Map<String, Object>> tempList = staffSelectedService.getDepartmentListCT();
	    
	    boolean allSelect = false;
	    if(projectDepartmentList.size() == staffSelectedService.countDepartment() && projectDepartmentList.size() == tempList.size()){
	    	allSelect = true;
	    }	
		Map<String, Object> departmentList = new HashMap<String, Object>();
	    List<Map<String, Object>> carmakeCT =null;
	    carmakeCT = projectSelectedService.getCarMakeListCT();
		CheckValue tempDepartment;
		for (Map<String, Object> map : tempList) {
			tempDepartment = new CheckValue();
			tempDepartment.setName(map.get("department").toString());
			tempDepartment.setChecked("false");			
			departmentList.put(map.get("departmentID").toString(), tempDepartment);
		}
		for (Project projectTemp : projectDepartmentList) {
			tempDepartment = new CheckValue();
			tempDepartment.setName(projectTemp.getProjectDepartment());
			tempDepartment.setChecked("true");
			departmentList.put(projectTemp.getProjectDepartmentID(), tempDepartment);
		}
		//项目所属课别
		/*
		List<Map<String,Object>> tempBranchList = staffSelectedService.getBranchListOfDevelopDepartment();
		List<Project> projectBranchList = projectService.getProjectBranchByProjectIDCT(id);
	    Map<String, Object> branchList = new HashMap<String,Object>();
	    CheckValue tempBranch;
	    for (Map<String, Object> map : tempBranchList) {
	    	tempBranch = new CheckValue();
	    	tempBranch.setName(map.get("branch").toString());
	    	tempBranch.setChecked("false");
	    	branchList.put(map.get("branchID").toString(), tempBranch);
	    }
	    for (Project projectTemp : projectBranchList ){
	    	tempBranch = new CheckValue();
	    	tempBranch.setName(projectTemp.getProjectBranch());
	    	tempBranch.setChecked("true");
	    	branchList.put(projectTemp.getProjectBranchID(), tempBranch);
	    }
	    */
	    
		String tempString = "";
		for (int j = 0;j < projectDepartmentList.size();j++)
		{
			tempString += projectDepartmentList.get(j).getProjectDepartment();
			
			if (j < projectDepartmentList.size() - 1) {
				tempString += ";";
			}
		}
		project.setProjectDepartment(tempString);
		/*
		tempString = "";
		for (int j = 0;j < projectBranchList.size();j++)
		{
			tempString += projectBranchList.get(j).getProjectBranch();
			
			if (j < projectBranchList.size() - 1) {
				tempString += ";";
			}
		}
		project.setProjectBranch(tempString);
		*/
		mv.addObject("allSelect",allSelect);
		mv.addObject("categoryList", categoryList);
		mv.addObject("clientList",clientList);
		mv.addObject("functionList", functionList);
		mv.addObject("departmentList", departmentList);
	//	mv.addObject("branchList",branchList);
		mv.addObject("project", project);
		mv.addObject("projectCarmakeCT",carmakeCT);
		mv.setViewName("admin/project/projectInfoCT");
		return mv;
	}
	
	/**
	 * 删除项目
	 * 
	 * @param ids
	 * @param out
	 */
	@RequestMapping(value = "/delete")
	public void deleteProject(@RequestParam String ids, PrintWriter out) {
		String[] allId = ids.split(",");
		projectService.delectProject(allId);
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	
	/**
	 * 删除项目CT
	 * 
	 * @param ids
	 * @param out
	 */
	@RequestMapping(value = "/deleteCT")
	public void deleteProjectCT(@RequestParam String ids, PrintWriter out) {
		String[] allId = ids.split(",");
		projectService.delectProjectCT(allId);
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	
	/**
	 * 导出项目详细信息CXEE
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/downloadProject")
	public ModelAndView outPutProject(HttpSession httpSession,HttpServletResponse response) throws IOException {
		//DataToExcle newClass = new DataToExcle();
		List<Project> projectList= projectService.downLoadShowAllProject();
		
		for (int i = 0; i < projectList.size(); i++) {
			ProjectResponseRows projectResponseRows = new ProjectResponseRows(i, projectList.get(i));
			List<Project> projectDepartmentList =projectService.getProjectDepartmentByProjectID(projectResponseRows.getCell().getProjectID());
			String tempString = "";
			for (int j = 0;j < projectDepartmentList.size();j++)
			{
				tempString += projectDepartmentList.get(j).getProjectDepartment();
				
				if (j < projectDepartmentList.size() - 1) {
					tempString += ",";
				}
			}
			
			projectList.get(i).setProjectDepartment(tempString);
			
			List<Project> projectBranchList =projectService.getProjectBranchByProjectID(projectResponseRows.getCell().getProjectID());
			tempString = "";
			for (int j = 0;j < projectBranchList.size();j++)
			{
				tempString += projectBranchList.get(j).getProjectBranch();
				
				if (j < projectBranchList.size() - 1) {
					tempString += ",";
				}
			}
			
			projectList.get(i).setProjectBranch(tempString);
			switch (Integer.parseInt(projectList.get(i).getProjectState())) {
			case 1://开始
				projectList.get(i).setProjectState("開発中");
				break;
			case 2://完成
				projectList.get(i).setProjectState("開発完了");
				break;
			case 3://暂停
				projectList.get(i).setProjectState("開発中止");
				break;
			case 4://终止
				projectList.get(i).setProjectState("保留");
				break;
			default:
				break;
			}
		}
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath(); 
		//String webadr=contextpath+newClass.toExcel(projectList,realPathString);
		String webadr=contextpath+projectService.downloadProject(projectList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	
	/**
	 * 导出项目详细信息CT
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/downloadProjectCT")
	public ModelAndView outPutProjectCT(HttpSession httpSession,HttpServletResponse response) throws IOException {
		//DataToExcle newClass = new DataToExcle();
		List<Project> projectList= projectService.downLoadShowAllProjectCT();
		for (int i = 0; i < projectList.size(); i++) {
			ProjectResponseRows projectResponseRows = new ProjectResponseRows(i, projectList.get(i));
			List<Project> projectDepartmentList =projectService.getProjectDepartmentByProjectIDCT(projectResponseRows.getCell().getProjectID());
			String tempString = "";
			for (int j = 0;j < projectDepartmentList.size();j++)
			{
				tempString += projectDepartmentList.get(j).getProjectDepartment();
				
				if (j < projectDepartmentList.size() - 1) {
					tempString += ",";
				}
			}
			
			projectList.get(i).setProjectDepartment(tempString);
			
			/*
			List<Project> projectBranchList =projectService.getProjectBranchByProjectID(projectResponseRows.getCell().getProjectID());
			tempString = "";
			for (int j = 0;j < projectBranchList.size();j++)
			{
				tempString += projectBranchList.get(j).getProjectBranch();
				
				if (j < projectBranchList.size() - 1) {
					tempString += ";";
				}
			}
			
			projectList.get(i).setProjectBranch(tempString);
			*/
			switch (Integer.parseInt(projectList.get(i).getProjectState())) {
			case 1://开始
				projectList.get(i).setProjectState("開発中");
				break;
			case 2://完成
				projectList.get(i).setProjectState("開発完了");
				break;
			case 3://暂停
				projectList.get(i).setProjectState("開発中止");
				break;
			case 4://终止
				projectList.get(i).setProjectState("保留");
				break;
			default:
				break;
			}
		}
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath(); 
		String webadr=contextpath+projectService.downloadProjectCT(projectList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	
	/**
	 * 导出项目详细信息Common
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/downloadProjectCommon")
	public ModelAndView outPutProjectCommon(HttpSession httpSession,HttpServletResponse response) throws IOException {
		//DataToExcle newClass = new DataToExcle();
		List<Project> projectList= projectService.downLoadShowAllProject();
		
		for (int i = 0; i < projectList.size(); i++) {
			ProjectResponseRows projectResponseRows = new ProjectResponseRows(i, projectList.get(i));
			List<Project> projectDepartmentList =projectService.getProjectDepartmentByProjectID(projectResponseRows.getCell().getProjectID());
			String tempString = "";
			for (int j = 0;j < projectDepartmentList.size();j++)
			{
				tempString += projectDepartmentList.get(j).getProjectDepartment();
				
				if (j < projectDepartmentList.size() - 1) {
					tempString += ",";
				}
			}
			
			projectList.get(i).setProjectDepartment(tempString);
			
			List<Project> projectBranchList =projectService.getProjectBranchByProjectID(projectResponseRows.getCell().getProjectID());
			tempString = "";
			for (int j = 0;j < projectBranchList.size();j++)
			{
				tempString += projectBranchList.get(j).getProjectBranch();
				
				if (j < projectBranchList.size() - 1) {
					tempString += ",";
				}
			}
			
			projectList.get(i).setProjectBranch(tempString);
			switch (Integer.parseInt(projectList.get(i).getProjectState())) {
			case 1://开始
				projectList.get(i).setProjectState("開発中");
				break;
			case 2://完成
				projectList.get(i).setProjectState("開発完了");
				break;
			case 3://暂停
				projectList.get(i).setProjectState("開発中止");
				break;
			case 4://终止
				projectList.get(i).setProjectState("保留");
				break;
			default:
				break;
			}
		}
		
		List<Project> projectListCT= projectService.downLoadShowAllProjectCT();
		for (int i = 0; i < projectListCT.size(); i++) {
			ProjectResponseRows projectResponseRows = new ProjectResponseRows(i, projectListCT.get(i));
			List<Project> projectDepartmentList =projectService.getProjectDepartmentByProjectIDCT(projectResponseRows.getCell().getProjectID());
			String tempString = "";
			for (int j = 0;j < projectDepartmentList.size();j++)
			{
				tempString += projectDepartmentList.get(j).getProjectDepartment();
				
				if (j < projectDepartmentList.size() - 1) {
					tempString += ",";
				}
			}
			
			projectListCT.get(i).setProjectDepartment(tempString);
			
			switch (Integer.parseInt(projectListCT.get(i).getProjectState())) {
			case 1://开始
				projectListCT.get(i).setProjectState("開発中");
				break;
			case 2://完成
				projectListCT.get(i).setProjectState("開発完了");
				break;
			case 3://暂停
				projectListCT.get(i).setProjectState("開発中止");
				break;
			case 4://终止
				projectListCT.get(i).setProjectState("保留");
				break;
			default:
				break;
			}
		}
		
		for(int i =0;i<projectListCT.size();i++){
			projectList.add(projectListCT.get(i));
		}
		
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath(); 
		String webadr=contextpath+projectService.downloadProjectCommon(projectList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	
	/**
	 * 上传页面CXEE
	 */
	@RequestMapping(value = "/upload")
	public ModelAndView toUpload(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		//Project projectInfo = (Project) httpSession.getAttribute(Const.SESSION_USER);
		Project projectInfo = new Project();
		mv.addObject("projectInfo", projectInfo);
		mv.setViewName("admin/project/projectUpload");
		return mv;
	}
	
	/**
	 * 上传页面CT
	 */
	@RequestMapping(value = "/uploadCT")
	public ModelAndView toUploadCT(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		//Project projectInfo = (Project) httpSession.getAttribute(Const.SESSION_USER);
		Project projectInfo = new Project();
		mv.addObject("projectInfo", projectInfo);
		mv.setViewName("admin/project/projectUploadCT");
		return mv;
	}
	
	/**
	 * 项目信息文件上传CXEE
	 */
	@RequestMapping(value = "/fileUpload",method=RequestMethod.POST)
	public void fileUpload(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		//Project projectInfo = (Project) request.getSession().getAttribute(Const.SESSION_USER);
		//Staff staffInfo = (Staff) request.getSession().getAttribute(Const.SESSION_USER);
		HttpSession session = request.getSession();
		try {
			// 转型为MultipartHttpRequest：  
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            // 获得文件：  
            MultipartFile file = multipartRequest.getFile("file");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
            // 获得输入流：  
            InputStream filestream = file.getInputStream();
            
            HSSFWorkbook wb = new HSSFWorkbook(filestream);
            
            HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(0);

            if (sheet == null ){
            	filestream.close();
				PrintWriter out = response.getWriter();
				out.write("NOSHEET");
				out.close();
            } else {
	            HSSFRow row = null;
	            Project uploadProject = null;
	            int startRow = 2;
	            int startCell = 1;
	            List<Map<String, Object>> departmentList = projectSelectedService.getProjectDepartmentListCXEE();
	            List<Map<String, Object>> branchList = projectSelectedService.getProjectBranchListCXEE();
	            List<Map<String, Object>> categoryList = projectSelectedService.getProjectCategoryList();
	            List<Map<String, Object>> carMakerList = projectSelectedService.getCarMakeList();
	            List<Map<String, Object>> functionList = projectSelectedService.getFuctionList();
	            List<ProjectUploadResponse> errorProjectInfoList = new ArrayList<ProjectUploadResponse>();
	            ProjectUploadResponse errorProjectInfo;
	            String resultString = "";
	            int projectID = 0;
	            int totalRowCount = 0;
	            ArrayList deparmentIDList = new ArrayList();
	            ArrayList branchIDList = new ArrayList();
	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null  || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadProject = new Project();
	            	errorProjectInfo = new ProjectUploadResponse();
	            	projectID = 0;
	            	//项目名称
	            	if(checkTextCellAndRequired(row.getCell(1).toString().trim(),110)) {
	            		uploadProject.setProjectName((row.getCell(1).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectName"));
	            		errorProjectInfo.setErrContent(row.getCell(1).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//项目阶段
	            	if (checkTextCellAndRequired(row.getCell(2).toString().trim(),20)){
	            		boolean hasDepartment = false;
	            		for(int j=0;j<categoryList.size();j++){
	            			Map<String,Object> t = categoryList.get(j);	   
	            			if (row.getCell(2).toString().trim().equals(t.get("category"))){
	            				uploadProject.setCategory((row.getCell(2).toString().trim()));
	            				hasDepartment = true;
	            			}
	            		}
	            		if (!hasDepartment){
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblcategory"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(2).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblcategory"));
	            		if(row.getCell(2).toString().isEmpty()){
	            			errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()+Tools.getPropertiesValue(session, "projectUpload_string_msg_noBlank"));
	            		}
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//依赖号
	            	if (checkTextCellLength(row.getCell(3).toString().trim(),50)){
	            		uploadProject.setProjectClientNo((row.getCell(3).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectClientNo"));
	            		errorProjectInfo.setErrContent(row.getCell(3).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//依赖方
	            	if (checkTextCellLength(row.getCell(4).toString().trim(),100)){
	            		uploadProject.setProjectClientName((row.getCell(4).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectClientName"));
	            		errorProjectInfo.setErrContent(row.getCell(4).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//启动时间
	            	if(checkTextCellAndRequired(row.getCell(5).toString(),10)) {
	            		Date date = row.getCell(5).getDateCellValue();
	            		uploadProject.setStartupDate(dateFormat.format(date));
	            	} else {
	            		uploadProject.setStartupDate(null);
	            	}
	            	//结束时间
	            	if(checkTextCellAndRequired(row.getCell(6).toString(),10)) {
	            		Date date = row.getCell(6).getDateCellValue();
	            		uploadProject.setFinishDate(dateFormat.format(date));
	            	} else {
	            		uploadProject.setFinishDate(null);
	            	}
	            	//项目状态
	            	if(row.getCell(7) != null &&  !"".equals(row.getCell(7).toString().trim())) {
						if (row.getCell(7).toString().trim().equals(ProjectStateEnum.BEGIN.getName())){
							uploadProject.setProjectState(ProjectStateEnum.BEGIN.getId());
						} else if(row.getCell(7).toString().trim().equals(ProjectStateEnum.OVER.getName())){
							uploadProject.setProjectState(ProjectStateEnum.OVER.getId());
						} else if (row.getCell(7).toString().trim().equals(ProjectStateEnum.PAUSE.getName())){
							uploadProject.setProjectState(ProjectStateEnum.PAUSE.getId());
						} else if (row.getCell(7).toString().trim().equals(ProjectStateEnum.STOP.getName())){
							uploadProject.setProjectState(ProjectStateEnum.STOP.getId());
						} else {
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectState"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(7).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
						errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectState"));
						errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()+Tools.getPropertiesValue(session, "projectUpload_string_msg_noBlank"));
						errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//车厂
	            	if (row.getCell(8) != null &&  !"".equals(row.getCell(8).toString().trim())){
	            		boolean hasCarMaker = false;
	            		for(int j=0;j<carMakerList.size();j++){
	            			Map<String,Object> t = carMakerList.get(j);	   
	            			if (row.getCell(8).toString().trim().equals(t.get("carmake"))){
	            				uploadProject.setCarMaker((row.getCell(8).toString().trim()));
	            				hasCarMaker = true;
	            			}
	            		}
	            		if (!hasCarMaker){
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectCarmakeCXEE"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(8).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		uploadProject.setCarMaker(null);
	            	}
	            	
	            	//CXEE部门
	            	if(row.getCell(9)!= null && !"".equals(row.getCell(9).toString().trim())) {
	            		//departmentList
	            		boolean allDepartment = false;
	            		
	            		String[] temp = row.getCell(9).toString().split(",");
	            		for(int te=0;te<temp.length;te++){
	            			if(temp[te].equals("全部门")){
	            				allDepartment = true;
	            			}
	            		}
	            		if(allDepartment == false){
	            			for (int j = 0 ; j < temp.length; j++){
	            				
	            				for(int m = 0;m<departmentList.size();m++){
	            					Map<String,Object> t = departmentList.get(m);	 
	            					if (temp[j].trim().equals(t.get("department"))){
		            					deparmentIDList.add(t.get("departmentID"));
		            				}
	            				}
	            			}
	            		}
	            		else{
	            			for(int n = 0 ; n < departmentList.size(); n++){
	            				Map<String,Object> t = departmentList.get(n);	 
	            				deparmentIDList.add(t.get("departmentID"));
	            			}
	            		}
	            		
	            		
	            	
	            	}
	            	//CXEE课别
	            	if(row.getCell(10)!= null && !"".equals(row.getCell(10).toString().trim())) {
	            		//departmentList
	            		boolean allBranch = false;
	            		
	            		String[] temp = row.getCell(10).toString().split(",");
	            		for(int te=0;te<temp.length;te++){
	            			if(temp[te].equals("全课别")){
	            				allBranch = true;
	            			}
	            		}
	            		if(allBranch == false){
	            			for (int j = 0 ; j < temp.length; j++){
	            				
	            				for(int m = 0;m<branchList.size();m++){
	            					Map<String,Object> t = branchList.get(m);	 
	            					if (temp[j].trim().equals(t.get("branch"))){
	            						branchIDList.add(t.get("branchID"));
		            				}
	            				}
	            			}
	            		}
	            		else{
	            			for(int n = 0 ; n < branchList.size(); n++){
	            				Map<String,Object> t = branchList.get(n);	 
	            				branchIDList.add(t.get("branchID"));
	            			}
	            		}
	            	
	            	}

	            	//项目机能
	            	if (row.getCell(11) != null &&  !"".equals(row.getCell(11).toString().trim())){
	            		boolean hasFunction = false;
	            		for(int j=0;j<functionList.size();j++){
	            			Map<String,Object> t = functionList.get(j);	   
	            			if (row.getCell(11).toString().trim().equals(t.get("functionName"))){
	            				uploadProject.setFunction(t.get("functionName").toString());
	            				uploadProject.setFunctionID(Integer.valueOf(t.get("functionID").toString()));
	            				hasFunction = true;
	            			}
	            		}
	            		if (!hasFunction){
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblfunction"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(11).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		uploadProject.setFunction(null);
	            	}
	            	//机种
	            	if (checkTextCellLength(row.getCell(12).toString().trim(),45)){
	            		uploadProject.setModel((row.getCell(12).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectModel"));
	            		errorProjectInfo.setErrContent(row.getCell(12).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//管理项番
	            	if (checkTextCellLength(row.getCell(13).toString().trim(),45)){
	            		uploadProject.setTransferNo((row.getCell(13).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "projectInfo_string_transferNo"));
	            		errorProjectInfo.setErrContent(row.getCell(13).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//3D项番名
	            	if (checkTextCellLength(row.getCell(14).toString().trim(),50)){
	            		uploadProject.setItemName((row.getCell(14).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "projectInfo_string_itemNameCXEE"));
	            		errorProjectInfo.setErrContent(row.getCell(14).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//PJ No.
	            	if (checkTextCellLength(row.getCell(15).toString().trim(),50)){
	            		uploadProject.setPJNo((row.getCell(15).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectPJNo"));
	            		errorProjectInfo.setErrContent(row.getCell(15).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//仮PJ No.
	            	if (checkTextCellLength(row.getCell(16).toString().trim(),50)){
	            		uploadProject.setTempPJNo((row.getCell(16).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectTempPJNo"));
	            		errorProjectInfo.setErrContent(row.getCell(16).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//PJ名
	            	if (checkTextCellLength(row.getCell(17).toString().trim(),50)){
	            		uploadProject.setPJName((row.getCell(17).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectPJName"));
	            		errorProjectInfo.setErrContent(row.getCell(17).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//备注
	            	if (checkTextCellLength(row.getCell(18).toString().trim(),100)){
	            		uploadProject.setMemo((row.getCell(18).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectmemo"));
	            		errorProjectInfo.setErrContent(row.getCell(18).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//部门和课别
					/*if(row.getCell(9)!= null && !"".equals(row.getCell(9).toString().trim())) {
						boolean allDepartment = false;
						boolean hasDepartment = false;
						boolean hasBranch = false;
						ArrayList departmentIdList = new ArrayList();
						for (int j = 0 ; j < departmentList.size(); j++){
	            			Map<String,Object> t = departmentList.get(j);
	            			String[] temp = row.getCell(9).toString().trim().split(",");
	            			for(int k = 0; k<temp.length; k++){
	            				if(temp[k].equals("全部门")){
	            					allDepartment = true;
	            					break;
	            				}
	            			}
	            			if(allDepartment == true){
	            				for(int q=0;q<departmentList.size();q++){
	            					departmentIdList.add(departmentList.get(q).get("departmentID"));
	            				}
	            			}
	            			for(int k = 0; k<temp.length; k++){
		            			for(int m = 0;m<departmentList.size();m++){
		            				if(temp[k].equals(departmentList.get(m).get("department"))){
		            					departmentIdList.add(departmentList.get(m).get("departmentID"));
		            					hasDepartment = true;
		            					break;
		            				}else{
		            					hasDepartment = false;
		            				}
		            			}
		            			if(hasDepartment == false){
		            				break;
		            			}
		            		}
	            			if(hasDepartment == true){
	            				
	            				
	            			}
            				if (row.getCell(9).toString().trim().equals(t.get("department"))){
            					//uploadStaff.setDepartmentID(Integer.parseInt((t.get("departmentID").toString())));
            					uploadStaff.setDepartment(row.getCell(9).toString().trim());
            					hasDepartment = true;
            					//课别
            					if(row.getCell(10)!= null && !"".equals(row.getCell(10).toString().trim())) {
            						List<Map<String, Object>> branchselectList=staffSelectedService.getBranchList(t.get("departmentID").toString());
            						for (int k=0; k < branchselectList.size(); k++){
            							Map<String,Object> branchMap = branchselectList.get(k);
            							if(row.getCell(10).toString().trim().equals(branchMap.get("branch"))){
            								uploadStaff.setBranchID(Integer.parseInt(branchMap.get("branchID").toString()));
            								uploadStaff.setBranch(row.getCell(10).toString().trim());
            								hasBranch = true;
            								break;
            							}
            						}
            	            	} else {
            	            		hasBranch = true;
            	            		uploadStaff.setBranchID(0);
    								uploadStaff.setBranch("");
            	            	}
            					break;
            				}
	            		}
						if (!hasDepartment){
							errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_department"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(5).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						}
						if (!hasBranch){
							errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_branch"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(6).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_department"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	
	            	*/
					//staffID = istaffService.getStaffByEmail(uploadStaff.getEmail());
					projectID = projectService.getProjectIDByProjectName(uploadProject.getProjectName());
					if (projectID > 0){
						// 人员存在时更新人员信息
						uploadProject.setProjectID(projectID);
						projectService.updateProject(uploadProject);
						projectService.deletePDByProjectID(uploadProject.getProjectID());
						if (deparmentIDList != null && deparmentIDList.size() > 0){
							for (int de = 0; de < deparmentIDList.size(); de++) {
								projectService.insertPDByProjectID(Integer.valueOf(projectID),Integer.valueOf(deparmentIDList.get(de).toString()));
							}
						}
						projectService.deleteProBranchByProjectID(uploadProject.getProjectID());
						if (branchIDList != null && branchIDList.size() > 0){
							for (int br = 0; br < branchIDList.size(); br++) {
								projectService.insertProBranchByProjectID(Integer.valueOf(projectID),Integer.valueOf(branchIDList.get(br).toString()));
							}
						}

					} else {
						// 人员不存在时追加人员信息
						boolean result = projectService.insertNewProject(uploadProject);
						if(result == true){
							projectService.deletePDByProjectID(uploadProject.getProjectID());
							if (deparmentIDList != null && deparmentIDList.size() > 0){
								for (int de = 0; de < deparmentIDList.size(); de++) {
									projectService.insertPDByProjectID(uploadProject.getProjectID(),Integer.valueOf(deparmentIDList.get(de).toString()));
								}
							}
							projectService.deleteProBranchByProjectID(uploadProject.getProjectID());
							if (branchIDList != null && branchIDList.size() > 0){
								for (int br = 0; br < branchIDList.size(); br++) {
									projectService.insertProBranchByProjectID(uploadProject.getProjectID(),Integer.valueOf(branchIDList.get(br).toString()));
								}
							}
						}
					}
	            }
	            
	            filestream.close();

	            Gson gson = new Gson();
				resultString = gson.toJson(errorProjectInfoList);
				int totalErrorCount = errorProjectInfoList.size();
				String result ="";

				if(0==totalErrorCount){				
					result ="{\"code\":\"S\",\"successRowCount\":"+String.valueOf(totalRowCount)+",\"result\":"+resultString+"}";
				}else{
					int successRowCount=totalRowCount-totalErrorCount;
					result ="{\"code\":\"F\",\"ngRowCount\":"+totalErrorCount+",\"successRowCount\":"+successRowCount+",\"result\":"+resultString+"}";
				}
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
            }
		} catch (IOException e){
			e.printStackTrace();  
            PrintWriter out;
			try {
				out = response.getWriter();
				out.write("ERROR");
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 项目信息文件上传CT
	 */
	@RequestMapping(value = "/fileUploadCT",method=RequestMethod.POST)
	public void fileUploadCT(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		HttpSession session = request.getSession();
		try {
			// 转型为MultipartHttpRequest：  
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            // 获得文件：  
            MultipartFile file = multipartRequest.getFile("file");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
            // 获得输入流：  
            InputStream filestream = file.getInputStream();
            
            HSSFWorkbook wb = new HSSFWorkbook(filestream);
            
            HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(0);

            if (sheet == null ){
            	filestream.close();
				PrintWriter out = response.getWriter();
				out.write("NOSHEET");
				out.close();
            } else {
	            HSSFRow row = null;
	            Project uploadProject = null;
	            int startRow = 2;
	            int startCell = 1;
	            List<Map<String, Object>> departmentList = projectSelectedService.getProjectDepartmentListCT();
	            List<Map<String, Object>> categoryList = projectSelectedService.getProjectCategoryListCT();
	            List<Map<String, Object>> carMakerList = projectSelectedService.getCarMakeListCT();
	            List<Map<String, Object>> functionList = projectSelectedService.getFuctionListCT();
	            List<ProjectUploadResponse> errorProjectInfoList = new ArrayList<ProjectUploadResponse>();
	            ProjectUploadResponse errorProjectInfo;
	            String resultString = "";
	            int projectID = 0;
	            int totalRowCount = 0;
	            
	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadProject = new Project();
	            	errorProjectInfo = new ProjectUploadResponse();
	            	ArrayList deparmentIDList = new ArrayList();
	            	projectID = 0;
	            	//PJ No.
	            	if (checkTextCellLength(row.getCell(1).toString().trim(),50)){
	            		uploadProject.setPJNo((row.getCell(1).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectPJNo"));
	            		errorProjectInfo.setErrContent(row.getCell(1).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//仮PJ No.
	            	if (checkTextCellLength(row.getCell(2).toString().trim(),50)){
	            		uploadProject.setTempPJNo((row.getCell(2).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectTempPJNo"));
	            		errorProjectInfo.setErrContent(row.getCell(2).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	
	            	//PJ名
	            	if(row.getCell(3) != null &&  !"".equals(row.getCell(3).toString().trim())){
		            	if (checkTextCellLength(row.getCell(3).toString().trim(),50)){
		            		uploadProject.setPJName((row.getCell(3).toString().trim()));
		            	} else {
		            		//出错时处理
		            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectPJName"));
		            		errorProjectInfo.setErrContent(row.getCell(3).toString());
		            		errorProjectInfoList.add(errorProjectInfo);
		            		continue;
		            	}
	            	}else{	
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
						errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectState"));
						errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()+Tools.getPropertiesValue(session, "projectUpload_string_msg_noBlank"));
						errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	 //项目阶段
	            	if (checkTextCellAndRequired(row.getCell(4).toString().trim(),20)){
	            		boolean hasDepartment = false;
	            		for(int j=0;j<categoryList.size();j++){
	            			Map<String,Object> t = categoryList.get(j);	   
	            			if (row.getCell(4).toString().trim().equals(t.get("category"))){
	            				uploadProject.setCategory((row.getCell(4).toString().trim()));
	            				hasDepartment = true;
	            			}
	            		}
	            		if (!hasDepartment){
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblcategory"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(4).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblcategory"));
	            		if(row.getCell(4).toString().isEmpty()){
	            			errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()+Tools.getPropertiesValue(session, "projectUpload_string_msg_noBlank"));
	            		}
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//管理项番
	            	if (checkTextCellLength(row.getCell(5).toString().trim(),45)){
	            		uploadProject.setTransferNo((row.getCell(5).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "projectInfo_string_transferNo"));
	            		errorProjectInfo.setErrContent(row.getCell(5).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//3D项番名
	            	if (checkTextCellLength(row.getCell(6).toString().trim(),50)){
	            		uploadProject.setItemName((row.getCell(6).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "projectInfo_string_itemNameCXEE"));
	            		errorProjectInfo.setErrContent(row.getCell(6).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//启动时间
	            	if(checkTextCellAndRequired(row.getCell(7).toString(),10)) {
	            		Date date = row.getCell(7).getDateCellValue();
	            		uploadProject.setStartupDate(dateFormat.format(date));
	            	} else {
	            		uploadProject.setStartupDate(null);
	            	}
	            	//结束时间
	            	if(checkTextCellAndRequired(row.getCell(8).toString(),10)) {
	            		Date date = row.getCell(8).getDateCellValue();
	            		uploadProject.setFinishDate(dateFormat.format(date));
	            	} else {
	            		uploadProject.setFinishDate(null);
	            	}
	            	
	            	//项目状态
	            	if(row.getCell(9) != null &&  !"".equals(row.getCell(9).toString().trim())) {
						if (row.getCell(9).toString().trim().equals(ProjectStateEnumCT.BEGIN.getName())){
							uploadProject.setProjectState(ProjectStateEnumCT.BEGIN.getId());
						} else if(row.getCell(9).toString().trim().equals(ProjectStateEnumCT.OVER.getName())){
							uploadProject.setProjectState(ProjectStateEnumCT.OVER.getId());
						} else if (row.getCell(9).toString().trim().equals(ProjectStateEnumCT.PAUSE.getName())){
							uploadProject.setProjectState(ProjectStateEnumCT.PAUSE.getId());
						} else if (row.getCell(9).toString().trim().equals(ProjectStateEnumCT.STOP.getName())){
							uploadProject.setProjectState(ProjectStateEnumCT.STOP.getId());
						} else {
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectState"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(9).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
						errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectState"));
						errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()+Tools.getPropertiesValue(session, "projectUpload_string_msg_noBlank"));
						errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//车厂
	            	if (row.getCell(10) != null &&  !"".equals(row.getCell(10).toString().trim())){
	            		boolean hasCarMaker = false;
	            		for(int j=0;j<carMakerList.size();j++){
	            			Map<String,Object> t = carMakerList.get(j);	   
	            			if (row.getCell(10).toString().trim().equals(t.get("carmake"))){
	            				uploadProject.setCarMaker((row.getCell(10).toString().trim()));
	            				hasCarMaker = true;
	            			}
	            		}
	            		if (!hasCarMaker){
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectCarmakeCT"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(10).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		uploadProject.setCarMaker(null);
	            	}
	            	//事業セグメント
	            	if (checkTextCellLength(row.getCell(11).toString().trim(),50)){
	            		uploadProject.setEnterpriseSeg((row.getCell(11).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "projectInfo_string_enterpriseSeg"));
	            		errorProjectInfo.setErrContent(row.getCell(11).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	//项目机能
	            	if (row.getCell(12) != null &&  !"".equals(row.getCell(12).toString().trim())){
	            		boolean hasFunction = false;
	            		for(int j=0;j<functionList.size();j++){
	            			Map<String,Object> t = functionList.get(j);	   
	            			if (row.getCell(12).toString().trim().equals(t.get("functionName"))){
	            				uploadProject.setFunction(t.get("functionName").toString());
	            				uploadProject.setFunctionID(Integer.valueOf(t.get("functionID").toString()));
	            				hasFunction = true;
	            			}
	            		}
	            		if (!hasFunction){
							errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblfunction"));
							errorProjectInfo.setErrContent(errorProjectInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectUpload_string_msg_notExist").replace("{0}", row.getCell(12).toString()));
							errorProjectInfoList.add(errorProjectInfo);
		            		continue;
						}
	            	} else {
	            		uploadProject.setFunction(null);
	            	}
	            	//CT開発部署
	            	if(row.getCell(13)!= null && !"".equals(row.getCell(13).toString().trim())) {
	            		//departmentList
	            		boolean allDepartment = false;
	            		
	            		String[] temp = row.getCell(13).toString().split(",");
	            		for(int te=0;te<temp.length;te++){
	            			if(temp[te].equals("全部署")){
	            				allDepartment = true;
	            			}
	            		}
	            		if(allDepartment == false){
	            			for (int j = 0 ; j < temp.length; j++){
	            				//ArrayList deparmentIDList = new ArrayList();
	            				for(int m = 0;m<departmentList.size();m++){
	            					Map<String,Object> t = departmentList.get(m);	 
	            					if (temp[j].trim().equals(t.get("department"))){
		            					deparmentIDList.add(t.get("departmentID"));
		            				}
	            				}
	            			}
	            		}
	            		else{
	            			//ArrayList deparmentIDList = new ArrayList();
	            			for(int n = 0 ; n < departmentList.size(); n++){
	            				Map<String,Object> t = departmentList.get(n);	 
	            				deparmentIDList.add(t.get("departmentID"));
	            			}
	            		}
	            	
	            	}
	            	//备注
	            	if (checkTextCellLength(row.getCell(14).toString().trim(),100)){
	            		uploadProject.setMemo((row.getCell(14).toString().trim()));
	            	} else {
	            		//出错时处理
	            		errorProjectInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorProjectInfo.setErrItem(Tools.getPropertiesValue(session, "project_string_tblprojectmemo"));
	            		errorProjectInfo.setErrContent(row.getCell(14).toString());
	            		errorProjectInfoList.add(errorProjectInfo);
	            		continue;
	            	}
	            	/*
					projectID = projectService.getProjectIDByProjectName(uploadProject.getProjectName());
					if (projectID > 0){
						// 项目存在时更新人员信息
						uploadProject.setProjectID(projectID);
						projectService.updateProject(uploadProject);
						if (deparmentIDList != null && deparmentIDList.size() > 0){
							for (int de = 0; de < deparmentIDList.size(); de++) {
								projectService.insertPDByProjectID(Integer.valueOf(projectID),Integer.valueOf(deparmentIDList.get(de).toString()));
							}
						}
					} else 
					{*/
						// 项目不存在时追加人员信息
						boolean result = projectService.insertNewProjectCT(uploadProject);
						if(result == true){
							projectService.deletePDByProjectIDCT(uploadProject.getProjectID());
							if (deparmentIDList != null && deparmentIDList.size() > 0){
								for (int de = 0; de < deparmentIDList.size(); de++) {
									projectService.insertPDByProjectIDCT(uploadProject.getProjectID(),Integer.valueOf(deparmentIDList.get(de).toString()));
								}
							}
						}
					//}
	            }
	            
	            filestream.close();

	            Gson gson = new Gson();
				resultString = gson.toJson(errorProjectInfoList);
				int totalErrorCount = errorProjectInfoList.size();
				String result ="";

				if(0==totalErrorCount){				
					result ="{\"code\":\"S\",\"successRowCount\":"+String.valueOf(totalRowCount)+",\"result\":"+resultString+"}";
				}else{
					int successRowCount=totalRowCount-totalErrorCount;
					result ="{\"code\":\"F\",\"ngRowCount\":"+totalErrorCount+",\"successRowCount\":"+successRowCount+",\"result\":"+resultString+"}";
				}
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
            }
		} catch (IOException e){
			e.printStackTrace();  
            PrintWriter out;
			try {
				out = response.getWriter();
				out.write("ERROR");
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 项目详细页面
	 * 
	 * @param 项目ID
	 * @return
	 */
	@RequestMapping(value = "/more")
	public ModelAndView getMore(@RequestParam int id) {
		ModelAndView mv = new ModelAndView();
		Project project = projectService.getProjectById(id);
		List<Project> projectDepartmentList = projectService.getProjectDepartmentByProjectID(id);
		String tempString = "";
		for (int j = 0;j < projectDepartmentList.size();j++)
		{
			tempString += projectDepartmentList.get(j).getProjectDepartment();
			
			if (j < projectDepartmentList.size() - 1) {
				tempString += ";";
			}
		}
		project.setProjectDepartment(tempString);
		
		List<Project> projectBranchList = projectService.getProjectBranchByProjectID(id);
		tempString = "";
		for (int j = 0;j < projectBranchList.size();j++)
		{
			tempString += projectBranchList.get(j).getProjectBranch();
			
			if (j < projectBranchList.size() - 1) {
				tempString += ";";
			}
		}
		project.setProjectBranch(tempString);
//		switch (projectList.size()) {
//		case 0:
//			project.setProjectDepartment("无");
//			break;
//		case 1:
//			project.setProjectDepartment(projectList.get(0).getProjectDepartment());
//			break;
//		case 2:
//			String tempString = projectList.get(0).getProjectDepartment()+"、"+
//			projectList.get(1).getProjectDepartment();
//			project.setProjectDepartment(tempString);
//			break;
//		case 3:
//			project.setProjectDepartment("机构、电气、软件");
//			break;
//		case 9: //待确定全体部门数量后必须修改！！！！！！
//			project.setProjectDepartment("全体部门");
//			break;
//		default:
//			break;
//		}
		mv.addObject("project", project);
		mv.setViewName("admin/project/projectDetail");
		return mv;
	}
	
	/**
	 * 请求员工项目信息
	 * @param session
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/listSelectProject", method = RequestMethod.POST)
	public void listSelectProject(HttpSession session,HttpServletResponse response) throws IOException {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		CategoryParam categoryParam = this.getGateCategoryParam(staff.getDepartment());
		
		//特殊部门需要能查看所有项目信息
		List<Project> projectList = new ArrayList<Project>();
		List <Project> recentProjectList = new ArrayList<Project>();
		String staffDepartment = staff.getDepartment();
		String staffDepartmentID = Integer.toString(staff.getDepartmentID());
		//2014/11/24 取消部分部门可看见所有项目
		//if (KAIFA_GUANLI_BU.equals(staffDepartment) || SHEJI_KAIFA_BU.equals(staffDepartment) 
		//	|| GONGCHENG_SHEJI_BU.equals(staffDepartment) || SHEJI_KAIFAGUANLI.equals(staffDepartment)
		//	|| KAIFA_TONGKUO_SHI.equals(staffDepartment)||CHINA_ODM_ZHIPINKAIFABU.equals(staffDepartment)) {
		//	projectList = projectService.listAllStartupProject(staff.getStaffID());
		//	recentProjectList = projectService.recentProjectOther(staff.getStaffID());
		//}
		//else {//普通部门只能查看各自部门的项目信息
			//projectList = projectService.listSelectProject(staff.getStaffID(),staff.getDepartment());
			//recentProjectList = projectService.recentProject(staff.getStaffID(),staff.getDepartment());
		//软件，构造，电气设计三部门按照课别显示
		if (staff.getDepartmentID() == DepartmentEnum.RJKFB.getId() ||
			staff.getDepartmentID() == DepartmentEnum.GZKFB.getId() ||
			staff.getDepartmentID() == DepartmentEnum.DQKFB.getId()) {
			projectList = projectService.listSelectProjectByBranchID(staff.getStaffID(),staffDepartmentID,Integer.toString(staff.getBranchID()));
			recentProjectList = projectService.recentProjectByBranchID(staff.getStaffID(),staffDepartmentID,Integer.toString(staff.getBranchID()));
		} else {
			projectList = projectService.listSelectProject(staff.getStaffID(),staffDepartmentID);
			recentProjectList = projectService.recentProject(staff.getStaffID(),staffDepartmentID);
		}
		//}
 
		for (Project project : projectList) {
			recentProjectList.add(project);
		}
		
		Category devCategory = new Category();
		Category devCtCategory = new Category();
		Category devPrcCategory = new Category();
		Category rfirCategory = new Category();
		Category noAccordCategory = new Category();
		Category otherDevCategory = new Category();
		List<ProjectName> devProjectNames = new ArrayList<ProjectName>();
		List<ProjectName> devCtProjectNames = new ArrayList<ProjectName>();
		List<ProjectName> rfirProjectNames = new ArrayList<ProjectName>();
		List<ProjectName> noAccordProjectNames = new ArrayList<ProjectName>();
		List<ProjectName> otherProjectNames = new ArrayList<ProjectName>();
		devCtCategory.setVal("开发工数 CT项目");
		devPrcCategory.setVal("开发工数 PRC项目");
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
		for (Project project : recentProjectList) {
			if(project.getCategory().equals(noAccordCategory.getVal())){
				ProjectName projectName = new ProjectName();
				projectName.setMenu(noAccordTaskList);
				projectName.setVal(project.getProjectID()+"");
				if(project.getProjectClientNo()!= null && !project.getProjectClientNo().equals("")){
					projectName.setTxt(project.getProjectName()+"("+project.getProjectClientNo()+")");
				}else{
					projectName.setTxt(project.getProjectName());
				}
				noAccordProjectNames.add(projectName);
			}
			if(project.getCategory().equals(devCategory.getVal())){
				ProjectName projectName = new ProjectName();
				if(project.getProjectName().equals("185 工程设计部CT") | project.getProjectName().equals("185 工程设计部DCOE")){
					List<Task> engineeringTaskList = new ArrayList<Task>();
					Task task1 = new Task();
					task1.setVal("0");
					task1.setTxt("1 生产技术管理");
					Task task2 = new Task();
					task2.setVal("0");
					task2.setTxt("2 翻译");
					Task task3 = new Task();
					task3.setVal("0");
					task3.setTxt("3 购买关系");
					Task task4 = new Task();
					task4.setVal("0");
					task4.setTxt("4 休假");
					Task task5 = new Task();
					task5.setVal("0");
					task5.setTxt("5 教育");
					Task task6 = new Task();
					task6.setVal("0");
					task6.setTxt("6 流程改革、效率化");

					engineeringTaskList.add(task1);
					engineeringTaskList.add(task2);
					engineeringTaskList.add(task3);
					engineeringTaskList.add(task4);
					engineeringTaskList.add(task5);
					engineeringTaskList.add(task6);
					projectName.setMenu(engineeringTaskList);
					projectName.setVal(project.getProjectID()+"");
					projectName.setTxt(project.getProjectName());
				}else{
					projectName.setMenu(devTaskList);
					projectName.setVal(project.getProjectID()+"");
					if(project.getProjectClientNo()!= null && !project.getProjectClientNo().equals("")){
						projectName.setTxt(project.getProjectName()+"("+project.getProjectClientNo()+")");
					}else{
						projectName.setTxt(project.getProjectName());
					}
				}
				if(project.getProjectClientName().toLowerCase().equals("ct")||project.getProjectClientName().toLowerCase().equals("outout")){
					devCtProjectNames.add(projectName);
				}else{
					devProjectNames.add(projectName);
				}
                
			}
			if(project.getCategory().equals(rfirCategory.getVal())){
				ProjectName projectName = new ProjectName();
				projectName.setMenu(rfirTaskList);
				projectName.setVal(project.getProjectID()+"");
				if(project.getProjectClientNo()!= null && !project.getProjectClientNo().equals("")){
					projectName.setTxt(project.getProjectName()+"("+project.getProjectClientNo()+")");
				}else{
					projectName.setTxt(project.getProjectName());
				}
				rfirProjectNames.add(projectName);
			}
			if(project.getCategory().equals(otherDevCategory.getVal())){
				ProjectName projectName = new ProjectName();
				projectName.setMenu(advanceTaskList);
				projectName.setVal(project.getProjectID()+"");
				if(project.getProjectClientNo()!= null && !project.getProjectClientNo().equals("")){
					projectName.setTxt(project.getProjectName()+"("+project.getProjectClientNo()+")");
				}else{
					projectName.setTxt(project.getProjectName());
				}
				otherProjectNames.add(projectName);
			}
		}

		devPrcCategory.setMenu(devProjectNames);
		devCtCategory.setMenu(devCtProjectNames);
		otherDevCategory.setMenu(otherProjectNames);
		rfirCategory.setMenu(rfirProjectNames);
		noAccordCategory.setMenu(noAccordProjectNames);
		List<Category> resultList = new ArrayList<Category>();

		resultList.add(devCtCategory);
		resultList.add(devPrcCategory);
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
	private CategoryParam getGateCategoryParam(String departmentName) {
		CategoryParam categoryParam = null;
		String department = departmentName;
		//if (!categoryMap.containsKey(department)) {
			categoryParam = new CategoryParam();
			List<Map<String,Object>> devTaskList = projectService.listTask(categoryParam.getDev(), department);
			List<Map<String,Object>> otherTaskList = projectService.listTask(categoryParam.getRfir(), department);
			List<Map<String,Object>> advanceTaskList = projectService.listTask(categoryParam.getOther(), department);
			List<Map<String,Object>> noAccordTaskList = projectService.listTask(categoryParam.getNoAccord(), department);
			categoryParam.setDevTaskList(devTaskList);
			categoryParam.setOtherTaskList(otherTaskList);
			categoryParam.setNoAccordTaskList(noAccordTaskList);
			if(advanceTaskList.size() != 0){
				categoryParam.setAdvanceTaskList(advanceTaskList);
			}
			categoryMap.put(department, categoryParam);
		//}
		return (CategoryParam) categoryMap.get(department);
	}
	
	private boolean checkTextCellAndRequired(String value,int length){	
		if(value == null || "".equals(value)){
			return false;
		}
		if(value.length() > length){
			return false;
		}
		return true;
	}
	
	private boolean checkTextCellLength(String value,int length){	
		if(value.length() > length){
			return false;
		}
		return true;
	}
}
