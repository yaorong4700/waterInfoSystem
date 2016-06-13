package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * DownLoad Controller
 * 
 * @author chen_weijun@clarion.com.cn
 * @create: 2013-01-08
 * @histroy:
 * 	2013-01-08 ChenWeijun
 *  # 初版
 *   
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.service.CalendarManhourService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.DataToCSV;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/output")
public class OutputController {

	@Autowired
	private CalendarManhourService calendarManhourService;
	@Autowired
	private UserRoleManageService userRoleManageService;

	/**
	 * 跳转到工数下载界面
	 * @return
	 */
	@RequestMapping
	public ModelAndView showDefault(HttpSession httpSession) {
		//List<String> departList =   calendarManhourService.listAllDepart();
		Staff staff = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		List<Map<String,String>> departList =   calendarManhourService.listAllDepart();
		List<Map<String,String>> departList_ForCt =   calendarManhourService.listAllDepart_ForCt();
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"Output",userRoleManageService);
		ModelAndView mv = new ModelAndView();
		mv.addObject("staff", staff);
		mv.addObject("departList", departList);
		mv.addObject("departList_ForCt", departList_ForCt);
		mv.addObject("DownloadRoleFlag", userRole.getDownloadRoleFlag());//D/L
		mv.addObject("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
		mv.addObject("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
		mv.addObject("DepartmentFlag", userRole.getDepartmentFlag());//部门权限
		mv.setViewName("admin/manhour/reportExport");
		return mv;
	}

	/**
	 * 下载公司、部门、课别的集计信息
	 * @param department 部门名
	 * @param branch 部门名
	 * @param departmentID
	 * @param branchID
	 * @param year 起始年份
	 * @param month 起始月份
	 * @param endyear 截止年份
	 * @param endmonth 截止月份
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/outputDepartJijiExcel", method = RequestMethod.GET)
	public ModelAndView saveDepartcalendarManhour(String department,String branch,String departmentID,String branchID,int year,int month,int startday,int endyear,int endmonth,HttpServletResponse response,HttpSession httpSession) throws IOException {

		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath();
		//把页面传的参数，转换成中文
		department = java.net.URLDecoder.decode(java.net.URLDecoder.decode(department,"UTF-8"),"UTF-8");
		branch= java.net.URLDecoder.decode(java.net.URLDecoder.decode(branch,"UTF-8"),"UTF-8");
		
		String webadr=contextpath+calendarManhourService.outputDepartJijimanhour(department,branch,departmentID,branchID,year,month,startday,endyear,endmonth,realPathString);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}

	/**
	 * 下载公司、部门、课别的集计信息_ForCt
	 * @param department 部门名
	 * @param branch 部门名
	 * @param departmentID
	 * @param branchID
	 * @param year 起始年份
	 * @param month 起始月份
	 * @param endyear 截止年份
	 * @param endmonth 截止月份
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/outputDepartJijiExcel_ForCt", method = RequestMethod.GET)
	public ModelAndView saveDepartcalendarManhour_ForCt(String department,String branch,String departmentID,String branchID,int year,int month,int startday,int endyear,int endmonth,HttpServletResponse response,HttpSession httpSession) throws IOException {

		Staff staff = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath();
		//把页面传的参数，转换成中文
		department = java.net.URLDecoder.decode(java.net.URLDecoder.decode(department,"UTF-8"),"UTF-8");
		branch= java.net.URLDecoder.decode(java.net.URLDecoder.decode(branch,"UTF-8"),"UTF-8");
		
		String webadr=contextpath+calendarManhourService.outputDepartJijimanhour_ForCt(staff.getName(),department,branch,departmentID,branchID,year,month,startday,endyear,endmonth,realPathString);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}

	/**
	 * 下载个人集记的信息
	 * @param department 部门名
	 * @param branch 课别名
	 * @param departmentID
	 * @param branchID
	 * @param year 起始年份
	 * @param month 起始月份
	 * @param endyear 截止年份
	 * @param endmonth 截止月份
	 * @param year
	 * @param month
	 * @param endyear
	 * @param endmonth
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportPersonsWorkNum", method = RequestMethod.GET)
	public ModelAndView exportPersonsWorkNum(String department,String branch,String departmentID,String branchID,int year,int month,int startday,int endyear,int endmonth,HttpServletResponse response,HttpSession httpSession) throws IOException {

		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath();
		
		//把页面传的参数，转换成中文
		department = java.net.URLDecoder.decode(java.net.URLDecoder.decode(department,"UTF-8"),"UTF-8");
		branch= java.net.URLDecoder.decode(java.net.URLDecoder.decode(branch,"UTF-8"),"UTF-8");
		
		String webadr=contextpath+calendarManhourService.exportPersonsWorkNum(department,branch,departmentID,branchID,year,month,startday,endyear,endmonth,realPathString);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}

	/**
	 * 下载个人集记的信息_ForCt
	 * @param department 部门名
	 * @param branch 课别名
	 * @param departmentID
	 * @param branchID
	 * @param year 起始年份
	 * @param month 起始月份
	 * @param endyear 截止年份
	 * @param endmonth 截止月份
	 * @param year
	 * @param month
	 * @param endyear
	 * @param endmonth
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportPersonsWorkNum_ForCt", method = RequestMethod.GET)
	public ModelAndView exportPersonsWorkNum_ForCt(String department,String branch,String departmentID,String branchID,int year,int month,int startday,int endyear,int endmonth,HttpServletResponse response,HttpSession httpSession) throws IOException {

		Staff staff = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath();
		
		//把页面传的参数，转换成中文
		department = java.net.URLDecoder.decode(java.net.URLDecoder.decode(department,"UTF-8"),"UTF-8");
		branch= java.net.URLDecoder.decode(java.net.URLDecoder.decode(branch,"UTF-8"),"UTF-8");
		
		String webadr=contextpath+calendarManhourService.exportPersonsWorkNum_ForCt(staff.getName(),department,branch,departmentID,branchID,year,month,startday,endyear,endmonth,realPathString);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}

	/**
	 * 下载CXEE侧CSV文件
	 * @param department 部门名
	 * @param branch 部门名
	 * @param departmentID
	 * @param branchID
	 * @param year 起始年份
	 * @param month 起始月份
	 * @param endyear 截止年份
	 * @param endmonth 截止月份
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportCXEECSV", method = RequestMethod.GET)
	public ModelAndView exportCXEECSV(String department,String branch,String departmentID,String branchID,String year,String month,String endyear,String endmonth,String belong,HttpServletResponse response,HttpSession httpSession) throws IOException {

		Staff staff = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath();
		//把页面传的参数，转换成中文
		department = java.net.URLDecoder.decode(java.net.URLDecoder.decode(department,"UTF-8"),"UTF-8");
		branch= java.net.URLDecoder.decode(java.net.URLDecoder.decode(branch,"UTF-8"),"UTF-8");
		//计算开始终了年月
		String startDate = null;
		String endDate = null;
		if (branch.equals("--请选择--")|| branch.indexOf("項目を選択してください")>0){
			branch=null;
			branchID = null;
		}
		if("1".equals(month))//判断查询的起始月份是否为特殊的一月份
		{
			month = "12";//特殊的一月份是从上一年度的十二月份起始查询
			int tempYear =Integer.parseInt(year)-1;//起始查询的年份应为上一年度
			year = tempYear+"";
			startDate = year + "-" + month + "-21";
			endDate = endyear + "-" + endmonth + "-20";
		}
		else if(month!=null){//
			int tempMonth =Integer.parseInt(month)-1;
			month = tempMonth+"";
			startDate = year + "-" + month + "-21";
			endDate = endyear + "-" + endmonth + "-20";
		}
		//获得CSV出力用数据
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("departmentID", departmentID);
		params.put("branchID", branchID);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		String webadr="";
		if ("1".equals(belong)) {
			List<String> dataList = calendarManhourService.getCxeeCsvData_ForCt(params);
			//CSV出力
			webadr=contextpath+DataToCSV.exportCsv_ForCt(staff.getName(),realPathString, Parma.csvTitle_ForCt, dataList);
		} else {
			List<String> dataList = calendarManhourService.getCxeeCsvData(params);
			//CSV出力
			webadr=contextpath+DataToCSV.exportCsv(staff.getName(),realPathString, Parma.csvTitle, dataList);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	/**
	 * 下载CXEE侧CSV文件_ForCt
	 * @param department 部门名
	 * @param branch 部门名
	 * @param departmentID
	 * @param branchID
	 * @param year 起始年份
	 * @param month 起始月份
	 * @param endyear 截止年份
	 * @param endmonth 截止月份
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportCXEECSV_ForCt", method = RequestMethod.GET)
	public ModelAndView exportCXEECSV_ForCt(String department,String branch,String departmentID,String branchID,String year,String month,String endyear,String endmonth,String belong,HttpServletResponse response,HttpSession httpSession) throws IOException {

		Staff staff = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath();
		//把页面传的参数，转换成中文
		department = java.net.URLDecoder.decode(java.net.URLDecoder.decode(department,"UTF-8"),"UTF-8");
		branch= java.net.URLDecoder.decode(java.net.URLDecoder.decode(branch,"UTF-8"),"UTF-8");
		if (branch.equals("--请选择--")|| branch.indexOf("項目を選択してください")>0){
			branch=null;
			branchID = null;
		}
		if (department.equals("--请选择--")|| department.indexOf("項目を選択してください")>0){
			department=null;
			departmentID = null;
		}
		//计算开始终了年月
		String startDate = null;
		String endDate = null;
		if("1".equals(month))//判断查询的起始月份是否为特殊的一月份
		{
			month = "12";//特殊的一月份是从上一年度的十二月份起始查询
			int tempYear =Integer.parseInt(year)-1;//起始查询的年份应为上一年度
			year = tempYear+"";
			startDate = year + "-" + month + "-21";
			endDate = endyear + "-" + endmonth + "-20";
		}
		else if(month!=null){//
			int tempMonth =Integer.parseInt(month)-1;
			month = tempMonth+"";
			startDate = year + "-" + month + "-21";
			endDate = endyear + "-" + endmonth + "-20";
		}
		//获得CSV出力用数据
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("departmentID", departmentID);
		params.put("branchID", branchID);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		String webadr="";
		if ("2".equals(belong)) {
			List<String> dataList = calendarManhourService.getCxeeCsvData(params);
			//CSV出力
			webadr=contextpath+DataToCSV.exportCsv(staff.getName(),realPathString, Parma.csvTitle, dataList);
		} else {
			List<String> dataList = calendarManhourService.getCxeeCsvData_ForCt(params);
			//CSV出力
			webadr=contextpath+DataToCSV.exportCsv_ForCt(staff.getName(),realPathString, Parma.csvTitle_ForCt, dataList);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/manhour/download");
		mv.addObject("webadr", webadr);
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	/**
	 * 获取课别列表
	 * @param department 部门名
	 * @param response
	 * @param httpSession
	 * @throws IOException
	 */
	@RequestMapping(value = "/branchselect")
	public void savebranchselect(String departmentID,HttpServletResponse response,HttpSession httpSession) throws IOException {
	   
		//List<String> branchselectList=calendarManhourService.outputbranchselect(department);
		List<Map<String,String>> branchselectList=calendarManhourService.outputbranchselect(departmentID);
		Gson gson = new Gson();
		String resultString = gson.toJson(branchselectList);
		
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	/**
	 * 获取课别列表_ForCt
	 * @param department 部门名
	 * @param response
	 * @param httpSession
	 * @throws IOException
	 */
	@RequestMapping(value = "/branchselect_ForCt")
	public void savebranchselect_ForCt(String departmentID,HttpServletResponse response,HttpSession httpSession) throws IOException {
	   
		//List<String> branchselectList=calendarManhourService.outputbranchselect(department);
		List<Map<String,String>> branchselectList=calendarManhourService.outputbranchselect_ForCt(departmentID);
		Gson gson = new Gson();
		String resultString = gson.toJson(branchselectList);
		
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
}
