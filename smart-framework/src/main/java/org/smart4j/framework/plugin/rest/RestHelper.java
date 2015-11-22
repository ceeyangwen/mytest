package org.smart4j.framework.plugin.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpInInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPostStreamInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPreStreamInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.smart4j.framework.helper.BeanHelper;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * Rest助手类
 * @author GavinCee
 *
 */
public class RestHelper {

	private static final List<Object> providerList = new ArrayList<Object>();
	private static final List<Interceptor<? extends Message>> inInterceptorList = new ArrayList<Interceptor<? extends Message>>();
	private static final List<Interceptor<? extends Message>> outInterceptorList = new ArrayList<Interceptor<? extends Message>>();
	
	static {
		//添加JSONP Provider
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
		providerList.add(jsonProvider);
		//添加Log interceptor
		if(RestConfig.isLog()) {
			LoggingInInterceptor loggingInterceptor = new LoggingInInterceptor();
			inInterceptorList.add(loggingInterceptor);
			LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
			outInterceptorList.add(loggingOutInterceptor);
		}
		//添加JSONP interceptor
		if(RestConfig.isJsonp()) {
			JsonpInInterceptor jsonpInInceptor = new JsonpInInterceptor();
			jsonpInInceptor.setCallbackParam(RestConfig.getJsonpFunction());
			inInterceptorList.add(jsonpInInceptor);
			
			JsonpPreStreamInterceptor jsonpPreStreamInterceptor = new JsonpPreStreamInterceptor();
			outInterceptorList.add(jsonpPreStreamInterceptor);
			JsonpPostStreamInterceptor jsonpPostInterceptor = new JsonpPostStreamInterceptor();
			outInterceptorList.add(jsonpPostInterceptor);
		}
		//添加CORS Provider
		if(RestConfig.isCors()) {
			CrossOriginResourceSharingFilter corsFilter = new CrossOriginResourceSharingFilter();
			corsFilter.setAllowOrigins(RestConfig.getCorsOriginList());
			providerList.add(corsFilter);
		}
	}
	
	//发布REST服务
	public static void publishService(String wadl, Class<?> resourceClass) {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setAddress(wadl);
		factory.setResourceClasses(resourceClass);
		factory.setResourceProvider(resourceClass, new SingletonResourceProvider(BeanHelper.getBean(resourceClass)));
		factory.setProviders(providerList);
		factory.setInInterceptors(inInterceptorList);
		factory.setOutInterceptors(outInterceptorList);
		factory.create();
	}
	
	//创建REST客户端
	public static <T> T createClient(String wadl, Class<? extends T> resourceClass) {
		return JAXRSClientFactory.create(wadl, resourceClass, providerList);
	}
}
