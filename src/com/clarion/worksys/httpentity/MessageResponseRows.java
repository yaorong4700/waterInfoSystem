package com.clarion.worksys.httpentity;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 员工管理界面分页响应数据
 * 
 * @author chen_weijun@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 *  2013-3-1 chenweijun
 *  初版
 *   
 */

import com.clarion.worksys.entity.Message;

public class MessageResponseRows {
	
	
	private int id;
	private Message cell;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Message getCell() {
		return cell;
	}
	public void setCell(Message cell) {
		this.cell = cell;
	}
	public MessageResponseRows(int id,Message message) {
		this.id = id;
		this.cell = message;
	}
}
