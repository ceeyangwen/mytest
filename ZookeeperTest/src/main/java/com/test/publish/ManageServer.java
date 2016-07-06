package com.test.publish;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import com.alibaba.fastjson.JSON;

public class ManageServer {
	
	private String serverPath;
	private String commandPath;
	private String configPath;
	private ZkClient zkClient;
	private ServerConfig serverConfig;
	private IZkChildListener childListener;
	private IZkDataListener dataListener;
	private List<String> workServerList;
	
	public ManageServer(String serverPath, String commandPath, String configPath, ZkClient client, ServerConfig config) {
		this.serverPath = serverPath;
		this.commandPath = commandPath;
		this.zkClient = client;
		this.serverConfig = config;
		this.configPath = configPath;
		this.childListener = new IZkChildListener() {
			
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				workServerList = currentChilds;
				System.out.println("work server list change new list is ");
				execList();
			}
		};
		
		this.dataListener = new IZkDataListener() {
			
			public void handleDataDeleted(String arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			public void handleDataChange(String path, Object data) throws Exception {
				String cmd = new String((byte[])data);
				System.out.println("cmd : " + cmd);
				execCmd(cmd);
			}
		};
	}
	
	private void execCreate() {
		if(!zkClient.exists(configPath)) {
			try {
				zkClient.createPersistent(configPath, JSON.toJSONString(serverConfig).getBytes());
			} catch (ZkNodeExistsException e) {
				zkClient.writeData(configPath, JSON.toJSONString(serverConfig).getBytes());
			} catch (ZkNoNodeException e) {
				String parentPath = configPath.substring(0, configPath.lastIndexOf('/'));
				zkClient.createPersistent(parentPath, true);
				execCreate();
			}
		}
		
	}
	
	private void execModify() {
		serverConfig.setDbUser(serverConfig.getDbUser() + "_modify");
		try {
			zkClient.writeData(configPath, JSON.toJSONString(serverConfig).getBytes());
		} catch (ZkNodeExistsException e) {
			execCreate();
		}
	}
	
	public void stop() {
		zkClient.unsubscribeDataChanges(commandPath, dataListener);
		zkClient.unsubscribeChildChanges(serverPath, childListener);
	}
	
	public void start() {
		initRunning();
	}
	
	private void initRunning() {
		zkClient.subscribeChildChanges(serverPath, childListener);
		zkClient.subscribeDataChanges(commandPath, dataListener);
	}
	
	private void execCmd(String cmd) {
		if("list".equals(cmd)) {
			execList();
		} else if("create".equals(cmd)){
			execCreate();
		} else if("modify".equals(cmd)) {
			execModify();
		} else {
			System.out.println("error command! " + cmd);
		}
	}
	
	private void execList() {
		System.out.println(workServerList.toString());
	}

}
