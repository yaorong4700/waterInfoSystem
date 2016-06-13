package com.clarion.worksys.service;

import java.util.List;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRole;
import com.clarion.worksys.httpentity.StaffReqParam;

public interface UserRoleService {
	UserRole getUserByNameAndPwd(String username,String password);
	String checkUserId(String username);
}
