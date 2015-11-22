package org.smart4j.framework.plugin.security.exception;

/**
 * 认证异常（当非法访问的时候抛出）
 * @author GavinCee
 *
 */
public class AuthcException extends Exception{

	public AuthcException() {
		super();
	}

	public AuthcException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthcException(String message) {
		super(message);
	}

	public AuthcException(Throwable cause) {
		super(cause);
	}

}
