package com.fdm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fdm.model.Message;
import com.fdm.model.OutputMessage;

@Controller
public class MessageController {
	
	@MessageMapping("/chat")
	@SendTo("/topic/chat")
	public OutputMessage send(Message message) throws Exception {
		  String time = new SimpleDateFormat("HH:mm").format(new Date());
		  return new OutputMessage(message.getFrom(), message.getText(), time);
	}

}
