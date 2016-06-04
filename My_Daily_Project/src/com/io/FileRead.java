package com.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileRead {
	
	public static void main(String[] args) {
		method1();
		method2();
	}
	
	public static void method2() {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile("E:\\Develop Tool\\helloworld.txt", "rw");
			FileChannel channel = raf.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1024);
			int len = channel.read(buf);
			while(len != -1) {
				buf.flip();
				while(buf.hasRemaining()) {
					System.out.print((char)buf.get());
				}
				System.out.println();
				buf.compact();
				len = channel.read(buf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void method1() {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream("E:\\Develop Tool\\helloworld.txt"));
			byte[] buf = new byte[1024];
			int len = in.read(buf);
			while(len != -1) {
				for(int i = 0; i < len; i++) {
					System.out.print((char)buf[i]);
				}
				System.out.println();
				len = in.read(buf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
