package org.smart4j.framework.plugin.security.impl;

import java.util.Set;

import org.smart4j.framework.plugin.security.SmartSecurity;

/**
 * 应用安全控制
 * @author GavinCee
 *
 */
public class AppSecurty implements SmartSecurity {

	public String getPassword(String username) {
		String sql = "";
		return null;
	}

	public Set<String> getRoleNameSet(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getPermissionNameSet(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

}
