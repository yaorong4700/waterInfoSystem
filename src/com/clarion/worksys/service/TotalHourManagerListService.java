package com.clarion.worksys.service;

import java.util.List;

import com.clarion.worksys.entity.TotalHourManager;
import com.clarion.worksys.entity.TotalHourManagerSum;
import com.clarion.worksys.httpentity.TotalHourManageReqParam;



public interface TotalHourManagerListService {
	
	public List<TotalHourManager> searchList(TotalHourManageReqParam totalHourManageReqParam);
	public List<TotalHourManager> searchListCXEE(TotalHourManageReqParam totalHourManageReqParam);
	public List<TotalHourManager> searchListCT(TotalHourManageReqParam totalHourManageReqParam);
	
	int  totalPageCount(TotalHourManageReqParam totalHourManageReqParam);
	int  totalPageCountCXEE(TotalHourManageReqParam totalHourManageReqParam);
	int  totalPageCountCT(TotalHourManageReqParam totalHourManageReqParam);
	
	public TotalHourManagerSum sumSearch(TotalHourManageReqParam totalHourManageReqParam);
	public TotalHourManagerSum sumSearchCT(TotalHourManageReqParam totalHourManageReqParam);
	public TotalHourManagerSum sumSearchCXEE(TotalHourManageReqParam totalHourManageReqParam);
	
	String downloadTotalHour(List<TotalHourManager> totalHourManager , String realpathString);
	List<TotalHourManager> searchListDownload(TotalHourManageReqParam totalHourManageReqParam);
	List<TotalHourManager> searchListDownloadCXEE(TotalHourManageReqParam totalHourManageReqParam);
	List<TotalHourManager> searchListDownloadCT(TotalHourManageReqParam totalHourManageReqParam);


}
