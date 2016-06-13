/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数导出第一页
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.clarion.worksys.entity.AssumeDetail;
import com.clarion.worksys.entity.ManHourDto;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.util.Arith;

/**
 * @author huangjun
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PersonFirstPage_ForCt {
/**
 * 生成个人集计第一页信息（全工数）_ForCt
 * @param user   出力者
 * @param department   部门名
 * @param branch   课别名
 * @param year   起始年份
 * @param month   起始月份
 * @param endyear  截止年份
 * @param endmonth    截止月份
 * @param uniqueString   唯一性字符串
 * @param allMonthListManHourDtoList   所有工数信息
 * @param listProject   所有项目的集合
 * @param listProjectTasks   所有作业类型的集合
 * @param workDayslist  每个月上班天数的集合
 * @param allAssumePJDetailList    所有担当的PJ的详细
 * 
 */
	public void personWorkNumFirstPage_ForCt(String user,String department,String branch, int year,int month,int endyear, int endmonth,String uniqueString,
			List<List<ManHourDto>> allMonthListManHourDtoList,List<Project> listProject,List<Project_task> listProjectTasks,
			List<Integer> workDayslist,List<List<AssumeDetail>> allAssumePJDetailList) {
		try {
			File file = new File(Parma.TEMP_FILESecondAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			String strnameString2=Parma.TEMP_FILESecondAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			WritableWorkbook book =	Workbook.createWorkbook(new File(strnameString2));
			WritableSheet sheet = book.createSheet("全工数", 0);
			sheet.setColumnView(0,3);
			sheet.setColumnView(1,25);
			for (int i =2; i <= 20; i++) {
				sheet.setColumnView(i,10);
			}
			
			WritableFont fontgr_title1= new WritableFont(WritableFont.TIMES,14,WritableFont.BOLD);
			WritableFont fontgr_title2= new WritableFont(WritableFont.TIMES,9,WritableFont.NO_BOLD);
			WritableFont fontgr= new WritableFont(WritableFont.TIMES,9,WritableFont.BOLD); //黑色字体

			WritableCellFormat formato_title1=new WritableCellFormat(fontgr_title1); 
			formato_title1.setAlignment(jxl.format.Alignment.LEFT); 

			WritableCellFormat formato_title2=new WritableCellFormat(fontgr_title2); 
			formato_title2.setAlignment(jxl.format.Alignment.LEFT); 
			
			WritableCellFormat formato1=new WritableCellFormat(fontgr); 
			formato1.setAlignment(jxl.format.Alignment.LEFT); 
			formato1.setBackground(Colour.LIGHT_TURQUOISE);
			formato1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

			WritableCellFormat formato2=new WritableCellFormat(fontgr); 
			formato2.setAlignment(jxl.format.Alignment.LEFT); 
			formato2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

			WritableCellFormat formato3=new WritableCellFormat(fontgr); 
			formato3.setAlignment(jxl.format.Alignment.CENTRE); 
			formato3.setBackground(Colour.LIGHT_TURQUOISE);
			formato3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

			WritableCellFormat formato4=new WritableCellFormat(fontgr); 
			formato4.setAlignment(jxl.format.Alignment.CENTRE); 
			formato4.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

			WritableCellFormat formatoNum1=new WritableCellFormat(fontgr);
			formatoNum1.setAlignment(jxl.format.Alignment.RIGHT); 
			formatoNum1.setBackground(Colour.LIGHT_TURQUOISE);
			formatoNum1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

			WritableCellFormat formatoNum2=new WritableCellFormat(fontgr);
			formatoNum2.setAlignment(jxl.format.Alignment.RIGHT);
			formatoNum2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			int weiyi=0;//计算数据插入偏移数
			if (month>=4&&month<=12) {
				weiyi=(month-4)+2;
			}
			if (month>=1&&month<=3) {
				weiyi=month+10;
			}
			
		    Label label0_0 = new Label(1, 1, department + "/" + branch + " " + year + "年" + month + "月～" + endyear + "年" + endmonth + "月" + "工数実績データ",formato_title1);
			Label label0_1 = new Label(10, 1, "全工数",formato_title1);
			Label label0_2 = new Label(1, 3,"出力者：" + user,formato_title2);
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar calendar=Calendar.getInstance();//获得系统当前日期        
	    	Date date = calendar.getTime();
			Label label0_3 = new Label(2, 3,"出力日：" + df.format(date),formato_title2);
			sheet.addCell(label0_0);
			sheet.addCell(label0_1);
			sheet.addCell(label0_2);
			sheet.addCell(label0_3);
			
			/**
			 * 基礎情報
			 */
			Label label0_4 = new Label(1, 4, "基礎情報",formato2);
			sheet.addCell(label0_4);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label = new Label(i+2, 4, Parma.strmonth_ForCt[i],formato4);
				sheet.addCell(label);
			}
			for(int i=1;i<=Parma.strHomeSegement_ForCt.length;i++)
			{
				Label label = new Label(1, i+4, Parma.strHomeSegement_ForCt[i-1],formato1);
				sheet.addCell(label);
			}
			int weiyiTime=weiyi;
			int rowNumTime=5;
			for (int i = 0; i < workDayslist.size(); i++) {
				Number label2=new Number(weiyiTime,rowNumTime,workDayslist.get(i)*8,formatoNum1);
				sheet.addCell(label2);
				Number labelvtg=new Number(weiyiTime,rowNumTime+1,workDayslist.get(i),formatoNum1);
				sheet.addCell(labelvtg);
				Number labeldef=new Number(weiyiTime,rowNumTime+2,1,formatoNum1);
				sheet.addCell(labeldef);
				Number labelhyt=new Number(weiyiTime,rowNumTime+3,workDayslist.get(i)*8,formatoNum1);
				sheet.addCell(labelhyt);
				weiyiTime+=1;
			}

			/**
			 * PJ情報
			 */
			Label label0_5 = new Label(1, 11, "PJ情報",formato2);
			sheet.addCell(label0_5);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label = new Label(i+2, 11, Parma.strmonth_ForCt[i],formato4);
				sheet.addCell(label);
			}
			Label label0_6 = new Label(1, 12, "工数投入PJ数",formato1);
			sheet.addCell(label0_6);
			int weiyiPJDetail=weiyi;
			int rowNumPJ=12;
			for (int i = 0; i < allAssumePJDetailList.size(); i++) {
				for (int m = 0; m < allAssumePJDetailList.get(i).size(); m++) {
					Number label2=new Number(weiyiPJDetail,rowNumPJ,allAssumePJDetailList.get(i).get(m).getAssumePJNum(),formatoNum1);
					sheet.addCell(label2);
				}
				weiyiPJDetail+=1;
			}

			/**
			 * 開発段階
			 */
			
			Label label0_7 = new Label(1, 15, "開発段階",formato2);
			sheet.addCell(label0_7);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label = new Label(i+2, 15, Parma.strmonth_ForCt[i],formato4);
				sheet.addCell(label);
			}
			for(int i=1;i<=Parma.strCategoryName_ForCt.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(1, i+15, Parma.strCategoryName_ForCt[i-1],formato1);
					sheet.addCell(label);
				} else {
					Label label = new Label(1, i+15, Parma.strCategoryName_ForCt[i-1],formato2);
					sheet.addCell(label);
				}
			}
			int rowNumStep=16;
			for (int i = 0; i < Parma.strTotalWorkNumName_ForCt.length; i++) {
				int weiyiStep=weiyi;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double SumTemp1=0.0;
					double SumTemp2=0.0;
					double SumTemp3=0.0;
					double SumTemp4=0.0;
					double SumTemp_TatolD=0.0;
					double SumTemp_TatolM=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if ((Parma.strTotalWorkNumName_ForCt[i]).equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
							for (int j2 = 0; j2 < listProject.size(); j2++) {
								if (allMonthListManHourDtoList.get(m).get(j).getProjectName() != null && allMonthListManHourDtoList.get(m).get(j).getProjectName().toString().equals(listProject.get(j2).getProjectName())) {
									if ((Parma.strTotalWorkNumName_ForCt[i]).equals(listProject.get(j2).getCategory())) {
										if (i == 0) {
											SumTemp1 = Arith.add(SumTemp1,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 1) {
											SumTemp2 = Arith.add(SumTemp2,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 2) {
											SumTemp3 = Arith.add(SumTemp3,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 3) {
											SumTemp4 = Arith.add(SumTemp4,allMonthListManHourDtoList.get(m).get(j).getTimes());
										}
									}
								}
							}
						}
					}
					if (i == 0) {
						Number label_D=new Number(weiyiStep, rowNumStep + (i * 2),SumTemp1,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowNumStep + (i * 2) + 1,Arith.div(SumTemp1, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 1) {
						Number label_D=new Number(weiyiStep, rowNumStep + (i * 2),SumTemp2,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowNumStep + (i * 2) + 1,Arith.div(SumTemp2, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 2) {
						Number label_D=new Number(weiyiStep, rowNumStep + (i * 2),SumTemp3,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowNumStep + (i * 2) + 1,Arith.div(SumTemp3, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 3) {
						Number label_D=new Number(weiyiStep, rowNumStep + (i * 2),SumTemp4,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowNumStep + (i * 2) + 1,Arith.div(SumTemp4, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					}
					SumTemp_TatolD = Arith.add(SumTemp4, Arith.add(SumTemp3, Arith.add(SumTemp2,SumTemp1)));
					SumTemp_TatolM = Arith.add(Arith.div(SumTemp4, (workDayslist.get(m)*8))
												,Arith.add(Arith.div(SumTemp3, (workDayslist.get(m)*8))
												,Arith.add(Arith.div(SumTemp2, (workDayslist.get(m)*8))
												,Arith.div(SumTemp1, (workDayslist.get(m)*8)))));
					Cell cellTempD = sheet.getCell(weiyiStep, rowNumStep + (Parma.strTotalWorkNumName_ForCt.length * 2));
					if (!cellTempD.getContents().isEmpty()) {
						SumTemp_TatolD += new Double(cellTempD.getContents());
					}
					Number labelTol_D=new Number(weiyiStep, rowNumStep + (Parma.strTotalWorkNumName_ForCt.length * 2),SumTemp_TatolD,formatoNum1);
					sheet.addCell(labelTol_D);
					Cell cellTempM = sheet.getCell(weiyiStep, rowNumStep + (Parma.strTotalWorkNumName_ForCt.length * 2) + 1);
					if (!cellTempM.getContents().isEmpty()) {
						SumTemp_TatolM += new Double(cellTempM.getContents());
					}
					Number labelTol_M=new Number(weiyiStep, rowNumStep + (Parma.strTotalWorkNumName_ForCt.length * 2) + 1,SumTemp_TatolM,formatoNum2);
					sheet.addCell(labelTol_M);
					weiyiStep += 1;
				}
			}
			
			/**
			 * コンポーネント分類
			 */
			Label label0_8 = new Label(1, 28, "コンポーネント分類",formato2);
			sheet.addCell(label0_8);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label = new Label(i+2, 28, Parma.strmonth_ForCt[i],formato4);
				sheet.addCell(label);
			}
			for(int i=1;i<=Parma.strComponentName_ForCt.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(1, i+28, Parma.strComponentName_ForCt[i-1],formato1);
					sheet.addCell(label);
				} else {
					Label label = new Label(1, i+28, Parma.strComponentName_ForCt[i-1],formato2);
					sheet.addCell(label);
				}
			}
			int rowComStep=29;
			int weiyiStepCom=weiyi;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double SumTemp1=0.0;
				double SumTemp2=0.0;
				double SumTemp3=0.0;
				double SumTemp_TatolD=0.0;
				double SumTemp_TatolM=0.0;
				for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
					if ("2".equals(allMonthListManHourDtoList.get(m).get(j).getComponentSortID())) {
						SumTemp1 = Arith.add(SumTemp1,allMonthListManHourDtoList.get(m).get(j).getTimes());
					} else if ("3".equals(allMonthListManHourDtoList.get(m).get(j).getComponentSortID())) {
						SumTemp2 = Arith.add(SumTemp2,allMonthListManHourDtoList.get(m).get(j).getTimes());							
					} else {
						SumTemp3 = Arith.add(SumTemp3,allMonthListManHourDtoList.get(m).get(j).getTimes());							
					}
				}
				Number label_D1=new Number(weiyiStepCom, rowComStep,SumTemp1,formatoNum1);
				sheet.addCell(label_D1);
				Number label_M1=new Number(weiyiStepCom, rowComStep + 1,Arith.div(SumTemp1, (workDayslist.get(m)*8)),formatoNum2);
				sheet.addCell(label_M1);
				Number label_D2=new Number(weiyiStepCom, rowComStep + 2,SumTemp2,formatoNum1);
				sheet.addCell(label_D2);
				Number label_M2=new Number(weiyiStepCom, rowComStep + 3,Arith.div(SumTemp2, (workDayslist.get(m)*8)),formatoNum2);
				sheet.addCell(label_M2);
				SumTemp_TatolD = Arith.add(SumTemp3, Arith.add(SumTemp2,SumTemp1));
				SumTemp_TatolM = Arith.add(Arith.div(SumTemp3, (workDayslist.get(m)*8))
											,Arith.add(Arith.div(SumTemp2, (workDayslist.get(m)*8))
											,Arith.div(SumTemp1, (workDayslist.get(m)*8))));
				Number labelTol_D=new Number(weiyiStepCom, rowComStep + 4,SumTemp_TatolD,formatoNum1);
				sheet.addCell(labelTol_D);
				Number labelTol_M=new Number(weiyiStepCom, rowComStep + 5,SumTemp_TatolM,formatoNum2);
				sheet.addCell(labelTol_M);
				weiyiStepCom += 1;
			}

			/**
			 * 事業セグメント
			 */
			Label label0_9 = new Label(1, 37, "事業セグメント",formato2);
			sheet.addCell(label0_9);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label = new Label(i+2, 37, Parma.strmonth_ForCt[i],formato4);
				sheet.addCell(label);
			}
			for(int i=1;i<=Parma.strWorkSegement_ForCt.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(1, i+37, Parma.strWorkSegement_ForCt[i-1],formato1);
					sheet.addCell(label);
				} else {
					Label label = new Label(1, i+37, Parma.strWorkSegement_ForCt[i-1],formato2);
					sheet.addCell(label);
				}
			}
			int rowSegStep=38;
			for (int i = 0; i < Parma.strTotalWorkSegName_ForCt.length; i++) {
				int weiyiStep=weiyi;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double SumTemp1=0.0;
					double SumTemp2=0.0;
					double SumTemp3=0.0;
					double SumTemp4=0.0;
					double SumTemp5=0.0;
					double SumTemp6=0.0;
					double SumTemp_TatolD=0.0;
					double SumTemp_TatolM=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if ((Parma.strTotalWorkSegName_ForCt[i]).equals(allMonthListManHourDtoList.get(m).get(j).getEnterpriseSeg())) {
							for (int j2 = 0; j2 < listProject.size(); j2++) {
								if (allMonthListManHourDtoList.get(m).get(j).getProjectName() != null && allMonthListManHourDtoList.get(m).get(j).getProjectName().equals(listProject.get(j2).getProjectName())) {
									if ((Parma.strTotalWorkSegName_ForCt[i]).equals(listProject.get(j2).getEnterpriseSeg())) {
										if (i == 0) {
											SumTemp1 = Arith.add(SumTemp1,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 1) {
											SumTemp2 = Arith.add(SumTemp2,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 2) {
											SumTemp3 = Arith.add(SumTemp3,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 3) {
											SumTemp4 = Arith.add(SumTemp4,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 4) {
											SumTemp5 = Arith.add(SumTemp5,allMonthListManHourDtoList.get(m).get(j).getTimes());
										} else if (i == 5) {
											SumTemp6 = Arith.add(SumTemp6,allMonthListManHourDtoList.get(m).get(j).getTimes());
										}
									}
								}
							}
						}
					}
					if (i == 0) {
						Number label_D=new Number(weiyiStep, rowSegStep + (i * 2),SumTemp1,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowSegStep + (i * 2) + 1,Arith.div(SumTemp1, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 1) {
						Number label_D=new Number(weiyiStep, rowSegStep + (i * 2),SumTemp2,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowSegStep + (i * 2) + 1,Arith.div(SumTemp2, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 2) {
						Number label_D=new Number(weiyiStep, rowSegStep + (i * 2),SumTemp3,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowSegStep + (i * 2) + 1,Arith.div(SumTemp3, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 3) {
						Number label_D=new Number(weiyiStep, rowSegStep + (i * 2),SumTemp4,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowSegStep + (i * 2) + 1,Arith.div(SumTemp4, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 4) {
						Number label_D=new Number(weiyiStep, rowSegStep + (i * 2),SumTemp5,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowSegStep + (i * 2) + 1,Arith.div(SumTemp5, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					} else if (i == 5) {
						Number label_D=new Number(weiyiStep, rowSegStep + (i * 2),SumTemp6,formatoNum1);
						sheet.addCell(label_D);
						Number label_M=new Number(weiyiStep, rowSegStep + (i * 2) + 1,Arith.div(SumTemp6, (workDayslist.get(m)*8)),formatoNum2);
						sheet.addCell(label_M);
					}
					SumTemp_TatolD = Arith.add(SumTemp6, Arith.add(SumTemp5, Arith.add(SumTemp4, Arith.add(SumTemp3, Arith.add(SumTemp2,SumTemp1)))));
					SumTemp_TatolM = Arith.add(Arith.div(SumTemp6, (workDayslist.get(m)*8))
												,Arith.add(Arith.div(SumTemp5, (workDayslist.get(m)*8))
												,Arith.add(Arith.div(SumTemp4, (workDayslist.get(m)*8))
												,Arith.add(Arith.div(SumTemp3, (workDayslist.get(m)*8))
												,Arith.add(Arith.div(SumTemp2, (workDayslist.get(m)*8))
												,Arith.div(SumTemp1, (workDayslist.get(m)*8)))))));
					Cell cellTempD = sheet.getCell(weiyiStep, rowSegStep + (Parma.strTotalWorkSegName_ForCt.length * 2));
					if (!cellTempD.getContents().isEmpty()) {
						SumTemp_TatolD += new Double(cellTempD.getContents());
					}
					Number labelTol_D=new Number(weiyiStep, rowSegStep + (Parma.strTotalWorkSegName_ForCt.length * 2),SumTemp_TatolD,formatoNum1);
					sheet.addCell(labelTol_D);
					Cell cellTempM = sheet.getCell(weiyiStep, rowSegStep + (Parma.strTotalWorkSegName_ForCt.length * 2) + 1);
					if (!cellTempM.getContents().isEmpty()) {
						SumTemp_TatolM += new Double(cellTempM.getContents());
					}
					Number labelTol_M=new Number(weiyiStep, rowSegStep + (Parma.strTotalWorkSegName_ForCt.length * 2) + 1,SumTemp_TatolM,formatoNum2);
					sheet.addCell(labelTol_M);
					weiyiStep += 1;
				}
			}

			/**
			 * 对剩余的空白部分进行单元格处理
			 */ 
			for (int j = 5; j < 52; j++) {
				Cell celletemp = sheet.getCell(1, j);
				String resultemp = celletemp.getContents();
				if (!resultemp.isEmpty()) {
					for (int k = 1; k <=Parma.strmonth_ForCt.length+1; k++) {
						Cell celletemp1 = sheet.getCell(k, j);
						String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							if (j == 5 
							    || j == 6
							    || j == 7
							    || j == 8
							    || j == 12
							    || j == 16
							    || j == 18
							    || j == 20
							    || j == 22
							    || j == 24
							    || j == 29
							    || j == 31
							    || j == 33
							    || j == 38
							    || j == 40
							    || j == 42
							    || j == 44
							    || j == 46
							    || j == 48
							    || j == 50
							    ) {
								Label labelhLabel = new Label(k, j, null ,formato1);
								sheet.addCell(labelhLabel);
							} else {
								Label labelhLabel = new Label(k, j, null ,formato2);
								sheet.addCell(labelhLabel);
							}
						}
					}	
				}
			}

			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean deleteTempDir() {
		File temp = new File(Parma.TEMP_FILESecondAddress); 
		if (!temp.isFile()) {
			String[] fileList = temp.list();
			for (String string : fileList) {
				File delDile = new File(Parma.TEMP_FILESecondAddress+"/"+string);
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
