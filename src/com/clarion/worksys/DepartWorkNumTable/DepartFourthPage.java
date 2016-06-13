/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数导出第四页
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
import com.clarion.worksys.util.Const.DepartmentEnum;

/**
 * @author Chen_weijun
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DepartFourthPage {
/**
 * 	生成部门或者课别第四页（开发工数）的数据
 * @param department   部门名
 * @param branch  课别名
 * @param listProject   项目的集合
 * @param year   起始年份
 * @param month  起始月份
 * @param filenameString    唯一性字符串，防止文件重名
 * @param allMonthListManHourDtoList   所有工数信息的集合
 * @param listProjectTasks    所有作业类型的集合
 * @param workDayslist    每个月上班天数的集合
 */
	public void updateFourthXLS(String department,String branch,List<Project> listProject,int year, int month,
			                     String filenameString,List<List<ManHourDto>> allMonthListManHourDtoList,List<Project_task> listProjectTasks
			                     ,List<Integer> workDayslist) {
		
		try {
			File file = new File(Parma.TEMP_FILEFourthAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEFiveAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			String strnameString1=Parma.TEMP_FILEFourthAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//生成的模板存储所在地
			String strnameString2=Parma.TEMP_FILEFiveAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//由模板生成的最终文件所在地址
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			int weiyiko=0;
			if (month>=4&&month<=12) {
				weiyiko=(month-4)*4+1+7;
			}
			if (month>=1&&month<=3) {
				weiyiko=month*4+40;
			}
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetFour = book1.createSheet("开发工数", 3);
			//去掉网格线
			sheetFour.getSettings().setShowGridLines(false);
			sheetFour.setColumnView(0,60); 
			sheetFour.setColumnView(2,15); 
			sheetFour.setColumnView(4,20);
			sheetFour.setColumnView(5,20);
			
			WritableFont fontb= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD); 
			WritableCellFormat formatb=new WritableCellFormat(fontb); //title Alignment.CENTRE set 
			formatb.setAlignment(jxl.format.Alignment.CENTRE); 
			formatb.setBackground(Colour.LIGHT_TURQUOISE);
			formatb.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat format1=new WritableCellFormat(fontb); //text Alignment.center set
			format1.setAlignment(jxl.format.Alignment.CENTRE); 
			format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			format1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontb1_1= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.WHITE);//白色字体
			WritableCellFormat format1_1=new WritableCellFormat(fontb1_1); //白色字体
			format1_1.setAlignment(jxl.format.Alignment.CENTRE); 
			format1_1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			//format1_1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			format1_1.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN);
			format1_1.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN);
			format1_1.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.THIN);
			format1_1.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.NONE);//上边框不表示
			
			WritableCellFormat format1_2=new WritableCellFormat(fontb); //text Alignment.center set
			format1_2.setAlignment(jxl.format.Alignment.CENTRE); 
			format1_2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			format1_2.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN);
			format1_2.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN);
			format1_2.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.THIN);
			format1_2.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.NONE);//下边框不表示
			
			
			WritableCellFormat formatNum=new WritableCellFormat(fontb); //data Alignment.RIGHT set
			formatNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatNum.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formattitle=new WritableCellFormat(fontb); //ProjName Alignment.left set
			formattitle.setAlignment(jxl.format.Alignment.LEFT); 
			formattitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formattitle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			 
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(0, 0, "集计日",format1);
			sheetFour.addCell(labelaLabel);
			sheetFour.mergeCells(1, 0, 2, 0);
			Label labelaLabelmhk = new Label(1, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",format1);
			sheetFour.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(0, 1, "对象",format1);
			sheetFour.addCell(labelbLabel);
			sheetFour.mergeCells(1, 1, 2, 1);
			if (branch==null) {
				Label labelbLabelfg = new Label(1, 1, department,format1);
				sheetFour.addCell(labelbLabelfg);
			}
			else {
				Label labelbLabelfg = new Label(1, 1, department+branch,format1);
				sheetFour.addCell(labelbLabelfg);
			}
			
			int rownum=2;
//			double personMonthTotal=0.0f;
//			for (int i = 0; i < workDayslist.size(); i++) {
//				personMonthTotal+=workDayslist.get(i)*8;
//			}
			
			List<Double> listsum9=new ArrayList<Double>();
			List<Double> listsum10=new ArrayList<Double>();
			
			//if (("工程设计部").equals(department)) {
			if (DepartmentEnum.GCSJB.getName().equals(department)) {
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					double sum9=0.0;
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						for (int j2 = 0; j2 < Parma.strAdditionalProjDesignerDCOE.length; j2++) {
							if (Parma.strAdditionalProjDesignerDCOE[j2].equals(allMonthListManHourDtoList.get(j).get(m).getTask())) {
								if (("185 工程设计部DCOE").equals(allMonthListManHourDtoList.get(j).get(m).getProjectName())) {
									sum9 = Arith.add(sum9,allMonthListManHourDtoList.get(j).get(m).getTimes());
								}
							}
						}
					}
					System.out.println(sum9+"sum9");
					listsum9.add(sum9);
				}
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					double sum10=0.0;
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						for (int j2 = 0; j2 < Parma.strAdditionalProjDesignerCT.length; j2++) {
							if (Parma.strAdditionalProjDesignerCT[j2].equals(allMonthListManHourDtoList.get(j).get(m).getTask())) {
								if (("185 工程设计部CT").equals(allMonthListManHourDtoList.get(j).get(m).getProjectName())) {
									sum10 = Arith.add(sum10,allMonthListManHourDtoList.get(j).get(m).getTimes());
								}
							}
						}
					}
					listsum10.add(sum10);	
				}
			}
			/**
			 * 开发工数DCOE--CT--OUTOUT项目
			 */
			for (int m = 0; m < Parma.strProjectClientNameDevelopPage.length; m++) {
				rownum++;
				List<Project> listDepartProjectsCT=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
					if (("开发工数").equals(listProject.get(i).getCategory())) {
						if ((Parma.strProjectClientNameDevelopPage[m]).equals(listProject.get(i).getProjectClientName())) {
							listDepartProjectsCT.add(listProject.get(i));
						}
					}
				}
				Label labelckname= new Label(0,rownum, "项目名称",formatb);
				sheetFour.addCell(labelckname);
				for(int i=1;i<=Parma.strProjMonthTitle.length;i++)
				{
					Label label = new Label(i, rownum, Parma.strProjMonthTitle[i-1],formatb);
					sheetFour.addCell(label);
				}
				int temp=Parma.strProjMonthTitle.length+1;
				for(int i=Parma.strProjMonthTitle.length;i<Parma.strProjMonthTitle.length+Parma.strmonth.length-2;i++)
				{
					if (i%2==1) {
						sheetFour.mergeCells(temp, rownum, temp+1, rownum);
						Label label = new Label(temp, rownum, Parma.strmonth[i-Parma.strProjMonthTitle.length],formatPerson);
						sheetFour.addCell(label);
						temp=temp+2;
					}
					else {
						sheetFour.mergeCells(temp, rownum, temp+1, rownum);
						Label label = new Label(temp, rownum, Parma.strmonth[i-Parma.strProjMonthTitle.length],formatb);
						sheetFour.addCell(label);
						temp=temp+2;
					}
				}
				rownum++;
				for (int i = 0; i < listDepartProjectsCT.size(); i++) {
					//sheetFour.mergeCells(0, rownum, 0, rownum+1);
					if(listDepartProjectsCT.get(i).getProjectName() != null && !"".equals(listDepartProjectsCT.get(i).getProjectName())){
						Label labelPjname = new Label(0, rownum, listDepartProjectsCT.get(i).getProjectName(),format1_2);
						Label labelPjname_1 = new Label(0, rownum+1, listDepartProjectsCT.get(i).getProjectName(),format1_1);
						sheetFour.addCell(labelPjname);
						sheetFour.addCell(labelPjname_1);
					}else{
						Label labelPjname= new Label(0,rownum, listDepartProjectsCT.get(i).getProjectName(),formattitle);
						sheetFour.addCell(labelPjname);
					}
					
					sheetFour.mergeCells(1, rownum, 1, rownum+1);
					Label labelYLname= new Label(1,rownum, listDepartProjectsCT.get(i).getProjectClientNo(),format1);
					sheetFour.addCell(labelYLname);
					sheetFour.mergeCells(2, rownum, 2, rownum+1);
					Label labelDpartname= new Label(2,rownum, department,format1);
					sheetFour.addCell(labelDpartname);
					sheetFour.mergeCells(3, rownum, 3, rownum+1);
					Label labelFunctionname= new Label(3,rownum, listDepartProjectsCT.get(i).getFunction(),format1);
					sheetFour.addCell(labelFunctionname);
					//业务扩大No.  不合并单元格对应
					//sheetFour.mergeCells(4, rownum, 4, rownum+1);
					//Label labelKuoDaname= new Label(4,rownum, listDepartProjectsCT.get(i).getTransferNo(),format1);
					//sheetFour.addCell(labelKuoDaname);
					Label labelKuoDaname= new Label(4,rownum, listDepartProjectsCT.get(i).getTransferNo(),format1_2);
					sheetFour.addCell(labelKuoDaname);
					Label labelKuoDaname_1= new Label(4,rownum+1, listDepartProjectsCT.get(i).getTransferNo(),format1_1);
					sheetFour.addCell(labelKuoDaname_1);
					
					sheetFour.mergeCells(5, rownum, 5, rownum+1);
					Label labelClientname= new Label(5,rownum, listDepartProjectsCT.get(i).getProjectClientName(),format1);
					sheetFour.addCell(labelClientname);
					//sheetFour.mergeCells(6, rownum, 6, rownum+1);
					if(listDepartProjectsCT.get(i).getCarMaker() != null && !"".equals(listDepartProjectsCT.get(i).getCarMaker())){
						Label carmaker = new Label(6, rownum, listDepartProjectsCT.get(i).getCarMaker(),format1_2);
						Label carmaker_1 = new Label(6, rownum+1, listDepartProjectsCT.get(i).getCarMaker(),format1_1);
						sheetFour.addCell(carmaker);
						sheetFour.addCell(carmaker_1);
					}else{
						sheetFour.mergeCells(6, rownum, 6, rownum+1);
						Label carmaker= new Label(6,rownum, listDepartProjectsCT.get(i).getCarMaker(),format1);
						sheetFour.addCell(carmaker);
					}
					Label labelkaifaname= new Label(7,rownum, "开发工数",format1);
					sheetFour.addCell(labelkaifaname);
					Label labelfudainame= new Label(7,rownum+1,"附带工数",format1);
					sheetFour.addCell(labelfudainame);
					//if (("工程设计部").equals(department)&&("185 工程设计部CT").equals(listDepartProjectsCT.get(i).getProjectName())) {
					if (DepartmentEnum.GCSJB.getName().equals(department)&&("185 工程设计部CT").equals(listDepartProjectsCT.get(i).getProjectName())) {
						int weiyi=weiyiko;
						for (int m1 = 0; m1 < allMonthListManHourDtoList.size(); m1++) {
							Number labelnLabel_d3wabm=new Number(weiyi, rownum, 0,formatNum);
							sheetFour.addCell(labelnLabel_d3wabm); 
							Number labelil=new Number(weiyi+2, rownum, Arith.div(0, (workDayslist.get(m1)*8)),formatNum);
							sheetFour.addCell(labelil);
							Number labelnLabelfudaiabm=new Number(weiyi, rownum+1,listsum10.get(m1),formatNum);
							sheetFour.addCell(labelnLabelfudaiabm); 
							Number labelbu=new Number(weiyi+2, rownum+1, Arith.div(listsum10.get(m1), (workDayslist.get(m1)*8)),formatNum);
							sheetFour.addCell(labelbu);
							double sum=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(m1).size(); j++) {
								if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(m1).get(j).getProjectID()) {
									sum = Arith.add(sum,allMonthListManHourDtoList.get(m1).get(j).getTimes());
								}
							}
							sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
							Number labelnLabfk=new Number(weiyi+1, rownum, (double)sum,formatNum);
							sheetFour.addCell(labelnLabfk);
							sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
							Number labelftg=new Number(weiyi+1+2, rownum, Arith.div(sum, (workDayslist.get(m1)*8)),formatNum);
							sheetFour.addCell(labelftg);
							weiyi=weiyi+4;
						}
						rownum=rownum+2;
					}
					//else if(("工程设计部").equals(department)&&("185 工程设计部DCOE").equals(listDepartProjectsCT.get(i).getProjectName())){
					else if(DepartmentEnum.GCSJB.getName().equals(department)&&("185 工程设计部DCOE").equals(listDepartProjectsCT.get(i).getProjectName())){
						int weiyi=weiyiko;
						
						for (int k = 0; k < allMonthListManHourDtoList.size(); k++) {
							Number labelnLabel_d3wabm=new Number(weiyi, rownum, 0,formatNum);
							sheetFour.addCell(labelnLabel_d3wabm); 
							Number labelil=new Number(weiyi+2, rownum, Arith.div(0, (workDayslist.get(k)*8)),formatNum);
							sheetFour.addCell(labelil);
							Number labelnLabelfudaiabm=new Number(weiyi, rownum+1,listsum9.get(k),formatNum);
							sheetFour.addCell(labelnLabelfudaiabm); 
							Number labelbu=new Number(weiyi+2, rownum+1, Arith.div(listsum9.get(k), (workDayslist.get(k)*8)),formatNum);
							sheetFour.addCell(labelbu);
							double sum=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(k).size(); j++) {
								if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(k).get(j).getProjectID()) {
									sum = Arith.add(sum,allMonthListManHourDtoList.get(k).get(j).getTimes());
								}
							}
							sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
							Number labelnLabfk=new Number(weiyi+1, rownum, (double)sum,formatNum);
							sheetFour.addCell(labelnLabfk);
							sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
							Number labelftg=new Number(weiyi+1+2, rownum, Arith.div(sum, (workDayslist.get(k)*8)),formatNum);
							sheetFour.addCell(labelftg);
							weiyi=weiyi+4;
						}
						rownum=rownum+2;
					}
					else {
						int weiyi=weiyiko;
						for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
							double sumKaifa=0.0;
							
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
									if (("开发").equals(listProjectTasks.get(j2).getMemo())) {
										if (listProjectTasks.get(j2).getCategory().equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
											//if (listProjectTasks.get(j2).getTask().equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
											if (listProjectTasks.get(j2).getTaskID().equals(allMonthListManHourDtoList.get(s).get(j).getTaskID())) {
												if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(s).get(j).getProjectID()) {
													sumKaifa = Arith.add(sumKaifa,allMonthListManHourDtoList.get(s).get(j).getTimes());
												}
											}
										}
									}
								}
							}
							Number labelnLabel_d3wabm=new Number(weiyi, rownum, (double)sumKaifa,formatNum);
							sheetFour.addCell(labelnLabel_d3wabm);
							Number labelftg=new Number(weiyi+2, rownum, Arith.div(sumKaifa, (workDayslist.get(s)*8)),formatNum);
							sheetFour.addCell(labelftg);
							double sumFudai=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
									if (("附带").equals(listProjectTasks.get(j2).getMemo())) {
										if (listProjectTasks.get(j2).getCategory().equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
											//if (listProjectTasks.get(j2).getTask().equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
											if (listProjectTasks.get(j2).getTaskID().equals(allMonthListManHourDtoList.get(s).get(j).getTaskID())) {
												if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(s).get(j).getProjectID()) {
													sumFudai = Arith.add(sumFudai,allMonthListManHourDtoList.get(s).get(j).getTimes());
												}
											}
										}
									}
								}
							}
							Number labelnLabelfudaiabm=new Number(weiyi, rownum+1, (double)sumFudai,formatNum);
							sheetFour.addCell(labelnLabelfudaiabm); 
							Number labelbu=new Number(weiyi+2, rownum+1, Arith.div(sumFudai, (workDayslist.get(s)*8)),formatNum);
							sheetFour.addCell(labelbu);
							double sum=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(s).get(j).getProjectID()) {
									sum = Arith.add(sum,allMonthListManHourDtoList.get(s).get(j).getTimes());
								}
							}
							sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
							Number labelnLabfk=new Number(weiyi+1, rownum, (double)sum,formatNum);
							sheetFour.addCell(labelnLabfk);
							sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
							Number labelfr=new Number(weiyi+1+2, rownum, Arith.div(sum, (workDayslist.get(s)*8)),formatNum);
							sheetFour.addCell(labelfr);
							weiyi=weiyi+4;
						}
						rownum=rownum+2;
					}
				}
				int weiyi=weiyiko;
				sheetFour.mergeCells(0, rownum, Parma.strProjMonthTitle.length-1, rownum+1);
				Label labelTotalname= new Label(0,rownum, "合计",format1);
				sheetFour.addCell(labelTotalname);
				Label labelkaifaname= new Label(6,rownum, "开发工数",format1);
				sheetFour.addCell(labelkaifaname);
				Label labelfudainame= new Label(6,rownum+1,"附带工数",format1);
				sheetFour.addCell(labelfudainame);
				for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
					double sumKaifa=0.0;
					for (int i = 0; i < listDepartProjectsCT.size(); i++) {
						for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
							for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
								if (("开发").equals(listProjectTasks.get(j2).getMemo())) {
									if (listProjectTasks.get(j2).getCategory().equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
										//if (listProjectTasks.get(j2).getTask().equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
										if (listProjectTasks.get(j2).getTaskID().equals(allMonthListManHourDtoList.get(s).get(j).getTaskID())) {
											if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(s).get(j).getProjectID()) {
												sumKaifa = Arith.add(sumKaifa,allMonthListManHourDtoList.get(s).get(j).getTimes());
											}
										}
									}
								}
							}
						}
					}
					Number labelnLabel_d3wabm=new Number(weiyi, rownum,sumKaifa,formatNum);
					sheetFour.addCell(labelnLabel_d3wabm); 
					Number labelfr=new Number(weiyi+2, rownum, Arith.div(sumKaifa, (workDayslist.get(s)*8)),formatNum);
					sheetFour.addCell(labelfr);
					double sumFudai=0.0;
					for (int i = 0; i < listDepartProjectsCT.size(); i++) {
						for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
							for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
								if (("附带").equals(listProjectTasks.get(j2).getMemo())) {
									if (listProjectTasks.get(j2).getCategory().equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
										//if (listProjectTasks.get(j2).getTask().equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
										if (listProjectTasks.get(j2).getTaskID().equals(allMonthListManHourDtoList.get(s).get(j).getTaskID())) {
											if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(s).get(j).getProjectID()) {
												sumFudai = Arith.add(sumFudai,allMonthListManHourDtoList.get(s).get(j).getTimes());
											}
										}
									}
								}
							}
						}
					}
