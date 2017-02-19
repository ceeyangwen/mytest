package com.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class SimplePartitioner implements Partitioner {
	
	public SimplePartitioner(VerifiableProperties prop) {
	}

	@Override
	public int partition(Object key, int partitionNum) {
		int part = 0;
		int offset = key.toString().lastIndexOf(".");
		if(offset > 0) {
			part = Integer.parseInt(key.toString().substring(offset + 1)) % partitionNum;
		}
		return part;
	}

}
