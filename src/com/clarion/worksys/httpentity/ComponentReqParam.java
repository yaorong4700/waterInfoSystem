package com.clarion.worksys.httpentity;
/**
 * 
 * コンポーネント管理界面分页请求参数
 * 
 * @author hscn liuy
 * @create: 2015-12-24
 * @histroy:
 * 	2015-12-24 liuy
 *  # 初版
 *   
 */

public class ComponentReqParam {
	private int page;
	private int rp;
	private int pageSeq;
	private String categoryID;
	private String componentSort;
	private String componentID;
	private String isVisible;
	
	//20160317QueryRoleFlag
	private String QueryRoleFlag;
	
	
	public String getQueryRoleFlag() {
		return QueryRoleFlag;
	}
	public void setQueryRoleFlag(String queryRoleFlag) {
		QueryRoleFlag = queryRoleFlag;
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
	public int getPageSeq() {
		return pageSeq;
	}
	public void setPageSeq(int pageSeq) {
		this.pageSeq = pageSeq;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getComponentSort() {
		return componentSort;
	}
	public void setComponentSort(String componentSort) {
		this.componentSort = componentSort;
	}
	public String getComponentID() {
		return componentID;
	}
	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}
	public String getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
}
