package com.test.master;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class LeaderSelectorByCurator {
	
	private static final int CLIENT_NUM = 10;
	
	private static final String PATH = "/master";
	
	private static final String ZOOKEEPER_SERVER = "192.168.20.3:2181";
	
	public static void main(String[] args) {
		List<CuratorFramework> clients = new ArrayList<CuratorFramework>();
		List<WorkServer> servers = new ArrayList<WorkServer>();
		try {
			for(int i = 0; i < CLIENT_NUM; i++) {
				CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZOOKEEPER_SERVER)
											.sessionTimeoutMs(10000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
				client.start();
				clients.add(client);
				
				WorkServer server = new WorkServer(client, PATH, "Client #" + i);
				server.setListener(new RunningListener() {
					
					public void processStop(Object context) {
						System.out.println(context.toString() + " processStop... ...");
					}
					
					public void processStart(Object context) {
						System.out.println(context.toString() + " processStart... ...");
					}
					
					public void processActiveExit(Object context) {
						System.out.println(context.toString() + " processActiveExit... ...");
					}
					
					public void processActiveEnter(Object context) {
						System.out.println(context.toString() + " processActiveEnter... ...");
					}
				});
				servers.add(server);
				
				server.start();
			}
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("shutdown... ...");
//			for(WorkServer server : servers) {
//				CloseableUtils.closeQuietly(server);
//			}
//			for(CuratorFramework client : clients) {
//				CloseableUtils.closeQuietly(client);
//			}
		}
	}

}
