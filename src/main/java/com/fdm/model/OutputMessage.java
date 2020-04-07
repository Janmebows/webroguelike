package com.fdm.model;

public class OutputMessage {

	String sender;
	String text;
	String time;

	public OutputMessage(String sender, String text, String time) {
		super();
		this.sender = sender;
		this.text = text;
		this.time = time;
	}
	
	public OutputMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
