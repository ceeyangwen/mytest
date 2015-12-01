package com.test.rpc.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.test.rpc.exception.RpcException;
import com.test.rpc.exception.RpcExceptionCodeEnum;

public class ZookeeperClient {
	
	private ZkClient zkClient;
	
	private volatile Watcher.Event.KeeperState state = Watcher.Event.KeeperState.SyncConnected;
	
	public ZookeeperClient(String url) {
		zkClient = new ZkClient(url);
	}

	//创建持久化目录
	public void createPersistent(String path) {
		zkClient.createPersistent(path, true);
	}
	
	//创建临时目录
	public void createEphemeral(String path, String data) {
		zkClient.createEphemeral(path, data);
	}
	
	//删除目录
	public void delete(String path) {
		zkClient.delete(path);
	}
	
	//获取子目录 
	public List<String> getChildren(String path) throws RpcException{
		List<String> pathList = zkClient.getChildren(path);
		if(pathList != null && pathList.size() > 0) {
			return pathList;
		}
		throw new RpcException(RpcExceptionCodeEnum.NO_PROVIDERS.getCode(), path);
	}
	
	//获取节点的值
	public <T> T getData(String path) {
		return zkClient.readData(path, true);
	}
	
	//设置观察者
	public void setWatcher(String path, IZkChildListener watcher) {
		zkClient.subscribeChildChanges(path, watcher);
	}
	
	//判断是否连接
	public boolean isConnected() {
		return state == KeeperState.SyncConnected;
	}
	
	//关闭连接
	public void close() {
		zkClient.close();
	}
}
