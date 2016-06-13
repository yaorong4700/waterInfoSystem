package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理界面分页响应数据
 * 
 * @author chen_weijun@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 *  2013-3-1 chenweijun
 *  初版
 *   
 */
import java.util.List;

import com.clarion.worksys.entity.Message;

public class MessageResponse {
private int page;
private int total;
private List<MessageResponseRows> rows;

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
public List<MessageResponseRows> getRows() {
	return rows;
}
public void setRows(List<MessageResponseRows> rows) {
	this.rows = rows;
}


}
