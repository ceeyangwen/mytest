package com.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class GroupConsumer extends Thread {
	
	private final ConsumerConnector consumer;
	
	private final String topic;
	
	private ExecutorService executor;
	
	public GroupConsumer(String zookeeper, String groupid, String topic) {
		consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(zookeeper, groupid));
		this.topic = topic;
	}
	
	public void run(int threadNum) {
		executor = Executors.newFixedThreadPool(threadNum);
		Map<String, Integer> topicMap = new HashMap<>();
		topicMap.put(topic, new Integer(threadNum));
		consumer.commitOffsets();
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
		int index = 0;
		for(KafkaStream<byte[], byte[]> stream : streams) {
			executor.submit(new ConsumerTask(stream, index));
			index++;
		}
	}
	
	public void shutdown() {
		if(consumer != null)
			consumer.shutdown();
		if(executor != null)
			executor.shutdown();
		try {
			if(!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS)){
				System.out.println("shutdown fail!!!");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static ConsumerConfig createConsumerConfig(String zookeeper, String groupid) {
		Properties prop = new Properties();
		prop.put("zookeeper.connect", zookeeper);
		prop.put("group.id", groupid);
		prop.put("zookeeper.session.timeout.ms", "10000");
		prop.put("zookeeper.sync.time.ms", "2000");
		prop.put("auto.commit.interval.ms", "1000");
		return new ConsumerConfig(prop);
	}
	
	public static void main(String[] args) {
		GroupConsumer groupConsumer = new GroupConsumer("192.168.20.3:2181", "group1", "test-20161009");
		groupConsumer.run(1);
	}

}
