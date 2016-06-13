package com.clarion.worksys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clarion.worksys.entity.PageMst;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.mapper.UserRoleManageMapper;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Tools;

/**
 * 
 * @author HSCN Liuyang
 *
 */
@Service
@Transactional
public class UserRoleManageServiceImpl implements UserRoleManageService {

	@Autowired
	private UserRoleManageMapper userRoleManageMapper;
	public List<UserRoleManage> listAllRole() {
		
		return userRoleManageMapper.listAllRole();
	}
	public List<PageMst> listAllPage() {
		
		return userRoleManageMapper.listAllPage();
	}
	public List<UserRoleManage> listRoleCompetence(String keyCode) {
		return userRoleManageMapper.listRoleCompetence(keyCode);
	}
	public UserRoleManage getRoleByRoleName(String roleName) {
		return userRoleManageMapper.getRoleByRoleName(roleName);
	}
	public void deleteUserRoleManage(UserRoleManage userRoleManage) {
		userRoleManageMapper.deleteUserRoleManage(userRoleManage);
	}
	public String updateUserRole(String[] jsonLst,String hasSearch, Staff staff,UserRoleManage userRoleManageold) {
		String rtnStr = "";
		String keyCode = "";
		Map<String, Object> paramsgetkeyCode = new HashMap<String, Object>();
		paramsgetkeyCode.put("pjName", "UR");
		paramsgetkeyCode.put("insertUser", staff.getJobNo());
		if(jsonLst.length > 0){
			UserRoleManage userRoleManage =new UserRoleManage();
			userRoleManage = (UserRoleManage) Tools.jsonToBean(jsonLst[0].replace(".000Z", ".000 UTC"), userRoleManage.getClass());
			if(userRoleManageMapper.getRoleByRoleName(userRoleManage.getRoleName()) != null && hasSearch.equals("0")) {
				rtnStr = "E0041";
			} else if(hasSearch.equals("1") && 
					userRoleManageMapper.getRoleByRoleName(userRoleManage.getRoleName()) != null 
					&& !userRoleManageMapper.getRoleByRoleName(userRoleManage.getRoleName()).getKeyCode().equals(userRoleManage.getKeyCode())) {
						rtnStr = "E0041";
				} else {
				for(int i=0;i < jsonLst.length; i++){
					if(userRoleManage.getKeyCode().equals("")){
						keyCode = userRoleManageMapper.getkeyCode(paramsgetkeyCode);
					}
					userRoleManage = (UserRoleManage) Tools.jsonToBean(jsonLst[i].replace(".000Z", ".000 UTC"), userRoleManage.getClass());
					userRoleManageold.setNo(userRoleManage.getNo());
					if(userRoleManage.getKeyCode().equals("") || "1".equals(userRoleManage.getFlag())) {
						if(userRoleManage.getKeyCode().equals("")){
							userRoleManage.setKeyCode(keyCode);
						}
						userRoleManage.setInsertUser(staff.getJobNo());
						userRoleManage.setUpdateUser(staff.getJobNo());
						if("".equals(userRoleManage.getShowRoleFlag())){
							userRoleManage.setShowRoleFlag("0");
						}
						if(userRoleManage.getQueryRoleFlag().equals("")){
							userRoleManage.setQueryRoleFlag("0");
						}
						if(userRoleManage.getAlterRoleFlag().equals("")){
							userRoleManage.setAlterRoleFlag("0");
						}
						if("".equals(userRoleManage.getUploadRoleFlag())){
							userRoleManage.setUploadRoleFlag("0");
						}
						if("".equals(userRoleManage.getDownloadRoleFlag())){
							userRoleManage.setDownloadRoleFlag("0");
						}
						if(userRoleManage.getSpecialRole1Flag().equals("")){
							userRoleManage.setSpecialRole1Flag("0");
						}
						if(userRoleManage.getSpecialRole2Flag().equals("")){
							userRoleManage.setSpecialRole2Flag("0");
						}
						if(userRoleManage.getSpecialRole3Flag().equals("")){
							userRoleManage.setSpecialRole3Flag("0");
						}
						if(userRoleManage.getDepartmentFlag().equals("")){
							userRoleManage.setDepartmentFlag("0");
						}
						
						userRoleManageMapper.insertUserRoleManage(userRoleManage);
						rtnStr = "E0030" + userRoleManage.getKeyCode();
					} else {
						userRoleManage.setUpdateUser(staff.getJobNo());
						userRoleManageMapper.updateUserRoleManage(userRoleManage);
						rtnStr = "E0030" + userRoleManage.getKeyCode();
					}
				}
			}
		}
		return rtnStr;
	}
	
	public String getkeyCode(Map<String, Object> paramsgetkeyCode) {
		// TODO Auto-generated method stub
		return userRoleManageMapper.getkeyCode(paramsgetkeyCode);
	}
	@Override
	public boolean checkStaff(String keyCode) {
		// TODO Auto-generated method stub
		int count = userRoleManageMapper.checkStaff(keyCode);
		return count==0;
	}
	
}
