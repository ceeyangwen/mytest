package com.netty.chapter12.codec;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;

import io.netty.buffer.ByteBuf;

/**  
 * @author GavinCee  
 * @date 2016年9月11日  
 *
 */
public class ChannelBufferByteInput implements ByteInput {
	
	private final ByteBuf buffer;
	
	public ChannelBufferByteInput(ByteBuf buffer) {
		this.buffer = buffer;
	}

	@Override
	public void close() throws IOException {
		
	}

	@Override
	public int available() throws IOException {
		return buffer.readableBytes();
	}

	@Override
	public int read() throws IOException {
		if(buffer.isReadable()) {
			return buffer.readByte() & 0xff;
		}
 		return -1;
	}

	@Override
	public int read(byte[] arr) throws IOException {
		return read(arr, 0, arr.length);
	}

	@Override
	public int read(byte[] dst, int index, int length) throws IOException {
		int available = available();
		if(available == 0) {
			return -1;
		}
		length = Math.min(available, length);
		buffer.readBytes(dst, index, length);
		return length;
	}

	@Override
	public long skip(long bytes) throws IOException {
		int readable = buffer.readableBytes();
		if(readable < bytes) {
			bytes = readable;
		}
		buffer.readerIndex((int)(buffer.readerIndex() + bytes));
		return bytes;
	}

}
