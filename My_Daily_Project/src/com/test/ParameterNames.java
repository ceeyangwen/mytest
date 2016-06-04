package com.test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterNames {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Method method = ParameterNames.class.getMethod("main", String[].class);
		for(Parameter param : method.getParameters()) {
			System.out.println("Parameters：" + param.getName());
		}
	}

}
