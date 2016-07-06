package com.test.balance.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import com.test.balance.server.ServerData;

public class DefaultBalanceProvider extends AbstractBalanceProvider<ServerData>{
	
	private final String zkServer;
	
	private final String serverPath;
	
	private final ZkClient client;
	
	private final Integer SESSION_TIMEOUT = 10000;
	
	private final Integer CONNECT_TIMEOUT = 10000;
	
	public DefaultBalanceProvider(String zkServer, String serversPath) {
		this.serverPath = serversPath;
		this.zkServer = zkServer;
		this.client = new ZkClient(this.zkServer, SESSION_TIMEOUT, CONNECT_TIMEOUT, new SerializableSerializer());
	}

	@Override
	protected ServerData balanceAlgorithm(List<ServerData> items) {
		if(items.size() > 0) {
			Collections.sort(items);
			return items.get(0);
		} else {
			return null;
		}
	}

	@Override
	protected List<ServerData> getBalanceItems() {
		List<ServerData> lists = new ArrayList<ServerData>();
		List<String> children = client.getChildren(this.serverPath);
		for(int i = 0; i < children.size(); i++) {
			ServerData sd = client.readData(serverPath + "/" + children.get(i));
			lists.add(sd);
		}
		return lists;
	}

}
