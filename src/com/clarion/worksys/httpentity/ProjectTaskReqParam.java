/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 作业类型管理分页请求参数
 * 
 * @author weng_zhangchu@clarion.com.cn
 * @create: 2013-3-19
 * @histroy:
 * 	2013-3-19 weng_zhangchu
 *  # 初版
 *   
 */
package com.clarion.worksys.httpentity;

/**
 * @author weng_zhangchu
 *
 */
public class ProjectTaskReqParam {
	private int page;
	private int rp;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;
	private int pageSeq;
	
	private String category;
	private String task;
	private String department;
	private String departmentID;
	private String DepartmentFlag;
	public String getDepartmentFlag() {
		return DepartmentFlag;
	}
	public void setDepartmentFlag(String departmentFlag) {
		DepartmentFlag = departmentFlag;
	}
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the rp
	 */
	public int getRp() {
		return rp;
	}
	/**
	 * @param rp the rp to set
	 */
	public void setRp(int rp) {
		this.rp = rp;
	}
	/**
	 * @return the sortname
	 */
	public String getSortname() {
		return sortname;
	}
	/**
	 * @param sortname the sortname to set
	 */
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	/**
	 * @return the sortorder
	 */
	public String getSortorder() {
		return sortorder;
	}
	/**
	 * @param sortorder the sortorder to set
	 */
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the qtype
	 */
	public String getQtype() {
		return qtype;
	}
	/**
	 * @param qtype the qtype to set
	 */
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	/**
	 * @return the pageSeq
	 */
	public int getPageSeq() {
		return pageSeq;
	}
	/**
	 * @param pageSeq the pageSeq to set
	 */
	public void setPageSeq(int pageSeq) {
		this.pageSeq = pageSeq;
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
	
}
