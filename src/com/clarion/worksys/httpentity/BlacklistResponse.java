/**
 * 
 */
package com.clarion.worksys.httpentity;

import java.util.List;

/**
 * @author weng_zhangchu
 *
 */
public class BlacklistResponse {
	private int page;
	private int total;
	private List<BlacklistResponseRows> rows;
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
	public List<BlacklistResponseRows> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<BlacklistResponseRows> rows) {
		this.rows = rows;
	}

}
