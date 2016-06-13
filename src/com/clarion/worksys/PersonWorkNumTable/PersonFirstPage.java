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

import com.clarion.worksys.entity.AssumeDetail;
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
public class PersonFirstPage {
/**
 * 生成个人集计首页的信息
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
 * @param allListCategoryDetail   所有category的详细数据
 * @param workDayslist  每个月上班天数的集合
 * @param allAssumePJDetailList    所有担当的PJ的详细
 * 
 */
	public void personWorkNumFirstPage(String department,String branch, int year,int month,int endyear, int endmonth,String uniqueString,
			List<List<ManHourDto>> allMonthListManHourDtoList,List<Project> listProject,List<Project_task> listProjectTasks,
			List<List<Double>>  allListCategoryDetail,List<Integer> workDayslist,List<List<AssumeDetail>> allAssumePJDetailList) {
		try {
			File file = new File(Parma.TEMP_FILESecondAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			String strnameString2=Parma.TEMP_FILESecondAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			WritableWorkbook book =	Workbook.createWorkbook(new File(strnameString2));
			WritableSheet sheet = book.createSheet("首页", 0);
			sheet.setColumnView(0,25);
			for (int i =1; i <= 27; i++) {
				sheet.setColumnView(i,13);
			}
			double personMonthTotal=0.0f;
			for (int i = 0; i < workDayslist.size(); i++) {
				personMonthTotal+=workDayslist.get(i)*8;
			}
			
			Calendar calendar=Calendar.getInstance();//获得系统当前日期        
			int yearsys=calendar.get(Calendar.YEAR);        
			int monthsys=calendar.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=calendar.get(Calendar.DAY_OF_MONTH);
			
			WritableFont fontgr= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD); //黑色字体
			WritableCellFormat formatgr=new WritableCellFormat(fontgr); 
			formatgr.setAlignment(jxl.format.Alignment.CENTRE); 
			formatgr.setBackground(Colour.SKY_BLUE);
			formatgr.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatgrNum=new WritableCellFormat(fontgr); 
			formatgrNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatgrNum.setBackground(Colour.SKY_BLUE);
			formatgrNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formato=new WritableCellFormat(fontgr); 
			formato.setAlignment(jxl.format.Alignment.CENTRE); 
			formato.setBackground(Colour.LIGHT_TURQUOISE);
			formato.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatoNum=new WritableCellFormat(fontgr);
			formatoNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatoNum.setBackground(Colour.LIGHT_TURQUOISE);
			formatoNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			int weiyi=0;//计算数据插入偏移数
			if (month>=4&&month<=12) {
				weiyi=(month-4)*2+1;
			}
			if (month>=1&&month<=3) {
				weiyi=month*2+17;
			}
			
		    Label label0_0 = new Label(0, 0, "集计数据",formato);
			Label label0_1 = new Label(1, 0, "集计日",formato);
			Label label0_1hy = new Label(2, 0,String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formato);
			sheet.addCell(label0_0);
			sheet.addCell(label0_1);
			sheet.addCell(label0_1hy);
			
			if (branch==null) {
				Label label0_4 = new Label(0, 2, department,formato);
				sheet.addCell(label0_4);
			}
			else {
				Label label0_4 = new Label(0, 2, department+"/"+branch,formato);
				sheet.addCell(label0_4);
			}
			/**
			 * 担当PJ详细
			 */
			for(int i=1;i<=Parma.strAssumePerMonth.length;i++)
			{
				Label label = new Label(i, 2, Parma.strAssumePerMonth[i-1],formato);
				sheet.addCell(label);
			}
			for(int i=0;i<Parma.strProjectClientName.length;i++)
			{
				Label label = new Label(0, i+3, Parma.strProjectClientName[i],formato);
				sheet.addCell(label);
			}
			int weiyiPJDetail=weiyi;
			int rowNumPJ=3;
			for (int i = 0; i < allAssumePJDetailList.size(); i++) {
				for (int m = 0; m < allAssumePJDetailList.get(i).size(); m++) {
					Number label2=new Number(weiyiPJDetail,rowNumPJ+m,allAssumePJDetailList.get(i).get(m).getAssumePJNum(),formatoNum);
					sheet.addCell(label2);
					String tempString="";
					for (int k = 0; k < allAssumePJDetailList.get(i).get(m).getAssumePJName().size(); k++) {
						tempString+=allAssumePJDetailList.get(i).get(m).getAssumePJName().get(k);
					}
					Label label = new Label(weiyiPJDetail+1, rowNumPJ+m, tempString,formato);
					sheet.addCell(label);
				}
				weiyiPJDetail+=2;
			}
			/**
			 * 稼働時間
			 */
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, 7, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, 7, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			for(int i=4;i<Parma.strHomeSegement.length;i++)
			{
				Label label = new Label(0, i+4, Parma.strHomeSegement[i],formato);
				sheet.addCell(label);
			}
			int weiyiTime=weiyi;
			int rowNumTime=8;
			for (int i = 0; i < workDayslist.size(); i++) {
				Number label2=new Number(weiyiTime,rowNumTime,workDayslist.get(i)*8,formatoNum);
				sheet.addCell(label2);
				Number labelvtg=new Number(weiyiTime,rowNumTime+1,workDayslist.get(i),formatoNum);
				sheet.addCell(labelvtg);
				Number labeldef=new Number(weiyiTime,rowNumTime+2,1,formatoNum);
				sheet.addCell(labeldef);
				Number labelhyt=new Number(weiyiTime,rowNumTime+3,workDayslist.get(i)*8,formatoNum);
				sheet.addCell(labelhyt);
				weiyiTime+=2;
			}
			/**
			 * 全工数状況
			 */
			
			double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
			List<Double> listsum9=new ArrayList<Double>();
			List<Double> listsum10=new ArrayList<Double>();
			//if (("工程设计部").equals(department)) {
			if (DepartmentEnum.GCSJB.getName().equals(department)) {
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					double sum9=0.0;
					for (int i = 0; i < allMonthListManHourDtoList.get(j).size(); i++) {
						for (int j2 = 0; j2 < Parma.strAdditionalProjDesignerCT.length; j2++) {
							if (Parma.strAdditionalProjDesignerCT[j2].equals(allMonthListManHourDtoList.get(j).get(i).getTask())) {
								if (286==allMonthListManHourDtoList.get(j).get(i).getProjectID()) {
									sum9 = Arith.add(sum9,allMonthListManHourDtoList.get(j).get(i).getTimes());
								}
							}
						}
					}	
					listsum9.add(sum9);
				}
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					double sum10=0.0;
					for (int i = 0; i < allMonthListManHourDtoList.get(j).size(); i++) {
						for (int j2 = 0; j2 < Parma.strAdditionalProjDesignerDCOE.length; j2++) {
							if (Parma.strAdditionalProjDesignerDCOE[j2].equals(allMonthListManHourDtoList.get(j).get(i).getTask())) {
								if (304==allMonthListManHourDtoList.get(j).get(i).getProjectID()) {
									sum10 = Arith.add(sum10,allMonthListManHourDtoList.get(j).get(i).getTimes());
								}
							}
						}
					}
					listsum10.add(sum10);
				}
			}
			int rowStartnum=14;
			double sumtotal1=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					sumtotal1+=allMonthListManHourDtoList.get(m).get(i).getTimes();
				}
			}
			Label labelqwLabel= new Label(0, rowStartnum, Parma.strHomeTitle[0],formato);
			sheet.addCell(labelqwLabel);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			for (int i = 0; i < 2; i++) {
				int weiyi1=weiyi;
				double SumTempFirst=0.0;
				Label label = new Label(0, rowStartnum, Parma.strTotalWorkNumName[i],formato);
				sheet.addCell(label);
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double SumTemp=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (allMonthListManHourDtoList.get(m).get(j).getCategory().equals(Parma.strTotalWorkNumName[i])) {
							for (int j2 = 0; j2 < listProject.size(); j2++) {
								if (allMonthListManHourDtoList.get(m).get(j).getProjectName().equals(listProject.get(j2).getProjectName())) {
										if ((Parma.strTotalWorkNumName[i]).equals(listProject.get(j2).getCategory())) {
											SumTemp = Arith.add(SumTemp,allMonthListManHourDtoList.get(m).get(j).getTimes());
										}
								}
							}
						}
					}
					Number label2=new Number(weiyi1, rowStartnum,SumTemp,formatoNum);
					sheet.addCell(label2);
					Number labelgy=new Number(weiyi1+1, rowStartnum,Arith.div(SumTemp, (workDayslist.get(m)*8)),formatoNum);
					sheet.addCell(labelgy);
					SumTempFirst+=SumTemp;
					sumtempTol[m]+=SumTemp;
					weiyi1+=2;
				}
				Number labelnLabeld=new Number(25, rowStartnum, SumTempFirst,formatoNum);
				sheet.addCell(labelnLabeld);
				Number labelgy=new Number(26, rowStartnum,Arith.div(SumTempFirst,personMonthTotal),formatoNum);
				sheet.addCell(labelgy);
				Number labelef=new Number(27, rowStartnum,Arith.div(SumTempFirst, sumtotal1, 4)*100,formatoNum);
				sheet.addCell(labelef);
				rowStartnum++;
			}
			Label labelq12w= new Label(0, rowStartnum, Parma.strTotalWorkNumName[2],formato);
			sheet.addCell(labelq12w);
			int weiyi2=weiyi;
			double SumTempFirst1=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double sum61=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
						if ((Parma.strDevelopWorkNum[0]).equals(listProjectTasks.get(j2).getMemo())) {
							//if (allMonthListManHourDtoList.get(m).get(i).getTask().equals(listProjectTasks.get(j2).getTask())) {
							if (allMonthListManHourDtoList.get(m).get(i).getTaskID() == listProjectTasks.get(j2).getTaskID()) {
								if (allMonthListManHourDtoList.get(m).get(i).getCategory().equals(listProjectTasks.get(j2).getCategory())) {
									sum61 = Arith.add(sum61,allMonthListManHourDtoList.get(m).get(i).getTimes());
								}
							}
						}
					}
			}
			Number labelrw=new Number(weiyi2, rowStartnum,sum61,formatoNum);
			sheet.addCell(labelrw);
			Number labelgy=new Number(weiyi2+1, rowStartnum,Arith.div(sum61, (workDayslist.get(m)*8)),formatoNum);
			sheet.addCell(labelgy);
			SumTempFirst1+=sum61;
			sumtempTol[m]+=sum61;
			weiyi2+=2;
			}
			Number labelnLabeld1w=new Number(25, rowStartnum,SumTempFirst1,formatoNum);
			sheet.addCell(labelnLabeld1w);
			Number labelgy=new Number(26, rowStartnum,Arith.div(SumTempFirst1,personMonthTotal),formatoNum);
			sheet.addCell(labelgy);
			Number labelef=new Number(27, rowStartnum,Arith.div(SumTempFirst1, sumtotal1, 4)*100,formatoNum);
			sheet.addCell(labelef);
			rowStartnum++;
			Label labelq2w= new Label(0, rowStartnum, Parma.strTotalWorkNumName[3],formato);
			sheet.addCell(labelq2w);
			int weiyi4=weiyi;
			double SumTempFirst4=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double sum71=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					for (int j2 = 0; j2 < listProjectTasks.size(); j2++) {
						if ((Parma.strDevelopWorkNum[1]).equals(listProjectTasks.get(j2).getMemo())) {
							//if (allMonthListManHourDtoList.get(m).get(i).getTask().equals(listProjectTasks.get(j2).getTask())) {
							if (allMonthListManHourDtoList.get(m).get(i).getTaskID() == listProjectTasks.get(j2).getTaskID()) {
								if (allMonthListManHourDtoList.get(m).get(i).getCategory().equals(listProjectTasks.get(j2).getCategory())) {
									sum71 = Arith.add(sum71,allMonthListManHourDtoList.get(m).get(i).getTimes());
								}
							}
						}
					}
				}
				//if (("工程设计部").equals(department))sum71+=listsum9.get(m)+listsum10.get(m);
				if (DepartmentEnum.GCSJB.getName().equals(department))sum71+=listsum9.get(m)+listsum10.get(m);
				Number labelr1w=new Number(weiyi4, rowStartnum,sum71,formatoNum);
				sheet.addCell(labelr1w);
				Number labeldr=new Number(weiyi4+1, rowStartnum,Arith.div(sum71, (workDayslist.get(m)*8)),formatoNum);
				sheet.addCell(labeldr);
				SumTempFirst4+=sum71;
				sumtempTol[m]+=sum71;
				weiyi4+=2;
			}
				Number labelnLabeld2w=new Number(25, rowStartnum,SumTempFirst4,formatoNum);
				sheet.addCell(labelnLabeld2w);
				Number labeldr=new Number(26, rowStartnum,Arith.div(SumTempFirst4,personMonthTotal),formatoNum);
				sheet.addCell(labeldr);
				Number labelet=new Number(27, rowStartnum,Arith.div(SumTempFirst4, sumtotal1, 4)*100,formatoNum);
				sheet.addCell(labelet);
				rowStartnum++;

			Label labelabg = new Label(0, rowStartnum, Parma.strTotalWorkNumName[4],formato);
			sheet.addCell(labelabg);
			int weiyi3=weiyi;
			double SumTempFirst2=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double SumTempfg=0.0;
				for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
					if (allMonthListManHourDtoList.get(m).get(j).getCategory().equals(Parma.strTotalWorkNumName[4])) {
						for (int j2 = 0; j2 < listProject.size(); j2++) {
							if (allMonthListManHourDtoList.get(m).get(j).getProjectName().equals(listProject.get(j2).getProjectName())) {
									if ((Parma.strTotalWorkNumName[4]).equals(listProject.get(j2).getCategory())) {
										SumTempfg = Arith.add(SumTempfg,allMonthListManHourDtoList.get(m).get(j).getTimes());
									}
							}
						}
					}
				}
				SumTempFirst2+=SumTempfg;
				Number label2abg=new Number(weiyi3, rowStartnum,SumTempfg,formatoNum);
				sheet.addCell(label2abg);
				Number labelyh=new Number(weiyi3+1, rowStartnum,Arith.div(SumTempfg, (workDayslist.get(m)*8)),formatoNum);
				sheet.addCell(labelyh);
				sumtempTol[m]+=SumTempfg;
				weiyi3+=2;
			}
			Number labelnLabeld=new Number(25, rowStartnum, SumTempFirst2,formatoNum);
			sheet.addCell(labelnLabeld);
			Number labelyh=new Number(26, rowStartnum,Arith.div(SumTempFirst2,personMonthTotal),formatoNum);
			sheet.addCell(labelyh);
			Number labelerf=new Number(27, rowStartnum,Arith.div(SumTempFirst2, sumtotal1, 4)*100,formatoNum);
			sheet.addCell(labelerf);
			rowStartnum++;
			int weiyi5=weiyi;
			Label labelnLabel_d3wab= new Label(0, rowStartnum, Parma.strHomeTitle[0]+"  合计",formatgr);
			sheet.addCell(labelnLabel_d3wab); 
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				Number labelnLabel_d3wabm=new Number(weiyi5, rowStartnum,sumtempTol[m],formatgrNum);
				sheet.addCell(labelnLabel_d3wabm); 
				Number labeltg=new Number(weiyi5+1, rowStartnum,Arith.div(sumtempTol[m], (workDayslist.get(m)*8)),formatgrNum);
				sheet.addCell(labeltg);
				weiyi5+=2;
			}
			Number labelnLabel_d3wa=new Number(25, rowStartnum, sumtotal1,formatgrNum);
			sheet.addCell(labelnLabel_d3wa);  
			Number labeltg=new Number(26, rowStartnum,Arith.div(sumtotal1,personMonthTotal),formatgrNum);
			sheet.addCell(labeltg);
			Number labeleed=new Number(27, rowStartnum,Arith.div(sumtotal1, sumtotal1, 4)*100,formatgrNum);
			sheet.addCell(labeleed);
			rowStartnum++;
			
			/**
			 * CT　対応工数計
			 */
			for (int j = 0; j < allListCategoryDetail.size(); j++) {
				for (int i = 0; i < allListCategoryDetail.get(j).size(); i++) {
					if (allListCategoryDetail.get(j).get(i)==null) {
						allListCategoryDetail.get(j).set(i, 0.0);
					}
				}
			}
			rowStartnum+=2;
			List<Project> listProjectCT=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
					if ((Parma.strProjectClientName[0]).equals(listProject.get(i).getProjectClientName())) {
						listProjectCT.add(listProject.get(i));
					}
			}
			double sumtotalCT1=0.0;
			for (int i = 0; i < listProjectCT.size(); i++) {
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						if (listProjectCT.get(i).getProjectID()==allMonthListManHourDtoList.get(j).get(m).getProjectID()) {
							sumtotalCT1 = Arith.add(sumtotalCT1,allMonthListManHourDtoList.get(j).get(m).getTimes());
						}
					}
				}
			}
			Label labelq= new Label(0, rowStartnum, Parma.strHomeTitle[1],formato);
			sheet.addCell(labelq);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			double[] sumtempToltemp=new double[allMonthListManHourDtoList.size()];
			for (int i = 0; i < Parma.strCategoryName.length; i++) {
				Label label = new Label(0, rowStartnum, Parma.strCategoryName[i],formato);
				sheet.addCell(label);
				double sum5temp=0.0;
				int weiyi6=weiyi;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double sum5=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (allMonthListManHourDtoList.get(m).get(j).getCategory().equals(Parma.strCategoryName[i])) {
							for (int j2 = 0; j2 < listProjectCT.size(); j2++) {
								if (allMonthListManHourDtoList.get(m).get(j).getProjectID()==listProjectCT.get(j2).getProjectID()) {
									sum5 = Arith.add(sum5,allMonthListManHourDtoList.get(m).get(j).getTimes());
								}
							}
						}
					}
					Number label2=new Number(weiyi6, rowStartnum, sum5,formatoNum);
					sheet.addCell(label2);
					Number labeltu=new Number(weiyi6+1, rowStartnum,Arith.div(sum5, (workDayslist.get(m)*8)),formatoNum);
					sheet.addCell(labeltu);
					sum5temp+=sum5;
					sumtempToltemp[m]+=sum5;
					weiyi6+=2;
				}
				Number labelnLabeldbg=new Number(25, rowStartnum, sum5temp,formatoNum);
				sheet.addCell(labelnLabeldbg);
				Number labeltu=new Number(26, rowStartnum,Arith.div(sum5temp,personMonthTotal),formatoNum);
				sheet.addCell(labeltu);
				Number labelnLabel_d=new Number(27, rowStartnum,Arith.div(sum5temp, sumtotal1, 4)*100,formatoNum);
				sheet.addCell(labelnLabel_d);
				rowStartnum++;
			}
			int weiyi7=weiyi;
			Label labelnLabel_d6wab= new Label(0, rowStartnum, Parma.strHomeTitle[1]+"　计",formatgr);
			sheet.addCell(labelnLabel_d6wab); 
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				Number labelnLabel_d6wabm=new Number(weiyi7, rowStartnum,sumtempToltemp[i],formatgrNum);
				sheet.addCell(labelnLabel_d6wabm); 
				Number labeltu=new Number(weiyi7+1, rowStartnum,Arith.div(sumtempToltemp[i], workDayslist.get(i)*8),formatgrNum);
				sheet.addCell(labeltu);
				weiyi7+=2;
			}
			Number labelnLabel_d6wa=new Number(25, rowStartnum,sumtotalCT1,formatgrNum);
			sheet.addCell(labelnLabel_d6wa); 
			Number labelgt=new Number(26, rowStartnum,Arith.div(sumtotalCT1,personMonthTotal),formatgrNum);
			sheet.addCell(labelgt);
			Number labelnLabel_d6wac=new Number(27, rowStartnum,Arith.div(sumtotalCT1, sumtotal1, 4)*100,formatgrNum);
			sheet.addCell(labelnLabel_d6wac);
			rowStartnum++;
			/**
			 * CT开发工数类别明细
			 */
			
			double sumtotal4=0.0;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int m = 0; m < allMonthListManHourDtoList.get(i).size(); m++) {
					for (int j = 0; j < listProjectCT.size(); j++) {
						if (allMonthListManHourDtoList.get(i).get(m).getProjectID()==(listProjectCT.get(j).getProjectID())) {
							if (Parma.strCategoryName[2].equals(allMonthListManHourDtoList.get(i).get(m).getCategory())) {
								sumtotal4 = Arith.add(sumtotal4,allMonthListManHourDtoList.get(i).get(m).getTimes());
							}
						}
					}
				}
			}
			rowStartnum+=2;
			Label labelm1= new Label(0, rowStartnum, Parma.strHomeTitle[2],formato);
			sheet.addCell(labelm1);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			double[] sumtempToltemp1=new double[allMonthListManHourDtoList.size()];
			double tempsum9=0.0;
			for (int j = 0; j < listsum9.size(); j++) {
				tempsum9+=listsum9.get(j);
			}
			for (int i = 0; i < Parma.strFunction.length; i++) {
				//工程设计部特例项目的信心判定
				//if (("工程设计部").equals(department)) {
				if (DepartmentEnum.GCSJB.getName().equals(department)) {
					if (("其他").equals(Parma.strFunction[i])) {
						Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
						sheet.addCell(label);
						int weiyi8=weiyi;
						double sumtemp=0.0;
						for (int m = 0; m < allListCategoryDetail.size(); m++) {
							Number label2=new Number(weiyi8, rowStartnum,allListCategoryDetail.get(m).get(i),formatoNum);
							sheet.addCell(label2);
							Number labeltu=new Number(weiyi8+1, rowStartnum,Arith.div(allListCategoryDetail.get(m).get(i), workDayslist.get(m)*8),formatoNum);
							sheet.addCell(labeltu);
							sumtemp+=allListCategoryDetail.get(m).get(i);
							sumtempToltemp1[m]+=allListCategoryDetail.get(m).get(i);
							weiyi8+=2;
						}
						Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
						sheet.addCell(labelnLabeld4);
						Number labelgut=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
						sheet.addCell(labelgut);
						Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotal4, 4)*100,formatoNum);
						sheet.addCell(labelnLabel_d4);
						rowStartnum++;
					} else {
						Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
						sheet.addCell(label);
						int weiyi8=weiyi;
						double sumtemp=0.0;
						for (int m = 0; m < allListCategoryDetail.size(); m++) {
							Number label2=new Number(weiyi8, rowStartnum,allListCategoryDetail.get(m).get(i),formatoNum);
							sheet.addCell(label2);
							Number labeltu=new Number(weiyi8+1, rowStartnum,Arith.div(allListCategoryDetail.get(m).get(i), workDayslist.get(m)*8),formatoNum);
							sheet.addCell(labeltu);
							sumtemp+=allListCategoryDetail.get(m).get(i);
							sumtempToltemp1[m]+=allListCategoryDetail.get(m).get(i);
							weiyi8+=2;
						}
						Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
						sheet.addCell(labelnLabeld4);
						Number labelgut=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
						sheet.addCell(labelgut);
						Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sumtemp, (sumtotal4+tempsum9), 4)*100,formatoNum);
						sheet.addCell(labelnLabel_d4);
						rowStartnum++;
					}
				} else {
					int weiyi8=weiyi;
					Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
					sheet.addCell(label);
					double sumtemp=0.0;
					for (int m = 0; m < allListCategoryDetail.size(); m++) {
						Number label2=new Number(weiyi8, rowStartnum,allListCategoryDetail.get(m).get(i),formatoNum);
						sheet.addCell(label2);
						Number labeltu=new Number(weiyi8+1, rowStartnum,Arith.div(allListCategoryDetail.get(m).get(i), workDayslist.get(m)*8),formatoNum);
						sheet.addCell(labeltu);
						sumtemp+=allListCategoryDetail.get(m).get(i);
						sumtempToltemp1[m]+=allListCategoryDetail.get(m).get(i);
						weiyi8+=2;
					}
					Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
					sheet.addCell(labelnLabeld4);
					Number labelgut=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
					sheet.addCell(labelgut);
					Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotal4, 4)*100,formatoNum);
					sheet.addCell(labelnLabel_d4);
					rowStartnum++;	
			    }
			}
			Label labelnLabel_d7wab= new Label(0, rowStartnum, Parma.strHomeTitle[2]+"　计",formatgr);
			sheet.addCell(labelnLabel_d7wab);
			//if (("工程设计部").equals(department)) {
			if (DepartmentEnum.GCSJB.getName().equals(department)) {
				int weiyi9=weiyi;
				double tempsumjk=0.0;
				for (int j = 0; j < allListCategoryDetail.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,sumtempToltemp1[j],formatgrNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labeltu=new Number(weiyi9+1, rowStartnum,Arith.div(sumtempToltemp1[j], workDayslist.get(j)*8),formatgrNum);
					sheet.addCell(labeltu);
					tempsumjk+=sumtempToltemp1[j];
					weiyi9+=2;
				}
				Number labelnLabel_d7wa=new Number(25, rowStartnum,tempsumjk,formatgrNum);
				sheet.addCell(labelnLabel_d7wa);  
				Number labelgut=new Number(26, rowStartnum,Arith.div(tempsumjk,personMonthTotal),formatgrNum);
				sheet.addCell(labelgut);
				Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(tempsumjk, sumtotal4, 4)*100,formatgrNum);
				sheet.addCell(labelnLabel_d4);
				rowStartnum++;
			}
			else { 
				int weiyi9=weiyi;
				for (int j = 0; j < allListCategoryDetail.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,sumtempToltemp1[j],formatgrNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labeltu=new Number(weiyi9+1, rowStartnum,Arith.div(sumtempToltemp1[j], workDayslist.get(j)*8),formatgrNum);
					sheet.addCell(labeltu);
					weiyi9+=2;
				}
				Number labelnLabel_d7wa=new Number(25, rowStartnum,sumtotal4,formatgrNum);
				sheet.addCell(labelnLabel_d7wa); 
				Number labelgut=new Number(26, rowStartnum,Arith.div(sumtotal4,personMonthTotal),formatgrNum);
				sheet.addCell(labelgut);
				Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sumtotal4, sumtotal4, 4)*100,formatgrNum);
				sheet.addCell(labelnLabel_d4);
				rowStartnum++;
			}
			/**
			 * CT开发工数明细
			 */
			rowStartnum+=2;
			Label labeldevelopNUm= new Label(0, rowStartnum, Parma.strHomeTitle[3],formato);
			sheet.addCell(labeldevelopNUm);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
