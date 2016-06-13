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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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

import com.clarion.worksys.entity.Component;
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
public class PersonFivethPage_ForCt {
/**
 * 生成个人集计第五页（量産後の不具合対応）信息_ForCt
 * @param user   出力者
 * @param department  部门名  
 * @param branch  课别名
 * @param listProject  所有项目名称的集合
 * @param year  起始年份
 * @param month  起始月份
 * @param endyear  截止年份
 * @param endmonth    截止月份
 * @param uniqueString  唯一性字符串
 * @param allMonthListManHourDtoList  所有工数数据的集合
 * @param listProjectTasks   所有作业类型的集合
 * @param workDayslist  每个月上班天数的集合
 */
	public void updatePersonWorkNumFivethXLS_ForCt(String user, String department,String branch,List<Project> listProject,int year, int month,
			int endyear, int endmonth, String uniqueString,List<List<ManHourDto>> allMonthListManHourDtoList,List<Project_task> listProjectTasks
			                     ,List<Integer> workDayslist) {
		
		try {
			File file = new File(Parma.TEMP_FILEFiveAddress); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEFirstAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			String strnameString1=Parma.TEMP_FILEFiveAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//生成的模板存储所在地
			String strnameString2=Parma.TEMP_FILEFirstAddress+"/"+"集計データ"+department+year+"年"+month+"月"+uniqueString+".xls";//由模板生成的最终文件所在地址
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetTwo = book1.createSheet(Parma.strTotalWorkNumName_ForCt[3], 4);
			sheetTwo.setColumnView(0,3);
			sheetTwo.setColumnView(1,42);
			for (int i =2; i <= 20; i++) {
				sheetTwo.setColumnView(i,10);
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
			Label label0_1 = new Label(9, 1, Parma.strTotalWorkNumName_ForCt[3],formato_title1);
			Label label0_2 = new Label(1, 3,"出力者：" + user,formato_title2);
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar calendar=Calendar.getInstance();//获得系统当前日期        
	    	Date date = calendar.getTime();
			Label label0_3 = new Label(2, 3,"出力日：" + df.format(date),formato_title2);
			sheetTwo.addCell(label0_0);
			sheetTwo.addCell(label0_1);
			sheetTwo.addCell(label0_2);
			sheetTwo.addCell(label0_3);

			/**
			 * プロセス名
			 */
			List<Project_task> listDepartSencouTasks=new ArrayList<Project_task>();
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (Parma.strTotalWorkNumName_ForCt[3].equals(listProjectTasks.get(i).getCategory())) {
					listDepartSencouTasks.add(listProjectTasks.get(i));
				}
			}
			int rownum = 4;
			Label label0_4 = new Label(1, rownum, "プロセス名（H）",formato3);
			sheetTwo.addCell(label0_4);
			Label label0_5 = new Label(1, rownum + listDepartSencouTasks.size() + 3, "プロセス名（人月）",formato3);
			sheetTwo.addCell(label0_5);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label_H = new Label(i+2, rownum, Parma.strmonth_ForCt[i],formato3);
				sheetTwo.addCell(label_H);
				Label label_M = new Label(i+2, rownum + listDepartSencouTasks.size() + 3, Parma.strmonth_ForCt[i],formato3);
				sheetTwo.addCell(label_M);
			}
			rownum++;

			for (int i = 0; i < listDepartSencouTasks.size(); i++) {
				int weiyiStep=weiyi;
				Label label_proN_D=new Label(1, rownum, listDepartSencouTasks.get(i).getTask(),formato2);
				sheetTwo.addCell(label_proN_D);
				Label label_proN_M=new Label(1, rownum + listDepartSencouTasks.size() + 3, listDepartSencouTasks.get(i).getTask(),formato2);
				sheetTwo.addCell(label_proN_M);
				for (int m = 0;m < allMonthListManHourDtoList.size();m++) {
					double sumTask=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (Parma.strTotalWorkNumName_ForCt[3].equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
							if (listDepartSencouTasks.get(i).getTaskID() != null && listDepartSencouTasks.get(i).getTaskID().equals(allMonthListManHourDtoList.get(m).get(j).getTaskID())) {
								sumTask = Arith.add(sumTask,allMonthListManHourDtoList.get(m).get(j).getTimes());
							}
						}
					}
					Number label_proH=new Number(weiyiStep, rownum, sumTask,formatoNum2);
					sheetTwo.addCell(label_proH);
					Number label_proM=new Number(weiyiStep, rownum + listDepartSencouTasks.size() + 3, Arith.div(sumTask, (workDayslist.get(m)*8)),formatoNum2);
					sheetTwo.addCell(label_proM);
					weiyiStep += 1;
				}
				rownum++;
			}
			rownum = rownum + listDepartSencouTasks.size() + 5;

