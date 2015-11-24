package com.test.rpc.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 序列化器
 * @author GavinCee
 *
 */
public class Serializer {
	
	public static final Serializer serializer = new Serializer();
	
	/**
	 * 序列化请求对象
	 * @param clz 请求的接口
	 * @param methodName 请求的方法名
	 * @param param 请求的参数
	 * @return
	 */
	public String serializeReq(Class clz, String methodName, Object param) {
		Request req = new Request();
		req.setClz(clz);
		req.setMethodName(methodName);
		req.setParam(param);
		return JSON.toJSONString(req, SerializerFeature.WriteClassName);
	}

	/**
	 * 序列化结果
	 * @param param 响应的结果
	 * @return
	 */
	public String serializeRes(Object param) {
		return JSON.toJSONString(param, SerializerFeature.WriteClassName);
	}

}
