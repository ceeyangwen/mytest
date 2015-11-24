package com.test.rpc.invoke;

/**
 * 生产者配置类
 * @author GavinCee
 *
 */
public class ProviderConfig {
	
	private String target;
	private int port;
	
	public ProviderConfig() {
	}

	public ProviderConfig(String target, int port) {
		this.target = target;
		this.port = port;
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
