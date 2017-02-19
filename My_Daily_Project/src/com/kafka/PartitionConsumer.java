package com.kafka;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.cluster.Broker;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

public class PartitionConsumer {
	
	public static void main(String[] args) throws Exception {
		PartitionConsumer consumer = new PartitionConsumer();
		List<String> seeds = new ArrayList<>();
		seeds.add("192.168.20.3");
		consumer.run(Long.MAX_VALUE, "test-20161009", 0, seeds, 9092);
	}
	
	private List<String> m_replicaBroker = new ArrayList<String>();
	
	public void run(long maxReads, String topic, int partition, List<String> seedBrokers, int port) throws Exception{
		PartitionMetadata metadata = findLeader(seedBrokers, port, topic, partition);
		if(metadata == null) {
			System.out.println("Cannot find metadata");
			return;
		}
		if(metadata.leader() == null) {
			System.out.println("Cannot find leader");
			return;
		}
		String leaderBroker = metadata.leader().host();
		String clientName = "Client_" + topic + "_" + partition;
		
		SimpleConsumer consumer = new SimpleConsumer(leaderBroker, port, 10000, 64 * 1024, clientName);
		long readOffset = getLastOffset(consumer, topic, partition, kafka.api.OffsetRequest.LatestTime(), clientName);
		System.out.println("The Start Offset : " + readOffset);
		int numError = 0;
		while(maxReads > 0) {
			if(consumer == null) {
				consumer = new SimpleConsumer(leaderBroker, port, 10000, 64 * 1024, clientName);
			}
			FetchRequest req = new FetchRequestBuilder().clientId(clientName).addFetch(topic, partition, readOffset, 10000).build();
			FetchResponse res = consumer.fetch(req);
			if(res.hasError()) {
				numError ++;
				short code = res.errorCode(topic, partition);
				System.out.println("fetch error " + code);
				if(numError > 5)
					break;
				if(code == ErrorMapping.OffsetOutOfRangeCode()) {
					readOffset = getLastOffset(consumer, topic, partition, kafka.api.OffsetRequest.LatestTime(), clientName);
					continue;
				}
				consumer.close();
				consumer = null;
				leaderBroker = findNewLeader(leaderBroker, topic, partition, port);
				continue;
			}
			numError = 0;
			long numRead = 0;
			for(MessageAndOffset msgOffset : res.messageSet(topic, partition)) {
				long currentOffset = msgOffset.offset();
				if(currentOffset < readOffset) {
					continue;
				}
				readOffset = msgOffset.nextOffset();
				ByteBuffer buf = msgOffset.message().payload();
				byte[] bytes = new byte[buf.limit()];
				buf.get(bytes);
				System.out.println(String.valueOf(msgOffset.offset()) + ":" + new String(bytes, "UTF-8"));
				numRead++;
				maxReads--;
			}
			if(numRead == 0) {
				Thread.sleep(1000);
			}
		}
		if(consumer != null) {
			consumer.close();
		}
	}
	
	private String findNewLeader(String oldLeader, String topic, int partition, int port) throws Exception {
		for(int i = 0; i < 3; i++) {
			boolean goToSleep = false;
			PartitionMetadata metadata = findLeader(m_replicaBroker, port, topic, partition);
			if(metadata == null || metadata.leader() == null || (oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0)) {
				goToSleep = true;
			} else {
				return metadata.leader().host();
			}
			if(goToSleep) {
				Thread.sleep(1000);
			}
		}
		System.out.println("Cannot find new leader");
		throw new Exception("Cannot find new leader");
	}
	
	private long getLastOffset(SimpleConsumer consumer, String topic, int partition, long time, String clientname) {
		TopicAndPartition topicAndPart = new TopicAndPartition(topic, partition);
		Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<>();
		requestInfo.put(topicAndPart, new PartitionOffsetRequestInfo(time, 1));
		OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientname);
		OffsetResponse response = consumer.getOffsetsBefore(request);
		
		if(response.hasError()) {
			System.out.println("error offset");
			return 0;
		}
		long[] offsets = response.offsets(topic, partition);
		return offsets[0];
	}
	
	private PartitionMetadata findLeader(List<String> seedBrokers, int port, String topic, int partition) {
		PartitionMetadata result = null;
		loop:
			for(String seed : seedBrokers) {
				SimpleConsumer consumer = null;
				try {
					consumer = new SimpleConsumer(seed, port, 10000, 64 * 1024, "leaderLookup");
					List<String> topics = Collections.singletonList(topic);
					TopicMetadataRequest request = new TopicMetadataRequest(topics);
					TopicMetadataResponse response = consumer.send(request);
					List<TopicMetadata> metaDatas = response.topicsMetadata();
					for(TopicMetadata meta : metaDatas) {
						for(PartitionMetadata part : meta.partitionsMetadata()) {
							if(part.partitionId() == partition) {
								result = part;
								break loop;
							}
						}
					}
				} catch (Exception e) {
				} finally {
					if(consumer != null) {
						consumer.close();
					}
				}
			}
		if(result != null) {
			m_replicaBroker.clear();
			for(Broker replica : result.replicas()) {
				m_replicaBroker.add(replica.host());
			}
		}
		return result;
	}

}
