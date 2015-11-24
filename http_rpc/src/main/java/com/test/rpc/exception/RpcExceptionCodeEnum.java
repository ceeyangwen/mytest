package com.test.rpc.exception;

/**
 * 定义常见异常信息
 * @author GavinCee
 *
 */
public enum RpcExceptionCodeEnum {
	
	DATA_PARSER_ERROR("DATA_PARSER_ERROR", "数据转换异常"),
	NO_BEAN_FOUND("NO_BEAN_FOUND", "没有找到bean对象"),
	INVOKE_REQUEST_ERROR("INVOKE_REQUEST_ERROR", "rpc请求异常"),
	INVOKE_RESPONSE_ERROR("INVOKE_RESPONSE_ERROR", "rpc响应异常"),
	;
	
	private String code;
	private String msg;
	
	RpcExceptionCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
