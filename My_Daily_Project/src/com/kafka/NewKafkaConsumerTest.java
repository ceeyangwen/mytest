package com.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**  
 * @author GavinCee  
 * @date 2016年10月10日  
 *
 */
public class NewKafkaConsumerTest {
	
	public static void main(String[] args) {
		 Properties props = new Properties();
	     props.put("bootstrap.servers", "192.168.20.3:9092");
	     props.put("group.id", "group1");
	     props.put("enable.auto.commit", "false");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     
	     consumer.subscribe(Arrays.asList("test-20161009"));
	     
	     while(true) {
	    	ConsumerRecords<String, String> records = consumer.poll(100);
	    	for(ConsumerRecord<String, String> record : records) {
	    		System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
	    		System.out.println();
	    	}
	    	
	     }
//	     consumer.close();
	}

}
