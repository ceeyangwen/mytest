package com.test.rpc.serialize;

import com.alibaba.fastjson.JSON;
import com.test.rpc.exception.RpcException;
import com.test.rpc.exception.RpcExceptionCodeEnum;

/**
 * 反序列化器
 * @author GavinCee
 *
 */
public class Deserializer {
	
	public static final Deserializer deserializer = new Deserializer();

	/**
	 * 反序列化请求参数
	 * @param param 请求参数
	 * @return
	 * @throws RpcException
	 */
	public Request parseRequest(String param) throws RpcException {
		try {
			return (Request)JSON.parse(param);
		} catch (Exception e) {
			throw new RpcException("转换异常", e, RpcExceptionCodeEnum.DATA_PARSER_ERROR.getCode(), param);
		}
	}

	/**
	 * 反序列化响应结果
	 * @param result 响应的结果
	 * @return
	 */
	public <T> T parseResponse(String result) {
		return (T)JSON.parse(result);
	}

}
