package com.clarion.worksys.service;

import java.util.List;

import com.clarion.worksys.entity.WaterInfo;
import com.clarion.worksys.httpentity.RegionSumReqParam;

public interface RegionSumService {
	List<WaterInfo> searchList(RegionSumReqParam regionSumReqParam);
	int totalPageCount(RegionSumReqParam regionSumReqParam);
	void deleteByRegionDeviceCollectTime(RegionSumReqParam regionSumReqParam);
	List<WaterInfo> downloadregionSumShow();
	String downloadRegionSum(List<WaterInfo> regionSumList, String realpathString);
}
