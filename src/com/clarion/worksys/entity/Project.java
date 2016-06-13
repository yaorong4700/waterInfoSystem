package com.clarion.worksys.entity;

public class Project {
	private Integer projectID;
	
	private String category;
	private String directProjectID;
	private String projectName;
	private String projectClientNo;
	private String projectClientName;
	
	private String startupDate;
	private String finishDate;
	private String projectState;
	
	private String carMaker;
	
	private String function;
	private String model;
	private String transferNo;
	
	private String PJNo;// PJNo�ɲ� HSCNZJ ZZP
	private String tempPJNo;// ��PJNo�ɲ� HSCNZJ ZZP
	private String PJName;// PJName�ɲ� HSCNZJ ZZP
	private String itemName;// 3D����̾�ɲ� HSCNZJ ZZP

	private String memo;
	private String projectDepartmentID;
	private String projectDepartment = null;
	
	private String projectBranchID;
	private String projectBranch;
	
	private Integer carMakerID;
	private Integer enterpriseSegID;
	private Integer functionID;
	//20160315add
	private String projectNameOld;
	
	
	public String getProjectNameOld() {
		return projectNameOld;
	}

	public void setProjectNameOld(String projectNameOld) {
		this.projectNameOld = projectNameOld;
	}

	public Integer getCarMakerID() {
		return carMakerID;
	}

	public void setCarMakerID(Integer carMakerID) {
		this.carMakerID = carMakerID;
	}

	public Integer getEnterpriseSegID() {
		return enterpriseSegID;
	}

	public void setEnterpriseSegID(Integer enterpriseSegID) {
		this.enterpriseSegID = enterpriseSegID;
	}

	public Integer getFunctionID() {
		return functionID;
	}

	public void setFunctionID(Integer functionID) {
		this.functionID = functionID;
	}

	public String getEnterpriseSeg() {
		return enterpriseSeg;
	}

	public void setEnterpriseSeg(String enterpriseSeg) {
		this.enterpriseSeg = enterpriseSeg;
	}

	private String enterpriseSeg; // ���ȥ��������ɲ� HSCNZJ ZZP

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	
	public String getProjectClientNo() {
		return projectClientNo;
	}

	public void setProjectClientNo(String projectClientNo) {
		this.projectClientNo = projectClientNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	public String getCarMaker() {
		return carMaker;
	}

	public void setCarMaker(String carMaker) {
		this.carMaker = carMaker;
	}

	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getProjectClientName() {
		return projectClientName;
	}

	public void setProjectClientName(String projectClientName) {
		this.projectClientName = projectClientName;
	}

	/**
	 * @return the projectID
	 */
	public Integer getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	/**
	 * @return the catetoyID
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param catetoyID the catetoyID to set
	 */
	public void setCategoryID(String catetoy) {
		this.category = catetoy;
	}
	public String getDirectProjectID() {
		return directProjectID;
	}

	public void setDirectProjectID(String directProjectID) {
		this.directProjectID = directProjectID;
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
	 * @return the startupDate
	 */
	public String getStartupDate() {
		return startupDate;
	}

	/**
	 * @param startupDate the startupDate to set
	 */
	public void setStartupDate(String startupDate) {
		this.startupDate = startupDate;
	}

	/**
	 * @return the finishDate
	 */
	public String getFinishDate() {
		return finishDate;
	}

	/**
	 * @param finishDate the finishDate to set
	 */
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	/**
	 * @return the projectState
	 */
	public String getProjectState() {
		return projectState;
	}

	/**
	 * @param projectState the projectState to set
	 */
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * @return the projectDepartment
	 */
	public String getProjectDepartment() {
		return projectDepartment;
	}

	/**
	 * @param projectDepartment the projectDepartment to set
	 */
	public void setProjectDepartment(String projectDepartment) {
		this.projectDepartment = projectDepartment;
	}

	public String getProjectBranchID() {
		return projectBranchID;
	}

	public void setProjectBranchID(String projectBranchID) {
		this.projectBranchID = projectBranchID;
	}

	public String getProjectBranch() {
		return projectBranch;
	}

	public void setProjectBranch(String projectBranch) {
		this.projectBranch = projectBranch;
	}

	public String getProjectDepartmentID() {
		return projectDepartmentID;
	}

	public void setProjectDepartmentID(String projectDepartmentID) {
		this.projectDepartmentID = projectDepartmentID;
	}
}
