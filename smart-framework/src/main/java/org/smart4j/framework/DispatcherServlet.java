package org.smart4j.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.RequestHelper;
import org.smart4j.framework.helper.ServletHelper;
import org.smart4j.framework.helper.UploadHelper;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CodecUtil;
import org.smart4j.framework.util.JsonUtil;
import org.smart4j.framework.util.ReflectionUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

import com.mysql.fabric.Response;

/**
 * 请求转发器
 * @author GavinCee
 *
 */
@WebServlet(urlPatterns="/*", loadOnStartup=0)
public class DispatcherServlet extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//初始化相关的Helper
		HelperLoader.init();
		//获取ServletContext对象用于注册Servlet
		ServletContext context = config.getServletContext();
		//注册处理JSP的Servlet
		ServletRegistration jspServlet = context.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
		//注册处理静态资源的默认servlet
		ServletRegistration defaultServlet = context.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		
		UploadHelper.init(context);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletHelper.init(req, resp);
		//获取请求方法和请求路径
		String requestMethod = req.getMethod().toLowerCase();
		String requestPath = req.getPathInfo();
		
		if(requestPath.equals("/favicon.ico")) {
			return;
		}
		
		//获取Action处理器
		Handler handler =  ControllerHelper.getHandler(requestMethod, requestPath);
		if(handler != null) {
			//获取Controller实例
			Class<?> controllerCls = handler.getControllerClass();
			Object cntrollerBean = BeanHelper.getBean(controllerCls);
			//创建请求参数对象
			Param param = null;
			if(UploadHelper.isMultipart(req)) {
				param = UploadHelper.createParam(req);
			} else {
				param = RequestHelper.createParam(req);
			}
			
			//调用Action方法
			Method method = handler.getActionMethod();
			Object result = null;
			if(param.isEmpty()) {
				result = ReflectionUtil.invokeMethod(cntrollerBean, method);
			} else {
				result = ReflectionUtil.invokeMethod(cntrollerBean, method, param);
			}
			//处理Action方法返回值
			if(result instanceof View) {
				handleViewResult((View)result, req, resp);
			} else if(result instanceof Data) {
				handleDataResult((Data)result, resp);
			}
		}
		ServletHelper.destory();
	}
	
	private void handleDataResult(Data data, HttpServletResponse res) throws IOException {
		//返回JSON数据
		Object model = data.getModel();
		if(model != null) {
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			PrintWriter writer = res.getWriter();
			String json = JsonUtil.toJson(model);
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}
	
	private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String path = view.getPath();
		if(StringUtil.isNotEmpty(path)) {
			if(path.startsWith("/")) {
				res.sendRedirect(req.getContextPath() + path);
			} else {
				Map<String, Object> model = view.getModel();
				for(Map.Entry<String, Object> entry : model.entrySet()) {
					req.setAttribute(entry.getKey(), entry.getValue());
				}
				req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, res);
			}
		}
	}
}
