package com.clarion.worksys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.clarion.worksys.mapper.ManHourMapper;
import com.clarion.worksys.service.ManHourService;

@Service
public class ManHourServiceImpl implements ManHourService {

	@Autowired
	private ManHourMapper manHourMapper;

	public void deleteManHour(int id) {
		// TODO Auto-generated method stub
		manHourMapper.deleteManHour(id);
	}
	public double checkBeforeSave(CalendarManhour calendarmanhour){
		return manHourMapper.checkBeforeSave(calendarmanhour);
	}
	
	public double checkEditBeforeSave(CalendarManhour calendarmanhour){
		return manHourMapper.checkEditBeforeSave(calendarmanhour);
	}
	
	public boolean insertManhour(ManHour manHour) {
		// TODO Auto-generated method stub	
		return false;
	}

	public List<ManHour> listAllManHour() {
		// TODO Auto-generated method stub
		return manHourMapper.listAllManHour();
	}

	public void updateManHour(CalendarManhour calendarmanhour) {
		// TODO Auto-generated method stub
		manHourMapper.updateManHour(calendarmanhour);
	}

	public List<ManHour> getManHourByProject(int projectID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ManHour> getManHourByStaff(int StaffID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ManHourDto> getManHourByDate(String staffID, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		ManhourDate manhourDate = new ManhourDate();
		manhourDate.setStaffID(staffID);
		manhourDate.setStartDate(startDate);
		manhourDate.setEndDate(endDate);
		return manHourMapper.getManHourByDate(manhourDate);
	}
	public List<ManHourDtoCT> getManHourCTByDate(String staffID, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		ManhourDate manhourDate = new ManhourDate();
		manhourDate.setStaffID(staffID);
		manhourDate.setStartDate(startDate);
		manhourDate.setEndDate(endDate);
		return manHourMapper.getManHourCTByDate(manhourDate);
	}

	public String getDeadline() {
		// TODO Auto-generated method stub
		return manHourMapper.getDeadline();
	}

	public void setDeadline(String date) {
		// TODO Auto-generated method stub
		manHourMapper.setDeadline(date);
	}

	public boolean insertCalendarManhour(CalendarManhour calendarmanhour){
		int row = manHourMapper.insertCalendarManhour(calendarmanhour);
        System.out.println("=============insertCalendarManhour================="+calendarmanhour.getId()+" row"+row);
        if(row > 0){
        	return true;
        }else{
        	return false;
        }
		
	}
	public List<ManhourShowData> ShowData(){
		return manHourMapper.ShowData();
		
	}
	
	public List<ManhourShowData> ShowAllData(ManhourReqParam manhourReqParam){
		return manHourMapper.ShowAllData(manhourReqParam);
	}
	
	public int  totalPageCount(ManhourReqParam manhourReqParam){
		return manHourMapper.totalPageCount(manhourReqParam);
	}
	public List<StaffProjectQueryOut> getStaffProjectManhourQuery(StaffProjectQueryIn spqIn){
		return manHourMapper.getStaffProjectManhourQuery(spqIn);
		
	}
	public List<ManhourPersonalQueryView> ManhourPersonalQuery(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.ManhourPersonalQuery(manhourPersonalQueryParam);
	}
	
	public int totalPageCountForPersonalQuery(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.totalPageCountForPersonalQuery(manhourPersonalQueryParam);
	}
	public List<ManhourPersonalQueryView> ManhourPersonalQueryCT(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.ManhourPersonalQueryCT(manhourPersonalQueryParam);
	}
	
	public int totalPageCountForPersonalQueryCT(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.totalPageCountForPersonalQueryCT(manhourPersonalQueryParam);
	}
	public void callCheckError(ManhourCheckErrorParam manhourCheckErrorParam){
		manHourMapper.callCheckError(manhourCheckErrorParam);
	}
	
	public List<Blacklist> selectBlacklist(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.selectBlacklist(manhourCheckErrorParam);
	}
	
	public List<Blacklist> selectBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.selectBlacklistCT(manhourCheckErrorParam);
	}
	
	public int TotalPageCountForBlacklist(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.TotalPageCountForBlacklist(manhourCheckErrorParam);
	}
	
	public int TotalPageCountForBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.TotalPageCountForBlacklistCT(manhourCheckErrorParam);
	}
	
	public List<Blacklist> selectAllBlacklist(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.selectAllBlacklist(manhourCheckErrorParam);
	}
	
	public List<BlacklistCT> selectAllBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.selectAllBlacklistCT(manhourCheckErrorParam);
	}
	
	public int getTotalWorkday(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.getTotalWorkday(manhourCheckErrorParam);
	}
	
	public List<ManhourPersonalQueryView> totalHourQuery(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.totalHourQuery(manhourPersonalQueryParam);
	}
	
	public int TotalPageCountForTotalHourQuery(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.TotalPageCountForTotalHourQuery(manhourPersonalQueryParam);
	}

	public List<String> listMonthHoliday() {
		return manHourMapper.listMonthHoliday();
	}

	public List<ManhourByProject> getHourByProject(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.getHourByProject(manhourPersonalQueryParam);
	}
	
	public int totalPageCountForGetHourByProject(ManhourPersonalQueryParam manhourPersonalQueryParam){
		return manHourMapper.totalPageCountForGetHourByProject(manhourPersonalQueryParam);
	}
	public List<Integer> getDesignStaffID(Map<String, Object> paramsDepart){
		return manHourMapper.getDesignStaffID(paramsDepart);
	}
	public List<Integer> getDepartWorkNumProjectId(Map<String, Object> paramsDepart){
		return manHourMapper.getDepartWorkNumProjectId(paramsDepart);
	}
	public String getStaffName(Integer staffID){
		return manHourMapper.getStaffName(staffID);
	}
	public String getProjectName(Integer projectID){
		return manHourMapper.getProjectName(projectID);
	}
	public double getPersonTotalTime(Map<String, Object> paramsDepart){
		return manHourMapper.getPersonTotalTime(paramsDepart);
	}
	
	public List<Component> getComponentSelectInfo(){
		return manHourMapper.getComponentSelectInfo();
	}
	/**
     *根据部门和项目类别列出相应的作业类型
     */
    public List<Map<String,Object>> listCTTask(String category, String departmentID) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("category", category);
        params.put("departmentID", departmentID);
        return manHourMapper.listCTTask(params);
    }
    /**
     * 列出最近填写项目外其他项目信息
     */
    public List<Project> listAllProjectCT(int staffID,String departmentID) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("staffID", staffID);
        params.put("departmentID", departmentID);
        return manHourMapper.listAllProjectCT(params);
    }

    /**
     * 列出最近填写的项目信息
     */
    public List<Project> recentProjectCT(int staffID,String departmentID) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("staffID", staffID);
        params.put("departmentID", departmentID);
        return manHourMapper.recentProjectCT(params);
    }
    
	public List<BlacklistCT> getManhourTotalTimesCT(ManhourCheckErrorParam manhourCheckErrorParam){
		return manHourMapper.getManhourTotalTimesCT(manhourCheckErrorParam);
	}
}
