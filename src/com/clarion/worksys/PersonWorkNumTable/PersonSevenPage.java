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
public class PersonSevenPage {
/**
* 生成部门的第七页（管理项番——开发工数）的集记信息
* @param department  部门名称
* @param branch      课别名称
* @param listProject  本部门相关的项目
* @param year         起始年份
* @param month        起始月份
* @param endyear      截止年份
* @param endmonth     截止月份
* @param filenameString   唯一性字符串-确定文件不会重命名
* @param allMonthListManHourDtoList   由部门、时间所指定的所有工数信息
* @param listProjectTasks    项目的作业类型集合
* @param listtransferNo       本部门相关的移管号集合
* @param workDayslist         本部门各月上班天数
*/
	public void updatePersonWorkNumSevenXLS(String department,String branch,List<Project> listProject,int year, int month,
			                     int endyear, int endmonth,String uniqueString,List<List<ManHourDto>> allMonthListManHourDtoList,
			                     List<Project_task> listProjectTasks,List<String> listtransferNo,List<Integer> workDayslist) {
		
		try {
			File file;
				file= new File("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"); 
			
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEFirstAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			String strnameString1=Parma.TEMP_FILEFirstAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//生成的模板存储所在地
			String strnameString2;
			if (branch==null) {
				strnameString2="d:/"+department+"/"+department+"/"+year+"/"+month+"月"+"/"+"集計データ"+department+year+"年"+month+"月"+"_"+endyear+"年"+endmonth+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			}
			else {
				strnameString2="d:/"+department+"/"+department+"/"+year+"/"+month+"月"+"/"+"集計データ"+department+branch+year+"年"+month+"月"+"_"+endyear+"年"+endmonth+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			}
			
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetSeven = book1.createSheet("管理项番集计", 6);
			sheetSeven.setColumnView(2,36); 
			sheetSeven.setColumnView(1,36); 
			
			WritableFont fonttitleor= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
			WritableCellFormat formattitleor=new WritableCellFormat(fonttitleor);
			formattitleor.setAlignment(jxl.format.Alignment.CENTRE); 
			formattitleor.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formattitleor.setWrap(true);
			formattitleor.setBackground(Colour.SKY_BLUE);
			formattitleor.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formattitleNum=new WritableCellFormat(fonttitleor);//data Alignment.RIGHT set
			formattitleNum.setAlignment(jxl.format.Alignment.RIGHT); //
			formattitleNum.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //垂直居中
			formattitleNum.setWrap(true);
			formattitleNum.setBackground(Colour.SKY_BLUE);
			formattitleNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatgr=new WritableCellFormat(fonttitleor); 
			formatgr.setAlignment(jxl.format.Alignment.CENTRE); 
			formatgr.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			formatgr.setBackground(Colour.LIGHT_TURQUOISE);
			formatgr.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			 
			WritableCellFormat formatleft=new WritableCellFormat(fonttitleor); 
			formatleft.setAlignment(jxl.format.Alignment.LEFT); 
			formatleft.setBackground(Colour.LIGHT_TURQUOISE);
			formatleft.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatright=new WritableCellFormat(fonttitleor); 
			formatright.setAlignment(jxl.format.Alignment.RIGHT); 
			formatright.setBackground(Colour.LIGHT_TURQUOISE);
			formatright.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			sheetSeven.mergeCells(0,0,0,1); 
			int rownum=2;
			Label label = new Label(0, 0, "UNIT单位工数集计",formatgr);
			sheetSeven.addCell(label);
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(1, 0, "集计日",formatgr);
			sheetSeven.addCell(labelaLabel);
			Label labelaLabelmhk = new Label(2, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formatgr);
			sheetSeven.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(1, 1, "对象",formatgr);
			sheetSeven.addCell(labelbLabel);
			Label labelbLabelfg = new Label(2, 1, department,formatgr);
			sheetSeven.addCell(labelbLabelfg);
			
			double personMonthTotal=0.0f;
			for (int i = 0; i < workDayslist.size(); i++) {
				personMonthTotal+=workDayslist.get(i)*8;
			}
			//List<String> listFirsttaskname=new ArrayList<String>();
			List<Task> listFirstTask=new ArrayList<Task>();
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
			int weiyi=0;
			if (month>=4&&month<=12) {
				weiyi=(month-4)*2+3;
			}
			if (month>=1&&month<=3) {
				weiyi=month*2+19;
			}
			/**
			 * 開発工数  DCOE CT OUTOUT
			 */
			double categorysumtime1=0.0;
			Label labelaa = new Label(0, rownum, "区分",formatgr);
			sheetSeven.addCell(labelaa);
			for (int i = 0; i < Parma.transforTitle.length; i++) {
				if (i>2&&i%2==1) {
					Label labelaa1 = new Label(i+1, rownum, Parma.transforTitle[i],formatPerson);
					sheetSeven.addCell(labelaa1);
				}
				else {
					Label labelaa1 = new Label(i+1, rownum, Parma.transforTitle[i],formatgr);
					sheetSeven.addCell(labelaa1);
				}
			}
			rownum++;
			for (int i = 2; i < Parma.transforTitle.length+1; i++) {
				sheetSeven.mergeCells(i,rownum,i,rownum+1);
			}
			sheetSeven.mergeCells(0,rownum,1,rownum+1);
			double basetotalnum=0.0;
			
			Label labela1 = new Label(0, rownum, "总工数",formattitleor);
			sheetSeven.addCell(labela1);
			Label labela5 = new Label(2, rownum, "Tol",formattitleor);
			sheetSeven.addCell(labela5);
			
			int weiyikaifa=weiyi;
			int weiyiFirst=weiyi;
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double basetotalnumtemp=0.0;
				
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					basetotalnumtemp = Arith.add(basetotalnumtemp,allMonthListManHourDtoList.get(m).get(i).getTimes());
				}
				Number labela2=new Number(weiyiFirst, rownum,basetotalnumtemp,formattitleNum);
				sheetSeven.addCell(labela2);
				Number labelftg=new Number(weiyiFirst+1, rownum, Arith.div(basetotalnumtemp, (workDayslist.get(m)*8)),formattitleNum);
				sheetSeven.addCell(labelftg);
				basetotalnum+=basetotalnumtemp;
				weiyiFirst=weiyiFirst+2;
			}
			
