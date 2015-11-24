package com.test.rpc.exception;

/**
 * 定义异常
 * @author GavinCee
 *
 */
public class RpcException extends Throwable{

	private String code;
	
	private Object data;
	
	public RpcException(String message, Throwable cause, String code, Object data) {
		super(message, cause);
		this.code = code;
		this.data = data;
	}
	
	public RpcException(String code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
