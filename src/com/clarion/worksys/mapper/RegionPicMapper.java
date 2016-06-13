package com.clarion.worksys.mapper;

import java.util.List;
import com.clarion.worksys.entity.WaterInfo;
import com.clarion.worksys.httpentity.RegionPicReqParam;
import com.clarion.worksys.httpentity.RegionSumReqParam;

public interface RegionPicMapper {
	List<WaterInfo> searchRegionPicList(RegionPicReqParam regionPicReqParam);
	int totalPageCount(RegionPicReqParam regionPicReqParam);
	void editNumByRegionDeviceCollectTime(RegionPicReqParam regionPicReqParam);
	
	void deleteByRegionDeviceCollectTime(RegionSumReqParam regionSumReqParam);
	public List<WaterInfo> downloadregionSumShow();

}
