/**
 * 项目 管理返回的列表数据结构
 */
package com.clarion.worksys.httpentity;

import java.util.List;

/**
 * @author weng_zhangchu
 *
 */
public class RegionPicResponse {
	private int total;
	private int page;
	private List<RegionPicResponseRows> rows;
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
	public List<RegionPicResponseRows> getRows() {
		return rows;
	}
	public void setRows(List<RegionPicResponseRows> rows) {
		this.rows = rows;
	}
	
}
