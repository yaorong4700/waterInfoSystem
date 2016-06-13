/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * ProjectTask Controller
 * 
 * @author weng_zhangchu@clarion.com.cn
 * @create: 2012-3-19
 * @histroy:
 * 	
 *  2013-3-19 wengzhangchu
 *  # 第一版
 */
package com.clarion.worksys.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.CategoryParam;
import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UploadResponse;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.httpentity.ProjectTaskReqParam;
import com.clarion.worksys.httpentity.ProjectTaskResponse;
import com.clarion.worksys.httpentity.ProjectTaskResponseRows;
import com.clarion.worksys.service.ComponentService;
import com.clarion.worksys.service.ProjectService;
import com.clarion.worksys.service.Project_taskService;
import com.clarion.worksys.service.StaffSelectedService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.DataToExcle;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

/**
 * @author weng_zhangchu
 *
 */
@Controller
@RequestMapping(value = "/projectTask")
public class ProjectTaskController {
	
	@Autowired
	private Project_taskService projectTaskService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private StaffSelectedService staffSelectedService;
	@Autowired
	private UserRoleManageService userRoleManageService;
	
	@RequestMapping
	public ModelAndView showAndView(HttpSession session){
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"ProcessManage",userRoleManageService);
		ModelAndView view = new ModelAndView();
		view.addObject("staff",staff);
		view.addObject("AlterRoleFlag", userRole.getAlterRoleFlag());//編集
		view.addObject("UploadRoleFlag", userRole.getUploadRoleFlag());//U/L
		view.addObject("DownloadRoleFlag", userRole.getDownloadRoleFlag());//D/L
		view.setViewName("admin/project_task/projectTask");
		return view;
	}
	/**
	 * 显示所有项目
	 * 
	 * @param 好多
	 * @return 
	 */
	@RequestMapping(value ="/showAll")
	public void displayAllDate(HttpSession session,HttpServletResponse response,ProjectTaskReqParam projectTaskReqParam)throws IOException {
		//设置数据库查询时从哪条数据开始
		projectTaskReqParam.setPageSeq((projectTaskReqParam.getPage()-1)*projectTaskReqParam.getRp());
		//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(projectTaskReqParam.getCategory()!=null &&projectTaskReqParam.getCategory().trim().equals("")){
        	projectTaskReqParam.setCategory(null);
		}
        if(projectTaskReqParam.getQuery()!=null &&!projectTaskReqParam.getQuery().trim().equals("")){
			if(projectTaskReqParam.getQtype().trim().equals("category")){
				projectTaskReqParam.setCategory(projectTaskReqParam.getQuery().trim());
			}else if(projectTaskReqParam.getQtype().trim().equals("task")){
				projectTaskReqParam.setTask(projectTaskReqParam.getQuery().trim());
			}else if(projectTaskReqParam.getQtype().trim().equals("department")){
				projectTaskReqParam.setDepartment(projectTaskReqParam.getQuery().trim());
			}
		}
        List<Project_task> projectTaskList = new ArrayList<Project_task>();
        int totalPageCount = 0;
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"ProcessManage",userRoleManageService);
		if ("1".equals(userRole.getQueryRoleFlag())) {
			if ("1".equals(userRole.getQueryRoleFlag())) {
				if (("CT".equals(staff.getKaisya()) && "1".equals(userRole.getSpecialRole1Flag())) 
						|| ("CXEE".equals(staff.getKaisya()) && "1".equals(userRole.getSpecialRole2Flag()))) {
					if ("1".equals(userRole.getDepartmentFlag())) {
						projectTaskReqParam.setDepartmentFlag("1");
					} else {
						projectTaskReqParam.setDepartmentFlag("0");
					}
					projectTaskReqParam.setDepartmentID(String.valueOf(staff.getDepartmentID()));
					projectTaskList = projectTaskService.selectAllTask(projectTaskReqParam);
					totalPageCount = projectTaskService.totalPageCount(projectTaskReqParam);
				}
			}
		}
		
		List<ProjectTaskResponseRows> projectTaskRespongseList = new ArrayList<ProjectTaskResponseRows>();
		for (int i=0;i<projectTaskList.size();i++) {
			projectTaskList.get(i).setNo((projectTaskReqParam.getPage()-1)*projectTaskReqParam.getRp() + i + 1);
			ProjectTaskResponseRows projectTaskResponseRows = new ProjectTaskResponseRows(i, projectTaskList.get(i));
			projectTaskRespongseList.add(projectTaskResponseRows);
		}
		ProjectTaskResponse projectTaskResponse = new ProjectTaskResponse();
		projectTaskResponse.setPage(projectTaskReqParam.getPage());        //设置
		projectTaskResponse.setTotal(totalPageCount);
		projectTaskResponse.setRows(projectTaskRespongseList);
		Gson gson = new Gson();
		String resultString = gson.toJson(projectTaskResponse);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(resultString);
		out.close();
	}
	/**
	 * 新增作业类型页面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ModelAndView toAdd(HttpSession session) {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		List<Map<String, Object>> departList =  staffSelectedService.getDepartmentList();
		List<Map<String, Object>> categoryList = projectTaskService.getCategoryList();
		ModelAndView mv = new ModelAndView();
		mv.addObject("staff",staff);
		mv.addObject("categoryList", categoryList);
		mv.addObject("departList", departList);
		mv.setViewName("admin/project_task/projectTaskInfo");
		return mv;
	}
	
	/**
	 * 保存项目信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(Project_task projectTask) {
		if ("".equals(projectTask.getActivity())) {
			projectTask.setActivity(null);
		}
		/*if ("".equals(projectTask.getMemo())) {
			projectTask.setMemo(null);
		}*/
		List<Map<String, Object>> categoryList = projectTaskService.getCategoryList();
		for (int k = 0; k < categoryList.size(); k++){
			Map<String,Object> t = categoryList.get(k);	            
			if (projectTask.getCategoryID().toString().equals(t.get("categoryID").toString())){
				projectTask.setCategory(t.get("categoryName").toString());
				break;
			}
		}
		ModelAndView mv = new ModelAndView();
		if (projectTask.getTaskID() == null || projectTask.getTaskID().intValue() == 0 ) {
			if (projectTaskService.insertNewProjectTask(projectTask) == false ) {
				mv.addObject("msg", "failed");
			} else {
				mv.addObject("msg", "success");	
			}
		} else {
			projectTaskService.updateProjectTask(projectTask);
		}
		String department = staffSelectedService.getDepartmentByID(Integer.parseInt(projectTask.department));
		List<String> departments = new ArrayList<String>();
		departments.add(department);
		updateCategoryMap(departments);
		mv.setViewName("admin/project_task/save_result");
		return mv;
	}

	
	/**
	 * 编辑项目
	 * 
	 * @param 项目id
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit(@RequestParam int id,HttpSession session) {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		Project_task projectTask =  projectTaskService.getTaskByID(id);
		List<Map<String, Object>> departList =   staffSelectedService.getDepartmentList();
		List<Map<String, Object>> categoryList = projectTaskService.getCategoryList();
		
		mv.addObject("staff",staff);
		mv.addObject("categoryList", categoryList);
		mv.addObject("departList", departList);
		mv.addObject("projectTask", projectTask);
		mv.setViewName("admin/project_task/projectTaskInfo");
		return mv;
	}
	
	
	/**
	 * 删除项目
	 * 
	 * @param ids
	 * @param out
	 */
	@RequestMapping(value = "/delete")
	public void deleteProjectTask(@RequestParam String ids, PrintWriter out) {
		String[] allId = ids.split(",");
		projectTaskService.delectProjectTask(allId);
		List<Map<String, Object>> departmentInfoList = staffSelectedService.getDepartmentList();
		List<String> departments = new ArrayList<String>();
		for (Map<String, Object> map : departmentInfoList) {
			departments.add(map.get("department").toString());
		}
		updateCategoryMap(departments);
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	
	/**
	 * 导出项目详细信息
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/outPut")
	public ModelAndView outPutProjectTask(HttpServletResponse response,HttpSession httpSession) throws IOException {
		DataToExcle newClass = new DataToExcle();
		List<Project_task> projectList= projectTaskService.listAllTask();
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath(); 
		String webadr=contextpath+projectTaskService.downloadProjectTask(projectList,realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	
    public void updateCategoryMap(List<String> departments) {
    	for (String department : departments) {
    		CategoryParam categoryParam = new CategoryParam();
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
    		ProjectController.categoryMap.put(department, categoryParam);
		}
	}
    /**
	 * 上传页面
	 */
	@RequestMapping(value = "/upload")
	public ModelAndView toUpload(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		Staff staffInfo = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		mv.addObject("staffInfo", staffInfo);
		mv.setViewName("admin/project_task/projectTaskUpload");
		return mv;
	}
	/**
	 * 作业类型信息文件上传
	 */
	@RequestMapping(value = "/fileUpload",method=RequestMethod.POST)
	public void fileUpload(HttpServletRequest request,HttpServletResponse response) throws ParseException {

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
	            Project_task uploadTask = null;
	            int startRow = 2;
	            int startCell = 0;

	            List<Map<String, Object>> categoryList = projectTaskService.getCategoryList();
	            List<Map<String, Object>> departmentInfoList = staffSelectedService.getDepartmentList();
	            
	            List<UploadResponse> errorInfoList = new ArrayList<UploadResponse>();
	            UploadResponse errorInfo;
	            String resultString = "";
	            int totalRowCount = 0;

	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadTask = new Project_task();
	            	errorInfo = new UploadResponse();
	            	//開発段階
					if(row.getCell(1)!= null && !"".equals(row.getCell(1).toString().trim())) {
						boolean hasCategory = false;
						for (int j = 0 ; j < categoryList.size(); j++){
	            			Map<String,Object> t = categoryList.get(j);	            
            				if (row.getCell(1).toString().trim().equals(t.get("categoryName"))){
            					uploadTask.setCategoryID(Integer.parseInt(t.get("categoryID").toString()));
            					uploadTask.setCategory(row.getCell(1).toString().trim());
            					hasCategory = true;
            					break;
            				}
	            		}
						if (!hasCategory){
							errorInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorInfo.setErrItem(Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_category"));
		            		errorInfo.setErrContent(errorInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_notExist").replace("{0}", row.getCell(1).toString()));
		            		errorInfoList.add(errorInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_category"));
	            		errorInfo.setErrContent(errorInfo.getErrItem()+Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_noBlank"));
	            		errorInfoList.add(errorInfo);
	            		continue;
	            	}
					//プロセス名称
	            	if (row.getCell(2) != null && !"".equals(row.getCell(2).toString())){
	            		uploadTask.setTask(row.getCell(2).toString());
	            	} else {
	            		uploadTask.setTask("");
	            	}
	            	//プロセスID
	            	if(checkTextCellAndRequired(row.getCell(3).toString().trim(),10)) {
	            		uploadTask.setTaskProcessID(row.getCell(3).toString());
	            	} else {
	            		//出错时处理
	            		errorInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "projectTask_string_msg_processID"));

	            		if(row.getCell(3).toString().isEmpty()){
	            			errorInfo.setErrContent(errorInfo.getErrItem()+Tools.getPropertiesValue(session, "projectTask_string_msg_noBlank"));
	            		} else {
	            			errorInfo.setErrContent(errorInfo.getErrItem()+Tools.getPropertiesValue(session, "projectTask_string_msg_overLength"));
	            		}
	            		continue;
	            	}
	            	//開発部署
					if(row.getCell(4)!= null && !"".equals(row.getCell(4).toString().trim())) {
						boolean hasDepartment = false;
						for (int j = 0 ; j < departmentInfoList.size(); j++){
	            			Map<String,Object> t = departmentInfoList.get(j);	            
            				if (row.getCell(4).toString().trim().equals(t.get("department"))){
            					uploadTask.setDepartment((t.get("departmentID").toString()));
            					hasDepartment = true;
            					break;
            				}
	            		}
						if (!hasDepartment){
							errorInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorInfo.setErrItem(Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_department"));
							errorInfo.setErrContent(errorInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_notExist").replace("{0}", row.getCell(4).toString()));
		            		errorInfoList.add(errorInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_department"));
	            		errorInfo.setErrContent(errorInfo.getErrItem()+Tools.getPropertiesValue(session, "projectTask_string_msg_noBlank"));
	            		errorInfoList.add(errorInfo);
	            		continue;
	            	}
					//分类
					if(row.getCell(5)!= null && !"".equals(row.getCell(5).toString().trim())) {
	            		if (row.getCell(5).toString().equals(Tools.getPropertiesValue(session, "projectTaskInfo_string_tblmemo_1"))){
	            			uploadTask.setIsVisible("");//表示
	            		} else {
	            			uploadTask.setIsVisible(row.getCell(5).toString());//デフォルトは表示
	            		}
	            	} else {
	            		uploadTask.setIsVisible("");
	            	}
					//表示
	            	if(row.getCell(6)!= null && !"".equals(row.getCell(6).toString().trim())) {
	            		if (row.getCell(6).toString().equals(Tools.getPropertiesValue(session, "projectTaskInfo_string_visible_1"))){
	            			uploadTask.setIsVisible("1");//表示
	            		} else if (row.getCell(6).toString().equals(Tools.getPropertiesValue(session, "projectTaskInfo_string_visible_2"))){
	            			uploadTask.setIsVisible("0");//非表示
	            		} else {
	            			uploadTask.setIsVisible("1");//デフォルトは表示
	            		}
	            	} else {
	            		uploadTask.setIsVisible("1");
	            	}
	            	if (row.getCell(7) != null &&  !"".equals(row.getCell(7).toString().trim())){
						// 存在时更新信息
						if (row.getCell(7).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
		            		DecimalFormat df = new DecimalFormat("0");
		            		uploadTask.setTaskID(Integer.valueOf(df.format(row.getCell(7).getNumericCellValue())));
		            	} else {
		            		uploadTask.setTaskID(Integer.valueOf(row.getCell(7).getStringCellValue()));
		            	}
						//zxm20160512
						
						if(projectTaskService.checkID(uploadTask.getTaskID())){
							projectTaskService.updateProjectTask(uploadTask);
						}else{
							errorInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorInfo.setErrItem(Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_id"));
							errorInfo.setErrContent(errorInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "projectTaskUpload_string_msg_notExist").replace("{0}", row.getCell(7).toString()));
		            		errorInfoList.add(errorInfo);
		            		continue;
						}
						
					} else {
						// 不存在时追加信息
						projectTaskService.insertNewProjectTask(uploadTask);
					}
	            }
	            
	            filestream.close();

	            Gson gson = new Gson();
				resultString = gson.toJson(errorInfoList);
				int totalErrorCount = errorInfoList.size();
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
		if(value == null || "".equals(value)){
			return false;
		}
		if(value.length() > length){
			return false;
		}
		return true;
	}
}
