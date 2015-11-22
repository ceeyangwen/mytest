package org.smart4j.framework.plugin.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解：
 * 判断当前用户是否已登录（包括已认证与已记住）
 * Guest:判断当前用户是否未登录（包括未认证或未记住，即访客身份）
 * Authenticated:判断当前用户是否已认证
 * HasRoles:判断当前用户是否拥有某种角色
 * HasPermissions:判断当前用户是否拥有某种权限
 * @author GavinCee
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface User {

}
