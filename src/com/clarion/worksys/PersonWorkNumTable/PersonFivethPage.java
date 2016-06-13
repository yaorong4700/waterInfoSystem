/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数导出第5页
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
import com.clarion.worksys.entity.Task;
import com.clarion.worksys.util.Arith;
import com.clarion.worksys.util.Const.DepartmentEnum;

/**
 * @author chen_weijun
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PersonFivethPage {
/**
 * 	生成个人的（信息）数据
 * @param department  部门名  
 * @param branch  课别名
 * @param listProject  所有项目名称的集合
 * @param year  起始年份
 * @param month  起始月份
 * @param uniqueString  唯一性字符串
 * @param allMonthListManHourDtoList  所有工数数据的集合
 * @param listProjectTasks   所有作业类型的集合
 * @param workDayslist  每个月上班天数的集合
 */
	public void updatePersonWorkNumFivethXLS(String department,String branch,List<Project> listProject,int year, int month,
			String uniqueString,List<List<ManHourDto>> allMonthListManHourDtoList,List<Project_task> listProjectTasks
			                     ,List<Integer> workDayslist) {
		
		try {
			File file = new File(Parma.TEMP_FILESixthAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEFiveAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			String strnameString1=Parma.TEMP_FILEFiveAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//生成的模板存储所在地
			String strnameString2=Parma.TEMP_FILESixthAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetFive = book1.createSheet(department, 4);
			sheetFive.setColumnView(2,36); 
			sheetFive.setColumnView(1,20); 
			sheetFive.setColumnView(3,36);
			
			WritableFont fonttitleor= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
			WritableCellFormat formattitleor=new WritableCellFormat(fonttitleor);
			formattitleor.setAlignment(jxl.format.Alignment.CENTRE); //title deep Alignment.CENTRE
			formattitleor.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formattitleor.setWrap(true);
			formattitleor.setBackground(Colour.SKY_BLUE);
			formattitleor.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatNum=new WritableCellFormat(fonttitleor);
			formatNum.setAlignment(jxl.format.Alignment.RIGHT); //data deep green left set
			formatNum.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatNum.setWrap(true);
			formatNum.setBackground(Colour.SKY_BLUE);
			formatNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatgr=new WritableCellFormat(fonttitleor); //text Alignment.CENTRE
			formatgr.setAlignment(jxl.format.Alignment.CENTRE); 
			formatgr.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			formatgr.setBackground(Colour.LIGHT_TURQUOISE);
			formatgr.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatleft=new WritableCellFormat(fonttitleor); //title and data Alignment.LEFT set
			formatleft.setAlignment(jxl.format.Alignment.LEFT);
			formatleft.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			formatleft.setBackground(Colour.LIGHT_TURQUOISE);
			formatleft.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			//CARMAKER 天空蓝
			WritableFont fonttitleor1_1= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.LIGHT_TURQUOISE);//蓝色字体
			WritableCellFormat formatleft1_1=new WritableCellFormat(fonttitleor1_1); //title and data Alignment.LEFT set
			formatleft1_1.setAlignment(jxl.format.Alignment.LEFT);
			formatleft1_1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatleft1_1.setBackground(Colour.LIGHT_TURQUOISE);
			formatleft1_1.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN);
			formatleft1_1.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN);
			formatleft1_1.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.NONE);//上边框不表示
			formatleft1_1.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.NONE);//下边框不表示
			
			//CARMAKER 天空蓝
			WritableCellFormat formatleft1_2=new WritableCellFormat(fonttitleor1_1); //title and data Alignment.LEFT set
			formatleft1_2.setAlignment(jxl.format.Alignment.LEFT);
			formatleft1_2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatleft1_2.setBackground(Colour.LIGHT_TURQUOISE);
			formatleft1_2.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN);
			formatleft1_2.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN);
			formatleft1_2.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.NONE);//上边框不表示
			formatleft1_2.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.THIN);//下边框表示
			
			//用于不合并单元格后的第一行
			WritableCellFormat formatleft1_0=new WritableCellFormat(fonttitleor); //title and data Alignment.LEFT set
			formatleft1_0.setAlignment(jxl.format.Alignment.LEFT);
			formatleft1_0.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatleft1_0.setBackground(Colour.LIGHT_TURQUOISE);
			formatleft1_0.setBorder(jxl.format.Border.LEFT,jxl.format.BorderLineStyle.THIN);
			formatleft1_0.setBorder(jxl.format.Border.RIGHT,jxl.format.BorderLineStyle.THIN);
			formatleft1_0.setBorder(jxl.format.Border.TOP,jxl.format.BorderLineStyle.THIN);//上边框表示
			formatleft1_0.setBorder(jxl.format.Border.BOTTOM,jxl.format.BorderLineStyle.NONE);//下边不框表示

			WritableCellFormat formatright=new WritableCellFormat(fonttitleor); //title and data Alignment.LEFT set
			formatright.setAlignment(jxl.format.Alignment.RIGHT); 
			formatright.setBackground(Colour.LIGHT_TURQUOISE);
			formatright.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			sheetFive.mergeCells(0,0,0,1); 
			int rownum=2;
			Label label = new Label(0, 0, "UNIT単位工数集计",formatgr);
			sheetFive.addCell(label);
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(2, 0, "集计日",formatgr);
			sheetFive.addCell(labelaLabel);
			Label labelaLabelmhk = new Label(3, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formatgr);
			sheetFive.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(2, 1, "对象",formatgr);
			sheetFive.addCell(labelbLabel);
			Label labelbLabelfg = new Label(3, 1, department,formatgr);
			sheetFive.addCell(labelbLabelfg);
			
			double personMonthTotal=0.0f;
			for (int i = 0; i < workDayslist.size(); i++) {
				personMonthTotal+=workDayslist.get(i)*8;
			}
			int weiyi=0;
			if (month>=4&&month<=12) {
				weiyi=(month-4)*2+4;
			}
			if (month>=1&&month<=3) {
				weiyi=month*2+20;
			}
			//List<String> listFirsttaskname=new ArrayList<String>();
			//List<String> listSecondtaskname=new ArrayList<String>();
			//List<String> listThirdtaskname=new ArrayList<String>();
			//List<String> listFourthtaskname=new ArrayList<String>();
			List<Task> listFirstTask=new ArrayList<Task>();
			List<Task> listSecondTask=new ArrayList<Task>();
			List<Task> listThirdTask=new ArrayList<Task>();
			List<Task> listFourthTask=new ArrayList<Task>();
			Task tempTaskInfo = null;
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (("开发工数").equals(listProjectTasks.get(i).getCategory())) {
					//listFirsttaskname.add(listProjectTasks.get(i).getTask());
					tempTaskInfo = new Task();
					tempTaskInfo.setVal(listProjectTasks.get(i).getTaskID().toString());
					tempTaskInfo.setTxt(listProjectTasks.get(i).getTask());
					listFirstTask.add(tempTaskInfo);
				}
			}
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (("RFI和RFQ").equals(listProjectTasks.get(i).getCategory())) {
					//listSecondtaskname.add(listProjectTasks.get(i).getTask());
					tempTaskInfo = new Task();
					tempTaskInfo.setVal(listProjectTasks.get(i).getTaskID().toString());
					tempTaskInfo.setTxt(listProjectTasks.get(i).getTask());
					listSecondTask.add(tempTaskInfo);
				}
			}
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (("量产后不具合对应").equals(listProjectTasks.get(i).getCategory())) {
					//listThirdtaskname.add(listProjectTasks.get(i).getTask());
					tempTaskInfo = new Task();
					tempTaskInfo.setVal(listProjectTasks.get(i).getTaskID().toString());
					tempTaskInfo.setTxt(listProjectTasks.get(i).getTask());
					listThirdTask.add(tempTaskInfo);
				}
			}
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (("先行开发").equals(listProjectTasks.get(i).getCategory())) {
					//listFourthtaskname.add(listProjectTasks.get(i).getTask());
					tempTaskInfo = new Task();
					tempTaskInfo.setVal(listProjectTasks.get(i).getTaskID().toString());
					tempTaskInfo.setTxt(listProjectTasks.get(i).getTask());
					listFourthTask.add(tempTaskInfo);
				}
			}
			/**
			 * 開発工数  CT DCOE OUTOUT
			 */
			
			Label labelaa = new Label(0, rownum, "区分",formatgr);
			sheetFive.addCell(labelaa);
			for (int i = 0; i < Parma.strSheetFourTitle.length; i++) {
				if (i>2&&i%2==1) {
					Label labelaa1 = new Label(i+1, rownum, Parma.strSheetFourTitle[i],formatPerson);
					sheetFive.addCell(labelaa1);
				}
				else {
					Label labelaa1 = new Label(i+1, rownum, Parma.strSheetFourTitle[i],formatgr);
					sheetFive.addCell(labelaa1);
				}
			}
			rownum++;
			for (int i = 2; i < Parma.strSheetFourTitle.length+1; i++) {
				sheetFive.mergeCells(i+1,rownum,i+1,rownum+1);
			}
			double basetotalnum=0.0;
			double categorysumtime1=0.0;
			
			sheetFive.mergeCells(0,rownum,1,rownum+1);
			Label labela1 = new Label(0, rownum, "总工数",formattitleor);
			sheetFive.addCell(labela1);
			Label labela5 = new Label(3, rownum, "Tol",formattitleor);
			sheetFive.addCell(labela5);
			
			int weiyikaifa=weiyi;
			int weiyiRFI=weiyi;
			int weiyiBuJuHe=weiyi;
			int weiyiXXKaiFa=weiyi;
			int weiyiFirst=weiyi;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double basetotalnumtemp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					basetotalnumtemp = Arith.add(basetotalnumtemp,allMonthListManHourDtoList.get(m).get(i).getTimes());
				}
				Number labela2=new Number(weiyiFirst, rownum,basetotalnumtemp,formatNum);
				sheetFive.addCell(labela2);
				Number labelftg=new Number(weiyiFirst+1, rownum, Arith.div(basetotalnumtemp, (workDayslist.get(m)*8)),formatNum);
				sheetFive.addCell(labelftg);
				basetotalnum+=basetotalnumtemp;
				weiyiFirst=weiyiFirst+2;
			}
			
			Number labela3=new Number(28, rownum,basetotalnum,formatNum);
			sheetFive.addCell(labela3);
			Number labelgy=new Number(29, rownum,Arith.div(basetotalnum,personMonthTotal),formatNum);
			sheetFive.addCell(labelgy);
			Number labelef=new Number(30, rownum,Arith.div(basetotalnum, basetotalnum, 4)*100,formatNum);
			sheetFive.addCell(labelef);
			rownum++;
			rownum++;
			sheetFive.mergeCells(0,rownum,2,rownum);
			
			Label labelab = new Label(0, rownum, "开发工数　计",formattitleor);
			sheetFive.addCell(labelab);
			Label labelab12 = new Label(3, rownum, "Tol",formattitleor);
			sheetFive.addCell(labelab12);
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double categorysumtime1temp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("开发工数").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						categorysumtime1temp = Arith.add(categorysumtime1temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
				categorysumtime1+=categorysumtime1temp;
				Number labelac=new Number(weiyikaifa, rownum,categorysumtime1temp,formatNum);
				sheetFive.addCell(labelac);
				Number labelftg=new Number(weiyikaifa+1, rownum, Arith.div(categorysumtime1temp, (workDayslist.get(m)*8)),formatNum);
				sheetFive.addCell(labelftg);
				weiyikaifa=weiyikaifa+2;
			}
			Number labelad=new Number(28, rownum,categorysumtime1,formatNum);
			sheetFive.addCell(labelad);
			Number labeledr=new Number(29, rownum,Arith.div(categorysumtime1,personMonthTotal),formatNum);
			sheetFive.addCell(labeledr);
			Number labelfrtf=new Number(30, rownum,Arith.div(categorysumtime1, basetotalnum, 4)*100,formatNum);
			sheetFive.addCell(labelfrtf);
			rownum++;
			for (int m = 0; m < Parma.strProjectClientNameDevelopPage.length; m++) {
				List<Project> listProjectCT=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
					if (("开发工数").equals(listProject.get(i).getCategory())) {
						if ((Parma.strProjectClientNameDevelopPage[m]).equals(listProject.get(i).getProjectClientName())) {
							listProjectCT.add(listProject.get(i));
						}
					}
				}
				for (int k = 0; k < listProjectCT.size(); k++) {
					double sumtotalCT=0.0;
					//if (("工程设计部").equals(department)&&("185 工程设计部CT").equals(listProjectCT.get(k).getProjectName())){
					if (DepartmentEnum.GCSJB.getName().equals(department)&&("185 工程设计部CT").equals(listProjectCT.get(k).getProjectName())){
						double[] sumtempTol=new double[allMonthListManHourDtoList.size()];	
						for (int i = 0; i < Parma.strAdditionalProjDesignerCT.length; i++) {
								//2014/10/10DEL Label labelab1ab = new Label(2, rownum, Parma.strAdditionalProjDesignerCT[i],formatleft);
								Label labelab1ab = new Label(3, rownum, Parma.strAdditionalProjDesignerCT[i],formatleft);
								sheetFive.addCell(labelab1ab);
								double sumtimetemp=0.0;
								int weiyitemp=weiyi;
								for (int mk = 0; mk < allMonthListManHourDtoList.size(); mk++) {
									double sumtime=0.0;
									for (int j = 0; j < allMonthListManHourDtoList.get(mk).size(); j++) {
										if (("开发工数").equals(allMonthListManHourDtoList.get(mk).get(j).getCategory())) {
											if (listProjectCT.get(k).getProjectName().equals(allMonthListManHourDtoList.get(mk).get(j).getProjectName())) {
												if (Parma.strAdditionalProjDesignerCT[i].equals(allMonthListManHourDtoList.get(mk).get(j).getTask())) {
													sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(mk).get(j).getTimes());
												}
											}
										}
									}
									sumtimetemp+=sumtime;
									Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
									sheetFive.addCell(labelac3);
									Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(mk)*8)),formatright);
									sheetFive.addCell(labelftg);
									sumtempTol[mk]+=sumtime;
									weiyitemp=weiyitemp+2;
								}
								Number labeladb23=new Number(28, rownum,sumtimetemp,formatright);
								sheetFive.addCell(labeladb23);
								Number labelbh=new Number(29, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
								sheetFive.addCell(labelbh);
								Number labelju=new Number(30, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
								sheetFive.addCell(labelju);
								rownum++;
							}
							int weiyite=weiyi;
							Label labelab12a = new Label(3, rownum, "Tol",formatleft);
							sheetFive.addCell(labelab12a);
							for (int i = 0; i < sumtempTol.length; i++) {
								Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatNum);
								sheetFive.addCell(labelaca);
								Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatleft);
								sheetFive.addCell(labelftg);
								sumtotalCT+=sumtempTol[i];
								weiyite=weiyite+2;
							}
							Number labelada=new Number(28, rownum, sumtotalCT,formatNum);
							sheetFive.addCell(labelada);
							Number labelbh=new Number(29, rownum,Arith.div(sumtotalCT,personMonthTotal),formatNum);
							sheetFive.addCell(labelbh);
							Number labelju=new Number(30, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatNum);
							sheetFive.addCell(labelju);
							//PJ名・项目名去除合并单元格，第一行表示，第一行以下用背景色同色的字体表示（为了筛选）
							//sheetFive.mergeCells(2,rownum-Parma.strAdditionalProjDesignerCT.length,2,rownum);
							//Label labelaea2 = new Label(2, rownum-Parma.strAdditionalProjDesignerCT.length, listProjectCT.get(k).getProjectName(),formatleft);
							//sheetFive.addCell(labelaea2);
							for (int k1 = (rownum-Parma.strAdditionalProjDesignerCT.length); k1 < rownum+1; k1++) {
								if(k1 == (rownum-Parma.strAdditionalProjDesignerCT.length)){
									Label labelaea2 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_0);
									sheetFive.addCell(labelaea2);
								}else if(k1 != rownum){
									Label labelaea2 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_1);
									sheetFive.addCell(labelaea2);
								}else{
									Label labelaea2 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_2);
									sheetFive.addCell(labelaea2);
								}
							}
							
							if (("").equals(listProjectCT.get(k).getCarMaker())||listProjectCT.get(k).getCarMaker()==null) {
								//车厂去除合并单元格，第一行表示，第一行以下用背景色同色的字体表示（为了筛选）
								//sheetFive.mergeCells(1,rownum-Parma.strAdditionalProjDesignerCT.length,1,rownum);
								//Label labelaea1 = new Label(1,rownum-Parma.strAdditionalProjDesignerCT.length,"未知",formatleft);
								//sheetFive.addCell(labelaea1);
								for (int k1 = (rownum-Parma.strAdditionalProjDesignerCT.length); k1 < rownum+1; k1++) {
									if(k1 == (rownum-Parma.strAdditionalProjDesignerCT.length)){
										Label labelaea1 = new Label(1,k1,"未知",formatleft1_0);
										sheetFive.addCell(labelaea1);
									}else if(k1 != rownum){
										Label labelaea1 = new Label(1,k1,"未知",formatleft1_1);
										sheetFive.addCell(labelaea1);
									}else{
										Label labelaea1 = new Label(1,k1,"未知",formatleft1_2);
										sheetFive.addCell(labelaea1);
									}
								}
							}
							else {
								//sheetFive.mergeCells(1,rownum-Parma.strAdditionalProjDesignerCT.length,1,rownum);
								//Label labelaea1 = new Label(1, rownum-Parma.strAdditionalProjDesignerCT.length,listProjectCT.get(k).getCarMaker(),formatleft);
								//sheetFive.addCell(labelaea1);
								for (int k1 = (rownum-Parma.strAdditionalProjDesignerCT.length); k1 < rownum+1; k1++) {
									if(k1 == (rownum-Parma.strAdditionalProjDesignerCT.length)){
										Label labelaea1 = new Label(1,k1,listProjectCT.get(k).getCarMaker(),formatleft1_0);
										sheetFive.addCell(labelaea1);
									}else if(k1 != rownum){
										Label labelaea1 = new Label(1,k1,listProjectCT.get(k).getCarMaker(),formatleft1_1);
										sheetFive.addCell(labelaea1);
									}else{
										Label labelaea1 = new Label(1,k1,listProjectCT.get(k).getCarMaker(),formatleft1_2);
										sheetFive.addCell(labelaea1);
									}
								}
							}
							sheetFive.mergeCells(0,rownum-Parma.strAdditionalProjDesignerCT.length,0,rownum);
							Label labela1121 = new Label(0,rownum-Parma.strAdditionalProjDesignerCT.length,Parma.strProjectClientNameDevelopPage[m],formatgr);
							sheetFive.addCell(labela1121);
							rownum++;
						
					//}else if (("工程设计部").equals(department)&&("185 工程设计部DCOE").equals(listProjectCT.get(k).getProjectName())) {
					}else if (DepartmentEnum.GCSJB.getName().equals(department)&&("185 工程设计部DCOE").equals(listProjectCT.get(k).getProjectName())) {
						double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
						for (int i = 0; i < Parma.strAdditionalProjDesignerDCOE.length; i++) {
							Label labelab1ab = new Label(3, rownum, Parma.strAdditionalProjDesignerDCOE[i],formatleft);
							sheetFive.addCell(labelab1ab);
							double sumtimetemp=0.0;
							int weiyitemp=weiyi;
							for (int mk = 0; mk < allMonthListManHourDtoList.size(); mk++) {
								double sumtime=0.0;
								for (int j = 0; j < allMonthListManHourDtoList.get(mk).size(); j++) {
									if (("开发工数").equals(allMonthListManHourDtoList.get(mk).get(j).getCategory())) {
										if (listProjectCT.get(k).getProjectName().equals(allMonthListManHourDtoList.get(mk).get(j).getProjectName())) {
											if (Parma.strAdditionalProjDesignerDCOE[i].equals(allMonthListManHourDtoList.get(mk).get(j).getTask())) {
												sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(mk).get(j).getTimes());
											}
										}
									}
								}
								sumtimetemp+=sumtime;
								Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
								sheetFive.addCell(labelac3);
								Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(mk)*8)),formatright);
								sheetFive.addCell(labelftg);
								sumtempTol[mk]+=sumtime;
								weiyitemp=weiyitemp+2;
							}
							Number labeladb23=new Number(28, rownum,sumtimetemp,formatright);
							sheetFive.addCell(labeladb23);
							Number labelbh=new Number(29, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
							sheetFive.addCell(labelbh);
							Number labelju=new Number(30, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
							sheetFive.addCell(labelju);
							rownum++;
						}
						int weiyite=weiyi;
						Label labelab12a = new Label(3, rownum, "Tol",formatleft);
						sheetFive.addCell(labelab12a);
						for (int i = 0; i < sumtempTol.length; i++) {
							Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatNum);
							sheetFive.addCell(labelaca);
							Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatNum);
							sheetFive.addCell(labelftg);
							sumtotalCT+=sumtempTol[i];
							weiyite=weiyite+2;
						}
						Number labelada=new Number(28, rownum, sumtotalCT,formatNum);
						sheetFive.addCell(labelada);
						Number labelbh=new Number(29, rownum,Arith.div(sumtotalCT,personMonthTotal),formatNum);
						sheetFive.addCell(labelbh);
						Number labelju=new Number(30, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatNum);
						sheetFive.addCell(labelju);
						//sheetFive.mergeCells(2,rownum-Parma.strAdditionalProjDesignerDCOE.length,2,rownum);
						//Label labelaea2 = new Label(2, rownum-Parma.strAdditionalProjDesignerDCOE.length, listProjectCT.get(k).getProjectName(),formatleft);
						//sheetFive.addCell(labelaea2);
						for (int k1 = (rownum-Parma.strAdditionalProjDesignerDCOE.length); k1 < rownum+1; k1++) {
							if(k1 == (rownum-Parma.strAdditionalProjDesignerDCOE.length)){
								Label labelaea2 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_0);
								sheetFive.addCell(labelaea2);
							}else if(k1 != rownum){
								Label labelaea2 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_1);
								sheetFive.addCell(labelaea2);
							}else{
								Label labelaea2 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_2);
								sheetFive.addCell(labelaea2);
							}
						}

						if (("").equals(listProjectCT.get(k).getCarMaker())||listProjectCT.get(k).getCarMaker()==null) {
							//sheetFive.mergeCells(1,rownum-Parma.strAdditionalProjDesignerDCOE.length,1,rownum);
							//Label labelaea1 = new Label(1,rownum-Parma.strAdditionalProjDesignerDCOE.length,"未知",formatleft);
							//sheetFive.addCell(labelaea1);
							for (int k1 = (rownum-Parma.strAdditionalProjDesignerDCOE.length); k1 < rownum+1; k1++) {
								if(k1 == (rownum-Parma.strAdditionalProjDesignerDCOE.length)){
									Label labelaea1 = new Label(1,k1,"未知",formatleft1_0);
									sheetFive.addCell(labelaea1);
								}else if(k1 != rownum){
									Label labelaea1 = new Label(1,k1,"未知",formatleft1_1);
									sheetFive.addCell(labelaea1);
								}else{
									Label labelaea1 = new Label(1,k1,"未知",formatleft1_2);
									sheetFive.addCell(labelaea1);
								}
							}
						}
						else {
							//sheetFive.mergeCells(1,rownum-Parma.strAdditionalProjDesignerDCOE.length,1,rownum);
							//Label labelaea1 = new Label(1, rownum-Parma.strAdditionalProjDesignerDCOE.length,listProjectCT.get(k).getCarMaker(),formatleft);
							//sheetFive.addCell(labelaea1);
							for (int k1 = (rownum-Parma.strAdditionalProjDesignerDCOE.length); k1 < rownum+1; k1++) {
								if(k1 == (rownum-Parma.strAdditionalProjDesignerDCOE.length)){
									Label labelaea1 = new Label(1,k1,listProjectCT.get(k).getCarMaker(),formatleft1_0);
									sheetFive.addCell(labelaea1);
								}else if(k1 != rownum){
									Label labelaea1 = new Label(1,k1,listProjectCT.get(k).getCarMaker(),formatleft1_1);
									sheetFive.addCell(labelaea1);
								}else{
									Label labelaea1 = new Label(1,k1,listProjectCT.get(k).getCarMaker(),formatleft1_2);
									sheetFive.addCell(labelaea1);
								}
							}
						}
						sheetFive.mergeCells(0,rownum-Parma.strAdditionalProjDesignerDCOE.length,0,rownum);
						Label labela1121 = new Label(0,rownum-Parma.strAdditionalProjDesignerDCOE.length,Parma.strProjectClientNameDevelopPage[m],formatgr);
						sheetFive.addCell(labela1121);
						rownum++;
					}
					else {
					double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
					Task firstTask = new Task();
						for (int i = 0; i < listFirstTask.size(); i++) {
							firstTask = listFirstTask.get(i);
							//Label labelab1ab = new Label(3, rownum, listFirsttaskname.get(i),formatleft);
							Label labelab1ab = new Label(3, rownum, firstTask.getTxt(),formatleft);
							sheetFive.addCell(labelab1ab);
							double sumtimetemp=0.0;
							int weiyitemp=weiyi;
							for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
								double sumtime=0.0;
								for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
									if (("开发工数").equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
										if (listProjectCT.get(k).getProjectName().equals(allMonthListManHourDtoList.get(s).get(j).getProjectName())) {
											//if (listFirsttaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
											if (firstTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
												sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
											}
										}
									}
								}
								sumtimetemp+=sumtime;
								Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
								sheetFive.addCell(labelac3);
								Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
								sheetFive.addCell(labelftg);
								sumtempTol[s]+=sumtime;
								weiyitemp=weiyitemp+2;
							}
							Number labeladb23=new Number(28, rownum,sumtimetemp,formatright);
							sheetFive.addCell(labeladb23);
							Number labelbh=new Number(29, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
							sheetFive.addCell(labelbh);
							Number labelju=new Number(30, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
							sheetFive.addCell(labelju);
							rownum++;
						}
						int weiyite=weiyi;
						Label labelab12a = new Label(3, rownum, "Tol",formatleft);
						sheetFive.addCell(labelab12a);
						for (int i = 0; i < sumtempTol.length; i++) {
							Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatNum);
							sheetFive.addCell(labelaca);
							Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatNum);
							sheetFive.addCell(labelftg);
							sumtotalCT+=sumtempTol[i];
							weiyite=weiyite+2;
						}
						Number labelada=new Number(28, rownum, sumtotalCT,formatNum);
						sheetFive.addCell(labelada);
						Number labelbh=new Number(29, rownum,Arith.div(sumtotalCT,personMonthTotal),formatNum);
						sheetFive.addCell(labelbh);
						Number labelju=new Number(30, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatNum);
						sheetFive.addCell(labelju);
						
						//sheetFive.mergeCells(2,rownum-listFirsttaskname.size(),2,rownum);
						//Label labelaea2 = new Label(2, rownum-listFirsttaskname.size(), listProjectCT.get(k).getProjectName(),formatleft);
						//sheetFive.addCell(labelaea2);
						//for (int k1 = (rownum-listFirsttaskname.size()); k1 < rownum+1; k1++) {
						//	if(k1 == (rownum-listFirsttaskname.size())){
						for (int k1 = (rownum-listFirstTask.size()); k1 < rownum+1; k1++) {
							if(k1 == (rownum-listFirstTask.size())){
								Label labelaea1 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_0);
								sheetFive.addCell(labelaea1);
							}else if(k1 != rownum){
								Label labelaea1 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_1);
								sheetFive.addCell(labelaea1);
							}else{
								Label labelaea1 = new Label(2,k1,listProjectCT.get(k).getProjectName(),formatleft1_2);
								sheetFive.addCell(labelaea1);
							}
						}
						
						if ( ("").equals(listProjectCT.get(k).getCarMaker())||listProjectCT.get(k).getCarMaker()==null) {
							//sheetFive.mergeCells(1,rownum-listFirsttaskname.size(),1,rownum);
							//Label labelaea1 = new Label(1, rownum-listFirsttaskname.size(),"未知",formatleft);
							//sheetFive.addCell(labelaea1);
							//for (int k1 = (rownum-listFirsttaskname.size()); k1 < rownum+1; k1++) {
							//	if(k1 == (rownum-listFirsttaskname.size())){
							for (int k1 = (rownum-listFirstTask.size()); k1 < rownum+1; k1++) {
								if(k1 == (rownum-listFirstTask.size())){
									Label labelaea1 = new Label(1,k1,"未知",formatleft1_0);
									sheetFive.addCell(labelaea1);
								}else if(k1 != rownum){
									Label labelaea1 = new Label(1,k1,"未知",formatleft1_1);
									sheetFive.addCell(labelaea1);
								}else{
									Label labelaea1 = new Label(1,k1,"未知",formatleft1_2);
									sheetFive.addCell(labelaea1);
								}
							}
						}
						else {
							//sheetFive.mergeCells(1,rownum-listFirsttaskname.size(),1,rownum);
							//Label labelaea1 = new Label(1, rownum-listFirsttaskname.size(),listProjectCT.get(k).getCarMaker(),formatleft);
							//sheetFive.addCell(labelaea1);
							//for (int k1 = (rownum-listFirsttaskname.size()); k1 < rownum+1; k1++) {
							//	if(k1 == (rownum-listFirsttaskname.size())){
							for (int k1 = (rownum-listFirstTask.size()); k1 < rownum+1; k1++) {
								if(k1 == (rownum-listFirstTask.size())){
									Label labelaea1 = new Label(1, k1,listProjectCT.get(k).getCarMaker(),formatleft1_0);
									sheetFive.addCell(labelaea1);
								}else if(k1 != rownum){
									Label labelaea1 = new Label(1, k1,listProjectCT.get(k).getCarMaker(),formatleft1_1);
									sheetFive.addCell(labelaea1);
								}else{
									Label labelaea1 = new Label(1,k1,listProjectCT.get(k).getCarMaker(),formatleft1_2);
									sheetFive.addCell(labelaea1);
								}
							}
						}
						sheetFive.mergeCells(0,rownum-listFirstTask.size(),0,rownum);
						Label labela1121 = new Label(0,rownum-listFirstTask.size(),Parma.strProjectClientNameDevelopPage[m],formatgr);
						sheetFive.addCell(labela1121);
						rownum++;
					}
				}
			}
			/**
			 * RFIRFQ  CT DCOE OUTOUT
			 */
			double categorysumtime2=0.0;
			sheetFive.mergeCells(0,rownum,2,rownum);
			Label labelabm = new Label(0, rownum, "RFI和RFQ　计",formattitleor);
			sheetFive.addCell(labelabm);
			Label labelab12m = new Label(3, rownum, "Tol",formattitleor);
			sheetFive.addCell(labelab12m);
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double categorysumtime2temp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("RFI和RFQ").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						categorysumtime2temp = Arith.add(categorysumtime2temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
				categorysumtime2+=categorysumtime2temp;
				Number labelacm=new Number(weiyiRFI, rownum,categorysumtime2temp,formatNum);
				sheetFive.addCell(labelacm);
				Number labelftg=new Number(weiyiRFI+1, rownum, Arith.div(categorysumtime2temp, (workDayslist.get(m)*8)),formatNum);
				sheetFive.addCell(labelftg);
				weiyiRFI=weiyiRFI+2;
			}
			Number labeladm=new Number(28, rownum,categorysumtime2,formatNum);
			sheetFive.addCell(labeladm);
			Number labelbh=new Number(29, rownum,Arith.div(categorysumtime2,personMonthTotal),formatNum);
			sheetFive.addCell(labelbh);
			Number labelju=new Number(30, rownum,Arith.div(categorysumtime2, basetotalnum, 4)*100,formatNum);
			sheetFive.addCell(labelju);
			rownum++;
			
			for (int m = 0; m < Parma.strProjectClientNameDevelopPage.length; m++) {
				List<Project> listRFIRProjectCT=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
					if (("RFI和RFQ").equals(listProject.get(i).getCategory())) {
						if ((Parma.strProjectClientNameDevelopPage[m]).equals(listProject.get(i).getProjectClientName())) {
							listRFIRProjectCT.add(listProject.get(i));
						}
					}
				}
				for (int k = 0; k < listRFIRProjectCT.size(); k++) {
					double sumtotalCT=0.0;
					double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
					Task secondTask = new Task();
					//for (int i = 0; i < listSecondtaskname.size(); i++) {
					for (int i = 0; i < listSecondTask.size(); i++) {
						secondTask = listSecondTask.get(i);
						//Label labelab1ab = new Label(3, rownum, listSecondtaskname.get(i),formatleft);
						Label labelab1ab = new Label(3, rownum, secondTask.getTxt(),formatleft);
						sheetFive.addCell(labelab1ab);
						double sumtimetemp=0.0;
						int weiyitemp=weiyi;
						for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
							double sumtime=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								if (("RFI和RFQ").equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
									if (listRFIRProjectCT.get(k).getProjectName().equals(allMonthListManHourDtoList.get(s).get(j).getProjectName())) {
										//if (listSecondtaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
										if (secondTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
											sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
										}
									}
								}
							}
							sumtimetemp+=sumtime;
							Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
							sheetFive.addCell(labelac3);
							Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
							sheetFive.addCell(labelftg);
							sumtempTol[s]+=sumtime;
							weiyitemp=weiyitemp+2;
						}
						Number labeladb23=new Number(28, rownum,sumtimetemp,formatright);
						sheetFive.addCell(labeladb23);
						Number labeldt=new Number(29, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
						sheetFive.addCell(labeldt);
						Number labelwr=new Number(30, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
						sheetFive.addCell(labelwr);
						rownum++;
					}
					int weiyite=weiyi;
					Label labelab12a = new Label(3, rownum, "Tol",formatleft);
					sheetFive.addCell(labelab12a);
					for (int i = 0; i < sumtempTol.length; i++) {
						Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatNum);
						sheetFive.addCell(labelaca);
						Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatNum);
						sheetFive.addCell(labelftg);
						sumtotalCT+=sumtempTol[i];
						weiyite=weiyite+2;
					}
					Number labelada=new Number(28, rownum,sumtotalCT,formatNum);
					sheetFive.addCell(labelada);
					Number labeldt=new Number(29, rownum,Arith.div(sumtotalCT,personMonthTotal),formatNum);
					sheetFive.addCell(labeldt);
					Number labelwr=new Number(30, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatNum);
					sheetFive.addCell(labelwr);
					
					//sheetFive.mergeCells(2,rownum-listSecondtaskname.size(),2,rownum);
					//Label labelaea2 = new Label(2, rownum-listSecondtaskname.size(), listRFIRProjectCT.get(k).getProjectName(),formatleft);
					//sheetFive.addCell(labelaea2);
					//for (int k1 = (rownum-listSecondtaskname.size()); k1 < rownum+1; k1++) {
					//	if(k1 == (rownum-listSecondtaskname.size())){
					for (int k1 = (rownum-listSecondTask.size()); k1 < rownum+1; k1++) {
						if(k1 == (rownum-listSecondTask.size())){
							Label labelaea2 = new Label(2,k1,listRFIRProjectCT.get(k).getProjectName(),formatleft1_0);
							sheetFive.addCell(labelaea2);
						}else if(k1 != rownum){
							Label labelaea2 = new Label(2,k1,listRFIRProjectCT.get(k).getProjectName(),formatleft1_1);
							sheetFive.addCell(labelaea2);
						}else{
							Label labelaea2 = new Label(2,k1,listRFIRProjectCT.get(k).getProjectName(),formatleft1_2);
							sheetFive.addCell(labelaea2);
						}
					}
					
					
					if ( ("").equals(listRFIRProjectCT.get(k).getCarMaker())||listRFIRProjectCT.get(k).getCarMaker()==null) {
						//sheetFive.mergeCells(1,rownum-listSecondtaskname.size(),1,rownum);
						//Label labelaea1 = new Label(1, rownum-listSecondtaskname.size(),"未知",formatleft);
						//sheetFive.addCell(labelaea1);
						//for (int k1 = (rownum-listSecondtaskname.size()); k1 < rownum+1; k1++) {
						//	if(k1 == (rownum-listSecondtaskname.size())){
						for (int k1 = (rownum-listSecondTask.size()); k1 < rownum+1; k1++) {
							if(k1 == (rownum-listSecondTask.size())){
								Label labelaea1 = new Label(1,k1,"未知",formatleft1_0);
								sheetFive.addCell(labelaea1);
							}else if(k1 != rownum){
								Label labelaea1 = new Label(1,k1,"未知",formatleft1_1);
								sheetFive.addCell(labelaea1);
							}else{
								Label labelaea1 = new Label(1,k1,"未知",formatleft1_2);
								sheetFive.addCell(labelaea1);
							}
						}
					}
					else {
						//sheetFive.mergeCells(1,rownum-listSecondtaskname.size(),1,rownum);
						//Label labelaea1 = new Label(1, rownum-listSecondtaskname.size(),listRFIRProjectCT.get(k).getCarMaker(),formatleft);
						//sheetFive.addCell(labelaea1);
						//for (int k1 = (rownum-listSecondtaskname.size()); k1 < rownum+1; k1++) {
						//	if(k1 == (rownum-listSecondtaskname.size())){
						for (int k1 = (rownum-listSecondTask.size()); k1 < rownum+1; k1++) {
							if(k1 == (rownum-listSecondTask.size())){
								Label labelaea1 = new Label(1,k1,listRFIRProjectCT.get(k).getCarMaker(),formatleft1_0);
								sheetFive.addCell(labelaea1);
							}else if(k1 != rownum){
								Label labelaea1 = new Label(1,k1,listRFIRProjectCT.get(k).getCarMaker(),formatleft1_1);
								sheetFive.addCell(labelaea1);
							}else{
								Label labelaea1 = new Label(1,k1,listRFIRProjectCT.get(k).getCarMaker(),formatleft1_2);
								sheetFive.addCell(labelaea1);
							}
						}
					}
					sheetFive.mergeCells(0,rownum-listSecondTask.size(),0,rownum);
					Label labela1121 = new Label(0,rownum-listSecondTask.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetFive.addCell(labela1121);
					rownum++;
				}
			}
			
			/**
			 * 量产后不具合对应
			 */
			double categorysumtime3=0.0;
			sheetFive.mergeCells(0,rownum,2,rownum);
			Label labelabmnkLabel = new Label(0, rownum, "量产后不具合对应　计",formattitleor);
			sheetFive.addCell(labelabmnkLabel);
			Label labelab12mnk = new Label(3, rownum, "Tol",formattitleor);
			sheetFive.addCell(labelab12mnk);
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double categorysumtime3temp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("量产后不具合对应").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						categorysumtime3temp = Arith.add(categorysumtime3temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
				categorysumtime3+=categorysumtime3temp;
				Number labelacmnk=new Number(weiyiBuJuHe, rownum, categorysumtime3temp,formatNum);
				sheetFive.addCell(labelacmnk);
				Number labelftg=new Number(weiyiBuJuHe+1, rownum, Arith.div(categorysumtime3temp, (workDayslist.get(m)*8)),formatNum);
				sheetFive.addCell(labelftg);
				weiyiBuJuHe=weiyiBuJuHe+2;
			}
			Number labeladmnnk=new Number(28, rownum,categorysumtime3,formatNum);
			sheetFive.addCell(labeladmnnk);
			Number labeldt=new Number(29, rownum,Arith.div(categorysumtime3,personMonthTotal),formatNum);
			sheetFive.addCell(labeldt);
			Number labelwr=new Number(30, rownum,Arith.div(categorysumtime3, basetotalnum, 4)*100,formatNum);
			sheetFive.addCell(labelwr);
			rownum++;
			
			for (int m = 0; m < Parma.strProjectClientNameDevelopPage.length; m++) {
				List<Project> listRFIRProjectCT=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
					if (("量产后不具合对应").equals(listProject.get(i).getCategory())) {
						if ((Parma.strProjectClientNameDevelopPage[m]).equals(listProject.get(i).getProjectClientName())) {
							listRFIRProjectCT.add(listProject.get(i));
						}
					}
				}
				for (int k = 0; k < listRFIRProjectCT.size(); k++) {
					double sumtotalCT=0.0;
					double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
					Task thirdTask = new Task();
					//for (int i = 0; i < listThirdtaskname.size(); i++) {
					for (int i = 0; i < listThirdTask.size(); i++) {
						thirdTask = listThirdTask.get(i);
						//Label labelab1ab = new Label(3, rownum, listThirdtaskname.get(i),formatleft);
						Label labelab1ab = new Label(3, rownum, thirdTask.getTxt(),formatleft);
						sheetFive.addCell(labelab1ab);
						double sumtimetemp=0.0;
						int weiyitemp=weiyi;
						for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
							double sumtime=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								if (("量产后不具合对应").equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
									if (listRFIRProjectCT.get(k).getProjectName().equals(allMonthListManHourDtoList.get(s).get(j).getProjectName())) {
										//if (Parma.strNonCooperation[i].equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
										//if (listThirdtaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
										if (thirdTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
											sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
										}
									}
								}
							}
							sumtimetemp+=sumtime;
							Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
							sheetFive.addCell(labelac3);
							Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
							sheetFive.addCell(labelftg);
							sumtempTol[s]+=sumtime;
							weiyitemp=weiyitemp+2;
						}
						Number labeladb23=new Number(28, rownum,sumtimetemp,formatright);
						sheetFive.addCell(labeladb23);
						Number labelbrt=new Number(29, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
						sheetFive.addCell(labelbrt);
						Number labeldty=new Number(30, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
						sheetFive.addCell(labeldty);
						rownum++;
					}
					int weiyite=weiyi;
					Label labelab12a = new Label(3, rownum, "Tol",formatleft);
					sheetFive.addCell(labelab12a);
					for (int i = 0; i < sumtempTol.length; i++) {
						Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatNum);
						sheetFive.addCell(labelaca);
						Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatNum);
						sheetFive.addCell(labelftg);
						sumtotalCT+=sumtempTol[i];
						weiyite=weiyite+2;
					}
					Number labelada=new Number(28, rownum,sumtotalCT,formatNum);
					sheetFive.addCell(labelada);
					Number labelbrt=new Number(29, rownum,Arith.div(sumtotalCT,personMonthTotal),formatNum);
					sheetFive.addCell(labelbrt);
					Number labeldty=new Number(30, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatNum);
					sheetFive.addCell(labeldty);
					
					//sheetFive.mergeCells(2,rownum-listThirdtaskname.size(),2,rownum);
					//Label labelaea2 = new Label(2, rownum-listThirdtaskname.size(), listRFIRProjectCT.get(k).getProjectName(),formatleft);
					//sheetFive.addCell(labelaea2);
					//for (int k1 = (rownum-listThirdtaskname.size()); k1 < rownum+1; k1++) {
					//	if(k1 == (rownum-listThirdtaskname.size())){
					for (int k1 = (rownum-listThirdTask.size()); k1 < rownum+1; k1++) {
						if(k1 == (rownum-listThirdTask.size())){							
							Label labelaea2 = new Label(2,k1,listRFIRProjectCT.get(k).getProjectName(),formatleft1_0);
							sheetFive.addCell(labelaea2);
						}else if(k1 != rownum){
							Label labelaea2 = new Label(2,k1,listRFIRProjectCT.get(k).getProjectName(),formatleft1_1);
							sheetFive.addCell(labelaea2);
						}else{
							Label labelaea2 = new Label(2,k1,listRFIRProjectCT.get(k).getProjectName(),formatleft1_2);
							sheetFive.addCell(labelaea2);
						}
					}
					if ( ("").equals(listRFIRProjectCT.get(k).getCarMaker())||listRFIRProjectCT.get(k).getCarMaker()==null) {
						//sheetFive.mergeCells(1,rownum-listThirdtaskname.size(),1,rownum);
						//Label labelaea1 = new Label(1, rownum-listThirdtaskname.size(),"未知",formatleft);
						//sheetFive.addCell(labelaea1);
						//for (int k1 = (rownum-listThirdtaskname.size()); k1 < rownum+1; k1++) {
						//	if(k1 == (rownum-listThirdtaskname.size())){
						for (int k1 = (rownum-listThirdTask.size()); k1 < rownum+1; k1++) {
							if(k1 == (rownum-listThirdTask.size())){
								Label labelaea1 = new Label(1,k1,"未知",formatleft1_0);
								sheetFive.addCell(labelaea1);
							}else if(k1 != rownum){
								Label labelaea1 = new Label(1,k1,"未知",formatleft1_1);
								sheetFive.addCell(labelaea1);
							}else{
								Label labelaea1 = new Label(1,k1,"未知",formatleft1_2);
								sheetFive.addCell(labelaea1);
							}
						}
					}
					else {
						//sheetFive.mergeCells(1,rownum-listThirdtaskname.size(),1,rownum);
						//Label labelaea1 = new Label(1, rownum-listThirdtaskname.size(),listRFIRProjectCT.get(k).getCarMaker(),formatleft);
						//sheetFive.addCell(labelaea1);
						//for (int k1 = (rownum-listThirdtaskname.size()); k1 < rownum+1; k1++) {
						//	if(k1 == (rownum-listThirdtaskname.size())){
						for (int k1 = (rownum-listThirdTask.size()); k1 < rownum+1; k1++) {
							if(k1 == (rownum-listThirdTask.size())){
								Label labelaea1 = new Label(1,k1,listRFIRProjectCT.get(k).getCarMaker(),formatleft1_0);
								sheetFive.addCell(labelaea1);
							}else if(k1 != rownum){
								Label labelaea1 = new Label(1,k1,listRFIRProjectCT.get(k).getCarMaker(),formatleft1_1);
								sheetFive.addCell(labelaea1);
							}else{
								Label labelaea1 = new Label(1,k1,listRFIRProjectCT.get(k).getCarMaker(),formatleft1_2);
								sheetFive.addCell(labelaea1);
							}
						}
					}
					sheetFive.mergeCells(0,rownum-listThirdTask.size(),0,rownum);
					Label labela1121 = new Label(0,rownum-listThirdTask.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetFive.addCell(labela1121);
					rownum++;
				}
			}
			/**
			 * 先行开发
			 */
			double categorysumtime4=0.0;
			//if (listFourthtaskname.size()!=0) {
			if (listFourthTask.size()!=0) {
				sheetFive.mergeCells(0,rownum,1,rownum);
				Label labelabmnkng = new Label(0, rownum, "先行开发　计",formattitleor);
				sheetFive.addCell(labelabmnkng);
				Label labelab12ng = new Label(2, rownum, "Tol",formattitleor);
				sheetFive.addCell(labelab12ng);
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double categorysumtime4temp=0.0;
					for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
						if (("先行开发").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
							categorysumtime4temp = Arith.add(categorysumtime4temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
						}
					}
					categorysumtime4+=categorysumtime4temp;
					Number labelacm=new Number(weiyiXXKaiFa, rownum,categorysumtime4temp,formatNum);
					sheetFive.addCell(labelacm);
					Number labelftg=new Number(weiyiXXKaiFa+1, rownum, Arith.div(categorysumtime4temp, (workDayslist.get(m)*8)),formatNum);
					sheetFive.addCell(labelftg);
					weiyiXXKaiFa=weiyiXXKaiFa+2;
				}
				Number labeladgn=new Number(27, rownum,categorysumtime4,formatNum);
				sheetFive.addCell(labeladgn);
				Number labelbrt=new Number(28, rownum,Arith.div(categorysumtime4,personMonthTotal),formatNum);
				sheetFive.addCell(labelbrt);
				Number labeldty=new Number(29, rownum,Arith.div(categorysumtime4, basetotalnum, 4)*100,formatNum);
				sheetFive.addCell(labeldty);
				rownum++;
						double sumtotalCT=0.0;
						double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
						Task fourthTask = new Task();
						//for (int i = 0; i < listFourthtaskname.size(); i++) {
						for (int i = 0; i < listFourthTask.size(); i++) {
							fourthTask = listFourthTask.get(i);
							//Label labelab1ab = new Label(2, rownum, listFourthtaskname.get(i),formatleft);
							Label labelab1ab = new Label(2, rownum, fourthTask.getTxt(),formatleft);
							sheetFive.addCell(labelab1ab);
							double sumtimetemp=0.0;
							int weiyitemp=weiyi;
							
							for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
								double sumtime=0.0;
								for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
									if (("先行开发").equals(allMonthListManHourDtoList.get(s).get(j).getCategory())) {
										//if (listFourthtaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
										if (fourthTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
												sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
										}
									}
								}
								sumtimetemp+=sumtime;
								Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
								sheetFive.addCell(labelac3);
								Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
								sheetFive.addCell(labelftg);
								sumtempTol[s]+=sumtime;
								weiyitemp=weiyitemp+2;
							}
							Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
							sheetFive.addCell(labeladb23);
							Number labelcju=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
							sheetFive.addCell(labelcju);
							Number labeletg=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
							sheetFive.addCell(labeletg);
							rownum++;
						}
						int weiyite=weiyi;
						Label labelab12a = new Label(2, rownum, "Tol",formatleft);
						sheetFive.addCell(labelab12a);
						for (int i = 0; i < sumtempTol.length; i++) {
							Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatNum);
							sheetFive.addCell(labelaca);
							Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatNum);
							sheetFive.addCell(labelftg);
							sumtotalCT+=sumtempTol[i];
							weiyite=weiyite+2;
						}
						Number labelada =new Number(27, rownum,sumtotalCT,formatNum);
						sheetFive.addCell(labelada);
						Number labelcju=new Number(28, rownum,Arith.div(sumtotalCT,personMonthTotal),formatNum);
						sheetFive.addCell(labelcju);
						Number labeletg=new Number(29, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatNum);
						sheetFive.addCell(labeletg);
						//sheetFive.mergeCells(1,rownum-listFourthtaskname.size(),1,rownum);
						//Label labelaea1 = new Label(1, rownum-listFourthtaskname.size(), department,formatleft);
						sheetFive.mergeCells(1,rownum-listFourthTask.size(),1,rownum);
						Label labelaea1 = new Label(1, rownum-listFourthTask.size(), department,formatleft);
						sheetFive.addCell(labelaea1);
						rownum++;
			}
			/**
			 * 对剩余的空白部分进行单元格处理
			 */
			WritableCellFormat formatg=new WritableCellFormat(fonttitleor); 
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rownum; j++) {
					for (int k = 1; k <=Parma.strSheetFiveTitle.length ; k++) {
						Cell celletemp1 = sheetFive.getCell(k, j);
						String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							Label labelhLabel = new Label(k, j, null ,formatg);
							sheetFive.addCell(labelhLabel);
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
		File temp = new File(Parma.TEMP_FILESixthAddress); 
		if (!temp.isFile()) {
			String[] fileList = temp.list();
			for (String string : fileList) {
				File delDile = new File(Parma.TEMP_FILESixthAddress+"/"+string);
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
