package org.smart4j.framework.plugin.security.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.smart4j.framework.plugin.security.annotation.User;
import org.smart4j.framework.plugin.security.exception.AuthzException;
import org.smart4j.framework.proxy.AspectProxy;

/**
 * 授权注解切面
 * 实现前置增强机制
 * @author GavinCee
 *
 */
public class AuthzAnnotationAspect extends AspectProxy{

	/**
	 * 定义一个基于授权功能的注解类数组
	 */
	private static final Class[] ANNOTATION_CLASS_ARRAY = {
			User.class
	};
	
	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
		//从目标类与目标方法中获取相应的注解
		Annotation annotation = getAnnotation(cls, method);
		if(annotation != null) {
			Class<?> annotationType = annotation.annotationType();
			if(annotationType.equals(User.class)) {
				handleUser();
			}
		}
	}
	
	private Annotation getAnnotation(Class<?> cls, Method method) {
		for(Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY) {
			//首先判断目标方法上是否带有授权注解
			if(method.isAnnotationPresent(annotationClass)) {
				return method.getAnnotation(annotationClass);
			}
			//然后判断目标类上是否带有授权注解
			if(cls.isAnnotationPresent(annotationClass)) {
				return cls.getAnnotation(annotationClass);
			}
		}
		return null;
	}
	
	private void handleUser() throws AuthzException {
		Subject currentUser = SecurityUtils.getSubject();
		PrincipalCollection principals = currentUser.getPrincipals();
		if(null == principals || principals.isEmpty()) {
			throw new AuthzException("当前用户尚未登录");
		}
	}
}
