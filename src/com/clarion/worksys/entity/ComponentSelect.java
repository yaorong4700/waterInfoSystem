package com.clarion.worksys.entity;

import java.util.List;

/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * ComponentSelect JAVABEAN 用于界面Select的数据
 * 
 */
public class ComponentSelect {
	String val;
	String txt;
	List<Task> menu;
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	public List<Task> getMenu() {
		return menu;
	}

	public void setMenu(List<Task> menu) {
		this.menu = menu;
	}
}
