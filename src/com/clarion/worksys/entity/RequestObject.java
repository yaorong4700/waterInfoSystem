package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理页面请求数据javabean
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */

public class RequestObject {
	private String staffName;
	private String position;
	private int    pageNow;
	
	
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}