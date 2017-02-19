package com.lucence.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * 
 * @author GavinCee
 *
 */
public abstract class CrawlBasse {

	private static Logger log = Logger.getLogger(CrawlBasse.class);

	private String pageSourceCode = "";

	private Header[] responseHeaders = null;

	private static int connectTimeout = 10000;

	private static int readTimeout = 10000;

	private static int maxConnectTime = 3;

	private static String charsetName = "iso-8869-1";

	private static MultiThreadedHttpConnectionManager httpManager = new MultiThreadedHttpConnectionManager();

	private static HttpClient client = new HttpClient(httpManager);

	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeout);
		client.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
		client.getParams().setContentCharset(charsetName);
	}
	
	public boolean readPageByGet(String url, HashMap<String, String> params, String charset) {
		GetMethod method = createGetMethod(url, params);
		return readPage(method, charset, url);
	}
	
	public boolean readPageByPost(String url, HashMap<String, String> params, String charset) {
		PostMethod method = createPostMethod(url, params);
		return readPage(method, charset, url);
	}
	
	private boolean readPage(HttpMethod method, String charset, String url) {
		int n = maxConnectTime;
		while( n > 0) {
			try {
				if(client.executeMethod(method) != HttpStatus.SC_OK) {
					log.info("cant connect " + url);
					n--;
				} else {
					responseHeaders = method.getRequestHeaders();
					InputStream is = method.getResponseBodyAsStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));
					StringBuffer buf = new StringBuffer();
					String line = "";
					while((line = reader.readLine()) != null) {
						buf.append(line);
						buf.append("\n");
					}
					pageSourceCode = buf.toString();
					InputStream in = new ByteArrayInputStream(pageSourceCode.getBytes());
					charset = CharsetUtil.getStreamCharset(in, charset);
					if(!charsetName.toLowerCase().equals(charset.toLowerCase())) {
						pageSourceCode = new String(pageSourceCode.getBytes(charsetName), charset);
					}
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				n--;
			}
		}
		return false;
	}
	
	private GetMethod createGetMethod(String url, HashMap<String, String> params) {
		GetMethod method = new GetMethod(url);
		if(params == null) {
			return method;
		}
		Iterator<java.util.Map.Entry<String, String>> iter = params.entrySet().iterator();
		while(iter.hasNext()) {
			java.util.Map.Entry<String, String> entry = iter.next();
			method.addRequestHeader(entry.getKey(), entry.getValue());
		}
		return method;
	}
	
	private PostMethod createPostMethod(String url, HashMap<String, String> params) {
		PostMethod method = new PostMethod(url);
		if(params == null) {
			return method;
		}
		Iterator<java.util.Map.Entry<String, String>> iter = params.entrySet().iterator();
		while(iter.hasNext()) {
			java.util.Map.Entry<String, String> entry = iter.next();
			method.addRequestHeader(entry.getKey(), entry.getValue());
		}
		return method;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		CrawlBasse.log = log;
	}

	public String getPageSourceCode() {
		return pageSourceCode;
	}

	public void setPageSourceCode(String pageSourceCode) {
		this.pageSourceCode = pageSourceCode;
	}

	public Header[] getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Header[] responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public static int getConnectTimeout() {
		return connectTimeout;
	}

	public static void setConnectTimeout(int connectTimeout) {
		CrawlBasse.connectTimeout = connectTimeout;
	}

	public static int getReadTimeout() {
		return readTimeout;
	}

	public static void setReadTimeout(int readTimeout) {
		CrawlBasse.readTimeout = readTimeout;
	}

	public static int getMaxConnectTime() {
		return maxConnectTime;
	}

	public static void setMaxConnectTime(int maxConnectTime) {
		CrawlBasse.maxConnectTime = maxConnectTime;
	}

	public static String getCharsetName() {
		return charsetName;
	}

	public static void setCharsetName(String charsetName) {
		CrawlBasse.charsetName = charsetName;
	}

	public static MultiThreadedHttpConnectionManager getHttpManager() {
		return httpManager;
	}

	public static void setHttpManager(MultiThreadedHttpConnectionManager httpManager) {
		CrawlBasse.httpManager = httpManager;
	}

	public static HttpClient getClient() {
		return client;
	}

	public static void setClient(HttpClient client) {
		CrawlBasse.client = client;
	}

	public static void main(String[] args) {

	}

}
