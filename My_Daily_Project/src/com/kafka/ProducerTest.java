package com.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

public class ProducerTest {
	
	public static void main(String[] args) throws InterruptedException {
		//Sync
		long events = 100;
		Random rnd = new Random();
		Properties props = new Properties();
		props.put("metadata.broker.list", "192.168.20.3:9092");
		props.put("serializer.class", StringEncoder.class.getName());
		props.put("partitioner.class", SimplePartitioner.class.getName());
		//0 不等确认， 1 leader的一个副本收到消息即发回确认 -1 leader的所有副本都收到消息发回确认
		props.put("request.required.acks", "1");//异步不需要这个
//		props.put("producer.type", "async");//1 async 2 sync
		
		ProducerConfig propConfig = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<>(propConfig);
		long index = 0;
		for(long nEvent = 0; nEvent < events; nEvent++) {
			long runTime = new Date().getTime();
			String ip = "192.168.20." + rnd.nextInt(255);
			String msg = index + ",www.example.com," + ip;
			KeyedMessage<String, String> data = new KeyedMessage<String, String>("test-20161009", ip, msg);
			producer.send(data);
			System.out.println("Send Success!!!" + index);
			index++;
			Thread.sleep(3000);
		}
		producer.close();
	}

}
