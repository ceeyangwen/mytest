package com.lucence.test;

/**
 * 返回类所在的路径
 * @author GavinCee
 *
 */
public class ClassUtil {
	
	public static String getClassPath(Class<?> cls) {
		return cls.getResource("").getPath().replaceAll("%20", " ");
	}
	
	public static String getClassRootPath(Class<?> cls) {
		return cls.getResource("/").getPath().replaceAll("%20", " ");
	}

}
