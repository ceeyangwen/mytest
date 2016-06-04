package com.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class TcpClientNio {
	
	public static void main(String[] args) {
		client();
	}
	
	public static void client() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		SocketChannel socketChannel = null;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
			
			if(socketChannel.finishConnect()) {
				int i = 0;
				while(true) {
					TimeUnit.SECONDS.sleep(1);
					String info = "I'm " + (i++) + "-th information from client";
					buffer.clear();
					buffer.put(info.getBytes());
					buffer.flip();
					while(buffer.hasRemaining()) {
						System.out.println(buffer);
						socketChannel.write(buffer);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(socketChannel != null) {
				try {
					socketChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
