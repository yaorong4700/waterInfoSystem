package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 用户登录Controller
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2012-11-09
 * @histroy:
 * 	2012-11-09 GuoRenPeng
 *  # 初版
 *  
 *  2013-02-21 GuoRenPeng
 *  # 取消验证码
 *  
 *  2013-04-09 GuoRenPeng
 *  # 界面增加邮箱地址选择
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.ContentMst;
import com.clarion.worksys.entity.PageMst;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.RequestObject;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRole;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.mapper.ContentInfoMapper;
import com.clarion.worksys.service.ContentInfoService;
import com.clarion.worksys.service.StaffService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.service.UserRoleService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;


@Controller
public class LoginController {
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ContentInfoService contentInfoService;
	@Autowired
	private ContentInfoMapper contentInfoMapper;
	
	@Autowired
	private UserRoleManageService userRoleManageService;
	/**
	 * 访问登录页
	 * 
	 * @return	
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet() {
		return "login";
	}

	/**
	 * 请求登录，验证用户
	 * 
	 * @param session     Session:保存用户登录信息等
	 * @param username    用户名:E-mail
	 * @param password    密码
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginPost(HttpSession session,
			@RequestParam String username,@RequestParam String address, @RequestParam String password,@RequestParam String type){
		ModelAndView mv = new ModelAndView();
		String errInfo = "";
		//username =username + "@" +address;
		String sTemp = "";
		/*if ("clarion.co.jp".endsWith(address)) {
			sTemp= "CT";
			
		} else {
			sTemp= "CXEE";
		}*/
		
