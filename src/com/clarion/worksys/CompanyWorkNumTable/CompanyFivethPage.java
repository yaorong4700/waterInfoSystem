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
 * @author chen_weijun
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompanyFivethPage {
/**
 * 生成公司整体第五页（先行开发）的数据
 * @param department   此处为“公司整体”
 * @param listProject   项目的集合
 * @param year   起始年份
 * @param month    起始月份
 * @param endyear   截止年份
 * @param endmonth   截止月份
 * @param filenameString   唯一性字符串，防止文件重名
 * @param allMonthListManHourDtoList   所有工数信息
 * @param listProjectTasks  所有作业类型的集合
 * @param projectNum   项目数
 * @param workDayslist   每个月上班天数
 */
	public void updateFivethXLS(String department,List<Project> listProject,int year, int month,
			                     int endyear, int endmonth, String filenameString,List<List<ManHourDto>> allMonthListManHourDtoList,
			                     List<Project_task> listProjectTasks,List<Integer> projectNum,List<Integer> workDayslist) {

		
		try {
			File file = new File("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"+filenameString); 
			if (!file.exists()) {
				file.mkdirs();
			}
			File file1 = new File(Parma.TEMP_FILEFirstAddress); 
			if (!file1.exists()) {
				file1.mkdirs();
			}
			/*for (int op = 0; op < projectNum.size(); op++) {
				
			}*/
			String strnameString1=Parma.TEMP_FILEFirstAddress+"/"+"集計データ"+department+year+"年"+month+"月"+".xls";//生成的模板存储所在地
			String strnameString2="d:/"+department+"/"+department+"/"+year+"/"+month+"月"+filenameString+"/"+"集計データ"+department+year+"年"+month+"月"+"_"+endyear+"年"+endmonth+"月"+".xls";//由模板生成的最终文件所在地址
			//strnameString1=new  String(strnameString1.getBytes(),"UTF-8");
			//String strnameString1=Parma.TEMP_FILEFirstAddress+"/"+new String(("集計データ"+department+year+"年"+month+"月"+".xls").getBytes(),"UTF-8");
			//strnameString2=new  String(strnameString2.getBytes(),"UTF-8");
			//String strnameString2="d:/"+department+"/"+department+"/"+year+"/"+month+"月"+filenameString+"/"+new String(("集計データ"+department+year+"年"+month+"月"+"_"+endyear+"年"+endmonth+"月"+".xls").getBytes(),"UTF-8");
			
			Workbook wb = Workbook.getWorkbook(new File(strnameString1));
			
			WritableWorkbook book1 =Workbook.createWorkbook(new File(strnameString2), wb);
			//add a Sheet.
			WritableSheet sheetFive = book1.createSheet(Parma.strCategoryName[0], 4);
			sheetFive.setColumnView(2,36); 
			sheetFive.setColumnView(1,36); 
			sheetFive.setColumnView(2,36); 
			sheetFive.setColumnView(1,36); 
			
			
			WritableFont fontbl= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD); 
			WritableCellFormat formatbl=new WritableCellFormat(fontbl); 
			formatbl.setAlignment(jxl.format.Alignment.CENTRE); 
			formatbl.setBackground(Colour.LIGHT_TURQUOISE);
			formatbl.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			 
			WritableCellFormat formatNum=new WritableCellFormat(fontbl); 
			formatNum.setAlignment(jxl.format.Alignment.RIGHT); 
			formatNum.setBackground(Colour.LIGHT_TURQUOISE);
			formatNum.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat formatCategory=new WritableCellFormat(fontbl); 
			formatCategory.setAlignment(jxl.format.Alignment.LEFT); 
			formatCategory.setBackground(Colour.LIGHT_TURQUOISE);
			formatCategory.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableFont fontPerson= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);//深蓝字体
			WritableCellFormat formatPerson=new WritableCellFormat(fontPerson); 
			formatPerson.setAlignment(jxl.format.Alignment.CENTRE); 
			formatPerson.setBackground(Colour.LIGHT_TURQUOISE);
			formatPerson.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			int rownum=2;
			
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			Label labelaLabel = new Label(0, 0, "集记日",formatbl);
			sheetFive.addCell(labelaLabel);
			sheetFive.mergeCells(1, 0, 2, 0);
			Label labelaLabelmhk = new Label(1, 0, String.valueOf(yearsys)+"年"+String.valueOf(monthsys)+"月"+String.valueOf(daysys)+"日",formatbl);
			sheetFive.addCell(labelaLabelmhk);
			Label labelbLabel = new Label(0, 1, "对象",formatbl);
			sheetFive.addCell(labelbLabel);
			sheetFive.mergeCells(1, 1, 2, 1);
			Label labelbLabelfg = new Label(1, 1, department,formatbl);
			sheetFive.addCell(labelbLabelfg);
			int weiyitemp=0;
			if (month>=4&&month<=12) {
				weiyitemp=(month-4)*2+3;
			}
			if (month>=1&&month<=3) {
				weiyitemp=month*2+19;
			}
			double personMonthTotal=0.0f;
			for (int i = 0; i < workDayslist.size(); i++) {
				personMonthTotal+=workDayslist.get(i)*8;
			}
			/**
			 * 开发工数CT项目
			 */
			
			double sumTotal=0.0;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				for (int mk = 0; mk < allMonthListManHourDtoList.get(i).size(); mk++) {
					if (("先行开发").equals(allMonthListManHourDtoList.get(i).get(mk).getCategory())) {
						sumTotal = Arith.add(sumTotal,allMonthListManHourDtoList.get(i).get(mk).getTimes());
					}
				}
			}
			for (int i = 0; i < Parma.strInAdvanceSegement.length; i++) {
				Label labelajkl = new Label(i, rownum,Parma.strInAdvanceSegement[i],formatbl);
				sheetFive.addCell(labelajkl);
			}
			for (int i = Parma.strInAdvanceSegement.length; i < Parma.strmonth.length+Parma.strInAdvanceSegement.length; i++) {
				if (i%2==0) {
					Label labelajkl = new Label(i, rownum,Parma.strmonth[i-Parma.strInAdvanceSegement.length],formatPerson);
					sheetFive.addCell(labelajkl);
				}
				else {
					Label labelajkl = new Label(i, rownum,Parma.strmonth[i-Parma.strInAdvanceSegement.length],formatbl);
					sheetFive.addCell(labelajkl);
				}
				
			}
			List<Project_task> XianXiangKaiFaTasks=new ArrayList<Project_task>();
			for (int i = 0; i < listProjectTasks.size(); i++) {
				if (("先行开发").equals(listProjectTasks.get(i).getCategory())) {
					XianXiangKaiFaTasks.add(listProjectTasks.get(i));
				}
			}
			for (int i = 0; i < Parma.strInAdvanceDepart.length; i++) {
				rownum++;
				//List<String> tasktemp=new ArrayList<String>();
				List<Project_task> tasktemp=new ArrayList<Project_task>();
				for (int j = 0; j < XianXiangKaiFaTasks.size(); j++) {
					if (Parma.strInAdvanceDepart[i].equals(XianXiangKaiFaTasks.get(j).getDepartment())) {
							//tasktemp.add(XianXiangKaiFaTasks.get(j).getTask());
						tasktemp.add(XianXiangKaiFaTasks.get(j));
					}
				}
				for (int j = 0; j < tasktemp.size(); j++) {
					Label labelajkl = new Label(0, rownum,"先行开发",formatbl);
					sheetFive.addCell(labelajkl);
					Label labelsd = new Label(1, rownum,Parma.strInAdvanceDepart[i],formatbl);
					sheetFive.addCell(labelsd);
					//Label labelahj = new Label(2, rownum,tasktemp.get(j),formatCategory);
					Label labelahj = new Label(2, rownum,tasktemp.get(j).getTask(),formatCategory);
					sheetFive.addCell(labelahj);
					int weiyi=weiyitemp;
					double sumtasktemp=0.0;
					for (int m = 0; m < allMonthListManHourDtoList.size(); m++) {
						double sumtask=0.0;
						for (int k = 0; k < allMonthListManHourDtoList.get(m).size(); k++) {
							if (("先行开发").equals(allMonthListManHourDtoList.get(m).get(k).getCategory())) {
								//if (tasktemp.get(j).equals(allMonthListManHourDtoList.get(m).get(k).getTask())) {
								if (tasktemp.get(j).getTaskID().equals(allMonthListManHourDtoList.get(m).get(k).getTaskID())) {
										if (projectNum.get(i)==allMonthListManHourDtoList.get(m).get(k).getProjectID()) {
											sumtask = Arith.add(sumtask,allMonthListManHourDtoList.get(m).get(k).getTimes());
										}
								}
							}
						}
						Number labelnLabel_d3wabm=new Number(weiyi, rownum, sumtask,formatNum);
						sheetFive.addCell(labelnLabel_d3wabm); 
						Number labelftg=new Number(weiyi+1, rownum, Arith.div(sumtask, (workDayslist.get(m)*8)),formatNum);
						sheetFive.addCell(labelftg);
						sumtasktemp+=sumtask;
						weiyi+=2;
					}
					Number labelajfy=new Number(27, rownum, sumtasktemp,formatNum);
					sheetFive.addCell(labelajfy);
					Number labeledr=new Number(28, rownum,Arith.div(sumtasktemp,personMonthTotal),formatNum);
					sheetFive.addCell(labeledr);
					Number labelfrtf=new Number(29, rownum,Arith.div(sumtasktemp, sumTotal, 4)*100,formatNum);
					sheetFive.addCell(labelfrtf);
					rownum++;
				}
			}
			/**
			 * 全工数状況
			 */
			rownum++;
			rownum++;
			sheetFive.mergeCells(0, rownum, 2, rownum);
			Label labelqwLabel= new Label(0, rownum, Parma.strHomeTitle[0],formatbl);
			sheetFive.addCell(labelqwLabel);
			for(int i=3;i<Parma.strmonth.length+3;i++)
			{
				if (i%2==0) {
					Label label = new Label(i, rownum, Parma.strmonth[i-3],formatPerson);
					sheetFive.addCell(label);
				}
				else {
					Label label = new Label(i, rownum, Parma.strmonth[i-3],formatbl);
					sheetFive.addCell(label);
				}
			}
			rownum++;
			double[] sumtempTol=new double[allMonthListManHourDtoList.size()];
			for (int i = 0; i < Parma.strInAdvanceDepart.length; i++) {
				sheetFive.mergeCells(0, rownum, 2, rownum);
				Label labelqwsh= new Label(0, rownum, Parma.strInAdvanceDepart[i],formatbl);
				sheetFive.addCell(labelqwsh);
				int weiyi=weiyitemp;
				double sumtasktemp=0.0;
				for (int k = 0; k < allMonthListManHourDtoList.size(); k++) {
					double sumtemp=0.0;
					for (int j = 0; j < allMonthListManHourDtoList.get(k).size(); j++) {
						if (projectNum.get(i)==allMonthListManHourDtoList.get(k).get(j).getProjectID()) {
							if (("先行开发").equals(allMonthListManHourDtoList.get(k).get(j).getCategory())) {
								sumtemp = Arith.add(sumtemp,allMonthListManHourDtoList.get(k).get(j).getTimes());
							}
						}
					}
					Number labelnLabel_d3wabm=new Number(weiyi, rownum,sumtemp,formatNum);
					sheetFive.addCell(labelnLabel_d3wabm); 
					Number labelftg=new Number(weiyi+1, rownum, Arith.div(sumtemp, (workDayslist.get(k)*8)),formatNum);
					sheetFive.addCell(labelftg);
					sumtasktemp+=sumtemp;
					sumtempTol[k]+=sumtemp;
					weiyi+=2;
				}
				Number labelajfy=new Number(27, rownum, sumtasktemp,formatNum);
				sheetFive.addCell(labelajfy);
				Number labeledr=new Number(28, rownum,Arith.div(sumtasktemp,personMonthTotal),formatNum);
				sheetFive.addCell(labeledr);
				Number labelfrtf=new Number(29, rownum,Arith.div(sumtasktemp, sumTotal, 4)*100,formatNum);
				sheetFive.addCell(labelfrtf);
				rownum++;
			}
			int weiyi=weiyitemp;
			sheetFive.mergeCells(0, rownum, 2, rownum);
			Label labelqwsh= new Label(0, rownum, Parma.strHomeTitle[0]+" 合计",formatbl);
			sheetFive.addCell(labelqwsh);
			double tempheji=0.0;
			for (int i = 0; i < allMonthListManHourDtoList.size(); i++) {
				Number labelnLabel_d3wabm=new Number(weiyi, rownum, sumtempTol[i],formatNum);
				sheetFive.addCell(labelnLabel_d3wabm);
				Number labelftg=new Number(weiyi+1, rownum, Arith.div(sumtempTol[i], (workDayslist.get(i)*8)),formatNum);
				sheetFive.addCell(labelftg);
				tempheji+=sumtempTol[i];
				weiyi+=2;
			}
			Number labelajfy=new Number(27, rownum, tempheji,formatNum);
			sheetFive.addCell(labelajfy);
			Number labeledr=new Number(28, rownum,Arith.div(tempheji,personMonthTotal),formatNum);
			sheetFive.addCell(labeledr);
			Number labelfrtf=new Number(29, rownum,Arith.div(tempheji, sumTotal, 4)*100,formatNum);
			sheetFive.addCell(labelfrtf);
			rownum++;
			
			/**
			* 对剩余的空白部分进行单元格处理
			*/ 
			WritableCellFormat formatg=new WritableCellFormat(fontbl); 
			formatg.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			for (int j = 2; j < rownum; j++) {
			Cell celletemp = sheetFive.getCell(0, j);
			String resultemp = celletemp.getContents();
			if (!resultemp.isEmpty()) {
				for (int k = 1; k <3+Parma.strmonth.length ; k++) {
				Cell celletemp1 = sheetFive.getCell(k, j);
				String resultemp1 = celletemp1.getContents();
					if (resultemp1.isEmpty()) {
						Label labelhLabel = new Label(k, j, null ,formatg);
						sheetFive.addCell(labelhLabel);
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
