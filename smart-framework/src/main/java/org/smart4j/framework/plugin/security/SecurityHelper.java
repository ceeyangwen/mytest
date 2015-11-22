package org.smart4j.framework.plugin.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.plugin.security.exception.AuthcException;

/**
 * Security助手类
 * @author GavinCee
 *
 */
public class SecurityHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);

	//登录
	public static void login(String username, String password) throws AuthcException {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				currentUser.login(token);
			} catch (Exception e) {
				LOGGER.error("login fail", e);
				throw new AuthcException(e);
			}
		}
	}

	//注销
	public static void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null) {
			currentUser.logout();
		}
	}
	
}
