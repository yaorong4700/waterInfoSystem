/**
 * 项目管理分页所传入的参数
 */
package com.clarion.worksys.httpentity;

public class RegionSumReqParam {
	private int page;
	private int rp;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;
	private int pageSeq;
	
	private String regionID;
	private String regionSummary;
	private String collectTime;
	private String deviceID;
	private String deviceAddress;
	
	private String regionIdStart;
	private String regionIdEnd;
	
	
	public String getRegionIdStart() {
		return regionIdStart;
	}
	public void setRegionIdStart(String regionIdStart) {
		this.regionIdStart = regionIdStart;
	}
	public String getRegionIdEnd() {
		return regionIdEnd;
	}
	public void setRegionIdEnd(String regionIdEnd) {
		this.regionIdEnd = regionIdEnd;
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
	public String getRegionID() {
		return regionID;
	}
	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}
	public String getRegionSummary() {
		return regionSummary;
	}
	public void setRegionSummary(String regionSummary) {
		this.regionSummary = regionSummary;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getDeviceAddress() {
		return deviceAddress;
	}
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	
}
