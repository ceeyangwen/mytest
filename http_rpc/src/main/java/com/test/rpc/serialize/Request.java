package com.test.rpc.serialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 封装请求参数对象
 * @author GavinCee
 *
 */
public class Request implements Serializable {

	//请求的类、接口
	private Class clz;
	
	//请求方法名
	private String methodName;
	
	//请求的参数
	private Object param;

	public Class getClz() {
		return clz;
	}

	public void setClz(Class clz) {
		this.clz = clz;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}
	
	/**
	 * 执行对象方法
	 * @param bean 要执行方法的对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object invoke(Object bean) throws Exception {
		if(param instanceof JSONArray) {
			JSONArray paramJsonArr = (JSONArray)param;
			Class[] paramTypes = new Class[paramJsonArr.size()];
			Object[] values = new Object[paramJsonArr.size()];
			for(int i = 0; i < paramJsonArr.size(); i++) {
				paramTypes[i] = paramJsonArr.get(0).getClass();
				values[i] = paramJsonArr.get(i);
			}
			return clz.getMethod(methodName, paramTypes).invoke(bean, values);
		} else if(param instanceof JSONObject) {
			return clz.getMethod(methodName, ((JSONObject)param).getClass()).invoke(bean, ((JSONObject)param).clone());
		}
		return clz.getMethod(methodName).invoke(bean, null);
	}
}
