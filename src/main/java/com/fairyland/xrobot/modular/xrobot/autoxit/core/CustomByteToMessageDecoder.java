package com.fairyland.xrobot.modular.xrobot.autoxit.core;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomByteToMessageDecoder extends ByteToMessageDecoder {
	private final static Logger log = LoggerFactory.getLogger(CustomByteToMessageDecoder.class);
	
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf bufferIn, List<Object> out) throws Exception {
        if (bufferIn.readableBytes() < 4) {
            return;
        }
        
        int beginIndex = bufferIn.readerIndex();
        int length = bufferIn.readInt();
        if(length>Options.Packet.MAX_MESSAGE_LENGTH) {
        	//throw new Exception("数据包错误!");
        	log.error("数据包错误!");
        	bufferIn.clear();
        	bufferIn.readerIndex(0);
        	return;
        }
        
        if (bufferIn.readableBytes() < length) {
            bufferIn.readerIndex(beginIndex);
            return;
        }

        bufferIn.readerIndex(beginIndex + 4 + length);
        ByteBuf otherByteBufRef = bufferIn.slice(beginIndex, 4 + length);
        
        otherByteBufRef.retain();
        out.add(otherByteBufRef);
    }
}
