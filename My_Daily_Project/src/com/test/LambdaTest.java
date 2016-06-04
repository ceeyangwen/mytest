package com.test;

import java.util.Arrays;

public class LambdaTest {
	
	public static void main(String[] args) {
		Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));
		
		Thread thread = new Thread(() -> {
			System.out.println("Hello World");
		});
		
		thread.start();
	}

}
