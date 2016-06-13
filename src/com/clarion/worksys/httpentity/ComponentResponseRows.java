package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * コンポーネント管理界面分页响应数据
 * 
 * @author hscn liuy
 * @create: 2015-12-28
 * @histroy:
 * 	2015-12-28 liuy
 *  # 初版
 *   
 */

import com.clarion.worksys.entity.Component;

public class ComponentResponseRows {
	
	
	private int id;
	private Component cell;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Component getCell() {
		return cell;
	}
	public void setCell(Component cell) {
		this.cell = cell;
	}
	public ComponentResponseRows(int id,Component staff) {
		this.id = id;
		this.cell = staff;
	}
}
