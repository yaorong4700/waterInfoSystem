package com.clarion.worksys.service;

import java.util.List;

import com.clarion.worksys.entity.WaterInfo;
import com.clarion.worksys.httpentity.RegionPicReqParam;
import com.clarion.worksys.httpentity.RegionSumReqParam;

public interface RegionPicService {
	List<WaterInfo> searchRegionPicList(RegionPicReqParam regionPicReqParam);
	int totalPageCount(RegionPicReqParam regionPicReqParam);
	void editNumByRegionDeviceCollectTime(RegionPicReqParam regionPicReqParam);
	
	void deleteByRegionDeviceCollectTime(RegionSumReqParam regionSumReqParam);
	List<WaterInfo> downloadregionSumShow();
	String downloadRegionSum(List<WaterInfo> regionSumList, String realpathString);
}
