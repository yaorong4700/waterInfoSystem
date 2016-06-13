package com.clarion.worksys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.ExportClass;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.httpentity.StaffReqParam;
import com.clarion.worksys.mapper.StaffMapper;
import com.clarion.worksys.service.StaffService;
import com.clarion.worksys.util.Tools;
import com.clarion.worksys.util.TransDate;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service
public class StaffServiceImpl implements StaffService{
	@Autowired
	private StaffMapper staffMapper;
	
	public Staff getStaffById(Integer id) {
		return staffMapper.getStaffById(id);
	}

	public Staff getStaffByIdCXEE(Integer id) {
		return staffMapper.getStaffByIdCXEE(id);
	}
	
	public Staff getStaffByIdCT(Integer id) {
		return staffMapper.getStaffByIdCT(id);
	}
	
	public Staff getUserByNameAndPwd(String username, String password) {
		Staff staff = new Staff();
		staff.setEmail(username);
		staff.setPassword(password);
		return staffMapper.getStaffInfo(staff);
	}

	public boolean insertStaff(Staff staff) {
		int count = staffMapper.insertStaff(staff);
		
		if(count == 1){
			return true;
		}else{
			return false;
		}
	}


	public void updateStaff(Staff staff) {
		staffMapper.updateStaff(staff);
	}

	public List<Staff> listAllStaff() {
		return staffMapper.listAllStaff();
	}
	
	public List<Staff> listAllStaffCT() {
		return staffMapper.listAllStaffCT();
	}
	
	public List<Staff> listStaff(StaffReqParam staffReqParam) {
		// TODO Auto-generated method stub
		return staffMapper.listStaffs(staffReqParam);
	}

	public int totalPageCount(StaffReqParam staffReqParam) {
		// TODO Auto-generated method stub
		return staffMapper.totalPageCount(staffReqParam);
	}

	public void deleteStaffs(String[] ids) {
		// TODO Auto-generated method stub
		staffMapper.deleteStaffs(ids);
	}
	public void updateStaffpassword(Integer staffID, String mypasswordnew){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("staffID",staffID);
		params.put("mypasswordnew",mypasswordnew);
		staffMapper.updateStaffpassword(params);
	}
	
	public int getStaffByEmail(String email){
		return staffMapper.getStaffByEmail(email);
	}
	
	public int getStaffByJobNo(String jobNo){
		return staffMapper.getStaffByJobNo(jobNo);
	}
	
	public String getSort(String sortID){
		return staffMapper.getSort(sortID);
	}
	public List<Staff> listStaffCT(StaffReqParam staffReqParam) {
		// TODO Auto-generated method stub
		return staffMapper.listStaffsCT(staffReqParam);
	}
	public int totalPageCountCT(StaffReqParam staffReqParam) {
		// TODO Auto-generated method stub
		return staffMapper.totalPageCountCT(staffReqParam);
	}
	public String downloadStaff(List<Staff> staffList,String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "StaffList_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.STAFF_TEMPLATE_CXEE).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "StaffInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < staffList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				row.getCell(0).setCellValue(i+1);//序号
				row.getCell(1).setCellValue(staffList.get(i).getName());//姓名
				row.getCell(2).setCellValue(staffList.get(i).getPassword());//密码
				row.getCell(3).setCellValue(staffList.get(i).getJobNo());//工号
				row.getCell(4).setCellValue(staffList.get(i).getGender());//性别
				row.getCell(5).setCellValue(staffList.get(i).getDateGraduation());//毕业时间
				row.getCell(6).setCellValue(staffList.get(i).getDateIntoCompany());//进公司时间
				row.getCell(7).setCellValue(staffList.get(i).getPosition());//职位
				row.getCell(8).setCellValue(staffList.get(i).getMemo());//能力别
				row.getCell(9).setCellValue(staffList.get(i).getDepartment());//部门
				row.getCell(10).setCellValue(staffList.get(i).getBranch());//课别
				row.getCell(11).setCellValue(staffList.get(i).getTeam());//组别
				row.getCell(12).setCellValue(staffList.get(i).getSuperior());//上司
				row.getCell(13).setCellValue(staffList.get(i).getEmail());//E-Mail
				row.getCell(14).setCellValue(staffList.get(i).getStateName());//在职状态
				row.getCell(15).setCellValue(staffList.get(i).getDateQuitCompany());//离职日
				row.getCell(16).setCellValue(staffList.get(i).getRoleName());//角色权限
				row.getCell(17).setCellValue(staffList.get(i).getSort());//员工类别
				row.getCell(18).setCellValue(staffList.get(i).getCompanyName());//公司名称
				row.getCell(19).setCellValue(staffList.get(i).getComment());//备注
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
	
	public String downloadStaffCT(List<Staff> staffList,String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "StaffList_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.STAFF_TEMPLATE_CT).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "StaffInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			
			for(int i=0; i < staffList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				row.getCell(0).setCellValue(i+1);//順番
				row.getCell(1).setCellValue(staffList.get(i).getName());//氏名
				row.getCell(2).setCellValue(staffList.get(i).getJobNo());//従業員番号
				row.getCell(3).setCellValue(staffList.get(i).getPassword());//パスワード
				row.getCell(4).setCellValue(staffList.get(i).getPosition());//役職
				row.getCell(5).setCellValue(staffList.get(i).getDepartment());//部
				row.getCell(6).setCellValue(staffList.get(i).getBranch());//グループ
				row.getCell(7).setCellValue(staffList.get(i).getEnrolementCode());//在籍所属コード
				row.getCell(8).setCellValue(staffList.get(i).getEmail());//E-Mail
				row.getCell(9).setCellValue(staffList.get(i).getStateName());//在職状況
				row.getCell(10).setCellValue(staffList.get(i).getRoleName());//ロール権限
				row.getCell(11).setCellValue(staffList.get(i).getSort());//社員区分
				row.getCell(12).setCellValue(staffList.get(i).getSortID());//社員区分ID
				row.getCell(13).setCellValue(staffList.get(i).getCompanyName());//会社名称
				row.getCell(14).setCellValue(staffList.get(i).getJobCategory());//職種
				row.getCell(15).setCellValue(staffList.get(i).getDesignQualified());//設計有資格総合判定
				row.getCell(16).setCellValue(staffList.get(i).getPmLevel());//PMレベル
				row.getCell(17).setCellValue(staffList.get(i).getComment());//特記事項
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
	public List<String> getAbility() {
		
		return staffMapper.getAbility();
	}

	@Override
	public List<String> getAllSort() {
		// TODO Auto-generated method stub
		return staffMapper.getAllSort();
	}

	@Override
	public List<Staff> getJobCategory() {
		// TODO Auto-generated method stub
		return staffMapper.getJobCategory();
	}
	@Override
	public List<String> getAbilityCT() {
		
		return staffMapper.getAbilityCT();
	}

	@Override
	public List<String> getAllSortCT() {
		// TODO Auto-generated method stub
		return staffMapper.getAllSortCT();
	}

	@Override
	public List<Staff> getJobCategoryCT() {
		// TODO Auto-generated method stub
		return staffMapper.getJobCategoryCT();
	}
}
