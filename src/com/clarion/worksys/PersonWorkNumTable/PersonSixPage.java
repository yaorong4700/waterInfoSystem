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
public class PersonSixPage {
/**
 * 生成个人数据的集计信息
 * @param department   部门名
 * @param branch   课别名
 * @param listProject   项目名
 * @param year   起始年份
 * @param month   起始月份
 * @param endyear   截止年份
 * @param endmonth   截止月份
 * @param uniqueString    唯一性字符串
 * @param allMonthListManHourDtoList   所有工数数据信息
 * @param listProjectTasks  所有作业类型的集合
 * @param workDayslist     每个月上班天数的集合
 */
	public void updatePersonWorkNumSixXLS(String department,String branch,List<Project> listProject,int year, int month,
			                     int endyear, int endmonth,String uniqueString,List<List<ManHourDto>> allMonthListManHourDtoList,
			                     List<Project_task> listProjectTasks,List<Integer> workDayslist) {
		
		try {
			File file = new File(Parma.TEMP_FILESixthAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEFirstAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			
			String strnameString1=Parma.TEMP_FILESixthAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//生成的模板存储所在地
			String strnameString2=Parma.TEMP_FILEFirstAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetSix = book1.createSheet(department+"集计", 5);
			sheetSix.setColumnView(2,36); 
			sheetSix.setColumnView(1,36); 
			sheetSix.setColumnView(2,36); 
			sheetSix.setColumnView(1,36); 
			
			WritableFont fonttitleor= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
			WritableCellFormat formattitleor=new WritableCellFormat(fonttitleor);
			formattitleor.setAlignment(jxl.format.Alignment.CENTRE); //
			formattitleor.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formattitleor.setWrap(true);
			formattitleor.setBackground(Colour.SKY_BLUE);
			formattitleor.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatTitleNum=new WritableCellFormat(fonttitleor);
			formatTitleNum.setAlignment(jxl.format.Alignment.RIGHT); //data Alignment.RIGHT set
			formatTitleNum.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formatTitleNum.setWrap(true);
			formatTitleNum.setBackground(Colour.SKY_BLUE);
			formatTitleNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatgr=new WritableCellFormat(fonttitleor); 
			formatgr.setAlignment(jxl.format.Alignment.CENTRE); 
			formatgr.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			formatgr.setBackground(Colour.LIGHT_TURQUOISE);
			formatgr.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatleft=new WritableCellFormat(fonttitleor); //data Alignment.LEFT set
			formatleft.setAlignment(jxl.format.Alignment.LEFT); 
			formatleft.setBackground(Colour.LIGHT_TURQUOISE);
			formatleft.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatright=new WritableCellFormat(fonttitleor); //title and data Alignment.LEFT set
			formatright.setAlignment(jxl.format.Alignment.RIGHT); 
			formatright.setBackground(Colour.LIGHT_TURQUOISE);
			formatright.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			sheetSix.mergeCells(0,0,0,1); 
			int rownum=2;
			Label label = new Label(0, 0, "UNIT単位工数集計",formatgr);
			sheetSix.addCell(label);
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(1, 0, "集计日",formatgr);
			sheetSix.addCell(labelaLabel);
			Label labelaLabelmhk = new Label(2, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formatgr);
			sheetSix.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(1, 1, "对象",formatgr);
			sheetSix.addCell(labelbLabel);
			Label labelbLabelfg = new Label(2, 1, department,formatgr);
			sheetSix.addCell(labelbLabelfg);
			
			double personMonthTotal=0.0f;
			for (int i = 0; i < workDayslist.size(); i++) {
				personMonthTotal+=workDayslist.get(i)*8;
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
			double categorysumtime1=0.0;
			Label labelaa = new Label(0, rownum, "区分",formatgr);
			sheetSix.addCell(labelaa);
			for (int i = 0; i < Parma.transforTitle.length; i++) {
				if (i>2&&i%2==1) {
					Label labelaa1 = new Label(i+1, rownum, Parma.transforTitle[i],formatPerson);
					sheetSix.addCell(labelaa1);
				}
				else {
					Label labelaa1 = new Label(i+1, rownum, Parma.transforTitle[i],formatgr);
					sheetSix.addCell(labelaa1);
				}
			}
			rownum++;
			for (int i = 2; i < Parma.transforTitle.length+1; i++) {
				sheetSix.mergeCells(i,rownum,i,rownum+1);
			}
			sheetSix.mergeCells(0,rownum,1,rownum+1);
			double basetotalnum=0.0;
			
			Label labela1 = new Label(0, rownum, "总工数",formattitleor);
			sheetSix.addCell(labela1);
			Label labela5 = new Label(2, rownum, "Tol",formattitleor);
			sheetSix.addCell(labela5);
			int weiyi=0;
			if (month>=4&&month<=12) {
				weiyi=(month-4)*2+3;
			}
			if (month>=1&&month<=3) {
				weiyi=month*2+19;
			}
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
				Number labela2=new Number(weiyiFirst, rownum,basetotalnumtemp,formatTitleNum);
				sheetSix.addCell(labela2);
				Number labelftg=new Number(weiyiFirst+1, rownum, Arith.div(basetotalnumtemp, (workDayslist.get(m)*8)),formatTitleNum);
				sheetSix.addCell(labelftg);
				basetotalnum+=basetotalnumtemp;
				weiyiFirst=weiyiFirst+2;
			}
			
			Number labela3=new Number(27, rownum,basetotalnum,formatTitleNum);
			sheetSix.addCell(labela3);
			Number labelgy=new Number(28, rownum,Arith.div(basetotalnum,personMonthTotal),formatTitleNum);
			sheetSix.addCell(labelgy);
			Number labelef=new Number(29, rownum,Arith.div(basetotalnum, basetotalnum, 4)*100,formatTitleNum);
			sheetSix.addCell(labelef);
			rownum++;
			rownum++;
			sheetSix.mergeCells(0,rownum,1,rownum);
			Label labelab = new Label(0, rownum, "开发工数　計",formattitleor);
			sheetSix.addCell(labelab);
			Label labelab12 = new Label(2, rownum, "Tol",formattitleor);
			sheetSix.addCell(labelab12);
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double categorysumtime1temp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("开发工数").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						categorysumtime1temp = Arith.add(categorysumtime1temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
				categorysumtime1+=categorysumtime1temp;
				Number labelac=new Number(weiyikaifa, rownum,categorysumtime1temp,formatTitleNum);
				sheetSix.addCell(labelac);
				Number labelftg=new Number(weiyikaifa+1, rownum, Arith.div(categorysumtime1temp, (workDayslist.get(m)*8)),formatTitleNum);
				sheetSix.addCell(labelftg);
				weiyikaifa=weiyikaifa+2;
			}
			Number labelad=new Number(27, rownum,categorysumtime1,formatTitleNum);
			sheetSix.addCell(labelad);
			Number labelrhg=new Number(28, rownum,Arith.div(categorysumtime1,personMonthTotal),formatTitleNum);
			sheetSix.addCell(labelrhg);
			Number labelwdr=new Number(29, rownum,Arith.div(categorysumtime1, basetotalnum, 4)*100,formatTitleNum);
			sheetSix.addCell(labelwdr);
			rownum++;
			//总计
			List<Project> listProjectDevelopWorkNum=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
				if (("开发工数").equals(listProject.get(i).getCategory())) {
					listProjectDevelopWorkNum.add(listProject.get(i));
				}
			}
			double sumtotalDevelopWorkNum=0.0;
			double[] sumtempTolDevelopWorkNum=new double[allMonthListManHourDtoList.size()];
			Task firstTask = new Task();
				//for (int i = 0; i < listFirsttaskname.size(); i++) {
				for (int i = 0; i < listFirstTask.size(); i++) {
					firstTask = listFirstTask.get(i);
					//Label labelab1ab = new Label(2, rownum, listFirsttaskname.get(i),formatleft);
					Label labelab1ab = new Label(2, rownum, firstTask.getTxt(),formatleft);
					sheetSix.addCell(labelab1ab);
					double sumtimetemp=0.0;
					int weiyiDevelop=weiyi;
					for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
						double sumtime=0.0;
						for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
							for (int n = 0; n < listProjectDevelopWorkNum.size(); n++) {
								//if (listFirsttaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
								if (firstTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
									if (allMonthListManHourDtoList.get(s).get(j).getProjectID()==listProjectDevelopWorkNum.get(n).getProjectID()) {
											sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
									}
								}
							}
						}	
						sumtimetemp+=sumtime;
						Number labelac3=new Number(weiyiDevelop, rownum,sumtime,formatright);
						sheetSix.addCell(labelac3);
						Number labelftg=new Number(weiyiDevelop+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
						sheetSix.addCell(labelftg);
						sumtempTolDevelopWorkNum[s]+=sumtime;
						weiyiDevelop=weiyiDevelop+2;
					}
					Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
					sheetSix.addCell(labeladb23);
					Number labelft=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
					sheetSix.addCell(labelft);
					Number labelwft=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
					sheetSix.addCell(labelwft);
					rownum++;
				}
				int weiyitedeve=weiyi;	
				Label labelbhy = new Label(2, rownum, "Tol",formatleft);
				sheetSix.addCell(labelbhy);
				for (int i = 0; i < sumtempTolDevelopWorkNum.length; i++) {
					Number labelaca=new Number(weiyitedeve, rownum,sumtempTolDevelopWorkNum[i],formatTitleNum);
					sheetSix.addCell(labelaca);
					Number labelftg=new Number(weiyitedeve+1, rownum, Arith.div(sumtempTolDevelopWorkNum[i], (workDayslist.get(i)*8)),formatTitleNum);
					sheetSix.addCell(labelftg);
					sumtotalDevelopWorkNum+=sumtempTolDevelopWorkNum[i];
					weiyitedeve=weiyitedeve+2;
				}
				Number labelfhu=new Number(27, rownum, sumtotalDevelopWorkNum,formatTitleNum);
				sheetSix.addCell(labelfhu);
				Number labelft=new Number(28, rownum,Arith.div(sumtotalDevelopWorkNum,personMonthTotal),formatTitleNum);
				sheetSix.addCell(labelft);
				Number labelwft=new Number(29, rownum,Arith.div(sumtotalDevelopWorkNum, basetotalnum, 4)*100,formatTitleNum);
				sheetSix.addCell(labelwft);
				//sheetSix.mergeCells(1,rownum-listFirsttaskname.size(),1,rownum);
				//Label labeldfgt = new Label(1, rownum-listFirsttaskname.size(),"合计",formatgr);
				sheetSix.mergeCells(1,rownum-listFirstTask.size(),1,rownum);
				Label labeldfgt = new Label(1, rownum-listFirstTask.size(),"合计",formatgr);
				sheetSix.addCell(labeldfgt);
				//sheetSix.mergeCells(0,rownum-listFirsttaskname.size(),0,rownum);
				//Label labelcfgh = new Label(0,rownum-listFirsttaskname.size(),"合计",formatgr);
				sheetSix.mergeCells(0,rownum-listFirstTask.size(),0,rownum);
				Label labelcfgh = new Label(0,rownum-listFirstTask.size(),"合计",formatgr);
				sheetSix.addCell(labelcfgh);
				rownum++;
			//总计
			for (int m = 0; m < Parma.strProjectClientNameDevelopPage.length; m++) {
				List<Project> listProjectCT=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
					if (("开发工数").equals(listProject.get(i).getCategory())) {
						if ((Parma.strProjectClientNameDevelopPage[m]).equals(listProject.get(i).getProjectClientName())) {
							listProjectCT.add(listProject.get(i));
						}
					}
				}
				double sumtotalCT=0.0;
				double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
					//for (int i = 0; i < listFirsttaskname.size(); i++) {
					for (int i = 0; i < listFirstTask.size(); i++) {
						firstTask = listFirstTask.get(i);
						//Label labelab1ab = new Label(2, rownum, listFirsttaskname.get(i),formatleft);
						Label labelab1ab = new Label(2, rownum, firstTask.getTxt(),formatleft);
						sheetSix.addCell(labelab1ab);
						double sumtimetemp=0.0;
						int weiyitemp=weiyi;
						for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
							double sumtime=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								for (int n = 0; n < listProjectCT.size(); n++) {
									//if (listFirsttaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
									if (firstTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
										if (allMonthListManHourDtoList.get(s).get(j).getProjectID()==listProjectCT.get(n).getProjectID()) {
											if (Parma.strProjectClientNameDevelopPage[m].equals(listProjectCT.get(n).getProjectClientName())) {
												sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
											}
										}
									}
								}
							}	
							sumtimetemp+=sumtime;
							Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
							sheetSix.addCell(labelac3);
							Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
							sheetSix.addCell(labelftg);
							sumtempTol[s]+=sumtime;
							weiyitemp=weiyitemp+2;
						}
						Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
						sheetSix.addCell(labeladb23);
						Number labelgtge=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
						sheetSix.addCell(labelgtge);
						Number labelwer=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
						sheetSix.addCell(labelwer);
						rownum++;
					}
					int weiyite=weiyi;	
					Label labelab12a = new Label(2, rownum, "Tol",formatleft);
					sheetSix.addCell(labelab12a);
					for (int i = 0; i < sumtempTol.length; i++) {
						Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatTitleNum);
						sheetSix.addCell(labelaca);
						Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatTitleNum);
						sheetSix.addCell(labelftg);
						sumtotalCT+=sumtempTol[i];
						weiyite=weiyite+2;
					}
					Number labelada=new Number(27, rownum, sumtotalCT,formatTitleNum);
					sheetSix.addCell(labelada);
					Number labelgtge=new Number(28, rownum,Arith.div(sumtotalCT,personMonthTotal),formatTitleNum);
					sheetSix.addCell(labelgtge);
					Number labelwer=new Number(29, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatTitleNum);
					sheetSix.addCell(labelwer);
					//sheetSix.mergeCells(1,rownum-listFirsttaskname.size(),1,rownum);
					//Label labelaea1 = new Label(1, rownum-listFirsttaskname.size(),Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.mergeCells(1,rownum-listFirstTask.size(),1,rownum);
					Label labelaea1 = new Label(1, rownum-listFirstTask.size(),Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.addCell(labelaea1);
					//sheetSix.mergeCells(0,rownum-listFirsttaskname.size(),0,rownum);
					//Label labela1121 = new Label(0,rownum-listFirsttaskname.size(),Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.mergeCells(0,rownum-listFirstTask.size(),0,rownum);
					Label labela1121 = new Label(0,rownum-listFirstTask.size(),Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.addCell(labela1121);
					rownum++;
					
		}
			//if (department.equals("工程设计部")) {
			if (DepartmentEnum.GCSJB.getName().equals(department)) {
				double sumtotalCTtemp=0.0;
				double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
				for (int i = 0; i < Parma.strAdditionalProjDesignerCT.length; i++) {
					Label labelab1ab = new Label(2, rownum, Parma.strAdditionalProjDesignerCT[i],formatleft);
					sheetSix.addCell(labelab1ab);
					double sumtimetemp=0.0;
					int weiyitemp=weiyi;
					
					for (int j = 0; j < allMonthListManHourDtoList.size(); j++) {
						double sumtime=0.0;
						for (int mk = 0; mk < allMonthListManHourDtoList.get(j).size(); mk++) {
							if (("开发工数").equals(allMonthListManHourDtoList.get(j).get(mk).getCategory())) {
								if (("185 工程设计部CT").equals(allMonthListManHourDtoList.get(j).get(mk).getProjectName())) {
									if (Parma.strAdditionalProjDesignerCT[i].equals(allMonthListManHourDtoList.get(j).get(mk).getTask())) {
										sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(j).get(mk).getTimes());
									}
								}
							}
						}
						sumtimetemp+=sumtime;
						Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
						sheetSix.addCell(labelac3);
						Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(j)*8)),formatright);
						sheetSix.addCell(labelftg);
						sumtempTol[j]+=sumtime;
						weiyitemp=weiyitemp+2;
					}
					
					Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
					sheetSix.addCell(labeladb23);
					Number labelgtge=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
					sheetSix.addCell(labelgtge);
					Number labelwer=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
					sheetSix.addCell(labelwer);
					rownum++;
				}
				int weiyite=weiyi;
				
				Label labelab12a = new Label(2, rownum, "Tol",formatleft);
				sheetSix.addCell(labelab12a);
				for (int i = 0; i < sumtempTol.length; i++) {
					Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatTitleNum);
					sheetSix.addCell(labelaca);
					Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatTitleNum);
					sheetSix.addCell(labelftg);
					sumtotalCTtemp+=sumtempTol[i];
					weiyite=weiyite+2;
				}
				Number labelada=new Number(27, rownum, sumtotalCTtemp,formatTitleNum);
				sheetSix.addCell(labelada);
				Number labelgtge=new Number(28, rownum,Arith.div(sumtotalCTtemp,personMonthTotal),formatTitleNum);
				sheetSix.addCell(labelgtge);
				Number labelwer=new Number(29, rownum,Arith.div(sumtotalCTtemp, basetotalnum, 4)*100,formatTitleNum);
				sheetSix.addCell(labelwer);
				sheetSix.mergeCells(1,rownum-Parma.strAdditionalProjDesignerCT.length,1,rownum);
				Label labelaea1 = new Label(1, rownum-Parma.strAdditionalProjDesignerCT.length,"185 工程设计部CT",formatgr);
				sheetSix.addCell(labelaea1);
				sheetSix.mergeCells(0,rownum-Parma.strAdditionalProjDesignerCT.length,0,rownum);
				Label labela1121 = new Label(0,rownum-Parma.strAdditionalProjDesignerCT.length,"CT",formatgr);
				sheetSix.addCell(labela1121);
				rownum++;
				
				double[] sumtempToltemp=new double[allMonthListManHourDtoList.size()];
				double sumtotalDCOEtemp=0.0;
				for (int i = 0; i < Parma.strAdditionalProjDesignerDCOE.length; i++) {
					Label labelab1ab = new Label(2, rownum, Parma.strAdditionalProjDesignerDCOE[i],formatleft);
					sheetSix.addCell(labelab1ab);
					double sumtimetemp=0.0;
					int weiyitemp=weiyi;
					
					for (int mk = 0; mk < allMonthListManHourDtoList.size(); mk++) {
						double sumtime=0.0;
						for (int j = 0; j < allMonthListManHourDtoList.get(mk).size(); j++) {
							if (("开发工数").equals(allMonthListManHourDtoList.get(mk).get(j).getCategory())) {
								if (("185 工程设计部DCOE").equals(allMonthListManHourDtoList.get(mk).get(j).getProjectName())) {
									if (Parma.strAdditionalProjDesignerDCOE[i].equals(allMonthListManHourDtoList.get(mk).get(j).getTask())) {
										sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(mk).get(j).getTimes());
									}
								}
							}
						}
						sumtimetemp+=sumtime;
						Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
						sheetSix.addCell(labelac3);
						Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(mk)*8)),formatright);
						sheetSix.addCell(labelftg);
						sumtempToltemp[mk]+=sumtime;
						weiyitemp=weiyitemp+2;
					}
					
					
					Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
					sheetSix.addCell(labeladb23);
					Number labelwfts=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
					sheetSix.addCell(labelwfts);
					Number labelvgt=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
					sheetSix.addCell(labelvgt);
					rownum++;
				}
				int weiyitetemp=weiyi;
				
				Label labelab12am = new Label(2, rownum, "Tol",formatleft);
				sheetSix.addCell(labelab12am);
				for (int i = 0; i < sumtempToltemp.length; i++) {
					Number labelaca=new Number(weiyitetemp, rownum,sumtempToltemp[i],formatTitleNum);
					sheetSix.addCell(labelaca);
					Number labelftg=new Number(weiyitetemp+1, rownum, Arith.div(sumtempToltemp[i], (workDayslist.get(i)*8)),formatTitleNum);
					sheetSix.addCell(labelftg);
					sumtotalDCOEtemp+=sumtempToltemp[i];
					weiyitetemp=weiyitetemp+2;
				}
				Number labeladam=new Number(27, rownum, sumtotalDCOEtemp,formatTitleNum);
				sheetSix.addCell(labeladam);
				Number labelwfts=new Number(28, rownum,Arith.div(sumtotalDCOEtemp,personMonthTotal),formatTitleNum);
				sheetSix.addCell(labelwfts);
				Number labelvgt=new Number(29, rownum,Arith.div(sumtotalDCOEtemp, basetotalnum, 4)*100,formatTitleNum);
				sheetSix.addCell(labelvgt);
				sheetSix.mergeCells(1,rownum-Parma.strAdditionalProjDesignerDCOE.length,1,rownum);
				Label labelaea1m = new Label(1, rownum-Parma.strAdditionalProjDesignerDCOE.length, "185 工程设计部DCOE",formatgr);
				sheetSix.addCell(labelaea1m);
				sheetSix.mergeCells(0,rownum-Parma.strAdditionalProjDesignerDCOE.length,0,rownum);
				Label labela1121m = new Label(0,rownum-Parma.strAdditionalProjDesignerDCOE.length,"DCOE",formatgr);
				sheetSix.addCell(labela1121m);
				rownum++;
			}
			/**
			 * RFIRFQ  CT DCOE OUTOUT
			 */
			double categorysumtime2=0.0;
			sheetSix.mergeCells(0,rownum,1,rownum);
			Label labelabm = new Label(0, rownum, "RFI和RFQ　計",formattitleor);
			sheetSix.addCell(labelabm);
			Label labelab12m = new Label(2, rownum, "Tol",formattitleor);
			sheetSix.addCell(labelab12m);
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double categorysumtime2temp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("RFI和RFQ").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						categorysumtime2temp = Arith.add(categorysumtime2temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
				categorysumtime2+=categorysumtime2temp;
				Number labelacm=new Number(weiyiRFI, rownum,categorysumtime2temp,formatTitleNum);
				sheetSix.addCell(labelacm);
				Number labelftg=new Number(weiyiRFI+1, rownum, Arith.div(categorysumtime2temp, (workDayslist.get(m)*8)),formatTitleNum);
				sheetSix.addCell(labelftg);
				weiyiRFI=weiyiRFI+2;
			}
			Number labeladm=new Number(27, rownum,categorysumtime2,formatTitleNum);
			sheetSix.addCell(labeladm);
			Number labelwfts=new Number(28, rownum,Arith.div(categorysumtime2,personMonthTotal),formatTitleNum);
			sheetSix.addCell(labelwfts);
			Number labelvgt=new Number(29, rownum,Arith.div(categorysumtime2, basetotalnum, 4)*100,formatTitleNum);
			sheetSix.addCell(labelvgt);
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
					double sumtotalCT=0.0;
					double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
					Task secondTask = new Task();
					//for (int i = 0; i < listSecondtaskname.size(); i++) {
					for (int i = 0; i < listSecondTask.size(); i++) {
						secondTask = listSecondTask.get(i);
						//Label labelab1ab = new Label(2, rownum, listSecondtaskname.get(i),formatleft);
						Label labelab1ab = new Label(2, rownum, secondTask.getTxt(),formatleft);
						sheetSix.addCell(labelab1ab);
						double sumtimetemp=0.0;
						int weiyitemp=weiyi;
						for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
							double sumtime=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								for (int j2 = 0; j2 < listRFIRProjectCT.size(); j2++) {
									//if (listSecondtaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
									if (secondTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
										if (allMonthListManHourDtoList.get(s).get(j).getProjectID()==listRFIRProjectCT.get(j2).getProjectID()) {
											if (Parma.strProjectClientNameDevelopPage[m].equals(listRFIRProjectCT.get(j2).getProjectClientName())) {
												sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
											}
										}
									}
								}
							}
							sumtimetemp+=sumtime;
							Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
							sheetSix.addCell(labelac3);
							Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
							sheetSix.addCell(labelftg);
							sumtempTol[s]+=sumtime;
							weiyitemp=weiyitemp+2;
						}
						
						Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
						sheetSix.addCell(labeladb23);
						Number labelafr=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
						sheetSix.addCell(labelafr);
						Number labelwdrd=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
						sheetSix.addCell(labelwdrd);
						rownum++;
					}
					int weiyite=weiyi;
					
					Label labelab12a = new Label(2, rownum, "Tol",formatleft);
					sheetSix.addCell(labelab12a);
					for (int i = 0; i < sumtempTol.length; i++) {
						Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatTitleNum);
						sheetSix.addCell(labelaca);
						Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatTitleNum);
						sheetSix.addCell(labelftg);
						sumtotalCT+=sumtempTol[i];
						weiyite=weiyite+2;
					}
					Number labelada=new Number(27, rownum,sumtotalCT,formatTitleNum);
					sheetSix.addCell(labelada);
					Number labelafr=new Number(28, rownum,Arith.div(sumtotalCT,personMonthTotal),formatTitleNum);
					sheetSix.addCell(labelafr);
					Number labelwdrd=new Number(29, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatTitleNum);
					sheetSix.addCell(labelwdrd);
					//sheetSix.mergeCells(1,rownum-listSecondtaskname.size(),1,rownum);
					//Label labelaea1 = new Label(1, rownum-listSecondtaskname.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.mergeCells(1,rownum-listSecondTask.size(),1,rownum);
					Label labelaea1 = new Label(1, rownum-listSecondTask.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.addCell(labelaea1);
					//sheetSix.mergeCells(0,rownum-listSecondtaskname.size(),0,rownum);
					//Label labela1121 = new Label(0,rownum-listSecondtaskname.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.mergeCells(0,rownum-listSecondTask.size(),0,rownum);
					Label labela1121 = new Label(0,rownum-listSecondTask.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.addCell(labela1121);
					rownum++;
			}
			
			/**
			 * 量产后不具合对应
			 */
			double categorysumtime3=0.0;
			sheetSix.mergeCells(0,rownum,1,rownum);
			Label labelabmnkLabel = new Label(0, rownum, "量产后不具合对应　計",formattitleor);
			sheetSix.addCell(labelabmnkLabel);
			Label labelab12mnk = new Label(2, rownum, "Tol",formattitleor);
			sheetSix.addCell(labelab12mnk);
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double categorysumtime3temp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("量产后不具合对应").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						categorysumtime3temp = Arith.add(categorysumtime3temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
				categorysumtime3+=categorysumtime3temp;
				Number labelacmnk=new Number(weiyiBuJuHe, rownum, categorysumtime3temp,formatTitleNum);
				sheetSix.addCell(labelacmnk);
				Number labelftg=new Number(weiyiBuJuHe+1, rownum, Arith.div(categorysumtime3temp, (workDayslist.get(m)*8)),formatTitleNum);
				sheetSix.addCell(labelftg);
				weiyiBuJuHe=weiyiBuJuHe+2;
			}
			Number labeladmnnk=new Number(27, rownum,categorysumtime3,formatTitleNum);
			sheetSix.addCell(labeladmnnk);
			Number labelafr=new Number(28, rownum,Arith.div(categorysumtime3,personMonthTotal),formatTitleNum);
			sheetSix.addCell(labelafr);
			Number labelwdrd=new Number(29, rownum,Arith.div(categorysumtime3, basetotalnum, 4)*100,formatTitleNum);
			sheetSix.addCell(labelwdrd);
			rownum++;
			
			for (int m = 0; m < Parma.strProjectClientNameDevelopPage.length; m++) {
				List<Project> listBuJuHeProjectCT=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
					if (("量产后不具合对应").equals(listProject.get(i).getCategory())) {
						if ((Parma.strProjectClientNameDevelopPage[m]).equals(listProject.get(i).getProjectClientName())) {
							listBuJuHeProjectCT.add(listProject.get(i));
						}
					}
				}
					double sumtotalCT=0.0;
					double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
					Task thirdTask = new Task();
					//for (int i = 0; i < listThirdtaskname.size(); i++) {
					for (int i = 0; i < listThirdTask.size(); i++) {
						thirdTask = listThirdTask.get(i);
						//Label labelab1ab = new Label(2, rownum, listThirdtaskname.get(i),formatleft);
						Label labelab1ab = new Label(2, rownum, thirdTask.getTxt(),formatleft);
						sheetSix.addCell(labelab1ab);
						double sumtimetemp=0.0;
						int weiyitemp=weiyi;
						for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
							double sumtime=0.0;
							for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
								for (int j2 = 0; j2 < listBuJuHeProjectCT.size(); j2++) {
									//if (listThirdtaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
									if (thirdTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
										if (allMonthListManHourDtoList.get(s).get(j).getProjectID()==listBuJuHeProjectCT.get(j2).getProjectID()) {
											if (Parma.strProjectClientNameDevelopPage[m].equals(listBuJuHeProjectCT.get(j2).getProjectClientName())) {
												sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
											}
										}
									}
								}
							}
							sumtimetemp+=sumtime;
							Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
							sheetSix.addCell(labelac3);
							Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
							sheetSix.addCell(labelftg);
							sumtempTol[s]+=sumtime;
							weiyitemp=weiyitemp+2;
						}
						
						Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
						sheetSix.addCell(labeladb23);
						Number labelnh=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
						sheetSix.addCell(labelnh);
						Number labehyu=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
						sheetSix.addCell(labehyu);
						rownum++;
					}
					int weiyite=weiyi;
					
					Label labelab12a = new Label(2, rownum, "Tol",formatleft);
					sheetSix.addCell(labelab12a);
					for (int i = 0; i < sumtempTol.length; i++) {
						Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatTitleNum);
						sheetSix.addCell(labelaca);
						Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatTitleNum);
						sheetSix.addCell(labelftg);
						sumtotalCT+=sumtempTol[i];
						weiyite=weiyite+2;
					}
					Number labelada=new Number(27, rownum,sumtotalCT,formatTitleNum);
					sheetSix.addCell(labelada);
					Number labelnh=new Number(28, rownum,Arith.div(sumtotalCT,personMonthTotal),formatTitleNum);
					sheetSix.addCell(labelnh);
					Number labehyu=new Number(29, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatTitleNum);
					sheetSix.addCell(labehyu);
					//sheetSix.mergeCells(1,rownum-listThirdtaskname.size(),1,rownum);
					//Label labelaea1 = new Label(1, rownum-listThirdtaskname.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.mergeCells(1,rownum-listThirdTask.size(),1,rownum);
					Label labelaea1 = new Label(1, rownum-listThirdTask.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.addCell(labelaea1);
					//sheetSix.mergeCells(0,rownum-listThirdtaskname.size(),0,rownum);
					//Label labela1121 = new Label(0,rownum-listThirdtaskname.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.mergeCells(0,rownum-listThirdTask.size(),0,rownum);
					Label labela1121 = new Label(0,rownum-listThirdTask.size(), Parma.strProjectClientNameDevelopPage[m],formatgr);
					sheetSix.addCell(labela1121);
					rownum++;
			}
			
			/**
			 * 先行开发
			 */
			double categorysumtime4=0.0;
			//if (listFourthtaskname.size()!=0) {
			if (listFourthTask.size()!=0) {
				sheetSix.mergeCells(0,rownum,1,rownum);
				Label labelabmnkng = new Label(0, rownum, "先行开发　計",formattitleor);
				sheetSix.addCell(labelabmnkng);
				Label labelab12ng = new Label(2, rownum, "Tol",formattitleor);
				sheetSix.addCell(labelab12ng);
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double categorysumtime4temp=0.0;
					for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
						if (("先行开发").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
							categorysumtime4temp = Arith.add(categorysumtime4temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
						}
					}
					categorysumtime4+=categorysumtime4temp;
					Number labelacm=new Number(weiyiXXKaiFa, rownum,categorysumtime4temp,formatTitleNum);
					sheetSix.addCell(labelacm);
					Number labelftg=new Number(weiyiXXKaiFa+1, rownum, Arith.div(categorysumtime4temp, (workDayslist.get(m)*8)),formatTitleNum);
					sheetSix.addCell(labelftg);
					weiyiXXKaiFa=weiyiXXKaiFa+2;
				}
				Number labeladgn=new Number(27, rownum,categorysumtime4,formatTitleNum);
				sheetSix.addCell(labeladgn);
				Number labelnh=new Number(28, rownum,Arith.div(categorysumtime4,personMonthTotal),formatTitleNum);
				sheetSix.addCell(labelnh);
				Number labehyu=new Number(29, rownum,Arith.div(categorysumtime4, basetotalnum, 4)*100,formatTitleNum);
				sheetSix.addCell(labehyu);
				rownum++;
						double sumtotalCT=0.0;
						double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
						Task fourthTask = new Task();
						//for (int i = 0; i < listFourthtaskname.size(); i++) {
						for (int i = 0; i < listFourthTask.size(); i++) {
							fourthTask = listFourthTask.get(i);
							//Label labelab1ab = new Label(2, rownum, listFourthtaskname.get(i),formatleft);
							Label labelab1ab = new Label(2, rownum, fourthTask.getTxt(),formatleft);
							sheetSix.addCell(labelab1ab);
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
								sheetSix.addCell(labelac3);
								Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
								sheetSix.addCell(labelftg);
								sumtempTol[s]+=sumtime;
								weiyitemp=weiyitemp+2;
							}
							
							Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
							sheetSix.addCell(labeladb23);
							Number labelvfr=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
							sheetSix.addCell(labelvfr);
							Number labesdf=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
							sheetSix.addCell(labesdf);
							rownum++;
						}
						int weiyite=weiyi;
						Label labelab12a = new Label(2, rownum, "Tol",formatleft);
						sheetSix.addCell(labelab12a);
						for (int i = 0; i < sumtempTol.length; i++) {
							Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formatTitleNum);
							sheetSix.addCell(labelaca);
							Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatTitleNum);
							sheetSix.addCell(labelftg);
							sumtotalCT+=sumtempTol[i];
							weiyite=weiyite+2;
						}
						Number labelada =new Number(27, rownum,sumtotalCT,formatTitleNum);
						sheetSix.addCell(labelada);
						Number labelvfr=new Number(28, rownum,Arith.div(sumtotalCT,personMonthTotal),formatTitleNum);
						sheetSix.addCell(labelvfr);
						Number labesdf=new Number(29, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formatTitleNum);
						sheetSix.addCell(labesdf);
						//sheetSix.mergeCells(1,rownum-listFourthtaskname.size(),1,rownum);
						//Label labelaea1 = new Label(1, rownum-listFourthtaskname.size(), department,formatgr);
						sheetSix.mergeCells(1,rownum-listFourthTask.size(),1,rownum);
						Label labelaea1 = new Label(1, rownum-listFourthTask.size(), department,formatgr);
						sheetSix.addCell(labelaea1);
						rownum++;
			}
			/**
			 * 对剩余的空白部分进行单元格处理
			 */ 
			WritableCellFormat formatg=new WritableCellFormat(fonttitleor); 
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rownum; j++) {
					for (int k = 1; k <=Parma.strSheetFiveTitle.length ; k++) {
						Cell celletemp1 = sheetSix.getCell(k, j);
						String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							Label labelhLabel = new Label(k, j, null ,formatg);
							sheetSix.addCell(labelhLabel);
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
