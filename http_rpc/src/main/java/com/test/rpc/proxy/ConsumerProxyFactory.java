package com.test.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.test.rpc.invoke.ConsumerConfig;
import com.test.rpc.invoke.HttpInvoker;
import com.test.rpc.invoke.Invoker;
import com.test.rpc.serialize.Deserializer;
import com.test.rpc.serialize.Serializer;

/**
 * 主要用于远程对象的代理
 * @author GavinCee
 *
 */
public class ConsumerProxyFactory implements InvocationHandler{
	
	private ConsumerConfig consumerConfig;
	
	private Deserializer deserializer = Deserializer.deserializer;
	
	private Serializer serializer = Serializer.serializer;
	
	private Invoker invoker = HttpInvoker.invoker;
	
	private String className;
	
	public Object create() throws Exception {
		Class interfaceClass = Class.forName(className);
		return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Class interfaceClass = proxy.getClass().getInterfaces()[0];
		//封装请求报文
		String req = serializer.serializeReq(interfaceClass, method.getName(), args);
		String res = null;
		int times = 0;
		//心跳机制延迟， 默认重连3次
		while(times++ < 2 && res == null) {
			try {
				res = invoker.request(req, consumerConfig.getUrl(interfaceClass));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if(null == res) {
			invoker.request(req, consumerConfig.getUrl(interfaceClass));
		}
		//String res = invoker.request(req, consumerConfig);
		return deserializer.parseResponse(res);
	}

	public ConsumerConfig getConsumerConfig() {
		return consumerConfig;
	}

	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}

	public Deserializer getDeserializer() {
		return deserializer;
	}

	public void setDeserializer(Deserializer deserializer) {
		this.deserializer = deserializer;
	}

	public Serializer getSerializer() {
		return serializer;
	}

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Invoker getInvoker() {
		return invoker;
	}

	public void setInvoker(Invoker invoker) {
		this.invoker = invoker;
	}

}