//					if (("工程设计部").equals(department)&&Parma.strProjectClientNameDevelopPage[m].equals("DCOE"))sumFudai+=listsum9.get(s);
//					if (("工程设计部").equals(department)&&Parma.strProjectClientNameDevelopPage[m].equals("CT"))sumFudai+=listsum10.get(s);
					if (DepartmentEnum.GCSJB.getName().equals(department)&&Parma.strProjectClientNameDevelopPage[m].equals("DCOE"))sumFudai+=listsum9.get(s);
					if (DepartmentEnum.GCSJB.getName().equals(department)&&Parma.strProjectClientNameDevelopPage[m].equals("CT"))sumFudai+=listsum10.get(s);
					Number labelnLabelfudaiabm=new Number(weiyi, rownum+1, sumFudai,formatNum);
					sheetFour.addCell(labelnLabelfudaiabm); 
					Number labeled=new Number(weiyi+2, rownum+1, Arith.div(sumFudai, (workDayslist.get(s)*8)),formatNum);
					sheetFour.addCell(labeled);
					double sumtotal=0.0;
					for (int i = 0; i < listDepartProjectsCT.size(); i++) {
						for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
							if (listDepartProjectsCT.get(i).getProjectID()==allMonthListManHourDtoList.get(s).get(j).getProjectID()) {
								sumtotal = Arith.add(sumtotal,allMonthListManHourDtoList.get(s).get(j).getTimes());
							}
		                 }
					}
					sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
					Number labelnLabfk=new Number(weiyi+1, rownum, sumtotal,formatNum);
					sheetFour.addCell(labelnLabfk);
					sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
					Number labelfrt=new Number(weiyi+1+2, rownum, Arith.div(sumtotal, (workDayslist.get(s)*8)),formatNum);
					sheetFour.addCell(labelfrt);
					weiyi=weiyi+4;
				}
				rownum=rownum+3;
			}
			
			/**
			* 对剩余的空白部分进行单元格处理
			*/ 
			WritableCellFormat formatg=new WritableCellFormat(fontb); 
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rownum; j++) {
			Cell celletemp = sheetFour.getCell(0, j);
			String resultemp = celletemp.getContents();
			if (!resultemp.isEmpty()) {
				for (int k = 1; k <=(Parma.strmonth.length-2)*2+Parma.strProjMonthTitle.length ; k++) {
				Cell celletemp1 = sheetFour.getCell(k, j);
				String resultemp1 = celletemp1.getContents();
					if (resultemp1.isEmpty()) {
						Label labelhLabel = new Label(k, j, null ,formatg);
						sheetFour.addCell(labelhLabel);
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
	File temp = new File(Parma.TEMP_FILEFiveAddress); 
	if (!temp.isFile()) {
		String[] fileList = temp.list();
		for (String string : fileList) {
			File delDile = new File(Parma.TEMP_FILEFiveAddress+"/"+string);
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
