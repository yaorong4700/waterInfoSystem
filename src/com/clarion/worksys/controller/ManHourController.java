package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数填写显示Controller
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *   
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.ManhourReqParam;
import com.clarion.worksys.entity.ManhourShowData;

import com.clarion.worksys.httpentity.ManhourResponse;
import com.clarion.worksys.httpentity.ManhourResponseRows;
import com.clarion.worksys.service.ManHourService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/manhour")
public class ManHourController {

	@Autowired
	private ManHourService manHourService;

	/**
	 * 所有工数展示,已弃用 
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView showDefault() {
		List<ManhourShowData> manHourList =   manHourService.ShowData();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/manhour/listall");
		mv.addObject("manHourList", manHourList);
		return mv;
	}
	
	/**
	 * 所有工数展示,没有使用
	 * @param response
	 * @param manhourReqParam
	 * @throws IOException
	 */
	@RequestMapping(value = "/listAllManhour")
	public void listAll(HttpServletResponse response,ManhourReqParam manhourReqParam) throws IOException {
        
		//设置数据库查询时从哪条数据开始
		manhourReqParam.setPageSeq((manhourReqParam.getPage()-1)*manhourReqParam.getRp());
		//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(manhourReqParam.getStaffName()!=null &&manhourReqParam.getStaffName().trim().equals("")){
			manhourReqParam.setStaffName(null);
		}
        if(manhourReqParam.getQuery()!=null &&!manhourReqParam.getQuery().trim().equals("")){
			if(manhourReqParam.getQtype().trim().equals("name")){
				manhourReqParam.setStaffName(manhourReqParam.getQuery().trim());
			}else if(manhourReqParam.getQtype().trim().equals("category")){
				manhourReqParam.setCategory(manhourReqParam.getQuery().trim());
			}else if(manhourReqParam.getQtype().trim().equals("projectName")){
				manhourReqParam.setProjectName(manhourReqParam.getQuery().trim());
			}
		}
		List<ManhourShowData> manhourList = manHourService.ShowAllData(manhourReqParam);
		for (ManhourShowData manhourShowData : manhourList) {
			System.out.println(manhourShowData.getInsertTime()+"manhourShowData.getInsertTime()");
		}
		int totalPageCount = manHourService.totalPageCount(manhourReqParam);
		
		List<ManhourResponseRows> manhourRespongseList = new ArrayList<ManhourResponseRows>();
		for (int i=0;i<manhourList.size();i++) {
			ManhourResponseRows manhourResponseRows = new ManhourResponseRows(i, manhourList.get(i));
			manhourRespongseList.add(manhourResponseRows);
		}
		ManhourResponse manhourResponse = new ManhourResponse();
		manhourResponse.setPage(manhourReqParam.getPage());        //设置
		manhourResponse.setTotal(totalPageCount);
		manhourResponse.setRows(manhourRespongseList);
		Gson gson = new Gson();
		String resultString = gson.toJson(manhourResponse);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(resultString);
		out.close();
	}

	
}
