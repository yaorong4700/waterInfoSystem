/**
 * 项目管理分页所传入的参数
 */
package com.clarion.worksys.httpentity;

public class RegionPicReqParam {
	private int page;
	private int rp;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;
	private int pageSeq;
	
	private String regionId;
	private String regionSummary;
	private String collectTime;
	private String deviceId;
	private String deviceAddress;
	private String waterNum;
	

	public String getWaterNum() {
		return waterNum;
	}
	public void setWaterNum(String waterNum) {
		this.waterNum = waterNum;
	}
	private String collectTimeStart;
	private String collectTimeEnd;
	
	public String getCollectTimeStart() {
		return collectTimeStart;
	}
	public void setCollectTimeStart(String collectTimeStart) {
		this.collectTimeStart = collectTimeStart;
	}
	public String getCollectTimeEnd() {
		return collectTimeEnd;
	}
	public void setCollectTimeEnd(String collectTimeEnd) {
		this.collectTimeEnd = collectTimeEnd;
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
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public void setPageSeq(int pageSeq) {
		this.pageSeq = pageSeq;
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
	public String getDeviceAddress() {
		return deviceAddress;
	}
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	
}
