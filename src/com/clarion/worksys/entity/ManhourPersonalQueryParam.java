package com.clarion.worksys.entity;

/**
 * 个人查询参数
 * @author weng_zhangchu
 *
 */
public class ManhourPersonalQueryParam {
	private int page; //当前的页数
	private int rp;  //选择的页面显示数据条数
	private String sortname;//排序的列名
	private String sortorder;//排序的方式（asc desc）
	private String query;//搜索框输入的值
	private String qtype;//搜索的类型
	private int pageSeq;  //页面序数（即选择的当前页面应该跳过的页面所含数据条数）
	private String category;  //搜索框中查询项目类别
	private String projectName;//搜索框中查询项目名称
	private String startYear; 
	private String startMonth;
	private String endYear;
	private String endMonth;
	private String startDate;
	private String endDate;
	private int staffID;//个人查询的员工id
	
	//工数集记查询，包括部门和课别查询
	private String department;
	private String departmentID;
	private String branchID;
	private String branch;
	private String name;//员工姓名
	private String projectClientName;
	private String function;
	private String task;
	private String DepartmentFlag;
	public String getDepartmentFlag() {
		return DepartmentFlag;
	}
	public void setDepartmentFlag(String departmentFlag) {
		DepartmentFlag = departmentFlag;
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
	 * @return the startYear
	 */
	public String getStartYear() {
		return startYear;
	}
	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	/**
	 * @return the startMonth
	 */
	public String getStartMonth() {
		return startMonth;
	}
	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	/**
	 * @return the endYear
	 */
	public String getEndYear() {
		return endYear;
	}
	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	/**
	 * @return the endMonth
	 */
	public String getEndMonth() {
		return endMonth;
	}
	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
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
	 * @return the projectClientName
	 */
	public String getProjectClientName() {
		return projectClientName;
	}
	/**
	 * @param projectClientName the projectClientName to set
	 */
	public void setProjectClientName(String projectClientName) {
		this.projectClientName = projectClientName;
	}
	/**
	 * @return the functiong
	 */
	public String getFunction() {
		return function;
	}
	/**
	 * @param functiong the functiong to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
}
