package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

public interface ProjectSelectedService {
	List<Map<String, Object>> getProjectCategoryList();
	List<Map<String, Object>> getProjectCategoryListCT();
	List<Map<String, Object>> getProjectDepartmentListCXEE();
	List<Map<String, Object>> getBranchListCXEE(String departmentID);
	List<Map<String, Object>> getCarMakeList();
	List<Map<String, Object>> getCarMakeListCT();
	List<Map<String, Object>> getFuctionList();
	List<Map<String, Object>> getProjectDepartmentListCT();
	List<Map<String, Object>> getProjectBranchListCXEE();
	List<Map<String, Object>> getFuctionListCT();
}
