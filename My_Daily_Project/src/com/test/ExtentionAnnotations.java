package com.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解扩展
 * @author GavinCee
 *
 */
public class ExtentionAnnotations {
	/**
	 * ElementType.TYPE_USE和ElementType.TYPE_PARAMETER是两个新添加的用于描述适当的注解上下文的元素类型
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target( {ElementType.TYPE_USE, ElementType.TYPE_PARAMETER} )
	public @interface TestAnnotation{
		
	}
	
	public static class SubClass<@TestAnnotation T> extends @TestAnnotation Object {
		public void method() throws @TestAnnotation Exception {
			
		}
	}
	
	public static void main(String[] args) {
		SubClass<String> cls = new @TestAnnotation SubClass<>();
		@TestAnnotation List<@TestAnnotation String> strings = new ArrayList<>();
	}

}
