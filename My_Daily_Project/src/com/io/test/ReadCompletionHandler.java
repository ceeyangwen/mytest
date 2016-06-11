package com.io.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.sql.Date;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer>{
	
	private AsynchronousSocketChannel asynchronousSocketChannel;
	
	public ReadCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
		if(this.asynchronousSocketChannel == null) {
			this.asynchronousSocketChannel = asynchronousSocketChannel;
		}
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try {
			String req = new String(body, "UTF-8");
			System.out.println("The time server receive order : " + req);
			String current = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
			doWrite(current);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void doWrite(String currentTime) {
		if(null != currentTime && currentTime.length() > 0) {
			byte[] bytes = currentTime.getBytes();
			ByteBuffer buf = ByteBuffer.allocate(bytes.length);
			buf.put(bytes);
			buf.flip();
			asynchronousSocketChannel.write(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					if(attachment.hasRemaining()) {
						asynchronousSocketChannel.write(buf, buf, this);
					}
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					closeChannel();
				}
			});
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		closeChannel();
	}
	
	private void closeChannel() {
		if(this.asynchronousSocketChannel != null) {
			try {
				this.asynchronousSocketChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
