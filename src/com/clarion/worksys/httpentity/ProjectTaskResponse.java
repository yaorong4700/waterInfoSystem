/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 作业类型管理返回的数据类
 * 
 * @author weng_zhangchu@clarion.com.cn
 * @create: 2013-3-19
 * @histroy:
 * 	2013-3-19 weng_zhangchu
 *  # 初版
 *   
 */
package com.clarion.worksys.httpentity;

import java.util.List;

/**
 * @author weng_zhangchu
 *
 */
public class ProjectTaskResponse {
	private int total;
	private int page;
	private List<ProjectTaskResponseRows> rows;
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the rows
	 */
	public List<ProjectTaskResponseRows> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<ProjectTaskResponseRows> rows) {
		this.rows = rows;
	}
	
}
