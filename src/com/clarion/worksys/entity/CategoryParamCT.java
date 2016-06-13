package com.clarion.worksys.entity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 项目类别参数java bean
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-12-25
 * @histroy:
 * 	2012-12-25 GuoRenPeng
 *  # 初版
 *   
 *  2013-1-29 GuoRenPeng
 *   修改rfir、noAccord为RFIRFQ,不具合・仕様変更
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目信息接口定义
 * @author guo_renpeng
 *
 */
public class CategoryParamCT {
	
	List<Map<String,Object>> devTaskList = new ArrayList<Map<String,Object>>();
	List<Map<String,Object>> otherTaskList = new ArrayList<Map<String,Object>>();
	List<Map<String,Object>> advanceTaskList = new ArrayList<Map<String,Object>>();
	//量产后不具合对应
	List<Map<String,Object>> noAccordTaskList = new ArrayList<Map<String,Object>>();
	List<String> categoryList = new ArrayList<String>();
	String dev  = new String();
	String rfir = new String();
	String noAccord = new String();
	String other = new String();
	
	public CategoryParamCT(){
		dev = "開発工数";
		rfir = "RFI&RFQ";
		noAccord = "量産後の不具合対応";
		other = "先行開発";
		/*dev = "开发工数";
		rfir = "RFI和RFQ";
		noAccord = "量产后不具合对应";
		other = "先行开发";*/
		categoryList.add(dev);
		categoryList.add(rfir);
		categoryList.add(noAccord);
		categoryList.add(other);
	}
	

	public List<Map<String, Object>> getDevTaskList() {
		return devTaskList;
	}


	public void setDevTaskList(List<Map<String, Object>> devTaskList) {
		this.devTaskList = devTaskList;
	}


	public List<Map<String, Object>> getOtherTaskList() {
		return otherTaskList;
	}


	public void setOtherTaskList(List<Map<String, Object>> otherTaskList) {
		this.otherTaskList = otherTaskList;
	}


	public List<Map<String, Object>> getAdvanceTaskList() {
		return advanceTaskList;
	}


	public void setAdvanceTaskList(List<Map<String, Object>> advanceTaskList) {
		this.advanceTaskList = advanceTaskList;
	}


	public List<Map<String, Object>> getNoAccordTaskList() {
		return noAccordTaskList;
	}


	public void setNoAccordTaskList(List<Map<String, Object>> noAccordTaskList) {
		this.noAccordTaskList = noAccordTaskList;
	}


	public List<String> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}
	public String getDev() {
		return dev;
	}
	public void setDev(String dev) {
		this.dev = dev;
	}
	public String getRfir() {
		return rfir;
	}
	public void setRfir(String rfir) {
		this.rfir = rfir;
	}
	public String getNoAccord() {
		return noAccord;
	}
	public void setNoAccord(String noAccord) {
		this.noAccord = noAccord;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}

	
}
