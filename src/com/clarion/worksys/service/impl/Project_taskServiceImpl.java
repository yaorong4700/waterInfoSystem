/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * Project_taskService Implement
 * 
 * @author weng_zhangchu@clarion.com.cn
 * @create: 2012-12-24
 * @histroy:
 * 	2012-12-24 weng_zhangchu
 *  # 初版
 *  2013-3-19 wengzhangchu
 *  第二版
 */
package com.clarion.worksys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.Component;
import com.clarion.worksys.entity.ExportClass;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.httpentity.ProjectTaskReqParam;
import com.clarion.worksys.mapper.Project_taskMapper;
import com.clarion.worksys.service.Project_taskService;

@Service
public class Project_taskServiceImpl implements Project_taskService {

	/* (non-Javadoc)
	 * @see com.clarion.worksys.service.Project_taskService#getActivityByTask(java.lang.String)
	 */
	@Autowired
	private Project_taskMapper project_taskMapper;
	public List<Project_task> getActivityByTask(String task) {
		// TODO Auto-generated method stub
		return project_taskMapper.getActivityByTask(task);
	}

	/* (non-Javadoc)
	 * @see com.clarion.worksys.service.Project_taskService#getTaskByCategory(java.lang.String)
	 */
	public List<Project_task> getTaskByCategory(String category) {
		// TODO Auto-generated method stub
		return project_taskMapper.getTaskByCategory(category);
	}

	/* (non-Javadoc)
	 * @see com.clarion.worksys.service.Project_taskService#listAllTask()
	 */
	public List<Project_task> listAllTask() {
		// TODO Auto-generated method stub
		return project_taskMapper.listAllTask();
	}

	public List<Project_task> selectAllTask(ProjectTaskReqParam projectTaskReqParam){
		return project_taskMapper.selectAllTask(projectTaskReqParam);
	}

	public int totalPageCount(ProjectTaskReqParam projectTaskReqParam){
		return project_taskMapper.totalPageCount(projectTaskReqParam);
	}

	public Project_task getTaskByID(int id){
		return project_taskMapper.getTaskByID(id);
	}

	public boolean insertNewProjectTask(Project_task projectTask){
		int count = project_taskMapper.insertNewProjectTask(projectTask);
		if (count == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void updateProjectTask(Project_task projectTask){
		project_taskMapper.updateProjectTask(projectTask);
	}
	
	public void delectProjectTask(String[] ids){
		project_taskMapper.delectProjectTask(ids);
	}
	
	public List<String> getTaskByDepartment(String departmentID){
		return project_taskMapper.getTaskByDepartment(departmentID);
	}
	
	public List<Map<String, Object>> getDepartmentList() {
		return project_taskMapper.getDepartmentList();
	}
	
	public List<Map<String, Object>> getCategoryList() {
		return project_taskMapper.getCategoryList();
	}
	
	public int getTaskIDByCDP(String categoryID, String departmentID, String taskProcessID){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("categoryID",categoryID);
		params.put("departmentID",departmentID);
		params.put("taskProcessID",taskProcessID);
		return project_taskMapper.getTaskIDByCDP(params);
	}
	
	public String downloadProjectTask(List<Project_task> projectTaskList,String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "ProjectTaskList_"
        					  + String.valueOf(c1.get(Calendar.YEAR))
                              + String.valueOf(c1.get(Calendar.MONTH) + 1)  //系统日期从0开始算起
                              + String.valueOf(c1.get(Calendar.DAY_OF_MONTH))
                              + String.valueOf(c1.get(Calendar.HOUR_OF_DAY))
                              + String.valueOf(c1.get(Calendar.MINUTE))
                              + String.valueOf(c1.get(Calendar.SECOND));
        String strnameString2 = realpathString + "\\" + filenameString + ".xls";

        //返回文件路径
        String webaddrString  = "/temp/" + filenameString + ".xls";

        try{
			File file;
			POIFSFileSystem fs;
			HSSFWorkbook wb;
			HSSFSheet sheet;
			int curruntRow=2;

			//读取模板
			file = new File(Project_taskServiceImpl.class.getResource(Parma.PROJECTTASK_TEMPLATE).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "ProjectTaskInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < projectTaskList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				row.getCell(0).setCellValue(i+1);//順番
				row.getCell(1).setCellValue(projectTaskList.get(i).getCategory());//開発段階
				row.getCell(2).setCellValue(projectTaskList.get(i).getTask());//プロセス
				row.getCell(3).setCellValue(projectTaskList.get(i).getTaskProcessID());//プロセスID
				row.getCell(4).setCellValue(projectTaskList.get(i).getDepartment());//所属部署
				row.getCell(5).setCellValue(projectTaskList.get(i).getMemo());//分類
				row.getCell(6).setCellValue(projectTaskList.get(i).getIsVisible());//表示
				row.getCell(7).setCellValue(projectTaskList.get(i).getTaskID()); //id
			}

			OutputStream out = new FileOutputStream(strnameString2);
			wb.write(out);
	    	out.close();
			return webaddrString;
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public boolean checkID(Integer taskID) {
		// TODO Auto-generated method stub
		int count=project_taskMapper.checkID(taskID);
		return count==1;
	}
}
