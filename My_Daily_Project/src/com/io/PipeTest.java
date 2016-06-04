package com.io;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipeTest {
	
	public static void main(String[] args) {
//		DatagramTest.send();
		method();
	}
	
	public static void method() {
		Pipe pipe = null;
		ExecutorService exec = Executors.newFixedThreadPool(2);
		try {
			pipe = Pipe.open();
			final Pipe pipeTmp = pipe;
			exec.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					//向通道写数据
					Pipe.SinkChannel sinkChannel = pipeTmp.sink();
					while(true) {
						TimeUnit.SECONDS.sleep(1);
						String newData = "Pipe Test at Time " + System.currentTimeMillis();
						ByteBuffer buf = ByteBuffer.allocate(1024);
						buf.clear();
						buf.put(newData.getBytes());
						buf.flip();
						
						while(buf.hasRemaining()) {
							System.out.println(buf);
							sinkChannel.write(buf);
						}
					}
				}
				
			});
			
			exec.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					//向通道读数据
					Pipe.SourceChannel sourceChannel = pipeTmp.source();
					while(true) {
						TimeUnit.SECONDS.sleep(1);
						ByteBuffer buf = ByteBuffer.allocate(1024);
						buf.clear();
						int len = sourceChannel.read(buf);
						System.out.println("read len = " + len);
						while(len > 0) {
							buf.flip();
							byte b[] = new byte[len];
							int i = 0;
							while(buf.hasRemaining()) {
								b[i] = buf.get();
								System.out.print((char)b[i]);
								i++;
							}
							String s = new String(b);
							System.out.println("-----" + s);
							len = sourceChannel.read(buf);
						}
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
