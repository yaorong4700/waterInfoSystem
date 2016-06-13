package com.clarion.worksys.entity;

import java.util.Date;


/**
 * Copyright(C) 2014 HSCN Corp. All rights reserved.
 * 
 * 人员权限管理javaBean
 * 
 * @author HSCN 张贤良
 * @create: 2014-06-03
 * @histroy:
 * 2014-06-03 HSCN 张贤良
 *  # 初版
 *   
 */
public class UserRoleManage {
	private String No;
	private String keyCode;         				//采番
	private String roleName;						//角色名	
	private String pageID;		    				//画面ID
	private String pageName;						//画面名
	private String showRoleFlag;                    //查看权限
	private String queryRoleFlag;   				//检索权限
	private String alterRoleFlag;   				//编辑权限
	private String uploadRoleFlag;   				//上传权限
	private String downloadRoleFlag;   				//下载权限
	private String specialRole1Flag;				//特殊权限1
	private String specialRole2Flag;				//特殊权限2
	private String specialRole3Flag;				//特殊权限3
	private String specialRole1;					//特殊权限1名
	private String specialRole2;					//特殊权限2名
	private String specialRole3;					//特殊权限3名
	private String departmentFlag;					//部门标识
	private String deleteFlag;      				//删除标识
	private String insertUser;      				//登录人员
	private Date insertDate;      					//登录日期
	private String updateUser;      				//更新人员
	private Date updateDate;      					//更新日期
	private String flag;      					//更新日期
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getNo() {
		return No;
	}
	public void setNo(String No) {
		this.No = No;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPageID() {
		return pageID;
	}
	public void setPageID(String pageID) {
		this.pageID = pageID;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getQueryRoleFlag() {
		return queryRoleFlag;
	}
	public void setQueryRoleFlag(String queryRoleFlag) {
		this.queryRoleFlag = queryRoleFlag;
	}
	public String getAlterRoleFlag() {
		return alterRoleFlag;
	}
	public void setAlterRoleFlag(String alterRoleFlag) {
		this.alterRoleFlag = alterRoleFlag;
	}
	public String getSpecialRole1Flag() {
		return specialRole1Flag;
	}
	public void setSpecialRole1Flag(String specialRole1Flag) {
		this.specialRole1Flag = specialRole1Flag;
	}
	public String getSpecialRole2Flag() {
		return specialRole2Flag;
	}
	public void setSpecialRole2Flag(String specialRole2Flag) {
		this.specialRole2Flag = specialRole2Flag;
	}
	public String getSpecialRole1() {
		return specialRole1;
	}
	public void setSpecialRole1(String specialRole1) {
		this.specialRole1 = specialRole1;
	}
	public String getSpecialRole2() {
		return specialRole2;
	}
	public void setSpecialRole2(String specialRole2) {
		this.specialRole2 = specialRole2;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getInsertUser() {
		return insertUser;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDepartmentFlag() {
		return departmentFlag;
	}
	public void setDepartmentFlag(String departmentFlag) {
		this.departmentFlag = departmentFlag;
	}
	public String getSpecialRole3Flag() {
		return specialRole3Flag;
	}
	public void setSpecialRole3Flag(String specialRole3Flag) {
		this.specialRole3Flag = specialRole3Flag;
	}
	public String getSpecialRole3() {
		return specialRole3;
	}
	public void setSpecialRole3(String specialRole3) {
		this.specialRole3 = specialRole3;
	}
	public String getShowRoleFlag() {
		return showRoleFlag;
	}
	public void setShowRoleFlag(String showRoleFlag) {
		this.showRoleFlag = showRoleFlag;
	}
	public String getUploadRoleFlag() {
		return uploadRoleFlag;
	}
	public void setUploadRoleFlag(String uploadRoleFlag) {
		this.uploadRoleFlag = uploadRoleFlag;
	}
	public String getDownloadRoleFlag() {
		return downloadRoleFlag;
	}
	public void setDownloadRoleFlag(String downloadRoleFlag) {
		this.downloadRoleFlag = downloadRoleFlag;
	}
	
	
	
}
