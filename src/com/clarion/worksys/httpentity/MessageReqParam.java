package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理界面分页请求参数
 * 
 * @author chen_weijun@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 *  2013-3-1 chenweijun
 *  初版
 *   
 */

public class MessageReqParam {
	private int page;
	private int rp;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;
	private int pageSeq;
	private String message;
	private String state;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRp() {
		return rp;
	}
	public void setRp(int rp) {
		this.rp = rp;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public int getPageSeq() {
		return pageSeq;
	}
	public void setPageSeq(int pageSeq) {
		this.pageSeq = pageSeq;
	}
	
	
	

}
