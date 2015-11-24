package com.test.rpc.invoke;

import java.io.OutputStream;

import com.test.rpc.exception.RpcException;

/**
 * 执行接口
 * @author GavinCee
 *
 */
public interface Invoker {

	/**
	 * 处理请求
	 * @param request 请求报文
	 * @param consumerConfig 消费者配置
	 * @return
	 * @throws RpcException
	 */
	public String request(String request, ConsumerConfig consumerConfig) throws RpcException;
	
	/**
	 * 请求应答
	 * @param response 响应报文
	 * @param os 输出流
	 * @throws RpcException
	 */
	public void response(String response, OutputStream os) throws RpcException;
	
}
