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
 *  2013-3-1 chenweijun
 *  第二版
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.clarion.worksys.service.CalendarManhourService;

@Controller
@RequestMapping(value = "/deadline")
public class DeadlineController {

	@Autowired
	private CalendarManhourService calendarManhourService;

	/**
	 * 跳转到工数下载界面
	 * @return
	 */
	@RequestMapping
	public ModelAndView showDefault() {
		Date deadlinetime=calendarManhourService.getdeadlinetime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date3=df.format(deadlinetime);
		ModelAndView mv = new ModelAndView();
		mv.addObject("date3", date3);
		mv.setViewName("admin/manhour/deadline");
		
		return mv;
	}
	/**
	 * 设置具体的工数填写截止时间
	 * @param session
	 * @param myendtimenew
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/endtime", method = RequestMethod.POST)
	public void endtimeset(HttpSession session,String myendtimenew,HttpServletResponse response) throws IOException {
		calendarManhourService.updateEndtime(myendtimenew);
        String msg ="{\"result\":\"success\"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.close();
	}
}
