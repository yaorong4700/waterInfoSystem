package com.clarion.worksys.httpentity;

import com.clarion.worksys.entity.DevelopDepartment;

public class DevelopDepartmentResponseRows {

	
	
	private int id;
	private DevelopDepartment cell;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DevelopDepartment getCell() {
		return cell;
	}
	public void setCell(DevelopDepartment cell) {
		this.cell = cell;
	}
	
	public  DevelopDepartmentResponseRows(int id,DevelopDepartment cell)
	{
		this.id = id;
		this.cell = cell;
	}
}
