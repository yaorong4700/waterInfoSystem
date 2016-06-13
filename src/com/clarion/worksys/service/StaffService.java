package com.clarion.worksys.service;

import java.util.List;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.httpentity.StaffReqParam;

public interface StaffService {
	
	List<Staff> listAllStaff();
	List<Staff> listAllStaffCT();
    List<Staff> listStaff(StaffReqParam staffReqParam);
    
    Staff getStaffById(Integer id);
    
    Staff getStaffByIdCXEE(Integer id);
    
    Staff getStaffByIdCT(Integer id);
    
	boolean insertStaff(Staff staff);

	void updateStaff(Staff staff);

	void deleteStaffs(String[] ids);
	
	int  totalPageCount(StaffReqParam staffReqParam);
	
	Staff getUserByNameAndPwd(String username,String password);

	int getStaffByEmail(String email);

	void updateStaffpassword(Integer staffID, String mypasswordnew);
	
	String getSort(String sortID);
	
	String downloadStaff(List<Staff> staffList, String realpathString);
	
	String downloadStaffCT(List<Staff> staffList,String realpathString);
	
	int getStaffByJobNo( String jobNo);
	
	List<Staff> listStaffCT(StaffReqParam staffReqParam);
	int  totalPageCountCT(StaffReqParam staffReqParam);
	List<String> getAbility();
	List<String> getAllSort();
	List<Staff> getJobCategory();
	List<String> getAbilityCT();
	List<String> getAllSortCT();
	List<Staff> getJobCategoryCT();
}
