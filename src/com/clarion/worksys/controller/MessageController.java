package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 获得消息Controller
 * 
 * @author guo_renpeng@clarion.com.cn
 * @create: 2013-1-24
 * @histroy:
 * 	2013-1-24 GuoRenPeng
 *  初版
 *  2013-3-1 chenweijun
 *  第二版
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.Message;
import com.clarion.worksys.entity.RequestObject;
import com.clarion.worksys.httpentity.MessageReqParam;
import com.clarion.worksys.httpentity.MessageResponse;
import com.clarion.worksys.httpentity.MessageResponseRows;
import com.clarion.worksys.service.MessageService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@RequestMapping
	public String staffList(RequestObject ro) {
		return "admin/message/message";
	}
	
	@RequestMapping(value = "/getMessage")
	public void getMessage(HttpServletResponse response) throws IOException {
		System.out.println("MessageControllerMessageControllerMessageController");
		List<Message> messagesList = messageService.getMessage();
		Gson gson = new Gson();
		String resultString = gson.toJson(messagesList);
		String result ="{\"result\":"+resultString+"}";
		System.out.println(result);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	@RequestMapping(value = "/listMessage")
	public void listStaff(HttpServletResponse response,MessageReqParam messageReqParam) throws IOException {
        
		//设置数据库查询时从哪条数据开始
		messageReqParam.setPageSeq((messageReqParam.getPage()-1)*messageReqParam.getRp());
		//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
        if(messageReqParam.getMessage()!=null &&messageReqParam.getMessage().trim().equals("")){
			messageReqParam.setMessage(null);
		}
        if(messageReqParam.getQuery()!=null &&!messageReqParam.getQuery().trim().equals("")){
			if(messageReqParam.getQtype().trim().equals("message")){
				messageReqParam.setMessage(messageReqParam.getQuery().trim());
			}else if(messageReqParam.getQtype().trim().equals("state")){
				messageReqParam.setState(messageReqParam.getQuery().trim());
			}
		}
		List<Message> messageList = messageService.listMessage(messageReqParam);
		
		int totalPageCount = messageService.totalPageCount(messageReqParam);
		
		List<MessageResponseRows> messageRespongseList = new ArrayList<MessageResponseRows>();
		for (int i=0;i<messageList.size();i++) {
			MessageResponseRows staffResponseRows = new MessageResponseRows(i, messageList.get(i));
			messageRespongseList.add(staffResponseRows);
		}
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setPage(messageReqParam.getPage());        //设置
		messageResponse.setTotal(totalPageCount);
		messageResponse.setRows(messageRespongseList);
		Gson gson = new Gson();
		String resultString = gson.toJson(messageResponse);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(resultString);
		out.close();
	}
	/**
	 * 请求提示详细信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/more")
	public ModelAndView getMore(@RequestParam int id) {
		ModelAndView mv = new ModelAndView();
		Message message = messageService.getMessageById(id);
		mv.addObject("message", message);
		mv.setViewName("admin/message/detailed");
		return mv;
	}

	/**
	 * 请求新增提示信息页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ModelAndView toAdd() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/message/messageInfo");
		
		return mv;
	}
	/**
	 * 保存提示信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(Message message) {
		ModelAndView mv = new ModelAndView();
		if (message.getId() == null || message.getId().intValue() == 0) {
			if (messageService.insertMessage(message) == false) {
				mv.addObject("msg", "failed");
			} else {
				mv.addObject("msg", "success");
			}
		} else {
			messageService.updateStaff(message);
		}
		mv.setViewName("admin/save_result");
		return mv;
	}
	/**
	 * 删除提示信息
	 * 
	 * @param id
	 * @param out
	 */
	@RequestMapping(value = "/messageDelete")
	public void deleteStaff(@RequestParam String ids, PrintWriter out) {
		String[] allId = ids.split(",");
		messageService.deleteMessages(allId);
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	/**
	 * 请求编辑提示信息页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit(@RequestParam int id) {
		ModelAndView mv = new ModelAndView();
		Message message = messageService.getMessageById(id);
		mv.addObject("message", message);
		mv.setViewName("admin/message/messageInfo");
		return mv;
	}
}
