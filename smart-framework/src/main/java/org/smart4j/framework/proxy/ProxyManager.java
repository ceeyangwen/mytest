package org.smart4j.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理管理器
 * 它提供一个创建代理对象的方法，输入一个目标类和一组Proxy接口实现，输出一个代理对象，用它来创建所有的代理对象
 * @author GavinCee
 *
 */
public class ProxyManager {

	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor(){

			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return new ProxyChain(targetClass, obj, method, proxy, args, proxyList).doProxyChain();
			}
			
		});
	}
	
}
