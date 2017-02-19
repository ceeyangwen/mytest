package com.netty.chapter12.codec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.netty.chapter12.struct.Header;
import com.netty.chapter12.struct.NettyMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**  
 * @author GavinCee  
 * @date 2016年10月16日  
 *
 */
public class TestCodec {
	
	private MarshallingEncoder marshallingEncoder;
	
	private MarshallingDecoder marshallingDecoder;
	
	public TestCodec() throws IOException {
		marshallingDecoder = new MarshallingDecoder();
		marshallingEncoder = new MarshallingEncoder();
	}
	
	public NettyMessage getMessage() {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(12345);
		header.setLength(123);
		header.setSessionID(99999);
		header.setType((byte)1);
		header.setPriority((byte)7);
		Map<String, Object> attachment = new HashMap<>();
		for(int i = 0; i < 10; i++) {
			attachment.put("city --> " + i, "chenyanwgen " + i);
		}
		header.setAttachment(attachment);
		msg.setHeader(header);
		msg.setBody("abcdefg-----------------------AAAAAA");
		return msg;
	}
	
	public ByteBuf encode(NettyMessage msg) throws IOException {
		ByteBuf sendBuf = Unpooled.buffer();
		sendBuf.writeInt(msg.getHeader().getCrcCode());
		sendBuf.writeInt(msg.getHeader().getLength());
		sendBuf.writeLong(msg.getHeader().getSessionID());
		sendBuf.writeByte(msg.getHeader().getType());
		sendBuf.writeByte(msg.getHeader().getPriority());
		sendBuf.writeInt(msg.getHeader().getAttachment().size());
		String key = null;
		byte[] keyArray = null;
		Object value = null;
		
		for(Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
			key = param.getKey();
			keyArray = key.getBytes("UTF-8");
			sendBuf.writeInt(keyArray.length);
			sendBuf.writeBytes(keyArray);
			value = param.getValue();
			marshallingEncoder.encode(value, sendBuf);
		}
		key = null;
		keyArray = null;
		value = null;
		if(msg.getBody() != null) {
			marshallingEncoder.encode(msg.getBody(), sendBuf);
		} else {
			sendBuf.writeInt(0);
		}
		sendBuf.setInt(4, sendBuf.readableBytes());
		return sendBuf;
	}
	
	public NettyMessage decode(ByteBuf in) throws ClassNotFoundException, IOException {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(in.readInt());
		header.setLength(in.readInt());
		header.setSessionID(in.readLong());
		header.setType(in.readByte());
		header.setPriority(in.readByte());
		
		int size = in.readInt();
		if(size > 0) {
			Map<String, Object> attach = new HashMap<>();
			int keySize = 0;
			byte[] keyArray = null;
			String key = null;
			for(int i = 0; i < size; i++) {
				keySize = in.readInt();
				keyArray = new byte[keySize];
				in.readBytes(keyArray);
				key = new String(keyArray, "UTF-8");
				attach.put(key, marshallingDecoder.decode(in));
			}
			keyArray = null;
			key = null;
			header.setAttachment(attach);
		}
		if(in.readableBytes() > 4) {
			msg.setBody(marshallingDecoder.decode(in));
		}
		msg.setHeader(header);
		return msg;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		TestCodec test = new TestCodec();
		NettyMessage msg = test.getMessage();
		System.out.println(msg + " [body] " + msg.getBody());
		for(int i = 0; i < 5; i++) {
			System.out.println("-----------------------------------");
			ByteBuf buf = test.encode(msg);
			NettyMessage decodeMsg = test.decode(buf);
			System.out.println(decodeMsg + " [body] " + decodeMsg.getBody());
		}
	}

}
