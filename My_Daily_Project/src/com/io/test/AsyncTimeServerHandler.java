package com.io.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {
	
	CountDownLatch latch;
	
	AsynchronousServerSocketChannel asyncServerChannel;
	
	public AsyncTimeServerHandler(int port) {
		try {
			asyncServerChannel = AsynchronousServerSocketChannel.open();
			asyncServerChannel.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("The time server start in port : " + port);
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doAccept() {
		asyncServerChannel.accept(this, new AcceptCompletionHandler());
	}
	
}