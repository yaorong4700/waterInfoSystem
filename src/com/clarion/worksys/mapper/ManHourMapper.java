package com.clarion.worksys.mapper;

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
import com.clarion.worksys.entity.ManhourDate;
import com.clarion.worksys.entity.ManhourPersonalQueryParam;
import com.clarion.worksys.entity.ManhourPersonalQueryView;
import com.clarion.worksys.entity.ManhourReqParam;
import com.clarion.worksys.entity.ManhourShowData;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.StaffProjectQueryIn;
import com.clarion.worksys.entity.StaffProjectQueryOut;

public interface ManHourMapper {
	
	List<ManHour> listAllManHour();

	//请求一个月的工数LIST
	List<ManHourDto> getManHourByDate(ManhourDate manhourDate);
	
	List<ManHourDtoCT> getManHourCTByDate(ManhourDate manhourDate);

	void updateManHour(CalendarManhour calendarmanhour);

	void deleteManHour(int id);
	
	void exportAllManhour();//以指定形式导出所有工数信息

	String getDeadline();
	
	int insertCalendarManhour(CalendarManhour calendarmanhour);
	
	void setDeadline(String date);
	
	//获得所有工数
	List<ManhourShowData> ShowData();
	
	//获得所有工数
	List<ManhourShowData> ShowAllData(ManhourReqParam manhourReqParam);
	
	//获得所有工数总页数
	int  totalPageCount(ManhourReqParam manhourReqParam);
	
	List<StaffProjectQueryOut> getStaffProjectManhourQuery(StaffProjectQueryIn spqIn);
	
    //个人工数统计查询 
	List<ManhourPersonalQueryView> ManhourPersonalQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int totalPageCountForPersonalQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
    //个人工数统计查询CT侧用 
	List<ManhourPersonalQueryView> ManhourPersonalQueryCT(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int totalPageCountForPersonalQueryCT(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	void callCheckError(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<Blacklist> selectBlacklist(ManhourCheckErrorParam manhourCheckErrorParam);
	
	int TotalPageCountForBlacklist(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<Blacklist> selectBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam);
	
	int TotalPageCountForBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<Blacklist> selectAllBlacklist(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<BlacklistCT> selectAllBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam);
	
	int getTotalWorkday(ManhourCheckErrorParam manhourCheckErrorParam);
	
	List<ManhourPersonalQueryView> totalHourQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int TotalPageCountForTotalHourQuery(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	List<String> listMonthHoliday();
	
	List<ManhourByProject> getHourByProject(ManhourPersonalQueryParam manhourPersonalQueryParam);
	
	int totalPageCountForGetHourByProject(ManhourPersonalQueryParam manhourPersonalQueryParam);

	List<Integer> getDesignStaffID(Map<String, Object> paramsDepart);

	List<Integer> getDepartWorkNumProjectId(Map<String, Object> paramsDepart);

	String getStaffName(Integer staffID);

	String getProjectName(Integer projectID);

	double getPersonTotalTime(Map<String, Object> paramsDepart);

	double checkBeforeSave(CalendarManhour calendarmanhour);

	double checkEditBeforeSave(CalendarManhour calendarmanhour);
	
	List<Map<Integer, String>> getCategoryList();
	List<Component> getComponentSelectInfo();
	
	List<Map<String,Object>> listCTTask(Map<String, Object> params);
	
	List<Project> listAllProjectCT(Map<String, Object> params);
	
	List<Project> recentProjectCT(Map<String, Object> params);
	
	List<BlacklistCT> getManhourTotalTimesCT(ManhourCheckErrorParam manhourCheckErrorParam);
}
