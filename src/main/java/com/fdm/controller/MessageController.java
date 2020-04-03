package com.fdm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.model.Message;
import com.fdm.model.OutputMessage;

@RestController
public class MessageController {
	
	@MessageMapping("/chat")
	@SendTo("/topic/chat")
	public OutputMessage send(Message message) throws Exception {
		System.out.println("hi");
		  String time = new SimpleDateFormat("HH:mm").format(new Date());
		  return new OutputMessage(message.getFrom(), message.getText(), time);
	}

}
