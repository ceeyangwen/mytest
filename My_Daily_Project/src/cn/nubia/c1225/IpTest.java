package cn.nubia.c1225;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**  
 * @author GavinCee  
 * @date 2016年12月25日  
 *
 */
public class IpTest {
	
	public static void main(String[] args) {
		
	}
	
	public static Map<String, Integer> serverWeightMap = new HashMap<>();
	
	static {
		serverWeightMap.put("192.168.1.100", 1);
		serverWeightMap.put("192.168.1.101", 1);
		serverWeightMap.put("192.168.1.102", 4);
		serverWeightMap.put("192.168.1.103", 1);
		serverWeightMap.put("192.168.1.104", 1);
		serverWeightMap.put("192.168.1.105", 3);
		serverWeightMap.put("192.168.1.106", 1);
		serverWeightMap.put("192.168.1.107", 2);
		serverWeightMap.put("192.168.1.108", 1);
		serverWeightMap.put("192.168.1.109", 1);
		serverWeightMap.put("192.168.1.110", 1);
	}
	
	private static Integer pos = 0;
	
	/**
	 * Roung Robin轮询
	 */
	public static String roundRobin() {
		//重建一个map，避免并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(serverWeightMap);
		
		//取得iplist
		Set<String> ipSet = serverMap.keySet();
		ArrayList<String> ipList = new ArrayList<>();
		ipList.addAll(ipSet);
		
		String server = null;
		synchronized (pos) {
			if(pos > ipList.size())
				pos = 0;
			server = ipList.get(pos);
			pos++;
		}
		return server;
	}
	
	public static String random() {
		//重建一个map，避免并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(serverWeightMap);
				
		//取得iplist
		Set<String> ipSet = serverMap.keySet();
		ArrayList<String> ipList = new ArrayList<>();
		ipList.addAll(ipSet);
		
		Random random = new Random();
		int pos = random.nextInt(ipList.size());
		return ipList.get(pos);
	}
	
	public static String hash(String sourceIp) {
		//重建一个map，避免并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(serverWeightMap);
						
		//取得iplist
		Set<String> ipSet = serverMap.keySet();
		ArrayList<String> ipList = new ArrayList<>();
		ipList.addAll(ipSet);
		
		int hashCode = sourceIp.hashCode();
		int serverSize = ipList.size();
		int pos = hashCode % serverSize;
		
		return ipList.get(pos);
	}
	
	public static String weightRoundRobin() {
		//重建一个map，避免并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(serverWeightMap);
								
		//取得iplist
		Set<String> ipSet = serverMap.keySet();
		Iterator<String> iter = ipSet.iterator();
		
		ArrayList<String> ipList = new ArrayList<>();
		
		while(iter.hasNext()) {
			String server = iter.next();
			int weight = serverMap.get(server);
			for(int i = 0; i < weight; i++) {
				ipList.add(server);
			}
		}
		
		String server = null;
		synchronized (pos) {
			if(pos > ipList.size())
				pos = 0;
			server = ipList.get(pos);
			pos++;
		}
		return server;
	}
	

}
