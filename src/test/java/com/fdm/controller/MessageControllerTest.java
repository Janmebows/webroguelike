package com.fdm.controller;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fdm.model.Message;
import com.fdm.model.OutputMessage;

public class MessageControllerTest {
	
	Message message;
	OutputMessage outputMessage;
	
	MessageController messageController = new MessageController();
	
	@Before
	public void init() {
		message = new Message();
		outputMessage = new OutputMessage();
	}

	@Test
	public void test_controller_turns_message_into_outputMessage() {
		message.setFrom("guy");
		message.setText("hi");
		
		outputMessage.setSender("guy");
		outputMessage.setText("hi");
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		outputMessage.setTime(time);
		OutputMessage result = new OutputMessage();
		try {
		 result = messageController.send(message);
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals(outputMessage.getSender(), result.getSender());
		assertEquals(outputMessage.getText(), result.getText());
		assertEquals(outputMessage.getTime(), result.getTime());
	}

}
