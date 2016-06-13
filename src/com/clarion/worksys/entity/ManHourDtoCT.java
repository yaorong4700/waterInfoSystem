package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数信息JAVABEAN 用于界面数据展示,用于两张表关联
 *   
 */
public class ManHourDtoCT {
	private int    id;
	private int    staffID;
	private String date;
	private String category;
	private int    projectID;
	private String task;
	private int    taskRate;
	private int    taskID;
	private double  times;
	private String memo;
	private String projectName;
	private String carMaker;
	private int componentid;
	private String componentName;
	
	public String getCarMaker() {
		return carMaker;
	}
	public void setCarMaker(String carMaker) {
		this.carMaker = carMaker;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStaffID() {
		return staffID;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public int getTaskRate() {
		return taskRate;
	}
	public void setTaskRate(int taskRate) {
		this.taskRate = taskRate;
	}
	public double getTimes() {
		return times;
	}
	public void setTimes(double times) {
		this.times = times;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getComponentid() {
		return componentid;
	}
	public void setComponentid(int componentid) {
		this.componentid = componentid;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
    
}
