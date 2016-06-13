/**
 * 
 */
package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.ManhourByProject;

/**
 * @author weng_zhangchu
 *
 */
public class ManhourByProjectResponseRows {
	private int id;
	private ManhourByProject cell;
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
	public ManhourByProject getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public void setCell(ManhourByProject cell) {
		this.cell = cell;
	}
	public ManhourByProjectResponseRows(int id,ManhourByProject manhourByProject){
		this.id = id;
		this.cell = manhourByProject;
	}
	
}
