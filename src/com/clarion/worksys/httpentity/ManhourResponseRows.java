/**
 * 
 */
package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.ManhourShowData;

/**
 * 工数显示返回的一行数据类，包括id以及具体数据行
 * @author weng_zhangchu
 *
 */
public class ManhourResponseRows {
	private int id;
	private ManhourShowData cell;
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
	 * @return the manhourShowData
	 */
	public ManhourShowData getManhourShowData() {
		return cell;
	}
	/**
	 * @param manhourShowData the manhourShowData to set
	 */
	public void setManhourShowData(ManhourShowData cell) {
		this.cell = cell;
	}
	public  ManhourResponseRows(int id,ManhourShowData cell)
	{
		this.id = id;
		this.cell = cell;
	}

}
