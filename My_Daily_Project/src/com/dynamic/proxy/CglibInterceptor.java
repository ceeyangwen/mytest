package com.dynamic.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibInterceptor {
	
	static class MethodInterceptorImpl implements MethodInterceptor {

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			System.out.println(method);
			proxy.invokeSuper(obj, args);
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(TestClass.class);
		enhancer.setCallback(new MethodInterceptorImpl());
		TestClass test = (TestClass)enhancer.create();
		test.doSome();
	}

}
