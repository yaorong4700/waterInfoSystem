package com.clarion.worksys.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Project_category;
import com.clarion.worksys.entity.Stage;
import com.clarion.worksys.httpentity.ProjectReqParam;

public interface ProjectMapper {
	
	List<Project> listAllProject(Map<String, Object> params);
	
	List<Project> recentProject(Map<String, Object> params);
	
	List<Project> recentProjectOther(int staffID);
	
	List<Project> getProjectByCategory(String category);
	
	String getProjectNameByProjectID(int projectID);
	
	List<Map<String,Object>> listTask(Map<String, Object> params);
	
	List<Project> showAll(ProjectReqParam projectReqParam);
	
	List<Project> showAllForDepartment(ProjectReqParam projectReqParam);
	
	List<Project> showAllCTCXEE(ProjectReqParam projectReqParam);

	int totalPageCount(ProjectReqParam projectReqParam);
	
	int totalPageCountCTCXEE(ProjectReqParam projectReqParam);
	
	int totalPageCountForDepartment(ProjectReqParam projectReqParam);
	
	Project getProjectByIdCXEE(int projectID);
	Project getProjectByIdCT(int projectID);
	
	int insertNewProject(Project project);
	
	int insertNewProjectCT(Project project);
	
	void updateProject(Project project);
	
	void updateProjectCT(Project project);
	
	void delectProject(String[] ids);
	
	void delectProjectCT(String[] ids);
	
	void callinsertPD(Map<String, Object> paramMap);
	
	void callinsertPDCXEE(Map<String, Object> paramMap);
	
	void calldeletePD(Map<String, Object> paramMap);
	
	void calldeletePDCXEE(Map<String, Object> paramMap);
	
	void callinsertDIANQI(Map<String, Object> paramMap);
	
	void callinsertJIGOU(Map<String, Object> paramMap);
	
	void callinsertSoft(Map<String, Object> paramMap);
	
	void callinsertQuality(Map<String, Object> paramMap);
	
	void callinsertGOUXIANG(Map<String, Object> paramMap);
	
	void callinsertGONGCHENG(Map<String, Object> paramMap);
	
	void callinsertSHANGPIN(Map<String, Object> paramMap);
	
	void callinsertKAIFAGUANLI(Map<String, Object> paramMap);
	
	void callinsertYUANJIA(Map<String, Object> paramMap);
	
	void callinsertSHEJIKAIFA(Map<String, Object> paramMap);
	
	void callinsertSHEJIKAIFAKAIFAGUANLI(Map<String, Object> paramMap);
	
	void callinsertKAIFATONGKUO(Map<String, Object> paramMap);
	
	void callinsertCHINAZHIPINKAIFA(Map<String, Object> paramMap);
	
	List<Project> getProjectDepartmentByProjectID(int projectID);
	
	List<Project> getProjectDepartmentByProjectIDCXEE(int projectID);
	
	List<String> listCategorySelect();
	List<String> listProjectClientSelect();
	List<String> listFunctionListSelect();
	List<String> listFunctionListSelectCT();
	List<Project> listAllStartupProject(int staffID);
	
	List<Project> getProjectByDepartment(Map<String, Object> paraMap);
	
	List<Project> listProject();
	
	List<Project> showAllProject();//黑名单导出所有项目
	
	void calldeletePB(Map<String, Object> paramMap);
	void calldeletePBCXEE(Map<String, Object> paramMap);
	void callinsertProjectBranch(Map<String, Object> paramMap);
	void callinsertProjectBranchCXEE(Map<String, Object> paramMap);
	List<Project> getProjectBranchByProjectID(int projectID);
	List<Project> showAllForBranch(ProjectReqParam projectReqParam);	
	int totalPageCountForBranch(ProjectReqParam projectReqParam);
	List<Project> listAllProjectByBranchID(Map<String, Object> params);
	List<Project> recentProjectByBranchID(Map<String, Object> params);
	
	int getProjectIDByProjectName(String projectName);
	int totalPageCountCT(ProjectReqParam projectReqParam);
	List<Project> showAllCT(ProjectReqParam projectReqParam);
	
	void deletePDByProjectID(Integer projectID);
	void deletePDByProjectIDCT(Integer projectID);
	void insertPDByProjectID(Map<String, Object> paramMap);
	void insertPDByProjectIDCT(Map<String, Object> paramMap);
	List<Project>  downLoadShowAllProject();
	List<Project>  downLoadShowAllProjectCT();
	List<Project> getAllProClientName();
	List<Project> getAllProFunction();
	List<Project> getAllProFunctionCT();
	List<Project> getAllProCarMaker();
	List<Project> getAllProCarMakerCT();
	List<Project> getAllTransferNo();
	List<Stage> getAllStage();
	List<Stage> getAllCategoryCT();
	
	int totalPageCountCommon(ProjectReqParam projectReqParam);
	List<Project> showAllCommon(ProjectReqParam projectReqParam);
	List<Project> downLoadShowAllProjectCommon();
	void deleteProBranchByProjectID(Integer projectID);
	void insertProBranchByProjectID(Map<String, Object> paramMap);
	List<Project> getProjectDepartmentByProjectIDCT(int projectID);
	List<Project> getProjectBranchByProjectIDCT(int projectID);
	int totalPageCountCommonCT(ProjectReqParam projectReqParam);
	List<Project> showAllCommonCT(ProjectReqParam projectReqParam);
	List<Project> getAllPJNo();
	List<Project> getPJName(String PJNo);
	List<Project> getAllTempPJNo();
	List<Project> getPJNameByTempPJNo(String tempPJNo);
	List<Map<String,Object>> getAllBranch();

	List<Project> getPJNameCXEE(String PJNo);
}
