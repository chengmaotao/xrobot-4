package com.fairyland.xrobot.modular.xrobot.autoxit.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class MessagePacket {
    public static final int REQ_MESSAGE = 0;
    public static final int RESP_MESSAGE = 1;

    /**
     * 客户端请求指令定义
     */
    /**
     * 登录
     */
    public static final int CLIENT_LOGIN_COMMAND = 10000;
    /**
     * 报告状态
     */
    public static final int CLIENT_REPORTSTATUS_COMMAND = 10001;


    /**
     * 获取任务
     */
    public static final int CLIENT_GETTASK_COMMAND = 10100;
    /**
     * 检查用户是否可以发送消息
     */
    public static final int CLIENT_CHECKPUSHMESSAGE_COMMAND = 10101;
    /**
     * 上报发消息加群结果
     */
    public static final int CLIENT_SUBMIT_PUSHJOINGROUPS_COMMAND = 10102;
    /**
     * 上报发消息结果
     */
    public static final int CLIENT_SUBMIT_PUSHMESSAGES_COMMAND = 10103;
    /**
     * 上报评论加群结果
     */
    public static final int CLIENT_SUBMIT_COMMENTJOINGROUPS_COMMAND = 10104;

    /**
     * 上报评论结果
     */
    public static final int CLIENT_SUBMIT_COMMENTS_COMMAND = 10105;

    /**
     * 上报建群发贴结果
     */
    public static final int CLIENT_SUBMIT_CREATEGROUPS_COMMAND = 10106;
    /**
     * 任务执行结果报告
     */
    public static final int CLIENT_SUBMIT_TASKRESPONSE_COMMAND = 10107;


    /**
     * 服务器请求指令定义
     */

    /**
     * 启动客户端
     */
    public static final int SERVER_START_COMMAND = 20001;
    /**
     * 暂停操作
     */
    public static final int SERVER_QUIET_COMMAND = 20001;
    /**
     * 退出客户端
     */
    public static final int SERVER_EXIT_COMMAND = 20002;

    /**
     * 有新任务通知
     */
    public static final int SERVER_TASKNOTIFY_COMMAND = 20100;


    /**
     * 心跳指令
     */
    public static final int CLIENT_HEART_MESSAG = 88888;

    private Message message = new Message(0, 0, "", "");
    private String encoding = "UTF-8";

    public MessagePacket() {
    }

    public MessagePacket(String encoding) {
        this.encoding = encoding.toUpperCase();
    }

    public ByteBuf getReqPacket(int command, String body) {
        this.message.setMsgType(REQ_MESSAGE);
        this.message.setCommand(command);
        UUID uuid = UUID.randomUUID();
        this.message.setSerial(uuid.toString());
        this.message.setBody(body);
        return getPacket();

    }

    public ByteBuf getRespPacket(int command, String serial, String body) {
        this.message.setMsgType(RESP_MESSAGE);
        this.message.setCommand(command);
        this.message.setSerial(serial);
        this.message.setBody(body);
        return getPacket();
    }

    private ByteBuf getPacket() {
        if (encoding.equals("UTF-8")) {
            byte[] serialBytes = this.message.getSerial().getBytes();
            byte[] bodyBytes = this.message.getBody().getBytes();
            int length = 4 + 4 + serialBytes.length + bodyBytes.length;

            UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);

            ByteBuf buffer = allocator.buffer(4 + length);
            buffer.writeInt(length);// 数据长度
            buffer.writeInt(this.message.getMsgType());
            buffer.writeInt(this.message.getCommand());
            buffer.writeBytes(serialBytes);
            buffer.writeBytes(bodyBytes);
            return buffer;
        } else {
            byte[] serialBytes = null;
            byte[] bodyBytes = null;
            try {
                serialBytes = this.message.getSerial().getBytes("UTF-8");
                bodyBytes = this.message.getBody().getBytes("UTF-8");
                int length = 4 + 4 + serialBytes.length + bodyBytes.length;
                UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);

                ByteBuf buffer = allocator.buffer(4 + length);
                buffer.writeInt(length);// 数据长度
                buffer.writeInt(this.message.getMsgType());
                buffer.writeInt(this.message.getCommand());
                buffer.writeBytes(serialBytes);
                buffer.writeBytes(bodyBytes);
                return buffer;
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
    }

    public Message getMessage() {
        try {
            return this.message.clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }

    public String getSerial() {
        return this.message.getSerial();
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

//    public static void main(String[] args) {
//        MessagePacket messagePacket = new MessagePacket("GBK");
//
//        ByteBuf buffer = messagePacket.getReqPacket(MessagePacket.CLIENT_LOGIN_COMMAND,
//                "{\"id\":\"100001\",\"token\":\"d97c3c06-a0b2-9eca-eda8-53ce55c22c80\"}");
//
//        if (buffer.hasArray()) { // 处理堆缓冲区
//            byte[] bytes = buffer.array();
//            // int len = buffer.array().length;
//            System.out.println(bytesToHexString(bytes));
//            // String str = new String(buffer.array(), buffer.arrayOffset() +
//            // buffer.readerIndex(), buffer.readableBytes());
//        } else {
//            byte[] bytes = new byte[buffer.readableBytes()];
//            buffer.getBytes(buffer.readerIndex(), bytes);
//        }
//    }

}
