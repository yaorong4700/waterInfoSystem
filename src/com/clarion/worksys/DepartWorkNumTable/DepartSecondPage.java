/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数导出第二页
 * 
 * @author chen_weijun@clarion.com.cn
 * @create: 2012-2-21
 * @histroy:
 * 	2012-2-21 chenweijun
 *  # 第二版
 *   
 */
package com.clarion.worksys.DepartWorkNumTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.clarion.worksys.entity.ManHourDto;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.util.Arith;

/**
 * @author Chen_weijun
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DepartSecondPage {
/**
 * 生成部门或者课别第二页（量产后不具合对应）的数据
 * @param department   部门名
 * @param branch    课别名
 * @param listProject  与本部门相关的所有项目的集合
 * @param year   起始年份
 * @param month   起始月份
 * @param filenameString  唯一性字符串，防止文件重名
 * @param allMonthListManHourDtoList   所有的工数信息
 * @param listProjectTasks   所有作业类型的集合
 * @param workDayslist   每个月上班天数的集合
 */
	public void updateSecondXLS(String department,String branch,List<Project> listProject,int year, int month,
			                     String filenameString,List<List<ManHourDto>> allMonthListManHourDtoList,List<Project_task> listProjectTasks
			                     ,List<Integer> workDayslist) {
		
		try {
			File file = new File(Parma.TEMP_FILEThirdAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILESecondAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			String strnameString1=Parma.TEMP_FILESecondAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//生成的模板存储所在地
			String strnameString2=Parma.TEMP_FILEThirdAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//由模板生成的最终文件所在地址
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			
			
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetTwo = book1.createSheet("量产后不具合对应", 1);
			
			
			WritableFont fontbl= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD); 
			WritableCellFormat formatbl=new WritableCellFormat(fontbl); //data set alignment center
			formatbl.setAlignment(jxl.format.Alignment.CENTRE); 
			formatbl.setBackground(Colour.LIGHT_TURQUOISE);
			formatbl.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatNum=new WritableCellFormat(fontbl); //data set alignment RIGHT 
			formatNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formattitle=new WritableCellFormat(fontbl); //Title set alignment left
			formattitle.setAlignment(jxl.format.Alignment.LEFT); 
			formattitle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			int rownum=2;
			sheetTwo.setColumnView(0,30);
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(0, 0, "集计日",formatbl);
			sheetTwo.addCell(labelaLabel);
			sheetTwo.mergeCells(1, 0, 2, 0);
			Label labelaLabelmhk = new Label(1, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formatbl);
			sheetTwo.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(0, 1, "对象",formatbl);
			sheetTwo.addCell(labelbLabel);
			sheetTwo.mergeCells(1, 1, 2, 1);
			if (branch==null) {
				Label labelbLabelfg = new Label(1, 1, department,formatbl);
				sheetTwo.addCell(labelbLabelfg);
			}
			else {
				Label labelbLabelfg = new Label(1, 1, department+branch,formatbl);
				sheetTwo.addCell(labelbLabelfg);
			}
			
			
			double personMonthTotal=0.0f;
			for (int i = 0; i < workDayslist.size(); i++) {
				personMonthTotal+=workDayslist.get(i)*8;
			}
			int weiyitemp=0;
			if (month>=4&&month<=12) {
				weiyitemp=(month-4)*2+2;
			}
			if (month>=1&&month<=3) {
				weiyitemp=month*2+18;
			}
			/**
			 * 作业类型
			 */
			rownum++;
			List<Project_task> listDepartBuJuHeTasks=new ArrayList<Project_task>();
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (("量产后不具合对应").equals(listProjectTasks.get(i).getCategory())) {
					listDepartBuJuHeTasks.add(listProjectTasks.get(i));
				}
			}
			sheetTwo.mergeCells(0, rownum, 1, rownum);
			Label labelckname= new Label(0,rownum, "作业类型",formatbl);
			sheetTwo.addCell(labelckname);
			
			for(int i=2;i<=Parma.strmonth.length+1;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatPerson);
					sheetTwo.addCell(label);
				}
				else {
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatbl);
					sheetTwo.addCell(label);
				}
			}
			rownum++;
			double sumTotalBuJuHe=0.0;
		
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int j = 0; j < allMonthListManHourDtoList.get(i).size(); j++) {
					if (("量产后不具合对应").equals(allMonthListManHourDtoList.get(i).get(j).getCategory())) {
						sumTotalBuJuHe = Arith.add(sumTotalBuJuHe,allMonthListManHourDtoList.get(i).get(j).getTimes());
					}
				}
			}
			for (int i = 0; i < listDepartBuJuHeTasks.size(); i++) {
				double sumHeji=0.0;
				int weiyi=weiyitemp;
				sheetTwo.mergeCells(0, rownum, 1, rownum);
				Label labelleixing = new Label(0, rownum, listDepartBuJuHeTasks.get(i).getTask(),formattitle);
				sheetTwo.addCell(labelleixing);
			for (int m = 0;m < allMonthListManHourDtoList.size();m++) {
				double sumTask=0.0;
				for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
					if (("量产后不具合对应").equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
						//if (listDepartBuJuHeTasks.get(i).getTask().equals(allMonthListManHourDtoList.get(m).get(j).getTask())) {
						if (listDepartBuJuHeTasks.get(i).getTaskID().equals(allMonthListManHourDtoList.get(m).get(j).getTaskID())) {
							sumTask = Arith.add(sumTask,allMonthListManHourDtoList.get(m).get(j).getTimes());
						}
					}
				}
				Number labelnLabel_d3wabm=new Number(weiyi, rownum, sumTask,formatNum);
				sheetTwo.addCell(labelnLabel_d3wabm);
				Number labelftg=new Number(weiyi+1, rownum, Arith.div(sumTask, (workDayslist.get(m)*8)),formatNum);
				sheetTwo.addCell(labelftg);
				weiyi+=2;
				sumHeji+=sumTask;
			}
				Number labelnLabel_d3wa=new Number(26, rownum,sumHeji,formatNum);
				sheetTwo.addCell(labelnLabel_d3wa);   
				Number labelgy=new Number(27, rownum,Arith.div(sumHeji,personMonthTotal),formatNum);
				sheetTwo.addCell(labelgy);
				Number labelef=new Number(28, rownum,Arith.div(sumHeji, sumTotalBuJuHe, 4)*100,formatNum);
				sheetTwo.addCell(labelef);
				rownum++;
			}
			rownum++;
			/**
			 * 项目名称
			 */
			rownum++;
			List<Project> listDepartBuJuHeProjects=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
				if (("量产后不具合对应").equals(listProject.get(i).getCategory())) {
					listDepartBuJuHeProjects.add(listProject.get(i));
				}
			}
			Label labelPJname= new Label(0,rownum, "项目名称",formatbl);
			sheetTwo.addCell(labelPJname);
			Label carMakername= new Label(1,rownum, "车厂",formatbl);
			sheetTwo.addCell(carMakername);
			for(int i=2;i<=Parma.strmonth.length+1;i++)
			{
				if (i%2==1) {
					
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatPerson);
					sheetTwo.addCell(label);
				}
				else {
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatbl);
					sheetTwo.addCell(label);
				}
			}
			rownum++;
			double sumTotalRFIRFQPj=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("量产后不具合对应").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						sumTotalRFIRFQPj = Arith.add(sumTotalRFIRFQPj,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
			}
			for (int i = 0; i < listDepartBuJuHeProjects.size(); i++) {
				double sumHeji=0.0;
				int weiyi=weiyitemp;
				Label labelleixing = new Label(0, rownum, listDepartBuJuHeProjects.get(i).getProjectName(),formattitle);
				sheetTwo.addCell(labelleixing);
				Label carmaker = new Label(1, rownum, listDepartBuJuHeProjects.get(i).getCarMaker(),formattitle);
				sheetTwo.addCell(carmaker);
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double sumTask=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (("量产后不具合对应").equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
							if (listDepartBuJuHeProjects.get(i).getProjectID().intValue()==(allMonthListManHourDtoList.get(m).get(j).getProjectID())) {
								sumTask = Arith.add(sumTask,allMonthListManHourDtoList.get(m).get(j).getTimes());
							}
						}
					}
					Number labelnLabel_d3wabm=new Number(weiyi, rownum,sumTask,formatNum);
					sheetTwo.addCell(labelnLabel_d3wabm); 
					Number labelftg=new Number(weiyi+1, rownum, Arith.div(sumTask, (workDayslist.get(m)*8)),formatNum);
					sheetTwo.addCell(labelftg);
					weiyi+=2;
				sumHeji+=sumTask;
				}
				Number labelnLabel_d3wa=new Number(26, rownum,sumHeji,formatNum);
				sheetTwo.addCell(labelnLabel_d3wa); 
				Number labelgy=new Number(27, rownum,Arith.div(sumHeji,personMonthTotal),formatNum);
				sheetTwo.addCell(labelgy);
				Number labelef=new Number(28, rownum,Arith.div(sumHeji, sumTotalRFIRFQPj, 4)*100,formatNum);
				sheetTwo.addCell(labelef);
				rownum++;
			}
			rownum++;
			
			/**
			* 对剩余的空白部分进行单元格处理
			*/
			WritableCellFormat formatg=new WritableCellFormat(fontbl); 
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rownum; j++) {
				Cell celletemp = sheetTwo.getCell(0, j);
				String resultemp = celletemp.getContents();
				if (!resultemp.isEmpty()) {
					for (int k = 1; k <=Parma.strmonth.length ; k++) {
					Cell celletemp1 = sheetTwo.getCell(k, j);
					String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							Label labelhLabel = new Label(k, j, null ,formatg);
							sheetTwo.addCell(labelhLabel);
						}
					
					}	
				}
			}
			book1.write();
			book1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean deleteTempDir() {
		File temp = new File(Parma.TEMP_FILEThirdAddress); 
		if (!temp.isFile()) {
			String[] fileList = temp.list();
			for (String string : fileList) {
				File delDile = new File(Parma.TEMP_FILEThirdAddress+"/"+string);
				if(delDile.isDirectory()){
					
				}else{
					delDile.delete();
				}
				
			}
			temp.delete();
		}
		return true;
	}
}
