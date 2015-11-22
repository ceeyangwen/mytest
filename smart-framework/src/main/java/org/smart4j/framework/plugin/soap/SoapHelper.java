package org.smart4j.framework.plugin.soap;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;

/**
 * SOAP助手类
 * 用于发布SOAP服务，还可以创建SOAP客户端，也就是SOAP服务接口代理对象
 * 当调用Web服务时，可开启调用日志
 * @author GavinCee
 *
 */
public class SoapHelper {
	//输入拦截
	private static final List<Interceptor<? extends Message>> inInterceptorList = 
			new ArrayList<Interceptor<? extends Message>>();
	//输出拦截
	private static final List<Interceptor<? extends Message>> outInterceptorList = 
			new ArrayList<Interceptor<? extends Message>>();
	static {
		//添加Logging Interceptor
		if(SoapConfig.isLog()) {
			LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
			inInterceptorList.add(loggingInInterceptor);
			LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
			outInterceptorList.add(loggingOutInterceptor);
		}
	}
	
	//发布SOAP服务
	public static void publishServer(String wsdl, Class<?> interfaceClass, Object implementInstance) {
		ServerFactoryBean factory = new ServerFactoryBean();
		factory.setAddress(wsdl);
		factory.setServiceClass(interfaceClass);
		factory.setServiceBean(implementInstance);
		factory.setInInterceptors(inInterceptorList);
		factory.setOutInterceptors(outInterceptorList);
		factory.create();
	}
	
	//创建SOAP客户端
	public static <T> T createClient(String wsdl, Class<? extends T> interfaceClass) {
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
		factory.setAddress(wsdl);
		factory.setServiceClass(interfaceClass);
		factory.setInInterceptors(inInterceptorList);
		factory.setOutInterceptors(outInterceptorList);
		return factory.create(interfaceClass);
	}
}
