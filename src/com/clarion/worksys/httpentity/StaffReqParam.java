package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理界面分页请求参数
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */

public class StaffReqParam {
	private int page;
	private int rp;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;
	private int pageSeq;
	private String staffName;
	private String position;
	private String branch;
	private String department;
	private String superior;
	private String stateName;
	private int departmentID;
	private int branchID;
	private String dateQuitCompany;
	private String dateQuitCompanyEnd;
	private String isYMDFlag;
	//20160315add
	private String belong;
	private String QueryRoleFlag;//查询权限
	
	
	public String getQueryRoleFlag() {
		return QueryRoleFlag;
	}
	public void setQueryRoleFlag(String queryRoleFlag) {
		QueryRoleFlag = queryRoleFlag;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRp() {
		return rp;
	}
	public void setRp(int rp) {
		this.rp = rp;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public int getPageSeq() {
		return pageSeq;
	}
	public void setPageSeq(int pageSeq) {
		this.pageSeq = pageSeq;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public int getBranchID() {
		return branchID;
	}
	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}
	public String getDateQuitCompany() {
		return dateQuitCompany;
	}
	public void setDateQuitCompany(String dateQuitCompany) {
		this.dateQuitCompany = dateQuitCompany;
	}
	public String getDateQuitCompanyEnd() {
		return dateQuitCompanyEnd;
	}
	public void setDateQuitCompanyEnd(String dateQuitCompanyEnd) {
		this.dateQuitCompanyEnd = dateQuitCompanyEnd;
	}
	public String getIsYMDFlag() {
		return isYMDFlag;
	}
	public void setIsYMDFlag(String isYMDFlag) {
		this.isYMDFlag = isYMDFlag;
	}
}
