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
public class WaterInfo {
	
	private String regionID;
	private String deviceID;
	private String collectTime;
	private String waterPic;
	private String waterNumber;
	private String identifyFlag;
	private String insertUser;
	private String insertTime;
	private String updateUser;
	private String updateTime;
	private String deviceAddress;
	private String regionSummary;
	
	private String codeValue;
	

	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	private int total;
	

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getRegionSummary() {
		return regionSummary;
	}
	public void setRegionSummary(String regionSummary) {
		this.regionSummary = regionSummary;
	}
	public String getRegionID() {
		return regionID;
	}
	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getWaterPic() {
		return waterPic;
	}
	public void setWaterPic(String waterPic) {
		this.waterPic = waterPic;
	}
	public String getWaterNumber() {
		return waterNumber;
	}
	public void setWaterNumber(String waterNumber) {
		this.waterNumber = waterNumber;
	}
	public String getIdentifyFlag() {
		return identifyFlag;
	}
	public void setIdentifyFlag(String identifyFlag) {
		this.identifyFlag = identifyFlag;
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
	public String getDeviceAddress() {
		return deviceAddress;
	}
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	
}
