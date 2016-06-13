package com.clarion.worksys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.ContentMst;
import com.clarion.worksys.mapper.ContentInfoMapper;
import com.clarion.worksys.service.ContentInfoService;


@Service
public class ContentInfoServiceImpl implements ContentInfoService{
	
	@Autowired
	private ContentInfoMapper contentInfoMapper;
	
	
	//保存通知
	public String saveContent(String keyCode, String type, String content, String link, String jobNo) {
		Map<String, Object>  params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("content", content);
		params.put("link", link);
		params.put("jobNo", jobNo);
		if("".equals(keyCode)){
			Map<String, Object>  paramsgetkeyCode = new HashMap<String, Object>();
			paramsgetkeyCode.put("pjName", "CI");
			paramsgetkeyCode.put("insertUser", jobNo);
			
			params.put("keyCode", keyCode);
			contentInfoMapper.insContent(params);
		} else {
			params.put("keyCode", keyCode);
			contentInfoMapper.updContent(params);
		}
		return keyCode;
	}
	
	//删除通知
	public void deleteContent(String keyCode) {
		contentInfoMapper.delContent(keyCode);
	}
	
	//获取通知
	public String getContentText() {
		// TODO Auto-generated method stub
		return contentInfoMapper.getContentText();
	}
	//获取通知
	public String getCTContentText() {
		// TODO Auto-generated method stub
		return contentInfoMapper.getCTContentText();
	}
	//获取通知
	public List<ContentMst> getContentList() {
		// TODO Auto-generated method stub
		return contentInfoMapper.getContentList();
	}
	
	//获取通知
	public List<ContentMst> getCTContentList() {
		// TODO Auto-generated method stub
		return contentInfoMapper.getCTContentList();
	}
}
