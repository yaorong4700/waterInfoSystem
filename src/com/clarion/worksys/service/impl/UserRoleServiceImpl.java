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
import com.clarion.worksys.entity.UserRole;
import com.clarion.worksys.httpentity.StaffReqParam;
import com.clarion.worksys.mapper.StaffMapper;
import com.clarion.worksys.mapper.UserRoleMapper;
import com.clarion.worksys.service.StaffService;
import com.clarion.worksys.service.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	public UserRole getUserByNameAndPwd(String userId,String password) {
		UserRole userRole = new UserRole();
		userRole.setUserID(userId);
		userRole.setUserPassword(password);
		return userRoleMapper.getUserRoleInfo(userRole);
	}
	
	public String checkUserId(String userId){
		return userRoleMapper.checkUserId(userId);
	}
}
