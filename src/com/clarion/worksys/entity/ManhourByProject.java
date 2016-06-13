/**
 * 
 */
package com.clarion.worksys.entity;

/**
 * @author weng_zhangchu
 *
 */
public class ManhourByProject {
	private int staffID;
	private String staffName;
	private String department;
	private String branch;
	private String new_branch;
	private String Email;
	private double devTime;
	private double otherTime;
	private double totalTimes;

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the staffID
	 */
	public int getStaffID() {
		return staffID;
	}

	/**
	 * @param staffID the staffID to set
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	/**
	 * @return the staffName
	 */
	public String getStaffName() {
		return staffName;
	}

	/**
	 * @param staffName the staffName to set
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the new_branch
	 */
	public String getNew_branch() {
		return new_branch;
	}

	/**
	 * @param new_branch the new_branch to set
	 */
	public void setNew_branch(String new_branch) {
		this.new_branch = new_branch;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * @return the devTime
	 */
	public double getDevTime() {
		return devTime;
	}

	/**
	 * @param devTime the devTime to set
	 */
	public void setDevTime(double devTime) {
		this.devTime = devTime;
	}

	/**
	 * @return the otherTime
	 */
	public double getOtherTime() {
		return otherTime;
	}

	/**
	 * @param otherTime the otherTime to set
	 */
	public void setOtherTime(double otherTime) {
		this.otherTime = otherTime;
	}

	/**
	 * @return the totalTimes
	 */
	public double getTotalTimes() {
		return totalTimes;
	}

	/**
	 * @param totalTimes the totalTimes to set
	 */
	public void setTotalTimes(double totalTimes) {
		this.totalTimes = totalTimes;
	}
}
