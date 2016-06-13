/**
 * 项目管理返回的单条数据结构
 */
package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.Project;

/**
 * @author weng_zhangchu
 *
 */
public class ProjectResponseRows {
	private int id;
	private Project cell;
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
	public Project getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public void setCell(Project cell) {
		this.cell = cell;
	}
	public ProjectResponseRows(int id,Project cell){
		this.id = id;
		this.cell = cell;
	}
}
