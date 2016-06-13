package com.clarion.worksys.service;

import java.util.List;

import com.clarion.worksys.entity.Message;
import com.clarion.worksys.httpentity.MessageReqParam;

/**
 * @author guo_renpeng
 *  2013-3-1 chenweijun
 *  第二版
 */
public interface MessageService {
	List<Message> getMessage();

	List<Message> listMessage(MessageReqParam messageReqParam);

	int totalPageCount(MessageReqParam messageReqParam);

	Message getMessageById(Integer id);

	boolean insertMessage(Message message);

	void updateStaff(Message message);

	void deleteMessages(String[] allId);
}
