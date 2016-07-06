package com.test.lock;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

public class BaseDistributedLock {
	
	private final ZkClient client;
	
	private final String path;
	
	private final String basePath;
	
	private final String lockname;
	
	private static final Integer MAX_RETRY_NUM = 10;

	public BaseDistributedLock(ZkClient client, String basePath, String lockname) {
		super();
		this.client = client;
		this.basePath = basePath;
		this.lockname = lockname;
		this.path = basePath.concat("/").concat(lockname);
	}
	
	private void deleteOurPath(String ourPath) throws Exception {
		client.delete(ourPath);
	}
	
	private String createLockNode(ZkClient client, String path) throws Exception {
		return client.createEphemeralSequential(path, null);
	}
	
	private String getLockNodeNumber(String str, String lockname) {
		int index = str.lastIndexOf(lockname);
		if(index > 0) {
			index += lockname.length();
			return index <= str.length() ? str.substring(index) : "";
		}
		return str;
	}
	
	private boolean waitToLock(long startMills, Long millToWait, String ourPath) throws Exception {
		boolean haveTheLock = false;
		boolean doDelete = false;
		try {
			while(!haveTheLock) {
				List<String> children = getSortedChildren();
				String sequenceNodeName = ourPath.substring(basePath.length() + 1);
				
				int ourIndex = children.indexOf(sequenceNodeName);
				if(ourIndex < 0) {
					throw new ZkNoNodeException("节点没有找到:" + sequenceNodeName);
				}
				
				boolean isGetTheLock = ourIndex == 0;
				String pathToWatch = isGetTheLock ? null : children.get(ourIndex - 1);
				if(isGetTheLock) {
					haveTheLock = true;
				} else {
					String previousSequencePath = basePath.concat("/").concat(pathToWatch);
					final CountDownLatch countDown = new CountDownLatch(1);
					final IZkDataListener listener = new IZkDataListener() {
						
						public void handleDataDeleted(String arg0) throws Exception {
							countDown.countDown();
						}
						
						public void handleDataChange(String arg0, Object arg1) throws Exception {
							// TODO Auto-generated method stub
							
						}
					};
					
					try {
						client.subscribeDataChanges(previousSequencePath, listener);
						if(millToWait != null) {
							millToWait -= (System.currentTimeMillis() - startMills);
							startMills = System.currentTimeMillis();
							if(millToWait <= 0) {
								doDelete = true;
								break;
							}
							countDown.await(millToWait, TimeUnit.MILLISECONDS);
						} else {
							countDown.await();
						}
					} catch (ZkNoNodeException e) {
						
					} finally {
						client.unsubscribeDataChanges(previousSequencePath, listener);
					}
				}
			}
		} catch (Exception e) {
			//发生异常，删除节点
			doDelete = true;
			throw e;
		} finally {
			if(doDelete) {
				deleteOurPath(ourPath);
			}
		}
		return haveTheLock;
	}
	
	private List<String> getSortedChildren() throws Exception {
		List<String> children = client.getChildren(basePath);
		Collections.sort(children, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return getLockNodeNumber(s1, lockname).compareTo(getLockNodeNumber(s2, lockname));
			};
		});
		return children;
	}
	
	protected void releaseLock(String lockPath) throws Exception {
		deleteOurPath(lockPath);
	}
	
	protected String attemptLock(long time, TimeUnit unit) throws Exception {
		final long startMills = System.currentTimeMillis();
		final Long millToWait = (unit != null) ? unit.toMillis(time) : null;
		String ourPath = null;
		boolean hasTheLock = false;
		boolean isDone = false;
		int retryCount = 0;
		while(!isDone) {
			isDone = true;
			try {
				ourPath = createLockNode(client, path);
				hasTheLock = waitToLock(startMills, millToWait, ourPath);
			} catch (ZkNoNodeException e) {
				if(retryCount++ < MAX_RETRY_NUM) {
					isDone = false;
				} else {
					throw e;
				}
			}
		}
		if(hasTheLock) {
			return ourPath;
		}
		return null;
	}

}
