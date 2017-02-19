package com.netty.chapter12.codec;

import java.io.IOException;

import org.jboss.marshalling.ByteOutput;

import io.netty.buffer.ByteBuf;

/**  
 * @author GavinCee  
 * @date 2016年9月11日  
 *
 */
public class ChannelBufferByteOutput implements ByteOutput {
	
	private final ByteBuf buffer;
	
	public ChannelBufferByteOutput(ByteBuf buffer) {
		this.buffer = buffer;
	}

	@Override
	public void close() throws IOException {
		
	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(int i) throws IOException {
		buffer.writeInt(i);
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		buffer.writeBytes(bytes);
	}

	@Override
	public void write(byte[] bytes, int index, int length) throws IOException {
		buffer.writeBytes(bytes, index, length);
	}
	
	public ByteBuf getBuffer() {
		return buffer;
	}

}
