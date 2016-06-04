package com.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AOPFactory implements InvocationHandler{
	
	private Object proxyed;
	
	public AOPFactory(Object prObject) {
		this.proxyed = prObject;
	}
	
	public void printlnInfo(String info, Object... args) {
		System.out.println(info);
		if(args == null) {
			System.out.println("空值");
		} else {
			for(Object obj : args) {
				System.out.println(obj);
			}
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("----->调用的方法名：" + method.getName());
		Class<?>[] paramTypes = method.getParameterTypes();
		for(Class<?> type : paramTypes) {
			System.out.println("----->" + type.getName());
		}
		printlnInfo("传入的参数为：", args);
		Object result = method.invoke(this.proxyed, args);
		printlnInfo("返回的参数为：", result);
		printlnInfo("返回值类型为：", method.getReturnType());
		return result;
	}

}
