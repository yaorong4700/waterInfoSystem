/**
 * 项目管理分页所传入的参数
 */
package com.clarion.worksys.httpentity;

public class ProjectReqParam {
	private int page;
	private int rp;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;
	private int pageSeq;
	
	private String category;
	private String projectName;
	private String projectClientName;
	private String function;
	private String department;//搜索的部门
	private String transferNo;
	private String projectClientNo;
	private String branch;
	private String PJNo;// PJNo追加 
	private String tempPJNo;// 仮PJNo追加 
	private String PJName;// PJName追加 
	private String itemName;// 3D項番名追加 
	private Integer enterpriseSegID;//事业单元追加
	private String carMaker;//车厂
	private String projectState;//项目状态
	private String funtion;
	
	
	
	
	//20160317QueryRoleFlag
	private String QueryRoleFlag;
	private String SpecialRole1Flag;
	private String SpecialRole2Flag;
	private String DepartmentFlag;
	private String departmentID;
	
	
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getDepartmentFlag() {
		return DepartmentFlag;
	}
	public void setDepartmentFlag(String departmentFlag) {
		DepartmentFlag = departmentFlag;
	}
	public String getSpecialRole1Flag() {
		return SpecialRole1Flag;
	}
	public void setSpecialRole1Flag(String specialRole1Flag) {
		SpecialRole1Flag = specialRole1Flag;
	}
	public String getSpecialRole2Flag() {
		return SpecialRole2Flag;
	}
	public void setSpecialRole2Flag(String specialRole2Flag) {
		SpecialRole2Flag = specialRole2Flag;
	}
	public String getQueryRoleFlag() {
		return QueryRoleFlag;
	}
	public void setQueryRoleFlag(String queryRoleFlag) {
		QueryRoleFlag = queryRoleFlag;
	}
	public String getFuntion() {
		return funtion;
	}
	public void setFuntion(String funtion) {
		this.funtion = funtion;
	}
	public String getPJNo() {
		return PJNo;
	}
	public void setPJNo(String pJNo) {
		PJNo = pJNo;
	}
	public String getTempPJNo() {
		return tempPJNo;
	}
	public void setTempPJNo(String tempPJNo) {
		this.tempPJNo = tempPJNo;
	}
	public String getPJName() {
		return PJName;
	}
	public void setPJName(String pJName) {
		PJName = pJName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getEnterpriseSegID() {
		return enterpriseSegID;
	}
	public void setEnterpriseSegID(Integer enterpriseSegID) {
		this.enterpriseSegID = enterpriseSegID;
	}
	public String getCarMaker() {
		return carMaker;
	}
	public void setCarMaker(String carMaker) {
		this.carMaker = carMaker;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
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
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}
	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
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
	 * @return the projectClientNo
	 */
	public String getProjectClientNo() {
		return projectClientNo;
	}
	/**
	 * @param projectClientNo the projectClientNo to set
	 */
	public void setProjectClientNo(String projectClientNo) {
		this.projectClientNo = projectClientNo;
	}
	public String getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	
}
