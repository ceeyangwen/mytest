package cn.nubia.msg;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import sun.misc.SignalHandler;

/**  
 * @author GavinCee  
 * @date 2017年1月8日  
 *
 */
public class MsgClient {
	
	//模拟消息队列订阅者，同时4个线程处理
	private static final ThreadPoolExecutor THREAD_POOL = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
	
	//模拟消息队列生产者
	private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
	
	//用于判断是否关闭订阅
	private static volatile boolean isClose = false;
	
	private static int index = 0;
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
		producer(queue);
		consumer(queue);
	}

	//模拟消息队列生产者
	private static void producer(final BlockingQueue queue) {
		//每200ms向对了中放入一个消息
		SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				queue.offer("The Message " + (index++));
			}
		}, 0, 200, TimeUnit.MILLISECONDS);
	}
	
	//模拟消息队列消费者 生产者每秒生产5个，消费者4个小城消费1秒1个 每秒积压一个
	private static void consumer(final BlockingQueue queue) throws InterruptedException {
		while(!isClose) {
			getPoolBacklogSize();
			//从队列中拿到消息
			final String msg = (String)queue.take();
			//放入线程池处理
			if(!THREAD_POOL.isShutdown()) {
				THREAD_POOL.execute(new Runnable() {
					@Override
					public void run() {
						try {
							System.out.println("start do msg : " + msg);
							TimeUnit.SECONDS.sleep(1);
							System.out.println("end do msg : " + msg);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}
	
	//查看线程池堆积消息个数
	private static long getPoolBacklogSize() {
		long backlog = THREAD_POOL.getTaskCount() - THREAD_POOL.getCompletedTaskCount();
		System.out.println(String.format("[%s]THREAD_POOL backlog:%s", System.currentTimeMillis(), backlog));
		return backlog;
	}
	
	static{
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName != null && osName.indexOf("window") == -1) {
			//注册Linux kill信号量kill -12
			sun.misc.Signal sig = new sun.misc.Signal("USR2");
			sun.misc.Signal.handle(sig, new SignalHandler() {
				
				@Override
				public void handle(sun.misc.Signal arg0) {
					System.out.println("收到kill消息，执行关闭操作");
					//关闭消息订阅
					isClose = true;
					//关闭线程池，等待线程池积压消息处理
					THREAD_POOL.shutdown();
					//判断线程池是否关闭
					while(!THREAD_POOL.isTerminated()) {
						//没200毫秒判断线程池积压数量
						getPoolBacklogSize();
						try {
							TimeUnit.MILLISECONDS.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("订阅者关闭，线程池处理结束");
					System.exit(0);
				}
			});
		}
	}
	
}
