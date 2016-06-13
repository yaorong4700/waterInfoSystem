package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理界面分页响应数据
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */
import java.util.List;

public class StaffResponse {
private int page;
private int total;
private List<StaffResponseRows> rows;

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
public List<StaffResponseRows> getRows() {
	return rows;
}
public void setRows(List<StaffResponseRows> rows) {
	this.rows = rows;
}


}