			/**
			 * コンポーネント名
			 */
			HashSet<String> tempH = new HashSet<String>();
			List<Component> listDepartSencouCom=new ArrayList<Component>();
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int j = 0; j < allMonthListManHourDtoList.get(i).size(); j++) {
					if (Parma.strTotalWorkNumName_ForCt[3].equals(allMonthListManHourDtoList.get(i).get(j).getCategory())) {
						if (!tempH.contains(allMonthListManHourDtoList.get(i).get(j).getComponentID())){
							tempH.add(allMonthListManHourDtoList.get(i).get(j).getComponentID());
							Component componentSencou = new Component();
							componentSencou.setComponentID(allMonthListManHourDtoList.get(i).get(j).getComponentID());
							componentSencou.setComponentName(allMonthListManHourDtoList.get(i).get(j).getComponentName());
							listDepartSencouCom.add(componentSencou);
						}
					}
				}
			}
			Label label0_6 = new Label(1, rownum, "コンポーネント名（H）",formato3);
			sheetTwo.addCell(label0_6);
			Label label0_7 = new Label(1, rownum + listDepartSencouCom.size() + 3, "コンポーネント名（人月）",formato3);
			sheetTwo.addCell(label0_7);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label_H = new Label(i+2, rownum, Parma.strmonth_ForCt[i],formato3);
				sheetTwo.addCell(label_H);
				Label label_M = new Label(i+2, rownum + listDepartSencouCom.size() + 3, Parma.strmonth_ForCt[i],formato3);
				sheetTwo.addCell(label_M);
			}
			rownum++;

			for (int i = 0; i < listDepartSencouCom.size(); i++) {
				int weiyiStep=weiyi;
				Label label_proN_D=new Label(1, rownum, listDepartSencouCom.get(i).getComponentName(),formato2);
				sheetTwo.addCell(label_proN_D);
				Label label_proN_M=new Label(1, rownum + listDepartSencouCom.size() + 3, listDepartSencouCom.get(i).getComponentName(),formato2);
				sheetTwo.addCell(label_proN_M);
				for (int m = 0;m < allMonthListManHourDtoList.size();m++) {
					double sumTask=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (Parma.strTotalWorkNumName_ForCt[3].equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
							if (listDepartSencouCom.get(i).getComponentID() != null && listDepartSencouCom.get(i).getComponentID().equals(allMonthListManHourDtoList.get(m).get(j).getComponentID())) {
								sumTask = Arith.add(sumTask,allMonthListManHourDtoList.get(m).get(j).getTimes());
							}
						}
					}
					Number label_proH=new Number(weiyiStep, rownum, sumTask,formatoNum2);
					sheetTwo.addCell(label_proH);
					Number label_proM=new Number(weiyiStep, rownum + listDepartSencouCom.size() + 3, Arith.div(sumTask, (workDayslist.get(m)*8)),formatoNum2);
					sheetTwo.addCell(label_proM);
					weiyiStep += 1;
				}
				rownum++;
			}
			rownum = rownum + listDepartSencouCom.size() + 5;

			/**
			 * プロジェクト名
			 */
			List<Project> listDepartSencouProjects=new ArrayList<Project>();
			for (int i = 0; i < listProject.size(); i++) {
				if (Parma.strTotalWorkNumName_ForCt[3].equals(listProject.get(i).getCategory())) {
					listDepartSencouProjects.add(listProject.get(i));
				}
			}
			Label label0_8 = new Label(1, rownum, "プロジェクト名（H）",formato3);
			sheetTwo.addCell(label0_8);
			Label label0_9 = new Label(1, rownum + listDepartSencouProjects.size() + 3, "プロジェクト名（人月）",formato3);
			sheetTwo.addCell(label0_9);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label_H = new Label(i+2, rownum, Parma.strmonth_ForCt[i],formato3);
				sheetTwo.addCell(label_H);
				Label label_M = new Label(i+2, rownum + listDepartSencouProjects.size() + 3, Parma.strmonth_ForCt[i],formato3);
				sheetTwo.addCell(label_M);
			}
			rownum++;

			for (int i = 0; i < listDepartSencouProjects.size(); i++) {
				int weiyiStep=weiyi;
				Label label_proN_D=new Label(1, rownum, listDepartSencouProjects.get(i).getProjectName(),formato2);
				sheetTwo.addCell(label_proN_D);
				Label label_proN_M=new Label(1, rownum + listDepartSencouProjects.size() + 3, listDepartSencouProjects.get(i).getProjectName(),formato2);
				sheetTwo.addCell(label_proN_M);
				for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
					double sumProject=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
						if (Parma.strTotalWorkNumName_ForCt[3].equals(allMonthListManHourDtoList.get(m).get(j).getCategory())) {
							if (listDepartSencouProjects.get(i).getProjectID() != null && listDepartSencouProjects.get(i).getProjectID().intValue()==(allMonthListManHourDtoList.get(m).get(j).getProjectID())) {
								sumProject = Arith.add(sumProject,allMonthListManHourDtoList.get(m).get(j).getTimes());
							}
						}
					}
					Number label_proH=new Number(weiyiStep, rownum,sumProject,formatoNum2);
					sheetTwo.addCell(label_proH); 
					Number label_proM=new Number(weiyiStep, rownum + listDepartSencouProjects.size() + 3, Arith.div(sumProject, (workDayslist.get(m)*8)),formatoNum2);
					sheetTwo.addCell(label_proM);
					weiyiStep += 1;
				}
				rownum++;
			}
			rownum = rownum + listDepartSencouProjects.size() + 3;

			/**
			* 对剩余的空白部分进行单元格处理
			*/ 
			for (int j = 5; j < rownum; j++) {
				Cell celletemp = sheetTwo.getCell(1, j);
				String resultemp = celletemp.getContents();
				if (!resultemp.isEmpty()) {
					for (int k = 1; k <=Parma.strmonth_ForCt.length+1; k++) {
					Cell celletemp1 = sheetTwo.getCell(k, j);
					String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							Label labelhLabel = new Label(k, j, null ,formatoNum2);
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
