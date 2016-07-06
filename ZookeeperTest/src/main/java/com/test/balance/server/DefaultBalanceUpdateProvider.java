package com.test.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

public class DefaultBalanceUpdateProvider implements BalanceUpdateProvider {
	
	private String serverPath;
	
	private ZkClient client;
	
	public DefaultBalanceUpdateProvider(String path, ZkClient client) {
		this.serverPath = path;
		this.client = client;
	}

	public boolean addBalance(Integer step) {
		Stat stat = new Stat();
		ServerData data;
		while(true) {
			try {
				data = client.readData(this.serverPath, stat);
				data.setBalance(data.getBalance() + step);
				client.writeData(this.serverPath, data, stat.getVersion());
				return true;
			} catch(ZkBadVersionException e) {
				
			} catch (Exception e) {
				return false;
			}
		}
	}

	public boolean reduceBalance(Integer step) {
		Stat stat = new Stat();
		ServerData data;
		while(true) {
			try {
				data = client.readData(this.serverPath, stat);
				data.setBalance(data.getBalance() > step ? data.getBalance() - step : 0);
				client.writeData(this.serverPath, data, stat.getVersion());
				return true;
			} catch(ZkBadVersionException e) {
				
			} catch (Exception e) {
				return false;
			}
		}
	}

}
