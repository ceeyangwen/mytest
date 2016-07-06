package com.test.publish;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import com.alibaba.fastjson.JSON;

public class WorkServer {
	
	private ZkClient zkClient;
	private String configPath;
	private String serverPath;
	private ServerData serverData;
	private ServerConfig serverConfig;
	private IZkDataListener dataListener;
	
	public WorkServer(String configPath, String serverPath, ServerData serverData, ServerConfig serverConfig, ZkClient zkClient) {
		this.zkClient = zkClient;
		this.serverPath = serverPath;
		this.configPath = configPath;
		this.serverConfig = serverConfig;
		this.serverData = serverData;
		this.dataListener = new IZkDataListener() {
			
			public void handleDataDeleted(String arg0) throws Exception {
				
			}
			
			public void handleDataChange(String dataPath, Object data) throws Exception {
				String retJson = new String((byte[])data);
				ServerConfig config = (ServerConfig)JSON.parseObject(retJson, ServerConfig.class);
				updateConfig(config);
				System.out.println("new work server config is : " + config.toString());
			}
		};
	}
	
	public void start() {
		System.out.println("work server start... ...");
		initRunning();
	}
	
	public void stop() {
		System.out.println("work server stop... ...");
		zkClient.unsubscribeDataChanges(configPath, dataListener);
	}
	
	private void initRunning() {
		register();
		zkClient.subscribeDataChanges(configPath, dataListener);
	}
	
	/**
	 * 
	 */
	private void register() {
		String path = serverPath.concat("/").concat(serverData.getAddress());
		try {
			zkClient.createEphemeral(path, JSON.toJSONString(serverData).getBytes());
		} catch (ZkNoNodeException e) {
			zkClient.createPersistent(serverPath, true);
			register();
		}
	}
	
	private void updateConfig(ServerConfig config) {
		this.serverConfig = config;
	}

}
