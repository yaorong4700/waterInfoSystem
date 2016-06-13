package com.clarion.worksys.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.CompanyWorkNumTable.CompanyFirstPage;
import com.clarion.worksys.CompanyWorkNumTable.CompanyFivethPage;
import com.clarion.worksys.CompanyWorkNumTable.CompanyFourthPage;
import com.clarion.worksys.CompanyWorkNumTable.CompanySecondPage;
import com.clarion.worksys.CompanyWorkNumTable.CompanyThirdPage;
import com.clarion.worksys.DepartWorkNumTable.DepartFirstPage;
import com.clarion.worksys.DepartWorkNumTable.DepartFirstPage_ForCt;
import com.clarion.worksys.DepartWorkNumTable.DepartFivethPage;
import com.clarion.worksys.DepartWorkNumTable.DepartFivethPage_ForCt;
import com.clarion.worksys.DepartWorkNumTable.DepartFourthPage;
import com.clarion.worksys.DepartWorkNumTable.DepartFourthPage_ForCt;
import com.clarion.worksys.DepartWorkNumTable.DepartSecondPage;
import com.clarion.worksys.DepartWorkNumTable.DepartSecondPage_ForCt;
import com.clarion.worksys.DepartWorkNumTable.DepartSevenPage;
import com.clarion.worksys.DepartWorkNumTable.DepartSixPage;
import com.clarion.worksys.DepartWorkNumTable.DepartSixPage_ForCt;
import com.clarion.worksys.DepartWorkNumTable.DepartThirdPage;
import com.clarion.worksys.DepartWorkNumTable.DepartThirdPage_ForCt;
import com.clarion.worksys.PersonWorkNumTable.PersonFirstPage;
import com.clarion.worksys.PersonWorkNumTable.PersonFirstPage_ForCt;
import com.clarion.worksys.PersonWorkNumTable.PersonFivethPage;
import com.clarion.worksys.PersonWorkNumTable.PersonFivethPage_ForCt;
import com.clarion.worksys.PersonWorkNumTable.PersonFourthPage;
import com.clarion.worksys.PersonWorkNumTable.PersonFourthPage_ForCt;
import com.clarion.worksys.PersonWorkNumTable.PersonSecondPage;
import com.clarion.worksys.PersonWorkNumTable.PersonSecondPage_ForCt;
import com.clarion.worksys.PersonWorkNumTable.PersonSevenPage;
import com.clarion.worksys.PersonWorkNumTable.PersonSixPage;
import com.clarion.worksys.PersonWorkNumTable.PersonSixPage_ForCt;
import com.clarion.worksys.PersonWorkNumTable.PersonThirdPage;
import com.clarion.worksys.PersonWorkNumTable.PersonThirdPage_ForCt;
import com.clarion.worksys.entity.AssumeDetail;
import com.clarion.worksys.entity.CsvData;
import com.clarion.worksys.entity.ManHourDto;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Project_task;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.departmentBranch;
import com.clarion.worksys.mapper.CalendarManhourMapper;
import com.clarion.worksys.service.CalendarManhourService;
import com.clarion.worksys.util.Const.DepartmentEnum;

@Service
public class CalendarManhourServiceImpl implements CalendarManhourService{
	
	@Autowired
	private CalendarManhourMapper calendarmanhourMapper;

