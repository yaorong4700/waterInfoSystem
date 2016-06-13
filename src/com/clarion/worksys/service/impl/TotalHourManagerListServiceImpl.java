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

import com.clarion.worksys.entity.ExportClass;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.TotalHourManager;
import com.clarion.worksys.entity.TotalHourManagerSum;
import com.clarion.worksys.httpentity.TotalHourManageReqParam;
import com.clarion.worksys.mapper.TotalHourManageMapper;
import com.clarion.worksys.service.TotalHourManagerListService;

@Service
public class TotalHourManagerListServiceImpl implements TotalHourManagerListService {

	
	
	@Autowired
	private TotalHourManageMapper totalHourManageMapper;
	@Override
	public List<TotalHourManager> searchList(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.listSearch(totalHourManageReqParam);
	}

	@Override
	public int totalPageCount(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.totalPageCount(totalHourManageReqParam);
	}

	@Override
	public TotalHourManagerSum sumSearch(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.sumSearch(totalHourManageReqParam);
	}

	@Override
	public List<TotalHourManager> searchListCXEE(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		 return totalHourManageMapper.listSearchCXEE(totalHourManageReqParam);
	}

	@Override
	public List<TotalHourManager> searchListCT(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		 return totalHourManageMapper.listSearchCT(totalHourManageReqParam);
	}

	@Override
	public int totalPageCountCXEE(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.totalPageCountCXEE(totalHourManageReqParam);
	}

	@Override
	public int totalPageCountCT(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.totalPageCountCT(totalHourManageReqParam);
	}

	@Override
	public TotalHourManagerSum sumSearchCT(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.sumSearchCT(totalHourManageReqParam);
	}

	@Override
	public TotalHourManagerSum sumSearchCXEE(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.sumSearchCXEE(totalHourManageReqParam);
	}

	@Override
	public String downloadTotalHour(List<TotalHourManager> totalHourManager, String realpathString) {
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "TotalManHour_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.TOTALMANHOUR).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "TotalHourInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < totalHourManager.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				row.getCell(0).setCellValue(i+1);//序号
				row.getCell(1).setCellValue(totalHourManager.get(i).getDepartment());//部
				row.getCell(2).setCellValue(totalHourManager.get(i).getBranch());//グループ
				row.getCell(3).setCellValue(totalHourManager.get(i).getStaffName());//氏名
				row.getCell(4).setCellValue(totalHourManager.get(i).getPosition());//役職
				row.getCell(5).setCellValue(totalHourManager.get(i).getJobCategory());//職種
				row.getCell(6).setCellValue(totalHourManager.get(i).getSort());//社員区分
				row.getCell(7).setCellValue(totalHourManager.get(i).getPJNo());//PJ No.
				row.getCell(8).setCellValue(totalHourManager.get(i).getTempPJNo());//仮PJ No.
				row.getCell(9).setCellValue(totalHourManager.get(i).getPJName());//PJ名
				row.getCell(10).setCellValue(totalHourManager.get(i).getTransferNo());//管理項番
				row.getCell(11).setCellValue(totalHourManager.get(i).getItemName());//アイテム名称
				row.getCell(12).setCellValue(totalHourManager.get(i).getProjectClientNo());//依頼No.
				row.getCell(13).setCellValue(totalHourManager.get(i).getProjectName());//依頼項目名
				row.getCell(14).setCellValue(totalHourManager.get(i).getCategory());//開発段階
				row.getCell(15).setCellValue(totalHourManager.get(i).getCarMaker());//メーカー
				row.getCell(16).setCellValue(totalHourManager.get(i).getProjectClientName());//依赖方
				row.getCell(17).setCellValue(totalHourManager.get(i).getFunction());//機能
				row.getCell(18).setCellValue(totalHourManager.get(i).getAchieve4());
				row.getCell(19).setCellValue(totalHourManager.get(i).getAchieve5());
				row.getCell(20).setCellValue(totalHourManager.get(i).getAchieve6());
				row.getCell(21).setCellValue(totalHourManager.get(i).getAchieve7());
				row.getCell(22).setCellValue(totalHourManager.get(i).getAchieve8());
				row.getCell(23).setCellValue(totalHourManager.get(i).getAchieve9());
				row.getCell(24).setCellValue(totalHourManager.get(i).getAchieve10());
				row.getCell(25).setCellValue(totalHourManager.get(i).getAchieve11());
				row.getCell(26).setCellValue(totalHourManager.get(i).getAchieve12());
				row.getCell(27).setCellValue(totalHourManager.get(i).getAchieve1());
				row.getCell(28).setCellValue(totalHourManager.get(i).getAchieve2());
				row.getCell(29).setCellValue(totalHourManager.get(i).getAchieve3());
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

	@Override
	public List<TotalHourManager> searchListDownload(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.listSearchDownload(totalHourManageReqParam);
	}

	@Override
	public List<TotalHourManager> searchListDownloadCXEE(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.listSearchDownloadCXEE(totalHourManageReqParam);
	}

	@Override
	public List<TotalHourManager> searchListDownloadCT(TotalHourManageReqParam totalHourManageReqParam) {
		// TODO Auto-generated method stub
		return totalHourManageMapper.listSearchDownloadCT(totalHourManageReqParam);
	}

	

}
