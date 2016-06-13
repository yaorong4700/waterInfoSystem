package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * Category JAVABEAN 用于界面Select的数据
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *  2013-08-21 GuoRenPeng
 *  #新增txt字段
 * 
 */
import java.util.List;
public class CategoryCT {
	String val;
	List<ProjectNameCT> menu;
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public List<ProjectNameCT> getMenu() {
		return menu;
	}
	public void setMenu(List<ProjectNameCT> menu) {
		this.menu = menu;
	}
	

}
