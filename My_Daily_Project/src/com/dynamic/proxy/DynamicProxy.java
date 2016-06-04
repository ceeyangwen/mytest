package com.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxy {
	
	public static Object getBean(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Object obj = Class.forName(className).newInstance();
		InvocationHandler handler = new AOPFactory(obj);
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), 
				obj.getClass().getInterfaces(), handler);
	}
	
	public static void main(String[] args) {
		try {
			Hello hello = (Hello)getBean("com.dynamic.proxy.HelloImpl");
			hello.setInfo("Hello World1", "Hello World2");
			hello.getInfos1();
			hello.getInfos2();
			hello.display();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
