package com.test.rpc.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.rpc.consumer.PeopleConsumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-*.xml")
public class HttpRpcTest {

	private static final Logger LOGGER = Logger.getLogger(HttpRpcTest.class);
	
	@Resource
	private PeopleConsumer peopleConsumer;
	
	@Test
	public void test() throws InterruptedException {
		final CountDownLatch count = new CountDownLatch(10000);
		final ExecutorService exec = Executors.newFixedThreadPool(50);
		Date date = new Date();
		for(int i = 0; i < 10000; i++) {
			final int no = i + 1;
			Runnable run = new Runnable() {
				@Override
				public void run() {
					System.out.println(no + ",");
					int age = new Random().nextInt(100);
					int sex = new Random().nextInt(1);
					String res = peopleConsumer.getSpeak(age, sex);
					System.out.println(res);
					count.countDown();
				}
			};
			exec.submit(run);
		}
		count.await();
		System.out.println(new Date().getTime() - date.getTime());
		exec.shutdown();
	}
	
}
