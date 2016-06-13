package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * ProjectNameCT JAVABEAN 用于CT界面Select的数据
 * 
 *   
 */
import java.util.List;
public class ProjectNameCT {
	String val;
	String txt;
	List<ComponentSelect> menu;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public List<ComponentSelect> getMenu() {
		return menu;
	}

	public void setMenu(List<ComponentSelect> menu) {
		this.menu = menu;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	
}
