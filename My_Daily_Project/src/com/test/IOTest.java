package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IOTest {
	
	public static void main(String[] args) throws Exception {
		String file = "C:\\Users\\GavinCee\\Desktop\\深入理解Java虚拟机总结文档.docx";
		ioRead(file);
		System.out.println("---------------------");
		nioRead(file);
		System.out.println("------------");
		nioWrite("C:\\Users\\GavinCee\\Desktop\\test.txt");
	}
	
	/*
	 * 使用IO方式读取指定文件的前1024个字节
	 */
	public static void ioRead(String file) throws Exception {
		FileInputStream fis = new FileInputStream(new File(file));
		byte[] bytes = new byte[1024];
		fis.read(bytes);
		System.out.println(new String(bytes));
		fis.close();
	}
	
	public static void nioRead(String file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileChannel channel = fis.getChannel();
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		channel.read(buf);
		byte[] bytes = buf.array();
		System.out.println(new String(bytes, "UTF-8"));
		fis.close();
	}
	
	public static void nioWrite(String file) throws Exception {
		FileOutputStream fos = new FileOutputStream(file);
		FileChannel channel = fos.getChannel();
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		for(int i = 0; i < 256; i++) {
			buf.put((byte)i);
		}
		buf.flip();
		channel.write(buf);
		fos.close();
	}

}
