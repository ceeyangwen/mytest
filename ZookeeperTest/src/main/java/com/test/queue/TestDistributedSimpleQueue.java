package com.test.queue;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class TestDistributedSimpleQueue {
	
	public static void main(String[] args) throws Exception {
		ZkClient client = new ZkClient("192.168.20.3:2181", 10000, 10000, new SerializableSerializer());
		DistributedSimpleQueue<User> queue = new DistributedSimpleQueue<User>(client, "/Queue");
		
		User user1 = new User();
		user1.setName("test1");
		user1.setId("1");
		
		User user2 = new User();
		user2.setName("test2");
		user2.setId("2");
		
		queue.offer(user1);
		queue.offer(user2);
		
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}

}
