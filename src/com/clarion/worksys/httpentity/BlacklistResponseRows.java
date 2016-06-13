/**
 * 
 */
package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.Blacklist;

/**
 * @author weng_zhangchu
 *
 */
public class BlacklistResponseRows {
	private int id;
	private Blacklist cell;
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
	public Blacklist getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public void setCell(Blacklist cell) {
		this.cell = cell;
	}
	public  BlacklistResponseRows (int id,Blacklist cell){
		this.id = id;
		this.cell = cell;
	}

}
