/**
 * 
 */
package com.clarion.worksys.entity;

/**
 * @author weng_zhangchu
 * 个人查询返回的工数统计类
 */
public class ManhourPersonalQueryView {
	private String category;
	private String projectName;
	private String taskName;
	private double totalTimes;
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
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
}
