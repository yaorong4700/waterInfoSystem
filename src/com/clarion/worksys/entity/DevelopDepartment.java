package com.clarion.worksys.entity;

public class DevelopDepartment {
	
	private String departmentID;					//部门ID
	private String department;						//部门
	private String departmentCategory;				//部门分类
	private String belongCode;						//在籍归属code
	private String branch;							//课别
	private String team;							//组别	
	private String memo;							//部门备注
	private String branchMemo;						//课别备注
	private String deployment;						//现旧部署
	private String belong;							//归属
	private String branchID;						//课别ID
	private String branchDeployment;				//课别现旧部署
	
	private String type;							//参数类型 edit,add
	private String belongCodeOld;					//旧的belongCode
	private String departmentIDOld;					//旧的部门
	private String branchOld;						//旧的课别
	private String branchIDOld;						//旧的课别ID
	
	
	
	
	
	public String getBranchDeployment() {
		return branchDeployment;
	}
	public void setBranchDeployment(String branchDeployment) {
		this.branchDeployment = branchDeployment;
	}
	public String getBranchMemo() {
		return branchMemo;
	}
	public void setBranchMemo(String branchMemo) {
		this.branchMemo = branchMemo;
	}
	public String getBranchIDOld() {
		return branchIDOld;
	}
	public void setBranchIDOld(String branchIDOld) {
		this.branchIDOld = branchIDOld;
	}
	public String getBranchOld() {
		return branchOld;
	}
	public void setBranchOld(String branchOld) {
		this.branchOld = branchOld;
	}
	public String getDepartmentIDOld() {
		return departmentIDOld;
	}
	public void setDepartmentIDOld(String departmentIDOld) {
		this.departmentIDOld = departmentIDOld;
	}
	public String getBelongCodeOld() {
		return belongCodeOld;
	}
	public void setBelongCodeOld(String belongCodeOld) {
		this.belongCodeOld = belongCodeOld;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDeployment() {
		return deployment;
	}
	public void setDeployment(String deployment) {
		this.deployment = deployment;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartmentCategory() {
		return departmentCategory;
	}
	public void setDepartmentCategory(String departmentCategory) {
		this.departmentCategory = departmentCategory;
	}
	public String getBelongCode() {
		return belongCode;
	}
	public void setBelongCode(String belongCode) {
		this.belongCode = belongCode;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	
}
