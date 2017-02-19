package com.test.master;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkServer extends LeaderSelectorListenerAdapter implements Closeable{
	
	private static final Logger logger = LoggerFactory.getLogger(WorkServer.class);
	
	private final String name;;
	
	private final LeaderSelector leaderSelector;
	
	private RunningListener listener;
	
	public WorkServer(CuratorFramework client, String path, String name) {
		this.name = name;
		leaderSelector = new LeaderSelector(client, path, this);
		leaderSelector.autoRequeue();
	}
	
	public void start() throws Exception {
		leaderSelector.start();
		processStart(this.name);
	}

	public void takeLeadership(CuratorFramework arg0) throws Exception {
		processActiveEnter(this.name);
		try {
			TimeUnit.SECONDS.sleep(5);
		} finally {
			processActiveExit(this.name);
		}
	}

	public void close() throws IOException {
		leaderSelector.close();
		processStop(this.name);
	}
	
	private void processStop(Object context) {
		if(null != listener) {
			listener.processStop(context);
		}
	}
	
	private void processStart(Object context) {
		if(null != listener) {
			listener.processStart(context);
		}
	}
	
	private void processActiveExit(Object context) {
		if(null != listener) {
			listener.processActiveExit(context);
		}
	}
	
	private void processActiveEnter(Object context) {
		if(null != listener) {
			listener.processActiveEnter(context);
		}
	}
	
	public RunningListener getListener() {
		return listener;
	}

	public void setListener(RunningListener listener) {
		this.listener = listener;
	}

}
