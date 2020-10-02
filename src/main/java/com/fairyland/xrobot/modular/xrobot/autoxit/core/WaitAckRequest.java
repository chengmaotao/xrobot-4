package com.fairyland.xrobot.modular.xrobot.autoxit.core;

public interface WaitAckRequest {
	/**
	 * 消息接收人
	 * @return
	 */
	public String getReceiverId();
	/**
	 * 请求消息
	 * @return
	 */
	public Message getMessage();
	/**
	 * 消息响应超时触发
	 */
	public void passiveExpiration();
	

}
