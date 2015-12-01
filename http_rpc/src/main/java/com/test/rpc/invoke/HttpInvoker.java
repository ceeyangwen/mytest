package com.test.rpc.invoke;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.test.rpc.exception.RpcException;
import com.test.rpc.exception.RpcExceptionCodeEnum;
import com.test.rpc.util.CommonConstant;
import com.test.rpc.util.CommonPreperty;

public class HttpInvoker implements Invoker{
	
	private static final HttpClient httpClient = getHttpClient();
	
	public static final Invoker invoker = new HttpInvoker();
	
	private HttpInvoker() {}
	
	//请求
	public String request(String request, String url) throws RpcException {
		HttpPost post = new HttpPost(url);
		post.setHeader("Connection", "Keep-Alive");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("data", request));
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if(response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			throw new RpcException(RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(), request);
		} catch (Exception e) {
			throw new RpcException("请求异常", e, RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(), request);
		}
	}
//	public String request(String request, ConsumerConfig consumerConfig) throws RpcException {
//		HttpPost post = new HttpPost(consumerConfig.getUrl());
//		post.setHeader("Connection", "Keep-Alive");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("data", request));
//		try {
//			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//			HttpResponse response = httpClient.execute(post);
//			if(response.getStatusLine().getStatusCode() == 200) {
//				return EntityUtils.toString(response.getEntity(), "UTF-8");
//			}
//			throw new RpcException(RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(), request);
//		} catch (Exception e) {
//			throw new RpcException("请求异常", e, RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(), request);
//		}
//	}

	//响应
	public void response(String response, OutputStream os) throws RpcException {
		try {
			os.write(response.getBytes("UTF-8"));
			os.flush();
		} catch (Exception e) {
			throw new RpcException("响应异常", e, RpcExceptionCodeEnum.INVOKE_RESPONSE_ERROR.getCode(), response);
		}
	}

	public static HttpClient getHttpClient() {
		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
		//连接池最大连接数
		manager.setMaxTotal(CommonPreperty.getIntProperty(CommonConstant.HTTP_MAX_CONNECTION_NUM));
		//默认设置route连接数
		manager.setDefaultMaxPerRoute(CommonPreperty.getIntProperty(CommonConstant.DEFAULT_MAX_PER_ROUTE));
		//指定专门的route，设置最大连接数
		HttpHost httpHost = new HttpHost(CommonPreperty.getStringProperty(CommonConstant.HTTP_HOST), 
				CommonPreperty.getIntProperty(CommonConstant.HTTP_HOST_PORT));
		manager.setMaxPerRoute(new HttpRoute(httpHost), CommonPreperty.getIntProperty(CommonConstant.MAX_PER_ROUTE));
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(500).build();
		return HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(requestConfig).build();
	}
}
