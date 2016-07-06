package com.test.id;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class IdMaker {
	
	private ZkClient client = null;
	private final String server;
	private final String root;
	private final String nodeName;
	private volatile boolean running = false;
	private ExecutorService executor = null;
	
	public enum RemoveMethod {
		NONE,IMMEDIATELY,DELAY
	}
	
	public IdMaker(String zkServer, String root, String nodeName) {
		this.root = root;
		this.server = zkServer;
		this.nodeName = nodeName;
	}
	
	public void start() throws Exception {
		if(running)
			throw new Exception("server has start... ...");
		running = true;
		init();
	}
	
	private void init() {
		client = new ZkClient(server, 10000, 10000, new BytesPushThroughSerializer());
		executor = Executors.newFixedThreadPool(10);
		try {
			client.createPersistent(root, true);
		} catch (ZkNodeExistsException e) {
		}
	}
	
	public void stop() throws Exception {
		if(!running)
			throw new Exception("server has stop... ...");
		running = false;
		freeResource();
	}
	
	private void freeResource() {
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executor = null;
		}
		
		if(client != null){
			client.close();
			client = null;
		}
	}
	
	public String generateId(RemoveMethod removeMethod) throws Exception {
		checkRunning();
		String nodePath = root.concat("/").concat(nodeName);
		final String path = client.createPersistentSequential(nodePath, null);
		if(removeMethod.equals(RemoveMethod.IMMEDIATELY)) {
			client.delete(path);
		} else if(removeMethod.equals(RemoveMethod.DELAY)) {
			executor.execute(new Runnable() {
				
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					client.delete(path);
				}
			});
		}
		//node-00000000,node-000000001
		return extractId(path);
	}
	
	private String extractId(String str) {
		int index = str.lastIndexOf(nodeName);
		if(index > 0) {
			index += nodeName.length();
			return index <= str.length() ? str.substring(index) : "";
		}
		return str;
	}
	
	private void checkRunning() throws Exception {
		if(!running)
			throw new Exception("请先启动服务!!!");
	}

}
