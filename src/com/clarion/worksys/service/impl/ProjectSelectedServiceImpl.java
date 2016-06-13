package com.clarion.worksys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.service.ProjectSelectedService;
import com.clarion.worksys.mapper.ProjectSelectedMapper;
@Service
public class ProjectSelectedServiceImpl implements ProjectSelectedService {
	@Autowired
	private ProjectSelectedMapper projectSelectedMapper;

	public List<Map<String, Object>> getProjectCategoryList() {
		return projectSelectedMapper.getProjectCategoryList();
	}
	
	public List<Map<String, Object>> getProjectDepartmentListCXEE(){
		return projectSelectedMapper.getProjectDepartmentListCXEE();
	}
	
	@Override
	public List<Map<String, Object>> getBranchListCXEE(String departmentID) {
		// TODO Auto-generated method stub
		return projectSelectedMapper.getBranchListCXEE(departmentID);
	}

	@Override
	public List<Map<String, Object>> getCarMakeList() {
		return projectSelectedMapper.getCarMakeList();
	}
	
	public List<Map<String, Object>> getFuctionList() {
		return projectSelectedMapper.getFuctionList();
	}
	public List<Map<String, Object>> getProjectDepartmentListCT() {
		return projectSelectedMapper.getProjectDepartmentListCT();
	}
	public List<Map<String, Object>> getProjectBranchListCXEE(){
		return projectSelectedMapper.getProjectBranchListCXEE();
	}

	@Override
	public List<Map<String, Object>> getProjectCategoryListCT() {
		// TODO Auto-generated method stub
		return projectSelectedMapper.getProjectCategoryListCT();
	}

	@Override
	public List<Map<String, Object>> getCarMakeListCT() {
		// TODO Auto-generated method stub
		return projectSelectedMapper.getCarMakeListCT();
	}

	@Override
	public List<Map<String, Object>> getFuctionListCT() {
		// TODO Auto-generated method stub
		return projectSelectedMapper.getFuctionListCT();
	}
	
}
