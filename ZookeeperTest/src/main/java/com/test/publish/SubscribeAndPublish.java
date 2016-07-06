package com.test.publish;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class SubscribeAndPublish {
	
	private static final int CLIENT_NUM = 5;
	private static final String ZOOKEEPER_SERVER = "192.168.20.3:2181";
	private static final String CONFIG_PATH ="/config";
	private static final String SERVER_PATH = "/server";
	private static final String COMMAND_PATH = "/command";
	
	public static void main(String[] args) throws Exception{
		List<ZkClient> clients = new ArrayList<ZkClient>();
		List<WorkServer> servers = new ArrayList<WorkServer>();
		ManageServer manager = null;
		
		try {
			ServerConfig config = new ServerConfig();
			config.setDbPwd("123456");
			config.setDbUrl("jdbc:mysql://localhost:3306/test");
			config.setDbUser("root");
			
			ZkClient clientManager = new ZkClient(ZOOKEEPER_SERVER, 10000, 10000, new BytesPushThroughSerializer());
			manager = new ManageServer(SERVER_PATH, COMMAND_PATH, CONFIG_PATH, clientManager, config);
			manager.start();
			
			for(int i = 0; i < CLIENT_NUM; i++) {
				ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 10000, 10000, new BytesPushThroughSerializer());
				clients.add(client);
				ServerData data = new ServerData();
				data.setId(i);
				data.setName("workserver #" + i);
				data.setAddress("192.168.1." + i);
				
				WorkServer server = new WorkServer(CONFIG_PATH, SERVER_PATH, data, config, client);
				servers.add(server);
				server.start();
			}
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} finally {
			System.out.println("Shutting down...");
//            for ( WorkServer workServer : servers )
//            {
//            	try {
//            		workServer.stop();	
//				} catch (Exception e) {
//					e.printStackTrace();
//				}           	
//            }
//            for ( ZkClient client : clients )
//            {
//            	try {
//            		client.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//            	
//            }
		}
	}

}
