package com.clarion.worksys.entity;

import java.util.List;

/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数信息JAVABEAN 用于界面数据展示,用于两张表关联
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */
public class AssumeDetail {
	private int       assumePJNum;
	private List<String>    assumePJName;
	public int getAssumePJNum() {
		return assumePJNum;
	}
	public void setAssumePJNum(int assumePJNum) {
		this.assumePJNum = assumePJNum;
	}
	public List<String> getAssumePJName() {
		return assumePJName;
	}
	public void setAssumePJName(List<String> assumePJName) {
		this.assumePJName = assumePJName;
	}
	
	
}
