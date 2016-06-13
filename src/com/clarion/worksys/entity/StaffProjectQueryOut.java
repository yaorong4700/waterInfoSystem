/**
 * 
 */
package com.clarion.worksys.entity;

/**
 * 根据员工项目查询的工数数据所得到的数据类
 * @author weng_zhangchu
 *
 */
public class StaffProjectQueryOut {
	private String projectName;
	
	private String category;
	
	private float totalTime;

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the totalTime
	 */
	public float getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
	
	

}
