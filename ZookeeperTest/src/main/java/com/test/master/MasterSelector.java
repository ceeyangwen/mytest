package com.test.master;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class MasterSelector {
	
	//启动线程个数
	private static final int CLIENT_NUM = 10;
	
	private static final String ZOOKEEPER_SERVER = "192.168.20.3:2181";
	
	public static void main(String[] args) {
		//保存所有zkClient的列表
		List<ZkClient> clients = new ArrayList<ZkClient>();
		//保存所有服务列表
		List<WorkServer> servers = new ArrayList<WorkServer>();
		
		try {
			for(int i = 0; i < CLIENT_NUM; i++) {
				//创建zkclient
				ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 10000, 10000, new SerializableSerializer());
				clients.add(client);
				//创建data
				RunningData data = new RunningData();
				data.setCid((long)i);
				data.setName("Client #" + i);
				//创建服务
				WorkServer server = new WorkServer(data);
				server.setZkClient(client);
				
				servers.add(server);
				server.start();
			}
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			System.out.println("Shutting down... ...");
			for(WorkServer server : servers) {
				try {
					server.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(ZkClient client : clients) {
				client.close();
			}
		}
	}

}
