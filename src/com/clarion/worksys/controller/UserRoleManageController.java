package com.clarion.worksys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.PageMst;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/UserRoleManage")
public class UserRoleManageController {
	@Autowired
	private UserRoleManageService userRoleManageService;

	/**
	 * 跳转到人员权限管理界面
	 * @return
	 */
	@RequestMapping
	public ModelAndView showDefault(HttpSession session,HttpServletResponse response)throws IOException {
		Staff staff = (Staff)session.getAttribute(Const.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		//选择角色名下拉框
		List<UserRoleManage> roleList = userRoleManageService.listAllRole();
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"UserRoleManage",userRoleManageService);
		if(userRole != null){
			mv.addObject("QueryRoleFlag", userRole.getQueryRoleFlag());
			mv.addObject("AlterRoleFlag", userRole.getAlterRoleFlag());
			mv.addObject("SpecialRole1Flag", userRole.getSpecialRole1Flag());//CT
			mv.addObject("SpecialRole2Flag", userRole.getSpecialRole2Flag());//CXEE
		} else {
			mv.addObject("QueryRoleFlag", "0");
			mv.addObject("AlterRoleFlag", "0");
			mv.addObject("SpecialRole1Flag", "0");//CT
			mv.addObject("SpecialRole2Flag", "0");//CXEE
		}
		mv.addObject("roleList", roleList);
		mv.addObject("staff", staff);
		mv.setViewName("admin/userRole/UserRoleManage");
		return mv;
	}
	
	/**
	 * 页面初始化
	 * @param response
	 * @param httpSession
	 * @throws IOException
	 */
	@RequestMapping(value = "/init")
	public void showInit(HttpServletResponse response,HttpSession httpSession) throws IOException{

		List<PageMst> pageList=userRoleManageService.listAllPage();
		
		Gson gson = new Gson();
		String resultString = gson.toJson(pageList);
		
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	/**
	 * 检索
	 * @param roleName
	 * @param response
	 * @param httpSession
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchClick")
	public void searchButtonClick(String keyCode,HttpServletResponse response,HttpSession httpSession) throws IOException{
		
		List<UserRoleManage> searchList=userRoleManageService.listRoleCompetence(keyCode);
		for(int i=0 ;i < searchList.size(); i++) {
			searchList.get(i).setNo(String.valueOf(i));
		}
		
		Gson gson = new Gson();
		String resultString = gson.toJson(searchList);
		
		String result ="{\"result\":"+resultString+"}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	
	/**
	 * 删除权限
	 * @param keyCode
	 * @param roleName
	 * @param session
	 * @param response
	 * @throws IOException
	 */
	 @RequestMapping(value = "/deleteClick", method = RequestMethod.POST)
	 public void deleteButton(String keyCode,String roleName,HttpSession session, HttpServletResponse response) throws IOException{
		String rtnStr = "E0035";
		Staff staff = (Staff)session.getAttribute(Const.SESSION_USER);
		UserRoleManage userRoleManage = new UserRoleManage();
		userRoleManage.setUpdateUser(staff.getJobNo());
		userRoleManage.setKeyCode(keyCode);
		userRoleManage.setRoleName(roleName);
		if(!userRoleManageService.checkStaff(userRoleManage.getKeyCode())){
			rtnStr = "E0100";//删除失败！该权限正在使用中，请调整相关人员权限！
		}else{
		
			try{
				userRoleManageService.deleteUserRoleManage(userRoleManage);
			} catch (CannotAcquireLockException e) {
				e.printStackTrace();
				rtnStr = "E0054:" + userRoleManage.getKeyCode();
			} catch (Exception e){
				e.printStackTrace();
				rtnStr = "E0036";
			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(rtnStr);
		out.close();
	 }
	
	 /**
	  *   保存
	  * @param jsonStr
	  * @param session
	  * @param response
	  * @throws IOException
	  */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveButton(String jsonStr,String hasSearch,HttpSession session, HttpServletResponse response) throws IOException{  
    	String rtnStr = "";
		Staff staff = (Staff)session.getAttribute(Const.SESSION_USER);
		Locale.setDefault(Locale.CHINA); 
		UserRoleManage userRoleManageold = new UserRoleManage();
		String[] jsonLst = jsonStr.split("@@");
		try{
			rtnStr = userRoleManageService.updateUserRole(jsonLst,hasSearch,staff,userRoleManageold);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			rtnStr = "E0084:" + userRoleManageold.getNo();
		} catch (UncategorizedSQLException e) {
			e.printStackTrace();
			rtnStr = "E0084:" + userRoleManageold.getNo();
		} catch (CannotAcquireLockException e) {
			e.printStackTrace();
			rtnStr = "E0085:" + userRoleManageold.getNo();
		}catch(Exception e){
			e.printStackTrace();
			rtnStr = "E0025";
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(rtnStr);
		out.close();
    	
    }
}
