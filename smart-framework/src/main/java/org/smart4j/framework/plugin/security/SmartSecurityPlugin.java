package org.smart4j.framework.plugin.security;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

public class SmartSecurityPlugin implements ServletContainerInitializer {

	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		//设置初始化参数
		ctx.setInitParameter("shiroConfigLocations", "classpath:smart-security.ini");
		//注册listener
		ctx.addListener(EnvironmentLoaderListener.class);
		//注册filter
		FilterRegistration.Dynamic smartSecurityFilter = ctx.addFilter("SmartSecurityFilter", SmartSecurityFilter.class);
		smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
	}

}
