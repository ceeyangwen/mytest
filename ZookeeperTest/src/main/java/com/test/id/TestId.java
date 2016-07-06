package com.test.id;

import java.util.concurrent.TimeUnit;

import com.test.id.IdMaker.RemoveMethod;

public class TestId {
	
	public static void main(String[] args) throws Exception {
		IdMaker maker = new IdMaker("192.168.20.3:2181", "/IdService", "ID");
		maker.start();
		for(int i = 0; i < 10; i++) {
			System.out.println(maker.generateId(RemoveMethod.DELAY));
		}
		TimeUnit.SECONDS.sleep(5);
	}

}
