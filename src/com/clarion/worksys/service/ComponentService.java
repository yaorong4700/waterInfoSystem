package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Component;
import com.clarion.worksys.httpentity.ComponentReqParam;

public interface ComponentService{
	
	//段階リスト
	List<Map<Integer, Object>> getCategoryList();
	List<Map<String, Object>> getComponentSortList();
	List<Component> listComponent(ComponentReqParam componentReqParam);
	List<Component> listAllComponent();
	int  totalPageCount(ComponentReqParam componentReqParam);
	void deleteComponents(String[] ids);
	Component getComponentById(String id);
	int getComponentCount(Component component);
	boolean insertComponent(Component component);
	void updateComponent(Component component);
	String downloadComponent(List<Component> componentList,String realpathString);
}
