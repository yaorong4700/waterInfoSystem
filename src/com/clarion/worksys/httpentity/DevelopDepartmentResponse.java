package com.clarion.worksys.httpentity;

import java.util.List;

public class DevelopDepartmentResponse {

	
	private int page;
	private int total;
	private List<DevelopDepartmentResponseRows> rows;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<DevelopDepartmentResponseRows> getRows() {
		return rows;
	}
	public void setRows(List<DevelopDepartmentResponseRows> rows) {
		this.rows = rows;
	}
	
	
}
