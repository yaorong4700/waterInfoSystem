package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理界面Select列表
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */
import java.util.List;
import java.util.Map;

public class StaffSelectInfo {
	List<Map<String, Object>> positionList;
	List<Map<String, Object>> departmentList;
	List<String> branchList;
	List<String> teamList;
	List<String> leadList;
	List<Map<String, Object>> sortList;
	List<Map<String, Object>> roleList;
	List<Map<String, Object>> jobCategoryList;
	

	public List<Map<String, Object>> getJobCategoryList() {
		return jobCategoryList;
	}
	public void setJobCategoryList(List<Map<String, Object>> jobCategoryList) {
		this.jobCategoryList = jobCategoryList;
	}
	public List<Map<String, Object>> getPositionList() {
		return positionList;
	}
	public void setPositionList(List<Map<String, Object>> positionList) {
		this.positionList = positionList;
	}

	public List<Map<String, Object>> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<Map<String, Object>> departmentList) {
		this.departmentList = departmentList;
	}
	public List<String> getBranchList() {
		return branchList;
	}
	public void setBranchList(List<String> branchList) {
		this.branchList = branchList;
	}
	public List<String> getTeamList() {
		return teamList;
	}
	public void setTeamList(List<String> teamList) {
		this.teamList = teamList;
	}
	public List<String> getLeadList() {
		return leadList;
	}
	public void setLeadList(List<String> leadList) {
		this.leadList = leadList;
	}
	public List<Map<String, Object>> getSortList() {
		return sortList;
	}
	public void setSortList(List<Map<String, Object>> sortList) {
		this.sortList = sortList;
	}
	public List<Map<String, Object>> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Map<String, Object>> roleList) {
		this.roleList = roleList;
	}
	

}
