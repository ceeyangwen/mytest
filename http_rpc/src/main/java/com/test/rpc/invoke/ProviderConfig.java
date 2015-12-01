package com.test.rpc.invoke;

import java.net.Inet4Address;

import com.test.rpc.zookeeper.ZookeeperClient;

/**
 * 生产者配置类
 * @author GavinCee
 *
 */
public class ProviderConfig {
	
	private String target;
	private int port;
	private ZookeeperClient client;
	
	public ProviderConfig() {
	}

	public ProviderConfig(String target, int port) {
		this.target = target;
		this.port = port;
	}
	
	public void register(Class clz) {
		if(null == client && target.toLowerCase().startsWith("zookeeper://")) {
			client = new ZookeeperClient(target.toLowerCase().replaceFirst("zookeeper://", ""));
		}
		if(null != client) {
			client.createPersistent("/rpc/" + clz.getName().replaceAll("\\.", "/"));
			client.createEphemeral("/rpc/" + clz.getName().replaceAll("\\.", "/") + "/node", getNodeInfo());
		}
	}
	
	public String getNodeInfo() {
		try {
			return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":" + getPort();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

}
