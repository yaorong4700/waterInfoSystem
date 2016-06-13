package com.clarion.worksys.filter;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 登录验证filter,防止未登录用户通过url进入系统
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-30
 * @histroy:
 * 	2012-11-30 GuoRenPeng
 *  # 初版
 *   
 */
import java.io.IOException;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRole;
import com.clarion.worksys.util.Const;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;


public class SecurityFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		String sUrl=req.getRequestURI();
		
		if (sUrl.endsWith("login.do")){
			//登录界面无需验证
			UserRole userRole = (UserRole)session.getAttribute(Const.SESSION_USER);
			if (userRole != null) {
				//已经登录过的直接跳转
				res.sendRedirect(req.getContextPath()+"/index.do");
			} else {
				//未登录的跳回登录界面
				chain.doFilter(request, response);
			}
		}else if(sUrl.endsWith("code.do")){
			//验证码生成界面无需验证
			chain.doFilter(request, response);
		}else{
			
			UserRole userRole = (UserRole)session.getAttribute(Const.SESSION_USER);
			if (userRole != null) {
				//已经登录过的直接跳转
				chain.doFilter(request, response);
			} else {
				//未登录的跳回登录界面
				res.sendRedirect(req.getContextPath()+"/index.jsp");
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
