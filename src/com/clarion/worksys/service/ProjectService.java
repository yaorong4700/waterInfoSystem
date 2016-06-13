/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * Project_taskService
 * 
 * @author weng_zhangchu@clarion.com.cn
 * @create: 2012-12-24
 * @histroy:
 * 	2012-12-24 weng_zhangchu
 *  # 初版
 *  2013-3-19 wengzhangchu
 *  第二版
 */
package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Project_category;
import com.clarion.worksys.entity.Stage;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.httpentity.ProjectReqParam;

public interface ProjectService {
	List<Project> listSelectProject(int staffID,String department);
	
	List<Project> recentProject(int staffID,String department);
	
	List<Project> recentProjectOther(int staffID);
	
	List<Project> getProjectByCategory(String category);
	
	String getProjectNameByProjectID(int projectID);

	List<Map<String,Object>> listTask(String category,String department);
	
	List<Project> showAll(ProjectReqParam projectReqParam);
	
	List<Project> showAllForDepartment(ProjectReqParam projectReqParam);
	
	List<Project> showAllCTCXEE(ProjectReqParam projectReqParam);

	int totalPageCount(ProjectReqParam projectReqParam);
	
	int totalPageCountForDepartment(ProjectReqParam projectReqParam);
	int totalPageCountCTCXEE(ProjectReqParam projectReqParam);
	
	Project getProjectById(int projectID);
	
	Project getProjectByIdCXEE(int projectID);
	
	Project getProjectByIdCT(int projectID);
	
	boolean insertNewProject(Project project);
	
	void updateProject(Project project);
	
	void updateProjectCT(Project project);
	
	void delectProject(String[] ids);
	
	void delectProjectCT(String[] ids);
	
	void callinsertPD(String project,String category,String departmentID);
	
	void callinsertPDCXEE(String project,String category,String departmentID);
	
	void calldeletePD(String project,String category);
	
	void calldeletePDCXEE(String project,String category);
	
	void callinsertDIANQI(String project,String category);
	void callinsertJIGOU(String project,String category);
	void callinsertSoft(String project,String category);
	void callinsertQuality(String project,String category);
	void callinsertGOUXIANG(String project,String category);
	void callinsertGONGCHENG(String project,String category);
	void callinsertSHANGPIN(String project,String category);
	void callinsertKAIFAGUANLI(String project,String category);
	void callinsertYUANJIA(String project,String category);
	void callinsertSHEJIKAIFA(String project,String category);
	void callinsertSHEJIKAIFAKAIFAGUANLI(String project,String category);
	void callinsertKAIFATONGKUO(String project,String category);
	void callinsertCHINAZHIPINKAIFA(String project, String category);
	
	List<Project> getProjectDepartmentByProjectID(int projectID);
	
	List<Project> getProjectDepartmentByProjectIDCXEE(int projectID);
	
	List<String> listCategorySelect();
	List<String> listProjectClientSelect();
	List<String> listFunctionListSelect();
	List<String> listFunctionListSelectCT();
	List<Project> listAllStartupProject(int staffID);
	
	List<Project> getProjectByDepartment(String departmentID,String category);
	
	List<Project> listProject();
	
	List<Project> showAllProject();//黑名单导出所有项目
	
	void calldeletePB(String project,String category);
	void calldeletePBCXEE(String project,String category);
	void callinsertProjectBranch(String project,String category,String branch);
	void callinsertProjectBranchCXEE(String project,String category,String branch);
	
	List<Project> getProjectBranchByProjectID(int projectID);
	List<Project> showAllForBranch(ProjectReqParam projectReqParam);	
	int totalPageCountForBranch(ProjectReqParam projectReqParam);
	
	List<Project> listSelectProjectByBranchID(int staffID,String departmentID, String branch);
	List<Project> recentProjectByBranchID(int staffID,String departmentID, String branch);
	
	int getProjectIDByProjectName(String projectName);
	int totalPageCountCT(ProjectReqParam projectReqParam);
	List<Project> showAllCT(ProjectReqParam projectReqParam);
	void deletePDByProjectID(Integer projectID);
	void deletePDByProjectIDCT(Integer projectID);
	void insertPDByProjectID(Integer projectID,Integer departmentID);
	void insertPDByProjectIDCT(Integer projectID,Integer departmentID);
	List<Project> downLoadShowAllProject();
	List<Project> downLoadShowAllProjectCT();
	String downloadProject(List<Project> projectList, String realpathString);
	String downloadProjectCT(List<Project> projectList, String realpathString);
	List<Project> getAllProClientName();
	List<Project> getAllProFunction();
	List<Project> getAllProFunctionCT();
	List<Project> getAllProCarMaker();
	List<Project> getAllProCarMakerCT();
	List<Project> getAllTransferNo();
	List<Stage> getAllStage();
	List<Stage> getAllCategoryCT();
	int totalPageCountCommon(ProjectReqParam projectReqParam);
	int totalPageCountCommonCT(ProjectReqParam projectReqParam);
	List<Project> showAllCommon(ProjectReqParam projectReqParam);
	List<Project> showAllCommonCT(ProjectReqParam projectReqParam);
	List<Project> downLoadShowAllProjectCommon();
	String downloadProjectCommon(List<Project> projectList, String realpathString);
	void deleteProBranchByProjectID(Integer projectID);
	void insertProBranchByProjectID(Integer projectID,Integer branchID);
	List<Project> getProjectDepartmentByProjectIDCT(int projectID);
	List<Project> getProjectBranchByProjectIDCT(int projectID);
	boolean insertNewProjectCT(Project project);
	//20160310
	List<Project> getAllPJNo();
	List<Project> projectNameSelect(String PJNo);
	List<Project> getAllTempPJNo();
	List<Project> projectNameSelectByTempPJNo(String tempPJNo);
	//20160315
	List<Map<String,Object>> getAllBranch();

	List<Project> projectNameSelectCXEE(String PJNo);
}
