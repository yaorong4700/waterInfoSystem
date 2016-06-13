package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.DevelopDepartment;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.httpentity.DevelopDepartmentReqParam;

public interface DevelopDepartmentService {
	List<Map<String, Object>> getDepartmentList();
	List<Map<String, Object>> getDepartmentCategoryList();
	List<DevelopDepartment> searchList(DevelopDepartmentReqParam developDepartmentReqParam);
	List<DevelopDepartment> searchListBranch(DevelopDepartment developDepartment);
	List<DevelopDepartment> searchListCT(DevelopDepartmentReqParam developDepartmentReqParam);
	int totalPageCount(DevelopDepartmentReqParam developDepartmentReqParam);
	int totalPageCountCT(DevelopDepartmentReqParam developDepartmentReqParam);
	boolean insertNewDepartment(DevelopDepartment developDepartment);
	boolean insertNewDepartmentBranch(DevelopDepartment developDepartment);
	boolean insertNewDepartmentCT(DevelopDepartment developDepartment);
	boolean insertNewDepartmentBranchCT(DevelopDepartment developDepartment);
	void updateDepartment(DevelopDepartment developDepartment);
	void updateBranch(DevelopDepartment developDepartment);
	void updateBranchCT(DevelopDepartment developDepartment);
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
	
	DevelopDepartment getDevelopDepartmentSelectedCT(String belongCode, String departmentID, String branchID);
	List<Integer> getDeploymentList();
	boolean belongCodeExist(String belongCode);
	List<DevelopDepartment> downloadDepartmentShow();
	List<DevelopDepartment> downloadDepartmentShowCT();
	String downloadDepartment(List<DevelopDepartment> departmentList , String realpathString);
	String downloadDepartmentCT(List<DevelopDepartment> departmentList , String realpathString);
	List<Map<String, Object>> getBranchListMap(String departmentID);
	//List<Map<String, Object>> getTeamListMap(String belongCode);
	String getBranch(String branchInput);
	String getTeam(String teamInput);
	List<String> getTeamListMap();
	List<Map<String,Object>> getBranchListCXEE(String departmentID);
	List<Map<String,Object>> getBranchListCT(String departmentID);
	//20160309新增删除时有belongcode 获得部门ID
	List<String> getDepartmentIDByBelongCode(String[] belongCode);
	boolean checkBranchByDeparmentID(String departmentID);
	void deleteDepartmentByID(String departmentID);
	boolean departmentBranchBelongCodeExist(String department, String branch, String belongCode);
	void deleteByBelongCodeDepartmentBranch(String belongCodes, String departmentIDNew, String branchIDNew);
	String getBranchIDByBranchCT(String branch);
	List<Map<String, Object>> getDepartmentListCT();
	DevelopDepartment getDevelopDepartmentSelected(String belongCodes, String departmentID);
	DevelopDepartment getDepartment(String departmentID);
	boolean checkBranchExist(String departmentID, String branchID, String belongCode);
	void deleteByDepartmentBranch(String departmentIDNew, String branchIDNew);
	boolean checkHasBranch(String departmentID, String branchID);
	boolean checkBranchBydepartmentBranchTeam(String departmentID, String branchID, String team);
	void updateBranchByTeam(DevelopDepartment developDepartment);
	String getBranchByTeam(String branch, String team);
}
