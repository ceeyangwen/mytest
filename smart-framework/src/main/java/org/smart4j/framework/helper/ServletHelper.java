package org.smart4j.framework.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet助手类
 * @author GavinCee
 *
 */
public final class ServletHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);
	
	//使每个线程独自拥有一份ServletHelper实例
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<ServletHelper>();
	
	private HttpServletRequest req;
	
	private HttpServletResponse res;
	
	private ServletHelper(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
	}
	
	//初始化
	public static void init(HttpServletRequest req, HttpServletResponse res) {
		SERVLET_HELPER_HOLDER.set(new ServletHelper(req, res));
	}
	
	//销毁
	public static void destory() {
		SERVLET_HELPER_HOLDER.remove();
	}
	
	//获取request
	public static HttpServletRequest getRequest() {
		return SERVLET_HELPER_HOLDER.get().req;
	}
	
	//获取response
	public static HttpServletResponse getResponse() {
		return SERVLET_HELPER_HOLDER.get().res;
	}
	
	//获取session
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	//获取Context对象
	public static ServletContext getServletContext() {
		return getRequest().getServletContext();
	}
	
	public static void setRequestAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}
	
	public static <T> T getRequestAttribute(String key) {
		return (T)getRequest().getAttribute(key);
	}
	
	public static void removeRequestAttribute(String key) {
		getRequest().removeAttribute(key);
	}
	
	public static void sendRedirect(String location) {
		try {
			getResponse().sendRedirect(getRequest().getContextPath() + location);
		} catch (Exception e) {
			LOGGER.error("redirect fail", e);
		}
	}
	
	public static void setSessionAttribute(String key, Object value) {
		getSession().setAttribute(key, value);
	}
	
	public static <T> T getSessionAttribute(String key) {
		return (T)getSession().getAttribute(key);
	}
	
	public static void removeSessionAttribute(String key) {
		getSession().removeAttribute(key);
	}
	
	public static void invalidateSession() {
		getSession().invalidate();
	}
}
