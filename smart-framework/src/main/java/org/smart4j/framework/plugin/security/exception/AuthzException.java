package org.smart4j.framework.plugin.security.exception;

/**
 * 授权异常（当权限无效的时候抛出）
 * @author GavinCee
 *
 */
public class AuthzException extends Exception{

	public AuthzException() {
		super();
	}

	public AuthzException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthzException(String message) {
		super(message);
	}

	public AuthzException(Throwable cause) {
		super(cause);
	}

}
