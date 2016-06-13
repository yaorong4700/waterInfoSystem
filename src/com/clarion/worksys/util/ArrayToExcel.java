package com.clarion.worksys.util;
import java.util.List;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ArrayToExcel {

	private WritableWorkbook workbook;
	public ArrayToExcel(WritableWorkbook workbook) {
		this.workbook = workbook;
	}
	
	public void createSheet(String sheetName,Integer sheetNum,List<String[]> date) throws WriteException {
		WritableSheet sheet = workbook.createSheet(sheetName,sheetNum);
		
		for (int i = 0; i < date.get(0).length; i++) {
				sheet.setColumnView(i, 25);									
		}
		WritableFont font1= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
		WritableCellFormat format=new WritableCellFormat(font1);
		format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
		format.setAlignment(jxl.format.Alignment.CENTRE);
		for (int i=0;i<date.size();i++) {
			int j = 0;
			for (String string : date.get(i)) {
				Label label = new Label(j, i, string,format);
				sheet.addCell(label);
				j++;
			}
		}

	}
}
