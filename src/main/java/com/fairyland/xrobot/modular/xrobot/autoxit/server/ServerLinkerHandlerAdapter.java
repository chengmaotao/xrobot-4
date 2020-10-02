package com.fairyland.xrobot.modular.xrobot.autoxit.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerLinkerHandlerAdapter extends ChannelInboundHandlerAdapter {
	private final static Logger log = LoggerFactory.getLogger(ServerLinkerHandlerAdapter.class);

	@Autowired
	private LinkerServer server;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		server.channelRead(ctx, msg);
		/*
		 * ByteBuf buf = (ByteBuf) msg; int length = buf.readInt(); int msgType =
		 * buf.readInt(); int command = buf.readInt(); byte[] serial = new byte[36];
		 * buf.readBytes(serial); String messageSerial = new String(serial);
		 * 
		 * byte[] body = new byte[length-8-36]; buf.readBytes(body); String bodyString =
		 * new String(body); System.out.println("总消息数:" + ++count);
		 * System.out.println("接收到消息:" + length + "," + msgType + "," + command + "," +
		 * messageSerial + "," + bodyString);
		 */
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
		// 读写超时断开连接（客户端通过发送心跳包来保持连接）
		try {
			if (o instanceof IdleStateEvent && ((IdleStateEvent) o).state().equals(IdleState.ALL_IDLE)) {
				log.info("服务器端读写空闲超时，连接即将关闭!");
				ctx.channel().close();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//ctx.channel().attr(Options.SESSION_LASTREADTIME).set(System.currentTimeMillis());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelInactive...");
		server.closeChannel(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		Channel channel = ctx.channel();
//		if (channel.isActive()) {
//			ctx.close();
//		}
		log.info("exceptionCaught...");
		server.closeChannel(ctx.channel());
	}

}
