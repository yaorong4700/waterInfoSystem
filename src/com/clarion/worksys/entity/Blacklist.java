package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 黑名单信息JAVABEAN
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */

public class Blacklist {
	private String staffName;    //员工姓名
	private String staffDepartment;
	private String staffBranch;
	private String staffEmail;   //员工E-mail
	private String date;         //时间
	private String totalTimes;   //总时间
	private String superior;
	private String superiorEmail;
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffDepartment() {
		return staffDepartment;
	}
	public void setStaffDepartment(String staffDepartment) {
		this.staffDepartment = staffDepartment;
	}
	public String getStaffBranch() {
		return staffBranch;
	}
	public void setStaffBranch(String staffBranch) {
		this.staffBranch = staffBranch;
	}
	public String getStaffEmail() {
		return staffEmail;
	}
	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(String totalTimes) {
		this.totalTimes = totalTimes;
	}
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	public String getSuperiorEmail() {
		return superiorEmail;
	}
	public void setSuperiorEmail(String superiorEmail) {
		this.superiorEmail = superiorEmail;
	}

	

}