//			double temp111=0.0;
//			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
//				for (int j = 0; j < allMonthListManHourDtoList.get(i).size(); j++) {
//					if (("开发工数").equals(allMonthListManHourDtoList.get(i).get(j).getCategory())) {
//						temp111+=allMonthListManHourDtoList.get(i).get(j).getTimes();
//					}
//				}	
//			}
			for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {
				//if (("工程设计部").equals(department)&&("附带").equals(Parma.strDevelopWorkNum[i])) {
				if (DepartmentEnum.GCSJB.getName().equals(department)&&("附带").equals(Parma.strDevelopWorkNum[i])) {
				Label label = new Label(0, rowStartnum, Parma.strDevelopWorkNum[i]+"工数",formato);
				sheet.addCell(label);
				int weiyi9=weiyi;
				double sumtemp=0.0;
				for (int j = 0; j < allListCategoryDetail.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,listsum9.get(j),formatoNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labeltu=new Number(weiyi9+1, rowStartnum,Arith.div(listsum9.get(j), workDayslist.get(j)*8),formatoNum);
					sheet.addCell(labeltu);
					sumtemp+=listsum9.get(j);
					weiyi9+=2;
				}
				Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
				sheet.addCell(labelnLabeld4);
				Number labelgut=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
				sheet.addCell(labelgut);
				Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotal4, 4)*100,formatoNum);
				sheet.addCell(labelnLabel_d4);
				rowStartnum++;
				}
				else {
					Label label = new Label(0, rowStartnum, Parma.strDevelopWorkNum[i]+"工数",formato);
					sheet.addCell(label);
					int weiyi10=weiyi;
					double sumtemp=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
						double sum91=0.0;
						for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
							for (int j2 = 0; j2 < listProjectCT.size(); j2++) {
								if (allMonthListManHourDtoList.get(j).get(m).getProjectID()==(listProjectCT.get(j2).getProjectID())) {
										for (int k2 = 0; k2 < listProjectTasks.size(); k2++) {
											if ((Parma.strDevelopWorkNum[i]).equals(listProjectTasks.get(k2).getMemo())) {
												//if (allMonthListManHourDtoList.get(j).get(m).getTask().equals(listProjectTasks.get(k2).getTask())) {
												if (allMonthListManHourDtoList.get(j).get(m).getTaskID() == listProjectTasks.get(k2).getTaskID()) {
													if (allMonthListManHourDtoList.get(j).get(m).getCategory().equals(listProjectTasks.get(k2).getCategory())) {
															sum91 = Arith.add(sum91,allMonthListManHourDtoList.get(j).get(m).getTimes());
													}
												}
											}
										}
								}
							}
						}
						sumtemp+=sum91;
						Number label2=new Number(weiyi10, rowStartnum,sum91,formatoNum);
						sheet.addCell(label2);
						Number labeltu=new Number(weiyi10+1, rowStartnum,Arith.div(sum91,workDayslist.get(j)*8),formatoNum);
						sheet.addCell(labeltu);
						weiyi10+=2;
				}
				Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
				sheet.addCell(labelnLabeld4);
				Number labelgut=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
				sheet.addCell(labelgut);
				Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotal4, 4)*100,formatoNum);
				sheet.addCell(labelnLabel_d4);
				rowStartnum++;
				}
			}
			/**
			 * PRC　対応工数計
			 */
			rowStartnum+=2;
			List<Project> listProjectPRC=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
					if ((Parma.strProjectClientName[1]).equals(listProject.get(i).getProjectClientName())) {
						listProjectPRC.add(listProject.get(i));
					}
			}
			double sumtotalPRC1=0.0;
			for (int i = 0; i < listProjectPRC.size(); i++) {
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						if (listProjectPRC.get(i).getProjectID()==allMonthListManHourDtoList.get(j).get(m).getProjectID()) {
							sumtotalPRC1 = Arith.add(sumtotalPRC1,allMonthListManHourDtoList.get(j).get(m).getTimes());
						}
					}
				}
			}
			Label labelqab= new Label(0, rowStartnum, Parma.strHomeTitle[4],formato);
			sheet.addCell(labelqab);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			double[] sumtempToltempPRC=new double[allMonthListManHourDtoList.size()];
			for (int i = 0; i < Parma.strCategoryName.length; i++) {
				Label label = new Label(0, rowStartnum, Parma.strCategoryName[i],formato);
				sheet.addCell(label);
				double sum5temp=0.0;
				int weiyi6=weiyi;
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					double sum5=0.0;
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						if (allMonthListManHourDtoList.get(j).get(m).getCategory().equals(Parma.strCategoryName[i])) {
							for (int j2 = 0; j2 < listProjectPRC.size(); j2++) {
								if (allMonthListManHourDtoList.get(j).get(m).getProjectID()==listProjectPRC.get(j2).getProjectID()) {
									sum5 = Arith.add(sum5,allMonthListManHourDtoList.get(j).get(m).getTimes());
								}
							}
						}
					}
					Number label2=new Number(weiyi6, rowStartnum,sum5,formatoNum);
					sheet.addCell(label2);
					Number labeltu=new Number(weiyi6+1, rowStartnum,Arith.div(sum5, workDayslist.get(j)*8),formatoNum);
					sheet.addCell(labeltu);
					sum5temp+=sum5;
					sumtempToltempPRC[j]+=sum5;
					weiyi6+=2;
				}
				Number labelnLabeldbg=new Number(25, rowStartnum,sum5temp,formatoNum);
				sheet.addCell(labelnLabeldbg);
				Number labelgut=new Number(26, rowStartnum,Arith.div(sum5temp,personMonthTotal),formatoNum);
				sheet.addCell(labelgut);
				Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sum5temp, sumtotalPRC1, 4)*100,formatoNum);
				sheet.addCell(labelnLabel_d4);
				rowStartnum++;
			}
			int weiyi11=weiyi;
			Label labelnLabelk6wab= new Label(0, rowStartnum, Parma.strHomeTitle[4]+"　计",formatgr);
			sheet.addCell(labelnLabelk6wab); 
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				Number labelnLabel_d6wabm=new Number(weiyi11, rowStartnum,sumtempToltempPRC[i],formatgrNum);
				sheet.addCell(labelnLabel_d6wabm); 
				Number labeltu=new Number(weiyi11+1, rowStartnum,Arith.div(sumtempToltempPRC[i], workDayslist.get(i)*8),formatgrNum);
				sheet.addCell(labeltu);
				weiyi11+=2;
			} 
			Number labelnLabelk6wa=new Number(25, rowStartnum,sumtotalPRC1,formatgrNum);
			sheet.addCell(labelnLabelk6wa);  
			Number labelgut=new Number(26, rowStartnum,Arith.div(sumtotalPRC1,personMonthTotal),formatgrNum);
			sheet.addCell(labelgut);
			Number labelnLabel_d4=new Number(27, rowStartnum,Arith.div(sumtotalPRC1, sumtotalPRC1, 4)*100,formatgrNum);
			sheet.addCell(labelnLabel_d4);
			rowStartnum++;
			/**
			 * PRC开发工数类别明细
			 */
			double sumtotalPRC4=0.0;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int m = 0; m < allMonthListManHourDtoList.get(i).size(); m++) {
					for (int j = 0; j < listProjectPRC.size(); j++) {
						if (allMonthListManHourDtoList.get(i).get(m).getProjectID()==(listProjectPRC.get(j).getProjectID())) {
							if (Parma.strCategoryName[2].equals(allMonthListManHourDtoList.get(i).get(m).getCategory())) {
								sumtotalPRC4 = Arith.add(sumtotalPRC4,allMonthListManHourDtoList.get(i).get(m).getTimes());
							}
						}
					}
				}
			}
			rowStartnum+=2;
			Label labelmkl= new Label(0, rowStartnum, Parma.strHomeTitle[5],formato);
			sheet.addCell(labelmkl);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			double[] sumtempToltempPRC1=new double[allMonthListManHourDtoList.size()];
			double tempsum10=0.0;
			for (int j = 0; j < listsum10.size(); j++) {
				tempsum10+=listsum10.get(j);
			}
			for (int i = 0; i < Parma.strFunction.length; i++) {
				//if (("工程设计部").equals(department)){
				if (DepartmentEnum.GCSJB.getName().equals(department)){
					if (("其他").equals(Parma.strFunction[i])) {
						Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
						sheet.addCell(label);
						int weiyi8=weiyi;
						double sumtemp=0.0;
						for (int m = 0; m < allListCategoryDetail.size(); m++) {
							Number label2=new Number(weiyi8, rowStartnum,allListCategoryDetail.get(m).get(Parma.strFunction.length+i),formatoNum);
							sheet.addCell(label2);
							Number labeltu=new Number(weiyi8+1, rowStartnum,Arith.div(allListCategoryDetail.get(m).get(Parma.strFunction.length+i), workDayslist.get(m)*8),formatoNum);
							sheet.addCell(labeltu);
							sumtemp+=allListCategoryDetail.get(m).get(Parma.strFunction.length+i);
							sumtempToltempPRC1[m]+=allListCategoryDetail.get(m).get(Parma.strFunction.length+i);
							weiyi8+=2;
						}
						Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
						sheet.addCell(labelnLabeld4);
						Number labelete=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
						sheet.addCell(labelete);
						Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotalPRC4, 4)*100,formatoNum);
						sheet.addCell(labeldrt_d4);
						rowStartnum++;
					}else {
						Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
						sheet.addCell(label);
						int weiyi8=weiyi;
						double sumtemp=0.0;
						for (int m = 0; m < allListCategoryDetail.size(); m++) {
							Number label2=new Number(weiyi8, rowStartnum,allListCategoryDetail.get(m).get(Parma.strFunction.length+i),formatoNum);
							sheet.addCell(label2);
							Number labeltu=new Number(weiyi8+1, rowStartnum,Arith.div(allListCategoryDetail.get(m).get(Parma.strFunction.length+i), workDayslist.get(m)*8),formatoNum);
							sheet.addCell(labeltu);
							sumtemp+=allListCategoryDetail.get(m).get(Parma.strFunction.length+i);
							sumtempToltempPRC1[m]+=allListCategoryDetail.get(m).get(Parma.strFunction.length+i);
							weiyi8+=2;
						}
						Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
						sheet.addCell(labelnLabeld4);
						Number labelete=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
						sheet.addCell(labelete);
						Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sumtemp, (sumtotalPRC4+tempsum10), 4)*100,formatoNum);
						sheet.addCell(labeldrt_d4);
						rowStartnum++;
					}
				}
				else {
					Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
					sheet.addCell(label);
					int weiyi8=weiyi;
					double sumtemp=0.0;
					for (int m = 0; m < allListCategoryDetail.size(); m++) {
						Number label2=new Number(weiyi8, rowStartnum,allListCategoryDetail.get(m).get(Parma.strFunction.length+i),formatoNum);
						sheet.addCell(label2);
						Number labeltu=new Number(weiyi8+1, rowStartnum,Arith.div(allListCategoryDetail.get(m).get(Parma.strFunction.length+i), workDayslist.get(m)*8),formatoNum);
						sheet.addCell(labeltu);
						sumtemp+=allListCategoryDetail.get(m).get(Parma.strFunction.length+i);
						sumtempToltempPRC1[m]+=allListCategoryDetail.get(m).get(Parma.strFunction.length+i);
						weiyi8+=2;
					}
					Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
					sheet.addCell(labelnLabeld4);
					Number labelete=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
					sheet.addCell(labelete);
					Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotalPRC4, 4)*100,formatoNum);
					sheet.addCell(labeldrt_d4);
					rowStartnum++;
				}
				
			}
			Label labelnLabelu7wab= new Label(0, rowStartnum, Parma.strHomeTitle[5]+"　计",formatgr);
			sheet.addCell(labelnLabelu7wab);
			//if (("工程设计部").equals(department)){
			if (DepartmentEnum.GCSJB.getName().equals(department)){
				int weiyi9=weiyi;
				double tempsumjk=0.0;
				for (int j = 0; j < allListCategoryDetail.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,sumtempToltempPRC1[j],formatgrNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labeltu=new Number(weiyi9+1, rowStartnum,Arith.div(sumtempToltempPRC1[j], workDayslist.get(j)*8),formatgrNum);
					sheet.addCell(labeltu);
					tempsumjk+=sumtempToltempPRC1[j];
					weiyi9+=2;
				}
				Number labelnLabelu7wa=new Number(25, rowStartnum,tempsumjk,formatgrNum);
				sheet.addCell(labelnLabelu7wa);   
				Number labelete=new Number(26, rowStartnum,Arith.div(tempsumjk,personMonthTotal),formatgrNum);
				sheet.addCell(labelete);
				Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(tempsumjk, sumtotalPRC4, 4)*100,formatgrNum);
				sheet.addCell(labeldrt_d4);
				rowStartnum++;
			}
			else {
				int weiyi9=weiyi;
				for (int j = 0; j < allListCategoryDetail.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,sumtempToltempPRC1[j],formatgrNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labeltu=new Number(weiyi9+1, rowStartnum,Arith.div(sumtempToltempPRC1[j], workDayslist.get(j)*8),formatgrNum);
					sheet.addCell(labeltu);
					weiyi9+=2;
				}
				Number labelnLabelu7wa=new Number(25, rowStartnum,sumtotalPRC4,formatgrNum);
				sheet.addCell(labelnLabelu7wa);   
				Number labelete=new Number(26, rowStartnum,Arith.div(sumtotalPRC4,personMonthTotal),formatgrNum);
				sheet.addCell(labelete);
				Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sumtotalPRC4, sumtotalPRC4, 4)*100,formatgrNum);
				sheet.addCell(labeldrt_d4);
				rowStartnum++;
			}
			/**
			 * PRC开发工数明细
			 */
			rowStartnum+=2;
			Label labeldevelopNUmg= new Label(0, rowStartnum, Parma.strHomeTitle[6],formato);
			sheet.addCell(labeldevelopNUmg);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {
				//if (("工程设计部").equals(department)&&("附带").equals(Parma.strDevelopWorkNum[i])) {
				if (DepartmentEnum.GCSJB.getName().equals(department)&&("附带").equals(Parma.strDevelopWorkNum[i])) {
				Label label = new Label(0, rowStartnum, Parma.strDevelopWorkNum[i]+"工数",formato);
				sheet.addCell(label);
				int weiyi9=weiyi;
				double sumtemp=0.0;
				for (int j = 0; j < allListCategoryDetail.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,listsum10.get(j),formatoNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labeltu=new Number(weiyi9+1, rowStartnum,Arith.div(listsum10.get(j), workDayslist.get(j)*8),formatoNum);
					sheet.addCell(labeltu);
					sumtemp+=listsum10.get(j);
					weiyi9+=2;
				}
				Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
				sheet.addCell(labelnLabeld4);
				Number labelete=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
				sheet.addCell(labelete);
				Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotal4, 4)*100,formatoNum);
				sheet.addCell(labeldrt_d4);
				rowStartnum++;
				}
				else {
					Label label = new Label(0, rowStartnum, Parma.strDevelopWorkNum[i]+"工数",formato);
					sheet.addCell(label);
					int weiyi10=weiyi;
					double sumtemp=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
						double sum91=0.0;
						for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
							for (int j2 = 0; j2 < listProjectPRC.size(); j2++) {
								if (allMonthListManHourDtoList.get(j).get(m).getProjectID()==(listProjectPRC.get(j2).getProjectID())) {
										for (int k2 = 0; k2 < listProjectTasks.size(); k2++) {
											if ((Parma.strDevelopWorkNum[i]).equals(listProjectTasks.get(k2).getMemo())) {
												//if (allMonthListManHourDtoList.get(j).get(m).getTask().equals(listProjectTasks.get(k2).getTask())) {
												if (allMonthListManHourDtoList.get(j).get(m).getTaskID() == listProjectTasks.get(k2).getTaskID()) {
													if (allMonthListManHourDtoList.get(j).get(m).getCategory().equals(listProjectTasks.get(k2).getCategory())) {
														sum91 = Arith.add(sum91,allMonthListManHourDtoList.get(j).get(m).getTimes());
													}
												}
											}
										}
								}
							}
						}
						sumtemp+=sum91;
						Number label2=new Number(weiyi10, rowStartnum,sum91,formatoNum);
						sheet.addCell(label2);
						Number labeltu=new Number(weiyi10+1, rowStartnum,Arith.div(sum91, workDayslist.get(j)*8),formatoNum);
						sheet.addCell(labeltu);
						weiyi10+=2;
				}
				Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
				sheet.addCell(labelnLabeld4);
				Number labelete=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
				sheet.addCell(labelete);
				Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotalPRC4, 4)*100,formatoNum);
				sheet.addCell(labeldrt_d4);
				rowStartnum++;
				}
			}
			
			/**
			 * OUTOUT　対応工数計
			 */
			rowStartnum+=2;
			List<Project> listProjectOUT=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
					if ((Parma.strProjectClientName[2]).equals(listProject.get(i).getProjectClientName())) {
						listProjectOUT.add(listProject.get(i));
					}
			}
			double sumtotalOUT1=0.0;
			for (int i = 0; i < listProjectOUT.size(); i++) {
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						if (listProjectOUT.get(i).getProjectID()==allMonthListManHourDtoList.get(j).get(m).getProjectID()) {
							sumtotalOUT1 = Arith.add(sumtotalOUT1,allMonthListManHourDtoList.get(j).get(m).getTimes());
						}
					}
				}
			}
			Label labelqhu= new Label(0, rowStartnum, Parma.strHomeTitle[7],formato);
			sheet.addCell(labelqhu);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			double[] sumtempToltempOUT=new double[allMonthListManHourDtoList.size()];
			for (int i = 0; i < Parma.strCategoryName.length; i++) {
				Label label = new Label(0, rowStartnum, Parma.strCategoryName[i],formato);
				sheet.addCell(label);
				double sum5temp=0.0;
				int weiyi6=weiyi;
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					double sum5=0.0;
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						if (allMonthListManHourDtoList.get(j).get(m).getCategory().equals(Parma.strCategoryName[i])) {
							for (int j2 = 0; j2 < listProjectOUT.size(); j2++) {
								if (allMonthListManHourDtoList.get(j).get(m).getProjectID()==listProjectOUT.get(j2).getProjectID()) {
									sum5 = Arith.add(sum5,allMonthListManHourDtoList.get(j).get(m).getTimes());
								}
							}
						}
					}
					Number label2=new Number(weiyi6, rowStartnum,sum5,formatoNum);
					sheet.addCell(label2);
					Number labeltu=new Number(weiyi6+1, rowStartnum,Arith.div(sum5, workDayslist.get(j)*8),formatoNum);
					sheet.addCell(labeltu);
					sum5temp+=sum5;
					sumtempToltempOUT[j]+=sum5;
					weiyi6+=2;
				}
				Number labelnLabeldbg=new Number(25, rowStartnum,sum5temp,formatoNum);
				sheet.addCell(labelnLabeldbg);
				Number labelete=new Number(26, rowStartnum,Arith.div(sum5temp,personMonthTotal),formatoNum);
				sheet.addCell(labelete);
				Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sum5temp, sumtotalPRC1, 4)*100,formatoNum);
				sheet.addCell(labeldrt_d4);
				rowStartnum++;
			}
			int weiyie=weiyi;
			Label labelnLabelde= new Label(0, rowStartnum, Parma.strHomeTitle[7]+"　计",formatgr);
			sheet.addCell(labelnLabelde); 
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				Number labelnLabel_d6wabm=new Number(weiyie, rowStartnum,sumtempToltempOUT[i],formatgrNum);
				sheet.addCell(labelnLabel_d6wabm); 
				Number labeltu=new Number(weiyie+1, rowStartnum,Arith.div(sumtempToltempOUT[i], workDayslist.get(i)*8),formatgrNum);
				sheet.addCell(labeltu);
				weiyie+=2;
			} 
			Number labelnLabeldr=new Number(25, rowStartnum,sumtotalOUT1,formatgrNum);
			sheet.addCell(labelnLabeldr);  
			Number labelete=new Number(26, rowStartnum,Arith.div(sumtotalOUT1,personMonthTotal),formatgrNum);
			sheet.addCell(labelete);
			Number labeldrt_d4=new Number(27, rowStartnum,Arith.div(sumtotalOUT1, sumtotalOUT1, 4)*100,formatgrNum);
			sheet.addCell(labeldrt_d4);
			rowStartnum++;
			/**
			 * OUTOUT开发工数类别明细
			 */
			double sumtotalOUT4=0.0;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int m = 0; m < allMonthListManHourDtoList.get(i).size(); m++) {
					for (int j = 0; j < listProjectOUT.size(); j++) {
						if (allMonthListManHourDtoList.get(i).get(m).getProjectID()==(listProjectOUT.get(j).getProjectID())) {
							if (Parma.strCategoryName[2].equals(allMonthListManHourDtoList.get(i).get(m).getCategory())) {
								sumtotalOUT4 = Arith.add(sumtotalOUT4,allMonthListManHourDtoList.get(i).get(m).getTimes());
							}
						}
					}
				}
			}
			rowStartnum+=2;
			Label labeletw= new Label(0, rowStartnum, Parma.strHomeTitle[8],formato);
			sheet.addCell(labeletw);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			double[] sumtempToltempOUT1=new double[allMonthListManHourDtoList.size()];
			for (int i = 0; i < Parma.strFunction.length; i++) {
					Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
					sheet.addCell(label);
					int weiyi8=weiyi;
					double sumtemp=0.0;
					for (int m = 0; m < allListCategoryDetail.size(); m++) {
						Number label2=new Number(weiyi8, rowStartnum,allListCategoryDetail.get(m).get(2*Parma.strFunction.length+i),formatoNum);
						sheet.addCell(label2);
						Number labeltu=new Number(weiyi8+1, rowStartnum,Arith.div(allListCategoryDetail.get(m).get(2*Parma.strFunction.length+i), workDayslist.get(m)*8),formatoNum);
						sheet.addCell(labeltu);
						sumtemp+=allListCategoryDetail.get(m).get(2*Parma.strFunction.length+i);
						sumtempToltempOUT1[m]+=allListCategoryDetail.get(m).get(2*Parma.strFunction.length+i);
						weiyi8+=2;
					}
					Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
					sheet.addCell(labelnLabeld4);
					Number labelvt=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
					sheet.addCell(labelvt);
					Number labelfrt=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotalOUT4, 4)*100,formatoNum);
					sheet.addCell(labelfrt);
					rowStartnum++;
			}
			Label labelnLabelds= new Label(0, rowStartnum, Parma.strHomeTitle[8]+"　计",formatgr);
			sheet.addCell(labelnLabelds);
				int weiyi9=weiyi;
				for (int j = 0; j < allListCategoryDetail.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,sumtempToltempOUT1[j],formatgrNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labeltu=new Number(weiyi9+1, rowStartnum,Arith.div(sumtempToltempOUT1[j], workDayslist.get(j)*8),formatgrNum);
					sheet.addCell(labeltu);
					weiyi9+=2;
				}
				Number labelnLabelu7wa=new Number(25, rowStartnum,sumtotalOUT4,formatgrNum);
				sheet.addCell(labelnLabelu7wa); 
				Number labelvt=new Number(26, rowStartnum,Arith.div(sumtotalOUT4,personMonthTotal),formatgrNum);
				sheet.addCell(labelvt);
				Number labelfrt=new Number(27, rowStartnum,Arith.div(sumtotalOUT4, sumtotalOUT4, 4)*100,formatgrNum);
				sheet.addCell(labelfrt);
				rowStartnum++;
			/**
			 * OUTOUT开发工数明细
			 */
			rowStartnum+=2;
			Label labelhi= new Label(0, rowStartnum, Parma.strHomeTitle[9],formato);
			sheet.addCell(labelhi);
			for(int i=1;i<=Parma.strmonth.length;i++)
			{
				if (i%2==1) {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
					sheet.addCell(label);
				}
				else {
					Label label = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
					sheet.addCell(label);
				}
			}
			rowStartnum++;
			for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {
					Label label = new Label(0, rowStartnum, Parma.strDevelopWorkNum[i]+"工数",formato);
					sheet.addCell(label);
					int weiyi10=weiyi;
					double sumtemp=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
						double sum91=0.0;
						for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
							for (int j2 = 0; j2 < listProjectOUT.size(); j2++) {
								if (allMonthListManHourDtoList.get(j).get(m).getProjectID()==(listProjectOUT.get(j2).getProjectID())) {
										for (int k2 = 0; k2 < listProjectTasks.size(); k2++) {
											if ((Parma.strDevelopWorkNum[i]).equals(listProjectTasks.get(k2).getMemo())) {
												//if (allMonthListManHourDtoList.get(j).get(m).getTask().equals(listProjectTasks.get(k2).getTask())) {
												if (allMonthListManHourDtoList.get(j).get(m).getTaskID() == listProjectTasks.get(k2).getTaskID()) {
													if (allMonthListManHourDtoList.get(j).get(m).getCategory().equals(listProjectTasks.get(k2).getCategory())) {
														sum91 = Arith.add(sum91,allMonthListManHourDtoList.get(j).get(m).getTimes());
													}
												}
											}
										}
								}
							}
						}
						sumtemp+=sum91;
						Number label2=new Number(weiyi10, rowStartnum,sum91,formatoNum);
						sheet.addCell(label2);
						Number labeltu=new Number(weiyi10+1, rowStartnum,Arith.div(sum91, workDayslist.get(j)*8),formatoNum);
						sheet.addCell(labeltu);
						weiyi10+=2;
				}
				Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatoNum);
				sheet.addCell(labelnLabeld4);
				Number labelwt=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatoNum);
				sheet.addCell(labelwt);
				Number labelffg=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotalOUT4, 4)*100,formatoNum);
				sheet.addCell(labelffg);
				rowStartnum++;
				}
			/**
			 * 对剩余的空白部分进行单元格处理
			 */ 
			WritableCellFormat formatg=new WritableCellFormat(fontgr); 
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rowStartnum; j++) {
				Cell celletemp = sheet.getCell(0, j);
				String resultemp = celletemp.getContents();
				if (!resultemp.isEmpty()) {
					for (int k = 1; k <=Parma.strmonth.length ; k++) {
						Cell celletemp1 = sheet.getCell(k, j);
						String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							Label labelhLabel = new Label(k, j, null ,formatg);
							sheet.addCell(labelhLabel);
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
