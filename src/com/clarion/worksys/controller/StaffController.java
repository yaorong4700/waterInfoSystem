package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理Controller
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 *  2013-3-21 GuoRenPeng
 *  # 增加新的权限,员工管理界面返回staff类
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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.RequestObject;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.StaffUploadResponse;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.filter.DateEditor;
import com.clarion.worksys.httpentity.StaffReqParam;
import com.clarion.worksys.httpentity.StaffResponse;
import com.clarion.worksys.httpentity.StaffResponseRows;
import com.clarion.worksys.httpentity.StaffSelectInfo;
import com.clarion.worksys.service.StaffSelectedService;
import com.clarion.worksys.service.StaffService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Const.AbilityEnum;
import com.clarion.worksys.util.Const.StateEnum;
import com.clarion.worksys.util.Const.StateEnumCT;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/staff")
public class StaffController {
	@Autowired
	private StaffService istaffService;
	@Autowired
	private StaffSelectedService staffSelectedService;
	@Autowired
	private UserRoleManageService userRoleManageService;

	/**
	 * 返回员工分页页面
	 * @param ro
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping
	public String staffList(RequestObject ro, Model model,HttpSession session) {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		List<Map<String, Object>> positionListCXEE = null;
		List<Map<String, Object>> positionListCT = null;
		List<Map<String, Object>> departmentListCXEE = null;
		List<Map<String, Object>> departmentListCT = null;
		
		try {
			positionListCXEE = staffSelectedService.getPositionListCXEE();
			departmentListCXEE = staffSelectedService.getDepartmentListCXEE();
		} catch (Exception e){
			positionListCXEE = null;
			departmentListCXEE = null;
		}
		
		try {
			positionListCT = staffSelectedService.getPositionListCT();
			departmentListCT = staffSelectedService.getDepartmentListCT();
		} catch (Exception e){
			positionListCT = null;
			departmentListCT = null;
		}
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"StaffManage",userRoleManageService);
		model.addAttribute("staff", staff);
		model.addAttribute("departmentListCXEE", departmentListCXEE);
		model.addAttribute("departmentListCT", departmentListCT);
		model.addAttribute("positionListCXEE", positionListCXEE);
		model.addAttribute("positionListCT", positionListCT);
		model.addAttribute("QueryRoleFlag", userRole.getQueryRoleFlag());//検索
		model.addAttribute("AlterRoleFlag", userRole.getAlterRoleFlag());//編集
		model.addAttribute("UploadRoleFlag", userRole.getUploadRoleFlag());//U/L
		model.addAttribute("DownloadRoleFlag", userRole.getDownloadRoleFlag());//D/L
		model.addAttribute("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
		model.addAttribute("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
		model.addAttribute("DepartmentFlag", userRole.getDepartmentFlag());//部门权限
		return "admin/staff/staff";
	}
	
	/**
	 * 下载员工信息 返回下载地址
	 * @param httpSession
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/downloadStaff")
	public ModelAndView downloadStaff(HttpSession httpSession,HttpServletResponse response) {
    	List<Staff> staffs = istaffService.listAllStaff();
    	String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath(); 
		//String webadr=contextpath+dataToExcle.toExcel(staffs,realPathString);
		String webadr=contextpath+istaffService.downloadStaff(staffs, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	/**
	 * 下载CT员工信息，返回下载地址
	 * @param httpSession
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/downloadStaffCT")
	public ModelAndView downloadStaffCT(HttpSession httpSession,HttpServletResponse response) {
    	List<Staff> staffs = istaffService.listAllStaffCT();
    	String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath(); 
		String webadr=contextpath+istaffService.downloadStaffCT(staffs, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
    
    /**
     * 请求员工信息分页数据
     * @param response
     * @param staffReqParam
     * @throws IOException
     * @throws ParseException 
     */
	@RequestMapping(value = "/listStaff")
	public void listStaff(HttpServletResponse response,StaffReqParam staffReqParam) throws IOException, ParseException {
		
		//response.setCharacterEncoding("UTF-8");
		if("1".equals(staffReqParam.getQueryRoleFlag())){
			//设置数据库查询时从哪条数据开始
			staffReqParam.setPageSeq((staffReqParam.getPage()-1)*staffReqParam.getRp());
			//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
	        if(staffReqParam.getStaffName()!=null &&staffReqParam.getStaffName().trim().equals("")){
				staffReqParam.setStaffName(null);
			}
	        /*if(staffReqParam.getQuery()!=null &&!staffReqParam.getQuery().trim().equals("")){
				if(staffReqParam.getQtype().trim().equals("name")){
					staffReqParam.setStaffName(staffReqParam.getQuery().trim());
				}else if(staffReqParam.getQtype().trim().equals("position")){
					staffReqParam.setPosition(staffReqParam.getQuery().trim());
				}else if(staffReqParam.getQtype().trim().equals("branch")){
					staffReqParam.setBranch(staffReqParam.getQuery().trim());
				}else if(staffReqParam.getQtype().trim().equals("department")){
					staffReqParam.setDepartment(staffReqParam.getQuery().trim());
				}else if(staffReqParam.getQtype().trim().equals("superior")){
					staffReqParam.setSuperior(staffReqParam.getQuery().trim());
				}else if(staffReqParam.getQtype().trim().equals("stateName")){
					staffReqParam.setStateName(staffReqParam.getQuery().trim());
				}
			}*/
	        //检索部条件设定
	        if (staffReqParam.getDepartment() != null && "".equals(staffReqParam.getDepartment().trim())){
	        	staffReqParam.setDepartment(null);
	        }
	        if (staffReqParam.getBranch() != null && "".equals(staffReqParam.getBranch().trim())){
	        	staffReqParam.setBranch(null);
	        }
	        if (staffReqParam.getPosition() != null && "".equals(staffReqParam.getPosition().trim())){
	        	staffReqParam.setPosition(null);
	        }
	        if (staffReqParam.getSuperior() != null && "".equals(staffReqParam.getSuperior().trim())){
	        	staffReqParam.setSuperior(null);
	        }
	        if (staffReqParam.getStateName() != null && "".equals(staffReqParam.getStateName().trim())){
	        	staffReqParam.setStateName(null);
	        }
	        if (staffReqParam.getDateQuitCompany() != null && "".equals(staffReqParam.getDateQuitCompany().trim())){
	        	staffReqParam.setDateQuitCompany(null);
	        } else {
	        	if ("1".equals(staffReqParam.getIsYMDFlag())){
	        		//YYYY/MM/DD的场合
	        		staffReqParam.setDateQuitCompanyEnd(staffReqParam.getDateQuitCompany());
	        	} else if ("2".equals(staffReqParam.getIsYMDFlag())){
	        		// YYYY/MM的场合
	        		String lastDay = "";
					String firstDay = staffReqParam.getDateQuitCompany() + "/01";

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = dateFormat.parse(firstDay);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, 1);
					calendar.add(Calendar.DATE, -1);
					
					lastDay = dateFormat.format(calendar.getTime());
					
					staffReqParam.setDateQuitCompany(firstDay);
					staffReqParam.setDateQuitCompanyEnd(lastDay);
	        	} else if ("3".equals(staffReqParam.getIsYMDFlag())){
	        		// YYYY的场合
	        		String firstDay = staffReqParam.getDateQuitCompany() + "/01/01";
	        		String lastDay = staffReqParam.getDateQuitCompany() + "/12/31";
	        		
	        		staffReqParam.setDateQuitCompany(firstDay);
					staffReqParam.setDateQuitCompanyEnd(lastDay);
	        	}
	        }
	        
			List<Staff> staffList = istaffService.listStaff(staffReqParam);		
			int totalPageCount = istaffService.totalPageCount(staffReqParam);
			
			List<StaffResponseRows> staffRespongseList = new ArrayList<StaffResponseRows>();
			for (int i=0;i<staffList.size();i++) {
				if(staffList.get(i).getBranch()==null){
					staffList.get(i).setBranch("");	
				}
				if(staffList.get(i).getDepartment()==null){
					staffList.get(i).setDepartment("");
				}
				if(staffList.get(i).getSuperior()==null){
					staffList.get(i).setSuperior("");
				}
				StaffResponseRows staffResponseRows = new StaffResponseRows(i, staffList.get(i));
				staffRespongseList.add(staffResponseRows);
			}
			StaffResponse staffResponse = new StaffResponse();
			staffResponse.setPage(staffReqParam.getPage());        //设置
			staffResponse.setTotal(totalPageCount);
			staffResponse.setRows(staffRespongseList);
			Gson gson = new Gson();
			String resultString = gson.toJson(staffResponse);
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
     * 请求CT员工信息分页数据
     * @param response
     * @param staffReqParam
     * @throws IOException
     * @throws ParseException 
     */
	@RequestMapping(value = "/listStaffCT")
	public void listStaffCT(HttpServletResponse response,StaffReqParam staffReqParam) throws IOException, ParseException {
		if("1".equals(staffReqParam.getQueryRoleFlag())){
		//设置数据库查询时从哪条数据开始
		staffReqParam.setPageSeq((staffReqParam.getPage()-1)*staffReqParam.getRp());
		//检索部条件设定,如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(staffReqParam.getStaffName()!=null &&staffReqParam.getStaffName().trim().equals("")){
			staffReqParam.setStaffName(null);
		}
        if (staffReqParam.getDepartment() != null && "".equals(staffReqParam.getDepartment().trim())){
        	staffReqParam.setDepartment(null);
        }
        if (staffReqParam.getBranch() != null && "".equals(staffReqParam.getBranch().trim())){
        	staffReqParam.setBranch(null);
        }
        if (staffReqParam.getPosition() != null && "".equals(staffReqParam.getPosition().trim())){
        	staffReqParam.setPosition(null);
        }
        if (staffReqParam.getStateName() != null && "".equals(staffReqParam.getStateName().trim())){
        	staffReqParam.setStateName(null);
        }
                
		List<Staff> staffList = istaffService.listStaffCT(staffReqParam);		
		int totalPageCount = istaffService.totalPageCountCT(staffReqParam);
		
		List<StaffResponseRows> staffRespongseList = new ArrayList<StaffResponseRows>();
		for (int i=0;i<staffList.size();i++) {
			
			if(staffList.get(i).getBranch()==null){
				staffList.get(i).setBranch("");	
			}
			if(staffList.get(i).getDepartment()==null){
				staffList.get(i).setDepartment("");
			}
			StaffResponseRows staffResponseRows = new StaffResponseRows(i, staffList.get(i));
			staffRespongseList.add(staffResponseRows);
		}
		StaffResponse staffResponse = new StaffResponse();
		staffResponse.setPage(staffReqParam.getPage());
		staffResponse.setTotal(totalPageCount);
		staffResponse.setRows(staffRespongseList);
		Gson gson = new Gson();
		String resultString = gson.toJson(staffResponse);
		
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
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	                              ServletRequestDataBinder binder) throws Exception {  
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理   
	    binder.registerCustomEditor(Date.class, new DateEditor());  
	}

	/**
	 * 请求新增用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ModelAndView toAdd() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> positionList = staffSelectedService.getPositionList();
		List<Map<String, Object>> departmentList = staffSelectedService.getDepartmentList();
		List<Map<String, Object>> sortList = staffSelectedService.getStaffSortList();
		List<Map<String, Object>> roleList = staffSelectedService.getRoleList();

		
//		List<String> branchList = staffSelectedService.getBranchList();
		List<String> teamList =staffSelectedService.getTeamList();
//		List<String> leadList =staffSelectedService.getLeadList();
		StaffSelectInfo selectInfo = new StaffSelectInfo();
//		selectInfo.setBranchList(branchList);
		selectInfo.setDepartmentList(departmentList);
//		selectInfo.setLeadList(leadList);
		selectInfo.setPositionList(positionList);
		selectInfo.setTeamList(teamList);
		selectInfo.setSortList(sortList);
		selectInfo.setRoleList(roleList);
		mv.addObject("selectInfo", selectInfo);
		mv.setViewName("admin/staff/staffInfo");
		return mv;
	}
	
	/**
	 * 请求新增CT用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addCT")
	public ModelAndView toAddCT() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> positionList = staffSelectedService.getPositionListCT();
		List<Map<String, Object>> departmentList = staffSelectedService.getDepartmentListCT();
		List<Map<String, Object>> sortList = staffSelectedService.getStaffSortList();
		List<Map<String, Object>> roleList = staffSelectedService.getRoleList();
		List<Map<String, Object>> jobCategoryList = staffSelectedService.getJobCaegory();
		String department = (String) departmentList.get(0).get("department");
		List<Map<String, Object>> branchList = staffSelectedService.getBranchListCT(department);

		StaffSelectInfo selectInfo = new StaffSelectInfo();
		
		selectInfo.setDepartmentList(departmentList);
		selectInfo.setPositionList(positionList);
		selectInfo.setSortList(sortList);
		selectInfo.setRoleList(roleList);
		selectInfo.setJobCategoryList(jobCategoryList);
		mv.addObject("branchList",branchList);
		mv.addObject("selectInfo", selectInfo);
		mv.setViewName("admin/staff/staffInfoCT");
		return mv;
	}
	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(Staff staff) {
		ModelAndView mv = new ModelAndView();
		
		if (staff.getGender() == null ){
			staff.setGender("");
		}
		
		//设定员工类别
		staff.setSort(istaffService.getSort(staff.getSortID()));
		
		if (staff.getStaffID() == null || staff.getStaffID().intValue() == 0 ) {
			if (staff.getDateGraduation()==null || "".equals(staff.getDateGraduation())) {
				staff.setDateGraduation(null);
			}
			// 入职年月可以为空
			if (staff.getDateIntoCompany()==null || "".equals(staff.getDateIntoCompany())) {
				staff.setDateIntoCompany(null);
			}
			if (staff.getDateQuitCompany()==null || "".equals(staff.getDateQuitCompany())) {
				staff.setDateQuitCompany(null);
			}
			
			int staffID = istaffService.getStaffByJobNo(staff.getJobNo());
			if (staffID > 0){
				// 人员存在时更新人员信息,提示员工番号已存在
				mv.addObject("msg", "failedJobNo");
			} else {
				// 人员不存在时追加人员信息
				if (istaffService.insertStaff(staff) == false) {
					mv.addObject("msg", "failed");
				} else {
					mv.addObject("msg", "success");
				}
			}
		} else {
			if (staff.getDateGraduation()==null || "".equals(staff.getDateGraduation())) {
				staff.setDateGraduation(null);
			}
			// 入职年月可以为空
			if (staff.getDateIntoCompany()==null || "".equals(staff.getDateIntoCompany())) {
				staff.setDateIntoCompany(null);
			}
			if (staff.getDateQuitCompany()==null || "".equals(staff.getDateQuitCompany())) {
				staff.setDateQuitCompany(null);
			}
			istaffService.updateStaff(staff);
		}
		
		mv.setViewName("admin/save_result");
		return mv;
	}

	/**
	 * 请求用户详细页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/more")
	public ModelAndView getMore(@RequestParam int id,HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		Staff staff = istaffService.getStaffByIdCXEE(id);
		Staff staffInfo = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		mv.addObject("staffInfo", staffInfo);
		mv.addObject("staff", staff);
		mv.setViewName("admin/staff/detailed");
		return mv;
	}
	@RequestMapping(value = "/moreCT")
	public ModelAndView getMoreCT(@RequestParam int id,HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		Staff staff = istaffService.getStaffByIdCT(id);
		Staff staffInfo = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		mv.addObject("staffInfo", staffInfo);
		mv.addObject("staff", staff);
		mv.setViewName("admin/staff/detailedCT");
		return mv;
	}
	/**
	 * 按部门请求课别信息
	 * 
	 * @param department
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/listBranch")
	public void listBranch(@RequestParam String departmentID,HttpServletResponse response) throws IOException {
		List<Map<String, Object>> branchselectList=staffSelectedService.getBranchList(departmentID);
		List<String> leadselectList=staffSelectedService.getLeadList(departmentID);
		Gson gson = new Gson();
		String resultString = gson.toJson(branchselectList);
		String leadString = gson.toJson(leadselectList);
		String result ="{\"branch\":"+resultString+",\"lead\":"+leadString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	
	/**
	 * 按部门请求课别信息
	 * 
	 * @param department
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/checkEnrolement")
	public void checkEnrolement(@RequestParam String departmentID,@RequestParam String enrolementCode,@RequestParam String branchID,HttpServletResponse response) throws IOException {
		int countenrolementCode=staffSelectedService.countEnrolementCode(departmentID,enrolementCode,branchID);
		Gson gson = new Gson();
		String result ="{\"Count\":"+countenrolementCode+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	
	@RequestMapping(value = "/getBranchCXEE")
	public void getBranchCXEE(String departmentID,HttpServletResponse response,HttpSession httpSession) throws IOException {
		List<Map<String,Object>> branchselectList=staffSelectedService.getBranchListCXEE(departmentID);
		Gson gson = new Gson();
		String resultString = gson.toJson(branchselectList);
		
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	@RequestMapping(value = "/getBranchCT")
	public void getBranchCT(String departmentID,HttpServletResponse response,HttpSession httpSession) throws IOException {
		List<Map<String,Object>> branchselectList=staffSelectedService.getBranchListCT(departmentID);
		Gson gson = new Gson();
		String resultString = gson.toJson(branchselectList);
		
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	/**
	 * 请求编辑用户页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit(@RequestParam int id) {
		ModelAndView mv = new ModelAndView();
		Staff staff = istaffService.getStaffById(id);
		List<Map<String, Object>> positionList = staffSelectedService.getPositionList();
		List<Map<String, Object>> departmentList = staffSelectedService.getDepartmentList();
		List<Map<String, Object>> sortList = staffSelectedService.getStaffSortList();
		List<Map<String, Object>> roleList = staffSelectedService.getRoleList();
		List<Map<String, Object>> jobCategoryList = staffSelectedService.getJobCaegory();
		
//		List<String> branchList = staffSelectedService.getBranchList();
		List<String> teamList =staffSelectedService.getTeamList();
//		List<String> leadList =staffSelectedService.getLeadList();
		StaffSelectInfo selectInfo = new StaffSelectInfo();
//		selectInfo.setBranchList(branchList);
		selectInfo.setDepartmentList(departmentList);
//		selectInfo.setLeadList(leadList);
		selectInfo.setPositionList(positionList);
		selectInfo.setTeamList(teamList);
		selectInfo.setSortList(sortList);
		selectInfo.setRoleList(roleList);
		selectInfo.setJobCategoryList(jobCategoryList);
		mv.addObject("selectInfo", selectInfo);
		mv.addObject("staff", staff);
		if (staff.getEmail().endsWith("co.jp")){
			mv.setViewName("admin/staff/staffInfoCT");
		} else {
			mv.setViewName("admin/staff/staffInfo");
		}
		return mv;
	}

	/**
	 * 删除员工
	 * 
	 * @param id
	 * @param out
	 */
	@RequestMapping(value = "/staffDelete")
	public void deleteStaff(@RequestParam String ids, PrintWriter out) {
		String[] allId = ids.split(",");
		istaffService.deleteStaffs(allId);
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	
	/**
	 * 上传页面
	 */
	@RequestMapping(value = "/upload")
	public ModelAndView toUpload(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		Staff staffInfo = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		mv.addObject("staffInfo", staffInfo);
		mv.setViewName("admin/staff/staffUpload");
		return mv;
	}
	/**
	 * CT上传页面
	 */
	@RequestMapping(value = "/uploadCT")
	public ModelAndView toUploadCT(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		Staff staffInfo = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		mv.addObject("staffInfo", staffInfo);
		mv.setViewName("admin/staff/staffUploadCT");
		return mv;
	}
	/**
	 * 员工信息文件上传
	 */
	@RequestMapping(value = "/fileUpload",method=RequestMethod.POST)
	public void fileUpload(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		Staff staffInfo = (Staff) request.getSession().getAttribute(Const.SESSION_USER);
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
	            Staff uploadStaff = null;
	            int startRow = 2;
	            int startCell = 1;
	            List<Map<String, Object>> positionList = staffSelectedService.getPositionList();
	            List<Map<String, Object>> departmentList = staffSelectedService.getDepartmentList();
	            List<Map<String, Object>> roleList = staffSelectedService.getRoleList();
	            List<Map<String, Object>> sortList = staffSelectedService.getStaffSortList();
	            List<String> teamList =staffSelectedService.getTeamList();
	            List<StaffUploadResponse> errorStaffInfoList = new ArrayList<StaffUploadResponse>();
	            StaffUploadResponse errorStaffInfo;
	            String resultString = "";
	            String strPassword="";
	            String strJobNo="";
	            int staffID = 0;
	            int totalRowCount = 0;
	            
	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadStaff = new Staff();
	            	errorStaffInfo = new StaffUploadResponse();
	            	staffID = 0;
	            	//员工姓名
	            	if(checkTextCellAndRequired(row.getCell(1).toString().trim(),20)) {
	            		uploadStaff.setName(row.getCell(1).toString().trim());
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_staffName"));
	            		errorStaffInfo.setErrContent(row.getCell(1).toString());
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//密码
	            	if (row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
	            		DecimalFormat df = new DecimalFormat("0");
	            		strPassword = df.format(row.getCell(2).getNumericCellValue());
	            	} else {
	            		strPassword = row.getCell(2).toString().trim();
	            	}
	            	if(checkTextCellAndRequired(strPassword,20)) {
	            		uploadStaff.setPassword(strPassword);
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_password"));
	            		if(row.getCell(2).toString().isEmpty()){
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		} else {
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_overLength"));
	            		}
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//工号
	            	if (row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
	            		DecimalFormat df = new DecimalFormat("0");
	            		strJobNo = df.format(row.getCell(3).getNumericCellValue());
	            	} else {
	            		strJobNo = row.getCell(3).toString().trim();
	            	}
	            	if(checkTextCellAndRequired(strJobNo,20)) {
	            		uploadStaff.setJobNo(strJobNo);
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_jobNo"));
	            		if(row.getCell(3).toString().isEmpty()){
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		} else {
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_overLength"));
	            		}
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//性别
	            	if(checkTextCellAndRequired(row.getCell(4).toString(),2)) {
	            		uploadStaff.setGender(row.getCell(4).toString().trim());
	            	} else {
	            		uploadStaff.setGender("");
	            	}
	            	//毕业时间
	            	if(checkTextCellAndRequired(row.getCell(5).toString(),10)) {
	            		Date date = row.getCell(5).getDateCellValue();
	            		uploadStaff.setDateGraduation(dateFormat.format(date));
	            	} else {
	            		uploadStaff.setDateGraduation(null);
	            	}
	            	//进公司时间
					if(checkTextCellAndRequired(row.getCell(6).toString(),10)) {
						Date date = row.getCell(6).getDateCellValue();
						uploadStaff.setDateIntoCompany(dateFormat.format(date));
					} else {
						uploadStaff.setDateIntoCompany(null);
					}
	            	//职位
					if(row.getCell(7)!= null && !"".equals(row.getCell(7).toString().trim())) {
						boolean hasPosition = false;
	            		for (int j = 0 ; j < positionList.size(); j++){
	            			Map<String,Object> t = positionList.get(j);	            
            				if (row.getCell(7).toString().trim().equals(t.get("position"))){
            					uploadStaff.setPositionID(Integer.parseInt((t.get("positionID").toString())));
            					uploadStaff.setPosition(row.getCell(7).toString().trim());
            					hasPosition = true;
            					break;
            				}
	            		}
	            		if (!hasPosition){
	            			errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_position"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(7).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
	            		}
	            	} else {
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_position"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//能力别
					if(row.getCell(8) != null && !"".equals(row.getCell(8).toString().trim())) {
						if(!row.getCell(8).toString().trim().equals(AbilityEnum.CHIEF.getName()) &&
								!row.getCell(8).toString().trim().equals(AbilityEnum.DIRECTOR.getName()) && 
								!row.getCell(8).toString().trim().equals(AbilityEnum.ABOVE3YEARS.getName()) &&
								!row.getCell(8).toString().trim().equals(AbilityEnum.UNDER3YEARS.getName())){
							errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_memo"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(8).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						} else {
							uploadStaff.setMemo(row.getCell(8).toString().trim());
						}
	            	} else {
	            		uploadStaff.setMemo("");
	            	}
	            	//部门和课别
					if(row.getCell(9)!= null && !"".equals(row.getCell(9).toString().trim())) {
						boolean hasDepartment = false;
						boolean hasBranch = false;
						for (int j = 0 ; j < departmentList.size(); j++){
	            			Map<String,Object> t = departmentList.get(j);	            
            				if (row.getCell(9).toString().trim().equals(t.get("department"))){
            					uploadStaff.setDepartmentID(Integer.parseInt((t.get("departmentID").toString())));
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
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(9).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						}
						if (!hasBranch){
							errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_branch"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(10).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						}
	            	} else {
	            		/*//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_department"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;*/
	            		uploadStaff.setDepartmentID(0);
	            		uploadStaff.setDepartment("");
	            	}
	            	
	            	//组别
					if(row.getCell(11) != null &&  !"".equals(row.getCell(11).toString().trim())) {
	            		boolean hasTeam = false;
	            		for (String team : teamList){
	            			if (row.getCell(11).toString().trim().equals(team)){
	            				uploadStaff.setTeam(row.getCell(11).toString().trim());
	            				hasTeam = true;
	            				break;
	            			}
	            		}
	            		if (!hasTeam){
	            			errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_team"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(11).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
	            		}
	            	} else {
	            		uploadStaff.setTeam("");
	            	}
	            	//上司
					if(row.getCell(12) != null &&  !"".equals(row.getCell(12).toString().trim())) {
	            		uploadStaff.setSuperior(row.getCell(12).toString().trim());
	            	} else {
	            		uploadStaff.setSuperior("");
	            	}
	            	//email
					if(row.getCell(13) != null && !"".equals(row.getCell(13).toString().trim()) && 
							(row.getCell(13).toString().trim().endsWith("clarion.com.cn")||row.getCell(13).toString().trim().endsWith("clariondcoe.com.cn")
									||row.getCell(13).toString().trim().endsWith("clarionchina.com")||row.getCell(13).toString().trim().endsWith("clarionchi.com.hk"))) {
						uploadStaff.setEmail(row.getCell(13).toString().trim());
	            	} else {
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem("e-mail");
	            		if("".equals(row.getCell(13).toString().trim())){
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		}else{
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_mail"));
	            		}
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//在职状态
					if(row.getCell(14) != null &&  !"".equals(row.getCell(14).toString().trim())) {
						if (row.getCell(14).toString().trim().equals(StateEnum.ONJOB.getName())){
							uploadStaff.setState(StateEnum.ONJOB.getId());
						} else if(row.getCell(14).toString().trim().equals(StateEnum.VACATION.getName())){
							uploadStaff.setState(StateEnum.VACATION.getId());
						} else if (row.getCell(14).toString().trim().equals(StateEnum.DEMISSION.getName())){
							uploadStaff.setState(StateEnum.DEMISSION.getId());
						} else {
							errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_state"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(14).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_state"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
					//离职日
	            	if(checkTextCellAndRequired(row.getCell(15).toString(),10)) {
	            		Date date = row.getCell(15).getDateCellValue();
	            		uploadStaff.setDateQuitCompany(dateFormat.format(date));
	            	} else {
	            		uploadStaff.setDateQuitCompany(null);
	            	}
	            	//角色权限
					if(row.getCell(16) != null &&  !"".equals(row.getCell(16).toString().trim())) {
						/*if (row.getCell(16).toString().trim().equals(RoleEnum.NORMAL.getName())){
							uploadStaff.setRole(String.valueOf(RoleEnum.NORMAL.getId()));
						} else if(row.getCell(16).toString().trim().equals(RoleEnum.SUPER.getName())){
							uploadStaff.setRole(String.valueOf(RoleEnum.SUPER.getId()));
						} else if (row.getCell(16).toString().trim().equals(RoleEnum.CHIEF.getName())){
							uploadStaff.setRole(String.valueOf(RoleEnum.CHIEF.getId()));
						} else if (row.getCell(16).toString().trim().equals(RoleEnum.WORKNUM.getName())){
							uploadStaff.setRole(String.valueOf(RoleEnum.WORKNUM.getId()));
						} else {
							errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_role"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(16).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						}*/
						
						boolean hasRole = false;
	            		for (int j = 0 ; j < roleList.size(); j++){
	            			Map<String,Object> t = roleList.get(j);	            
            				if (row.getCell(16).toString().trim().equals(t.get("roleName"))){
            					uploadStaff.setURKeyCode(t.get("roleID").toString());
            					hasRole = true;
            					break;
            				}
	            		}
	            		if (!hasRole){
	            			errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_role"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(16).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
	            		}
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_role"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//员工类别
					if(row.getCell(17) != null &&  !"".equals(row.getCell(17).toString().trim())) {
						boolean hasSort = false;
	            		for (int j = 0 ; j < sortList.size(); j++){
	            			Map<String,Object> t = sortList.get(j);	            
            				if (row.getCell(17).toString().trim().equals(t.get("sort"))){
            					uploadStaff.setSortID(t.get("sortID").toString());
            					uploadStaff.setSort(row.getCell(17).toString().trim());
            					hasSort = true;
            					break;
            				}
	            		}
	            		if (!hasSort){
	            			errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_sort"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(17).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
	            		}
	            	} else {
	            		uploadStaff.setSort("");
	            	}
					//公司名称
					if(row.getCell(18) != null &&  !"".equals(row.getCell(18).toString().trim())) {
	            		uploadStaff.setCompanyName(row.getCell(18).toString().trim());
	            	} else {
	            		uploadStaff.setCompanyName("");
	            	}
					//备注
					if(row.getCell(19) != null &&  !"".equals(row.getCell(19).toString().trim())) {
	            		uploadStaff.setComment(row.getCell(19).toString().trim());
	            	} else {
	            		uploadStaff.setComment("");
	            	}
					
					//staffID = istaffService.getStaffByEmail(uploadStaff.getEmail());
					staffID = istaffService.getStaffByJobNo(uploadStaff.getJobNo());
					if (staffID > 0){
						// 人员存在时更新人员信息
						uploadStaff.setStaffID(staffID);
						istaffService.updateStaff(uploadStaff);
					} else {
						// 人员不存在时追加人员信息
						istaffService.insertStaff(uploadStaff);
					}
	            }
	            
	            filestream.close();

	            Gson gson = new Gson();
				resultString = gson.toJson(errorStaffInfoList);
				int totalErrorCount = errorStaffInfoList.size();
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
	 * 员工信息文件上传
	 */
	@RequestMapping(value = "/fileUploadCT",method=RequestMethod.POST)
	public void fileUploadCT(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		Staff staffInfo = (Staff) request.getSession().getAttribute(Const.SESSION_USER);
		HttpSession session = request.getSession();
		try {
			// 转型为MultipartHttpRequest：  
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            // 获得文件：  
            MultipartFile file = multipartRequest.getFile("file");

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
	            Staff uploadStaff = null;
	            int startRow = 2;
	            int startCell = 1;
	            List<Map<String, Object>> positionList = staffSelectedService.getPositionList();
	            List<Map<String, Object>> departmentList = staffSelectedService.getDepartmentList();
	            List<Map<String, Object>> sortList = staffSelectedService.getStaffSortList();
	            List<Map<String, Object>> roleList = staffSelectedService.getRoleList();

	            List<StaffUploadResponse> errorStaffInfoList = new ArrayList<StaffUploadResponse>();
	            StaffUploadResponse errorStaffInfo;
	            String resultString = "";
	            String strPassword = "";
	            String strJobNo = "";
	            int staffID = 0;
	            int totalRowCount = 0;
	            boolean checkflag=false;
	            row = sheet.getRow(startRow-1);
	            //check 模板
	            if(row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            	errorStaffInfo = new StaffUploadResponse();
            		errorStaffInfo.setErrNo("0");
            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_excel"));
            		errorStaffInfo.setErrContent(Tools.getPropertiesValue(session, "staffUpload_string_msg_default"));
            		errorStaffInfoList.add(errorStaffInfo);
	            }
	            if(row.getCell(0).toString().indexOf("顺番")<0||
	            		row.getCell(1).toString().indexOf("氏名")<0||
	            		row.getCell(2).toString().indexOf("従業員番号")<0||
	            		row.getCell(3).toString().indexOf("パスワード")<0||
	            		row.getCell(4).toString().indexOf("役職")<0||
	            		row.getCell(5).toString().indexOf("部")<0||
	            		row.getCell(6).toString().indexOf("グループ")<0||
	            		row.getCell(7).toString().indexOf("在籍所属コード")<0||
	            		row.getCell(8).toString().indexOf("E-MAIL")<0||
	            		row.getCell(9).toString().indexOf("在職状況")<0||
	            		row.getCell(10).toString().indexOf("ロール権限")<0||
	            		row.getCell(11).toString().indexOf("社員区分")<0||
	            		row.getCell(12).toString().indexOf("社員区分ID")<0||
	            		row.getCell(13).toString().indexOf("会社名称")<0||
	            		row.getCell(14).toString().indexOf("職種")<0||
	            		row.getCell(15).toString().indexOf("設計有資格")<0||
	            		row.getCell(16).toString().indexOf("PMレベル")<0||
	            		row.getCell(17).toString().indexOf("特記事項")<0){
	            	checkflag=false;
	            	errorStaffInfo = new StaffUploadResponse();
            		errorStaffInfo.setErrNo("0");
            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_excel"));
            		errorStaffInfo.setErrContent(Tools.getPropertiesValue(session, "staffUpload_string_msg_default"));
            		errorStaffInfoList.add(errorStaffInfo);
	            }else{
	            	checkflag=true;
	            }
	            if(checkflag){
	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadStaff = new Staff();
	            	errorStaffInfo = new StaffUploadResponse();
	            	staffID = 0;
	            	//通用设定
	            	uploadStaff.setGender("");
	            	//员工姓名
	            	if(checkTextCellAndRequired(row.getCell(1).toString().trim(),20)) {
	            		uploadStaff.setName(row.getCell(1).toString().trim());
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_staffName"));
	            		errorStaffInfo.setErrContent(row.getCell(1).toString());
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//工号
	            	if (row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
	            		DecimalFormat df = new DecimalFormat("0");
	            		strJobNo = df.format(row.getCell(2).getNumericCellValue());
	            	} else {
	            		strJobNo = row.getCell(2).toString().trim();
	            	}
	            	if(checkTextCellAndRequired(strJobNo,20)) {
	            		uploadStaff.setJobNo(strJobNo);
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_jobNo"));
	            		if(row.getCell(2).toString().isEmpty()){
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		} else {
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_overLength"));
	            		}
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//密码
	            	if (row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
	            		DecimalFormat df = new DecimalFormat("0");
	            		strPassword = df.format(row.getCell(3).getNumericCellValue());
	            	} else {
	            		strPassword = row.getCell(3).toString().trim();
	            	}
	            	if(checkTextCellAndRequired(strPassword,20)) {
	            		uploadStaff.setPassword(strPassword);
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_password"));
	            		if(row.getCell(3).toString().isEmpty()){
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		} else {
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_overLength"));
	            		}
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	
	            	//职位
					if(row.getCell(4)!= null && !"".equals(row.getCell(4).toString().trim())) {
						boolean hasPosition = false;
	            		for (int j = 0 ; j < positionList.size(); j++){
	            			Map<String,Object> t = positionList.get(j);	            
            				if (row.getCell(4).toString().trim().equals(t.get("position"))){
            					uploadStaff.setPositionID(Integer.parseInt((t.get("positionID").toString())));
            					uploadStaff.setPosition(row.getCell(4).toString().trim());
            					hasPosition = true;
            					break;
            				}
	            		}
	            		if (!hasPosition){
	            			errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_position"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(4).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
	            		}
	            	} else {
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_position"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	
	            	//部门和课别
					if(row.getCell(5)!= null && !"".equals(row.getCell(5).toString().trim())) {
						boolean hasDepartment = false;
						boolean hasBranch = false;
						for (int j = 0 ; j < departmentList.size(); j++){
	            			Map<String,Object> t = departmentList.get(j);	            
            				if (row.getCell(5).toString().trim().equals(t.get("department"))){
            					uploadStaff.setDepartmentID(Integer.parseInt((t.get("departmentID").toString())));
            					uploadStaff.setDepartment(row.getCell(5).toString().trim());
            					hasDepartment = true;
            					//课别
            					if(row.getCell(6)!= null && !"".equals(row.getCell(6).toString().trim())) {
            						List<Map<String, Object>> branchselectList=staffSelectedService.getBranchList(t.get("departmentID").toString());
            						for (int k=0; k < branchselectList.size(); k++){
            							Map<String,Object> branchMap = branchselectList.get(k);
            							if(row.getCell(6).toString().trim().equals(branchMap.get("branch"))){
            								uploadStaff.setBranchID(Integer.parseInt(branchMap.get("branchID").toString()));
            								uploadStaff.setBranch(row.getCell(6).toString().trim());
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
	            		/*errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_department"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;*/
	            		uploadStaff.setDepartment("");
	            		uploadStaff.setDepartmentID(0);
	            		
	            	}
	            	
	            	//在籍所属コード
					if(row.getCell(7) != null &&  !"".equals(row.getCell(7).toString().trim())) {
	            		uploadStaff.setEnrolementCode(row.getCell(7).toString().trim());
	            	} else {
	            		uploadStaff.setEnrolementCode("");
	            	}
	            	//email
					if(row.getCell(8) != null && !"".equals(row.getCell(8).toString().trim()) && row.getCell(8).toString().trim().endsWith("clarion.co.jp")) {
						uploadStaff.setEmail(row.getCell(8).toString().trim());
	            	} else {
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem("e-mail");
	            		if("".equals(row.getCell(8).toString().trim())){
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		}else{
	            			errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_mail"));
	            		}
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//在职状态
					if(row.getCell(9) != null &&  !"".equals(row.getCell(9).toString().trim())) {
						if (row.getCell(9).toString().trim().equals(StateEnumCT.ONJOB.getName())){
							uploadStaff.setState(StateEnumCT.ONJOB.getId());
						} else if(row.getCell(9).toString().trim().equals(StateEnumCT.VACATION.getName())){
							uploadStaff.setState(StateEnumCT.VACATION.getId());
						} else if (row.getCell(9).toString().trim().equals(StateEnumCT.DEMISSION.getName())){
							uploadStaff.setState(StateEnumCT.DEMISSION.getId());
						} else if (row.getCell(9).toString().trim().equals(StateEnumCT.MOBILE.getName())){
								uploadStaff.setState(StateEnumCT.MOBILE.getId());
						}
						 else {
							errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_state"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(9).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_state"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
	            	//角色权限
					if(row.getCell(10) != null &&  !"".equals(row.getCell(10).toString().trim())) {
						boolean hasRole = false;
	            		for (int j = 0 ; j < roleList.size(); j++){
	            			Map<String,Object> t = roleList.get(j);	            
            				if (row.getCell(10).toString().trim().equals(t.get("roleName"))){
            					uploadStaff.setURKeyCode(t.get("roleID").toString());
            					hasRole = true;
            					break;
            				}
	            		}
	            		if (!hasRole){
	            			errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_role"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(10).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
	            		}
	            	} else {
	            		//出错时处理
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_role"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}

	            	//员工类别
					if(row.getCell(11) != null &&  !"".equals(row.getCell(11).toString().trim())) {
						boolean hasSort = false;
	            		for (int j = 0 ; j < sortList.size(); j++){
	            			Map<String,Object> t = sortList.get(j);	            
            				if (row.getCell(11).toString().trim().equals(t.get("sort"))){
            					uploadStaff.setSortID(t.get("sortID").toString());
            					uploadStaff.setSort(row.getCell(11).toString().trim());
            					hasSort = true;
            					break;
            				}
	            		}
	            		if (!hasSort){
	            			errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_sort"));
		            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(11).toString()));
		            		errorStaffInfoList.add(errorStaffInfo);
		            		continue;
	            		}
						
	            	} else {
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_sort"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
	            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
					//会社名称
					if(row.getCell(13) != null &&  !"".equals(row.getCell(13).toString().trim())) {
	            		uploadStaff.setCompanyName(row.getCell(13).toString().trim());
	            	} else {
	            		uploadStaff.setCompanyName("");
	            	}
					//職種
					if(row.getCell(14) != null &&  !"".equals(row.getCell(14).toString().trim())) {
	            		uploadStaff.setJobCategory(row.getCell(14).toString().trim());
	            	} else {
	            		errorStaffInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorStaffInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_jobCategory"));
	            		errorStaffInfo.setErrContent(errorStaffInfo.getErrItem()
	            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorStaffInfoList.add(errorStaffInfo);
	            		continue;
	            	}
					//設計有資格総合判定
					if(row.getCell(15) != null &&  !"".equals(row.getCell(15).toString().trim())) {
	            		uploadStaff.setDesignQualified(row.getCell(15).toString().trim());
	            	} else {
	            		uploadStaff.setDesignQualified("");
	            	}
					//PMレベル
					if(row.getCell(16) != null &&  !"".equals(row.getCell(16).toString().trim())) {
	            		uploadStaff.setPmLevel(row.getCell(16).toString().trim());
	            	} else {
	            		uploadStaff.setPmLevel("");
	            	}
					//特記事項
					if(row.getCell(17) != null &&  !"".equals(row.getCell(17).toString().trim())) {
	            		uploadStaff.setComment(row.getCell(17).toString().trim());
	            	} else {
	            		uploadStaff.setComment("");
	            	}
					
					staffID = istaffService.getStaffByJobNo(uploadStaff.getJobNo());
					if (staffID > 0){
						// 人员存在时更新人员信息
						uploadStaff.setStaffID(staffID);
						istaffService.updateStaff(uploadStaff);
					} else {
						// 人员不存在时追加人员信息
						istaffService.insertStaff(uploadStaff);
					}
	            }
	            }
	            filestream.close();

	            Gson gson = new Gson();
				resultString = gson.toJson(errorStaffInfoList);
				int totalErrorCount = errorStaffInfoList.size();
				String result ="";
				if(checkflag==false){
					totalRowCount =1;
				}
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
	
	private boolean checkTextCellAndRequired(String value,int length){	
		if(value == null || "".equals(value)){
			return false;
		}
		if(value.length() > length){
			return false;
		}
		return true;
	}
}
