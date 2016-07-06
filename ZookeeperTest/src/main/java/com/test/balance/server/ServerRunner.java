package com.test.balance.server;

import java.util.ArrayList;
import java.util.List;

public class ServerRunner {
	
	private static final int SERVER_NUM = 2;
	
	private static final String ZK_SERVER = "192.168.20.3:2181";
	
	private static final String SERVER_PATH = "/servers";
	
	public static void main(String[] args) {
		List<Thread> threadList = new ArrayList<Thread>();
		
		for(int i = 0; i < SERVER_NUM; i++) {
			final Integer count = i;
			Thread thread = new Thread(new Runnable() {
				
				public void run() {
					ServerData data = new ServerData();
					data.setBalance(0);
					data.setHost("127.0.0.1");
					data.setPort(6000 + count);
					Server server = new ServerImpl(ZK_SERVER, SERVER_PATH, data);
					server.bind();
				}
			});
			threadList.add(thread);
			thread.start();
		}
		for(int i = 0; i < threadList.size(); i++) {
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
