package com.fairyland.xrobot.modular.xrobot.autoxit.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.Message;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.WaitAckRequest;

public class WaitAckServerRequest implements WaitAckRequest{
	private final static Logger log = LoggerFactory.getLogger(WaitAckServerRequest.class);
	
	private String receiverId;
	private Message message;
	
	public WaitAckServerRequest(String receiverId,Message message) {
		this.receiverId = receiverId;
		this.message = message;
		
	}
	@Override
	public String getReceiverId() {
		return receiverId;
		
	}
	@Override
	public Message getMessage() {
		return this.message;
	}
	@Override
	/**
	 * 过期未应答失效消息回调
	 */
	public void passiveExpiration() {
		log.info(this.message.getSerial());
		log.info(this.message.getBody());
		log.info("passiveExpiration...");
	}
}
