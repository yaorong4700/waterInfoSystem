package com.clarion.worksys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.ProjectTime;
import com.clarion.worksys.mapper.StaffSelectedMapper;
import com.clarion.worksys.service.StaffSelectedService;

@Service
public class StaffSelectedServiceImpl implements StaffSelectedService{
	
	@Autowired
	private StaffSelectedMapper staffSelectedMapper;

	public String getDepartmentByID(int departmentID) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getDepartmentByID(departmentID);
	}

	public List<ProjectTime> getDetailManhoue(Map<String, Object> parma) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getDetailManhour(parma);
	}

	public List<Project> getDetailProject(Map<String, Object> parma) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getDetailProject(parma);
	}

	public List<Map<String, Object>> getBranchList(String department) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getBranchList(department);
	}
	public List<Map<String, Object>> getBranchListCXEE(String department) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getBranchListCXEE(department);
	}
	public List<Map<String, Object>> getBranchListCT(String department) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getBranchListCT(department);
	}
	public List<Map<String, Object>> getDepartmentList() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getDepartmentList();
	}
	public List<Map<String, Object>> getDepartmentListCXEE() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getDepartmentListCXEE();
	}
	public List<Map<String, Object>> getDepartmentListCT() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getDepartmentListCT();
	}
	public List<String> getLeadList(String department) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getLeadList(department);
	}

	public List<Map<String, Object>> getPositionList() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getPositionList();
	}
	public List<Map<String, Object>> getPositionListCXEE() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getPositionListCXEE();
	}
	public List<Map<String, Object>> getPositionListCT() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getPositionListCT();
	}
	public List<String> getTeamList() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getTeamList();
	}

	@Override
	public List<Map<String, Object>> getBranchListOfDevelopDepartment() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getBranchListOfDevelopDepartment();
	}
	public List<Map<String, Object>> getStaffSortList() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getStaffSortList();
	}
	public List<Map<String, Object>> getRoleList() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getRoleList();
	}

	@Override
	public List<Map<String, Object>> getJobCaegory() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.getJobCategoryList();
	}

	@Override
	public int countDepartment() {
		// TODO Auto-generated method stub
		return staffSelectedMapper.countDepartment();
	}

	@Override
	public int countEnrolementCode( String departmentID, String enrolementCode, String branchID) {
		// TODO Auto-generated method stub
		return staffSelectedMapper.countEnrolementCode(departmentID,  enrolementCode,  branchID);
	}
	
}
