package com.test.master;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

/**
 * 
 * @author GavinCee
 *
 */
public class WorkServer {

	// 记录运行状态
	private volatile boolean running = false;

	// zookeeper客户端
	private ZkClient zkClient;

	// 选举节点目录
	private static final String MASTER = "/master";

	// 监听者
	private IZkDataListener dataListener;

	// 当前服务器数据
	private RunningData serverData;

	// master节点数据
	private RunningData masterData;
	
	private ScheduledExecutorService delayExecutor = Executors.newScheduledThreadPool(1);
	
	//应对网络抖动，延迟5秒争抢
	private int delayTime = 5;

	public WorkServer(RunningData data) {
		this.serverData = data;
		this.dataListener = new IZkDataListener() {

			public void handleDataDeleted(String arg0) throws Exception {
//				takeMaster();
				//处理网路抖动的情况
				if(masterData != null && masterData.getName().equals(serverData.getName())) {
					takeMaster();
				} else {
					delayExecutor.schedule(new Runnable() {
						public void run() {
							takeMaster();
						}
					}, delayTime, TimeUnit.SECONDS);
				}
			}

			public void handleDataChange(String arg0, Object arg1) throws Exception {

			}
		};
	}

	public void start() throws Exception {
		// 如果已经在运行了就抛出异常
		if (running) {
			throw new Exception("server has started");
		}
		running = true;
		// 监听节点的数据
		zkClient.subscribeDataChanges(MASTER, dataListener);
		takeMaster();
	}

	public void stop() throws Exception {
		// 如果已经停止运行了就抛出异常
		if (!running) {
			throw new Exception("server has started");
		}
		running = false;
		delayExecutor.shutdown();
		zkClient.unsubscribeDataChanges(MASTER, dataListener);
		releaseMaster();
	}

	// 获取master
	private void takeMaster() {
		if(!running)
			return;
		try {
//			System.out.println(serverData.getName() + " take master");
			zkClient.create(MASTER, serverData, CreateMode.EPHEMERAL);
			masterData = serverData;
			System.out.println(serverData.getName() + " is master!!");
			//模拟释放master
			delayExecutor.schedule(new Runnable() {
				public void run() {
					if(checkMaster()) {
						releaseMaster();
					}
				}
			}, delayTime, TimeUnit.SECONDS);
		} catch(ZkNodeExistsException existExc) {
			RunningData runData = zkClient.readData(MASTER, true);
			//如果没有读到说明已经挂了
			if(runData == null) {
				takeMaster();
			} else {
				masterData = runData;
			}
		} catch(Exception e) {
			//其他异常不考虑
		}
	}

	private void releaseMaster() {
		if(checkMaster()) {
			zkClient.delete(MASTER);
		}
	}

	private boolean checkMaster() {
		try {
			//如果数据相等说明是master节点
			RunningData data = zkClient.readData(MASTER);
			masterData = data;
			if(masterData.getName().equals(serverData.getName())) {
				return true;
			}
			return false;
		} catch(ZkNodeExistsException e1) {
			//节点不存在，一定不是master
			return false;
		} catch (ZkInterruptedException e2) {
			//终端后重试
			return checkMaster();
		} catch (Exception e) {
			return false;
		}
	}

	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}

}
