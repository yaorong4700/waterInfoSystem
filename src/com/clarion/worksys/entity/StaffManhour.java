package com.clarion.worksys.entity;

import java.util.List;

/**
 * 员工项目工数详细信息,用于导出用户--项目集记表格
 * @author guo_renpeng
 *
 */
public class StaffManhour {

	private String staffID;
	private String staffName;
	private String department;
	private String branch;
	List<ManhourProject> manhourProjects;
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public List<ManhourProject> getManhourProjects() {
		return manhourProjects;
	}
	public void setManhourProjects(List<ManhourProject> manhourProjects) {
		this.manhourProjects = manhourProjects;
	}
	
	
}
