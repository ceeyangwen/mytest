package com.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其他通道那样
 * 读取和写入，它发送和接受的是数据包
 * @author GavinCee
 *
 */
public class DatagramTest {
	
	public static void main(String[] args) {
		receive();
	}
	
	public static void receive() {
		DatagramChannel channel = null;
		try {
			channel = DatagramChannel.open();
			channel.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.clear();
			channel.receive(buf);
			
			buf.flip();
			while(buf.hasRemaining()) {
				System.out.print((char)buf.get());
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void send() {
		DatagramChannel channel = null;
		try {
			channel = DatagramChannel.open();
			String info = "I'm the sender!";
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.clear();
			buf.put(info.getBytes());
			buf.flip();
			
			int sendLen = channel.send(buf, new InetSocketAddress("127.0.0.1", 8888));
			System.out.println("sendLen = " + sendLen);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
