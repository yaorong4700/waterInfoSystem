/**
 * 项目管理返回的单条数据结构
 */
package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.WaterInfo;

/**
 * @author weng_zhangchu
 *
 */
public class RegionSumResponseRows {
	private int id;
	private WaterInfo cell;
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
	public WaterInfo getCell() {
		return cell;
	}
	/**
	 * @param cell the cell to set
	 */
	public void setCell(WaterInfo cell) {
		this.cell = cell;
	}
	public RegionSumResponseRows(int id,WaterInfo waterInfo){
		this.id = id;
		this.cell = waterInfo;
	}
}
