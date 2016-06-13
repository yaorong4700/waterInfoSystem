/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * Project_task Mapper
 * 
 * @author weng_zhangchu@clarion.com.cn
 * @create: 2012-12-24
 * @histroy:
 * 	2012-12-24 weng_zhangchu
 *  # 初版
 *  2013-3-19 wengzhangchu
 *  第二版
 */
package com.clarion.worksys.mapper;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.httpentity.ProjectTaskReqParam;

/**
 * @author weng_zhangchu
 *
 */
public interface Project_taskMapper {
	
	List<Project_task> listAllTask();
	
	List<Project_task> getTaskByCategory(String category);
	
	List<Project_task> getActivityByTask(String task);
	

	List<Project_task> selectAllTask(ProjectTaskReqParam projectTaskReqParam);
	
	int totalPageCount(ProjectTaskReqParam projectTaskReqParam);
	
	Project_task getTaskByID(int id);
	
	int insertNewProjectTask(Project_task projectTask);
	
	void updateProjectTask(Project_task projectTask);
	
	void delectProjectTask(String[] ids);

	List<String> getTaskByDepartment(String department);
	
	List<Map<String, Object>> getDepartmentList();
	List<Map<String, Object>> getCategoryList();
	
	int getTaskIDByCDP(Map<String, Object> params);

	int checkID(Integer taskID);

}
