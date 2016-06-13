package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Blacklist;
import com.clarion.worksys.entity.BlacklistCT;
import com.clarion.worksys.entity.CalendarManhour;
import com.clarion.worksys.entity.Component;
import com.clarion.worksys.entity.ManHour;
import com.clarion.worksys.entity.ManHourDto;
import com.clarion.worksys.entity.ManHourDtoCT;
import com.clarion.worksys.entity.ManhourByProject;
import com.clarion.worksys.entity.ManhourCheckErrorParam;
import com.clarion.worksys.entity.ManhourPersonalQueryParam;
import com.clarion.worksys.entity.ManhourPersonalQueryView;
import com.clarion.worksys.entity.ManhourReqParam;
import com.clarion.worksys.entity.ManhourShowData;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.StaffProjectQueryIn;
import com.clarion.worksys.entity.StaffProjectQueryOut;

public interface ManHourService {
	List<ManHour> listAllManHour();

	List<ManHourDto> getManHourByDate(String staffID,String startDate,String endDate);
	
	List<ManHourDtoCT> getManHourCTByDate(String staffID,String startDate,String endDate);
	
	
	List<ManHour> getManHourByProject(int projectID);
	
	List<ManHour> getManHourByStaff(int StaffID);

	boolean insertManhour(ManHour manHour);

	void updateManHour(CalendarManhour calendarmanhour);

	void deleteManHour(int id);
	
	String getDeadline();
	
	void setDeadline(String date);
	
	boolean insertCalendarManhour(CalendarManhour calendarmanhour);
	
	List<ManhourShowData> ShowData();
	
	List<ManhourShowData> ShowAllData(ManhourReqParam manhourReqParam);
	
	int  totalPageCount(ManhourReqParam manhourReqParam);
	
	List<StaffProjectQueryOut> getStaffProjectManhourQuery(StaffProjectQueryIn spqIn);
	
	List<ManhourPersonalQueryView> ManhourPersonalQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int totalPageCountForPersonalQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	List<ManhourPersonalQueryView> ManhourPersonalQueryCT(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int totalPageCountForPersonalQueryCT(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	void callCheckError(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<Blacklist> selectBlacklist(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<Blacklist> selectBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam);
	
	int TotalPageCountForBlacklist(ManhourCheckErrorParam manhourCheckErrorParam);
	
	int TotalPageCountForBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<Blacklist> selectAllBlacklist(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<BlacklistCT> selectAllBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<BlacklistCT> getManhourTotalTimesCT(ManhourCheckErrorParam manhourCheckErrorParam);
	
	int getTotalWorkday(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<ManhourPersonalQueryView> totalHourQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int TotalPageCountForTotalHourQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	List<String> listMonthHoliday();
	
	List<ManhourByProject> getHourByProject(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int totalPageCountForGetHourByProject(ManhourPersonalQueryParam manhourPersonalQueryParam);

	List<Integer> getDesignStaffID(Map<String, Object> paramsDepart);

	List<Integer> getDepartWorkNumProjectId(Map<String, Object> paramsDepart);

	String getStaffName(Integer integer);

	String getProjectName(Integer integer);

	double getPersonTotalTime(Map<String, Object> paramsDepart);

	double checkBeforeSave(CalendarManhour calendarmanhour);

	double checkEditBeforeSave(CalendarManhour calendarmanhour);
	
	List<Component> getComponentSelectInfo();
	
	List<Map<String,Object>> listCTTask(String category,String department);
	
	List<Project> listAllProjectCT(int staffID,String department);
	
	List<Project> recentProjectCT(int staffID,String department);
}
