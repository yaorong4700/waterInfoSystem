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
import java.util.HashMap;
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
public class PersonSixPage_ForCt {
/**
* 生成个人集计第六页（PJ毎のプロセス工数）信息_ForCt
* @param user   出力者
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
* @param workDayslist         本部门各月上班天数
*/
	public void updatePersonWorkNumSixXLS_ForCt(String user, String department,String branch,List<Project> listProject,int year, int month,
			                     int endyear, int endmonth,String uniqueString,List<List<ManHourDto>> allMonthListManHourDtoList,
			                     List<Project_task> listProjectTasks,List<Integer> workDayslist) {
		
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
			WritableSheet sheetSix = book1.createSheet("PJ毎のプロセス工数", 5);
			sheetSix.setColumnView(0,3);
			sheetSix.setColumnView(1,9);
			sheetSix.setColumnView(2,42);
			sheetSix.setColumnView(3,40);
			for (int i =4; i <= 20; i++) {
				sheetSix.setColumnView(i,10);
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
			formato2.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);
			formato2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

			WritableCellFormat formato3=new WritableCellFormat(fontgr); 
			formato3.setAlignment(jxl.format.Alignment.CENTRE); 
			formato3.setBackground(Colour.LIGHT_TURQUOISE);
			formato3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

			WritableCellFormat formato4=new WritableCellFormat(fontgr); 
			formato4.setAlignment(jxl.format.Alignment.CENTRE); 
			formato4.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);
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
				weiyi=(month-4)+4;
			}
			if (month>=1&&month<=3) {
				weiyi=month+12;
			}

		    Label label0_0 = new Label(1, 1, department + "/" + branch + " " + year + "年" + month + "月～" + endyear + "年" + endmonth + "月" + "工数実績データ",formato_title1);
			Label label0_1 = new Label(6, 1, "PJ毎のプロセス工数",formato_title1);
			Label label0_2 = new Label(1, 3,"出力者：" + user,formato_title2);
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar calendar=Calendar.getInstance();//获得系统当前日期        
	    	Date date = calendar.getTime();
			Label label0_3 = new Label(3, 3,"出力日：" + df.format(date),formato_title2);
			sheetSix.addCell(label0_0);
			sheetSix.addCell(label0_1);
			sheetSix.addCell(label0_2);
			sheetSix.addCell(label0_3);

			/**
			 * PJ情報
			 */
			List<Project> listDepartSencouProjects=new ArrayList<Project>();
			List<Project_task> listDepartSencouTasks=new ArrayList<Project_task>();
			HashMap<String,List<Project_task>> listTaskscon=new HashMap<String,List<Project_task>>();
			for (int j = 0;j < 4 ;j++){
				listDepartSencouTasks=new ArrayList<Project_task>();
				for (int i = 0; i < listProject.size(); i++) {
					if (Parma.strTotalWorkNumName_ForCt[j].equals(listProject.get(i).getCategory())) {
						listDepartSencouProjects.add(listProject.get(i));
					}
					
				}
				for (int i = 0; i < listProjectTasks.size(); i++) {
					if (Parma.strTotalWorkNumName_ForCt[j].equals(listProjectTasks.get(i).getCategory())) {
						listDepartSencouTasks.add(listProjectTasks.get(i));
					}
				}
				if(listDepartSencouTasks.size()>0){
					listTaskscon.put(Parma.strTotalWorkNumName_ForCt[j],listDepartSencouTasks);
				}
			}
			int totalrowsum=0;
			for (int k = 0; k < listDepartSencouProjects.size(); k++) {
				totalrowsum=totalrowsum+listTaskscon.get(listDepartSencouProjects.get(k).getCategory()).size();
			}
			
			int rownum = 4;
			Label label0_4 = new Label(1, rownum, "PJ No.",formato3);
			sheetSix.addCell(label0_4);
			Label label0_5 = new Label(2, rownum, "PJ名（H）",formato3);
			sheetSix.addCell(label0_5);
			Label label0_6 = new Label(3, rownum, "プロセス名",formato3);
			sheetSix.addCell(label0_6);
			Label label0_7 = new Label(1, rownum + totalrowsum + 3, "PJ No.",formato3);
			sheetSix.addCell(label0_7);
			Label label0_8 = new Label(2, rownum + totalrowsum + 3, "PJ名（人月）",formato3);
			sheetSix.addCell(label0_8);
			Label label0_9 = new Label(3, rownum + totalrowsum + 3, "プロセス名",formato3);
			sheetSix.addCell(label0_9);
			for(int i=0;i<Parma.strmonth_ForCt.length;i++)
			{
				Label label_H = new Label(i+4, rownum, Parma.strmonth_ForCt[i],formato3);
				sheetSix.addCell(label_H);
				Label label_M = new Label(i+4, rownum + totalrowsum + 3, Parma.strmonth_ForCt[i],formato3);
				sheetSix.addCell(label_M);
			}
			rownum++;

			for (int k = 0; k < listDepartSencouProjects.size(); k++) {
				listDepartSencouTasks=listTaskscon.get(listDepartSencouProjects.get(k).getCategory());
				sheetSix.mergeCells(1,rownum,1,rownum + listDepartSencouTasks.size() - 1);
				Label label_PJNO_H = new Label(1, rownum, listDepartSencouProjects.get(k).getPJNo(),formato4);
				sheetSix.addCell(label_PJNO_H);
				sheetSix.mergeCells(2,rownum,2,rownum + listDepartSencouTasks.size() - 1);
				Label label_PJNAME_H = new Label(2, rownum, listDepartSencouProjects.get(k).getProjectName(),formato2);
				sheetSix.addCell(label_PJNAME_H);
				sheetSix.mergeCells(1,rownum + totalrowsum + 3,1,rownum + totalrowsum + 3 + listDepartSencouTasks.size() - 1);
				Label label_PJNO_M = new Label(1, rownum + totalrowsum + 3, listDepartSencouProjects.get(k).getPJNo(),formato4);
				sheetSix.addCell(label_PJNO_M);
				sheetSix.mergeCells(2,rownum + totalrowsum + 3,2,rownum + totalrowsum + 3 + listDepartSencouTasks.size() - 1);
				Label label_PJNAME_M = new Label(2, rownum + totalrowsum + 3, listDepartSencouProjects.get(k).getProjectName(),formato2);
				sheetSix.addCell(label_PJNAME_M);
				for (int i = 0; i < listDepartSencouTasks.size(); i++) {
					int weiyiStep=weiyi;
					Label label_proN_D=new Label(3, rownum, listDepartSencouTasks.get(i).getTask(),formato2);
					sheetSix.addCell(label_proN_D);
					Label label_proN_M=new Label(3, rownum + totalrowsum + 3, listDepartSencouTasks.get(i).getTask(),formato2);
					sheetSix.addCell(label_proN_M);
					for (int m = 0;m < allMonthListManHourDtoList.size();m++) {
						double sumTask=0.0;
						for (int j = 0; j < allMonthListManHourDtoList.get(m).size(); j++) {
							if (listDepartSencouProjects.get(k).getProjectID() != null && listDepartSencouProjects.get(k).getProjectID().equals(allMonthListManHourDtoList.get(m).get(j).getProjectID())) {
								if (listDepartSencouTasks.get(i).getTaskID() != null && listDepartSencouTasks.get(i).getTaskID().equals(allMonthListManHourDtoList.get(m).get(j).getTaskID())) {
									sumTask = Arith.add(sumTask,allMonthListManHourDtoList.get(m).get(j).getTimes());
								}
							}
						}
						Number label_proH=new Number(weiyiStep, rownum, sumTask,formatoNum2);
						sheetSix.addCell(label_proH);
						Number label_proM=new Number(weiyiStep, rownum + totalrowsum + 3, Arith.div(sumTask, (workDayslist.get(m)*8)),formatoNum2);
						sheetSix.addCell(label_proM);
						weiyiStep += 1;
					}
					rownum++;
				}
			}
			rownum = rownum + totalrowsum + 3;

			/**
			 * 对剩余的空白部分进行单元格处理
			 */ 
			for (int j = 5; j < rownum; j++) {
				Cell celletemp = sheetSix.getCell(3, j);
				String resultemp = celletemp.getContents();
				if (!resultemp.isEmpty()) {
					for (int k = 3; k <=Parma.strmonth_ForCt.length+2; k++) {
					Cell celletemp1 = sheetSix.getCell(k, j);
					String resultemp1 = celletemp1.getContents();
						if (resultemp1.isEmpty()) {
							Label labelhLabel = new Label(k, j, null ,formatoNum2);
							sheetSix.addCell(labelhLabel);
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
