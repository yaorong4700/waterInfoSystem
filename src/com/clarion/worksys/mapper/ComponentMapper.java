package com.clarion.worksys.mapper;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Component;
import com.clarion.worksys.httpentity.ComponentReqParam;

public interface ComponentMapper {

	//段階リスト
	List<Map<Integer, Object>> getCategoryList();
	
	List<Map<String, Object>> getComponentSortList();
	
	List<Component> listComponent(ComponentReqParam componentReqParam );
	
	List<Component> listAllComponent();
	
	int  totalPageCount(ComponentReqParam componentReqParam);
	
	void deleteComponents(String[] ids);
	
	Component getComponentById(String id);
	
	int getComponentCount(Component component);
    
	int insertComponent(Component component);
	
	void updateComponent(Component component);
}
