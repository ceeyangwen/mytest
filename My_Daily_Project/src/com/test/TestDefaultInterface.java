package com.test;

public class TestDefaultInterface {
	
	public static void main(String[] args) {
		DefaultInterface default1 = StaticInterfaceFactory.create(DeafaultImpl::new);
		System.out.println(default1.defaultMethod());
		
		DefaultInterface default2 = StaticInterfaceFactory.create(DefaultOverride::new);
		System.out.println(default2.defaultMethod());
	}

}
