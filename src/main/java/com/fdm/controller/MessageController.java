package com.fdm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.model.Message;
import com.fdm.model.OutputMessage;

/**
 * @author Kristen
 *
 */
@RestController
public class MessageController {
	protected transient static Logger logger = Logger.getLogger("MessageLogger");

	/**
	 * @param message: Received message from front-end
	 * @return OutputMessage
	 * @throws Exception
	 */
	@MessageMapping("/chat")
	@SendTo("/topic/chat")
	public OutputMessage send(Message message) throws Exception {
		  String time = new SimpleDateFormat("HH:mm").format(new Date());
		  logger.info("Outgoing message "+ message.getFrom() + ", " + message.getText() + ", " + time);
		  return new OutputMessage(message.getFrom(), message.getText(), time);
	}

}
