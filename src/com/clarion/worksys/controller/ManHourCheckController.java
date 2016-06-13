package com.clarion.worksys.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.Blacklist;
import com.clarion.worksys.entity.BlacklistCT;
import com.clarion.worksys.entity.ManhourCheckErrorParam;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.httpentity.BlacklistResponse;
import com.clarion.worksys.httpentity.BlacklistResponseRows;
import com.clarion.worksys.service.CalendarManhourService;
import com.clarion.worksys.service.ManHourService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.BlackListMail;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.DataToExcle;
import com.clarion.worksys.util.Tools;
import com.clarion.worksys.util.TransDate;
import com.google.gson.Gson;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 工数黑名单检查controller
 * @author weng_zhangchu
 *
 */
@Controller
@RequestMapping(value = "/hourCheck")
public class ManHourCheckController {
	@Autowired
	private ManHourService manHourService;
	@Autowired
	private CalendarManhourService calendarManhourService;
	@Autowired
	private UserRoleManageService userRoleManageService;
	@RequestMapping
	public ModelAndView showDefault(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/hourManage/hourCheck");
		Staff staff = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		mv.addObject("staff", staff);
		//取得部门的LIST
		//List<String> departList =   calendarManhourService.listAllDepart();
		List<Map<String,String>> departList =null ;
		
		
		if (staff.getEmail().indexOf(".co.jp")>0){
			departList = calendarManhourService.listAllDepart_ForCt();
		}else{
			departList=  calendarManhourService.listAllDepart();
		}
		
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"BlackList",userRoleManageService);
		mv.addObject("staff", staff);
		mv.addObject("QueryRoleFlag", userRole.getQueryRoleFlag());
		mv.addObject("DownloadRoleFlag", userRole.getDownloadRoleFlag());//D/L
		mv.addObject("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
		mv.addObject("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
		mv.addObject("DepartmentFlag", userRole.getDepartmentFlag());//部门权限
		mv.addObject("departList", departList);
		mv.addObject("mv", mv);
		return mv;
	}

	/**
	 * 输入查询的年月得到工数填写黑名单并发送邮件
	 * @param manhourCheckErrorParam
	 * 
	 */
	@RequestMapping(value ="/sendMail")
	public ModelAndView sendMail(ManhourCheckErrorParam manhourCheckErrorParam, HttpSession httpSession) {
		String kaista = manhourCheckErrorParam.getKaisya();
		
		if ("CT".endsWith(kaista)) {
			return sendMailCT(manhourCheckErrorParam,httpSession);
		} else if ("CXEE".endsWith(kaista)) {
			return sendMailCXEE(manhourCheckErrorParam,httpSession);
		}
		return null;
	}
	
	public ModelAndView sendMailCXEE(ManhourCheckErrorParam manhourCheckErrorParam, HttpSession httpSession) {
		Staff staff = (Staff)httpSession.getAttribute(Const.SESSION_USER);
		manhourCheckErrorParam.setCreateStaffID(staff.getStaffID());
    	List<Blacklist> allBlacklists = manHourService.selectAllBlacklist(manhourCheckErrorParam);
		BlackListMail blackListMail   = new BlackListMail();
		blackListMail.setBlacklist(allBlacklists);
		int count = blackListMail.sendMailToBlackList();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/hourManage/mailResult");
		mv.addObject("count", count);
		return mv;
		
	}
	
	public ModelAndView sendMailCT(ManhourCheckErrorParam manhourCheckErrorParam, HttpSession httpSession) {
		Staff staff = (Staff)httpSession.getAttribute(Const.SESSION_USER);
		manhourCheckErrorParam.setCreateStaffID(staff.getStaffID());
    	List<BlacklistCT> allBlacklists = manHourService.getManhourTotalTimesCT(manhourCheckErrorParam);
    	
    	int totalworkday = manHourService.getTotalWorkday(manhourCheckErrorParam);
    	
    	double totalworkhour = totalworkday * 7.75;
		BlackListMail blackListMail   = new BlackListMail();
		blackListMail.setBlacklistCT(allBlacklists);
		int count = blackListMail.sendMailToBlackListCT(totalworkhour);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/hourManage/mailResult");
		mv.addObject("count", count);
		return mv;
		
	}

	/**
	 * 
	 * @param response
	 * @param httpSession
	 * @param manhourCheckErrorParam
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/outPut")
	public ModelAndView outPutBlackList(HttpServletResponse response, HttpSession httpSession, ManhourCheckErrorParam manhourCheckErrorParam) throws IOException {
		String kaista = manhourCheckErrorParam.getKaisya();
		
		if ("CT".endsWith(kaista)) {
			return outPutBlackListCT(response,httpSession,manhourCheckErrorParam);
		} else if ("CXEE".endsWith(kaista)) {
			return outPutBlackListCXEE(response,httpSession,manhourCheckErrorParam);
		}
		return null;
	}
	
	public ModelAndView outPutBlackListCXEE(HttpServletResponse response, HttpSession httpSession, ManhourCheckErrorParam manhourCheckErrorParam) throws IOException {
		Staff staff = (Staff)httpSession.getAttribute(Const.SESSION_USER);
		manhourCheckErrorParam.setCreateStaffID(staff.getStaffID());
		DataToExcle newClass = new DataToExcle();
		List<Blacklist> allBlacklists = manHourService.selectAllBlacklist(manhourCheckErrorParam);
		for (int i = 0; i < allBlacklists.size(); i++) {
			if (allBlacklists.get(i).getTotalTimes() == null) {
				allBlacklists.get(i).setTotalTimes("0.00");
			}
		}
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath    = httpSession.getServletContext().getContextPath(); 
		String webadr         = contextpath + toExcel(manhourCheckErrorParam,allBlacklists,realPathString);
		ModelAndView mv       = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	
	public ModelAndView outPutBlackListCT(HttpServletResponse response, HttpSession httpSession, ManhourCheckErrorParam manhourCheckErrorParam) throws IOException {
		Staff staff = (Staff)httpSession.getAttribute(Const.SESSION_USER);
		manhourCheckErrorParam.setCreateStaffID(staff.getStaffID());
		DataToExcle newClass = new DataToExcle();
		List<BlacklistCT> allBlacklists = manHourService.selectAllBlacklistCT(manhourCheckErrorParam);
		for (int i = 0; i < allBlacklists.size(); i++) {
			if (allBlacklists.get(i).getTotalTimes() == null) {
				allBlacklists.get(i).setTotalTimes("0.00");
			}
		}
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath    = httpSession.getServletContext().getContextPath(); 
		String webadr         = contextpath + toExcel(manhourCheckErrorParam,allBlacklists,realPathString);
		ModelAndView mv       = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	
	public String toExcel(ManhourCheckErrorParam manhourCheckErrorParam,List list,String realpath) {

        Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "Blacklist_"
        					  +String.valueOf(c1.get(Calendar.YEAR))
                              + String.valueOf(c1.get(Calendar.MONTH) + 1)  //系统日期从0开始算起
                              + String.valueOf(c1.get(Calendar.DAY_OF_MONTH))
                              + String.valueOf(c1.get(Calendar.HOUR_OF_DAY))
                              + String.valueOf(c1.get(Calendar.MINUTE))
                              + String.valueOf(c1.get(Calendar.SECOND));
        String strnameString2 = realpath + "\\" +filenameString + ".xls";

        //1.新建一个excel表格
        //从TransDate 转化List 得到的是String数组的List
        //将Title数组和String数组写入Excel
        //返回文件路径
        String webaddrString  = "/temp/" + filenameString + ".xls";

        try {

            TransDate transDate = new TransDate();
            try {
                //从TransDate 转化List 得到的是String数组的List
                List<String[]> listdata = transDate.getDateList(list);
                WritableWorkbook book   = Workbook.createWorkbook(new File(strnameString2));
                WritableSheet sheet     = book.createSheet("Blacklist", 0);

                //标题
                	sheet.mergeCells(0, 0, 5,1 );
                	String title = "検索期間："+manhourCheckErrorParam.getStartDate()+"～"+manhourCheckErrorParam.getEndDate();
                    WritableFont font2= new WritableFont(WritableFont.TIMES,14,WritableFont.BOLD);
                    WritableCellFormat format2=new WritableCellFormat(font2);
                	format2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
                	format2.setAlignment(jxl.format.Alignment.LEFT);
                	Label labelx = new Label(0, 0, title,format2);
                	sheet.addCell(labelx);
                    for (int j = 0; j < listdata.get(0).length; j++) {
                        int maxlength=0;
                        for (int i = 0; i < listdata.size(); i++) {
                            WritableFont font1= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
                            WritableCellFormat format1=new WritableCellFormat(font1);
                            format1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
                            format1.setAlignment(jxl.format.Alignment.CENTRE);

                            boolean isNum = DataToExcle.isNumeric(listdata.get(i)[j]);
                            if (isNum==true&&(!listdata.get(i)[j].equals(""))) {
                                /* jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");    //设置数字格式
                                jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); //设置表单格式
                                jxl.write.Number labelNF = new jxl.write.Number(j, i, Double.valueOf(listdata.get(i)[j]),wcfN); //格式化数值
                                sheet.addCell(labelNF); */
                                sheet.addCell(new Number(j, i+2, Double.valueOf(listdata.get(i)[j]), format1));
                            }
                            else {
                                Label label1 = new Label(j, i+2, listdata.get(i)[j],format1);
                                sheet.addCell(label1);
                            }
                            //System.out.println(j+"hahahahah"+listdata.get(i)[j]+"++"+i);
                            if (listdata.get(i)[j].length()*2>maxlength) {
                                maxlength=listdata.get(i)[j].length()*2;
                            }

                        }
                        sheet.setColumnView(j,maxlength);
                    }
                    //写Excel表格一行
                    book.write();
                    //close file case.
                    book.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webaddrString;
    }

	/**
	 * 检查工数填写错误信息
	 * @param manhourCheckErrorParam
	 * @param response
	 * @param httpSession
	 * @throws IOException
	 */
	@RequestMapping(value ="/checkerror")
	public void checkBlacklist(ManhourCheckErrorParam manhourCheckErrorParam, HttpServletResponse response, HttpSession httpSession) throws IOException{
		if("1".equals(manhourCheckErrorParam.getQueryRoleFlag())){
			String kaista = manhourCheckErrorParam.getKaisya();
			
			if ("CT".endsWith(kaista)) {
				checkBlacklistCT(manhourCheckErrorParam,response,httpSession);
			} else if ("CXEE".endsWith(kaista)) {
				checkBlacklistCXEE(manhourCheckErrorParam,response,httpSession);
			}
		}else{
			PrintWriter out = response.getWriter();
			out.write("");
			out.close();
		}
		
			
		 /*		
		
		Staff staff = (Staff)httpSession.getAttribute(Const.SESSION_USER);
        manhourCheckErrorParam.setCreateStaffID(staff.getStaffID());

        //设置数据库查询时从哪条数据开始
        manhourCheckErrorParam.setPageSeq((manhourCheckErrorParam.getPage() - 1) * manhourCheckErrorParam.getRp());
        //如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(manhourCheckErrorParam.getName()== null || manhourCheckErrorParam.getName().trim().equals("")){
            manhourCheckErrorParam.setName(null);
        }
        if ("--请选择--".equals(manhourCheckErrorParam.getDepartmentID())) {
            manhourCheckErrorParam.setDepartmentID(null);
        }
        if ("--请选择--".equals(manhourCheckErrorParam.getBranchID())) {
            manhourCheckErrorParam.setBranchID(null);
        }
        if(manhourCheckErrorParam.getSerachSuperior() == null || manhourCheckErrorParam.getSerachSuperior().trim().equals("")){
            manhourCheckErrorParam.setSerachSuperior(null);
        }
        */
        /*
        if(manhourCheckErrorParam.getQuery()!=null &&!manhourCheckErrorParam.getQuery().trim().equals("")){
            if(manhourCheckErrorParam.getQtype().trim().equals("name")){
                manhourCheckErrorParam.setName(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("email")){
                manhourCheckErrorParam.setEmail(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("department")){
                manhourCheckErrorParam.setDepartment(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("branch")){
                manhourCheckErrorParam.setBranch(manhourCheckErrorParam.getQuery().trim());
            }
        } */
		/*	
        if (!manhourCheckErrorParam.getStartDate().equals(httpSession.getAttribute("startDate")) ||
        		!manhourCheckErrorParam.getEndDate().equals(httpSession.getAttribute("endDate"))) {
        	manHourService.callCheckError(manhourCheckErrorParam);
        	httpSession.setAttribute("startDate", manhourCheckErrorParam.getStartDate());
        	httpSession.setAttribute("endDate", manhourCheckErrorParam.getEndDate());
        }

        List<Blacklist> blacklists = manHourService.selectBlacklist(manhourCheckErrorParam);
        for (int i = 0; i < blacklists.size(); i++) {
            if (blacklists.get(i).getTotalTimes() == null) {
                blacklists.get(i).setTotalTimes("0.00");
            }
        }
        int totalPageCountForBlacklist = manHourService.TotalPageCountForBlacklist(manhourCheckErrorParam);
        List<BlacklistResponseRows> blacklistResponseRowsList = new ArrayList<BlacklistResponseRows>();
        for (int i = 0; i < blacklists.size(); i++) {
            BlacklistResponseRows blacklistResponseRows=new BlacklistResponseRows(i, blacklists.get(i));
            if (blacklistResponseRows.getCell().getTotalTimes() == null) {
                blacklistResponseRows.getCell().setTotalTimes("0.00");
            }
            blacklistResponseRowsList.add(i, blacklistResponseRows);
        }
        BlacklistResponse blacklistResponse = new BlacklistResponse();
        blacklistResponse.setPage(manhourCheckErrorParam.getPage());
        blacklistResponse.setTotal(totalPageCountForBlacklist);
        blacklistResponse.setRows(blacklistResponseRowsList);

        Gson gson = new Gson();
        String resultString = gson.toJson(blacklistResponse);

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(resultString);
        out.close();
        */
    }
	
	public void checkBlacklistCT(ManhourCheckErrorParam manhourCheckErrorParam, HttpServletResponse response, HttpSession httpSession) throws IOException{
		Staff staff = (Staff)httpSession.getAttribute(Const.SESSION_USER);
        manhourCheckErrorParam.setCreateStaffID(staff.getStaffID());

        //设置数据库查询时从哪条数据开始
        manhourCheckErrorParam.setPageSeq((manhourCheckErrorParam.getPage() - 1) * manhourCheckErrorParam.getRp());
        //如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(manhourCheckErrorParam.getName()== null || manhourCheckErrorParam.getName().trim().equals("")){
            manhourCheckErrorParam.setName(null);
        }
        if ("--请选择--".equals(manhourCheckErrorParam.getDepartmentID()) || manhourCheckErrorParam.getDepartmentID().indexOf("項目を選択してください")>0) {
            manhourCheckErrorParam.setDepartmentID(null);
        }
        if ("--请选择--".equals(manhourCheckErrorParam.getBranchID()) || manhourCheckErrorParam.getBranchID().indexOf("項目を選択してください")>0) {
            manhourCheckErrorParam.setBranchID(null);
        }
        if(manhourCheckErrorParam.getSerachSuperior() == null || manhourCheckErrorParam.getSerachSuperior().trim().equals("")){
            manhourCheckErrorParam.setSerachSuperior(null);
        }
        /*
        if(manhourCheckErrorParam.getQuery()!=null &&!manhourCheckErrorParam.getQuery().trim().equals("")){
            if(manhourCheckErrorParam.getQtype().trim().equals("name")){
                manhourCheckErrorParam.setName(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("email")){
                manhourCheckErrorParam.setEmail(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("department")){
                manhourCheckErrorParam.setDepartment(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("branch")){
                manhourCheckErrorParam.setBranch(manhourCheckErrorParam.getQuery().trim());
            }
        } 
        if (!manhourCheckErrorParam.getStartDate().equals(httpSession.getAttribute("startDate")) ||
        		!manhourCheckErrorParam.getEndDate().equals(httpSession.getAttribute("endDate"))) {
        	manHourService.callCheckError(manhourCheckErrorParam);
        	httpSession.setAttribute("startDate", manhourCheckErrorParam.getStartDate());
        	httpSession.setAttribute("endDate", manhourCheckErrorParam.getEndDate());
        }*/

        List<Blacklist> blacklists = manHourService.selectBlacklistCT(manhourCheckErrorParam);
        for (int i = 0; i < blacklists.size(); i++) {
            if (blacklists.get(i).getTotalTimes() == null) {
                blacklists.get(i).setTotalTimes("0.00");
            }
        }
        int totalPageCountForBlacklist = manHourService.TotalPageCountForBlacklistCT(manhourCheckErrorParam);
        List<BlacklistResponseRows> blacklistResponseRowsList = new ArrayList<BlacklistResponseRows>();
        for (int i = 0; i < blacklists.size(); i++) {
            BlacklistResponseRows blacklistResponseRows=new BlacklistResponseRows(i, blacklists.get(i));
            if (blacklistResponseRows.getCell().getTotalTimes() == null) {
                blacklistResponseRows.getCell().setTotalTimes("0.00");
            }
            blacklistResponseRowsList.add(i, blacklistResponseRows);
        }
        BlacklistResponse blacklistResponse = new BlacklistResponse();
        blacklistResponse.setPage(manhourCheckErrorParam.getPage());
        blacklistResponse.setTotal(totalPageCountForBlacklist);
        blacklistResponse.setRows(blacklistResponseRowsList);

        Gson gson = new Gson();
        String resultString = gson.toJson(blacklistResponse);

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(resultString);
        out.close();
    }
	
	public void checkBlacklistCXEE(ManhourCheckErrorParam manhourCheckErrorParam, HttpServletResponse response, HttpSession httpSession) throws IOException{
		Staff staff = (Staff)httpSession.getAttribute(Const.SESSION_USER);
        manhourCheckErrorParam.setCreateStaffID(staff.getStaffID());

        //设置数据库查询时从哪条数据开始
        manhourCheckErrorParam.setPageSeq((manhourCheckErrorParam.getPage() - 1) * manhourCheckErrorParam.getRp());
        //如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(manhourCheckErrorParam.getName()== null || manhourCheckErrorParam.getName().trim().equals("")){
            manhourCheckErrorParam.setName(null);
        }
        if ("--请选择--".equals(manhourCheckErrorParam.getDepartmentID())|| manhourCheckErrorParam.getDepartmentID().indexOf("項目を選択してください")>0) {
            manhourCheckErrorParam.setDepartmentID(null);
        }
        if ("--请选择--".equals(manhourCheckErrorParam.getBranchID())|| manhourCheckErrorParam.getBranchID().indexOf("項目を選択してください")>0) {
            manhourCheckErrorParam.setBranchID(null);
        }
        if(manhourCheckErrorParam.getSerachSuperior() == null || manhourCheckErrorParam.getSerachSuperior().trim().equals("")){
            manhourCheckErrorParam.setSerachSuperior(null);
        }
        /*
        if(manhourCheckErrorParam.getQuery()!=null &&!manhourCheckErrorParam.getQuery().trim().equals("")){
            if(manhourCheckErrorParam.getQtype().trim().equals("name")){
                manhourCheckErrorParam.setName(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("email")){
                manhourCheckErrorParam.setEmail(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("department")){
                manhourCheckErrorParam.setDepartment(manhourCheckErrorParam.getQuery().trim());
            }else if(manhourCheckErrorParam.getQtype().trim().equals("branch")){
                manhourCheckErrorParam.setBranch(manhourCheckErrorParam.getQuery().trim());
            }
        } 
        if (!manhourCheckErrorParam.getStartDate().equals(httpSession.getAttribute("startDate")) ||
        		!manhourCheckErrorParam.getEndDate().equals(httpSession.getAttribute("endDate"))) {
        	//manHourService.callCheckError(manhourCheckErrorParam);
        	httpSession.setAttribute("startDate", manhourCheckErrorParam.getStartDate());
        	httpSession.setAttribute("endDate", manhourCheckErrorParam.getEndDate());
        }*/

        List<Blacklist> blacklists = manHourService.selectBlacklist(manhourCheckErrorParam);
        for (int i = 0; i < blacklists.size(); i++) {
            if (blacklists.get(i).getTotalTimes() == null) {
                blacklists.get(i).setTotalTimes("0.00");
            }
        }
        int totalPageCountForBlacklist = manHourService.TotalPageCountForBlacklist(manhourCheckErrorParam);
        List<BlacklistResponseRows> blacklistResponseRowsList = new ArrayList<BlacklistResponseRows>();
        for (int i = 0; i < blacklists.size(); i++) {
            BlacklistResponseRows blacklistResponseRows=new BlacklistResponseRows(i, blacklists.get(i));
            if (blacklistResponseRows.getCell().getTotalTimes() == null) {
                blacklistResponseRows.getCell().setTotalTimes("0.00");
            }
            blacklistResponseRowsList.add(i, blacklistResponseRows);
        }
        BlacklistResponse blacklistResponse = new BlacklistResponse();
        blacklistResponse.setPage(manhourCheckErrorParam.getPage());
        blacklistResponse.setTotal(totalPageCountForBlacklist);
        blacklistResponse.setRows(blacklistResponseRowsList);

        Gson gson = new Gson();
        String resultString = gson.toJson(blacklistResponse);

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(resultString);
        out.close();
    }
}
