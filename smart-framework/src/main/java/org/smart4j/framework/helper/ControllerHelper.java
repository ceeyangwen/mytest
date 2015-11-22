package org.smart4j.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;

/**
 * 控制器助手类
 * 在ControllerHelper中封装了一个ActionMap，通过它来存放Request与Handler之间的映射
 * 关系，然后通过ClassHelper来获取所有带有Controller注解的类，接着遍历这些Controller类
 * 从Action注解中提取URL，最后初始化Request与Handler之间的映射关系
 * @author GavinCee
 *
 */
public class ControllerHelper {
 
	public final static Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();
	
	static {
		//获取所有的Controller类
		Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
		if(CollectionUtil.isNotEmpty(controllerSet)) {
			for(Class<?> controllerClass : controllerSet) {
				//获取Controller中定义的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods)) {
					for(Method method : methods) {
						if(method.isAnnotationPresent(Action.class)) {
							//从Action注解中获取URL映射规则
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							//验证URL映射规则
							if(mapping.matches("\\w+:/\\w*")) {
								String[] array = mapping.split(":");
								if(ArrayUtil.isNotEmpty(array) && array.length == 2) {
									//获取请求方法与陆军
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									ACTION_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}
	
	//获取Handler
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
	
}
