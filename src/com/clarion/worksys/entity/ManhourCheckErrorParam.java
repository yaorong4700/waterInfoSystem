package com.clarion.worksys.entity;

public class ManhourCheckErrorParam {
	private int page;         //当前的页数
	private int rp;           //选择的页面显示数据条数
	private String sortname;  //排序的列名
	private String sortorder; //排序的方式（asc desc）
	private String query;     //搜索框输入的值
	private String qtype;     //搜索的类型
	private int pageSeq;      //页面序数（即选择的当前页面应该跳过的页面所含数据条数）
	private int createStaffID;
	private String name;
	private String department;
	private String branch;
	private String departmentID;
	private String branchID;
	private String startDate;
	private String endDate;
	private String serachSuperior;
	private String kaisya;
	
	//20160317 QueryRoleFlag
	
	private String QueryRoleFlag;
	
	public String getQueryRoleFlag() {
		return QueryRoleFlag;
	}

	public void setQueryRoleFlag(String queryRoleFlag) {
		QueryRoleFlag = queryRoleFlag;
	}

	/**
	 * @return the kaisya
	 */
	public String getKaisya() {
		return kaisya;
	}

	/**
	 * @param kaisya the branch to set
	 */
	public void setKaisya(String kaisya) {
		this.kaisya = kaisya;
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
	 * @return the createStaffID
	 */
	public int getCreateStaffID() {
		return createStaffID;
	}

	/**
	 * @param createStaffID the createStaffID to set
	 */
	public void setCreateStaffID(int createStaffID) {
		this.createStaffID = createStaffID;
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
	 * @return the serachSuperior
	 */
	public String getSerachSuperior() {
		return serachSuperior;
	}

	/**
	 * @param serachSuperior the serachSuperior to set
	 */
	public void setSerachSuperior(String serachSuperior) {
		this.serachSuperior = serachSuperior;
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
