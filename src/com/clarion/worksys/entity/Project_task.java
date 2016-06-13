/**
 * 
 */
package com.clarion.worksys.entity;

/**
 * @author weng_zhangchu
 *
 */
public class Project_task {
	public Integer taskID;
	
	public String category;
	
	public Integer categoryID;
	
	public String task;
	
	public String activity;
	
	public String memo;
	
	public String department;
	
	public String taskProcessID;
	
	public String isVisible;
	
	public Integer No;

	/**
	 * @return the taskID
	 */
	public Integer getTaskID() {
		return taskID;
	}

	/**
	 * @param taskID the taskID to set
	 */
	public void setTaskID(int taskID) {
		this.taskID = taskID;
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
	 * @return the task
	 */
	public String getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
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
	 * @param taskID the taskID to set
	 */
	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}

	public String getTaskProcessID() {
		return taskProcessID;
	}

	public void setTaskProcessID(String taskProcessID) {
		this.taskProcessID = taskProcessID;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getNo() {
		return No;
	}

	public void setNo(Integer no) {
		No = no;
	}
	
  
}
