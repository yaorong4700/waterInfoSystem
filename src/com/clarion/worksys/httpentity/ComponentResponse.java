package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * コンポーネント管理界面分页响应数据
 *  
 */
import java.util.List;

public class ComponentResponse {
private int page;
private int total;
private List<ComponentResponseRows> rows;

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
public List<ComponentResponseRows> getRows() {
	return rows;
}
public void setRows(List<ComponentResponseRows> rows) {
	this.rows = rows;
}


}
