package com.test.rpc.proxy;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mortbay.jetty.handler.AbstractHandler;

import com.test.rpc.container.Container;
import com.test.rpc.container.HttpContainer;
import com.test.rpc.exception.RpcException;
import com.test.rpc.exception.RpcExceptionCodeEnum;
import com.test.rpc.invoke.HttpInvoker;
import com.test.rpc.invoke.Invoker;
import com.test.rpc.invoke.ProviderConfig;
import com.test.rpc.serialize.Deserializer;
import com.test.rpc.serialize.Request;
import com.test.rpc.serialize.Serializer;

/**
 * 主要用于服务的注册和响应请求
 * @author GavinCee
 *
 */
public class ProviderProxyFactory extends AbstractHandler{
	
	private static final Logger logger = Logger.getLogger(ProviderProxyFactory.class);
	
	private static ProviderProxyFactory factory ;
	
	private Map<Class, Object> providers = new ConcurrentHashMap<Class, Object>();
	
	private Deserializer deserializer = Deserializer.deserializer;
	
	private Serializer serializer = Serializer.serializer;
	
	private Invoker invoker = HttpInvoker.invoker;
	
	public ProviderProxyFactory(Map<Class, Object> providerMap) {
		this(providerMap, null);
	}
	
	//接收服务注册的列表
	public ProviderProxyFactory(Map<Class, Object> providerMap, ProviderConfig config) {
		if(null == Container.container) {
			if(null == config) {
				new HttpContainer(this).start();
			} else {
				new HttpContainer(this, config).start();
			}
		}
		for(Map.Entry<Class, Object> entry : providerMap.entrySet()) {
			register(entry.getKey(), entry.getValue());
		}
		factory = this;
	}
	
	private void register(Class clazz, Object obj) {
		providers.put(clazz, obj);
	}

	public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
			throws IOException, ServletException {
		//获取请求参数报文
		String reqStr = request.getParameter("data");
		try {
			//解析请求残数
			Request rpcRequest = deserializer.parseRequest(reqStr);
			//反射请求
			Object result = rpcRequest.invoke(ProviderProxyFactory.getInstance().getBeanClass(rpcRequest.getClz()));
			//响应请求
			invoker.response(serializer.serializeRes(result), response.getOutputStream());
		} catch (RpcException rpcException) {
			logger.error("", rpcException);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public Object getBeanClass(Class clazz) throws RpcException {
		Object bean = providers.get(clazz);
		if(null != bean) {
			return bean;
		}
		throw new RpcException(RpcExceptionCodeEnum.NO_BEAN_FOUND.getCode(), clazz); 
	}
	
	private ProviderProxyFactory() {}

	public static ProviderProxyFactory getInstance() {
		return factory;
	}
}
