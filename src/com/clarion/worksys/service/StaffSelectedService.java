package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.ProjectTime;

public interface StaffSelectedService{
	List<Map<String, Object>> getPositionList();
	List<Map<String, Object>> getPositionListCXEE();
	List<Map<String, Object>> getPositionListCT();
	List<Map<String, Object>> getDepartmentList();
	List<Map<String, Object>> getDepartmentListCXEE();
	List<Map<String, Object>> getDepartmentListCT();
	String getDepartmentByID(int departmentID);
	List<Map<String, Object>> getBranchList(String department);
	List<Map<String, Object>> getBranchListCXEE(String department);
	List<Map<String, Object>> getBranchListCT(String department);
	List<String> getTeamList();
	List<String> getLeadList(String department);
	List<ProjectTime> getDetailManhoue(Map<String, Object> parma);
	List<Project> getDetailProject(Map<String, Object> parma);
	List<Map<String, Object>> getBranchListOfDevelopDepartment();
	List<Map<String, Object>> getStaffSortList();
	List<Map<String, Object>> getRoleList();
	List<Map<String, Object>> getJobCaegory();
	int countDepartment();
	int countEnrolementCode( String departmentID, String enrolementCode, String branchID);
	
}
