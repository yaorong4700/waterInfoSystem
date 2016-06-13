package com.clarion.worksys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.Message;
import com.clarion.worksys.httpentity.MessageReqParam;
import com.clarion.worksys.mapper.MessageMapper;
import com.clarion.worksys.service.MessageService;

/**
 * @author guo_renpeng
 *
 */
@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageMapper messageMapper;
	
	public List<Message> getMessage() {
		// TODO Auto-generated method stub
		
		return messageMapper.getMessage();
		
	}
	public List<Message> listMessage(MessageReqParam messageReqParam){
		return messageMapper.listMessages(messageReqParam); 
	}

	public int totalPageCount(MessageReqParam messageReqParam){
		return messageMapper.totalPageCount(messageReqParam);
	}
	public Message getMessageById(Integer id) {
		return messageMapper.getMessageById(id);
	}
	public boolean insertMessage(Message message) {
		int count = messageMapper.insertMessage(message);
		if(count == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void updateStaff(Message message){
		messageMapper.updateMessage(message);
	}
	public void deleteMessages(String[] ids){
		messageMapper.deleteMessages(ids);
	}
}
