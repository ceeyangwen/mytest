package com.test.queue;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

public class DistributedBlockingQueue<T> extends DistributedSimpleQueue<T> {

	public DistributedBlockingQueue(ZkClient client, String path) {
		super(client, path);
	}
	
	@Override
	public T poll() throws Exception {
		while(true) {
			final CountDownLatch latch = new CountDownLatch(1);
			final IZkChildListener listener = new IZkChildListener() {
				
				public void handleChildChange(String arg0, List<String> arg1) throws Exception {
					latch.countDown();
				}
			};
			client.subscribeChildChanges(root, listener);
			try {
				T node = super.poll();
				if(node != null) {
					return node;
				} else {
					latch.await();
				}
			} catch (Exception e) {
			} finally {
				client.unsubscribeChildChanges(root, listener);
			}
		}
	}

}
