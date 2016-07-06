package com.test.lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimpleDistributedLockMutex extends BaseDistributedLock implements DistributedLock {
	
	//锁名前缀
	private static final String LOCK_NAME = "lock-";
	
	private static String basePath;
	
	private String ourLockPath;
	
	public SimpleDistributedLockMutex(ZkClientExt client, String basePath) {
		super(client, basePath, LOCK_NAME);
		this.basePath = basePath;
	}
	
	private boolean internlLock(long time, TimeUnit unit) throws Exception {
		ourLockPath = attemptLock(time, unit);
		return ourLockPath != null;
	}

	public void acquire() throws Exception {
		if(!internlLock(-1, null)) {
			throw new IOException("连接丢失!在路径:'"+basePath+"'下不能获取锁!");
		}
	}

	public boolean acquire(long time, TimeUnit unit) throws Exception {
		return internlLock(time, unit);
	}

	public void release() throws Exception {
		releaseLock(ourLockPath);
	}

}
