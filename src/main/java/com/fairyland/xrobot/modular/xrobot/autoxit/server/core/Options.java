package com.fairyland.xrobot.modular.xrobot.autoxit.server.core;

import io.netty.util.AttributeKey;

public class Options {
		public static final int SUCCESS = 1;
		public static final int FAIL = 0;
		
		public static final int MAX_AUTH_IDLE = 10;
		
		
	public static interface IdleStateHandler {
		public static final int READ_IDLE_TIME = 10;//读空闲时，超过该值时触发userEventTriggered IdleState.READER_IDLE
		public static final int WRITE_IDLE_TIME = 10;
		public static final int ALL_IDLE_TIME = 10;
	}
	
	public static final int MAX_IDLE = 10;
	/**
	 * 心跳包响应超时
	 */
	public static final int HEARTRESPONSE_TIMEOUT = 15;//秒
	
		
		
	public static final int RECONNECT_WAIT_TIME = 2;//秒
		
	public static interface Packet {
		public static final int MAX_MESSAGE_LENGTH = 1024*1024;//数据报文最大长度
	}
	
	public static final int SO_BACKLOG = 128;
	
	public static interface WriteBuffer {
		public static final int LOW_WATER_MARK = 32 * 1024;
		public static final int HIGH_WATER_MARK = 64 * 1024;
	}
	
	public static interface WriteAndFlush {
		public static final int WAIT_TIMEOUT = 10000;
	}
	
	
	
	
	public static final AttributeKey<String> SESSION_ID = AttributeKey.valueOf("sessionId");
	public static final AttributeKey<String> CLIENT_ID = AttributeKey.valueOf("clientId");
	public static final AttributeKey<Long> SESSION_LASTREADTIME = AttributeKey.valueOf("lastReadTime");
	
	
}
