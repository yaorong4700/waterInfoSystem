package com.clarion.worksys.mapper;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.httpentity.StaffReqParam;

public interface StaffMapper {

	List<Staff> listAllStaff();
	List<Staff> listAllStaffCT();
	
	List<Staff> listStaffs(StaffReqParam staffReqParam);
	
	Staff getStaffInfo(Staff staff);
	
	Staff getStaffById(int id);
	Staff getStaffByIdCXEE(int id);
	Staff getStaffByIdCT(int id);
	
	int insertStaff(Staff staff);
	
	void deleteStaffs(String[] ids);
	
	void updateStaff(Staff staff);
	
	int  totalPageCount(StaffReqParam staffReqParam);
	
	void updateStaffpassword(Map<String, Object> params);

	List<Staff> getStaffList(Map<String, Object> paramsPerson);
	
	int getStaffByEmail(String email);
	
	String getSort(String sortID);
	
	int getStaffByJobNo(String jobNo);
	
	List<Staff> listStaffsCT(StaffReqParam staffReqParam);
	int  totalPageCountCT(StaffReqParam staffReqParam);
	List<String> getAbility();
	List<String> getAllSort();
	List<Staff> getJobCategory();
	List<String> getAbilityCT();
	List<String> getAllSortCT();
	List<Staff> getJobCategoryCT();
}
