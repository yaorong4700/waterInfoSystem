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
package com.clarion.worksys.CompanyWorkNumTable;

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
public class CompanyFourthPage {
/**
 * 生成公司整体第四页（开发工数）的数据
 * @param department   此处为“公司整体”
 * @param listProject  公司所有项目的集合
 * @param year   起始年份
 * @param month  起始月份
 * @param filenameString   唯一性字符串，防止文件重名
 * @param allMonthListManHourDtoList   所有的工数信息
 * @param listProjectTasksmp   所有的作业类型的集合
 * @param workDayslist   每个月上班天数的集合
 */
	public void updateFourthXLS(String department,List<Project> listProject,int year, int month,
			                     String filenameString,List<List<ManHourDto>> allMonthListManHourDtoList,List<Project_task> listProjectTasksmp
			                     ,List<Integer> workDayslist) {
		try {
			File file = new File(Parma.TEMP_FILEFourthAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEFirstAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			int weiyiko=0;
			if (month>=4&&month<=12) {
				weiyiko=(month-4)*4+8;
			}
			if (month>=1&&month<=3) {
				weiyiko=month*4+40;
			}
			
			List<Project_task> listProjectTasks=new ArrayList<Project_task>();
			for (int i = 0; i < listProjectTasksmp.size(); i++) {
				if (listProjectTasksmp.get(i).getCategory().equals("开发工数")) {
					listProjectTasks.add(listProjectTasksmp.get(i));
				}
			}
			for (int j = 0; j < listProjectTasks.size(); j++) {
				String temp=listProjectTasks.get(j).getTask();
				for (int i = j+1; i < listProjectTasks.size(); i++) {
					if (temp.equals(listProjectTasks.get(i).getTask())) {
						if (listProjectTasks.get(j).getMemo().equals(listProjectTasks.get(i).getMemo())) {
								listProjectTasks.get(i).setTask("");
							
						}
						
					}
				}
		    }
			String strnameString1=Parma.TEMP_FILEFourthAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//生成的模板存储所在地
			String strnameString2=Parma.TEMP_FILEFirstAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//由模板生成的最终文件所在地址
		
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
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
			WritableCellFormat formatb=new WritableCellFormat(fontb); 
			formatb.setAlignment(jxl.format.Alignment.CENTRE); 
			formatb.setBackground(Colour.LIGHT_TURQUOISE);
			formatb.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat format1=new WritableCellFormat(fontb); 
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
			
			WritableCellFormat formatNum=new WritableCellFormat(fontb); 
			formatNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatNum.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatTitle=new WritableCellFormat(fontb); 
			formatTitle.setAlignment(jxl.format.Alignment.LEFT); 
			formatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatTitle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			List<List<ManHourDto>> listManHourDtoTotal=new ArrayList<List<ManHourDto>>();;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				List<ManHourDto> ManHourDtoTotal=new ArrayList<ManHourDto>();
				for (int m = 0; m < allMonthListManHourDtoList.get(i).size(); m++) {
					if (("开发工数").equals(allMonthListManHourDtoList.get(i).get(m).getCategory())) {
						ManHourDtoTotal.add(allMonthListManHourDtoList.get(i).get(m));
					}
				}
				listManHourDtoTotal.add(ManHourDtoTotal);
			}
			List<Double> listsum9=new ArrayList<Double>();
			List<Double> listsum10=new ArrayList<Double>();
				for (int j = 0; j < listManHourDtoTotal.size(); j++) {
					double sum9=0.0;
					for (int m = 0; m < listManHourDtoTotal.get(j).size(); m++) {
						for (int j2 = 0; j2 < Parma.strAdditionalProjDesignerCT.length; j2++) {
							if (Parma.strAdditionalProjDesignerCT[j2].equals(listManHourDtoTotal.get(j).get(m).getTask())) {
								if (286==listManHourDtoTotal.get(j).get(m).getProjectID()) {
									sum9 = Arith.add(sum9,listManHourDtoTotal.get(j).get(m).getTimes());
								}
							}
						}
					}
					listsum9.add(sum9);	
				}
				for (int j = 0; j < listManHourDtoTotal.size(); j++) {
					double sum10=0.0;
					for (int m = 0; m < listManHourDtoTotal.get(j).size(); m++) {
						for (int j2 = 0; j2 < Parma.strAdditionalProjDesignerDCOE.length; j2++) {
							if (Parma.strAdditionalProjDesignerDCOE[j2].equals(listManHourDtoTotal.get(j).get(m).getTask())) {
								if (304==listManHourDtoTotal.get(j).get(m).getProjectID()) {
									sum10 = Arith.add(sum10,listManHourDtoTotal.get(j).get(m).getTimes());
								}
							}
						}
					}
					listsum10.add(sum10);		
				}
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(0, 0, "集记日",format1);
			sheetFour.addCell(labelaLabel);
			sheetFour.mergeCells(1, 0, 2, 0);
			Label labelaLabelmhk = new Label(1, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",format1);
			sheetFour.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(0, 1, "对象",format1);
			sheetFour.addCell(labelbLabel);
			sheetFour.mergeCells(1, 1, 2, 1);
			Label labelbLabelfg = new Label(1, 1, department,format1);
			sheetFour.addCell(labelbLabelfg);
			int rownum=2;
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
						Label labelPjname= new Label(0,rownum, listDepartProjectsCT.get(i).getProjectName(),formatTitle);
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
						sheetFour.addCell(carmaker);
						Label carmaker_1 = new Label(6, rownum+1, listDepartProjectsCT.get(i).getCarMaker(),format1_1);
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
					int weiyi=weiyiko;
					if (("185 工程设计部CT").equals(listDepartProjectsCT.get(i).getProjectName())) {
						for (int mk = 0; mk < listManHourDtoTotal.size(); mk++) {
							Number labelnLabel_d3wabm=new Number(weiyi, rownum, 0,formatNum);
							sheetFour.addCell(labelnLabel_d3wabm); 
							Number labelil=new Number(weiyi+2, rownum, Arith.div(0, (workDayslist.get(mk)*8)),formatNum);
							sheetFour.addCell(labelil);
							Number labelnLabelfudaiabm=new Number(weiyi, rownum+1,listsum9.get(mk),formatNum);
							sheetFour.addCell(labelnLabelfudaiabm); 
							Number labelbu=new Number(weiyi+2, rownum+1, Arith.div(listsum9.get(mk), (workDayslist.get(mk)*8)),formatNum);
							sheetFour.addCell(labelbu);
							double sum=0.0;
							for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
								if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
									sum = Arith.add(sum,listManHourDtoTotal.get(mk).get(j).getTimes());
								}
							}
							sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
							Number labelnLabfk=new Number(weiyi+1, rownum, (double)sum,formatNum);
							sheetFour.addCell(labelnLabfk);
							sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
							Number labelftg=new Number(weiyi+1+2, rownum, Arith.div(sum, (workDayslist.get(mk)*8)),formatNum);
							sheetFour.addCell(labelftg);
							weiyi=weiyi+4;
						}
						rownum=rownum+2;
					}
					else if(("185 工程设计部DCOE").equals(listDepartProjectsCT.get(i).getProjectName())){
						for (int mk = 0; mk < listManHourDtoTotal.size(); mk++) {
							Number labelnLabel_d3wabm=new Number(weiyi, rownum, 0,formatNum);
							sheetFour.addCell(labelnLabel_d3wabm); 
							Number labelil=new Number(weiyi+2, rownum, Arith.div(0, (workDayslist.get(mk)*8)),formatNum);
							sheetFour.addCell(labelil);
							Number labelnLabelfudaiabm=new Number(weiyi, rownum+1,listsum10.get(mk),formatNum);
							sheetFour.addCell(labelnLabelfudaiabm);
							Number labelbu=new Number(weiyi+2, rownum+1, Arith.div(listsum10.get(mk), (workDayslist.get(mk)*8)),formatNum);
							sheetFour.addCell(labelbu);
							double sum=0.0;
							for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
								if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
									sum = Arith.add(sum,listManHourDtoTotal.get(mk).get(j).getTimes());
								}
							}
							sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
							Number labelnLabfk=new Number(weiyi+1, rownum, (double)sum,formatNum);
							sheetFour.addCell(labelnLabfk);
							sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
							Number labelftg=new Number(weiyi+1+2, rownum, Arith.div(sum, (workDayslist.get(mk)*8)),formatNum);
							sheetFour.addCell(labelftg);
							weiyi=weiyi+4;
						}
						rownum=rownum+2;
					}
					else {
					for (int mk = 0; mk < listManHourDtoTotal.size(); mk++) {
						double sumKaifa=0.0;
						for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
							for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
								if (("开发").equals(listProjectTasks.get(j2).getMemo())) {
									if (listProjectTasks.get(j2).getCategory().equals(listManHourDtoTotal.get(mk).get(j).getCategory())) {
										//if (listProjectTasks.get(j2).getTask().equals(listManHourDtoTotal.get(mk).get(j).getTask())) {
										if (listProjectTasks.get(j2).getTaskID().equals(listManHourDtoTotal.get(mk).get(j).getTaskID())) {
											if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
												sumKaifa = Arith.add(sumKaifa,listManHourDtoTotal.get(mk).get(j).getTimes());
											}
										}
									}
								}
							}
						}
						Number labelnLabel_d3wabm=new Number(weiyi, rownum,sumKaifa,formatNum);
						sheetFour.addCell(labelnLabel_d3wabm); 
						Number labelftg=new Number(weiyi+2, rownum, Arith.div(sumKaifa, (workDayslist.get(mk)*8)),formatNum);
						sheetFour.addCell(labelftg);
						double sumFudai=0.0;
						for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
							for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
								if (("附带").equals(listProjectTasks.get(j2).getMemo())) {
									if (listProjectTasks.get(j2).getCategory().equals(listManHourDtoTotal.get(mk).get(j).getCategory())) {
										//if (listProjectTasks.get(j2).getTask().equals(listManHourDtoTotal.get(mk).get(j).getTask())) {
										if (listProjectTasks.get(j2).getTaskID().equals(listManHourDtoTotal.get(mk).get(j).getTaskID())) {
											if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
												sumFudai = Arith.add(sumFudai,listManHourDtoTotal.get(mk).get(j).getTimes());
											}
										}
									}
								}
							}
						}
						Number labelnLabelfudaiabm=new Number(weiyi, rownum+1,sumFudai,formatNum);
						sheetFour.addCell(labelnLabelfudaiabm); 
						Number labelbu=new Number(weiyi+2, rownum+1, Arith.div(sumFudai, (workDayslist.get(mk)*8)),formatNum);
						sheetFour.addCell(labelbu);
						double sum=0.0;
						for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
								if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
										sum = Arith.add(sum,listManHourDtoTotal.get(mk).get(j).getTimes());
										}
								}
						sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
						Number labelnLabfk=new Number(weiyi+1, rownum,sum,formatNum);
						sheetFour.addCell(labelnLabfk);
						sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
						Number labelfr=new Number(weiyi+1+2, rownum, Arith.div(sum, (workDayslist.get(mk)*8)),formatNum);
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
				for (int mk = 0; mk < listManHourDtoTotal.size(); mk++) {
					double sumKaifa=0.0;
					for (int i = 0; i < listDepartProjectsCT.size(); i++) {
						for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
							for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
								if (("开发").equals(listProjectTasks.get(j2).getMemo())) {
									if (listProjectTasks.get(j2).getCategory().equals(listManHourDtoTotal.get(mk).get(j).getCategory())) {
										//if (listProjectTasks.get(j2).getTask().equals(listManHourDtoTotal.get(mk).get(j).getTask())) {
										if (listProjectTasks.get(j2).getTaskID().equals(listManHourDtoTotal.get(mk).get(j).getTaskID())) {
											if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
												sumKaifa = Arith.add(sumKaifa,listManHourDtoTotal.get(mk).get(j).getTimes());
											}
										}
									}
								}
							}
						}
					}
					Number labelnLabel_d3wabm=new Number(weiyi, rownum, sumKaifa,formatNum);
					sheetFour.addCell(labelnLabel_d3wabm); 
					Number labelfr=new Number(weiyi+2, rownum, Arith.div(sumKaifa, (workDayslist.get(mk)*8)),formatNum);
					sheetFour.addCell(labelfr);
					double sumFudai=0.0;
					for (int i = 0; i < listDepartProjectsCT.size(); i++) {
						for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
							for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
								if (("附带").equals(listProjectTasks.get(j2).getMemo())) {
									if (listProjectTasks.get(j2).getCategory().equals(listManHourDtoTotal.get(mk).get(j).getCategory())) {
										//if (listProjectTasks.get(j2).getTask().equals(listManHourDtoTotal.get(mk).get(j).getTask())) {
										if (listProjectTasks.get(j2).getTaskID().equals(listManHourDtoTotal.get(mk).get(j).getTaskID())) {
											if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
												sumFudai = Arith.add(sumFudai,listManHourDtoTotal.get(mk).get(j).getTimes());
											}
										}
									}
								}
							}
						}
					}
					if (Parma.strProjectClientNameDevelopPage[m].equals("DCOE")) sumFudai+=listsum10.get(mk);
					if (Parma.strProjectClientNameDevelopPage[m].equals("CT")) sumFudai+=listsum9.get(mk);
					Number labelnLabelfudaiabm=new Number(weiyi, rownum+1, (double)sumFudai,formatNum);
					sheetFour.addCell(labelnLabelfudaiabm); 
					Number labeled=new Number(weiyi+2, rownum+1, Arith.div(sumFudai, (workDayslist.get(mk)*8)),formatNum);
					sheetFour.addCell(labeled);
					double sumtotal=0.0;
					for (int i = 0; i < listDepartProjectsCT.size(); i++) {
						for (int j = 0; j < listManHourDtoTotal.get(mk).size(); j++) {
							if (listDepartProjectsCT.get(i).getProjectID()==listManHourDtoTotal.get(mk).get(j).getProjectID()) {
								sumtotal = Arith.add(sumtotal,listManHourDtoTotal.get(mk).get(j).getTimes());
							}
		                }
					}
					sheetFour.mergeCells(weiyi+1, rownum, weiyi+1, rownum+1);
					Number labelnLabfk=new Number(weiyi+1, rownum,sumtotal,formatNum);
					sheetFour.addCell(labelnLabfk);
					sheetFour.mergeCells(weiyi+1+2, rownum, weiyi+1+2, rownum+1);
					Number labelfrt=new Number(weiyi+1+2, rownum, Arith.div(sumtotal, (workDayslist.get(mk)*8)),formatNum);
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
				for (int k = 1; k <=(Parma.strmonth.length-2)*2+Parma.strProjMonthTitle.length-2; k++) {
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
	File temp = new File(Parma.TEMP_FILEFirstAddress); 
	if (!temp.isFile()) {
		String[] fileList = temp.list();
		for (String string : fileList) {
			File delDile = new File(Parma.TEMP_FILEFirstAddress+"/"+string);
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
