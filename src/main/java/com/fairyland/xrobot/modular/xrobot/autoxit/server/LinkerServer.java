package com.fairyland.xrobot.modular.xrobot.autoxit.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.*;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.AutoxitService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class LinkerServer {
    private final static Logger log = LoggerFactory.getLogger(LinkerServer.class);

    public static final int SERVER_PORT = 8899;
    private SessionManager sessionManager = SessionManager.getInstance();
    private ResettableCountDownLatch startedLatch = new ResettableCountDownLatch(1);


    // 30分钟客户端没回的指令，服务器认为已经失效
    Map<String, WaitAckRequest> waitAckRequestCache = ExpiringMap.builder().expiration(1800, TimeUnit.SECONDS)
            .expirationPolicy(ExpirationPolicy.CREATED)
            .expirationListener((key, waitAckRequest) -> ((WaitAckRequest) waitAckRequest).passiveExpiration()).build();


    @Autowired
    private AutoxitService autoxitService;

    @Autowired
    private ServerLinkerChannelInitializer serverLinkerChannelInitializer;

    public void start() {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            log.info("LinkerServer 正在启动...");
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(serverLinkerChannelInitializer);

            serverBootstrap.option(ChannelOption.SO_BACKLOG, Options.SO_BACKLOG);
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
                    new WriteBufferWaterMark(Options.WriteBuffer.LOW_WATER_MARK, Options.WriteBuffer.HIGH_WATER_MARK));

            ChannelFuture channelFuture = serverBootstrap.bind(SERVER_PORT).sync();
            // Channel channel = channelFuture.channel();
            channelFuture.await();
            if (channelFuture.isSuccess()) {
                startedLatch.countDown();
                log.info("LinkerServer 启动成功！");
            }
            /**
             * channelFuture.addListener(new ChannelFutureListener() { public void
             * operationComplete(ChannelFuture future) throws Exception { if
             * (future.isSuccess()) { System.out.println("RobotServer 启动成功！");
             * //log.info("LinkerServer 启动成功！ 监听端口:{}", SERVER_PORT); } else {
             * System.out.println("RobotServer 启动失败！"); //log.error("LinkerServer 启动失败!"); }
             * } });
             */
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.info("LinkerServer 启动失败！{}", e.getMessage());
        } finally {
            try {
                parentGroup.shutdownGracefully();
                childGroup.shutdownGracefully();
            } catch (Exception e) {
                log.error("LinkerServer:{}", e.getMessage());
            }
            log.info("LinkerServer 已退出！");
        }
    }

    public void waitStarted() {
        try {
            startedLatch.await();
        } catch (InterruptedException e) {
            log.error("waitStarted:{}", e.getMessage());
        }
    }

    public boolean responseMessage(ChannelHandlerContext ctx, ByteBuf buffer) {
        boolean success = false;
        try {
            if (ctx != null && ctx.channel().isActive() && ctx.channel().isWritable()) {
                ChannelFuture future = ctx.channel().writeAndFlush(buffer);
                future.awaitUninterruptibly(Options.WriteAndFlush.WAIT_TIMEOUT);
                if (future.isSuccess()) {
                    success = true;
                    // responseCount++;
                    // System.out.println("responseCount:"+responseCount);
                }
            }
        } catch (Exception e) {
            log.error("responseMessage:{}", e.getMessage());
        }
        return success;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        int length = buf.readInt();
        int msgType = buf.readInt();
        int command = buf.readInt();

        byte[] serial = new byte[36];
        buf.readBytes(serial);
        String messageSerial = new String(serial);

        byte[] body = new byte[length - 8 - 36];
        buf.readBytes(body);
        String bodyString = new String(body);

        ReferenceCountUtil.release(buf);

        log.info("接收到消息:{}-{}-{}-{}-{}", length, msgType, command, messageSerial, bodyString);
        if (msgType == MessagePacket.REQ_MESSAGE) {
            receiveReqMessages(ctx, command, messageSerial, bodyString);
        } else {
            receiveRespMessages(ctx, command, messageSerial, bodyString);
        }
        // 设置最后会话时间
        //ctx.channel().attr(Options.SESSION_LASTREADTIME).set(System.currentTimeMillis());
    }

    public void receiveRespMessages(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {
        WaitAckServerRequest waitAck = (WaitAckServerRequest) waitAckRequestCache.get(messageSerial);
        if (waitAck != null) {
			/*
			if (command == MessagePacket.SERVER_XXX_COMMAND) {
				log.info("XXX指令执行结果:{}", bodyString);

			} */
            /**
             * reqbodyString请求数据，bodyString响应数据
             */
            String reqbodyString = waitAck.getMessage().getBody();
            /**
             *
             *
             *
             * 写数据库
             */

            log.info("serial:{}-{}-{}", messageSerial, reqbodyString, bodyString);
            waitAckRequestCache.remove(messageSerial);
        } else {
            log.error("receiveRespMessages：消息已失效！");
        }
        // }
    }

    public void receiveReqMessages(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {
        if (command == MessagePacket.CLIENT_HEART_MESSAG) {
            /**
             * 心跳包响应
             */
            MessagePacket messagePacket = new MessagePacket();
            ByteBuf buffer = messagePacket.getRespPacket(MessagePacket.CLIENT_HEART_MESSAG, messageSerial, "{}");
            responseMessage(ctx, buffer);
        } else if (command == MessagePacket.CLIENT_LOGIN_COMMAND) {
            /**
             * 登录
             */
            clientLogin(ctx, command, messageSerial, bodyString);
        } else {
            if (channelIsAuth(ctx.channel())) {// 认证检查
                if (command == MessagePacket.CLIENT_REPORTSTATUS_COMMAND) {
                    /**
                     * 状态报告
                     */
                    clientReportStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_GETTASK_COMMAND) {
                    // 领取任务
                    clientGetTaskStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_CHECKPUSHMESSAGE_COMMAND) {
                    // 检查用户是否可以发送消息
                    clientCheckPushMessageStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_SUBMIT_PUSHJOINGROUPS_COMMAND) {
                    // 上报发消息加群结果
                    clientSubmitPushJoinGroupsStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_SUBMIT_PUSHMESSAGES_COMMAND) {
                    // 上报发消息结果
                    clientSubmitPushMessagesStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_SUBMIT_COMMENTJOINGROUPS_COMMAND) {
                    // 上报评论加群结果
                    clientSubmitCommentJoinGroupsStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_SUBMIT_COMMENTS_COMMAND) {
                    // 上报评论结果
                    clientSubmitCommentsStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_SUBMIT_CREATEGROUPS_COMMAND) {
                    // 上报建群发贴结果
                    clientSubmitCreateGroupsStatus(ctx, command, messageSerial, bodyString);
                } else if (command == MessagePacket.CLIENT_SUBMIT_TASKRESPONSE_COMMAND) {
                    // 任务执行结果报告
                    clientSubmitTaskResponseStatus(ctx, command, messageSerial, bodyString);
                } else {
                    log.info("未知command = {},bodyString = {}", command, bodyString);
                }

				/*其他请求（待补充）
				else if (command == MessagePacket.CLIENT_XXX_COMMAND) {

					
				
				
				
				
				
				
				} */
            } else {
                log.warn("来自IP:{} 终端请求未认证！", ctx.channel().remoteAddress());
            }
        }
    }

    // 任务执行结果报告
    private void clientSubmitTaskResponseStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientSubmitTaskResponseStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {

            ClientSubmitTaskResponseReq businessParam = JSON.parseObject(bodyString, ClientSubmitTaskResponseReq.class);

            log.info("clientSubmitTaskResponseStatus req businessParam = {}", businessParam);

            String id = businessParam.getId();
            String phone = businessParam.getPhone(); //
            String taskID = businessParam.getTaskID(); //
            String taskclass = businessParam.getTaskclass(); //
            Integer batch = businessParam.getBatch(); //
            String user = businessParam.getUser();
            Integer error = businessParam.getError();


            if (StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(taskID)
                    || StringUtils.isEmpty(taskclass)
                    || StringUtils.isEmpty(user)
                    || batch == null
                    || StringUtils.isEmpty(phone)
                    || error == null
            ) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 任务执行结果报告
                autoxitService.clientSubmitTaskResponseStatus(businessParam);

                JSONObject response = getSuccessResponse("1", "处理成功！");
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientSubmitTaskResponseStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientSubmitTaskResponseStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }

    // 上报建群发贴结果
    private void clientSubmitCreateGroupsStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientSubmitCreateGroupsStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {

            ClientSubmitCreateGroupsReq businessParam = JSON.parseObject(bodyString, ClientSubmitCreateGroupsReq.class);

            log.info("clientSubmitCreateGroupsStatus req businessParam = {}", businessParam);

            String id = businessParam.getId();
            String phone = businessParam.getPhone(); //
            String taskID = businessParam.getTaskID(); //
            String taskclass = businessParam.getTaskclass(); //
            Integer batch = businessParam.getBatch(); //
            String user = businessParam.getUser();
            String groupname = businessParam.getGroupname();
            String groupID = businessParam.getGroupID();
            Integer post = businessParam.getPost();


            if (StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(taskID)
                    || StringUtils.isEmpty(taskclass)
                    || StringUtils.isEmpty(user)
                    || batch == null
                    || StringUtils.isEmpty(phone)
                    || StringUtils.isEmpty(groupID)
                    || StringUtils.isEmpty(groupname)
                    || post == null
            ) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 上报建群发贴结果
                autoxitService.clientSubmitCreateGroupsStatus(businessParam);

                JSONObject response = getSuccessResponse("1", "处理成功！");
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientSubmitCreateGroupsStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientSubmitCreateGroupsStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }


    // 上报评论结果
    private void clientSubmitCommentsStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientSubmitCommentsStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {

            ClientSubmitCommentsReq businessParam = JSON.parseObject(bodyString, ClientSubmitCommentsReq.class);

            log.info("clientSubmitCommentsStatus req businessParam = {}", businessParam);

            String id = businessParam.getId();
            String phone = businessParam.getPhone(); //
            String taskID = businessParam.getTaskID(); //
            String taskclass = businessParam.getTaskclass(); //
            Integer batch = businessParam.getBatch(); //
            String user = businessParam.getUser();
            String keyword = businessParam.getKeyword();

            String groupname = businessParam.getGroupname();
            String groupname1 = businessParam.getGroupname1();
            String poster = businessParam.getPoster();
            Integer state = businessParam.getState();


            if (StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(taskID)
                    || StringUtils.isEmpty(taskclass)
                    || StringUtils.isEmpty(user)
                    || batch == null
                    || StringUtils.isEmpty(phone)
                    || StringUtils.isEmpty(keyword)
                    || StringUtils.isEmpty(groupname)
                    || StringUtils.isEmpty(groupname1)
                    || StringUtils.isEmpty(poster)
                    || state == null
            ) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 上报评论结果
                autoxitService.clientSubmitCommentsStatus(businessParam);

                JSONObject response = getSuccessResponse("1", "处理成功！");
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientSubmitCommentsStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientSubmitCommentsStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }

    // 上报评论加群结果
    private void clientSubmitCommentJoinGroupsStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientSubmitCommentJoinGroupsStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {

            ClientSubmitCommentJoinGroupsReq businessParam = JSON.parseObject(bodyString, ClientSubmitCommentJoinGroupsReq.class);

            log.info("clientSubmitCommentJoinGroupsStatus req businessParam = {}", businessParam);

            String id = businessParam.getId();
            String phone = businessParam.getPhone(); //
            String taskID = businessParam.getTaskID(); //
            String taskclass = businessParam.getTaskclass(); //
            Integer batch = businessParam.getBatch(); //
            String user = businessParam.getUser();
            String keyword = businessParam.getKeyword();


            if (StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(taskID)
                    || StringUtils.isEmpty(taskclass)
                    || StringUtils.isEmpty(user)
                    || batch == null
                    || StringUtils.isEmpty(phone)
                    || StringUtils.isEmpty(keyword)) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 上报评论加群结果
                autoxitService.clientSubmitCommentJoinGroupsStatus(businessParam);

                JSONObject response = getSuccessResponse("1", "处理成功！");
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientSubmitCommentJoinGroupsStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientSubmitCommentJoinGroupsStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }

    // 上报发消息结果
    private void clientSubmitPushMessagesStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientSubmitPushMessagesStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {

            ClientSubmitPushMessagesReq businessParam = JSON.parseObject(bodyString, ClientSubmitPushMessagesReq.class);

            log.info("clientSubmitPushMessagesStatus req businessParam = {}", businessParam);

            String id = businessParam.getId();
            String phone = businessParam.getPhone(); //
            String taskID = businessParam.getTaskID(); //
            String taskclass = businessParam.getTaskclass(); //
            Integer batch = businessParam.getBatch(); //
            String user = businessParam.getUser();
            String keyword = businessParam.getKeyword();

            String groupname = businessParam.getGroupname();
            String groupname1 = businessParam.getGroupname1();
            String usernumber = businessParam.getUsernumber();
            Integer state = businessParam.getState();


            if (StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(taskID)
                    || StringUtils.isEmpty(taskclass)
                    || StringUtils.isEmpty(user)
                    || batch == null
                    || StringUtils.isEmpty(phone)
                    || StringUtils.isEmpty(keyword)
                    || StringUtils.isEmpty(groupname)
                    || StringUtils.isEmpty(groupname1)
                    || StringUtils.isEmpty(usernumber)
                    || state == null) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 上报发消息结果
                autoxitService.clientSubmitPushMessagesStatus(businessParam);

                JSONObject response = getSuccessResponse("1", "处理成功！");
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientSubmitPushMessagesStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientSubmitPushMessagesStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }

    // 上报发消息加群结果
    private void clientSubmitPushJoinGroupsStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientSubmitPushJoinGroupsStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {

            ClientSubmitPushJoinGroupsReq businessParam = JSON.parseObject(bodyString, ClientSubmitPushJoinGroupsReq.class);

            log.info("clientSubmitPushJoinGroupsStatus req businessParam = {}", businessParam);

            String id = businessParam.getId();

            String taskID = businessParam.getTaskID(); //
            String taskclass = businessParam.getTaskclass(); //

            Integer batch = businessParam.getBatch(); //
            String user = businessParam.getUser();

            String phone = businessParam.getPhone(); //

            String keyword = businessParam.getKeyword();

            if (StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(taskID)
                    || StringUtils.isEmpty(taskclass)
                    || StringUtils.isEmpty(user)
                    || batch == null
                    || StringUtils.isEmpty(phone)
                    || StringUtils.isEmpty(keyword)) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 上报发消息加群结果
                autoxitService.clientSubmitPushJoinGroupsStatus(businessParam);

                JSONObject response = getSuccessResponse("1", "处理成功！");
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientSubmitPushJoinGroupsStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientSubmitPushJoinGroupsStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }

    // 检查用户是否可以发送消息
    private void clientCheckPushMessageStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {


        log.info("clientCheckPushMessageStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {

            ClientCheckPushMessageReq businessParam = JSON.parseObject(bodyString, ClientCheckPushMessageReq.class);

            log.info("clientCheckPushMessageStatus req businessParam = {}", businessParam);

            String id = businessParam.getId();
            String md5 = businessParam.getMd5();
            String phone = businessParam.getPhone();
            String usernumber = businessParam.getUsernumber();

            if (StringUtils.isEmpty(id) || StringUtils.isEmpty(md5) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(usernumber)) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }


            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 检查用户是否可以发送消息
                boolean isPush = autoxitService.clientCheckPushMessageStatus(businessParam);

                JSONObject response = getSuccessResponse("1", "处理成功");
                response.put("data", isPush);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientCheckPushMessageStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientCheckPushMessageStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }

    // 领取任务
    public void clientGetTaskStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientGetTaskStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {
            JSONObject jsonObject = JSONObject.parseObject(bodyString);
            String id = (String) jsonObject.get("id");

            if (StringUtils.isEmpty(id)) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                // 领取任务
                List<ServerTaskNotifyCommandReq> list = autoxitService.clientGetTaskStatus(id);
                JSONObject response = getSuccessResponse("1", "处理成功");
                response.put("data", list);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientGetTaskStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientGetTaskStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }


    }

    /**
     * 客户端登录，登录之后ctx.channel与客户端id进行绑定
     *
     * @param ctx
     * @param command
     * @param messageSerial
     * @param bodyString
     */
    public void clientLogin(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {
        // 这些需要从数据库取出验证，一致才可以，数据库记录一下日志
        MessagePacket messagePacket = new MessagePacket();
        try {

            ClinetLoginReq businessParam = JSON.parseObject(bodyString, ClinetLoginReq.class);

            log.info("clientLogin ClinetLoginReq businessParam = {}", businessParam);

            String id = businessParam.getId();  // deviceID
            String token = businessParam.getToken();
            String phone = businessParam.getPhone();
            String account = businessParam.getAccount();
            //String password = (String) jsonObject.get("password1");
            String account1 = businessParam.getAccount1();
            //String password1 = (String) jsonObject.get("password1");
            String client = businessParam.getClient();
            String status = businessParam.getStatus();
            //String message = (String) jsonObject.get("message");


            if (StringUtils.isEmpty(id)
                    || StringUtils.isEmpty(token)
                    || StringUtils.isEmpty(phone)) {

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            // 查询数据做验证，验证id,token,phone,account,account1
            Device device = autoxitService.checkClinetLogin(businessParam);

            if (device == null) {

                log.warn("clientLogin 来自IP:{} req=:{} 终端连接被拒绝【认证失败】！", ctx.channel().remoteAddress(), businessParam);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_2, "终端连接被拒绝【认证失败】！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            // 账号被 暂停使用
            if (device.getAllow() == 0) {

                log.warn("clientLogin 来自IP:{} req=:{} 终端连接被拒绝【认证失败】！,账号状态：暂停使用", ctx.channel().remoteAddress(), businessParam);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_3, "终端连接被拒绝，账号被禁用【认证失败】！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            Session session = new Session(id, token, phone, account, account1, status, ctx.channel());
            if (authSession(session, ctx.channel(), client)) {
                log.info("客户端登录成功！");
                JSONObject response = getSuccessResponse("1", "客户端登录成功");
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);
                log.info("登录成功,终端数：{} 连接数：{}", sessionManager.getSessionCount(), sessionManager.getSessionCount());
            } else {
                log.warn("clientLogin 来自IP:{} req:{} 终端连接被拒绝，已存在相同ID的终端连接认证", ctx.channel().remoteAddress(), businessParam);
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_4, "终端连接被拒绝，已存在相同ID的终端连接认证！").toJSONString());
                responseMessage(ctx, buffer);
                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        } catch (Exception ex) {
            log.error("clientLogin 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

    }

    /**
     * 客户端登录成功后，操作状态发生变化报告
     *
     * @param ctx
     * @param command
     * @param messageSerial
     * @param bodyString
     */
    public void clientReportStatus(ChannelHandlerContext ctx, int command, String messageSerial, String bodyString) {

        log.info("clientReportStatus req = {}", bodyString);

        MessagePacket messagePacket = new MessagePacket();


        try {
            JSONObject jsonObject = JSONObject.parseObject(bodyString);
            String id = (String) jsonObject.get("id");
            String status = (String) jsonObject.get("status");

            if (StringUtils.isEmpty(id) || StringUtils.isEmpty(status)) {
                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_5, "请求必填参数不能为空").toJSONString());
                responseMessage(ctx, buffer);
                return;
            }

            Map<String, Object> map = getSignature(ctx.channel());
            String sid = (String) map.get("id");// map.get("token");
            if (id.equals(sid)) {

                //  更新数据表状态
                autoxitService.modifyDeviceStateByClientStata(id, status);

                updateStatus(ctx.channel(), status);
                JSONObject response = getSuccessResponse(ServerCode.SERVER_CODE_1, "报告状态成功");

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, response.toJSONString());
                responseMessage(ctx, buffer);

            } else {
                log.warn("clientReportStatus 请求数据错误！id = {},sid = {}", id, sid);

                ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_6, "请求数据id 和 sid不一致！").toJSONString());
                responseMessage(ctx, buffer);

                try {
                    ctx.channel().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (XRobotException ex) {
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(String.valueOf(ex.getErrorCode()), ex.getErrorMessage()).toJSONString());
            responseMessage(ctx, buffer);

            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } catch (Exception ex) {
            log.error("clientReportStatus 未知错误：");
            ex.printStackTrace();
            ByteBuf buffer = messagePacket.getRespPacket(command, messageSerial, getErrorResponse(ServerCode.SERVER_CODE_99, "信息获取失败，请重试").toJSONString());
            responseMessage(ctx, buffer);
            try {
                ctx.channel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }


    }

    public void closeChannel(Channel channel) {
        String sessionId = sessionManager.getChannelSessionId(channel);
        if (StringUtils.isNotEmpty(sessionId)) {
            if (sessionManager.getSession(sessionId) != null) {
                sessionManager.removeSession(sessionId, channel.id().asShortText());
                log.info("sessionManager.removeSession：{}", sessionId);
            }
        }
        try {
            if (channel.isActive())
                channel.close();
        } catch (Exception e) {

        }
        log.info("终端数：{} 连接数：{}", sessionManager.getSessionCount(), sessionManager.getSessionCount());
    }

    /**
     * 会话添加认证
     *
     * @param session
     * @param channel
     * @return
     */
    public boolean authSession(Session session, Channel channel, String client) {
        if (channelIsAuth(session, channel, client)) {
            return true;
        } else {
            if (sessionManager.exist(session.getId())) {
                log.info("相同SessionId的连接已存在:{}", session.getId());
                if (client != null) {
                    Session currentSession = sessionManager.getSession(session.getId());
                    SessionChannel sessionChannel = currentSession.getSessionChannelByClientId(client);
                    if (sessionChannel != null) {//同一个客户端之前的连接（客户端异常退出，服务器没超时之前依然存在其连接信息）
                        log.info("相同ClientId的连接已存在:{}", client);
                        sessionManager.removeSession(session.getId(), sessionChannel.getChannel().id().asShortText());
                    }
                }
            }
            if (sessionManager.exist(session.getId()) == false) {// 不允许同一个ID同时连接 return false;
                log.info("SessionId:{}的连接不存在，添加连接登录会话！", session.getId());
                sessionManager.setChannelSessionId(channel, session.getId());// 标记客户端ID，认证（未被标记channel超时后会被服务器断开）
                sessionManager.setChannelClientId(channel, client);
                sessionManager.addSession(session);
                // sessionManager.updateSession(session);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 终端连接是否已认证
     *
     * @param channel
     * @return
     */
    public boolean channelIsAuth(Session session, Channel channel, String client) {
        String sessionId = sessionManager.getChannelSessionId(channel);
        if (StringUtils.isNotBlank(sessionId)) {
            if (session != null) {
                if (!sessionId.equals(session.getId())) {
                    sessionManager.setChannelSessionId(channel, session.getId());
                    sessionManager.setChannelClientId(channel, client);
                    sessionManager.updateSession(session);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 终端连接是否已认证
     *
     * @param channel
     * @return
     */
    public boolean channelIsAuth(Channel channel) {
        String sessionId = sessionManager.getChannelSessionId(channel);
        if (StringUtils.isNotBlank(sessionId)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 客户端状态更新后，更新状态
     *
     * @param channel
     * @param status
     */
    public void updateStatus(Channel channel, String status) {
        sessionManager.updateStatus(channel, status);
    }

    /**
     * 获取客户端交易APP的状态
     *
     * @param sessionId
     */
    public String getSeesionStatus(String sessionId) {
        return sessionManager.getSessionStatus(sessionId);
    }

    /**
     * 终端连接签名信息
     *
     * @param channel
     * @return
     */
    public Map<String, Object> getSignature(Channel channel) {
        return sessionManager.getSignature(channel);
    }

    /**
     * 已连接终端数量（做用户认证后的，只是连接的，未做认证的，超时会被服务器断开）
     *
     * @return
     */
    public int getSessionCount() {
        return sessionManager.getSessionCount();
    }

    /**
     * 获取已连接终端的ID集合
     *
     * @return
     */
    public Set<String> getSessionIds() {
        return sessionManager.getSessionKeys();
    }

    /**
     * 已连接终端健康状况
     *
     * @param sessionId
     * @return
     */
    public boolean sessionIsActive(String sessionId) {
        return sessionManager.isActive(sessionId);
    }

    /**
     * 向某终端会话发送消息
     *
     * @param sessionId
     * @param msg
     * @return
     */
    public boolean sendMessage(String sessionId, ByteBuf msg) {
        return sessionManager.write(sessionId, msg);
    }

    public boolean sendStartCommand(String sessionId) {
        JSONObject json = new JSONObject();
        MessagePacket messagePacket = new MessagePacket();
        ByteBuf buffer = messagePacket.getReqPacket(MessagePacket.SERVER_START_COMMAND, json.toJSONString());
        Message message = messagePacket.getMessage();
        WaitAckRequest waitAck = new WaitAckServerRequest(sessionId, message);
        waitAckRequestCache.put(message.getSerial(), waitAck);
        return sendMessage(sessionId, buffer);
    }

    public boolean sendQuietCommand(String sessionId) {
        JSONObject json = new JSONObject();
        MessagePacket messagePacket = new MessagePacket();
        ByteBuf buffer = messagePacket.getReqPacket(MessagePacket.SERVER_QUIET_COMMAND, json.toJSONString());
        Message message = messagePacket.getMessage();
        WaitAckRequest waitAck = new WaitAckServerRequest(sessionId, message);
        waitAckRequestCache.put(message.getSerial(), waitAck);
        return sendMessage(sessionId, buffer);
    }

    public boolean sendExitCommand(String sessionId) {
        JSONObject json = new JSONObject();
        MessagePacket messagePacket = new MessagePacket();
        ByteBuf buffer = messagePacket.getReqPacket(MessagePacket.SERVER_EXIT_COMMAND, json.toJSONString());
        Message message = messagePacket.getMessage();
        WaitAckRequest waitAck = new WaitAckServerRequest(sessionId, message);
        waitAckRequestCache.put(message.getSerial(), waitAck);
        return sendMessage(sessionId, buffer);
    }

    public boolean sendTaskNotifyCommand(String sessionId, ServerTaskNotifyCommandReq serverCommandReq) {
        String body = JSONObject.toJSONString(serverCommandReq);
        log.info("sendTaskNotifyCommand 发送消息：{}", body);
        MessagePacket messagePacket = new MessagePacket();
        ByteBuf buffer = messagePacket.getReqPacket(MessagePacket.SERVER_TASKNOTIFY_COMMAND, body);
        Message message = messagePacket.getMessage();
        WaitAckRequest waitAck = new WaitAckServerRequest(sessionId, message);
        waitAckRequestCache.put(message.getSerial(), waitAck);
        return sendMessage(sessionId, buffer);
    }

    private JSONObject getErrorResponse(String errorCode, String errorMessage) {
        JSONObject response = new JSONObject();

        response.put("success", "0");
        response.put("code", errorCode);
        response.put("message", errorMessage);

        return response;
    }

    private JSONObject getSuccessResponse(String errorCode, String errorMessage) {
        JSONObject response = new JSONObject();

        response.put("success", "1");
        response.put("code", 0);

        if (StringUtils.isNotEmpty(errorCode)) {
            response.put("code", errorCode);
        }

        if (StringUtils.isNotEmpty(errorMessage)) {
            response.put("message", errorMessage);
        }

        return response;
    }


    public void test() {
        try {
            TimeUnit.SECONDS.sleep(5);
            long begin = System.currentTimeMillis();
            for (int m = 0; m < 100; m++) {
                /*



                 */

            }
            log.info("耗时:" + (System.currentTimeMillis() - begin));
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) throws InterruptedException {

       /* LinkerServer server = LinkerServer.getInstance();
        Thread thread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception e) {
                log.error("start thread:{}", e.getMessage());

            }
        });
        thread.start();
        server.waitStarted();*/
        //server.test();

    }
}
