package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 工数管理Controller
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.service.StaffService;
import com.clarion.worksys.util.Const;


@Controller
@RequestMapping(value = "/passwordupdate")
public class PasswordUpdateController {

	@Autowired
	private StaffService staffervice;

	/**
	 * test
	 * @param model
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(Model model) {
	
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/passwordupdate");
		return mv;
	}
	@RequestMapping(value = "/passwordsend", method = RequestMethod.POST)
	public void savecalendarManhour(HttpSession session,String mypasswordnew,HttpServletResponse response) throws IOException {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		staffervice.updateStaffpassword(staff.getStaffID(),mypasswordnew);
		String msg ="{\"result\":\"success\"}";
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.close();
	}
	
	
}