/**
 * 
 */
package com.clarion.worksys.httpentity;

import java.util.List;

/**
 * 工数显示返回的数据，包括页码，数据条数，以及返回的数据条行
 * @author weng_zhangchu
 *
 */
public class ManhourResponse {
	private int page;
	private int total;
	private List<ManhourResponseRows> rows;
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
	 * @return the rows
	 */
	public List<ManhourResponseRows> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<ManhourResponseRows> rows) {
		this.rows = rows;
	}

}
