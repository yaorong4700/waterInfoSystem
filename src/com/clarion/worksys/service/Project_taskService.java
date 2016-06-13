package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.httpentity.ProjectTaskReqParam;

public interface Project_taskService {
	
	List<Project_task> listAllTask();
	
	List<Project_task> getTaskByCategory(String category);
	
	List<Project_task> getActivityByTask(String task);

	List<Project_task> selectAllTask(ProjectTaskReqParam projectTaskReqParam);
	
	int totalPageCount(ProjectTaskReqParam projectTaskReqParam);
	
	Project_task getTaskByID(int id);
	
	boolean insertNewProjectTask(Project_task projectTask);
	
	void updateProjectTask(Project_task projectTask);
	
	void delectProjectTask(String[] ids);

	List<String> getTaskByDepartment(String department);
	
	List<Map<String, Object>> getDepartmentList();
	
	List<Map<String, Object>> getCategoryList();
	
	int getTaskIDByCDP(String categoryID, String departmentID, String taskProcessID);
	
	String downloadProjectTask(List<Project_task> projectTaskList,String realpathString);

	boolean checkID(Integer taskID); 
}
