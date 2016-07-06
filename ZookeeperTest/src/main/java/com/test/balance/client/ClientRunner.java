package com.test.balance.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.test.balance.server.ServerData;

public class ClientRunner {
	
	private static final int CLIENT_NUM = 3;
	private static final String ZOOKEEPER_SERVER = "192.168.20.3:2181";
	private static final String SERVER_PATH = "/servers";
	
	public static void main(String[] args) throws InterruptedException, IOException {
		List<Thread> threadList = new ArrayList<Thread>();
		final BalanceProvider<ServerData> providers = new DefaultBalanceProvider(ZOOKEEPER_SERVER, SERVER_PATH);
		
		for(int i = 0; i < CLIENT_NUM; i++) {
			Thread t = new Thread(new Runnable() {
				
				public void run() {
					Client client = new ClientImpl(providers);
					try {
						client.connect();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			threadList.add(t);
			t.start();
			Thread.sleep(2000);
		}
		new BufferedReader(new InputStreamReader(System.in)).readLine();
	}

}
