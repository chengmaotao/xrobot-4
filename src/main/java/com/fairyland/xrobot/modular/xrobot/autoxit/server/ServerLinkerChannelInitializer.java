package com.fairyland.xrobot.modular.xrobot.autoxit.server;

import java.util.concurrent.TimeUnit;

import com.fairyland.xrobot.modular.xrobot.autoxit.server.core.CustomByteToMessageDecoder;
import com.fairyland.xrobot.modular.xrobot.autoxit.server.core.Options;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerLinkerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		/**
		 * 服务器端两倍于客户端的读写空闲，说明超时未收到服务器心跳数据
		 */
		pipeline.addLast(new IdleStateHandler(2 * Options.IdleStateHandler.READ_IDLE_TIME,
				2 * Options.IdleStateHandler.WRITE_IDLE_TIME, 2 * Options.IdleStateHandler.ALL_IDLE_TIME,
				TimeUnit.SECONDS));//// 读写超时
		
		pipeline.addLast(new CustomByteToMessageDecoder());
		pipeline.addLast(new ServerLinkerHandlerAdapter());
	}
}
