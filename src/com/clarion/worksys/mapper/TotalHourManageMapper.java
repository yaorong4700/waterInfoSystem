package com.clarion.worksys.mapper;

import java.util.List;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.TotalHourManager;
import com.clarion.worksys.entity.TotalHourManagerSum;
import com.clarion.worksys.httpentity.TotalHourManageReqParam;


public interface TotalHourManageMapper {
	
	List<TotalHourManager> listSearch(TotalHourManageReqParam totalHourManageReqParam);
	List<TotalHourManager> listSearchCXEE(TotalHourManageReqParam totalHourManageReqParam);
	List<TotalHourManager> listSearchCT(TotalHourManageReqParam totalHourManageReqParam);
	
	int  totalPageCount(TotalHourManageReqParam totalHourManageReqParam);
	int  totalPageCountCXEE(TotalHourManageReqParam totalHourManageReqParam);
	int  totalPageCountCT(TotalHourManageReqParam totalHourManageReqParam);

	TotalHourManagerSum sumSearch(TotalHourManageReqParam totalHourManageReqParam);
	TotalHourManagerSum sumSearchCT(TotalHourManageReqParam totalHourManageReqParam);
	TotalHourManagerSum sumSearchCXEE(TotalHourManageReqParam totalHourManageReqParam);
	List<TotalHourManager> listSearchDownload(TotalHourManageReqParam totalHourManageReqParam);
	List<TotalHourManager> listSearchDownloadCXEE(TotalHourManageReqParam totalHourManageReqParam);
	List<TotalHourManager> listSearchDownloadCT(TotalHourManageReqParam totalHourManageReqParam);
	
}
