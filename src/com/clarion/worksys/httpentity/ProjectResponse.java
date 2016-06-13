/**
 * 项目 管理返回的列表数据结构
 */
package com.clarion.worksys.httpentity;

import java.util.List;

/**
 * @author weng_zhangchu
 *
 */
public class ProjectResponse {
	private int total;
	private int page;
	private List<ProjectResponseRows> rows;
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
	public List<ProjectResponseRows> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<ProjectResponseRows> rows) {
		this.rows = rows;
	}
	
}
