package com.test.rpc.container;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-*.xml");
		context.start();
		//使主线程等待
		CountDownLatch latch = new CountDownLatch(1);
		latch.await();
	}
	
}
