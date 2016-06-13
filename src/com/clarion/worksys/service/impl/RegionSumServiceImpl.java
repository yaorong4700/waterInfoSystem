package com.clarion.worksys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.DevelopDepartment;
import com.clarion.worksys.entity.ExportClass;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.WaterInfo;
import com.clarion.worksys.httpentity.RegionSumReqParam;
import com.clarion.worksys.mapper.RegionSumMapper;
import com.clarion.worksys.service.RegionSumService;

@Service
public class RegionSumServiceImpl implements RegionSumService{
	@Autowired
	private RegionSumMapper regionSumMapper;
	
	public List<WaterInfo> searchList(RegionSumReqParam regionSumReqParam){
		return regionSumMapper.searchList(regionSumReqParam);
	}
	
	public int totalPageCount(RegionSumReqParam regionSumReqParam){
		return regionSumMapper.totalPageCount(regionSumReqParam);
	}
	public void deleteByRegionDeviceCollectTime(RegionSumReqParam regionSumReqParam){
		regionSumMapper.deleteByRegionDeviceCollectTime(regionSumReqParam);
	}
	
	public List<WaterInfo> downloadregionSumShow(){
		return regionSumMapper.downloadregionSumShow();
	}
	
	@Override
	public String downloadRegionSum(List<WaterInfo> regionSumList, String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "RegionSum_"
        		              + String.valueOf(c1.get(Calendar.YEAR))
                              + String.valueOf(c1.get(Calendar.MONTH) + 1)  //系统日期从0开始算起
                              + String.valueOf(c1.get(Calendar.DAY_OF_MONTH))
                              + String.valueOf(c1.get(Calendar.HOUR_OF_DAY))
                              + String.valueOf(c1.get(Calendar.MINUTE))
                              + String.valueOf(c1.get(Calendar.SECOND));
        String strnameString2 = realpathString + "\\" + filenameString + ".xls";

        //返回文件路径
        String webaddrString  = "/temp/" + filenameString + ".xls";

        try{
			File file;
			POIFSFileSystem fs;
			HSSFWorkbook wb;
			HSSFSheet sheet;
			int curruntRow=2;

			//读取模板
			file = new File(StaffServiceImpl.class.getResource(Parma.REGIONSUM).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "RegionSum");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < regionSumList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				row.getCell(0).setCellValue(regionSumList.get(i).getRegionID());
				row.getCell(1).setCellValue(regionSumList.get(i).getRegionSummary());
				row.getCell(2).setCellValue(regionSumList.get(i).getDeviceID());
				row.getCell(3).setCellValue(regionSumList.get(i).getDeviceAddress());
				row.getCell(4).setCellValue(regionSumList.get(i).getWaterNumber());
			}

			OutputStream out = new FileOutputStream(strnameString2);
			wb.write(out);
	    	out.close();
			return webaddrString;
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
