package com.clarion.worksys.service;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.PageMst;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
/**
 * 
 * @author HSCN 张贤良
 *
 */
public interface UserRoleManageService {
	public List<UserRoleManage> listAllRole();
	public List<PageMst> listAllPage();
	public List<UserRoleManage> listRoleCompetence(String keyCode);
	UserRoleManage getRoleByRoleName(String roleName);
	public void deleteUserRoleManage(UserRoleManage userRoleManage);
	public String updateUserRole(String[] jsonLst,String hasSearch, Staff staff,UserRoleManage userRoleManage);
	public String getkeyCode(Map<String, Object> paramsgetkeyCode);
	public boolean checkStaff(String keyCode);
}
