package com.netty.chapter12.codec;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import io.netty.buffer.ByteBuf;

/**  
 * @author GavinCee  
 * @date 2016年9月11日  
 *
 */
public class MarshallingDecoder {
	
	private final Unmarshaller unmarshaller;
	
	public MarshallingDecoder() throws IOException {
		unmarshaller = MarshallingCodecFactory.buildUnmarshalling();
	}
	
	public Object decode(ByteBuf in) throws IOException, ClassNotFoundException {
		try {
			int objSize = in.readInt();
			ByteBuf buf = in.slice(in.readerIndex(), objSize);
			ByteInput input = new ChannelBufferByteInput(buf);
			unmarshaller.start(input);
			Object obj = unmarshaller.readObject();
			unmarshaller.finish();
			in.readerIndex(in.readerIndex() + objSize);
			return obj;
		} finally {
			unmarshaller.close();
		}
	}

}
