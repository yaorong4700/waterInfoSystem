package com.clarion.worksys.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.clarion.worksys.entity.Staff;


/**
 * 将JavaBean List转换成相应的String数据List
 * 
 * @author guo_renpeng
 * 
 */
public class TransDate {

	/**
	 * JavaBean List转换成相应的String数组List 将
	 * 
	 * @param title
	 *            javaBean List中所需要取出的字段,与Bean中的成员变量名要一样
	 * @param list
	 *            javaBean List
	 * @return dateList
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public List<String[]> getDateList(String[] title, List<Object> list)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		List<String[]> dateList = new ArrayList<String[]>();
		for (Object object : list) {
			Class c = object.getClass();
			// 得到对象中所有的方法
			Method[] methods = c.getMethods();
			String[] date = new String[title.length];
			for (Method method : methods) {
				String mName = method.getName();
				if (mName.startsWith("get") && !mName.startsWith("getClass")) {
					String fieldName = mName.substring(3, mName.length()).toLowerCase();
					for (int i = 0; i < title.length; i++) {
						if (fieldName.equals(title[i].toLowerCase())) {
							if (method.invoke(object, null) != null) {
								date[i] = method.invoke(object, null)
										.toString();
							}else{
								date[i] = "";
							}
						}
					}
				}
			}
			dateList.add(date);
		}
		
		return dateList;

	}
	
	public  List<String[]> getDateList(List list)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		List<String[]> dateList = new ArrayList<String[]>();
		
		//javabean list如果为空直接返回
		if(list.size()==0){
			return dateList;
		}
		Object titleObject = list.get(0);
		Class c = titleObject.getClass();
		Field[] fieldList = c.getDeclaredFields();
		int arrLength = fieldList.length;
		String[] title = new String[arrLength];
		for (int i=0;i<fieldList.length;i++) {
			title[i] = fieldList[i].getName();
		}
		dateList.add(title);
		
		for (Object object : list) {
			Class c1 = object.getClass();
			// 得到对象中所有的方法
			Method[] methods = c1.getMethods();
			String[] date = new String[arrLength];
			for (Method method : methods) {
				String mName = method.getName();
				if (mName.startsWith("get") && !mName.startsWith("getClass")) {
					String fieldName = mName.substring(3, mName.length()).toLowerCase();
					for (int i = 0; i < arrLength; i++) {
						if (fieldName.equals(title[i].toLowerCase())) {
							if (method.invoke(object, null) != null) {
								date[i] = method.invoke(object, null)
										.toString();
							}else{
								date[i] = "";
							}
						}
					}
				}
			}
			dateList.add(date);
		}
		return dateList;
		
	}
	

	 
}
