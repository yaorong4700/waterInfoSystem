/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数导出第三页
 * 
 * @author chen_weijun@clarion.com.cn
 * @create: 2012-2-21
 * @histroy:
 * 	2012-2-21 chenweijun
 *  # 第二版
 *   
 */
package com.clarion.worksys.PersonWorkNumTable;

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
public class PersonThirdPage {
/**
 * 生成个人集计第三页（RFI和RFQ）的数据
 * @param department  部门名
 * @param branch  课别名
 * @param listProject   所有项目的集合
 * @param year   起始年份
 * @param month   起始月份
 * @param uniqueString   唯一性字符串
 * @param allMonthListManHourDtoList 所有工数信息
 * @param listProjectTasks  所有作业类型
 * @param workDayslist   每个月上班天数集合
 */
	public void updatePersonWorkNumThirdXLS(String department,String branch,List<Project> listProject,int year, int month,
			String uniqueString,List<List<ManHourDto>> allMonthListManHourDtoList,List<Project_task> listProjectTasks
			                     ,List<Integer> workDayslist) {
		
		try {
			File file = new File(Parma.TEMP_FILEFourthAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEThirdAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			String strnameString1=Parma.TEMP_FILEThirdAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//生成的模板存储所在地
			String strnameString2=Parma.TEMP_FILEFourthAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetThree = book1.createSheet("RFI和RFQ", 2);
			sheetThree.setColumnView(0,40); 
			
			
			WritableFont fontgr= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD); 
			WritableCellFormat formatgr=new WritableCellFormat(fontgr); //title Alignment.CENTRE set
			formatgr.setAlignment(jxl.format.Alignment.CENTRE); 
			formatgr.setBackground(Colour.LIGHT_TURQUOISE);
			formatgr.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatNum=new WritableCellFormat(fontgr); // data Alignment.LEFT set
			formatNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formattitle=new WritableCellFormat(fontgr); //taskName and ProjName Alignment.LEFT set
			formattitle.setAlignment(jxl.format.Alignment.LEFT); 
			formattitle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			int weiyiko=0;
			if (month>=4&&month<=12) {
				weiyiko=(month-4)*2+2;
			}
			if (month>=1&&month<=3) {
				weiyiko=month*2+18;
			}
			int rownum=2;
			
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(0, 0, "集计日",formatgr);
			sheetThree.addCell(labelaLabel);
			sheetThree.mergeCells(1, 0, 2, 0);
			Label labelaLabelmhk = new Label(1, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formatgr);
			sheetThree.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(0, 1, "对象",formatgr);
			sheetThree.addCell(labelbLabel);
			sheetThree.mergeCells(1, 1, 2, 1);
			Label labelbLabelfg = new Label(1, 1, department,formatgr);
			sheetThree.addCell(labelbLabelfg);
			
			double personMonthTotal=0.0f;
			for (int i = 0; i < workDayslist.size(); i++) {
				personMonthTotal+=workDayslist.get(i)*8;
			}
			/**
			 * 作业类型
			 */
			rownum++;
			List<Project_task> listDepartRFIRFQTasks=new ArrayList<Project_task>();
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (("RFI和RFQ").equals(listProjectTasks.get(i).getCategory())) {
					listDepartRFIRFQTasks.add(listProjectTasks.get(i));
				}
			}
			sheetThree.mergeCells(0, rownum, 1, rownum);
			Label labelckname= new Label(0,rownum, "作业类型",formatgr);
			sheetThree.addCell(labelckname);
			
			for(int i=2;i<=Parma.strmonth.length+1;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatPerson);
					sheetThree.addCell(label);
				}
				else {
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatgr);
					sheetThree.addCell(label);
				}
			}
			rownum++;
			double sumTotalRFIRFQ=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("RFI和RFQ").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						sumTotalRFIRFQ = Arith.add(sumTotalRFIRFQ,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
			}
			for (int i = 0; i < listDepartRFIRFQTasks.size(); i++) {
				double sumHeji=0.0;
				int weiyi=weiyiko;
				sheetThree.mergeCells(0, rownum, 1, rownum);
				Label labelleixing = new Label(0, rownum, listDepartRFIRFQTasks.get(i).getTask(),formattitle);
				sheetThree.addCell(labelleixing);
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double sumTask=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (("RFI和RFQ").equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
							//if (listDepartRFIRFQTasks.get(i).getTask().equals(allMonthListManHourDtoList.get(m).get(j).getTask())) {
							if (listDepartRFIRFQTasks.get(i).getTaskID().equals(allMonthListManHourDtoList.get(m).get(j).getTaskID())) {
								sumTask = Arith.add(sumTask,allMonthListManHourDtoList.get(m).get(j).getTimes());
							}
						}
					}
					Number labelnLabel_d3wabm=new Number(weiyi, rownum,sumTask,formatNum);
					sheetThree.addCell(labelnLabel_d3wabm); 
					Number labelftg=new Number(weiyi+1, rownum, Arith.div(sumTask, (workDayslist.get(m)*8)),formatNum);
					sheetThree.addCell(labelftg);
					weiyi+=2;
					sumHeji+=sumTask;
				}
				Number labelnLabel_d3wa=new Number(26, rownum,sumHeji,formatNum);
				sheetThree.addCell(labelnLabel_d3wa); 
				Number labelgy=new Number(27, rownum,Arith.div(sumHeji,personMonthTotal),formatNum);
				sheetThree.addCell(labelgy);
				Number labelef=new Number(28, rownum,Arith.div(sumHeji, sumTotalRFIRFQ, 4)*100,formatNum);
				sheetThree.addCell(labelef);
				rownum++;
			}
			rownum++;
			/**
			 * 项目名称
			 */
			rownum++;
			List<Project> listDepartRFIRFQProjects=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
				if (("RFI和RFQ").equals(listProject.get(i).getCategory())) {
					listDepartRFIRFQProjects.add(listProject.get(i));
				}
			}
			Label labelPJname= new Label(0,rownum, "项目名称",formatgr);
			sheetThree.addCell(labelPJname);
			Label carMakername= new Label(1,rownum, "车厂",formatgr);
			sheetThree.addCell(carMakername);
			for(int i=2;i<=Parma.strmonth.length+1;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatPerson);
					sheetThree.addCell(label);
				}
				else {
					Label label = new Label(i, rownum, Parma.strmonth[i-2],formatgr);
					sheetThree.addCell(label);
				}
			}
			rownum++;
			double sumTotalRFIRFQPj=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("RFI和RFQ").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						sumTotalRFIRFQPj = Arith.add(sumTotalRFIRFQPj,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
			}
			for (int i = 0; i < listDepartRFIRFQProjects.size(); i++) {
				
				double sumHeji=0.0;
				int weiyi=weiyiko;
				Label labelleixing = new Label(0, rownum, listDepartRFIRFQProjects.get(i).getProjectName(),formattitle);
				sheetThree.addCell(labelleixing);
				Label carmaker = new Label(1, rownum, listDepartRFIRFQProjects.get(i).getCarMaker(),formattitle);
				sheetThree.addCell(carmaker);
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double sumTask=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (("RFI和RFQ").equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
							if (listDepartRFIRFQProjects.get(i).getProjectID()==(allMonthListManHourDtoList.get(m).get(j).getProjectID())) {
								sumTask = Arith.add(sumTask,allMonthListManHourDtoList.get(m).get(j).getTimes());
							}
						}
					}
					Number labelnLabel_d3wabm=new Number(weiyi, rownum,sumTask,formatNum);
					sheetThree.addCell(labelnLabel_d3wabm); 
					Number labelftg=new Number(weiyi+1, rownum, Arith.div(sumTask, (workDayslist.get(m)*8)),formatNum);
					sheetThree.addCell(labelftg);
					weiyi+=2;
				sumHeji+=sumTask;
				}
				Number labelnLabel_d3wa=new Number(26, rownum,sumHeji,formatNum);
				sheetThree.addCell(labelnLabel_d3wa);  
				Number labelgy=new Number(27, rownum,Arith.div(sumHeji,personMonthTotal),formatNum);
				sheetThree.addCell(labelgy);
				Number labelef=new Number(28, rownum,Arith.div(sumHeji, sumTotalRFIRFQPj, 4)*100,formatNum);
				sheetThree.addCell(labelef);
				rownum++;
			}
			rownum++;
			
			/**
			* 对剩余的空白部分进行单元格处理
			*/ 
			WritableCellFormat formatg=new WritableCellFormat(fontgr);
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rownum; j++) {
			Cell celletemp = sheetThree.getCell(0, j);
			String resultemp = celletemp.getContents();
			if (!resultemp.isEmpty()) {
				for (int k = 1; k <=Parma.strmonth.length ; k++) {
				Cell celletemp1 = sheetThree.getCell(k, j);
				String resultemp1 = celletemp1.getContents();
					if (resultemp1.isEmpty()) {
						Label labelhLabel = new Label(k, j, null ,formatg);
						sheetThree.addCell(labelhLabel);
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
		File temp = new File(Parma.TEMP_FILEFourthAddress); 
		if (!temp.isFile()) {
			String[] fileList = temp.list();
			for (String string : fileList) {
				File delDile = new File(Parma.TEMP_FILEFourthAddress+"/"+string);
				if(delDile.isDirectory()){
					
				}else{
					delDile.delete();
				}
			}
			temp.delete();
		}
		
		return true;
	}

	public boolean deleteTempDir(String pathname) {
		File temp = new File(pathname); 
		if (!temp.isFile()) {
			String[] fileList = temp.list();
			for (String string : fileList) {
				File delDile = new File(pathname+"/"+string);
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