			Number labela3=new Number(27, rownum,basetotalnum,formattitleNum);
			sheetSeven.addCell(labela3);
			Number labelgty=new Number(28, rownum,Arith.div(basetotalnum,personMonthTotal),formattitleNum);
			sheetSeven.addCell(labelgty);
			Number labelwerf=new Number(29, rownum,Arith.div(basetotalnum, basetotalnum, 4)*100,formattitleNum);
			sheetSeven.addCell(labelwerf);
			rownum++;
			rownum++;
			sheetSeven.mergeCells(0,rownum,1,rownum);
			Label labelab = new Label(0, rownum, "开发工数　计",formattitleor);
			sheetSeven.addCell(labelab);
			Label labelab12 = new Label(2, rownum, "Tol",formattitleor);
			sheetSeven.addCell(labelab12);
			for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
				double categorysumtime1temp=0.0;
				for (int i = 0; i < allMonthListManHourDtoList.get(m).size(); i++) {
					if (("开发工数").equals(allMonthListManHourDtoList.get(m).get(i).getCategory())) {
						categorysumtime1temp = Arith.add(categorysumtime1temp,allMonthListManHourDtoList.get(m).get(i).getTimes());
					}
				}
				categorysumtime1+=categorysumtime1temp;
				Number labelac=new Number(weiyikaifa, rownum,categorysumtime1temp,formattitleNum);
				sheetSeven.addCell(labelac);
				Number labelftg=new Number(weiyikaifa+1, rownum, Arith.div(categorysumtime1temp, (workDayslist.get(m)*8)),formattitleNum);
				sheetSeven.addCell(labelftg);
				weiyikaifa=weiyikaifa+2;
			}
			Number labelad=new Number(27, rownum,categorysumtime1,formattitleNum);
			sheetSeven.addCell(labelad);
			Number labelgy=new Number(28, rownum,Arith.div(categorysumtime1,personMonthTotal),formattitleNum);
			sheetSeven.addCell(labelgy);
			Number labelef=new Number(29, rownum,Arith.div(categorysumtime1, basetotalnum, 4)*100,formattitleNum);
			sheetSeven.addCell(labelef);
			rownum++;
			listtransferNo.add("未标明管理项番");
			for (int m = 0; m < listtransferNo.size(); m++) {
				List<Project> listProjectTransfer=new ArrayList<Project>();
				for (int i = 0; i < listProject.size(); i++) {
					if (("开发工数").equals(listProject.get(i).getCategory())) {
						listProjectTransfer.add(listProject.get(i));
					}
				}
					if (!listtransferNo.get(m).equals("未标明管理项番")) {
						double sumtotalCT=0.0;
						double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
						Task firstTask = new Task();
							//for (int i = 0; i < listFirsttaskname.size(); i++) {
							for (int i = 0; i < listFirstTask.size(); i++) {
								firstTask = listFirstTask.get(i);
								//Label labelab1ab = new Label(2, rownum, listFirsttaskname.get(i),formatleft);
								Label labelab1ab = new Label(2, rownum, firstTask.getTxt(),formatleft);
								sheetSeven.addCell(labelab1ab);
								double sumtimetemp=0.0;
								int weiyitemp=weiyi;
								for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
									double sumtime=0.0;
									for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
										for (int n = 0; n < listProjectTransfer.size(); n++) {
											//if (listFirsttaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
											if (firstTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
												if (allMonthListManHourDtoList.get(s).get(j).getProjectID()==listProjectTransfer.get(n).getProjectID()) {
													if (listtransferNo.get(m).equals(listProjectTransfer.get(n).getTransferNo())) {
														sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
													}
												}
											}
										}
									}
									sumtimetemp+=sumtime;
									Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
									sheetSeven.addCell(labelac3);
									Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
									sheetSeven.addCell(labelftg);
									sumtempTol[s]+=sumtime;
									weiyitemp=weiyitemp+2;
								}
								Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
								sheetSeven.addCell(labeladb23);
								Number labelbgt=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
								sheetSeven.addCell(labelbgt);
								Number labeler=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
								sheetSeven.addCell(labeler);
								rownum++;
							}
							int weiyite=weiyi;
							Label labelab12a = new Label(2, rownum, "Tol",formatleft);
							sheetSeven.addCell(labelab12a);
							for (int i = 0; i < sumtempTol.length; i++) {
								Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formattitleNum);
								sheetSeven.addCell(labelaca);
								Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formattitleNum);
								sheetSeven.addCell(labelftg);
								sumtotalCT+=sumtempTol[i];
								weiyite=weiyite+2;
							}
							Number labelada=new Number(27, rownum, sumtotalCT,formattitleNum);
							sheetSeven.addCell(labelada);
							Number labelbgt=new Number(28, rownum,Arith.div(sumtotalCT,personMonthTotal),formattitleNum);
							sheetSeven.addCell(labelbgt);
							Number labeler=new Number(29, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formattitleNum);
							sheetSeven.addCell(labeler);
							//sheetSeven.mergeCells(0,rownum-listFirsttaskname.size(),1,rownum);
							//Label labelaea1 = new Label(0, rownum-listFirsttaskname.size(),listtransferNo.get(m),formatgr);
							sheetSeven.mergeCells(0,rownum-listFirstTask.size(),1,rownum);
							Label labelaea1 = new Label(0, rownum-listFirstTask.size(),listtransferNo.get(m),formatgr);
							sheetSeven.addCell(labelaea1);
							rownum++;
					}
					else {
						double sumtotalCT=0.0;
						double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
						Task firstTask = new Task();
							//for (int i = 0; i < listFirsttaskname.size(); i++) {
							for (int i = 0; i < listFirstTask.size(); i++) {
								firstTask = listFirstTask.get(i);
								//Label labelab1ab = new Label(2, rownum, listFirsttaskname.get(i),formatleft);
								Label labelab1ab = new Label(2, rownum, firstTask.getTxt(),formatleft);
								sheetSeven.addCell(labelab1ab);
								double sumtimetemp=0.0;
								int weiyitemp=weiyi;
								for (int s = 0; s < allMonthListManHourDtoList.size(); s++) {
									double sumtime=0.0;
									for (int j = 0; j < allMonthListManHourDtoList.get(s).size(); j++) {
										for (int n = 0; n < listProjectTransfer.size(); n++) {
											//if (listFirsttaskname.get(i).equals(allMonthListManHourDtoList.get(s).get(j).getTask())) {
											if (firstTask.getVal().equals(String.valueOf(allMonthListManHourDtoList.get(s).get(j).getTaskID()))) {
												if (allMonthListManHourDtoList.get(s).get(j).getProjectID()==listProjectTransfer.get(n).getProjectID()) {
													if (("").equals(listProjectTransfer.get(n).getTransferNo())) {
														sumtime = Arith.add(sumtime,allMonthListManHourDtoList.get(s).get(j).getTimes());
													}
												}
											}
										}
									}
									sumtimetemp+=sumtime;
									Number labelac3=new Number(weiyitemp, rownum,sumtime,formatright);
									sheetSeven.addCell(labelac3);
									Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(s)*8)),formatright);
									sheetSeven.addCell(labelftg);
									sumtempTol[s]+=sumtime;
									weiyitemp=weiyitemp+2;
								}
								Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
								sheetSeven.addCell(labeladb23);
								Number labelbgt=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
								sheetSeven.addCell(labelbgt);
								Number labeler=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
								sheetSeven.addCell(labeler);
								rownum++;
							}
							int weiyite=weiyi;
							Label labelab12a = new Label(2, rownum, "Tol",formatleft);
							sheetSeven.addCell(labelab12a);
							for (int i = 0; i < sumtempTol.length; i++) {
								Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formattitleNum);
								sheetSeven.addCell(labelaca);
								Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formattitleNum);
								sheetSeven.addCell(labelftg);
								sumtotalCT+=sumtempTol[i];
								weiyite=weiyite+2;
							}
							Number labelada=new Number(27, rownum, sumtotalCT,formattitleNum);
							sheetSeven.addCell(labelada);
							Number labelbgt=new Number(28, rownum,Arith.div(sumtotalCT,personMonthTotal),formattitleNum);
							sheetSeven.addCell(labelbgt);
							Number labeler=new Number(29, rownum,Arith.div(sumtotalCT, basetotalnum, 4)*100,formattitleNum);
							sheetSeven.addCell(labeler);
							//sheetSeven.mergeCells(0,rownum-listFirsttaskname.size(),1,rownum);
							//Label labelaea1 = new Label(0, rownum-listFirsttaskname.size(),listtransferNo.get(m),formatgr);
							sheetSeven.mergeCells(0,rownum-listFirstTask.size(),1,rownum);
							Label labelaea1 = new Label(0, rownum-listFirstTask.size(),listtransferNo.get(m),formatgr);
							sheetSeven.addCell(labelaea1);
							rownum++;
					}
					
			}
			//if (department.equals("工程设计部")) {
			if (DepartmentEnum.GCSJB.getName().equals(department)) {
				double sumtotalCTtemp=0.0;
				double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
				for (int i = 0; i < Parma.strAdditionalProjDesignerCT.length; i++) {
					Label labelab1ab = new Label(2, rownum, Parma.strAdditionalProjDesignerCT[i],formatleft);
					sheetSeven.addCell(labelab1ab);
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
						sheetSeven.addCell(labelac3);
						Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(j)*8)),formatright);
						sheetSeven.addCell(labelftg);
						sumtempTol[j]+=sumtime;
						weiyitemp=weiyitemp+2;
					}
					Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
					sheetSeven.addCell(labeladb23);
					Number labelbgt=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
					sheetSeven.addCell(labelbgt);
					Number labeler=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
					sheetSeven.addCell(labeler);
					rownum++;
				}
				int weiyite=weiyi;
				Label labelab12a = new Label(2, rownum, "Tol",formatleft);
				sheetSeven.addCell(labelab12a);
				for (int i = 0; i < sumtempTol.length; i++) {
					Number labelaca=new Number(weiyite, rownum,sumtempTol[i],formattitleNum);
					sheetSeven.addCell(labelaca);
					Number labelftg=new Number(weiyite+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formattitleNum);
					sheetSeven.addCell(labelftg);
					sumtotalCTtemp+=sumtempTol[i];
					weiyite=weiyite+2;
				}
				Number labelada=new Number(27, rownum, sumtotalCTtemp,formattitleNum);
				sheetSeven.addCell(labelada);
				Number labelbgt=new Number(28, rownum,Arith.div(sumtotalCTtemp,personMonthTotal),formattitleNum);
				sheetSeven.addCell(labelbgt);
				Number labeler=new Number(29, rownum,Arith.div(sumtotalCTtemp, basetotalnum, 4)*100,formattitleNum);
				sheetSeven.addCell(labeler);
				sheetSeven.mergeCells(0,rownum-Parma.strAdditionalProjDesignerCT.length,1,rownum);
				Label labelaea1 = new Label(0, rownum-Parma.strAdditionalProjDesignerCT.length,"185 工程设计部CT",formatgr);
				sheetSeven.addCell(labelaea1);
				rownum++;
				
				double[] sumtempToltemp=new double[allMonthListManHourDtoList.size()];
				double sumtotalDCOEtemp=0.0;
				for (int i = 0; i < Parma.strAdditionalProjDesignerDCOE.length; i++) {
					Label labelab1ab = new Label(2, rownum, Parma.strAdditionalProjDesignerDCOE[i],formatleft);
					sheetSeven.addCell(labelab1ab);
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
						sheetSeven.addCell(labelac3);
						Number labelftg=new Number(weiyitemp+1, rownum, Arith.div(sumtime, (workDayslist.get(mk)*8)),formatright);
						sheetSeven.addCell(labelftg);
						sumtempToltemp[mk]+=sumtime;
						weiyitemp=weiyitemp+2;
					}
					Number labeladb23=new Number(27, rownum,sumtimetemp,formatright);
					sheetSeven.addCell(labeladb23);
					Number labelcdrt=new Number(28, rownum,Arith.div(sumtimetemp,personMonthTotal),formatright);
					sheetSeven.addCell(labelcdrt);
					Number labeldrt=new Number(29, rownum,Arith.div(sumtimetemp, basetotalnum, 4)*100,formatright);
					sheetSeven.addCell(labeldrt);
					rownum++;
				}
				int weiyitetemp=weiyi;
				
				Label labelab12am = new Label(2, rownum, "Tol",formatleft);
				sheetSeven.addCell(labelab12am);
				for (int i = 0; i < sumtempToltemp.length; i++) {
					Number labelaca=new Number(weiyitetemp, rownum,sumtempToltemp[i],formattitleNum);
					sheetSeven.addCell(labelaca);
					Number labelftg=new Number(weiyitetemp+1, rownum, Arith.div(sumtempToltemp[i], (workDayslist.get(i)*8)),formattitleNum);
					sheetSeven.addCell(labelftg);
					sumtotalDCOEtemp+=sumtempToltemp[i];
					weiyitetemp=weiyitetemp+2;
				}
				Number labeladam=new Number(27, rownum, sumtotalDCOEtemp,formattitleNum);
				sheetSeven.addCell(labeladam);
				Number labelcdrt=new Number(28, rownum,Arith.div(sumtotalDCOEtemp,personMonthTotal),formattitleNum);
				sheetSeven.addCell(labelcdrt);
				Number labeldrt=new Number(29, rownum,Arith.div(sumtotalDCOEtemp, basetotalnum, 4)*100,formattitleNum);
				sheetSeven.addCell(labeldrt);
				sheetSeven.mergeCells(0,rownum-Parma.strAdditionalProjDesignerDCOE.length,1,rownum);
				Label labelaea1m = new Label(0, rownum-Parma.strAdditionalProjDesignerDCOE.length, "185 工程设计部DCOE",formatgr);
				sheetSeven.addCell(labelaea1m);
				rownum++;
			}
			/**
			 * 对剩余的空白部分进行单元格处理
			 */ 
			WritableCellFormat formatg=new WritableCellFormat(fonttitleor);  
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rownum; j++) {
					for (int k = 1; k <=Parma.strSheetFiveTitle.length ; k++) {
						Cell celletemp1 = sheetSeven.getCell(k, j);
						String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							Label labelhLabel = new Label(k, j, null ,formatg);
							sheetSeven.addCell(labelhLabel);
						}
						
					}	
				
			}
			book1.write();
			book1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
