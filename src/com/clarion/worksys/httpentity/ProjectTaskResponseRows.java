/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 作业类型管理返回的单条数据类
 * 
 * @author weng_zhangchu@clarion.com.cn
 * @create: 2013-3-19
 * @histroy:
 * 	2013-3-19 weng_zhangchu
 *  # 初版
 *   
 */
package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.Project_task;

/**
 * @author weng_zhangchu
 *
 */
public class ProjectTaskResponseRows {
	private int id;
	private Project_task cell;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the cell
	 */
	public Project_task getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public void setCell(Project_task cell) {
		this.cell = cell;
	}
	public ProjectTaskResponseRows(int id,Project_task cell)
	{
		this.id = id;
		this.cell = cell;
	}
}
