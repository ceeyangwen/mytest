package com.test;

public class SingletonTest {
	
	private static Object instance = null;
	
	public static Object method1() {
		if(instance == null) {
			instance = new Object();
		}
		return instance;
	}
	
	public static Object method2() {
		if(instance == null) {
			synchronized (SingletonTest.class) {
				instance = new Object();
			}
		}
		return instance;
	}
	
	//double-checked locking 双重锁定失败
	public static Object method3() {
		if(instance == null) {
			synchronized (SingletonTest.class) {
				if(instance == null)
					instance = new Object();
			}
		}
		return instance;
	}

}
