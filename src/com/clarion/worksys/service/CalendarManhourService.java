package com.clarion.worksys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.departmentBranch;

public interface CalendarManhourService{

	public List<Map<String,String>> listAllDepart();
	public List<Map<String,String>> listAllDepart_ForCt();
	
	
	public List<departmentBranch> listDepartment();
	public List<departmentBranch> branchSelect(String departmentID);
	public List<departmentBranch> listDepartmentCT();
	public List<departmentBranch> branchSelectCT(String departmentID);
	

	public String outputDepartJijimanhour(String department
								            ,String branch
								            ,String departmentID
								            ,String branchID
								            ,int year
								            ,int month
								            ,int startday
								            ,int endyear
								            ,int endmonth
								            ,String realPathString);

	public String outputDepartJijimanhour_ForCt(String user
            								,String department
								            ,String branch
								            ,String departmentID
								            ,String branchID
								            ,int year
								            ,int month
								            ,int startday
								            ,int endyear
								            ,int endmonth
								            ,String realPathString);

	public List<Map<String,String>> outputbranchselect(String departmentID);

	public List<Map<String,String>> outputbranchselect_ForCt(String departmentID);
	
	public void updateEndtime(String endtime);

	public Date getdeadlinetime();

	public String exportPersonsWorkNum(String department
									,String branch
									,String departmentID
									,String branchID
									,int year
									,int month
									,int startday
									,int endyear
									,int endmonth
									,String realPathString);

	public String exportPersonsWorkNum_ForCt(String user
						            ,String department
						            ,String branch
						            ,String departmentID
						            ,String branchID
						            ,int year
						            ,int month
						            ,int startday
						            ,int endyear
						            ,int endmonth
						            ,String realPathString);

	public List<String> listCompanystaffselect(Map<String, Object> params);
	
	public List<String> liststaffselect(Map<String, Object> params);
	
	public List<String> getCxeeCsvData(Map<String, Object> params);

	public List<String> getCxeeCsvData_ForCt(Map<String, Object> params);
}
