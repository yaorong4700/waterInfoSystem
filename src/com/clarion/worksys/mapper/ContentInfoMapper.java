package com.clarion.worksys.mapper;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.ContentMst;

public interface ContentInfoMapper {

	//新增通知
	void insContent(Map<String, Object> params);
	
	//修改通知
	void updContent(Map<String, Object> params);
	
	//删除通知
	void delContent(String keyCode);
	
	//获取通知
	String getContentText();
	//获取CT通知
	String getCTContentText();
	
	//查找文件
	int checkContentText(Map<String, Object> params);
	
	//获取资料列表
	List<ContentMst> getContentList();
	
	//获取CT侧资料列表
	List<ContentMst> getCTContentList();
}
