package com.clarion.worksys.mapper;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.PageMst;
import com.clarion.worksys.entity.UserRoleManage;
/**
 * 
 * @author HSCN 张贤良
 *
 */
public interface UserRoleManageMapper {
	
	List<UserRoleManage> listAllRole();
	List<PageMst> listAllPage();
	List<UserRoleManage> listRoleCompetence(String keyCode);
	void updateUserRoleManage(UserRoleManage userRoleManage);
	int insertUserRoleManage(UserRoleManage userRoleManage);
	UserRoleManage getRoleByRoleName(String roleName);
	void deleteUserRoleManage(UserRoleManage userRoleManage);
	String getkeyCode(Map<String, Object> paramsgetkeyCode);
	int checkStaff(String keyCode);

}
