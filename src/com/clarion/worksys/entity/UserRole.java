package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 用户信息javaBean
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */
public class UserRole {
	private String keyCode;
	private String userID;
	private String userName;
	private String userPassword;
	private String userValidFlag;
	private String insertUser;
	private String insertTime;
	private String updateUser;
	private String updateTime;
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserValidFlag() {
		return userValidFlag;
	}
	public void setUserValidFlag(String userValidFlag) {
		this.userValidFlag = userValidFlag;
	}
	public String getInsertUser() {
		return insertUser;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
