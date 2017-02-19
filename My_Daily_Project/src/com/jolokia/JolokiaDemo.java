package com.jolokia;

import java.util.Map;

import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;

public class JolokiaDemo {
	
	public static void main(String[] args) throws Exception {
		J4pClient client = new J4pClient("http://localhost:8080/jolokia");
		J4pReadRequest req = new J4pReadRequest("java.lang:type=Memory", "HeapMemoryUsage");
		J4pReadResponse res = client.execute(req);
		Map<String, Object> map = res.getValue();
		String.valueOf(map.get("used"));
		int used = Integer.parseInt(String.valueOf(map.get("used")));
		System.out.println(used);
//        int max = Integer.parseInt(map.get("max"));
//        int usage = (int) (used * 100 / max);
	}

}
