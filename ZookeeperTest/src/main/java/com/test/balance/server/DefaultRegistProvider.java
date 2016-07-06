package com.test.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

public class DefaultRegistProvider implements RegistProvider {

	public void regist(Object context) throws Exception {
		ZookeeperRegistContext registContext = (ZookeeperRegistContext)context;
		String path = registContext.getPath();
		ZkClient client = registContext.getZkClient();
		
		try {
			client.createEphemeral(path, registContext.getData());
		} catch (ZkNoNodeException e) {
			String parentDir = path.substring(0, path.lastIndexOf('/'));
			client.createPersistent(parentDir, true);
			regist(registContext);
		}
	}

	public void unRegist(Object context) throws Exception {
	}

}
