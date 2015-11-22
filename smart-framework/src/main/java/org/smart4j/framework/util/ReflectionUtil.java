package org.smart4j.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.plugin.security.SmartSecurity;

/**
 * 映射工具类
 * @author GavinCee
 *
 */
public class ReflectionUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);
	
	//实例化对象类
	public static Object newInstance(Class<?> cls) {
		Object instance = null;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			LOGGER.error("new instance fail", e);
			throw new RuntimeException(e);
		}
		return instance;
	}
	
	//调用方法
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result = null;
		try {
			method.setAccessible(true);
			method.invoke(obj, args);
		} catch (Exception e) {
			LOGGER.error("invoke method fail", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	//设置成员变量值
	public static void setField(Object obj, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			LOGGER.error("set field fail", e);
			throw new RuntimeException(e);
		}
	}

	public static Object newInstance(String className) throws ClassNotFoundException {
		return newInstance(Class.forName(className));
	}
}
