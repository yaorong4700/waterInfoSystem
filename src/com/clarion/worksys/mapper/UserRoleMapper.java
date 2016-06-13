package com.clarion.worksys.mapper;

import java.util.List;
import java.util.Map;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRole;
import com.clarion.worksys.httpentity.StaffReqParam;

public interface UserRoleMapper {
	UserRole getUserRoleInfo(UserRole userRole);
	String checkUserId(String userId);
}
