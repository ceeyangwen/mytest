package org.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.util.ReflectionUtil;

/**
 * 相当于一个Bean容器 
 * @author GavinCee
 *
 */
public class BeanHelper {

	//定义Bean映射（用于存放Bean类和Bean类实例的映射关系）
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();
	
	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for(Class<?> beanClass : beanClassSet) {
			Object obj = ReflectionUtil.newInstance(beanClass);
			BEAN_MAP.put(beanClass, obj);
		}
	}
	
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}
	
	public static <T> T getBean(Class<?> cls) {
		if(!BEAN_MAP.containsKey(cls)) {
			throw new RuntimeException("can not get bean by class : " + cls);
		}
		return (T)BEAN_MAP.get(cls);
	}
	
	//设置添加Bean实例
	public static void setBean(Class<?> cls, Object obj) {
		BEAN_MAP.put(cls, obj);
	}
}
