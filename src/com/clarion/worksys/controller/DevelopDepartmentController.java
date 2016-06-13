package com.clarion.worksys.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.DepartmentUploadResponse;
import com.clarion.worksys.entity.DevelopDepartment;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.httpentity.DevelopDepartmentReqParam;
import com.clarion.worksys.httpentity.DevelopDepartmentResponse;
import com.clarion.worksys.httpentity.DevelopDepartmentResponseRows;
import com.clarion.worksys.service.DevelopDepartmentService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/developDepartment")
public class DevelopDepartmentController {

	@Autowired
	private DevelopDepartmentService developDepartmentService;
	@Autowired
	private UserRoleManageService userRoleManageService;
	
	
	@RequestMapping(value = "/getBranchCXEE")
	public void getBranchCXEE(String departmentID,HttpServletResponse response,HttpSession httpSession) throws IOException {
		List<Map<String, Object>> branchselectList=developDepartmentService.getBranchListCXEE(departmentID);
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
		List<Map<String, Object>> branchselectList=developDepartmentService.getBranchListCT(departmentID);
		Gson gson = new Gson();
		String resultString = gson.toJson(branchselectList);
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	@RequestMapping(value = "/changeDepartment",method=RequestMethod.POST)
	public void changeDepartmentCT(HttpServletResponse response,HttpSession httpSession) throws IOException {
		List<Map<String, Object>> departmentList = developDepartmentService.getDepartmentListCT();
		Gson gson = new Gson();
		String resultString = gson.toJson(departmentList);
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	@RequestMapping
	public ModelAndView showDefault(HttpSession session) {

	//	Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);

		List<Map<String, Object>> departmentList = null;
	//	if(staff.getEmail().indexOf(".co.jp")>0){
		//	departmentList = developDepartmentService.getDepartmentListCT();
		//}else{
			departmentList = developDepartmentService.getDepartmentList();
	//	}
		List<Map<String, Object>> departmentCategoryList = developDepartmentService.getDepartmentCategoryList();
		//UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"DepartmentManage",userRoleManageService);

		ModelAndView mv = new ModelAndView();
		mv.addObject("departmentList", departmentList);
	//	mv.addObject("staff", staff);
		mv.addObject("departmentCategoryList", departmentCategoryList);
	//	mv.addObject("QueryRoleFlag", userRole.getQueryRoleFlag());//検索
	//	mv.addObject("AlterRoleFlag", userRole.getAlterRoleFlag());//編集
	//	mv.addObject("UploadRoleFlag", userRole.getUploadRoleFlag());//U/L
	//	mv.addObject("DownloadRoleFlag", userRole.getDownloadRoleFlag());//D/L
	//	mv.addObject("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
	//	mv.addObject("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
	//	mv.addObject("DepartmentFlag", userRole.getDepartmentFlag());//部门权限
		mv.setViewName("admin/developDepartment/developDepartment");
		return mv;

	}

	@RequestMapping(value = "/showAll")
	public void showAll(HttpSession session, HttpServletResponse response,DevelopDepartmentReqParam developDepartmentReqParam) throws IOException {
		if("1".equals(developDepartmentReqParam.getQueryRoleFlag())){
			// 设置数据库查询时从哪条数据开始
			developDepartmentReqParam.setPageSeq((developDepartmentReqParam.getPage() - 1) * developDepartmentReqParam.getRp());
			// 如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
			if ("".equals(developDepartmentReqParam.getDepartment())) {
				developDepartmentReqParam.setDepartment(null);
			}

			if ("".equals(developDepartmentReqParam.getBranch())) {
				developDepartmentReqParam.setBranch(null);
			}

			if ("".equals(developDepartmentReqParam.getDepartmentCategory())) {
				developDepartmentReqParam.setDepartmentCategory(null);
			}

			List<DevelopDepartment> searchList = developDepartmentService.searchList(developDepartmentReqParam);
			
			
			int totalPageCount = developDepartmentService.totalPageCount(developDepartmentReqParam);

			List<DevelopDepartmentResponseRows> developDepartmentResponseList = new ArrayList<DevelopDepartmentResponseRows>();
			for(int j = 0; j < searchList.size(); j++){
				DevelopDepartmentResponseRows developDepartmentResponseRows = new DevelopDepartmentResponseRows(j,searchList.get(j));
				if (developDepartmentResponseRows.getCell().getDepartmentCategory() == null) {
					developDepartmentResponseRows.getCell().setDepartmentCategory("");
				}
				
				if (developDepartmentResponseRows.getCell().getBranch() == null) {
					developDepartmentResponseRows.getCell().setBranch("");
				}
				if (developDepartmentResponseRows.getCell().getBranchID() == null) {
					developDepartmentResponseRows.getCell().setBranchID("");
				}
				if (developDepartmentResponseRows.getCell().getTeam() == null) {
					developDepartmentResponseRows.getCell().setTeam("");
				}
				if (developDepartmentResponseRows.getCell().getBranchMemo() == null) {
					developDepartmentResponseRows.getCell().setBranchMemo("");
				}
				
				Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
				if (staff.getEmail().indexOf(".co.jp") > 0) {
					developDepartmentResponseRows.getCell().setBelong("CT");
				} else {
					developDepartmentResponseRows.getCell().setBelong("CXEE");
				}
				developDepartmentResponseList.add(developDepartmentResponseRows);
			}
			DevelopDepartmentResponse developDepartmentResponse = new DevelopDepartmentResponse();
			developDepartmentResponse.setPage(developDepartmentReqParam.getPage());
			developDepartmentResponse.setTotal(totalPageCount);
			developDepartmentResponse.setRows(developDepartmentResponseList);
			Gson gson = new Gson();
			String resultString = gson.toJson(developDepartmentResponse);
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

	@RequestMapping(value = "/showAllCT")
	public void showAllCT(HttpSession session, HttpServletResponse response,
			DevelopDepartmentReqParam developDepartmentReqParam) throws IOException {
		if("1".equals(developDepartmentReqParam.getQueryRoleFlag())){
			// 设置数据库查询时从哪条数据开始
			developDepartmentReqParam.setPageSeq((developDepartmentReqParam.getPage() - 1) * developDepartmentReqParam.getRp());
			// 如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
			if ("".equals(developDepartmentReqParam.getBelongCode())) {
				developDepartmentReqParam.setBelongCode(null);
			}
			if ("".equals(developDepartmentReqParam.getDepartment())) {
				developDepartmentReqParam.setDepartment(null);
			}

			if ("".equals(developDepartmentReqParam.getBranch())) {
				developDepartmentReqParam.setBranch(null);
			}

			List<DevelopDepartment> searchList = developDepartmentService.searchListCT(developDepartmentReqParam);
			int totalPageCount = developDepartmentService.totalPageCountCT(developDepartmentReqParam);

			
			List<DevelopDepartmentResponseRows> developDepartmentResponseList = new ArrayList<DevelopDepartmentResponseRows>();
			for (int i = 0; i < searchList.size(); i++) {
				DevelopDepartmentResponseRows developDepartmentResponseRows = new DevelopDepartmentResponseRows(i,
						searchList.get(i));
				if(developDepartmentResponseRows.getCell().getBranchDeployment().equals("1")){
					
					developDepartmentResponseRows.getCell().setBranchDeployment("現");
				}else if(developDepartmentResponseRows.getCell().getBranchDeployment().equals("0") ){
					developDepartmentResponseRows.getCell().setBranchDeployment("旧");
				}
				
				if (developDepartmentResponseRows.getCell().getBranch() == null) {
					developDepartmentResponseRows.getCell().setBranch("");
				}
				
				if (developDepartmentResponseRows.getCell().getBranchMemo() == null) {
					developDepartmentResponseRows.getCell().setBranchMemo("");
				}
				if(developDepartmentResponseRows.getCell().getBelongCode() == null){
					developDepartmentResponseRows.getCell().setBelongCode("");
				}
				Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
				if (staff.getEmail().indexOf(".co.jp") > 0) {
					developDepartmentResponseRows.getCell().setBelong("CT");
				} else {
					developDepartmentResponseRows.getCell().setBelong("CXEE");
				}
				developDepartmentResponseList.add(developDepartmentResponseRows);
			}
			DevelopDepartmentResponse developDepartmentResponse = new DevelopDepartmentResponse();
			developDepartmentResponse.setPage(developDepartmentReqParam.getPage());
			developDepartmentResponse.setTotal(totalPageCount);
			developDepartmentResponse.setRows(developDepartmentResponseList);
			Gson gson = new Gson();
			String resultString = gson.toJson(developDepartmentResponse);
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
	@RequestMapping(value = "/add")
	public ModelAndView addDevelopDepartment(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/developDepartment/developDepartmentAdd");
		return mv;
	}
	@RequestMapping(value = "/addCT")
	public ModelAndView addDevelopDepartmentCT(){
		
		
		ModelAndView mv = new ModelAndView();
		List<Integer> deploymentList = developDepartmentService.getDeploymentList();
		List<String> deploymentStr = new ArrayList<String>();
		for(int i = 0 ; i < deploymentList.size(); i++){
			Integer deployment = deploymentList.get(i);
			if(deployment == 1){
				deploymentStr .add("現");
			}else{
				deploymentStr .add("旧");
			}
		}
		
		mv.addObject("deployment", deploymentStr);
		mv.setViewName("admin/developDepartment/developDepartmentAddCT");
		return mv;
		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(DevelopDepartment developDepartment) {
			
			ModelAndView mv = new ModelAndView();
			if ("".equals(developDepartment.getDepartment().trim()) || developDepartment.getDepartment().trim() == null ) {
				mv.addObject("msg","failedNoDepartment");//输入部门名
			}
			/*if ("".equals(developDepartment.getBranch().trim())) {
				mv.addObject("msg","failedNoBranch");//输入课别名
			}*/
			
			if("".equals(developDepartment.getDepartmentCategory())){
				developDepartment.setDepartmentCategory(null);
			}
			if("".equals(developDepartment.getBranchMemo())){
				developDepartment.setBranchMemo(null);
			}
			
			String departmentID="";
			if(!developDepartment.getDepartment().trim() .equals("") && developDepartment.getDepartment().trim() != null){
				 departmentID=developDepartmentService.getDepartmentID(developDepartment.getDepartment().trim());
				 if(departmentID == null || departmentID.equals("")){
					//部门不存在，新增部门，课别
					 developDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentID()+1));
					 developDepartmentService.insertNewDepartment(developDepartment);
					 departmentID = String.valueOf(developDepartmentService.getMaxDepartmentID());
				 }
			} 
			//String branchID = developDepartmentService.getBranchID(developDepartment.getBranch());
			String departmentIDOld = developDepartment.getDepartmentIDOld();
			/*if(departmentID .equals("") && departmentID != null){
				//部门不存在，新增部门，课别
				developDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentID()));
				developDepartmentService.insertNewDepartment(developDepartment);
				departmentID = developDepartment.getDepartmentID();
			}*/
			
			if(developDepartment.getType().trim() .equals("edit")){
				//edit;
				String branchOld = developDepartment.getBranchOld();
				//String branchIDOld = developDepartment.getBranchIDOld();
				String branch = developDepartment.getBranch();
				if(!branchOld.trim().equals("")){
					if(departmentID.equals(departmentIDOld) && branch.equals(branchOld)){
						//部门、课别都没有改变
						developDepartmentService.updateDepartment(developDepartment);
						developDepartmentService.updateBranch(developDepartment);
					}else if(departmentID.equals(departmentIDOld) && !branch.equals(branchOld)){
						//课别名改变时
						developDepartment.setBranchID(developDepartmentService.getBranchID(developDepartment.getBranch()));
						developDepartmentService.updateBranch(developDepartment);
					}else if(!departmentID.equals(departmentIDOld) && !branch.equals(branchOld)){
						//部门、课别都改变时
						developDepartment.setDepartmentID(departmentID);
						developDepartment.setBranchID(developDepartmentService.getBranchID(developDepartment.getBranch()));
						developDepartmentService.updateBranch(developDepartment);
						if(!developDepartmentService.checkBranchByDeparmentID(departmentIDOld)){
							developDepartmentService.deleteDepartmentByID(departmentIDOld);
						}
					}else if(!departmentID.equals(departmentIDOld) && branch.equals(branchOld)){
						//部门名改变时
						developDepartment.setDepartmentID(departmentID);
						developDepartmentService.updateBranch(developDepartment);
						if(!developDepartmentService.checkBranchByDeparmentID(departmentIDOld)){
							developDepartmentService.deleteDepartmentByID(departmentIDOld);
						}
					}
				}else{
					//编辑了课别为空的记录时，插入课别表
					/*if( !developDepartment.getBranch().trim().equals("") || !developDepartment.getTeam().trim().equals("")){
						developDepartment.setDepartmentID(String.valueOf(developDepartment.getDepartmentIDOld()));
						developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID()+1));
						developDepartmentService.insertNewDepartmentBranch(developDepartment);
					}else {*/
					developDepartment.setBelongCodeOld(developDepartment.getBelongCode());
					developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID()+1));
					developDepartmentService.updateBranch(developDepartment);
					developDepartmentService.updateDepartment(developDepartment);
					//}

				}
				
			}else if(developDepartment.getType().trim() == null || ("").equals(developDepartment.getType().trim())){
				//add;
				String branchs="";
				if(developDepartment.getBranch().trim() != null){
					branchs = developDepartment.getBranch().trim();
				}
				String teams="";
				if(developDepartment.getTeam() .trim() != null){
					teams = developDepartment.getTeam().trim();
				}
				
				//添加insert
				if(!departmentID .equals("")){
					//该部门已存在
					developDepartment.setDepartmentID(departmentID);
					
					Random r = new Random();
					int b = r.nextInt();
					if(b<0){b=-b;}
					developDepartment.setBelongCode(String.valueOf(b));
					
					if(!branchs .equals("") && !teams .equals("") ){
						if(branchs.indexOf(",") > 0 && teams.indexOf(",") > 0 ){
							mv.addObject("msg","failedManyBranchTeam");
						}else if((branchs.indexOf(",") > 0) && (teams.indexOf(",") == -1)){
							String[] branchList =  branchs.split(",");
							for(int i = 0 ; i < branchList.length; i++){
								developDepartment.setBranch(branchList[i]);
								developDepartmentService.insertNewDepartmentBranch(developDepartment);
							}
						}else if((branchs.indexOf(",") == -1) && (teams.indexOf(",") > 0 )){
							if(developDepartmentService.getBranchID(developDepartment.getBranch()) != null ){
								String[] teamList =  teams.split(",");
								for(int i = 0 ; i < teamList.length; i++){
									developDepartment.setTeam(teamList[i]);
									String team = developDepartmentService.getTeam(developDepartment.getTeam());
									if(team != null){
										mv.addObject("msg","failed");
									}else{
										developDepartmentService.insertNewDepartmentBranch(developDepartment);
									}
								}
							}else{
								developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID()+1));
								String[] teamList =  teams.split(",");
								for(int i = 0 ; i < teamList.length; i++){
									developDepartment.setTeam(teamList[i]);
									String team = developDepartmentService.getTeam(developDepartment.getTeam());
									if(team != null){
										mv.addObject("msg","failed");
									}else{
										developDepartmentService.insertNewDepartmentBranch(developDepartment);
									}
								}
							}
						}else if((branchs.indexOf(",") == -1) && (teams.indexOf(",") == -1 )){
							developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
							developDepartmentService.insertNewDepartmentBranch(developDepartment);
						}
						
					}else if(!branchs.equals("") && teams .equals("")){
						if(branchs.indexOf(",") > 0 ){
							String[] branchList =  branchs.split(",");
							for(int i = 0 ; i < branchList.length; i++){
								developDepartment.setBranch(branchList[i]);
								String branch = developDepartmentService.getBranch(developDepartment.getBranch());
								if(branch != null){
									mv.addObject("msg","failed");	//课别已存在；
								}else{
									developDepartmentService.insertNewDepartmentBranch(developDepartment);
								}
							}
						}else if(branchs.indexOf(",") == -1){
							developDepartment.setBranchID(developDepartmentService.getBranchID(branchs));
							developDepartmentService.updateBranch(developDepartment);
						}
					}else if(branchs .equals("") && teams .equals("")){
						//mv.addObject("msg","failedOnlyDepartment");//只有部门，不能新增
						developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
						developDepartmentService.insertNewDepartmentBranch(developDepartment);
					}
				}else {
						mv.addObject("msg","failed");//没有输入部门
					}
			}
			mv.setViewName("admin/developDepartment/save_result");
			return mv;
			
			
	}
	@RequestMapping(value = "/saveCT", method = RequestMethod.POST)
	public ModelAndView saveCT(DevelopDepartment developDepartment) {
			
			ModelAndView mv = new ModelAndView();
			
			if("".equals(developDepartment.getBranchMemo().trim())){
				developDepartment.setBranchMemo(null);
			}
			String departmentID = developDepartmentService.getDepartmentIDCT(developDepartment.getDepartment());
			//String branchID = developDepartmentService.getBranchIDCT(developDepartment.getBelongCode());
			
			//String belongCode = developDepartment.getBelongCode();
			/*if(developDepartment.getDeployment() .equals("現")){
				developDepartment.setDeployment("1");
			}else{
				developDepartment.setDeployment("0");
			}*/
			if(developDepartment.getType().trim() .equals("edit")){
				//edit;
				/*DevelopDepartment developDepartments = developDepartmentService.getDevelopDepartmentSelectedCT(developDepartment.getBelongCodeOld(),developDepartment.getDepartmentIDOld(),developDepartment.getBranchIDOld());
				developDepartment.setBelongCodeOld(developDepartments.getBelongCode());
				developDepartment.setDepartmentIDOld(developDepartments.getDepartmentID());
				developDepartment.setBranchIDOld(developDepartments.getBranchID());*/
				if(!developDepartment.getBelongCode().equals(developDepartment.getBelongCodeOld())){
					//belongCode
					if(departmentID == null){
						//编辑了部门并且增加了新部门
						developDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentIDCT()));
						developDepartmentService.insertNewDepartmentCT(developDepartment);
						if(!developDepartment.getBranch().equals(developDepartment.getBranchOld())){
							//编辑了课别
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							String branchIDs=developDepartmentService.getBranchIDByBranchCT(developDepartment.getBranch());
							if(branchIDs!=null){
								developDepartment.setBranchID(branchIDs);
							}else{
								developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
							}
							developDepartmentService.updateBranchCT(developDepartment);
						}else{
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							developDepartmentService.updateBranchCT(developDepartment);
						}
					}else if (!departmentID.equals(developDepartment.getDepartmentIDOld())){
						//编辑部门，更换了一个别的部门
						developDepartment.setDepartmentID(departmentID);
						if(!developDepartment.getBranch().equals(developDepartment.getBranchOld())){
							//编辑了课别
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							String branchIDs=developDepartmentService.getBranchIDByBranchCT(developDepartment.getBranch());
							if(branchIDs!=null){
								developDepartment.setBranchID(branchIDs);
							}else{
								developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
							}
							developDepartmentService.updateBranchCT(developDepartment);
						}else{
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							developDepartmentService.updateBranchCT(developDepartment);
						}
					}else{
						//没有编辑部门
						developDepartment.setDepartmentID(departmentID);
						if(!developDepartment.getBranch().equals(developDepartment.getBranchOld())){
							//编辑了课别
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							String branchIDs=developDepartmentService.getBranchIDByBranchCT(developDepartment.getBranch());
							if(branchIDs!=null){
								developDepartment.setBranchID(branchIDs);
							}else{
								developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
							}
							developDepartmentService.updateBranchCT(developDepartment);
						}else{
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							developDepartmentService.updateBranchCT(developDepartment);
						}
					}
				}else{
					if(departmentID == null){
						//编辑了部门并且增加了新部门
						developDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentIDCT()+1));
						developDepartmentService.insertNewDepartmentCT(developDepartment);
						if(!developDepartment.getBranch().equals(developDepartment.getBranchOld())){
							//编辑了课别
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							String branchIDs=developDepartmentService.getBranchIDByBranchCT(developDepartment.getBranch());
							if(branchIDs!=null){
								developDepartment.setBranchID(branchIDs);
							}else{
								developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
							}
							developDepartmentService.updateBranchCT(developDepartment);
						}else{
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							developDepartmentService.updateBranchCT(developDepartment);
						}
					}else if (!departmentID.equals(developDepartment.getDepartmentIDOld())){
						//编辑部门，更换了一个别的部门
						developDepartment.setDepartmentID(departmentID);
						if(!developDepartment.getBranch().equals(developDepartment.getBranchOld())){
							//编辑了课别
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							String branchIDs=developDepartmentService.getBranchIDByBranchCT(developDepartment.getBranch());
							if(branchIDs!=null){
								developDepartment.setBranchID(branchIDs);
							}else{
								developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
							}
							developDepartmentService.updateBranchCT(developDepartment);
						}else{
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							developDepartmentService.updateBranchCT(developDepartment);
						}
					}else{
						//没有编辑部门
						developDepartment.setDepartmentID(departmentID);
						if(!developDepartment.getBranch().equals(developDepartment.getBranchOld())){
							//编辑了课别
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							developDepartment.setDepartmentIDOld(departmentID);
							developDepartment.setBelongCodeOld(developDepartment.getBelongCode());
							/*String branchIDs=developDepartmentService.getBranchIDByBranchCT(developDepartment.getBranch());
							if(branchIDs!=null){
								developDepartment.setBranchID(branchIDs);
							}else{
								developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
							}
							*/
							developDepartmentService.updateBranchCT(developDepartment);
						}else{
							developDepartment.setBranchIDOld(developDepartment.getBranchID());
							developDepartmentService.updateBranchCT(developDepartment);
						}
					}
				}
				/*if(departmentID == null){
					//编辑了部门并且增加了新部门
					developDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentIDCT()));
					developDepartmentService.insertNewDepartmentCT(developDepartment);
					developDepartment.setDepartmentIDOld(developDepartment.getDepartmentIDOld());
					developDepartmentService.updateBranchCT(developDepartment);
					
				}else{
					
				}
				if(!developDepartment.getDepartmentIDOld().equals(departmentID)){
					//编辑了部门
					
					developDepartment.setDepartmentID(departmentID);
					
				}
				if(!developDepartmentService.belongCodeExist(developDepartment.getBelongCode())){
						if(departmentID == null){
							developDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentIDCT()));
							developDepartmentService.insertNewDepartmentCT(developDepartment);
							developDepartment.setDepartmentIDOld(developDepartment.getDepartmentIDOld());
							developDepartmentService.updateBranchCT(developDepartment);
							
						}else if(departmentID != null){
							developDepartment.setDepartmentID(departmentID);
							developDepartmentService.updateDepartmentCT(developDepartment);
							developDepartment.setDepartmentIDOld(developDepartment.getDepartmentIDOld());
							developDepartmentService.updateBranchCT(developDepartment);
						}
				}else{
					//更新
					developDepartment.setDepartmentID(departmentID);
					developDepartment.setBelongCodeOld(developDepartment.getBelongCodeOld());
					developDepartmentService.updateDepartmentCT(developDepartment);
					developDepartmentService.updateBranchCT(developDepartment);
					
					//developDepartmentService.insertNewDepartmentBranch(developDepartment);
				}	*/
			}else if(developDepartment.getType().trim()  == null || developDepartment.getType().trim().equals("")){
				//add;
				if(developDepartmentService.departmentBranchBelongCodeExist(developDepartment.getDepartment(),developDepartment.getBranch(),developDepartment.getBelongCode())){
					mv.addObject("msg","failed");
				}else if(departmentID == null ){
					boolean newD = developDepartmentService.insertNewDepartmentCT(developDepartment);
					developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
					developDepartment.setDepartmentID(developDepartment.getDepartmentID());
					boolean newDB = developDepartmentService.insertNewDepartmentBranchCT(developDepartment);
					if ( newD == false || newDB == false) {
						mv.addObject("msg", "failed");
					} else{
						mv.addObject("msg", "success");	
					}
				} else if(departmentID != null ){
					
					developDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchIDCT()+1));
					developDepartment.setDepartmentID(departmentID);
					boolean newDB = developDepartmentService.insertNewDepartmentBranchCT(developDepartment);
					if (  newDB == false) {
						mv.addObject("msg", "failed");
					}else{
						mv.addObject("msg", "success");	
					}
				}
			}
			mv.setViewName("admin/developDepartment/save_result");
			return mv;
	}
	
	@RequestMapping(value = "/edit")
	public ModelAndView edit(String departmentID ,  String branchID) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView();
		DevelopDepartment developDepartment = null;
		String branchIDs = java.net.URLDecoder.decode(java.net.URLDecoder.decode(branchID,"UTF-8"),"UTF-8");
		String departmentIDs = java.net.URLDecoder.decode(departmentID,"UTF-8");
		if( !" ".equals(branchIDs.trim())){
			developDepartment = developDepartmentService.getDevelopDepartmentSelected(departmentIDs,branchIDs);	
		}else{
			developDepartment = developDepartmentService.getDepartment(departmentID);
		}
		//DevelopDepartment developDepartment = developDepartmentService.getDevelopDepartmentSelected(belongCode);
		
		developDepartment.setType("edit");
		
		mv.addObject("developDepartment",developDepartment);
		
		mv.setViewName("admin/developDepartment/developDepartmentAdd");
		return mv;
	}
	
	@RequestMapping(value = "/delete")
	public void delete(@RequestParam String departmentID,@RequestParam String branchID, PrintWriter out) throws UnsupportedEncodingException {
		//String[] belongCodes = belongCode.split(",");
		String[] branchIDNew = branchID.split(",");
		String[] departmentIDNew = departmentID.split(",");
		String msg = null;
		if(branchIDNew.length != departmentIDNew.length){
			msg ="{\"result\":\"failed\"}";
		}else{
			for(int j=0; j<departmentIDNew.length;j++){
				developDepartmentService.deleteByDepartmentBranch(departmentIDNew[j],branchIDNew[j]);
				if(!developDepartmentService.checkBranchByDeparmentID(departmentIDNew[j])){
					developDepartmentService.deleteDepartmentByID(departmentIDNew[j]);
				};
			}
			msg ="{\"result\":\"success\"}";
		}
		
		out.write(msg);
		out.close();
	}
	@RequestMapping(value = "/editCT", method = {RequestMethod.GET,RequestMethod.POST })
	public ModelAndView editCT(String belongCode, String departmentID , @RequestParam String branchID) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView();
		String belongCodes = java.net.URLDecoder.decode(java.net.URLDecoder.decode(belongCode, "UTF-8"),"UTF-8");
		DevelopDepartment developDepartment = developDepartmentService.getDevelopDepartmentSelectedCT(belongCodes,departmentID,branchID);
		List<Integer> deploymentList = developDepartmentService.getDeploymentList();
		List<String> deploymentStr = new ArrayList<String>();
		for(int i = 0 ; i < deploymentList.size(); i++){
			Integer deployment = deploymentList.get(i);
			if(deployment == 1){
				deploymentStr .add("現");
			}else{
				deploymentStr .add("旧");
			}
		}
		mv.addObject("deployment", deploymentStr);
		
		//developDepartment.setBelongCode(belongCode);
		developDepartment.setType("edit");
		mv.addObject("developDepartment",developDepartment);
		
		mv.setViewName("admin/developDepartment/developDepartmentAddCT");
		return mv;
	}
	
	@RequestMapping(value = "/deleteCT")
	public void deleteCT(@RequestParam String belongCode,@RequestParam String departmentID , @RequestParam String branchID,PrintWriter out) throws UnsupportedEncodingException {
		//belongCode = java.net.URLDecoder.decode(belongCode,"UTF-8");
		//belongCode = new String(belongCode.getBytes("ISO8859-1"),"UTF-8");
		//request.setCharacterEncoding("UTF-8");
		//String belongCode = request.getParameter("belongCode");
		//String departmentID = request.getParameter("departmentID");
		//belongCode = new String(belongCode.getBytes("ISO8859-1"),"UTF-8");
		//departmentID = new String(departmentID.getBytes("ISO8859-1"),"UTF-8");
		String[] belongCodes = belongCode.split(",");
		String[] departmentIDNew = departmentID.split(",");
		String[] branchIDNew = branchID.split(",");
		
		if("".equals(belongCode.trim()) && departmentID != null){
			for(int i=0;i<departmentIDNew.length;i++){
				developDepartmentService.deleteDepartmentByID(departmentIDNew[i]);
			}
		}else{
			for(int i=0; i<departmentIDNew.length;i++){
				developDepartmentService.deleteByBelongCodeDepartmentBranch(belongCodes[i],departmentIDNew[i],branchIDNew[i]);
				if(!developDepartmentService.checkBranchByDeparmentID(departmentIDNew[i])){
					developDepartmentService.deleteDepartmentByID(departmentIDNew[i]);
				};
			}
		}
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	@RequestMapping(value = "/downloadDepartment")
	public ModelAndView outPutDepartment(HttpSession session,HttpServletResponse response) throws IOException {
		//DataToExcle newClass = new DataToExcle();
		List<DevelopDepartment> departmentList= developDepartmentService.downloadDepartmentShow();
		for (int i = 0; i < departmentList.size(); i++){
			if(departmentList.get(i).getBranch() != null){
				departmentList.get(i).setMemo(departmentList.get(i).getBranchMemo());
			}	
			departmentList.get(i).setBelong("CXEE");
		}
		String realPathString = session.getServletContext().getRealPath("temp");
		String contextpath = session.getServletContext().getContextPath(); 
		//String webadr=contextpath+newClass.toExcel(projectList,realPathString);
		String webadr=contextpath+developDepartmentService.downloadDepartment(departmentList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	@RequestMapping(value = "/downloadDepartmentCT")
	public ModelAndView outPutDepartmentCT(HttpSession session,HttpServletResponse response) throws IOException {
		//DataToExcle newClass = new DataToExcle();
		List<DevelopDepartment> departmentList= developDepartmentService.downloadDepartmentShowCT();
		for (int i = 0; i < departmentList.size(); i++){
			if(departmentList.get(i).getBranch() != null){
				departmentList.get(i).setMemo(departmentList.get(i).getBranchMemo());
			}
			
			if(departmentList.get(i).getBranchDeployment().equals("1")){
				departmentList.get(i).setBranchDeployment("現");
			}else{
				departmentList.get(i).setBranchDeployment("旧");
			}
		}
		String realPathString = session.getServletContext().getRealPath("temp");
		String contextpath = session.getServletContext().getContextPath(); 
		//String webadr=contextpath+newClass.toExcel(projectList,realPathString);
		String webadr=contextpath+developDepartmentService.downloadDepartmentCT(departmentList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	@RequestMapping(value = "/upload")
	public ModelAndView upload(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		//Project projectInfo = (Project) httpSession.getAttribute(Const.SESSION_USER);
		DevelopDepartment developDepartment = new DevelopDepartment();
		mv.addObject("developDepartment", developDepartment);
		mv.setViewName("admin/developDepartment/departmentUpload");
		return mv;
	}
	@RequestMapping(value = "/uploadCT")
	public ModelAndView uploadCT(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		//Project projectInfo = (Project) httpSession.getAttribute(Const.SESSION_USER);
		DevelopDepartment developDepartment = new DevelopDepartment();
		mv.addObject("developDepartment", developDepartment);
		mv.setViewName("admin/developDepartment/departmentUploadCT");
		return mv;
	}
	
	@RequestMapping(value = "/fileUpload",method=RequestMethod.POST)
	public void fileUpload(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		//Staff staffInfo = (Staff) request.getSession().getAttribute(Const.SESSION_USER);
		HttpSession session = request.getSession();
		try {
			// 转型为MultipartHttpRequest：  
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            // 获得文件：  
            MultipartFile file = multipartRequest.getFile("file");
            
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
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
	            DevelopDepartment uploadDepartment = null;
	            int startRow = 2;
	            int startCell = 1;
	            
	            List<String> teamList =developDepartmentService.getTeamListMap();
	            List<Map<String, Object>> departmentList = developDepartmentService.getDepartmentList();
	    		List<Map<String, Object>> departmentCategoryList = developDepartmentService.getDepartmentCategoryList();
	    		List<DepartmentUploadResponse> errorDepartmentInfoList = new ArrayList<DepartmentUploadResponse>();
	    		DepartmentUploadResponse errorDepartmentInfo;
	            String resultString = "";
	            //String strPassword="";
	            //String strJobNo="";
	            String departmentID = "";	
	            int totalRowCount = 0;

	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadDepartment = new DevelopDepartment();
	            	errorDepartmentInfo = new DepartmentUploadResponse();
	            	departmentID = "";
	            	
					//部门
					if(row.getCell(1) != null &&  !"".equals(row.getCell(1).toString().trim())) {
	            		uploadDepartment.setDepartment(row.getCell(1).toString().trim());
	            	} else {
	            		errorDepartmentInfo.setErrNo(String.valueOf(startRow + i + 1));
						errorDepartmentInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_department"));
						errorDepartmentInfo.setErrContent(errorDepartmentInfo.getErrItem()
	            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(1).toString()));
	            		errorDepartmentInfoList.add(errorDepartmentInfo);
	            		continue;
	            	}
					//课别
					if(row.getCell(2) != null &&  !"".equals(row.getCell(2).toString().trim())) {
	            		uploadDepartment.setBranch(row.getCell(2).toString().trim());
	            	} else {
	            		uploadDepartment.setBranch("");
	            	}
					//组别
					if(row.getCell(3) != null &&  !"".equals(row.getCell(3).toString().trim())) {
	            		uploadDepartment.setTeam(row.getCell(3).toString().trim());
	            	} else {
	            		uploadDepartment.setTeam("");
	            	}
	            	//部门分类
    				if(row.getCell(4) != null &&  !"".equals(row.getCell(4).toString().trim())) {
						uploadDepartment.setDepartmentCategory(row.getCell(4).toString().trim());
	            	} else {
	            		uploadDepartment.setDepartmentCategory("");
	            	}
	            	//备注
	            	if(checkTextCellAndRequired(row.getCell(5).toString(),20)) {
	            		uploadDepartment.setBranchMemo(row.getCell(5).toString().trim());
	            	} else {
	            		uploadDepartment.setBranchMemo("");
	            	}
					//staffID = istaffService.getStaffByEmail(uploadStaff.getEmail());
					departmentID = developDepartmentService.getDepartmentID(uploadDepartment.getDepartment());
					String branchID = "";
					if(uploadDepartment.getBranch().trim() != null && !uploadDepartment.getBranch().equals("")){
						branchID = developDepartmentService.getBranchID(uploadDepartment.getBranch());
					}					
					//！------当输入的部门、课别相同时，只会更新其中一条记录，不会更新所有符合条件的记录！----
					if (departmentID != null ){
						// 部门存在时更新部门信息
						uploadDepartment.setDepartmentID(departmentID);
						uploadDepartment.setDepartmentIDOld(departmentID);
						developDepartmentService.updateDepartment(uploadDepartment);
						
						if(!"".equals(branchID) &&  branchID != null){
							//课别存在时，增加该部门、课别下的组别；
							uploadDepartment.setBranchID(branchID);
							if(developDepartmentService.checkHasBranch(departmentID,branchID)){
								if(developDepartmentService.checkBranchBydepartmentBranchTeam(departmentID,developDepartmentService.getBranchByTeam(uploadDepartment.getBranch(),uploadDepartment.getTeam().trim()),uploadDepartment.getTeam().trim())){
									uploadDepartment.setBranchIDOld(developDepartmentService.getBranchByTeam(uploadDepartment.getBranch(),uploadDepartment.getTeam().trim()));
									developDepartmentService.updateBranchByTeam(uploadDepartment);
								}else if(uploadDepartment.getTeam().trim().indexOf(",") > 0){
									//有多个组别时，insert
									String[] tList = uploadDepartment.getTeam().trim().split(",");
									for(int no = 0; no < tList.length ; no++){
										if(developDepartmentService.checkBranchBydepartmentBranchTeam(departmentID,developDepartmentService.getBranchByTeam(uploadDepartment.getBranch(),tList[no]),uploadDepartment.getTeam().trim())){
											Random r = new Random();
											int b = r.nextInt();
											if(b<0){b=-b;}
											uploadDepartment.setBelongCode(String.valueOf(b));
											uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
											developDepartmentService.insertNewDepartmentBranch(uploadDepartment);
										}
									}
								}else if(uploadDepartment.getTeam().trim().indexOf(",") == -1 ){
									//只有一个组别时，更新branch;
									uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
									Random random = new Random();
									int randomBelongCode = random.nextInt();
									if(randomBelongCode < 0){randomBelongCode = -randomBelongCode;}
									uploadDepartment.setBelongCode(String.valueOf(randomBelongCode));
									developDepartmentService.insertNewDepartmentBranch(uploadDepartment);
								}else{
									//课别和组别不能都为空
									developDepartmentService.updateBranch(uploadDepartment);
									developDepartmentService.updateDepartment(uploadDepartment);
								}
								
								//uploadDepartment.setBranchIDOld(branchID);
								//developDepartmentService.updateBranch(uploadDepartment);
							}else{
								if(uploadDepartment.getTeam().trim().indexOf(",") > 0){
									//多个组别时；
									String[] teams = uploadDepartment.getTeam().split(",");
									for(int m = 0 ; m < teams.length; m++){
										uploadDepartment.setTeam(teams[m]);
										Random random = new Random();
										int randomBelongCode = random.nextInt();
										if(randomBelongCode < 0){randomBelongCode = -randomBelongCode;}
										uploadDepartment.setBelongCode(String.valueOf(randomBelongCode));
										if(developDepartmentService.insertNewDepartmentBranch(uploadDepartment)){
										//插入新的组别成功	
											System.out.println("!----insert team success----");
										}
									}
								}else if(uploadDepartment.getTeam().trim().indexOf(",") == -1){
									
									if(developDepartmentService.checkBranchBydepartmentBranchTeam(departmentID,developDepartmentService.getBranchByTeam(uploadDepartment.getBranch(),uploadDepartment.getTeam().trim()),uploadDepartment.getTeam().trim())){
										developDepartmentService.updateBranchByTeam(uploadDepartment);
									}else{
										//单个组别时，插入branch
										Random random = new Random();
										int randomBelongCode = random.nextInt();
										if(randomBelongCode < 0){randomBelongCode = -randomBelongCode;}
										uploadDepartment.setBelongCode(String.valueOf(randomBelongCode));
										developDepartmentService.insertNewDepartmentBranch(uploadDepartment);
									}
								}
							}
						}else if( "".equals(branchID) || branchID == null){
							//部门存在，课别不存在时，
							String branch = uploadDepartment.getBranch().trim();
							String team = uploadDepartment.getTeam().trim();
							if(!"".equals(branch) && !"".equals(team)){
								if(branch.indexOf(",") > 0 && team.indexOf(",") > 0){
									//不能同时输入多个组别和课别
									errorDepartmentInfo.setErrNo(String.valueOf(startRow + i + 1));
									errorDepartmentInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_manymany"));
									errorDepartmentInfo.setErrContent(errorDepartmentInfo.getErrItem());
				            		errorDepartmentInfoList.add(errorDepartmentInfo);
				            		continue;
								}else if(branch.indexOf(",") > 0 && team.indexOf(",") == -1){
									//有多个课别时，insert
									String[] branchList = branch.split(",");
									for(int no = 0; no < branchList.length ; no++){
										uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID()+1));
										uploadDepartment.setBranch(branchList[i]);
										Random r = new Random();
										int b = r.nextInt();
										if(b<0){b=-b;}
										uploadDepartment.setBelongCode(String.valueOf(b));
										developDepartmentService.insertNewDepartmentBranch(uploadDepartment);
									}
								}else if(branch.indexOf(",") == -1 && team.indexOf(",") == -1 && ! branch.trim().equals("")){
									//只有一个课别时，更新branch;
									uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
									Random random = new Random();
									int randomBelongCode = random.nextInt();
									if(randomBelongCode < 0){randomBelongCode = -randomBelongCode;}
									uploadDepartment.setBelongCode(String.valueOf(randomBelongCode));
									developDepartmentService.insertNewDepartmentBranch(uploadDepartment);
								}else{
									//课别和组别不能都为空
									developDepartmentService.updateBranch(uploadDepartment);
									developDepartmentService.updateDepartment(uploadDepartment);
								}
							}else if(! "".equals(branch) && "".equals(team)){
								uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
								Random random = new Random();
								int randomBelongCode = random.nextInt();
								if(randomBelongCode < 0){randomBelongCode = -randomBelongCode;}
								uploadDepartment.setBelongCode(String.valueOf(randomBelongCode));
								developDepartmentService.insertNewDepartmentBranch(uploadDepartment);
							}else if("".equals(branch) ){
								uploadDepartment.setBranchIDOld(null);
								developDepartmentService.updateBranch(uploadDepartment);
							}
							
							//uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID()+1));
							//developDepartmentService.updateBranch(uploadDepartment);
						}
						//developDepartmentService.updateBranch(uploadDepartment);
					} else {
						// 部门不存在时追加部门信息
						uploadDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentID()+1));
						developDepartmentService.insertNewDepartment(uploadDepartment);
						uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID()+1));
						//uploadDepartment.setDepartmentID(uploadDepartment.getDepartmentID());
						Random random = new Random();
						int randomBelongCode = random.nextInt();
						if(randomBelongCode < 0){randomBelongCode = -randomBelongCode;}
						uploadDepartment.setBelongCode(String.valueOf(randomBelongCode));
						developDepartmentService.insertNewDepartmentBranch(uploadDepartment);
					}
	            }
	            
	            filestream.close();

	            Gson gson = new Gson();
				resultString = gson.toJson(errorDepartmentInfoList);
				int totalErrorCount = errorDepartmentInfoList.size();
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
	
	
	@RequestMapping(value = "/fileUploadCT",method=RequestMethod.POST)
	public void fileUploadCT(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		//Staff staffInfo = (Staff) request.getSession().getAttribute(Const.SESSION_USER);
		HttpSession session = request.getSession();
		try {
			// 转型为MultipartHttpRequest：  
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            // 获得文件：  
            MultipartFile file = multipartRequest.getFile("file");
            
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
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
	            DevelopDepartment uploadDepartment = null;
	            int startRow = 2;
	            int startCell = 1;
	            
	            List<Map<String, Object>> departmentList = developDepartmentService.getDepartmentList();
	    		List<DepartmentUploadResponse> errorDepartmentInfoList = new ArrayList<DepartmentUploadResponse>();
	    		DepartmentUploadResponse errorDepartmentInfo;
	            String resultString = "";
	            //String strPassword="";
	            //String strJobNo="";
	            String departmentID = "";	
	            int totalRowCount = 0;

	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadDepartment = new DevelopDepartment();
	            	errorDepartmentInfo = new DepartmentUploadResponse();
	            	departmentID = "";
	            	
	            	
	            	
	            	//在籍归属code
	           
					if(checkTextCellAndRequired(row.getCell(1).toString(),45)) {
	            		uploadDepartment.setBelongCode(row.getCell(1).toString().trim());
	            	} else{
	            		errorDepartmentInfo.setErrNo(String.valueOf(startRow + i + 1));
						errorDepartmentInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_enrolementCode"));
						errorDepartmentInfo.setErrContent(row.getCell(1).toString());
	            		errorDepartmentInfoList.add(errorDepartmentInfo);
	            		continue;
	            	}
					//部门
					if(row.getCell(2) != null &&  !"".equals(row.getCell(2).toString().trim())) {
	            		uploadDepartment.setDepartment(row.getCell(2).toString().trim());
	            	} else {
	            		errorDepartmentInfo.setErrNo(String.valueOf(startRow + i + 1));
						errorDepartmentInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_department"));
						errorDepartmentInfo.setErrContent(errorDepartmentInfo.getErrItem()
	            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorDepartmentInfoList.add(errorDepartmentInfo);
	            		continue;
	            	}
					//课别
					if(row.getCell(3) != null &&  !"".equals(row.getCell(3).toString().trim())) {
	            		uploadDepartment.setBranch(row.getCell(3).toString().trim());
	            	} else {
	            		uploadDepartment.setBranch("");
	            	}
					//现旧
    				if(row.getCell(4) != null &&  !"".equals(row.getCell(4).toString().trim())) {
	            		boolean hasDeployment = false;
	            		if(row.getCell(4).toString().trim().equals("現")){
	            			uploadDepartment.setBranchDeployment("1");
	            			hasDeployment = true;
	            		}else if(row.getCell(4).toString().trim().equals("旧")){
	            			uploadDepartment.setBranchDeployment("0");
	            			hasDeployment = true;
	            		}
	            		if (!hasDeployment){
	            			errorDepartmentInfo.setErrNo(String.valueOf(startRow + i + 1));
	            			errorDepartmentInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_deployment"));
	            			errorDepartmentInfo.setErrContent(errorDepartmentInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_notExist").replace("{0}", row.getCell(4).toString()));
		            		errorDepartmentInfoList.add(errorDepartmentInfo);
		            		continue;
	            		}
	            	} else {
	            		errorDepartmentInfo.setErrNo(String.valueOf(startRow + i + 1));
            			errorDepartmentInfo.setErrItem(Tools.getPropertiesValue(session, "staffUpload_string_msg_deployment"));
            			errorDepartmentInfo.setErrContent(errorDepartmentInfo.getErrItem()
	            				+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank").replace("{0}", row.getCell(4).toString()));
	            		errorDepartmentInfoList.add(errorDepartmentInfo);
	            		continue;
	            	}
	            	//备注
	            	if(checkTextCellAndRequired(row.getCell(5).toString(),20)) {
	            		uploadDepartment.setBranchMemo(row.getCell(5).toString().trim());
	            	} else {
	            		uploadDepartment.setBranchMemo("");
	            	}
					//staffID = istaffService.getStaffByEmail(uploadStaff.getEmail());
	            	
					departmentID = developDepartmentService.getDepartmentID(uploadDepartment.getDepartment().trim());
					//！------当输入的部门、课别相同时，只会更新其中一条记录，不会更新所有符合条件的记录！----

					
					if (departmentID != null ){
						// 部门存在时更新部门信息
						uploadDepartment.setDepartmentID(departmentID);
						String branchID = null;
						if( !uploadDepartment.getBranch().equals("")){
							branchID = developDepartmentService.getBranchIDByBranchCT(uploadDepartment.getBranch());
							if(branchID == null){
								//课别不存在时，插入数据
								uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
								developDepartmentService.insertNewDepartmentBranchCT(uploadDepartment);
							}else{
								//课别存在时，更新这条数据
								uploadDepartment.setDepartmentIDOld(departmentID);
								uploadDepartment.setBelongCodeOld(uploadDepartment.getBelongCode());
								uploadDepartment.setBranchIDOld(branchID);
								uploadDepartment.setBranchID(branchID);
								developDepartmentService.updateBranchCT(uploadDepartment);
							}
						}else{
							branchID=developDepartmentService.getBranchIDCT(uploadDepartment.getBelongCode());
							if(branchID != null && developDepartmentService.checkBranchExist(departmentID, branchID, uploadDepartment.getBelongCode())){
								uploadDepartment.setDepartmentIDOld(departmentID);
								uploadDepartment.setBelongCodeOld(uploadDepartment.getBelongCode());
								uploadDepartment.setBranchID(branchID);
								uploadDepartment.setBranchIDOld(branchID);
								developDepartmentService.updateBranchCT(uploadDepartment);
							}else{
								uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
								developDepartmentService.insertNewDepartmentBranchCT(uploadDepartment);
							}
							
							
							
							
						}
						
						
					} else {
						// 部门不存在时追加部门信息
						uploadDepartment.setDepartmentID(String.valueOf(developDepartmentService.getMaxDepartmentID()+1));
						developDepartmentService.insertNewDepartment(uploadDepartment);
						String branchID = null;
						if( !uploadDepartment.getBranch().equals("")){
							branchID = developDepartmentService.getBranchIDByBranchCT(uploadDepartment.getBranch());
							if(branchID == null){
								//课别不存在时，插入数据
								uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
								developDepartmentService.insertNewDepartmentBranchCT(uploadDepartment);
							}else{
								//课别存在时，更新这条数据
								uploadDepartment.setDepartmentIDOld(departmentID);
								uploadDepartment.setBelongCodeOld(uploadDepartment.getBelongCode());
								uploadDepartment.setBranchIDOld(branchID);
								uploadDepartment.setBranchID(branchID);
								developDepartmentService.updateBranchCT(uploadDepartment);
							}
						}else{
							uploadDepartment.setBranchID(String.valueOf(developDepartmentService.getMaxBranchID() +1));
							developDepartmentService.insertNewDepartmentBranchCT(uploadDepartment);
						}
						
					}
				}
					
	            filestream.close();
	            Gson gson = new Gson();
				resultString = gson.toJson(errorDepartmentInfoList);
				int totalErrorCount = errorDepartmentInfoList.size();
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
	private boolean checkTextCellAndRequired(String value,int length){	
		boolean bl=true;
		if(value == null ||  "".equals(value)){
			bl=false;
		}
		if(value.length() > length){
			bl=false;
		}
		return bl;
	}
	
	
}
