package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * コンポーネント信息javaBean
 * 
 * @author hscn liuy
 * @create: 2015-12-24
 * @histroy:
 * 	2015-12-24 liuy
 *  # 初版
 *   
 */
public class Component {

	private Integer    No;
	private Integer id;
	private String componentID;
	private String componentName;
	private int categoryID;
	private String categoryName;
	private String componentSort;
	private String componentSortID;
	private String isVisible;
	

	public Integer getNo() {
		return No;
	}
	public void setNo(Integer no) {
		No = no;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComponentID() {
		return componentID;
	}
	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getComponentSort() {
		return componentSort;
	}
	public void setComponentSort(String componentSort) {
		this.componentSort = componentSort;
	}
	public String getComponentSortID() {
		return componentSortID;
	}
	public void setComponentSortID(String componentSortID) {
		this.componentSortID = componentSortID;
	}
	public String getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	
}