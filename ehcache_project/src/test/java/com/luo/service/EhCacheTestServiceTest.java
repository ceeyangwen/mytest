package com.luo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.luo.baseTest.SpringTestCase;

public class EhCacheTestServiceTest extends SpringTestCase {
	
	@Autowired  
    private EhCacheTestService ehCacheTestService;
	
	@Test  
    public void getTimestampTest() throws InterruptedException{  
        System.out.println("��һ�ε��ã�" + ehCacheTestService.getTimestamp("param"));
        Thread.sleep(2000);
        System.out.println("2��֮����ã�" + ehCacheTestService.getTimestamp("param"));
        Thread.sleep(11000);
        System.out.println("�ٹ�11��֮����ã�" + ehCacheTestService.getTimestamp("param"));
    } 
}
