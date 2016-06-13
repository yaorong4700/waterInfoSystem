package com.clarion.worksys.service;
import java.util.List;

import com.clarion.worksys.entity.ContentMst;

public interface ContentInfoService{
	
	//保存通知
	String saveContent(String keyCode, String type, String content, String link, String jobNo);
	
	//删除通知
	void deleteContent(String keyCode);

	//获取通知
	String getContentText();
	//获取通知
	String getCTContentText();
	//获取列表
	List<ContentMst> getContentList();
	//获取列表
	List<ContentMst> getCTContentList();
}
