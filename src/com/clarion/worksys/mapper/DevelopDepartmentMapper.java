package com.clarion.worksys.mapper;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.DevelopDepartment;
import com.clarion.worksys.httpentity.DevelopDepartmentReqParam;

public interface DevelopDepartmentMapper {

	
	
	List<Map<String, Object>> getDepartmentList();
	List<Map<String, Object>> getDepartmentCategoryList();
	List<DevelopDepartment> searchList(DevelopDepartmentReqParam developDepartmentReqParam);
	List<DevelopDepartment> searchListBranch(DevelopDepartment developDepartment);
	int getTotalPageCount(DevelopDepartmentReqParam developDepartmentReqParam);
	List<DevelopDepartment> searchListCT(DevelopDepartmentReqParam developDepartmentReqParam);
	int getTotalPageCountCT(DevelopDepartmentReqParam developDepartmentReqParam);
	
	int insertNewDepartment(DevelopDepartment developDepartment);
	int insertNewDepartmentBranch(DevelopDepartment developDepartment);
	int insertNewDepartmentCT(DevelopDepartment developDepartment);
	int insertNewDepartmentBranchCT(DevelopDepartment developDepartment);
	void updateDepartment(DevelopDepartment developDepartment);
	void updateDepartmentCT(DevelopDepartment developDepartment);
	void deleteByBelongCode(String[] belongCode);
	void deleteByBelongCodeCT(String[] belongCode);
	int getMaxBranchID();
	int getMaxBranchIDCT();
	int getMaxDepartmentID();
	int getMaxDepartmentIDCT();
	String getBranchID(String branch);
	String getBranchIDCT(String belongCode);
	String getDepartmentID(String department);
	String getDepartmentIDCT(String department);
	
	DevelopDepartment getDevelopDepartmentSelected( String departmentID,String branchID);
	DevelopDepartment getDevelopDepartmentSelectedCT(String belongCode, String departmentID, String branchID);
	List<Integer> getDeploymentList();
	int belongCodeExist(String belongCode);
	void updateBranch(DevelopDepartment developDepartment);
	void updateBranchCT(DevelopDepartment developDepartment);
	List<DevelopDepartment> downloadDepartmentShow();
	List<DevelopDepartment> downloadDepartmentShowCT();
	List<Map<String, Object>> getBranchListMap(String departmentID);
	//List<Map<String, Object>> getTeamListMap(String belongCode);
	String getBranch(String branchInput);
	String getTeam(String teamInput);
	List<String> getTeamListMap();
	//20160309新增，删除时由belongcode获得部门ID
	List<String> getDepartmentIDByBelongCode(String[] belongCode);
	int getBranchCount(String departmentID);
	void deleteDepartmentByID(String departmentID);
	//20160426 add
	int checkBranchExist(String department, String branch, String belongCode);
	void deleteByBelongCodeDepartmentBranch(String belongCode, String department, String branch);
	String getBranchIDByBranchCT(String branch);
	List<Map<String, Object>> getDepartmentListCT();
	DevelopDepartment getDepartment(String departmentID);
	int checkBranch(String departmentID, String branchID, String belongCode);
	void deleteByDepartmentBranch(String departmentIDNew, String branchIDNew);
	int getBranchCounts(String departmentID, String branchID);
	int getBranchCountByTeam(String departmentID, String branchID, String team);
	void updateBranchByTeam(DevelopDepartment developDepartment);
	String getBranchByTeam(String branch, String team);
}
