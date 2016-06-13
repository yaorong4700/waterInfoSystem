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

import com.clarion.worksys.entity.AssumeDetail;
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
public class CompanyFirstPage {
/**
 * 生成公司整体工数Excel的首页信息
 * @param department  用户指定的部门名
 * @param year    起始年份
 * @param month	  起始月份
 * @param filenameString   唯一性字段，确保文件不会重名
 * @param allMonthListManHourDtoList    所有工数信息的集合
 * @param listProject    项目的集合
 * @param listProjectTasks   所有Task的集合
 * @param listDevelopWorkNum    开发工数（开发）与开发工数（附带）的工数集合
 * @param listCTDCOEDevelopAdditional	各个委托方对应的开发工数（开发）与开发工数（附带）的工数集合
 * @param workDayslist	每个月上班天数的集合
 * @param allAssumePJDetailList   每个月公司有产生工数的所有项目
 * @param designStaffNum   设计要员数
 */
	public void departFirstPage(String department, int year,int month,String filenameString,
			List<List<ManHourDto>> allMonthListManHourDtoList,List<Project> listProject,List<Project_task> listProjectTasks,
			List<List<Double>> listDevelopWorkNum,List<List<Double>> listCTDCOEDevelopAdditional,List<Integer> workDayslist,
			List<List<AssumeDetail>> allAssumePJDetailList,List<Integer> designStaffNum) {
		
		try {
			File file = new File(Parma.TEMP_FILESecondAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			
			String strnameString2=Parma.TEMP_FILESecondAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//由模板生成的最终文件所在地址
	      
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
			
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			
			WritableFont fontgr= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD); 
			WritableCellFormat formatgr=new WritableCellFormat(fontgr); //data deep alignment center
			formatgr.setAlignment(jxl.format.Alignment.CENTRE); 
			formatgr.setBackground(Colour.SKY_BLUE);
			formatgr.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatgrNum=new WritableCellFormat(fontgr); //data deep alignment left
			formatgrNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatgrNum.setBackground(Colour.SKY_BLUE);
			formatgrNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			 
			WritableCellFormat formato=new WritableCellFormat(fontgr); 
			formato.setAlignment(jxl.format.Alignment.CENTRE); 
			formato.setBackground(Colour.LIGHT_TURQUOISE);
			formato.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatNum=new WritableCellFormat(fontgr); //data alignment left set
			formatNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatNum.setBackground(Colour.LIGHT_TURQUOISE);
			formatNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
		    Label label0_0 = new Label(0, 0, "集计数据",formato);
			Label label0_1 = new Label(1, 0, "集计日",formato);
			Label label0_1hy = new Label(2, 0,String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formato);
			sheet.addCell(label0_0);
			sheet.addCell(label0_1);
			sheet.addCell(label0_1hy);
			
			Label label0_3 = new Label(0, 1, "集计范围",formato);
			Label label0_4 = new Label(1, 1, department,formato);
			sheet.addCell(label0_4);
			sheet.addCell(label0_3);
			int weiyi=0;
			if (month>=4&&month<=12) {
				weiyi=(month-4)*2+1;
			}
			if (month>=1&&month<=3) {
				weiyi=month*2+17;
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
					Number label2=new Number(weiyiPJDetail,rowNumPJ+m,allAssumePJDetailList.get(i).get(m).getAssumePJNum(),formato);
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
				Number label2=new Number(weiyiTime,rowNumTime,workDayslist.get(i)*8,formato);
				sheet.addCell(label2);
				Number labelvtg=new Number(weiyiTime,rowNumTime+1,workDayslist.get(i),formato);
				sheet.addCell(labelvtg);
				Number labeldef=new Number(weiyiTime,rowNumTime+2,designStaffNum.get(i),formato);
				sheet.addCell(labeldef);
				Number labelhyt=new Number(weiyiTime,rowNumTime+3,designStaffNum.get(i)*workDayslist.get(i)*8,formato);
				sheet.addCell(labelhyt);
				weiyiTime+=2;
			}
			/**
			 * 全工数状況
			 */
			int rowStartnum=14;
			double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
			double[] sum9list=new double[allMonthListManHourDtoList.size()];
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				double sum9=0.0;
				for (int j = 0; j < allMonthListManHourDtoList.get(i).size(); j++) {
					if ("185 工程设计部CT".equals(allMonthListManHourDtoList.get(i).get(j).getProjectName())) {
						sum9 = Arith.add(sum9,allMonthListManHourDtoList.get(i).get(j).getTimes());
					}
				}
				sum9list[i]+=sum9;
			}
			double[] sum10list=new double[allMonthListManHourDtoList.size()];
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				double sum10=0.0;
				for (int j = 0; j < allMonthListManHourDtoList.get(i).size(); j++) {
					if ("185 工程设计部DCOE".equals(allMonthListManHourDtoList.get(i).get(j).getProjectName())) {
						sum10 = Arith.add(sum10,allMonthListManHourDtoList.get(i).get(j).getTimes());
					}
				}
				sum10list[i]+=sum10;
			}
			double sumtotal1=0.0;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int j = 0; j < allMonthListManHourDtoList.get(i).size(); j++) {
					sumtotal1 = Arith.add(sumtotal1,allMonthListManHourDtoList.get(i).get(j).getTimes());
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
					Number label2=new Number(weiyi1, rowStartnum, SumTemp,formatNum);
					sheet.addCell(label2);
					Number labelgy=new Number(weiyi1+1, rowStartnum,Arith.div(SumTemp, (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelgy);
					SumTempFirst+=SumTemp;
					sumtempTol[m]+=SumTemp;
					weiyi1+=2;
				}
				Number labelnLabeld=new Number(25, rowStartnum, SumTempFirst,formatNum);
				sheet.addCell(labelnLabeld);
				Number labelgy=new Number(26, rowStartnum,Arith.div(rowStartnum,personMonthTotal),formatNum);
				sheet.addCell(labelgy);
				Number labelef=new Number(27, rowStartnum,Arith.div(rowStartnum, sumtotal1, 4)*100,formatNum);
				sheet.addCell(labelef);
				rowStartnum++;
			}
			//开发工数（开发）
			Label labelq12w= new Label(0, rowStartnum, Parma.strTotalWorkNumName[2],formato);
			sheet.addCell(labelq12w);
			int weiyi2=weiyi;
			double SumTempFirst1=0.0;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				Number labelrw=new Number(weiyi2, rowStartnum, listDevelopWorkNum.get(m).get(0),formatNum);
				sheet.addCell(labelrw);
				Number labelgy=new Number(weiyi2+1, rowStartnum,Arith.div((listDevelopWorkNum.get(m).get(0)), (workDayslist.get(m)*8)),formatNum);
				sheet.addCell(labelgy);
				SumTempFirst1+=listDevelopWorkNum.get(m).get(0);
				sumtempTol[m]+=listDevelopWorkNum.get(m).get(0);
				weiyi2+=2;
			}
			
			Number labelnLabeld1w=new Number(25, rowStartnum, SumTempFirst1,formatNum);
			sheet.addCell(labelnLabeld1w);
			Number labelgy=new Number(26, rowStartnum,Arith.div(SumTempFirst1,personMonthTotal),formatNum);
			sheet.addCell(labelgy);
			Number labelef=new Number(27, rowStartnum,Arith.div(SumTempFirst1, sumtotal1, 4)*100,formatNum);
			sheet.addCell(labelef);
			//开发工数（附带）
			rowStartnum++;
			int weiyi4=weiyi;
			double SumTempFirst4=0.0;
			Label labelq2w= new Label(0, rowStartnum, Parma.strTotalWorkNumName[3],formato);
			sheet.addCell(labelq2w);
			List<Double> tempsslist=new ArrayList<Double>();
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double tempss=0.0;
				tempss=sum10list[m]+sum9list[m];
				Number labelr1w=new Number(weiyi4, rowStartnum, listDevelopWorkNum.get(m).get(1)+tempss,formatNum);
				sheet.addCell(labelr1w);
				Number labeldr=new Number(weiyi4+1, rowStartnum,Arith.div((listDevelopWorkNum.get(m).get(1)+tempss), (workDayslist.get(m)*8)),formatNum);
				sheet.addCell(labeldr);
				SumTempFirst4+=listDevelopWorkNum.get(m).get(1)+tempss;
				sumtempTol[m]+=listDevelopWorkNum.get(m).get(1)+tempss;
				tempsslist.add(tempss);
				weiyi4+=2;
			}
			
			Number labelnLabeld2w=new Number(25, rowStartnum,SumTempFirst4,formatNum);
			sheet.addCell(labelnLabeld2w);
			Number labeldr=new Number(26, rowStartnum,Arith.div(SumTempFirst4,personMonthTotal),formatNum);
			sheet.addCell(labeldr);
			Number labelser=new Number(27, rowStartnum,Arith.div(SumTempFirst4, sumtotal1, 4)*100,formatNum);
			sheet.addCell(labelser);
			
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
				Number label2abg=new Number(weiyi3, rowStartnum,SumTempfg,formatNum);
				sheet.addCell(label2abg);
				Number labelswe=new Number(weiyi3+1, rowStartnum,Arith.div(SumTempfg, (workDayslist.get(m)*8)),formatNum);
				sheet.addCell(labelswe);
				SumTempFirst2+=SumTempfg;
				sumtempTol[m]+=SumTempfg;
				weiyi3+=2;
			}
			Number labelnLabeld=new Number(25, rowStartnum,SumTempFirst2,formatNum);
			sheet.addCell(labelnLabeld);
			Number labelwd=new Number(26, rowStartnum,Arith.div(SumTempFirst2,personMonthTotal),formatNum);
			sheet.addCell(labelwd);
			Number labelsd=new Number(27, rowStartnum,Arith.div(SumTempFirst2, sumtotal1, 4)*100,formatNum);
			sheet.addCell(labelsd);
			
			rowStartnum++;
			int weiyi5=weiyi;
			Label labelnLabel_d3wab= new Label(0, rowStartnum, Parma.strHomeTitle[0]+"  合计",formatgr);
			sheet.addCell(labelnLabel_d3wab); 
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				Number labelnLabel_d3wabm=new Number(weiyi5, rowStartnum,sumtempTol[m],formatgrNum);
				sheet.addCell(labelnLabel_d3wabm); 
				Number labelswe=new Number(weiyi5+1, rowStartnum,Arith.div(sumtempTol[m], (workDayslist.get(m)*8)),formatgrNum);
				sheet.addCell(labelswe);
				weiyi5+=2;
			}
			Number labelnLabel_d3wa=new Number(25, rowStartnum,sumtotal1,formatgrNum);
			sheet.addCell(labelnLabel_d3wa);   
			Number labelgtr=new Number(26, rowStartnum,Arith.div(sumtotal1,personMonthTotal),formatgrNum);
			sheet.addCell(labelgtr);
			Number labelswe=new Number(27, rowStartnum,Arith.div(sumtotal1, sumtotal1, 4)*100,formatgrNum);
			sheet.addCell(labelswe);
			rowStartnum++;
			
			/**
			 * CT　対応工数计
			 */
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
					Number label2=new Number(weiyi6, rowStartnum, sum5,formatNum);
					sheet.addCell(label2);
					Number labelbg=new Number(weiyi6+1, rowStartnum,Arith.div(sum5, (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelbg);
					sum5temp+=sum5;
					sumtempToltemp[m]+=sum5;
					weiyi6+=2;
				}
				Number labelnLabeldbg=new Number(25, rowStartnum, sum5temp,formatNum);
				sheet.addCell(labelnLabeldbg);
				Number labeser=new Number(26, rowStartnum,Arith.div(sum5temp,personMonthTotal),formatNum);
				sheet.addCell(labeser);
				Number labelawd=new Number(27, rowStartnum,Arith.div(sum5temp, sumtotalCT1, 4)*100,formatNum);
				sheet.addCell(labelawd);
				rowStartnum++;
			}
			int weiyi7=weiyi;
			Label labelnLabel_d6wab= new Label(0, rowStartnum, Parma.strHomeTitle[1]+"　计",formatgr);
			sheet.addCell(labelnLabel_d6wab); 
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				Number labelnLabel_d6wabm=new Number(weiyi7, rowStartnum,sumtempToltemp[i],formatgrNum);
				sheet.addCell(labelnLabel_d6wabm); 
				Number labelbg=new Number(weiyi7+1, rowStartnum,Arith.div(sumtempToltemp[i], (workDayslist.get(i)*8)),formatgrNum);
				sheet.addCell(labelbg);
				weiyi7+=2;
			}
			Number labelnLabel_d6wa=new Number(25, rowStartnum, sumtotalCT1,formatgrNum);
			sheet.addCell(labelnLabel_d6wa);   
			Number labeser=new Number(26, rowStartnum,Arith.div(sumtotalCT1,personMonthTotal),formatgrNum);
			sheet.addCell(labeser);
			Number labelawd=new Number(27, rowStartnum,Arith.div(sumtotalCT1, sumtotalCT1, 4)*100,formatgrNum);
			sheet.addCell(labelawd);
			rowStartnum++;
			/**
			 * CT开发工数类别明细
			 */
			double sumtotal4=0.0f;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int m = 0; m < allMonthListManHourDtoList.get(i).size(); m++) {
					for (int j = 0; j < listProjectCT.size(); j++) {
						if (allMonthListManHourDtoList.get(i).get(m).getProjectID()==(listProjectCT.get(j).getProjectID())) {
							//开发工数
							if (Parma.strCategoryName[2].equals(allMonthListManHourDtoList.get(i).get(m).getCategory())) {
										sumtotal4+=allMonthListManHourDtoList.get(i).get(m).getTimes();
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
			for (int i = 0; i < Parma.strFunction.length; i++) {
				int weiyi8=weiyi;
				Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
				sheet.addCell(label);
				double sumtemp=0.0;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double sum9=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
							for (int j2 = 0; j2 < listProjectCT.size(); j2++) {
								if (allMonthListManHourDtoList.get(m).get(j).getProjectID()==(listProjectCT.get(j2).getProjectID())) {
									if (("开发工数").equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
										if ((Parma.strFunction[i]).equals(listProjectCT.get(j2).getFunction())) {
											sum9 = Arith.add(sum9,allMonthListManHourDtoList.get(m).get(j).getTimes());
									}
									}		
								}
							}
					}
					Number label2=new Number(weiyi8, rowStartnum, sum9,formatNum);
					sheet.addCell(label2);
					Number labelbg=new Number(weiyi8+1, rowStartnum,Arith.div(sum9, (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelbg);
					sumtemp+=sum9;
					sumtempToltemp1[m]+=sum9;
					weiyi8+=2;
				}
				Number labelnLabeld4=new Number(25, rowStartnum, sumtemp,formatNum);
				sheet.addCell(labelnLabeld4);
				Number labese=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatNum);
				sheet.addCell(labese);
				Number labelddh=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotal4, 4)*100,formatNum);
				sheet.addCell(labelddh);
				rowStartnum++;
			}
			Label labelnLabel_d7wab= new Label(0, rowStartnum, Parma.strHomeTitle[2]+"　计",formatgr);
			sheet.addCell(labelnLabel_d7wab); 
			int weiyi9=weiyi;
			for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
				Number labelnLabel_d7wabm=new Number(weiyi9, rowStartnum,sumtempToltemp1[j],formatgrNum);
				sheet.addCell(labelnLabel_d7wabm); 
				Number labelbg=new Number(weiyi9+1, rowStartnum,Arith.div(sumtempToltemp1[j], (workDayslist.get(j)*8)),formatgrNum);
				sheet.addCell(labelbg);
				weiyi9+=2;
			} 
			Number labelnLabel_d7wa=new Number(25, rowStartnum, sumtotal4,formatgrNum);
			sheet.addCell(labelnLabel_d7wa);
			Number labese=new Number(26, rowStartnum,Arith.div(sumtotal4,personMonthTotal),formatgrNum);
			sheet.addCell(labese);
			Number labelddh=new Number(27, rowStartnum,Arith.div(sumtotal4, sumtotal4, 4)*100,formatgrNum);
			sheet.addCell(labelddh);
			rowStartnum++;
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
				Label labelvLabel = new Label(0, rowStartnum, Parma.strDevelopWorkNum[0]+"工数",formato);
				sheet.addCell(labelvLabel);
				int weiyi10=weiyi;
				double missionsum=0.0;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double misson=0.0;
					misson = Arith.add(misson,listCTDCOEDevelopAdditional.get(m).get(0));
					Number label2vb=new Number(weiyi10, rowStartnum,misson,formatNum);
					sheet.addCell(label2vb);
					Number labelbg=new Number(weiyi10+1, rowStartnum,Arith.div(misson, (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelbg);
					missionsum+=misson;
					weiyi10+=2;
				}
				Number labelnLabeld4vb=new Number(25, rowStartnum,missionsum,formatNum);
				sheet.addCell(labelnLabeld4vb);
				Number labesesr=new Number(26, rowStartnum,Arith.div(missionsum,personMonthTotal),formatNum);
				sheet.addCell(labesesr);
				Number labeladf=new Number(27, rowStartnum,Arith.div(missionsum, sumtotal4, 4)*100,formatNum);
				sheet.addCell(labeladf);
				rowStartnum++;
				
				Label labeldg = new Label(0, rowStartnum, Parma.strDevelopWorkNum[1]+"工数",formato);
				sheet.addCell(labeldg);
				int weiyi11=weiyi;
				double missionwesum=0.0;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double missonwe=0.0;
					missonwe = Arith.add(missonwe,listCTDCOEDevelopAdditional.get(m).get(1));
					Number label2df=new Number(weiyi11, rowStartnum,missonwe+sum9list[m],formatNum);
					sheet.addCell(label2df);
					Number labelbg=new Number(weiyi11+1, rowStartnum,Arith.div((missonwe+sum9list[m]), (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelbg);
					missionwesum+=missonwe+sum9list[m];
					weiyi11+=2;
				}
				Number labelnLabeldbg=new Number(25, rowStartnum,missionwesum,formatNum);
				sheet.addCell(labelnLabeldbg);
				Number labesae=new Number(26, rowStartnum,Arith.div(missionsum,personMonthTotal),formatNum);
				sheet.addCell(labesae);
				Number labeswe=new Number(27, rowStartnum,Arith.div(missionsum, sumtotal4, 4)*100,formatNum);
				sheet.addCell(labeswe);
				rowStartnum++;
			
			/**
			 * PRC　対応工数计
			 */
			rowStartnum+=2;
			List<Project> listProjectPRC=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
					if ((Parma.strProjectClientName[1]).equals(listProject.get(i).getProjectClientName())) {
						listProjectPRC.add(listProject.get(i));
					}
			}
			double sumtotalPRC1=0.0f;
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
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double tempAllTime1 = 0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (allMonthListManHourDtoList.get(m).get(j).getCategory().equals(Parma.strCategoryName[i])) {
							for (int j2 = 0; j2 < listProjectPRC.size(); j2++) {
								if (allMonthListManHourDtoList.get(m).get(j).getProjectID()==listProjectPRC.get(j2).getProjectID()) {
									tempAllTime1 = Arith.add(tempAllTime1,allMonthListManHourDtoList.get(m).get(j).getTimes());
								}
							}
						}
					}
					Number label2=new Number(weiyi6, rowStartnum,tempAllTime1,formatNum);
					sheet.addCell(label2);
					Number labelbg=new Number(weiyi6+1, rowStartnum,Arith.div(tempAllTime1, (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelbg);
					sum5temp+=tempAllTime1;
					sumtempToltempPRC[m]+=tempAllTime1;
					weiyi6+=2;
				}
				Number labelnLabeldbgdg=new Number(25, rowStartnum,sum5temp,formatNum);
				sheet.addCell(labelnLabeldbgdg);
				Number labesef=new Number(26, rowStartnum,Arith.div(sum5temp,personMonthTotal),formatNum);
				sheet.addCell(labesef);
				Number labesaed=new Number(27, rowStartnum,Arith.div(sum5temp, sumtotalPRC1, 4)*100,formatNum);
				sheet.addCell(labesaed);
				rowStartnum++;
			}
			int weiyi111=weiyi;
			Label labelnLabelk6wab= new Label(0, rowStartnum, Parma.strHomeTitle[4]+"　计",formatgr);
			sheet.addCell(labelnLabelk6wab);
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				double tempAllTime2 = 0.0;
				tempAllTime2 = Arith.add(tempAllTime2,sumtempToltempPRC[i]);
				Number labelnLabel_d6wabm=new Number(weiyi111, rowStartnum,tempAllTime2,formatgrNum);
				sheet.addCell(labelnLabel_d6wabm);
				Number labelbg=new Number(weiyi111+1, rowStartnum,Arith.div(tempAllTime2, (workDayslist.get(i)*8)),formatgrNum);
				sheet.addCell(labelbg);
				weiyi111+=2;
			} 
			Number labelnLabelk6wa=new Number(25, rowStartnum, sumtotalPRC1,formatgrNum);
			sheet.addCell(labelnLabelk6wa);  
			Number labesef=new Number(26, rowStartnum,Arith.div(sumtotalPRC1,personMonthTotal),formatgrNum);
			sheet.addCell(labesef);
			Number labesaed=new Number(27, rowStartnum,Arith.div(sumtotalPRC1, sumtotalPRC1, 4)*100,formatgrNum);
			sheet.addCell(labesaed);
			rowStartnum++;
			/**
			 * PRC开发工数类别明细
			 */
			double sumtotalPRC4=0.0;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int j = 0; j < listProjectPRC.size(); j++) {
					for (int m = 0; m < allMonthListManHourDtoList.get(i).size(); m++) {
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
			for (int i = 0; i < Parma.strFunction.length; i++) {
				Label label = new Label(0, rowStartnum, Parma.strFunction[i],formato);
				sheet.addCell(label);
				int weiyi8=weiyi;
				double sumtemp=0.0;
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					double tempAllTime = 0.0;
					for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
						for (int j2 = 0; j2 < listProjectPRC.size(); j2++) {
							if (allMonthListManHourDtoList.get(j).get(m).getProjectID()==(listProjectPRC.get(j2).getProjectID())) {
								if (("开发工数").equals(allMonthListManHourDtoList.get(j).get(m).getCategory())){
									if ((Parma.strFunction[i]).equals(listProjectPRC.get(j2).getFunction())) {
										tempAllTime = Arith.add(tempAllTime,allMonthListManHourDtoList.get(j).get(m).getTimes());
									}
								}
							}
						}
					}
					Number label2=new Number(weiyi8, rowStartnum,tempAllTime,formatNum);
					sheet.addCell(label2);
					Number labelbg=new Number(weiyi8+1, rowStartnum,Arith.div(tempAllTime, (workDayslist.get(j)*8)),formatNum);
					sheet.addCell(labelbg);
					sumtemp+=tempAllTime;
					sumtempToltempPRC1[j]+=tempAllTime;
					weiyi8+=2;
				}
				Number labelnLabeld4=new Number(25, rowStartnum,sumtemp,formatNum);
				sheet.addCell(labelnLabeld4);
				Number labekil=new Number(26, rowStartnum,Arith.div(sumtemp,personMonthTotal),formatNum);
				sheet.addCell(labekil);
				Number labesbth=new Number(27, rowStartnum,Arith.div(sumtemp, sumtotalPRC4, 4)*100,formatNum);
				sheet.addCell(labesbth);
				rowStartnum++;
			}
			Label labelnLabelu7wab= new Label(0, rowStartnum, Parma.strHomeTitle[5]+"　计",formatgr);
			sheet.addCell(labelnLabelu7wab);
			int weiyi19=weiyi;
			for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
				Number labelnLabel_d7wabm=new Number(weiyi19, rowStartnum,sumtempToltempPRC1[j],formatgrNum);
				sheet.addCell(labelnLabel_d7wabm);
				Number labelbg=new Number(weiyi19+1, rowStartnum,Arith.div(sumtempToltempPRC1[j], (workDayslist.get(j)*8)),formatgrNum);
				sheet.addCell(labelbg);
				weiyi19+=2;
			}
			Number labelnLabelu7wa=new Number(25, rowStartnum, sumtotalPRC4,formatgrNum);
			sheet.addCell(labelnLabelu7wa);   
			Number labekil=new Number(26, rowStartnum,Arith.div(sumtotalPRC4,personMonthTotal),formatgrNum);
			sheet.addCell(labekil);
			Number labesbth=new Number(27, rowStartnum,Arith.div(sumtotalPRC4, sumtotalPRC4, 4)*100,formatgrNum);
			sheet.addCell(labesbth);
			rowStartnum++;
			
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
				Label label = new Label(0, rowStartnum, Parma.strDevelopWorkNum[0]+"工数",formato);
				sheet.addCell(label);
				int weiyi101=weiyi;
				double sumtemp=0.0;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					Number label2=new Number(weiyi101, rowStartnum, listCTDCOEDevelopAdditional.get(m).get(2),formatNum);
					sheet.addCell(label2);
					Number labelbg=new Number(weiyi101+1, rowStartnum,Arith.div(listCTDCOEDevelopAdditional.get(m).get(2), (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelbg);
					sumtemp+=listCTDCOEDevelopAdditional.get(m).get(2);
					weiyi101+=2;
				}
				double tempAllTime = 0.0;
				tempAllTime = Arith.add(tempAllTime,sumtemp);
				Number labelnLabeld4=new Number(25, rowStartnum,tempAllTime,formatNum);
				sheet.addCell(labelnLabeld4);
				Number labeac=new Number(26, rowStartnum,Arith.div(tempAllTime,personMonthTotal),formatNum);
				sheet.addCell(labeac);
				Number labeasz=new Number(27, rowStartnum,Arith.div(tempAllTime, sumtotalPRC4, 4)*100,formatNum);
				sheet.addCell(labeasz);
				rowStartnum++;
				
				Label labeldf = new Label(0, rowStartnum, Parma.strDevelopWorkNum[1]+"工数",formato);
				sheet.addCell(labeldf);
				int weiyi1011=weiyi;
				double sumtemp1=0.0;
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					Number label2=new Number(weiyi1011, rowStartnum, listCTDCOEDevelopAdditional.get(m).get(3)+sum10list[m],formatNum);
					sheet.addCell(label2);
					Number labelbg=new Number(weiyi1011+1, rowStartnum,Arith.div((listCTDCOEDevelopAdditional.get(m).get(3)+sum10list[m]), (workDayslist.get(m)*8)),formatNum);
					sheet.addCell(labelbg);
					sumtemp1+=listCTDCOEDevelopAdditional.get(m).get(3)+sum10list[m];
					weiyi1011+=2;
				}
				double tempAllTimetemp = 0.0;
				tempAllTimetemp = Arith.add(tempAllTimetemp,sumtemp1);
				Number labelnLabeld4m=new Number(25, rowStartnum,tempAllTimetemp,formatNum);
				sheet.addCell(labelnLabeld4m);
				Number labesr=new Number(26, rowStartnum,Arith.div(tempAllTimetemp,personMonthTotal),formatNum);
				sheet.addCell(labesr);
				Number labebtgz=new Number(27, rowStartnum,Arith.div(tempAllTimetemp, sumtotalPRC4, 4)*100,formatNum);
				sheet.addCell(labebtgz);
				rowStartnum++;
				/**
				 * OUTOUT　対応工数计
				 */
				rowStartnum+=2;
				List<Project> listProjectOUT=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
						if ((Parma.strProjectClientName[2]).equals(listProject.get(i).getProjectClientName())) {
							listProjectOUT.add(listProject.get(i));
						}
				}
				double sumtotalOUT1=0.0f;
				for (int i = 0; i < listProjectOUT.size(); i++) {
					for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
						for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
							if (listProjectOUT.get(i).getProjectID()==allMonthListManHourDtoList.get(j).get(m).getProjectID()) {
								sumtotalOUT1 = Arith.add(sumtotalOUT1,allMonthListManHourDtoList.get(j).get(m).getTimes());
							}
						}
					}
				}
				Label labelnh= new Label(0, rowStartnum, Parma.strHomeTitle[7],formato);
				sheet.addCell(labelnh);
				for(int i=1;i<=Parma.strmonth.length;i++)
				{
					if (i%2==1) {
						Label labelhi = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
						sheet.addCell(labelhi);
					}
					else {
						Label labelhi = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
						sheet.addCell(labelhi);
					}
				}
				rowStartnum++;
				double[] sumtempToltempout=new double[allMonthListManHourDtoList.size()];
				for (int i = 0; i < Parma.strCategoryName.length; i++) {
					Label labelrty = new Label(0, rowStartnum, Parma.strCategoryName[i],formato);
					sheet.addCell(labelrty);
					double sum5temp=0.0;
					int weiyi6=weiyi;
					for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
						double tempAllTime1 = 0.0;
						for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
							if (allMonthListManHourDtoList.get(m).get(j).getCategory().equals(Parma.strCategoryName[i])) {
								for (int j2 = 0; j2 < listProjectOUT.size(); j2++) {
									if (allMonthListManHourDtoList.get(m).get(j).getProjectID()==listProjectOUT.get(j2).getProjectID()) {
										tempAllTime1 = Arith.add(tempAllTime1,allMonthListManHourDtoList.get(m).get(j).getTimes());
									}
								}
							}
						}
						Number label2=new Number(weiyi6, rowStartnum,tempAllTime1,formatNum);
						sheet.addCell(label2);
						Number labelbg=new Number(weiyi6+1, rowStartnum,Arith.div(tempAllTime1, (workDayslist.get(m)*8)),formatNum);
						sheet.addCell(labelbg);
						sum5temp+=tempAllTime1;
						sumtempToltempout[m]+=tempAllTime1;
						weiyi6+=2;
					}
					Number labelnLabeldbgdg=new Number(25, rowStartnum, sum5temp,formatNum);
					sheet.addCell(labelnLabeldbgdg);
					Number labedtr=new Number(26, rowStartnum,Arith.div(sum5temp,personMonthTotal),formatNum);
					sheet.addCell(labedtr);
					Number labebserf=new Number(27, rowStartnum,Arith.div(sum5temp, sumtotalOUT1, 4)*100,formatNum);
					sheet.addCell(labebserf);
					rowStartnum++;
				}
				int weiyirty=weiyi;
				Label labelnLabeldr= new Label(0, rowStartnum, Parma.strHomeTitle[7]+"　计",formatgr);
				sheet.addCell(labelnLabeldr);
				for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
					double tempAllTime2 = 0.0;
					tempAllTime2 = Arith.add(tempAllTime2,sumtempToltempout[i]);
					Number labelnLabel_d6wabm=new Number(weiyirty, rowStartnum,tempAllTime2,formatgrNum);
					sheet.addCell(labelnLabel_d6wabm); 
					Number labelbg=new Number(weiyirty+1, rowStartnum,Arith.div(tempAllTime2, (workDayslist.get(i)*8)),formatgrNum);
					sheet.addCell(labelbg);
					weiyirty+=2;
				} 
				Number labelnLabeldy=new Number(25, rowStartnum, sumtotalOUT1,formatgrNum);
				sheet.addCell(labelnLabeldy);  
				Number labedtr=new Number(26, rowStartnum,Arith.div(sumtotalOUT1,personMonthTotal),formatgrNum);
				sheet.addCell(labedtr);
				Number labebserf=new Number(27, rowStartnum,Arith.div(sumtotalOUT1, sumtotalOUT1, 4)*100,formatgrNum);
				sheet.addCell(labebserf);
				rowStartnum++;
				/**
				 * OUTOUT开发工数类别明细
				 */
				double sumtotalOUT4=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
					for (int j = 0; j < listProjectOUT.size(); j++) {
						for (int m = 0; m < allMonthListManHourDtoList.get(i).size(); m++) {
							if (allMonthListManHourDtoList.get(i).get(m).getProjectID()==(listProjectOUT.get(j).getProjectID())) {
								if (Parma.strCategoryName[2].equals(allMonthListManHourDtoList.get(i).get(m).getCategory())) {
									sumtotalOUT4 = Arith.add(sumtotalOUT4,allMonthListManHourDtoList.get(i).get(m).getTimes());
								}
							}
						}
					}
				}
				rowStartnum+=2;
				Label labelkl= new Label(0, rowStartnum, Parma.strHomeTitle[8],formato);
				sheet.addCell(labelkl);
				for(int i=1;i<=Parma.strmonth.length;i++)
				{
					if (i%2==1) {
						Label labelhi = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
						sheet.addCell(labelhi);
					}
					else {
						Label labelhi = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
						sheet.addCell(labelhi);
					}
				}
				rowStartnum++;
				double[] sumtempToltempOUT1=new double[allMonthListManHourDtoList.size()];
				for (int i = 0; i < Parma.strFunction.length; i++) {
					Label labeldt = new Label(0, rowStartnum, Parma.strFunction[i],formato);
					sheet.addCell(labeldt);
					int weiyi8=weiyi;
					double sumtempfr=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
						double tempAllTimeko = 0.0;
						for (int m = 0; m < allMonthListManHourDtoList.get(j).size(); m++) {
							for (int j2 = 0; j2 < listProjectOUT.size(); j2++) {
								if (allMonthListManHourDtoList.get(j).get(m).getProjectID()==(listProjectOUT.get(j2).getProjectID())) {
									if (("开发工数").equals(allMonthListManHourDtoList.get(j).get(m).getCategory())){
										if ((Parma.strFunction[i]).equals(listProjectOUT.get(j2).getFunction())) {
											tempAllTimeko = Arith.add(tempAllTimeko,allMonthListManHourDtoList.get(j).get(m).getTimes());
										}
									}
								}
							}
						}
						Number label2=new Number(weiyi8, rowStartnum,tempAllTimeko,formatNum);
						sheet.addCell(label2);
						Number labelbg=new Number(weiyi8+1, rowStartnum,Arith.div(tempAllTimeko, (workDayslist.get(j)*8)),formatNum);
						sheet.addCell(labelbg);
						sumtempfr+=tempAllTimeko;
						sumtempToltempOUT1[j]+=tempAllTimeko;
						weiyi8+=2;
					}
					Number labelnfrt=new Number(25, rowStartnum,sumtempfr,formatNum);
					sheet.addCell(labelnfrt);
					Number labedsde=new Number(26, rowStartnum,Arith.div(sumtempfr,personMonthTotal),formatNum);
					sheet.addCell(labedsde);
					Number labebsgffe=new Number(27, rowStartnum,Arith.div(sumtempfr, sumtotalOUT4, 4)*100,formatNum);
					sheet.addCell(labebsgffe);
					rowStartnum++;
				}
				Label labeldty= new Label(0, rowStartnum, Parma.strHomeTitle[8]+"　计",formatgr);
				sheet.addCell(labeldty);
				int weiyi19rhu=weiyi;
				for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
					Number labelnLabel_d7wabm=new Number(weiyi19rhu, rowStartnum,sumtempToltempOUT1[j],formatgrNum);
					sheet.addCell(labelnLabel_d7wabm); 
					Number labelbg=new Number(weiyi19rhu+1, rowStartnum,Arith.div(sumtempToltempOUT1[j], (workDayslist.get(j)*8)),formatgrNum);
					sheet.addCell(labelbg);
					weiyi19rhu+=2;
				}
				Number labelnLddty=new Number(25, rowStartnum, sumtotalOUT4,formatgrNum);
				sheet.addCell(labelnLddty);
				Number labedsde=new Number(26, rowStartnum,Arith.div(sumtotalOUT4,personMonthTotal),formatgrNum);
				sheet.addCell(labedsde);
				Number labebsgffe=new Number(27, rowStartnum,Arith.div(sumtotalOUT4, sumtotalOUT4, 4)*100,formatgrNum);
				sheet.addCell(labebsgffe);
				rowStartnum++;
				
				/**
				 * OUTOUT开发工数明细
				 */
				rowStartnum+=2;
				Label labelfy= new Label(0, rowStartnum, Parma.strHomeTitle[9],formato);
				sheet.addCell(labelfy);
				for(int i=1;i<=Parma.strmonth.length;i++)
				{
					if (i%2==1) {
						Label labelhi = new Label(i, rowStartnum, Parma.strmonth[i-1],formato);
						sheet.addCell(labelhi);
					}
					else {
						Label labelhi = new Label(i, rowStartnum, Parma.strmonth[i-1],formatPerson);
						sheet.addCell(labelhi);
					}
				}
				rowStartnum++;
					Label labelnk = new Label(0, rowStartnum, Parma.strDevelopWorkNum[0]+"工数",formato);
					sheet.addCell(labelnk);
					int weiyicf=weiyi;
					double sumtempny=0.0;
					for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
						Number label2=new Number(weiyicf, rowStartnum, listCTDCOEDevelopAdditional.get(m).get(4),formatNum);
						sheet.addCell(label2);
						Number labelbg=new Number(weiyicf+1, rowStartnum,Arith.div(listCTDCOEDevelopAdditional.get(m).get(4), (workDayslist.get(m)*8)),formatNum);
						sheet.addCell(labelbg);
						sumtempny+=listCTDCOEDevelopAdditional.get(m).get(4);
						weiyicf+=2;
					}
					double tempAllTimemny = 0.0;
					tempAllTimemny = Arith.add(tempAllTimemny,sumtempny);
					Number labelndfr=new Number(25, rowStartnum,tempAllTimemny,formatNum);
					sheet.addCell(labelndfr);
					Number labedsdee=new Number(26, rowStartnum,Arith.div(tempAllTimemny,personMonthTotal),formatNum);
					sheet.addCell(labedsdee);
					Number labebsaere=new Number(27, rowStartnum,Arith.div(tempAllTimemny, sumtotalOUT4, 4)*100,formatNum);
					sheet.addCell(labebsaere);
					rowStartnum++;
					
					Label labela = new Label(0, rowStartnum, Parma.strDevelopWorkNum[1]+"工数",formato);
					sheet.addCell(labela);
					int weiyissd=weiyi;
					double sumtemprt=0.0;
					for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
						Number label2=new Number(weiyissd, rowStartnum, listCTDCOEDevelopAdditional.get(m).get(5),formatNum);
						sheet.addCell(label2);
						Number labelbg=new Number(weiyissd+1, rowStartnum,Arith.div(listCTDCOEDevelopAdditional.get(m).get(5), (workDayslist.get(m)*8)),formatNum);
						sheet.addCell(labelbg);
						sumtemprt+=listCTDCOEDevelopAdditional.get(m).get(5);
						weiyissd+=2;
					}
					double tempAllTimetft = 0.0;
					tempAllTimetft = Arith.add(tempAllTimetft,sumtemprt);
					Number labelsw=new Number(25, rowStartnum,tempAllTimetft,formatNum);
					sheet.addCell(labelsw);
					Number labas=new Number(26, rowStartnum,Arith.div(tempAllTimetft,personMonthTotal),formatNum);
					sheet.addCell(labas);
					Number labevfr=new Number(27, rowStartnum,Arith.div(tempAllTimetft, sumtotalPRC4, 4)*100,formatNum);
					sheet.addCell(labevfr);
					rowStartnum++;
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
			//close file case.
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