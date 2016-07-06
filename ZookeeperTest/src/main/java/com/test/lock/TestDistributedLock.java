package com.test.lock;

import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class TestDistributedLock {
	
	public static void main(String[] args) {
		final ZkClientExt client1 = new ZkClientExt("192.168.20.3:2181", 10000, 10000, new BytesPushThroughSerializer());
		final SimpleDistributedLockMutex mutex1 = new SimpleDistributedLockMutex(client1, "/Mutex");
		final ZkClientExt client2 = new ZkClientExt("192.168.20.3:2181", 10000, 10000, new BytesPushThroughSerializer());
		final SimpleDistributedLockMutex mutex2 = new SimpleDistributedLockMutex(client2, "/Mutex");
		
		try {
			mutex1.acquire();
			System.out.println("Client1 locked");
			Thread chlientThread = new Thread(new Runnable() {
				
				public void run() {
					try {
						mutex2.acquire();
						System.out.println("Client2 locked");
						mutex2.release();
						System.out.println("Client2 release Lock");
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			chlientThread.start();
			Thread.sleep(5000);
			mutex1.release();
			System.out.println("Client1 release lock");
			chlientThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
