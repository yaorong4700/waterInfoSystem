/**
 * 
 */
package com.clarion.worksys.entity;

/**
 * 根据员工项目查询输入的参数类
 * @author weng_zhangchu
 *
 */
public class StaffProjectQueryIn {
	private int staffID;
	
	private String startDate;
	
	private String endDate;

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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
