package com.test.queue;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

public class DistributedSimpleQueue<T> {
	
	protected final ZkClient client;
	protected final String root;
	
	protected static final String NODE_NAME = "n_";
	
	public DistributedSimpleQueue(ZkClient client, String path) {
		this.client = client;
		this.root = path;
	}
	
	public int size() {
		return client.getChildren(root).size();
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	//推入消息
	public boolean offer(T element) throws Exception {
		String nodePath = root.concat("/").concat(NODE_NAME);
		try {
			client.createPersistentSequential(nodePath, element);
		} catch (ZkNoNodeException e) {
			client.createPersistent(root);
			offer(element);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	//获取消息
	public T poll() throws Exception {
		try {
			List<String> list = client.getChildren(root);
			if(list.size() == 0) {
				return null;
			}
			Collections.sort(list, new Comparator<String>() {
				public int compare(String str1, String str2) {
					return getNodeNumber(str1, NODE_NAME).compareTo(getNodeNumber(str2, NODE_NAME));
				}
			});
			for(String nodeName : list) {
				String nodePath = root.concat("/").concat(nodeName);
				try {
					T node = (T)client.readData(nodePath);
					client.delete(nodePath);
					return node;
				} catch (Exception e) {
				}
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private String getNodeNumber(String str, String nodeName) {
		int index = str.lastIndexOf(nodeName);
		if(index >= 0) {
			index += NODE_NAME.length();
			return index <= str.length() ? str.substring(index) : "";
		}
		return str;
	}

}
