package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool {
	
	public static void main(String[] args) {
		testCachedThreadPool();
	}
	
	public static void testSingleThreadExecutor() {
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.shutdown();
	}
	
	public static void testFixedThreadPool() {
		ExecutorService pool = Executors.newFixedThreadPool(2);
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.shutdown();
	}
	
	public static void testCachedThreadPool() {
		ExecutorService pool = Executors.newCachedThreadPool();
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.shutdown();
	}

}

