package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * ProjectName JAVABEAN 用于界面Select的数据
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */
import java.util.List;
public class ProjectName {
	String val;
	String txt;
	List<Task> menu;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public List<Task> getMenu() {
		return menu;
	}

	public void setMenu(List<Task> menu) {
		this.menu = menu;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	
}
