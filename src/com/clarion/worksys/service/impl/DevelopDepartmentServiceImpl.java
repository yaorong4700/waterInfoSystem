package com.clarion.worksys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.DevelopDepartment;
import com.clarion.worksys.entity.ExportClass;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.httpentity.DevelopDepartmentReqParam;
import com.clarion.worksys.mapper.DevelopDepartmentMapper;
import com.clarion.worksys.service.DevelopDepartmentService;

@Service
public class DevelopDepartmentServiceImpl implements DevelopDepartmentService {

	
	@Autowired
	private DevelopDepartmentMapper developDepartmentMapper;
	
	
	@Override
	public List<Map<String, Object>> getDepartmentList() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDepartmentList();
	}


	@Override
	public List<Map<String, Object>> getDepartmentCategoryList() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDepartmentCategoryList();
	}


	@Override
	public List<DevelopDepartment> searchList(DevelopDepartmentReqParam developDepartmentReqParam) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.searchList(developDepartmentReqParam);
	}


	@Override
	public int totalPageCount(DevelopDepartmentReqParam developDepartmentReqParam) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getTotalPageCount(developDepartmentReqParam);
	}


	@Override
	public List<DevelopDepartment> searchListCT(DevelopDepartmentReqParam developDepartmentReqParam) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.searchListCT(developDepartmentReqParam);
	}


	@Override
	public int totalPageCountCT(DevelopDepartmentReqParam developDepartmentReqParam) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getTotalPageCountCT(developDepartmentReqParam);
	}


	

	public boolean insertNewDepartment(DevelopDepartment developDepartment){
        int count = developDepartmentMapper.insertNewDepartment(developDepartment);
            return count == 1;
    }

	@Override
	public void updateDepartment(DevelopDepartment developDepartment) {
		// TODO Auto-generated method stub
		 developDepartmentMapper.updateDepartment(developDepartment);
	}


	@Override
	public void deleteByBelongCode(String[] belongCode) {
		// TODO Auto-generated method stub
		 developDepartmentMapper.deleteByBelongCode(belongCode);
	}


	@Override
	public boolean insertNewDepartmentBranch(DevelopDepartment developDepartment) {
		int count = developDepartmentMapper.insertNewDepartmentBranch(developDepartment);
        return count == 1;
	}


	@Override
	public int getMaxBranchID() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getMaxBranchID();
	}


	@Override
	public String getDepartmentID(String department) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDepartmentID(department);
	}


	

	@Override
	public List<Integer> getDeploymentList() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDeploymentList();
	}


	@Override
	public String getDepartmentIDCT(String department) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDepartmentIDCT(department);
	}


	@Override
	public boolean insertNewDepartmentCT(DevelopDepartment developDepartment) {
		 int count = developDepartmentMapper.insertNewDepartmentCT(developDepartment);
         return count == 1;
	}


	@Override
	public boolean insertNewDepartmentBranchCT(DevelopDepartment developDepartment) {
		int count = developDepartmentMapper.insertNewDepartmentBranchCT(developDepartment);
        return count == 1;
	}


	@Override
	public void updateDepartmentCT(DevelopDepartment developDepartment) {
		// TODO Auto-generated method stub
		developDepartmentMapper.updateDepartmentCT(developDepartment);
	}


	@Override
	public int getMaxBranchIDCT() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getMaxBranchIDCT();
	}


	@Override
	public boolean belongCodeExist(String belongCode) {
		// TODO Auto-generated method stub
		int count = developDepartmentMapper.belongCodeExist(belongCode);
        return count >= 1;
	}


	@Override
	public DevelopDepartment getDevelopDepartmentSelectedCT(String belongCode,String departmentID ,String branchID) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDevelopDepartmentSelectedCT(belongCode,departmentID,branchID);
	}


	@Override
	public void deleteByBelongCodeCT(String[] belongCode) {
		// TODO Auto-generated method stub
		developDepartmentMapper.deleteByBelongCodeCT(belongCode);
	}


	@Override
	public String getBranchID(String branch) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchID(branch);
	}




	@Override
	public void updateBranch(DevelopDepartment developDepartment) {
		// TODO Auto-generated method stub
		 developDepartmentMapper.updateBranch(developDepartment);
	}


	@Override
	public void updateBranchCT(DevelopDepartment developDepartment) {
		// TODO Auto-generated method stub
		 developDepartmentMapper.updateBranchCT(developDepartment);
	}


	@Override
	public int getMaxDepartmentIDCT() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getMaxDepartmentIDCT();
	}


	@Override
	public String getBranchIDCT(String belongCode) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchIDCT(belongCode);
	}


	@Override
	public List<DevelopDepartment> downloadDepartmentShow() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.downloadDepartmentShow();
	}


	@Override
	public String downloadDepartment(List<DevelopDepartment> departmentList, String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "DepartmentCXEE_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.DEPARTMENT_CXEE).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "DepartmentInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < departmentList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				
				row.getCell(0).setCellValue(i+1);//序号
				row.getCell(1).setCellValue(departmentList.get(i).getDepartment());//部门
				row.getCell(2).setCellValue(departmentList.get(i).getBranch());//课别
				row.getCell(3).setCellValue(departmentList.get(i).getTeam());//组别
				row.getCell(4).setCellValue(departmentList.get(i).getDepartmentCategory());//部门分类
				row.getCell(5).setCellValue(departmentList.get(i).getBelong());//归属
				row.getCell(6).setCellValue(departmentList.get(i).getBranchMemo());//备注
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
	public String downloadDepartmentCT(List<DevelopDepartment> departmentList, String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "DepartmentCT_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.DEPARTMENT_CT).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "DepartmentInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < departmentList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				System.out.println(HSSFWorkbook.class.getProtectionDomain().getCodeSource().getLocation());
				row.getCell(0).setCellValue(i+1);//序号
				row.getCell(1).setCellValue(departmentList.get(i).getBelongCode());//在籍归属code
				row.getCell(2).setCellValue(departmentList.get(i).getDepartment());//部
				row.getCell(3).setCellValue(departmentList.get(i).getBranch());//group
				//row.getCell(4).setCellValue(departmentList.get(i).getBelong());//归属
				row.getCell(4).setCellValue(departmentList.get(i).getBranchDeployment());//现旧
				row.getCell(5).setCellValue(departmentList.get(i).getBranchMemo());//特记事项
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
	public List<DevelopDepartment> downloadDepartmentShowCT() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.downloadDepartmentShowCT();
	}


	@Override
	public int getMaxDepartmentID() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getMaxDepartmentID();
	}


	@Override
	public List<Map<String, Object>> getBranchListMap(String departmentID) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchListMap(departmentID);
	}


	/*@Override
	public List<Map<String, Object>> getTeamListMap(String belongCode) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getTeamListMap(belongCode);
	}
*/

	@Override
	public String getBranch(String branchInput) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranch(branchInput);
	}


	@Override
	public String getTeam(String teamInput) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getTeam(teamInput);
	}


	@Override
	public List<String> getTeamListMap() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getTeamListMap();
	}


	@Override
	public List<DevelopDepartment> searchListBranch(DevelopDepartment developDepartment) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.searchListBranch(developDepartment);
	}


	@Override
	public List<Map<String, Object>> getBranchListCXEE(String departmentID) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchListMap(departmentID);
	}


	@Override
	public List<Map<String, Object>> getBranchListCT(String departmentID) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchListMap(departmentID);
	}


	@Override
	public List<String> getDepartmentIDByBelongCode(String[] belongCode) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDepartmentIDByBelongCode(belongCode);
	}


	@Override
	public boolean checkBranchByDeparmentID(String departmentID) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchCount(departmentID) > 0;
	}


	@Override
	public void deleteDepartmentByID(String departmentID) {
		// TODO Auto-generated method stub
		developDepartmentMapper.deleteDepartmentByID(departmentID);
	}


	@Override
	public boolean departmentBranchBelongCodeExist(String department, String branch, String belongCode) {
		// TODO Auto-generated method stub
		int count = developDepartmentMapper.checkBranchExist(department,branch,belongCode);
		return count >= 1;
	}


	@Override
	public void deleteByBelongCodeDepartmentBranch(String belongCodes, String departmentIDNew,
			String branchIDNew) {
		// TODO Auto-generated method stub
		developDepartmentMapper.deleteByBelongCodeDepartmentBranch(belongCodes,departmentIDNew,branchIDNew);
	}


	@Override
	public String getBranchIDByBranchCT(String branch) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchIDByBranchCT(branch);
	}


	@Override
	public List<Map<String, Object>> getDepartmentListCT() {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDepartmentListCT();
	}


	@Override
	public DevelopDepartment getDevelopDepartmentSelected(String departmentID, String branchID) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDevelopDepartmentSelected(departmentID,branchID);
	}


	@Override
	public DevelopDepartment getDepartment(String departmentID) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getDepartment(departmentID);
	}


	@Override
	public boolean checkBranchExist(String departmentID, String branchID, String belongCode) {
		// TODO Auto-generated method stub
		int count=developDepartmentMapper.checkBranch(departmentID, branchID, belongCode);
		return count>0;
	}


	@Override
	public void deleteByDepartmentBranch(String departmentIDNew, String branchIDNew) {
		// TODO Auto-generated method stub
		developDepartmentMapper.deleteByDepartmentBranch(departmentIDNew,branchIDNew);
	}


	@Override
	public boolean checkHasBranch(String departmentID, String branchID) {
		// TODO Auto-generated method stub
		int count=developDepartmentMapper.getBranchCounts(departmentID,branchID);
		return count==1;
	}


	@Override
	public boolean checkBranchBydepartmentBranchTeam(String departmentID, String branchID, String team) {
		// TODO Auto-generated method stub
		int count=developDepartmentMapper.getBranchCountByTeam(departmentID,branchID,team);
		return count==1;
	}


	@Override
	public void updateBranchByTeam(DevelopDepartment developDepartment) {
		// TODO Auto-generated method stub
		developDepartmentMapper.updateBranchByTeam(developDepartment);
	}


	@Override
	public String getBranchByTeam(String branch, String team) {
		// TODO Auto-generated method stub
		return developDepartmentMapper.getBranchByTeam(branch, team);
	}




	
}
