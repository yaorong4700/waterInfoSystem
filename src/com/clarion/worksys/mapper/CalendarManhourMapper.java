package com.clarion.worksys.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.CsvData;
import com.clarion.worksys.entity.ManHourDto;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.departmentBranch;

public interface CalendarManhourMapper {
	List<Staff> listOutPutStaff(Map<String, Object> paramsdeparttemp);

	List<Project> listProject(String department);
	
	List<Map<String,String>> listAllDepart();

	List<Map<String,String>> listAllDepart_ForCt();

	Float departtotaltwothreefourmanhour(Map<String, Object> params);

	List<ManHourDto> listDepartManhourDtoTotal(Map<String, Object> paramsdepart);

	List<ManHourDto> listDepartManhourDtoTotal_ct(Map<String, Object> paramsdepart);

	List<Map<String,String>> listbranchselect(String departmentID);

	List<Map<String,String>> listbranchselect_ForCt(String departmentID);

	List<ManHourDto> listBranchManhourDtoTotal(Map<String, Object> paramsdepart);

	Float Branchtotalmanhour(Map<String, Object> params);

	Float Branchtotaltwothreefourmanhour(Map<String, Object> params);

	List<ManHourDto> listCompanyManhourDtoTotal(Map<String, Object> paramsdepart);

	List<String> liststaffselect(Map<String, Object> params);

	void UpdateEndtime(String endtime);

	Date getdeadlinetime();

	List<Project_task> listDepartProjectTask(String department);

	List<Project_task> listDepartProjectTask_ct(String department);

	List<Project> listComapnyProject();

	List<Project_task> listCompanyProjectTasks();

	Double listDevelopWorkNum(Map<String, Object> paramsKaiFaFuDai);

	Integer SearchId(String string);

	Double listCTDCOEDevelopWorkNum(Map<String, Object> CTDCOEKaiFaFuDaitemp);

	Double listCategoryDetail(Map<String, Object> paramsdepart);

	List<String> listtransferNo();

	List<String> listDepartmenttransferNo(String department);

	Integer getWorkDays(Map<String, Object> paramsdepart);

	Integer getAssumePJNum(Map<String, Object> paramsdepart);

	Integer getAssumePJNum_ct(Map<String, Object> paramsdepart);

	List<String> getAssumePJName(Map<String, Object> paramsdepart);

	List<String> getAssumePJName_ct(Map<String, Object> paramsdepart);

	int getCompanyAssumePJNum(Map<String, Object> paramsdepart);

	List<String> getCompanyAssumePJName(Map<String, Object> paramsdepart);

	//List<Staff> getStaffList(Map<String, Object> paramsPerson);

	int getPersonAssumePJNum(Map<String, Object> paramsdepart);

	int getPersonAssumePJNum_ct(Map<String, Object> paramsdepart);

	List<String> getPersonAssumePJName(Map<String, Object> paramsdepart);

	List<String> getPersonAssumePJName_ct(Map<String, Object> paramsdepart);

	List<ManHourDto> listPersonManhourDtoTotal(Map<String, Object> paramsdepart);

	List<ManHourDto> listPersonManhourDtoTotal_ct(Map<String, Object> paramsdepart);

	Double listPersonCategoryDetail(Map<String, Object> paramsdepart);

	Integer getDesignStaffNum(Map<String, Object> paramsdepart);

	Integer getDepartmentDesignStaffNum(Map<String, Object> paramsdepart);

	List<Staff> getStaffIDlist(Map<String, Object> paramsPerson);

	List<Staff> getStaffIDlist_ct(Map<String, Object> paramsPerson);

	Staff getStaffdetail(Integer staffID);

	List<Integer> getWorkNumProjectId(Map<String, Object> paramsCompany);

	Project getProjectDetailByProjectId(Integer projectID);

	Project getProjectDetailByProjectId_ct(Integer projectID);

	List<Integer> getDepartWorkNumProjectId(Map<String, Object> paramsDepart);

	List<Integer> getDepartWorkNumProjectId_ct(Map<String, Object> paramsDepart);

	List<String> getTransforNOByDepart(Map<String, Object> paramsDepart);

	List<String> getTransforNOByDepart_ct(Map<String, Object> paramsDepart);

	List<String> listCompanystaffselect(Map<String, Object> params);

	List<ManHourDto> listDevelopDepartManhourDtoTotal(
			Map<String, Object> developDepartment);

	List<ManHourDto> listNonDevelopDepartManhourDtoTotal(
			Map<String, Object> nonDevelopDepartment);

	Double listdevelopDepartmentWorkNum(Map<String, Object> developDepartment);

	Double listNonDevelopDepartmentWorkNum(
			Map<String, Object> nonDevelopDepartment);

	Double listCTDCOEDevelopDepartWorkNum(
			Map<String, Object> cTDCOEDevelopAddtionaltemp);

	Integer getDevelopDepartAssumePJNum(Map<String, Object> developDepartment);

	List<String> getDevelopDepartAssumePJName(
			Map<String, Object> developDepartment);

	Integer getDevelopDepartDesignStaffNum(Map<String, Object> developDepartment);

	Integer getDevelopDepartWorkDays(Map<String, Object> developDepartment);

	Integer getNonDevelopDepartDesignStaffNum(
			Map<String, Object> nonDevelopDepartment);

	Integer getNonDevelopDepartAssumePJNum(Map<String, Object> nonDevelopDepartment);

	List<String> getNonDevelopDepartAssumePJName(
			Map<String, Object> nonDevelopDepartment);

	Double listCTDCOENonDevelopDeparWorkNum(
			Map<String, Object> cTDCOEDevelopAddtionaltemp);

	List<Integer> getDevelopDepartWorkNumProjectId(
			Map<String, Object> paramsCompany);

	List<Integer> getNonDevelopDepartWorkNumProjectId(
			Map<String, Object> paramsCompany);

	List<Project_task> listDevelopDepartProjectTasks(Map<String, Object> paramsCompany);

	List<Project_task> listNonDevelopDepartProjectTasks(Map<String, Object> paramsCompany);
	
	List<departmentBranch> listDepartment();
	List<departmentBranch> branchSelect(String departmentID);
	List<departmentBranch> listDepartmentCT();
	List<departmentBranch> branchSelectCT(String departmentID);
	List<CsvData> getCxeeCsvData(Map<String, Object> param);
	List<CsvData> getCxeeCsvData_ForCt(Map<String, Object> param);

	Integer getDepartmentDesignStaffNum_ct(Map<String, Object> paramsdepart);
}
