package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理界面分页响应数据
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */

import com.clarion.worksys.entity.Staff;

public class StaffResponseRows {
	
	
	private int id;
	private Staff cell;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Staff getCell() {
		return cell;
	}
	public void setCell(Staff cell) {
		this.cell = cell;
	}
	public StaffResponseRows(int id,Staff staff) {
		this.id = id;
		this.cell = staff;
	}
}
