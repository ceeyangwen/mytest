package com.test.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class TestDistributedBlockingQueue {
	
	public static void main(String[] args) throws Exception {
		ZkClient client = new ZkClient("192.168.20.3:2181", 10000, 10000, new SerializableSerializer());
		final DistributedBlockingQueue<User> queue = new DistributedBlockingQueue<User>(client, "/Queue");
		
		final User user1 = new User();
		user1.setName("test3");
		user1.setId("3");
		
		final User user2 = new User();
		user2.setName("test4");
		user2.setId("4");
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		int delayTime = 5;
		
		executor.schedule(new Runnable() {
			
			public void run() {
				try {
					queue.offer(user1);
					queue.offer(user2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}, delayTime, TimeUnit.SECONDS);
		
		System.out.println("ready poll");
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}

}
