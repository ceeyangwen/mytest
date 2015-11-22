package org.smart4j.framework.plugin.rest;

import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.StringUtil;

/**
 * REST Servlet
 * @author GavinCee
 *
 */
@WebServlet(urlPatterns = RestConstant.SERVLET_URL, loadOnStartup = 0)
public class RestServlet extends CXFNonSpringServlet{

	@Override
	protected void loadBus(ServletConfig sc) {
		//初始化CXF总线
		super.loadBus(sc);
		Bus bus = getBus();
		BusFactory.setDefaultBus(bus);
		//发布REST服务
		publishRestService();
	}
	
	private void publishRestService() {
		//遍历所有标注REST注解的类
		Set<Class<?>> restClassSet = ClassHelper.getClassSetByAnnotation(Rest.class);
		if(CollectionUtil.isNotEmpty(restClassSet)) {
			for(Class<?> restClass : restClassSet) {
				//获取地址
				String address = getAddress(restClass);
				//发布服务
				RestHelper.publishService(address, restClass);
			}
		}
	}
	
	private String getAddress(Class<?> restClass) {
		String address;
		String value = restClass.getAnnotation(Rest.class).value();
		if(StringUtil.isNotEmpty(value)) {
			address = value;
		} else {
			address = restClass.getSimpleName();
		}
		if(!address.startsWith("/")) {
			address = "/" + address;
		}
		address = address.replaceAll("\\/+", "/");
		return address;
	}
}
