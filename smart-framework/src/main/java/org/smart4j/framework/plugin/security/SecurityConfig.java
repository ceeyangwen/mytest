package org.smart4j.framework.plugin.security;

import java.util.Properties;

import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.util.PropsUtil;
import org.smart4j.framework.util.ReflectionUtil;

/**
 * 从配置文件中获取相关属性
 * @author GavinCee
 *
 */
public class SecurityConfig {
	
	private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

	public static String getRealms() {
		return PropsUtil.getString(CONFIG_PROPS, SecurityConstant.REALMS);
	}
	
	public static SmartSecurity getSmartSecurity() throws ClassNotFoundException {
		String className = PropsUtil.getString(CONFIG_PROPS, SecurityConstant.SMART_SECURITY);
		return (SmartSecurity)ReflectionUtil.newInstance(className);
	}
	
	public static String getJdbcAuthcQuery() {
		return PropsUtil.getString(CONFIG_PROPS, SecurityConstant.JDBC_AUTHC_QUERY);
	}
	
	public static String getJdbcRolesQuery() {
		return PropsUtil.getString(CONFIG_PROPS, SecurityConstant.JDBC_ROLES_QUERY);
	}
	
	public static String getJdbcPermissionQuery() {
		return PropsUtil.getString(CONFIG_PROPS, SecurityConstant.JDBC_PERMISSIONS_QUERY);
	}
	
	public static boolean isCacheable() {
		return PropsUtil.getBoollean(CONFIG_PROPS, SecurityConstant.CACHE);
	}
}
