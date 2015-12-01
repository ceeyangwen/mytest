package com.test.rpc.invoke;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.test.rpc.exception.RpcException;
import com.test.rpc.zookeeper.ZookeeperClient;

/**
 * 消费者配置类
 * @author GavinCee
 *
 */
public class ConsumerConfig {
	
	private String url;
	private ZookeeperClient client;
	//调用接口的对应次数
	private final ConcurrentHashMap<Class, AtomicInteger> invokeCount = new ConcurrentHashMap<Class, AtomicInteger>();

	public String getUrl(Class cls) throws RpcException {
		if(null != client) {
			//服务的所有地址列表
			List<String> urlList = new ArrayList<String>();
			List<String> pathList = client.getChildren("/rpc/" + cls.getName().replaceAll("\\.", "/"));
			for(String path : pathList) {
				String httpUrl = client.getData("/rpc/" + cls.getName().replaceAll("\\.", "/") + "/" + path);
				if(null != httpUrl) {
					urlList.add(httpUrl);
				}
			}
			return getCurrentUrl(cls, urlList);
		} else {
			return url;
		}
	}
	
	public String getCurrentUrl(Class clz,List<String> urlList) throws RpcException {
		if(invokeCount.get(clz) == null) {
			//初次调用接口
			invokeCount.putIfAbsent(clz, new AtomicInteger(1));
			return urlList.get(0);
		} else {
			int i = invokeCount.get(clz).incrementAndGet();
			//取余进行负载均衡
			return urlList.get(i % urlList.size());
		}
	}

	public void setUrl(String url) {
		this.url = url;
		if(url.toLowerCase().startsWith("zookeeper://")) {
			client = new ZookeeperClient(url.toLowerCase().replaceFirst("zookeeper://", ""));
		}
	}
	
}
