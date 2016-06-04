package com.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 分散(scatter)从channel中读取是指在读操作时将读取的数据写入到多个buffer中
 * 因此，channel将从channel中读取的数据分散到多个buffer
 * 聚集(gather)写入channel是指在写操作时将多个buffer的数据写入到同一个buffer中
 * 
 * scatter/gather经常用于需要将传输的数据分开处理的场合，例如传输一个由消息头
 * 和消息体组成的消息，你可能会将消息体和消息头分散到不同的Buffer中，这样你可以方便的处理消息头和消息体
 * @author GavinCee
 *
 */
public class ScattingAngGather {
	
	public static void main(String[] args) {
		
	}
	
	public static void gather() {
		ByteBuffer header = ByteBuffer.allocate(10);
		ByteBuffer body = ByteBuffer.allocate(10);
		
		byte[] b1 = {'0', '1'};
		byte[] b2 = {'2', '3'};
		
		header.put(b1);
		body.put(b2);
		
		ByteBuffer[] buffs = {header, body};
		
		try {
			FileOutputStream os = new FileOutputStream("E:\\Develop Tool\\helloworld.txt");
			FileChannel channel = os.getChannel();
			channel.write(buffs);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
