package com.clarion.worksys.mapper;

import java.util.List;
import com.clarion.worksys.entity.WaterInfo;
import com.clarion.worksys.httpentity.RegionSumReqParam;

public interface RegionSumMapper {
	List<WaterInfo> searchList(RegionSumReqParam regionSumReqParam);
	int totalPageCount(RegionSumReqParam regionSumReqParam);
	void deleteByRegionDeviceCollectTime(RegionSumReqParam regionSumReqParam);
	public List<WaterInfo> downloadregionSumShow();
}
