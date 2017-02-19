package com.netty.chapter12.codec;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import com.netty.chapter12.struct.Header;
import com.netty.chapter12.struct.NettyMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**  
 * @author GavinCee  
 * @date 2016年9月11日  
 *
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
	
	private MarshallingDecoder decoder;

	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		this.decoder = new MarshallingDecoder();
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf)super.decode(ctx, in);
		if(frame == null) {
			return null;
		}
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(frame.readInt());
		header.setLength(frame.readInt());
		header.setSessionID(frame.readLong());
		header.setType(frame.readByte());
		header.setPriority(frame.readByte());
		
		int size = frame.readInt();
		if(size > 0) {
			Map<String, Object> attach = new HashMap<>(size);
			int keySize = 0;
			byte[] keyAray = null;
			String key = null;
			for(int i = 0; i < size; i++) {
				keySize = frame.readInt();
				keyAray = new byte[keySize];
				frame.readBytes(keyAray);
				key = new String(keyAray, "UTF-8");
				attach.put(key, decoder.decode(frame));
			}
			keyAray = null;
			key = null;
			header.setAttachment(attach);
		}
		if(frame.readableBytes() > 4) {
			message.setBody(decoder.decode(frame));
		}
		message.setHeader(header);
		return message;
	}
	
}
