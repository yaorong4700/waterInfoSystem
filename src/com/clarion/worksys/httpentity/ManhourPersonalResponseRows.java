/**
 * 
 */
package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.ManhourPersonalQueryView;

/**
 * @author weng_zhangchu
 * 个人工数查询返回的一行数据类，包括id以及具体数据行
 */
public class ManhourPersonalResponseRows {
	private int id;
	private ManhourPersonalQueryView cell;
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
	public ManhourPersonalQueryView getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public void setCell(ManhourPersonalQueryView cell) {
		this.cell = cell;
	}
	public ManhourPersonalResponseRows (int id,ManhourPersonalQueryView cell){
		this.id = id;
		this.cell = cell;
	}
}
