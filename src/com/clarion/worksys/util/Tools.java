package com.clarion.worksys.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.service.UserRoleManageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Tools {
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**  
	 * 将json转换成bean对象  
     * @param jsonStr  
     * @return  
     */ 
	public static Object  jsonToBean(String jsonStr,Class<?> cl){  
    	
    	Gson gson = new GsonBuilder()
    	  .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    	  .create();  

        Object obj=null;  
        if(gson!=null){  
            obj=gson.fromJson(jsonStr, cl);  
        }  
        return obj;  
    }
	/**
     * 权限取得
     * @param roleKeyCode
     * @param pageId
     * @return
     */
	public static UserRoleManage getRoleCompetenceByKeyCodePageId(String roleKeyCode,String pageId,UserRoleManageService userRoleManageService){
		try{
			UserRoleManage userRoleManage = new UserRoleManage();
			if(roleKeyCode.isEmpty()) {
				userRoleManage = null;
			} else {
				List<UserRoleManage> userRoleListByKeyCode=userRoleManageService.listRoleCompetence(roleKeyCode);
				for(UserRoleManage userRoleListByPageId:userRoleListByKeyCode){
					if(pageId.equals(userRoleListByPageId.getPageID())){
						userRoleManage=userRoleListByPageId;
					}
				}
			}
			return userRoleManage;
		}catch (Exception e) {
			return null;
		}
		
	}
	/**
	 * 文字内容取得
	 * @param email 
	 * @param stringID
	 * @throws IOException 
	 */
	public static String getPropertiesValue(HttpSession session,String stringID) throws IOException{
		
		Properties pro = new Properties();
		InputStreamReader isr;
		String email = ((Staff) session.getAttribute(Const.SESSION_USER)).getEmail();
		if (email.endsWith("co.jp")){
			InputStream path = session.getServletContext().getResourceAsStream("/resource/i18n/strings_JP.properties");
			isr = new InputStreamReader(path,"UTF-8");	
		} else {
			InputStream path = session.getServletContext().getResourceAsStream("/resource/i18n/strings_CN.properties");
			isr = new InputStreamReader(path,"UTF-8");
		}
		pro.load(isr);
		return pro.getProperty(stringID);
	}
}
