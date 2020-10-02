package com.fairyland.xrobot.modular.xrobot.autoxit.server.core;

public class Message implements Cloneable {
	private int msgType = 0;
	private int command = 0;
	private String serial = "";
	private String body = "";

	public Message(int msgType, int command, String serial, String body) {
		this.msgType = msgType;
		this.command = command;
		this.serial = serial;
		this.body = body;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;

	}

	public int getMsgType() {
		return this.msgType;

	}

	public void setCommand(int command) {
		this.command = command;

	}

	public int getCommand() {
		return this.command;

	}

	public void setSerial(String serial) {
		this.serial = serial;

	}

	public String getSerial() {
		return this.serial;

	}

	public void setBody(String body) {
		this.body = body;

	}

	public String getBody() {
		return this.body;

	}

	@Override
	protected Message clone() throws CloneNotSupportedException {
		return (Message) super.clone();
	}

}