	public List<Map<String,String>> listAllDepart(){
		return calendarmanhourMapper.listAllDepart();
	}
	public List<Map<String,String>> listAllDepart_ForCt(){
		return calendarmanhourMapper.listAllDepart_ForCt();
	}
	public String outputDepartJijimanhour(String department,String branch,String departmentID, String branchID,int year,int month,int startday,int endyear, int endmonth,String realpath){
		String webaddrString="";
		if (branch.equals("--请选择--")|| branch.indexOf("項目を選択してください")>0){branch=null;branchID = null;}
		if (department.equals("公司整体")||department.equals("開発部門")||department.equals("開発以外")) {
			CompanyFirstPage companyFirstPagetemp=new CompanyFirstPage();
			CompanySecondPage companySecondPagetemp=new CompanySecondPage();
			CompanyThirdPage companyThirdPagetemp=new CompanyThirdPage();
			CompanyFourthPage companyFourthPagetemp=new CompanyFourthPage();
			CompanyFivethPage companyFivethPagetemp=new CompanyFivethPage();
			
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			int hoursys=c1.get(Calendar.HOUR_OF_DAY);
			int minutessys=c1.get(Calendar.MINUTE);
			int secondsys=c1.get(Calendar.SECOND);
			String filenameString=String.valueOf(yearsys)+String.valueOf(monthsys)+String.valueOf(daysys)+String.valueOf(hoursys)+String.valueOf(minutessys)+String.valueOf(secondsys); 
			List<List<ManHourDto>> allMonthListManHourDtoList=new ArrayList<List<ManHourDto>>();//符合条件的所有工数数据
			List<List<Double>> listDevelopWorkNum=new ArrayList<List<Double>>();//开发工数（开发）与开发工数（附带）
			List<List<Double>> listCTDCOEDevelopAdditional=new ArrayList<List<Double>>();//CT、DCOE、OUTOUT分别对应的开发工数（开发）与开发工数（附带）
			List<Integer> workDayslist=new ArrayList<Integer>();
			List<Integer> designStaffNum=new ArrayList<Integer>();
			List<List<AssumeDetail>> allAssumePJDetailList=new ArrayList<List<AssumeDetail>>();
			int yeartemp=year;
			int monthtemp=month;
			while (yeartemp<endyear||(yeartemp==endyear&&monthtemp<=endmonth)) {
				List<String> listtimetemp=new ArrayList<String>();
				Calendar c = Calendar.getInstance(); //获取Calendar实例        
				c.set(Calendar.YEAR, yeartemp); //设置年
				//c.set(Calendar.MONTH, monthtemp-2); //设置月        
				//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
				//月份起始日期是21或26时
				if (startday == 21 || startday == 26){
					c.set(Calendar.MONTH, monthtemp - 2); //设置月        	
				} else if (startday == 1){
					c.set(Calendar.MONTH, monthtemp - 1); //设置月
				}
				c.set(Calendar.DAY_OF_MONTH, startday); //设置月开始第一天日期
				int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
				for (int m=1; m<=end; m++) { //循环打印即可                       
					//c.add(Calendar.DAY_OF_MONTH, 1);    
					Date date=c.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date3=df.format(date);
					listtimetemp.add(date3);
					c.add(Calendar.DAY_OF_MONTH, 1);
					} 
				monthtemp++;
				if (monthtemp==13) {
					monthtemp=1;
					yeartemp++;
				}
				List<ManHourDto> listManHourDtoTotal=new ArrayList<ManHourDto>();
				List<Double> DevelopWorkNum=new ArrayList<Double>();
				List<Double> CTDCOEDevelopAddtional=new ArrayList<Double>();
				 
				if (department.equals("公司整体")) {
					List<AssumeDetail> assumePJNameList=new ArrayList<AssumeDetail>();
					Map<String,Object> paramsdepart = new HashMap<String, Object>();
					paramsdepart.put("index",listtimetemp.get(0));
				    int lastnum1=listtimetemp.size()-1;
				    paramsdepart.put("end",listtimetemp.get(lastnum1));
				    designStaffNum.add(calendarmanhourMapper.getDesignStaffNum(paramsdepart));
				    workDayslist.add(calendarmanhourMapper.getWorkDays(paramsdepart));
				    for (int i = 0; i < Parma.strProjectClientName.length; i++) {
				    	paramsdepart.put("projectClientName",Parma.strProjectClientName[i]);
				    	AssumeDetail assumeDetailtemp=new AssumeDetail();
				    	assumeDetailtemp.setAssumePJNum(calendarmanhourMapper.getCompanyAssumePJNum(paramsdepart));
				    	assumeDetailtemp.setAssumePJName(calendarmanhourMapper.getCompanyAssumePJName(paramsdepart));
				    	assumePJNameList.add(assumeDetailtemp);
					}
				    allAssumePJDetailList.add(assumePJNameList);
					listManHourDtoTotal=calendarmanhourMapper.listCompanyManhourDtoTotal(paramsdepart);
				    for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {//strDevelopWorkNum={"开发","附带"}
				    	paramsdepart.put("memo",Parma.strDevelopWorkNum[i]);
				    	DevelopWorkNum.add(calendarmanhourMapper.listDevelopWorkNum(paramsdepart));
					}
				    Map<String,Object> CTDCOEDevelopAddtionaltemp = new HashMap<String, Object>();
				    CTDCOEDevelopAddtionaltemp.put("index",listtimetemp.get(0));
				    CTDCOEDevelopAddtionaltemp.put("end",listtimetemp.get(listtimetemp.size()-1));
				    for (int j =0; j <Parma.strProjectClientName.length ; j++) {//strProjectClientName={"CT","DCOE","OUTOUT"}
				    	CTDCOEDevelopAddtionaltemp.put("projectClientName",Parma.strProjectClientName[j]);
				    	for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {
			    			 CTDCOEDevelopAddtionaltemp.put("memo",Parma.strDevelopWorkNum[i]);//strDevelopWorkNum={"开发","附带"}
			    			 CTDCOEDevelopAddtional.add(calendarmanhourMapper.listCTDCOEDevelopWorkNum(CTDCOEDevelopAddtionaltemp));
					        }
				    }
				}
				else if (department.equals("開発部門")) {
					List<AssumeDetail> assumePJNameList=new ArrayList<AssumeDetail>();
					Map<String,Object> developDepartment = new HashMap<String, Object>();
					developDepartment.put("index",listtimetemp.get(0));
				    int lastnum1=listtimetemp.size()-1;
				    developDepartment.put("end",listtimetemp.get(lastnum1));
				    //developDepartment.put("softDevelopDepart","软件开发部");
					//developDepartment.put("electricDevelopDepart","电气开发部");
					//developDepartment.put("constructDevelopDepart","构造开发部");
				    developDepartment.put("softDevelopDepart",DepartmentEnum.RJKFB.getId());
					developDepartment.put("electricDevelopDepart",DepartmentEnum.DQKFB.getId());
					developDepartment.put("constructDevelopDepart",DepartmentEnum.GZKFB.getId());
				    designStaffNum.add(calendarmanhourMapper.getDevelopDepartDesignStaffNum(developDepartment));
				    workDayslist.add(calendarmanhourMapper.getWorkDays(developDepartment));
				    for (int i = 0; i < Parma.strProjectClientName.length; i++) {
				    	developDepartment.put("projectClientName",Parma.strProjectClientName[i]);
				    	AssumeDetail assumeDetailtemp=new AssumeDetail();
				    	assumeDetailtemp.setAssumePJNum(calendarmanhourMapper.getDevelopDepartAssumePJNum(developDepartment));
				    	System.out.println(calendarmanhourMapper.getDevelopDepartAssumePJNum(developDepartment)+"calendarmanhourMapper.getDevelopDepartAssumePJNum(developDepartment)");
				    	assumeDetailtemp.setAssumePJName(calendarmanhourMapper.getDevelopDepartAssumePJName(developDepartment));
				    	assumePJNameList.add(assumeDetailtemp);
					}
				    allAssumePJDetailList.add(assumePJNameList);
					listManHourDtoTotal=calendarmanhourMapper.listDevelopDepartManhourDtoTotal(developDepartment);
					for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {//strDevelopWorkNum={"开发","附带"}
						developDepartment.put("memo",Parma.strDevelopWorkNum[i]);
				    	DevelopWorkNum.add(calendarmanhourMapper.listdevelopDepartmentWorkNum(developDepartment));
					}
					Map<String,Object> CTDCOEDevelopAddtionaltemp = new HashMap<String, Object>();
					CTDCOEDevelopAddtionaltemp.put("index",listtimetemp.get(0));
					CTDCOEDevelopAddtionaltemp.put("end",listtimetemp.get(listtimetemp.size()-1));
					//CTDCOEDevelopAddtionaltemp.put("softDevelopDepart","软件开发部");
					//CTDCOEDevelopAddtionaltemp.put("electricDevelopDepart","电气开发部");
					//CTDCOEDevelopAddtionaltemp.put("constructDevelopDepart","构造开发部");
					CTDCOEDevelopAddtionaltemp.put("softDevelopDepart",DepartmentEnum.RJKFB.getId());
					CTDCOEDevelopAddtionaltemp.put("electricDevelopDepart",DepartmentEnum.DQKFB.getId());
					CTDCOEDevelopAddtionaltemp.put("constructDevelopDepart",DepartmentEnum.GZKFB.getId());
					for (int j =0; j <Parma.strProjectClientName.length ; j++) {//strProjectClientName={"CT","DCOE","OUTOUT"}
				    	CTDCOEDevelopAddtionaltemp.put("projectClientName",Parma.strProjectClientName[j]);
				    	for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {
			    			 CTDCOEDevelopAddtionaltemp.put("memo",Parma.strDevelopWorkNum[i]);//strDevelopWorkNum={"开发","附带"}
			    			 CTDCOEDevelopAddtional.add(calendarmanhourMapper.listCTDCOEDevelopDepartWorkNum(CTDCOEDevelopAddtionaltemp));
					        }
				    }
				}
				else {
					List<AssumeDetail> assumePJNameList=new ArrayList<AssumeDetail>();
				    Map<String,Object> nonDevelopDepartment = new HashMap<String, Object>();
				    nonDevelopDepartment.put("index",listtimetemp.get(0));
				    //int lastnum1=listtimetemp.size()-1;
				    //nonDevelopDepartment.put("end",listtimetemp.get(lastnum1));
				    //nonDevelopDepartment.put("DevelopUnifiesRoom","开发统括室");
				    //nonDevelopDepartment.put("developQualityAssuranceDepart","开发品质保证部");
				    //nonDevelopDepartment.put("EngineeringDesignDepart","工程设计部");
				    //nonDevelopDepartment.put("OriginalEnterpriseDepart","原价企画部");
				    //nonDevelopDepartment.put("IdeaDevelopRoom","构想设计部");
				    //nonDevelopDepartment.put("BusinessManageDepart","经营管理部");
				    nonDevelopDepartment.put("DevelopUnifiesRoom",DepartmentEnum.KFTKS.getId());
				    nonDevelopDepartment.put("developQualityAssuranceDepart",DepartmentEnum.KFPZBZB.getId());
				    nonDevelopDepartment.put("EngineeringDesignDepart",DepartmentEnum.GCSJB.getId());
				    nonDevelopDepartment.put("OriginalEnterpriseDepart",DepartmentEnum.YJQHB.getId());
				    nonDevelopDepartment.put("IdeaDevelopRoom",DepartmentEnum.GXSJB.getId());
				    nonDevelopDepartment.put("BusinessManageDepart",DepartmentEnum.JYGLB.getId());
				    designStaffNum.add(calendarmanhourMapper.getNonDevelopDepartDesignStaffNum(nonDevelopDepartment));
				    workDayslist.add(calendarmanhourMapper.getWorkDays(nonDevelopDepartment));
				    for (int i = 0; i < Parma.strProjectClientName.length; i++) {
				    	nonDevelopDepartment.put("projectClientName",Parma.strProjectClientName[i]);
				    	AssumeDetail assumeDetailtemp=new AssumeDetail();
				    	assumeDetailtemp.setAssumePJNum(calendarmanhourMapper.getNonDevelopDepartAssumePJNum(nonDevelopDepartment));
				    	assumeDetailtemp.setAssumePJName(calendarmanhourMapper.getNonDevelopDepartAssumePJName(nonDevelopDepartment));
				    	assumePJNameList.add(assumeDetailtemp);
					}
				    allAssumePJDetailList.add(assumePJNameList);
					
					listManHourDtoTotal=calendarmanhourMapper.listNonDevelopDepartManhourDtoTotal(nonDevelopDepartment);
					for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {//strDevelopWorkNum={"开发","附带"}
						nonDevelopDepartment.put("memo",Parma.strDevelopWorkNum[i]);
				    	DevelopWorkNum.add(calendarmanhourMapper.listNonDevelopDepartmentWorkNum(nonDevelopDepartment));
					}
					Map<String,Object> CTDCOEDevelopAddtionaltemp = new HashMap<String, Object>();
					CTDCOEDevelopAddtionaltemp.put("index",listtimetemp.get(0));
					CTDCOEDevelopAddtionaltemp.put("end",listtimetemp.get(listtimetemp.size()-1));
					//CTDCOEDevelopAddtionaltemp.put("DevelopUnifiesRoom","开发统括室");
					//CTDCOEDevelopAddtionaltemp.put("developQualityAssuranceDepart","开发品质保证部");
					//CTDCOEDevelopAddtionaltemp.put("EngineeringDesignDepart","工程设计部");
					//CTDCOEDevelopAddtionaltemp.put("OriginalEnterpriseDepart","原价企画部");
					//CTDCOEDevelopAddtionaltemp.put("IdeaDevelopRoom","构想设计部");
					//CTDCOEDevelopAddtionaltemp.put("BusinessManageDepart","经营管理部");
					CTDCOEDevelopAddtionaltemp.put("DevelopUnifiesRoom",DepartmentEnum.KFTKS.getId());
					CTDCOEDevelopAddtionaltemp.put("developQualityAssuranceDepart",DepartmentEnum.KFPZBZB.getId());
					CTDCOEDevelopAddtionaltemp.put("EngineeringDesignDepart",DepartmentEnum.GCSJB.getId());
					CTDCOEDevelopAddtionaltemp.put("OriginalEnterpriseDepart",DepartmentEnum.YJQHB.getId());
					CTDCOEDevelopAddtionaltemp.put("IdeaDevelopRoom",DepartmentEnum.GXSJB.getId());
					CTDCOEDevelopAddtionaltemp.put("BusinessManageDepart",DepartmentEnum.JYGLB.getId());
					for (int j =0; j <Parma.strProjectClientName.length ; j++) {//strProjectClientName={"CT","DCOE","OUTOUT"}
				    	CTDCOEDevelopAddtionaltemp.put("projectClientName",Parma.strProjectClientName[j]);
				    	for (int i = 0; i < Parma.strDevelopWorkNum.length; i++) {
			    			 CTDCOEDevelopAddtionaltemp.put("memo",Parma.strDevelopWorkNum[i]);//strDevelopWorkNum={"开发","附带"}
			    			 CTDCOEDevelopAddtional.add(calendarmanhourMapper.listCTDCOENonDevelopDeparWorkNum(CTDCOEDevelopAddtionaltemp));
					        }
				    }
				}
				allMonthListManHourDtoList.add(listManHourDtoTotal);
			    listCTDCOEDevelopAdditional.add(CTDCOEDevelopAddtional);
			    listDevelopWorkNum.add(DevelopWorkNum); 
			}
			//时间段之内产生工数的项目的集合
			Map<String,Object> paramsCompany = new HashMap<String, Object>();
			paramsCompany.put("department",department);
			paramsCompany.put("branch",branch);
			List<String> listtimeDate=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
			int startyear=year;
			int startmonth=month;
			while (startyear<endyear||(startyear==endyear&&startmonth<=endmonth)) {
				Calendar c = Calendar.getInstance(); //获取Calendar实例        
				c.set(Calendar.YEAR, startyear); //设置年        
				//c.set(Calendar.MONTH, startmonth-2); //设置月        
				//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
				if (startday == 21 || startday == 26){
					c.set(Calendar.MONTH, startmonth - 2); //设置月        	
				} else if (startday == 1){
					c.set(Calendar.MONTH, startmonth - 1); //设置月
				}
				c.set(Calendar.DAY_OF_MONTH, startday); //设置月开始第一天日期
				int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
				for (int m=1; m<=end; m++) { //循环打印即可                       
					//c.add(Calendar.DAY_OF_MONTH, 1);    
					Date date=c.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date3=df.format(date);
					listtimeDate.add(date3);
					c.add(Calendar.DAY_OF_MONTH, 1);
					} 
				startmonth++;
				if (startmonth==13) {
					startmonth=1;
					startyear++;
				}
			}
			paramsCompany.put("index",listtimeDate.get(0));
		    int lastnumdate=listtimeDate.size()-1;
		    paramsCompany.put("end",listtimeDate.get(lastnumdate));
		    List<Integer> workNumProjectIdlist=new ArrayList<Integer>();
		    List<Project> listProject=new ArrayList<Project>();
		    List<Project_task> listCompanyProjectTasks=new ArrayList<Project_task>();
		    if (department.equals("公司整体")) {
		    	workNumProjectIdlist=calendarmanhourMapper.getWorkNumProjectId(paramsCompany);
		    	listCompanyProjectTasks=calendarmanhourMapper.listCompanyProjectTasks();
			}
		    else if (department.equals("開発部門")) {
		    	//paramsCompany.put("softDevelopDepart","软件开发部");
		    	//paramsCompany.put("electricDevelopDepart","电气开发部");
		    	//paramsCompany.put("constructDevelopDepart","构造开发部");
		    	paramsCompany.put("softDevelopDepart",DepartmentEnum.RJKFB.getId());
		    	paramsCompany.put("electricDevelopDepart",DepartmentEnum.DQKFB.getId());
		    	paramsCompany.put("constructDevelopDepart",DepartmentEnum.GZKFB.getId());
		    	workNumProjectIdlist=calendarmanhourMapper.getDevelopDepartWorkNumProjectId(paramsCompany);
		    	listCompanyProjectTasks=calendarmanhourMapper.listDevelopDepartProjectTasks(paramsCompany);
			}
		    else {
//		    	paramsCompany.put("DevelopUnifiesRoom","开发统括室");
//		    	paramsCompany.put("developQualityAssuranceDepart","开发品质保证部");
//		    	paramsCompany.put("EngineeringDesignDepart","工程设计部");
//		    	paramsCompany.put("OriginalEnterpriseDepart","原价企画部");
//				paramsCompany.put("IdeaDevelopRoom","构想设计部");
//				paramsCompany.put("BusinessManageDepart","经营管理部");
				paramsCompany.put("DevelopUnifiesRoom",DepartmentEnum.KFTKS.getId());
		    	paramsCompany.put("developQualityAssuranceDepart",DepartmentEnum.KFPZBZB.getId());
		    	paramsCompany.put("EngineeringDesignDepart",DepartmentEnum.GCSJB.getId());
		    	paramsCompany.put("OriginalEnterpriseDepart",DepartmentEnum.YJQHB.getId());
				paramsCompany.put("IdeaDevelopRoom",DepartmentEnum.GXSJB.getId());
				paramsCompany.put("BusinessManageDepart",DepartmentEnum.JYGLB.getId());
				workNumProjectIdlist=calendarmanhourMapper.getNonDevelopDepartWorkNumProjectId(paramsCompany);
				listCompanyProjectTasks=calendarmanhourMapper.listNonDevelopDepartProjectTasks(paramsCompany);
			}
		    for (int i = 0; i < workNumProjectIdlist.size(); i++) {
		    	Project projectTemp=calendarmanhourMapper.getProjectDetailByProjectId(workNumProjectIdlist.get(i));
		    	listProject.add(projectTemp);
			}
			
		    List<Integer> projectNum=new ArrayList<Integer>();
		    for (int i = 0; i < Parma.strInAdvanceDepart.length; i++) {
		    	projectNum.add(calendarmanhourMapper.SearchId(Parma.strInAdvanceDepart[i]));
			}
			
			companyFirstPagetemp.departFirstPage(department,year,month,filenameString,allMonthListManHourDtoList,listProject,listCompanyProjectTasks,
					listDevelopWorkNum,listCTDCOEDevelopAdditional,workDayslist,allAssumePJDetailList,designStaffNum);
			companySecondPagetemp.updateSecondXLS(department, listProject, year, month, filenameString,allMonthListManHourDtoList,listCompanyProjectTasks,workDayslist);
			companyThirdPagetemp.updateThirdXLS(department, listProject, year, month, filenameString,allMonthListManHourDtoList,listCompanyProjectTasks,workDayslist);
			companyFourthPagetemp.updateFourthXLS(department, listProject, year, month, filenameString,allMonthListManHourDtoList,listCompanyProjectTasks,workDayslist);
			
			List<Project_task> listCompanyProjectTaskstemp=new ArrayList<Project_task>();
			if (department.equals("公司整体")) {
				listCompanyProjectTaskstemp=calendarmanhourMapper.listCompanyProjectTasks();
			}
		    else if (department.equals("開発部門")) {
//		    	paramsCompany.put("softDevelopDepart","软件开发部");
//		    	paramsCompany.put("electricDevelopDepart","电气开发部");
//		    	paramsCompany.put("constructDevelopDepart","构造开发部");
		    	paramsCompany.put("softDevelopDepart",DepartmentEnum.RJKFB.getId());
		    	paramsCompany.put("electricDevelopDepart",DepartmentEnum.DQKFB.getId());
		    	paramsCompany.put("constructDevelopDepart",DepartmentEnum.GZKFB.getId());
		    	listCompanyProjectTaskstemp=calendarmanhourMapper.listDevelopDepartProjectTasks(paramsCompany);
			}
		    else {
//		    	paramsCompany.put("DevelopUnifiesRoom","开发统括室");
//		    	paramsCompany.put("developQualityAssuranceDepart","开发品质保证部");
//		    	paramsCompany.put("EngineeringDesignDepart","工程设计部");
//		    	paramsCompany.put("OriginalEnterpriseDepart","原价企画部");
//				paramsCompany.put("IdeaDevelopRoom","构想设计部");
//				paramsCompany.put("BusinessManageDepart","经营管理部");
				paramsCompany.put("DevelopUnifiesRoom",DepartmentEnum.KFTKS.getId());
		    	paramsCompany.put("developQualityAssuranceDepart",DepartmentEnum.KFPZBZB.getId());
		    	paramsCompany.put("EngineeringDesignDepart",DepartmentEnum.GCSJB.getId());
		    	paramsCompany.put("OriginalEnterpriseDepart",DepartmentEnum.YJQHB.getId());
				paramsCompany.put("IdeaDevelopRoom",DepartmentEnum.GXSJB.getId());
				paramsCompany.put("BusinessManageDepart",DepartmentEnum.JYGLB.getId());
				listCompanyProjectTaskstemp=calendarmanhourMapper.listNonDevelopDepartProjectTasks(paramsCompany);
			}
			companyFivethPagetemp.updateFivethXLS(department, listProject, year, month,endyear,endmonth, filenameString,allMonthListManHourDtoList,listCompanyProjectTaskstemp,projectNum,workDayslist);
			
			ZipCompressorByAnt zca = new ZipCompressorByAnt(realpath+"\\"+year+'_'+month+'_'+filenameString+".zip");   
			zca.compress("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"+filenameString);
			
			companyFivethPagetemp.deleteTempDir("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"+filenameString);
			companyFirstPagetemp.deleteTempDir();
			companySecondPagetemp.deleteTempDir();
			companyThirdPagetemp.deleteTempDir();
			companyFourthPagetemp.deleteTempDir();
			webaddrString="/temp/"+year+'_'+month+'_'+filenameString+".zip";
		}
		else {
			DepartFirstPage DepartFirstPagetemp=new DepartFirstPage();
			DepartSecondPage DepartSecondPagetemp=new DepartSecondPage();
			DepartThirdPage DepartThirdPagetemp=new DepartThirdPage();
			DepartFourthPage DepartFourthPagetemp=new DepartFourthPage();
			DepartFivethPage DepartFivethPagetemp=new DepartFivethPage();
			DepartSixPage DepartSixPagetemp=new DepartSixPage();
			DepartSevenPage DepartSevenPagetemp=new DepartSevenPage();
			Calendar c1=Calendar.getInstance();//获得系统当前日期        
			int yearsys=c1.get(Calendar.YEAR);        
			int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
			int daysys=c1.get(Calendar.DAY_OF_MONTH);
			int hoursys=c1.get(Calendar.HOUR_OF_DAY);
			int minutessys=c1.get(Calendar.MINUTE);
			int secondsys=c1.get(Calendar.SECOND);
			String filenameString=String.valueOf(yearsys)+String.valueOf(monthsys)+String.valueOf(daysys)+String.valueOf(hoursys)+String.valueOf(minutessys)+String.valueOf(secondsys); 

			List<List<ManHourDto>> allMonthListManHourDtoList=new ArrayList<List<ManHourDto>>();
			List<List<Double>> allListCategoryDetail=new ArrayList<List<Double>>();
			List<Integer> workDayslist=new ArrayList<Integer>();
			List<Integer> designStaffNum=new ArrayList<Integer>();
			List<List<AssumeDetail>> allAssumePJDetailList=new ArrayList<List<AssumeDetail>>();
			
			int yeartemp=year;
			int monthtemp=month;
			while (yeartemp<endyear||(yeartemp==endyear&&monthtemp<=endmonth)) {
				List<String> listtimetemp=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
				Calendar c = Calendar.getInstance(); //获取Calendar实例        
				c.set(Calendar.YEAR, yeartemp); //设置年        
				//c.set(Calendar.MONTH, monthtemp-2); //设置月        
				//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
				if (startday == 21 || startday == 26){
					c.set(Calendar.MONTH, monthtemp - 2); //设置月        	
				} else if (startday == 1){
					c.set(Calendar.MONTH, monthtemp - 1); //设置月
				}
				c.set(Calendar.DAY_OF_MONTH, startday); //设置月开始第一天日期
				int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
				for (int m=1; m<=end; m++) { //循环打印即可                       
					//c.add(Calendar.DAY_OF_MONTH, 1);    
					Date date=c.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date3=df.format(date);
					listtimetemp.add(date3);
					c.add(Calendar.DAY_OF_MONTH, 1);
					} 
				monthtemp++;
				if (monthtemp==13) {
					monthtemp=1;
					yeartemp++;
				}
				List<AssumeDetail> assumePJNameList=new ArrayList<AssumeDetail>();
				List<ManHourDto> listManHourDtoTotaltemp=new ArrayList<ManHourDto>();
				Map<String,Object> paramsdepart = new HashMap<String, Object>();
				paramsdepart.put("index",listtimetemp.get(0));
			    int lastnum1=listtimetemp.size()-1;
			    paramsdepart.put("end",listtimetemp.get(lastnum1));
			    paramsdepart.put("departmentID",departmentID);
			    paramsdepart.put("branchID",branchID);
			    designStaffNum.add(calendarmanhourMapper.getDepartmentDesignStaffNum(paramsdepart));
			    workDayslist.add(calendarmanhourMapper.getWorkDays(paramsdepart));
			    for (int i = 0; i < Parma.strProjectClientName.length; i++) {
			    	paramsdepart.put("projectClientName",Parma.strProjectClientName[i]);
			    	AssumeDetail assumeDetailtemp=new AssumeDetail();
			    	assumeDetailtemp.setAssumePJNum(calendarmanhourMapper.getAssumePJNum(paramsdepart));
			    	
			    	assumeDetailtemp.setAssumePJName(calendarmanhourMapper.getAssumePJName(paramsdepart));
			    	assumePJNameList.add(assumeDetailtemp);
				}
			    allAssumePJDetailList.add(assumePJNameList);
			   
				listManHourDtoTotaltemp=calendarmanhourMapper.listDepartManhourDtoTotal(paramsdepart);//与本部门或者本课别有关的所有工数
				
				allMonthListManHourDtoList.add(listManHourDtoTotaltemp);
				List<Double>  listCategoryDetail=new ArrayList<Double>();
				for (int i =0; i <Parma.strProjectClientName.length ; i++) {
					paramsdepart.put("projectClientName",Parma.strProjectClientName[i]);
					for (int j = 0; j < Parma.strFunction.length; j++) {
						paramsdepart.put("function",Parma.strFunction[j]);
						listCategoryDetail.add(calendarmanhourMapper.listCategoryDetail(paramsdepart));
					}
				}
				allListCategoryDetail.add(listCategoryDetail);
			}
			List<Project>   listProject=new ArrayList<Project>();
			List<String> listtransferNo=new ArrayList<String>();
			//时间段之内产生工数的项目的集合
			Map<String,Object> paramsDepart = new HashMap<String, Object>();
			paramsDepart.put("departmentID",departmentID);
			paramsDepart.put("branchID",branchID);
			List<String> listtimeDate=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
			int startyear=year;
			int startmonth=month;
			while (startyear<endyear||(startyear==endyear&&startmonth<=endmonth)) {
				Calendar c = Calendar.getInstance(); //获取Calendar实例        
				c.set(Calendar.YEAR, startyear); //设置年        
				//c.set(Calendar.MONTH, startmonth-2); //设置月        
				//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
				if (startday == 21 || startday == 26){
					c.set(Calendar.MONTH, startmonth - 2); //设置月        	
				} else if (startday == 1){
					c.set(Calendar.MONTH, startmonth - 1); //设置月
				}
				c.set(Calendar.DAY_OF_MONTH, startday);
				int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
				for (int m=1; m<=end; m++) { //循环打印即可                       
					//c.add(Calendar.DAY_OF_MONTH, 1);    
					Date date=c.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date3=df.format(date);
					listtimeDate.add(date3);
					c.add(Calendar.DAY_OF_MONTH, 1);
					} 
				startmonth++;
				if (startmonth==13) {
					startmonth=1;
					startyear++;
				}
			}
			paramsDepart.put("index",listtimeDate.get(0));
		    int lastnumdate=listtimeDate.size()-1;
		    paramsDepart.put("end",listtimeDate.get(lastnumdate));
		    paramsDepart.put("departmentID",departmentID);
		    paramsDepart.put("branchID",branchID);
		    List<Integer> workNumProjectIdlist=calendarmanhourMapper.getDepartWorkNumProjectId(paramsDepart);
		    for (int i = 0; i < workNumProjectIdlist.size(); i++) {
		    	Project projectTemp=calendarmanhourMapper.getProjectDetailByProjectId(workNumProjectIdlist.get(i));
		    	listProject.add(projectTemp);
			}
		   listtransferNo=calendarmanhourMapper.getTransforNOByDepart(paramsDepart);
			
			List<Project_task> listProjectTasks=calendarmanhourMapper.listDepartProjectTask(departmentID);//列出本部门相关的Task
			DepartFirstPagetemp.departFirstPage(department,branch,year,month,endyear,endmonth,filenameString,allMonthListManHourDtoList,listProject,
					listProjectTasks,allListCategoryDetail,workDayslist,allAssumePJDetailList,designStaffNum);
			DepartSecondPagetemp.updateSecondXLS(department,branch, listProject, year, month, filenameString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			DepartThirdPagetemp.updateThirdXLS(department,branch, listProject, year, month, filenameString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			DepartFourthPagetemp.updateFourthXLS(department,branch, listProject, year, month, filenameString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			DepartFivethPagetemp.updateFivethXLS(department,branch, listProject, year, month, filenameString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			DepartSixPagetemp.updateSixXLS(department,branch, listProject, year, month,endyear,endmonth, filenameString, allMonthListManHourDtoList, listProjectTasks,workDayslist);
			DepartSevenPagetemp.updateSevenXLS(department,branch, listProject, year, month,endyear,endmonth, filenameString, allMonthListManHourDtoList, listProjectTasks,listtransferNo,workDayslist);
			
			ZipCompressorByAnt zca = new ZipCompressorByAnt(realpath+"\\"+year+'_'+month+'_'+filenameString+".zip");

			zca.compress("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"+filenameString);
			DepartSevenPagetemp.deleteTempDir("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"+filenameString);

			DepartFirstPagetemp.deleteTempDir();
			DepartSecondPagetemp.deleteTempDir();
			DepartThirdPagetemp.deleteTempDir();
			DepartFourthPagetemp.deleteTempDir();
			DepartFivethPagetemp.deleteTempDir();
			DepartSixPagetemp.deleteTempDir();
			webaddrString="/temp/"+year+'_'+month+'_'+filenameString+".zip";
		}
		return webaddrString;		
	}

	public String outputDepartJijimanhour_ForCt(String user, String department,String branch,String departmentID, String branchID,int year,int month,int startday,int endyear, int endmonth,String realpath){
		String webaddrString="";
		if (branch.equals("--请选择--")|| branch.indexOf("項目を選択してください")>0){
			branch=null;
			branchID = null;
		}
		DepartFirstPage_ForCt DepartFirstPagetemp_ForCt=new DepartFirstPage_ForCt();
		DepartSecondPage_ForCt DepartSecondPagetemp_ForCt=new DepartSecondPage_ForCt();
		DepartThirdPage_ForCt DepartThirdPagetemp_ForCt=new DepartThirdPage_ForCt();
		DepartFourthPage_ForCt DepartFourthPagetemp_ForCt=new DepartFourthPage_ForCt();
		DepartFivethPage_ForCt DepartFivethPagetemp_ForCt=new DepartFivethPage_ForCt();
		DepartSixPage_ForCt DepartSixPagetemp_ForCt=new DepartSixPage_ForCt();
		Calendar c1=Calendar.getInstance();//获得系统当前日期        
		int yearsys=c1.get(Calendar.YEAR);        
		int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
		int daysys=c1.get(Calendar.DAY_OF_MONTH);
		int hoursys=c1.get(Calendar.HOUR_OF_DAY);
		int minutessys=c1.get(Calendar.MINUTE);
		int secondsys=c1.get(Calendar.SECOND);
		String filenameString=String.valueOf(yearsys)+String.valueOf(monthsys)+String.valueOf(daysys)+String.valueOf(hoursys)+String.valueOf(minutessys)+String.valueOf(secondsys); 

		List<List<ManHourDto>> allMonthListManHourDtoList=new ArrayList<List<ManHourDto>>();
		List<Integer> workDayslist=new ArrayList<Integer>();
		List<Integer> designStaffNum=new ArrayList<Integer>();
		List<List<AssumeDetail>> allAssumePJDetailList=new ArrayList<List<AssumeDetail>>();
		
		int yeartemp=year;
		int monthtemp=month;
		while (yeartemp<endyear||(yeartemp==endyear&&monthtemp<=endmonth)) {
			List<String> listtimetemp=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
			Calendar c = Calendar.getInstance(); //获取Calendar实例        
			c.set(Calendar.YEAR, yeartemp); //设置年        
			//c.set(Calendar.MONTH, monthtemp-2); //设置月        
			//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
			if (startday == 21 || startday == 26){
				c.set(Calendar.MONTH, monthtemp - 2); //设置月        	
			} else if (startday == 1){
				c.set(Calendar.MONTH, monthtemp - 1); //设置月
			}
			c.set(Calendar.DAY_OF_MONTH, startday); //设置月开始第一天日期
			int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
			for (int m=1; m<=end; m++) { //循环打印即可                       
				//c.add(Calendar.DAY_OF_MONTH, 1);    
				Date date=c.getTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String date3=df.format(date);
				listtimetemp.add(date3);
				c.add(Calendar.DAY_OF_MONTH, 1);
				} 
			monthtemp++;
			if (monthtemp==13) {
				monthtemp=1;
				yeartemp++;
			}
			List<AssumeDetail> assumePJNameList=new ArrayList<AssumeDetail>();
			List<ManHourDto> listManHourDtoTotaltemp=new ArrayList<ManHourDto>();
			Map<String,Object> paramsdepart = new HashMap<String, Object>();
			paramsdepart.put("index",listtimetemp.get(0));
		    int lastnum1=listtimetemp.size()-1;
		    paramsdepart.put("end",listtimetemp.get(lastnum1));
		    paramsdepart.put("departmentID",departmentID);
		    paramsdepart.put("branchID",branchID);
		    workDayslist.add(calendarmanhourMapper.getWorkDays(paramsdepart));
	    	AssumeDetail assumeDetailtemp=new AssumeDetail();
	    	assumeDetailtemp.setAssumePJNum(calendarmanhourMapper.getAssumePJNum_ct(paramsdepart));
	    	assumeDetailtemp.setAssumePJName(calendarmanhourMapper.getAssumePJName_ct(paramsdepart));
	    	assumePJNameList.add(assumeDetailtemp);
		    allAssumePJDetailList.add(assumePJNameList);
		    designStaffNum.add(calendarmanhourMapper.getDepartmentDesignStaffNum_ct(paramsdepart));
			listManHourDtoTotaltemp=calendarmanhourMapper.listDepartManhourDtoTotal_ct(paramsdepart);//与本部门或者本课别有关的所有工数
			
			allMonthListManHourDtoList.add(listManHourDtoTotaltemp);
		}
		List<Project>   listProject=new ArrayList<Project>();
		//时间段之内产生工数的项目的集合
		Map<String,Object> paramsDepart = new HashMap<String, Object>();
		paramsDepart.put("departmentID",departmentID);
		paramsDepart.put("branchID",branchID);
		List<String> listtimeDate=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
		int startyear=year;
		int startmonth=month;
		while (startyear<endyear||(startyear==endyear&&startmonth<=endmonth)) {
			Calendar c = Calendar.getInstance(); //获取Calendar实例        
			c.set(Calendar.YEAR, startyear); //设置年        
			//c.set(Calendar.MONTH, startmonth-2); //设置月        
			//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
			if (startday == 21 || startday == 26){
				c.set(Calendar.MONTH, startmonth - 2); //设置月        	
			} else if (startday == 1){
				c.set(Calendar.MONTH, startmonth - 1); //设置月
			}
			c.set(Calendar.DAY_OF_MONTH, startday);
			int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
			for (int m=1; m<=end; m++) { //循环打印即可                       
				//c.add(Calendar.DAY_OF_MONTH, 1);    
				Date date=c.getTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String date3=df.format(date);
				listtimeDate.add(date3);
				c.add(Calendar.DAY_OF_MONTH, 1);
				} 
			startmonth++;
			if (startmonth==13) {
				startmonth=1;
				startyear++;
			}
		}
		paramsDepart.put("index",listtimeDate.get(0));
	    int lastnumdate=listtimeDate.size()-1;
	    paramsDepart.put("end",listtimeDate.get(lastnumdate));
	    paramsDepart.put("departmentID",departmentID);
	    paramsDepart.put("branchID",branchID);
	    List<Integer> workNumProjectIdlist=calendarmanhourMapper.getDepartWorkNumProjectId_ct(paramsDepart);
	    for (int i = 0; i < workNumProjectIdlist.size(); i++) {
	    	Project projectTemp=calendarmanhourMapper.getProjectDetailByProjectId_ct(workNumProjectIdlist.get(i));
	    	listProject.add(projectTemp);
		}
		
		List<Project_task> listProjectTasks=calendarmanhourMapper.listDepartProjectTask_ct(departmentID);//列出本部门相关的Task
		DepartFirstPagetemp_ForCt.departFirstPage_ForCt(user, department, branch, year, month, endyear, endmonth, filenameString, allMonthListManHourDtoList, listProject
														,listProjectTasks, workDayslist, allAssumePJDetailList, designStaffNum);
		DepartSecondPagetemp_ForCt.updateSecondXLS_ForCt(user, department,branch, listProject, year, month, endyear, endmonth, filenameString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
		DepartThirdPagetemp_ForCt.updateThirdXLS_ForCt(user, department,branch, listProject, year, month, endyear, endmonth, filenameString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
		DepartFourthPagetemp_ForCt.updateFourthXLS_ForCt(user, department,branch, listProject, year, month, endyear, endmonth, filenameString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
		DepartFivethPagetemp_ForCt.updateFivethXLS_ForCt(user, department,branch, listProject, year, month, endyear, endmonth, filenameString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
		DepartSixPagetemp_ForCt.updateSixXLS_ForCt(user, department,branch, listProject, year, month, endyear, endmonth, filenameString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
		
		ZipCompressorByAnt zca = new ZipCompressorByAnt(realpath+"\\"+year+'_'+month+'_'+filenameString+".zip");

		zca.compress("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"+filenameString);
		DepartSixPagetemp_ForCt.deleteTempDir("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"+filenameString);

		DepartFirstPagetemp_ForCt.deleteTempDir();
		DepartSecondPagetemp_ForCt.deleteTempDir();
		DepartThirdPagetemp_ForCt.deleteTempDir();
		DepartFourthPagetemp_ForCt.deleteTempDir();
		DepartFivethPagetemp_ForCt.deleteTempDir();
		webaddrString="/temp/"+year+'_'+month+'_'+filenameString+".zip";

		return webaddrString;		
	}

	/*private List<String> getTransforNOByDepart(Map<String, Object> paramsDepart) {
		// TODO Auto-generated method stub
		return null;
	}*/
	public List<Map<String,String>> outputbranchselect(String departmentID){
		return calendarmanhourMapper.listbranchselect(departmentID);
	}

	public List<Map<String,String>> outputbranchselect_ForCt(String departmentID){
		return calendarmanhourMapper.listbranchselect_ForCt(departmentID);
	}

	public List<String> listCompanystaffselect(Map<String, Object> params){
		return calendarmanhourMapper.listCompanystaffselect(params);
	}
	
	public List<String> liststaffselect(Map<String, Object> params){
		return calendarmanhourMapper.liststaffselect(params);
	}

	public void updateEndtime(String endtime){
		calendarmanhourMapper.UpdateEndtime(endtime);
		
	}
	public Date getdeadlinetime(){
		return calendarmanhourMapper.getdeadlinetime();
	}
	public String exportPersonsWorkNum(String department, String branch,String departmentID,String branchID,
			int year, int month,int startday, int endyear, int endmonth,String realPath){
		String webaddrString="";
		if (branch.equals("--请选择--")|| branch.indexOf("項目を選択してください")>0){branch=null;branchID = null;}
		PersonFirstPage personFirstPagetemp=new PersonFirstPage();
		PersonSecondPage personSecondPagetemp=new PersonSecondPage();
		PersonThirdPage personThirdPagetemp=new PersonThirdPage();
		PersonFourthPage personFourthPagetemp=new PersonFourthPage();
		PersonFivethPage personFivethPagetemp=new PersonFivethPage();
		PersonSixPage personSixPagetemp=new PersonSixPage();
		PersonSevenPage personSevenPagetemp=new PersonSevenPage();
		
		Calendar c1=Calendar.getInstance();//获得系统当前日期        
		int yearsys=c1.get(Calendar.YEAR);        
		int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
		int daysys=c1.get(Calendar.DAY_OF_MONTH);
		int hoursys=c1.get(Calendar.HOUR_OF_DAY);
		int minutessys=c1.get(Calendar.MINUTE);
		int secondsys=c1.get(Calendar.SECOND);
		String filenameString=String.valueOf(yearsys)+String.valueOf(monthsys)+String.valueOf(daysys)+String.valueOf(hoursys)+String.valueOf(minutessys)+String.valueOf(secondsys); 

		List<Project>   listProject=new ArrayList<Project>();
		List<String> listtransferNo=new ArrayList<String>();
		List<Project_task> listProjectTasks=calendarmanhourMapper.listDepartProjectTask(departmentID);//列出本部门相关的Task
		Map<String,Object> paramsPerson = new HashMap<String, Object>();
		paramsPerson.put("departmentID",departmentID);
		paramsPerson.put("branchID",branchID);
		List<String> listtimeDate=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
		int startyear=year;
		int startmonth=month;
		while (startyear<endyear||(startyear==endyear&&startmonth<=endmonth)) {
			Calendar c = Calendar.getInstance(); //获取Calendar实例        
			c.set(Calendar.YEAR, startyear); //设置年        
			//c.set(Calendar.MONTH, startmonth-2); //设置月        
			//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
			if (startday == 21 || startday == 26){
				c.set(Calendar.MONTH, startmonth - 2); //设置月        	
			} else if (startday == 1){
				c.set(Calendar.MONTH, startmonth - 1); //设置月
			}
			c.set(Calendar.DAY_OF_MONTH, startday);
			int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
			for (int m=1; m<=end; m++) { //循环打印即可                       
				//c.add(Calendar.DAY_OF_MONTH, 1);    
				Date date=c.getTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String date3=df.format(date);
				listtimeDate.add(date3);
				c.add(Calendar.DAY_OF_MONTH, 1);
				} 
			startmonth++;
			if (startmonth==13) {
				startmonth=1;
				startyear++;
			}
		}
		paramsPerson.put("index",listtimeDate.get(0));
	    int lastnumdate=listtimeDate.size()-1;
	    paramsPerson.put("end",listtimeDate.get(lastnumdate));
	    paramsPerson.put("departmentID",departmentID);
	    paramsPerson.put("branchID",branchID);
	    List<Integer> workNumProjectIdlist=calendarmanhourMapper.getDepartWorkNumProjectId(paramsPerson);
	    for (int i = 0; i < workNumProjectIdlist.size(); i++) {
	    	Project projectTemp=calendarmanhourMapper.getProjectDetailByProjectId(workNumProjectIdlist.get(i));
	    	listProject.add(projectTemp);
		}
	    listtransferNo=calendarmanhourMapper.getTransforNOByDepart(paramsPerson);
	    
		List<Staff> staffIDlist=calendarmanhourMapper.getStaffIDlist(paramsPerson);//部门某个月工数的所包含的所有员工ID
		for (int k = 0;  k< staffIDlist.size(); k++) {//部门课别员工循环
			//Staff staff=calendarmanhourMapper.getStaffdetail(staffIDlist.get(k));
			Staff staff=staffIDlist.get(k);
			String uniqueString=staff.getDepartment()+"_"+staff.getBranch()+"_"+staff.getStaffID()+"_"+staff.getName();
			//课别为空时不作为文件名
			if (staff.getBranch() == null || "".equals(staff.getBranch()) ){
				uniqueString=staff.getDepartment()+"_"+staff.getStaffID()+"_"+staff.getName();
			}
			//String uniqueString=department+"_"+branch+"_"+staff.getStaffID()+"_"+staff.getName();
			List<List<ManHourDto>> allMonthListManHourDtoList=new ArrayList<List<ManHourDto>>();
			List<List<Double>> allListCategoryDetail=new ArrayList<List<Double>>();
			List<Integer> workDayslist=new ArrayList<Integer>();
			List<List<AssumeDetail>> allAssumePJDetailList=new ArrayList<List<AssumeDetail>>();
			
			int yeartemp=year;
			int monthtemp=month;
			//循环开始
			while (yeartemp<endyear||(yeartemp==endyear&&monthtemp<=endmonth)) {
				List<String> listtimetemp=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
				Calendar c = Calendar.getInstance(); //获取Calendar实例        
				c.set(Calendar.YEAR, yeartemp); //设置年        
				//c.set(Calendar.MONTH, monthtemp-2); //设置月        
				//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期        
				if (startday == 21 || startday == 26){
					c.set(Calendar.MONTH, monthtemp - 2); //设置月        	
				} else if (startday == 1){
					c.set(Calendar.MONTH, monthtemp - 1); //设置月
				}
				c.set(Calendar.DAY_OF_MONTH, startday);
				int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
				for (int m=1; m<=end; m++) { //循环打印即可                       
					//c.add(Calendar.DAY_OF_MONTH, 1);    
					Date date=c.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date3=df.format(date);
					listtimetemp.add(date3);
					c.add(Calendar.DAY_OF_MONTH, 1);
					} 
				monthtemp++;
				if (monthtemp==13) {
					monthtemp=1;
					yeartemp++;
				}
				List<AssumeDetail> assumePJNameList=new ArrayList<AssumeDetail>();
				List<ManHourDto> listManHourDtoTotaltemp=new ArrayList<ManHourDto>();
				Map<String,Object> paramsdepart = new HashMap<String, Object>();
				paramsdepart.put("index",listtimetemp.get(0));
			    int lastnum1=listtimetemp.size()-1;
			    paramsdepart.put("end",listtimetemp.get(lastnum1));
			    paramsdepart.put("departmentID",departmentID);
			    paramsdepart.put("branchID",branchID);
			    paramsdepart.put("staffID",staff.getStaffID());
			    workDayslist.add(calendarmanhourMapper.getWorkDays(paramsdepart));
			    for (int i = 0; i < Parma.strProjectClientName.length; i++) {
			    	paramsdepart.put("projectClientName",Parma.strProjectClientName[i]);
			    	AssumeDetail assumeDetailtemp=new AssumeDetail();
			    	assumeDetailtemp.setAssumePJNum(calendarmanhourMapper.getPersonAssumePJNum(paramsdepart));
			    	assumeDetailtemp.setAssumePJName(calendarmanhourMapper.getPersonAssumePJName(paramsdepart));
			    	assumePJNameList.add(assumeDetailtemp);
				}
			    allAssumePJDetailList.add(assumePJNameList);
				listManHourDtoTotaltemp=calendarmanhourMapper.listPersonManhourDtoTotal(paramsdepart);//与本部门或者本课别有关的所有工数
				
				allMonthListManHourDtoList.add(listManHourDtoTotaltemp);
				List<Double>  listCategoryDetail=new ArrayList<Double>();
				for (int i =0; i <Parma.strProjectClientName.length ; i++) {
					paramsdepart.put("projectClientName",Parma.strProjectClientName[i]);
					for (int j = 0; j < Parma.strFunction.length; j++) {
						paramsdepart.put("function",Parma.strFunction[j]);
						listCategoryDetail.add(calendarmanhourMapper.listPersonCategoryDetail(paramsdepart));
					}
				}
				allListCategoryDetail.add(listCategoryDetail);
			}
			//循环结束
			personFirstPagetemp.personWorkNumFirstPage(department,branch,year,month,endyear,endmonth,uniqueString,allMonthListManHourDtoList,listProject,listProjectTasks,allListCategoryDetail,workDayslist,allAssumePJDetailList);
			personSecondPagetemp.updatePersonWorkNumSecondXLS(department,branch, listProject, year, month,uniqueString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			personThirdPagetemp.updatePersonWorkNumThirdXLS(department,branch, listProject, year, month,uniqueString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			personFourthPagetemp.updatePersonWorkNumFourthXLS(department,branch, listProject, year, month,uniqueString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			personFivethPagetemp.updatePersonWorkNumFivethXLS(department,branch, listProject, year, month,uniqueString,allMonthListManHourDtoList,listProjectTasks,workDayslist);
			personSixPagetemp.updatePersonWorkNumSixXLS(department,branch, listProject, year, month,endyear,endmonth,uniqueString,allMonthListManHourDtoList, listProjectTasks,workDayslist);
			personSevenPagetemp.updatePersonWorkNumSevenXLS(department,branch, listProject, year, month,endyear,endmonth,uniqueString,allMonthListManHourDtoList, listProjectTasks,listtransferNo,workDayslist);
		}
		
		ZipCompressorByAnt zca = new ZipCompressorByAnt(realPath+"\\"+year+'_'+month+'_'+filenameString+".zip");
		zca.compress("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月");
		personSevenPagetemp.deleteTempDir("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月");
		personFirstPagetemp.deleteTempDir();
		personSecondPagetemp.deleteTempDir();
		personThirdPagetemp.deleteTempDir();
		personFourthPagetemp.deleteTempDir();
		personFivethPagetemp.deleteTempDir();
		personSixPagetemp.deleteTempDir();
		webaddrString="/temp/"+year+'_'+month+'_'+filenameString+".zip";
		return webaddrString;
	}
	public String exportPersonsWorkNum_ForCt(String user, String department, String branch,String departmentID,String branchID,
			int year, int month,int startday, int endyear, int endmonth,String realPath){
		String webaddrString="";
		if (branch.equals("--请选择--")|| branch.indexOf("項目を選択してください")>0){
			branch=null;
			branchID = null;
		}
		PersonFirstPage_ForCt personFirstPagetemp=new PersonFirstPage_ForCt();
		PersonSecondPage_ForCt personSecondPagetemp=new PersonSecondPage_ForCt();
		PersonThirdPage_ForCt personThirdPagetemp=new PersonThirdPage_ForCt();
		PersonFourthPage_ForCt personFourthPagetemp=new PersonFourthPage_ForCt();
		PersonFivethPage_ForCt personFivethPagetemp=new PersonFivethPage_ForCt();
		PersonSixPage_ForCt personSixPagetemp=new PersonSixPage_ForCt();
		
		Calendar c1=Calendar.getInstance();//获得系统当前日期        
		int yearsys=c1.get(Calendar.YEAR);        
		int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起       
		int daysys=c1.get(Calendar.DAY_OF_MONTH);
		int hoursys=c1.get(Calendar.HOUR_OF_DAY);
		int minutessys=c1.get(Calendar.MINUTE);
		int secondsys=c1.get(Calendar.SECOND);
		String filenameString=String.valueOf(yearsys)+String.valueOf(monthsys)+String.valueOf(daysys)+String.valueOf(hoursys)+String.valueOf(minutessys)+String.valueOf(secondsys); 

		List<Project> listProject=new ArrayList<Project>();
		List<Project_task> listProjectTasks=calendarmanhourMapper.listDepartProjectTask_ct(departmentID);//列出本部门相关的Task
		Map<String,Object> paramsPerson = new HashMap<String, Object>();
		paramsPerson.put("departmentID",departmentID);
		paramsPerson.put("branchID",branchID);
		List<String> listtimeDate=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
		int startyear=year;
		int startmonth=month;
		while (startyear<endyear||(startyear==endyear&&startmonth<=endmonth)) {
			Calendar c = Calendar.getInstance(); //获取Calendar实例        
			c.set(Calendar.YEAR, startyear); //设置年        
			//c.set(Calendar.MONTH, startmonth-2); //设置月        
			//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期
			if (startday == 21 || startday == 26){
				c.set(Calendar.MONTH, startmonth - 2); //设置月        	
			} else if (startday == 1){
				c.set(Calendar.MONTH, startmonth - 1); //设置月
			}
			c.set(Calendar.DAY_OF_MONTH, startday);
			int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
			for (int m=1; m<=end; m++) { //循环打印即可                       
				//c.add(Calendar.DAY_OF_MONTH, 1);    
				Date date=c.getTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String date3=df.format(date);
				listtimeDate.add(date3);
				c.add(Calendar.DAY_OF_MONTH, 1);
				} 
			startmonth++;
			if (startmonth==13) {
				startmonth=1;
				startyear++;
			}
		}
		paramsPerson.put("index",listtimeDate.get(0));
	    int lastnumdate=listtimeDate.size()-1;
	    paramsPerson.put("end",listtimeDate.get(lastnumdate));
	    paramsPerson.put("departmentID",departmentID);
	    paramsPerson.put("branchID",branchID);
	    List<Integer> workNumProjectIdlist=calendarmanhourMapper.getDepartWorkNumProjectId_ct(paramsPerson);
	    for (int i = 0; i < workNumProjectIdlist.size(); i++) {
	    	Project projectTemp=calendarmanhourMapper.getProjectDetailByProjectId_ct(workNumProjectIdlist.get(i));
	    	listProject.add(projectTemp);
		}
	    
		List<Staff> staffIDlist=calendarmanhourMapper.getStaffIDlist_ct(paramsPerson);//部门某个月工数的所包含的所有员工ID
		for (int k = 0;  k< staffIDlist.size(); k++) {//部门课别员工循环
			//Staff staff=calendarmanhourMapper.getStaffdetail(staffIDlist.get(k));
			Staff staff=staffIDlist.get(k);
			String uniqueString=staff.getDepartment()+"_"+staff.getBranch()+"_"+staff.getStaffID()+"_"+staff.getName();
			//课别为空时不作为文件名
			if (staff.getBranch() == null || "".equals(staff.getBranch()) ){
				uniqueString=staff.getDepartment()+"_"+staff.getStaffID()+"_"+staff.getName();
			}
			//String uniqueString=department+"_"+branch+"_"+staff.getStaffID()+"_"+staff.getName();
			List<List<ManHourDto>> allMonthListManHourDtoList=new ArrayList<List<ManHourDto>>();
			List<Integer> workDayslist=new ArrayList<Integer>();
			List<List<AssumeDetail>> allAssumePJDetailList=new ArrayList<List<AssumeDetail>>();
			
			int yeartemp=year;
			int monthtemp=month;
			//循环开始
			while (yeartemp<endyear||(yeartemp==endyear&&monthtemp<=endmonth)) {
				List<String> listtimetemp=new ArrayList<String>();//某个月的第一天至最后一天的日期的集合
				Calendar c = Calendar.getInstance(); //获取Calendar实例        
				c.set(Calendar.YEAR, yeartemp); //设置年        
				//c.set(Calendar.MONTH, monthtemp-2); //设置月        
				//c.set(Calendar.DAY_OF_MONTH, 20); //设置月开始第一天日期        
				if (startday == 21 || startday == 26){
					c.set(Calendar.MONTH, monthtemp - 2); //设置月        	
				} else if (startday == 1){
					c.set(Calendar.MONTH, monthtemp - 1); //设置月
				}
				c.set(Calendar.DAY_OF_MONTH, startday);
				int end = c.getActualMaximum(Calendar.DAY_OF_MONTH); //获得月末日期        
				for (int m=1; m<=end; m++) { //循环打印即可                       
					//c.add(Calendar.DAY_OF_MONTH, 1);    
					Date date=c.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date3=df.format(date);
					listtimetemp.add(date3);
					c.add(Calendar.DAY_OF_MONTH, 1);
					} 
				monthtemp++;
				if (monthtemp==13) {
					monthtemp=1;
					yeartemp++;
				}
				List<AssumeDetail> assumePJNameList=new ArrayList<AssumeDetail>();
				List<ManHourDto> listManHourDtoTotaltemp=new ArrayList<ManHourDto>();
				Map<String,Object> paramsdepart = new HashMap<String, Object>();
				paramsdepart.put("index",listtimetemp.get(0));
			    int lastnum1=listtimetemp.size()-1;
			    paramsdepart.put("end",listtimetemp.get(lastnum1));
			    paramsdepart.put("departmentID",departmentID);
			    paramsdepart.put("branchID",branchID);
			    paramsdepart.put("staffID",staff.getStaffID());
			    workDayslist.add(calendarmanhourMapper.getWorkDays(paramsdepart));
			    
		    	AssumeDetail assumeDetailtemp=new AssumeDetail();
		    	assumeDetailtemp.setAssumePJNum(calendarmanhourMapper.getPersonAssumePJNum_ct(paramsdepart));
		    	assumeDetailtemp.setAssumePJName(calendarmanhourMapper.getPersonAssumePJName_ct(paramsdepart));
		    	assumePJNameList.add(assumeDetailtemp);

		    	allAssumePJDetailList.add(assumePJNameList);
				listManHourDtoTotaltemp=calendarmanhourMapper.listPersonManhourDtoTotal_ct(paramsdepart);//与本部门或者本课别有关的所有工数
				
				allMonthListManHourDtoList.add(listManHourDtoTotaltemp);
			}
			//循环结束
			personFirstPagetemp.personWorkNumFirstPage_ForCt(user, department, branch, year, month, endyear, endmonth, uniqueString, allMonthListManHourDtoList, listProject, listProjectTasks, workDayslist, allAssumePJDetailList);
			personSecondPagetemp.updatePersonWorkNumSecondXLS_ForCt(user, department,branch, listProject, year, month, endyear, endmonth, uniqueString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
			personThirdPagetemp.updatePersonWorkNumThirdXLS_ForCt(user, department, branch, listProject, year, month, endyear, endmonth, uniqueString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
			personFourthPagetemp.updatePersonWorkNumFourthXLS_ForCt(user, department, branch, listProject, year, month, endyear, endmonth, uniqueString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
			personFivethPagetemp.updatePersonWorkNumFivethXLS_ForCt(user, department, branch, listProject, year, month, endyear, endmonth, uniqueString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
			personSixPagetemp.updatePersonWorkNumSixXLS_ForCt(user, department, branch, listProject, year, month, endyear, endmonth, uniqueString, allMonthListManHourDtoList, listProjectTasks, workDayslist);
		}
		
		File temp = new File("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月"); 
		if (temp.exists()) {
			ZipCompressorByAnt zca = new ZipCompressorByAnt(realPath+"\\"+year+'_'+month+'_'+filenameString+".zip");
			zca.compress("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月");
			personSixPagetemp.deleteTempDir("D:\\"+department+"\\"+department+"\\"+year+"\\"+month+"月");
			personFirstPagetemp.deleteTempDir();
			personSecondPagetemp.deleteTempDir();
			personThirdPagetemp.deleteTempDir();
			personFourthPagetemp.deleteTempDir();
			personFivethPagetemp.deleteTempDir();
			webaddrString="/temp/"+year+'_'+month+'_'+filenameString+".zip";
		}
		return webaddrString;
	}
	@Override
	public List<departmentBranch> listDepartment() {
		// TODO Auto-generated method stub
		return calendarmanhourMapper.listDepartment();
	}
	@Override
	public List<departmentBranch> branchSelect(String departmentID) {
		// TODO Auto-generated method stub
		return calendarmanhourMapper.branchSelect(departmentID);
	}
	@Override
	public List<departmentBranch> listDepartmentCT() {
		// TODO Auto-generated method stub
		return calendarmanhourMapper.listDepartmentCT();
	}
	@Override
	public List<departmentBranch> branchSelectCT(String departmentID) {
		// TODO Auto-generated method stub
		return calendarmanhourMapper.branchSelectCT(departmentID);
	}
	public List<String> getCxeeCsvData(Map<String, Object> params){
		List<String> dataList = new ArrayList<String>();
		List<CsvData> csvdataList = calendarmanhourMapper.getCxeeCsvData(params);
		String tempData = null;
		for(CsvData csvdata: csvdataList){
			tempData = "\"" + csvdata.getCompanyName()  
					+ "\",\"" + csvdata.getName()
					+ "\",\""  + csvdata.getJobNo()
					+ "\",\""  + csvdata.getDepartment()
					+ "\",\""  + csvdata.getBranch()
					+ "\",\""  + csvdata.getPosition()
					+ "\",\""  + csvdata.getSort()
					+ "\",\""  + csvdata.getPJNo()
					+ "\",\""  + csvdata.getPJName()
					+ "\",\""  + csvdata.getTempPJNo()
					+ "\",\""  + csvdata.getTransferNo()
					+ "\",\""  + csvdata.getItemName()
					+ "\",\""  + csvdata.getProjectName()
					+ "\",\""  + csvdata.getCategory()
					+ "\",\""  + csvdata.getProjectClientNo()
					+ "\",\""  + csvdata.getProjectClientName()
					+ "\",\""  + csvdata.getStartupDate()
					+ "\",\""  + csvdata.getFinishDate()
					+ "\",\""  + csvdata.getProjectState()
					+ "\",\""  + csvdata.getCarmake()
					+ "\",\""  + csvdata.getFunctionName()
					+ "\",\""  + csvdata.getModel()
					+ "\",\""  + csvdata.getMemo()
					+ "\",\""  + csvdata.getTaskID()
					+ "\",\""  + csvdata.getTask()
					+ "\",\""  + csvdata.getDate()
					+ "\",\""  + csvdata.getTimes() + "\"";
			dataList.add(tempData);
		}
		return dataList;
	}
	public List<String> getCxeeCsvData_ForCt(Map<String, Object> params){
		List<String> dataList = new ArrayList<String>();
		List<CsvData> csvdataList = calendarmanhourMapper.getCxeeCsvData_ForCt(params);
		String tempData = null;
		for(CsvData csvdata: csvdataList){
			tempData = "\"" + csvdata.getCompanyName()  
					+ "\",\"" + csvdata.getName()
					+ "\",\""  + csvdata.getJobNo()
					+ "\",\""  + csvdata.getEnrolementCode()
					+ "\",\""  + csvdata.getDepartment()
					+ "\",\""  + csvdata.getBranch()
					+ "\",\""  + csvdata.getPosition()
					+ "\",\""  + csvdata.getJobCategory()
					+ "\",\""  + csvdata.getSortID()
					+ "\",\""  + csvdata.getSort()
					+ "\",\""  + csvdata.getPJNo()
					+ "\",\""  + csvdata.getPJName()
					+ "\",\""  + csvdata.getTempPJNo()
					+ "\",\""  + csvdata.getTransferNo()
					+ "\",\""  + csvdata.getItemName()
					+ "\",\""  + csvdata.getCategory()
					+ "\",\""  + csvdata.getStartupDate()
					+ "\",\""  + csvdata.getFinishDate()
					+ "\",\""  + csvdata.getProjectState()
					+ "\",\""  + csvdata.getCarmake()
					+ "\",\""  + csvdata.getFunctionName()
					+ "\",\""  + csvdata.getMemo()
					+ "\",\""  + csvdata.getEnterpriseSeg()
					+ "\",\""  + csvdata.getComponentID()
					+ "\",\""  + csvdata.getComponentName()
					+ "\",\""  + csvdata.getComponentSortName()
					+ "\",\""  + csvdata.getTaskID()
					+ "\",\""  + csvdata.getTask()
					+ "\",\""  + csvdata.getDate()
					+ "\",\""  + csvdata.getTimes() + "\"";
			dataList.add(tempData);
		}
		return dataList;
	}
}
