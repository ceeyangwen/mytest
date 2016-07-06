package com.test.lock;

import java.util.concurrent.Callable;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.data.Stat;

public class ZkClientExt extends ZkClient {
	
	public ZkClientExt(String zkServer, int sessionTimeout, int connectionTimeout, ZkSerializer serializer) {
		super(zkServer, sessionTimeout, connectionTimeout, serializer);
	}
	
	@Override
	public void watchForData(final String path) {
		retryUntilConnected(new Callable<Object>() {
			public Object call() throws Exception {
				Stat stat = new Stat();
				_connection.readData(path, stat, true);
				return null;
			}
		});
	}

}
