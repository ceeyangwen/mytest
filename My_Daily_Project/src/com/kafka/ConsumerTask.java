package com.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class ConsumerTask implements Runnable {
	
	private KafkaStream<byte[], byte[]> stream;
	
	private int threadNum;
	
	public ConsumerTask(KafkaStream<byte[], byte[]> stream, int threadNum) {
		this.stream = stream;
		this.threadNum = threadNum;
	}

	@Override
	public void run() {
		ConsumerIterator<byte[], byte[]> iter = stream.iterator();
		while(iter.hasNext()) {
			System.out.println("Thread " + threadNum + " : " + new String(iter.next().message()));
		}
		System.out.println("shutdown thread " + threadNum);
	}
	
}
