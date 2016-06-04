package com.dynamic.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

public class CglibCallbackFilter {
	
	static class MethodInterceptorImpl implements MethodInterceptor {

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			System.out.println(method);
			return proxy.invokeSuper(obj, args);
		}
		
	}
	
	static class CallbackFilterImpl implements CallbackFilter {

		//返回1代表不会进行intercept的调用
		public int accept(Method method) {
			return "doTwo".equals(method.getName()) ? 1 :0;
		}
		
	}
	
	public static void main(String[] args) {
		Callback[] callback = new Callback[]{new MethodInterceptorImpl(), NoOp.INSTANCE};
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(CallBackFilterTest.class);
		enhancer.setCallbacks(callback);
		enhancer.setCallbackFilter(new CallbackFilterImpl());
		CallBackFilterTest test = (CallBackFilterTest)enhancer.create();
		test.doOne();
		test.doTwo();
	}

}