		String checkUser = userRoleService.checkUserId(username);
		if(checkUser.equals("0")){
			errInfo = "E0003";
		}else{		
			UserRole userRole = userRoleService.getUserByNameAndPwd(username,password);
			if (userRole != null) {
				if(userRole.getUserValidFlag().equals("1")){
				session.setAttribute(Const.SESSION_USER, userRole);
				}else{
					errInfo = "E0005";
				}
			}else{
				errInfo = "E0004";
			}
		}
		/*
		if(staff != null){
			if(staff.getState()==2||staff.getState()==3){
				errInfo = "E00061";
			}
		}
		*/
		if (Tools.isEmpty(errInfo)) {
			mv.setViewName("redirect:index.do");
		} else {
			//if(errInfo.equals("验证码输入有误！")){
				
				//mv.addObject("username", username);
				//mv.addObject("password", password);
			//}
			mv.addObject("errInfo", errInfo);
			mv.setViewName("login");
		}
		return mv;
	}

	/**
	 * 进入首页后的默认页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/default")
	public ModelAndView defaultPage(HttpSession session) {		
		ModelAndView mv = new ModelAndView();
		UserRole userRole = (UserRole) session.getAttribute(Const.SESSION_USER);
		//UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"Default",userRoleManageService);
		
		List<ContentMst> lstContentMstCT = null;
		List<ContentMst> lstContentMst = null;
		String contentText = "";
		//String contentTextCT = "";
		
		try {
			lstContentMst = contentInfoService.getContentList();
			contentText = contentInfoService.getContentText();
		} catch (Exception e) {
			lstContentMst = null;
			contentText = "";
		}
		
		Gson gson = new Gson();
		String lstString = gson.toJson(lstContentMst);
		//String lstStringCT = gson.toJson(lstContentMstCT);
		//mv.addObject("AlterRoleFlag", userRole.getAlterRoleFlag());
		mv.addObject("ContentList", lstString);
	//	mv.addObject("ContentListCT",lstStringCT);
		mv.addObject("ContentText", contentText);
		//mv.addObject("ContentTextCT", contentTextCT);
		mv.setViewName("admin/default");
		return mv;
	}

	/**
	 * 访问系统首页
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpSession session, Model model) {
		UserRole userRole = (UserRole) session.getAttribute(Const.SESSION_USER);
		HashMap<String,String> userRoleManageMap = new HashMap<String,String>();
	//	List<UserRoleManage> userRoleManageList = userRoleManageService.listRoleCompetence(staff.getURKeyCode());
		/*
		if(userRoleManageList.size() > 0){
			for(UserRoleManage userRoleManage:userRoleManageList){
				userRoleManageMap.put(userRoleManage.getPageID(),userRoleManage.getShowRoleFlag());
			}
			model.addAttribute("userRoleManageMap",userRoleManageMap);
		} else {
			List<PageMst> pageList=userRoleManageService.listAllPage();
			for(PageMst pageMst:pageList) {
				userRoleManageMap.put(pageMst.getPageID(), "0");
			}
			model.addAttribute("userRoleManageMap",userRoleManageMap);
		}
		*/
		model.addAttribute("userRole", userRole);
		//model.addAttribute("staffEmail", staff.getEmail());
		return "admin/index";
	}

	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute("startDate");
		session.removeAttribute("endDate");
		return "login";
	}
	/**
	 * 进入出错页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/errorPage")
	public String errorPage() {
		return "admin/errorPage";
	}
	
	/**
	 * 保存通知
	 * @param keyCode
	 * @param type
	 * @param content
	 * @param link
	 * @param session
	 * @param response
	 * @throws IOException
	 */
	
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public void saveData(String keyCode
			            ,String type
			            ,String content
			            ,String link
			            ,String index
			            ,HttpSession session
			            ,HttpServletResponse response) throws IOException {
		
		String strMsgCode = "E0030";
		Staff staff = (Staff)session.getAttribute(Const.SESSION_USER);
		String userJobNo = staff.getJobNo();
		String rtnKeyCode = "";
		try{
			rtnKeyCode = contentInfoService.saveContent(keyCode, type, content, link, userJobNo);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			strMsgCode = "E0084";
		} catch (UncategorizedSQLException e) {
			e.printStackTrace();
			strMsgCode = "E0084";
		} catch (CannotAcquireLockException e) {
			e.printStackTrace();
			strMsgCode = "E0085";
		} catch (Exception e){
			e.printStackTrace();
			strMsgCode = "E0025";
		}
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String newContent = "";
		if ("cxee".equals(index)){
			newContent = contentInfoService.getContentText();
		} else if ("ct".equals(index)){
			newContent = contentInfoService.getCTContentText();
		}
		out.write(strMsgCode+"@@"+rtnKeyCode+"@@"+newContent);
		out.close();
	}
	
	/**
	 * 删除内容
	 * 
	 * @param
	 * @return
	 * @throws IOException 
	 */
	
	@RequestMapping(value = "/deleteContent", method = RequestMethod.POST)
	public void deleteContent(String keyCode,String type,String fileName,String index,HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
		
		String strMsgCode = "E0035";
		try{
			contentInfoService.deleteContent(keyCode);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			strMsgCode = "E0084";
		} catch (UncategorizedSQLException e) {
			e.printStackTrace();
			strMsgCode = "E0084";
		} catch (CannotAcquireLockException e) {
			e.printStackTrace();
			strMsgCode = "E0085";
		} catch (Exception e){
			e.printStackTrace();
			strMsgCode = "E0025";
		}
		
		String uploadpath;
        if("2".equals(type)){
        	uploadpath = Parma.COMMON_FILE_PATH;
        } else {
        	uploadpath = Parma.REPORT_FILE_PATH;
        }
        File file = new File(uploadpath +"\\"+ fileName);
        if (file.exists()){
        	file.delete();
        }
        
        //文件列表的再取得
        List<ContentMst> lstContentMst = null;
        if ("cxee".equals(index)){
        	lstContentMst = contentInfoService.getContentList();
        } else if ("ct".equals(index)){
        	lstContentMst = contentInfoService.getCTContentList();
        }
		Gson gson = new Gson();
		String lstString = gson.toJson(lstContentMst);
		String result ="{\"result\":\""+strMsgCode+"\",\"contentlst\":"+lstString+"}";
        
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	
	
	@RequestMapping(value = "/docsUpload",method=RequestMethod.POST)
	public void docsUpload(@RequestParam("file") CommonsMultipartFile[] files, String type,String index,HttpServletRequest request,HttpServletResponse response) throws ParseException {
		Staff staffInfo = (Staff) request.getSession().getAttribute(Const.SESSION_USER);
		try {            
            String uploadpath;
            if("2".equals(type)){
            	uploadpath = Parma.COMMON_FILE_PATH;
            } else {
            	uploadpath = Parma.REPORT_FILE_PATH;
            }
            for (int i=0; i < files.length ; i++){
            	if(!files[i].isEmpty() && !"".equals(files[i].getOriginalFilename().trim())){
            		FileOutputStream fos = new FileOutputStream(uploadpath +"\\"+ files[i].getOriginalFilename());
            		InputStream is = files[i].getInputStream();
            		byte[] buffer = new byte[8192]; // 每次读8K字节  
                    int count = 0;  
                    // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中  
                    while ((count = is.read(buffer)) > 0) {  
                        fos.write(buffer, 0, count);
                    }  
                    fos.close();
                    is.close(); 
            	
	            	//更新DB数据
	            	Map<String, Object>  params = new HashMap<String, Object>();
	        		params.put("type", type);
	        		params.put("content", files[i].getOriginalFilename());
	        		params.put("link", files[i].getOriginalFilename());
	        		params.put("jobNo", staffInfo.getJobNo());
	    			if (contentInfoMapper.checkContentText(params) == 0){
	    				Map<String, Object>  paramsgetkeyCode = new HashMap<String, Object>();
	        			paramsgetkeyCode.put("pjName", "CI");
	        			paramsgetkeyCode.put("insertUser", staffInfo.getJobNo());
	    				String keyCode = userRoleManageService.getkeyCode(paramsgetkeyCode);
	    				params.put("keyCode", keyCode);
	    				contentInfoMapper.insContent(params);
	    			}
            	}
            }
            
            //文件列表的再取得
            List<ContentMst> lstContentMst = null;
            if ("cxee".equals(index)){
            	lstContentMst = contentInfoService.getContentList();
            } else if ("ct".equals(index)){
            	lstContentMst = contentInfoService.getCTContentList();
            }
    		Gson gson = new Gson();
    		String lstString = gson.toJson(lstContentMst);
    		
            response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write(lstString);
			out.close();            
		} catch (IOException e){
			e.printStackTrace();  
            PrintWriter out;
			try {
				out = response.getWriter();
				out.write("ERROR");
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@RequestMapping(value = "/downSmbLoad", method = RequestMethod.POST)
	protected void downSmbLoad( HttpServletResponse response,String path)throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter responseout = response.getWriter();
		try {
			URL url = new URL(path);  
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
			uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
			uc.connect();
			if (HttpURLConnection.HTTP_OK != uc.getResponseCode()){
				responseout.write("E0089");
	    		responseout.close();
	    		return;
			}
            responseout.write(path);
            responseout.close();
		} catch (Exception e) {  
            e.printStackTrace();
            responseout.write("E0089");
            responseout.close();
		} finally {
            try {  
                responseout.write("");
                responseout.close();
            } catch (Exception e) {  
                e.printStackTrace();
                responseout.write("");
                responseout.close();
            }  
       }
    } 
    
	
	@RequestMapping(value = "/downLoadExcel", method = RequestMethod.GET)
	public ModelAndView downLoadExcel(String fileName,String type,RequestObject ro, Model model,HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileName", fileName);
		mv.addObject("type", type);
		mv.setViewName("admin/defaultDownLoad");
		return mv;
	}
	@RequestMapping(value = "/downLoad", method = RequestMethod.GET)
	protected void downloadLocal( HttpServletResponse response,String path,String type)	throws ServletException, IOException {
    	try {
    		    		
    		URL url = new URL(path);  
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
			uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
			uc.connect();
			InputStream fis = new BufferedInputStream(uc.getInputStream());           

            // 清空response
            response.reset();
            
            path= java.net.URLDecoder.decode(java.net.URLDecoder.decode(path,"utf-8"),"utf-8"); 
            String name=path.substring(path.lastIndexOf("/")+1,path.length());
            name = java.net.URLEncoder.encode(name, "UTF-8").replaceAll("[+]", "%20");   
            response.addHeader("Content-Disposition", "attachment;filename=" +name);

            response.setCharacterEncoding("utf-8"); 
            response.addHeader("Content-Length", "" + uc.getContentLength());
            response.setContentType("application/octet-stream");
            BufferedOutputStream toClient= new BufferedOutputStream(response.getOutputStream());
            int c;
            while( (c = fis.read()) != -1 ) {
            	toClient.write(c);
            }
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
