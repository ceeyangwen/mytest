package com.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 实现基于线程池的服务器
 * @author GavinCee
 *
 */
public class ServerPool {

	private static final int THREAD_POOL_SIZE = 2;
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(20006);
		Socket client = null;
		Executor executor = Executors.newCachedThreadPool();
		boolean flag = true;
		while(flag) {
			client = server.accept();
			System.out.println("与客户端连接成功");
			executor.execute(new ServerThread(client));
		}
		server.close();
	}
	
	public static void method1() throws IOException {
		final ServerSocket server = new ServerSocket(20006);
		for(int i = 0; i < THREAD_POOL_SIZE; i++) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					//线程为了某一链接提供完服务后，循环等待其他链接请求
					while(true) {
						try {
							Socket client = server.accept();
							System.out.println("与客户端连接成功");
							ServerThread.execute(client);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			};
			thread.start();
		}
	}
	
}
