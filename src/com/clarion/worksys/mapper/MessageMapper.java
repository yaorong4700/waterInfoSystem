/**
 * 
 */
package com.clarion.worksys.mapper;

import com.clarion.worksys.entity.*;
import com.clarion.worksys.httpentity.MessageReqParam;

import java.util.List;
/**
 * @author guo_renpeng
 *
 */
public interface MessageMapper {
	
	List<Message> getMessage();

	List<Message> listMessages(MessageReqParam messageReqParam);

	int totalPageCount(MessageReqParam messageReqParam);

	Message getMessageById(int id);

	int insertMessage(Message message);

	void updateMessage(Message message);

	void deleteMessages(String[] ids);

}
