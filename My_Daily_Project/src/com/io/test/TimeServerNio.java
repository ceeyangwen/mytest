package com.io.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Date;
import java.util.Iterator;
import java.util.Set;

public class TimeServerNio {
	
	public static void main(String[] args) {
		int port = 8080;
		TimeServerHandlerExecutePool threadPool = new TimeServerHandlerExecutePool(50, 10000);
		threadPool.execute(new MultiplexerTimeServer(port));
	}

}

class MultiplexerTimeServer implements Runnable {
	
	private Selector selector;
	
	private ServerSocketChannel serverChannel;
	
	private volatile boolean stop = false;
	
	public MultiplexerTimeServer(int port) {
		try {
			//1、打开ServerSocketChannel，用于监听客户端的连接，所有客户端连接的父管道
			serverChannel = ServerSocketChannel.open();
			//2、绑定监听端口，设置连接为非阻塞模式
			serverChannel.socket().bind(new InetSocketAddress(port), 1024);
			serverChannel.configureBlocking(false);
			//3、创建多路复用器
			selector = Selector.open();
			//4、将serversocketchannel注册到reactor线程的多路复用器selector上，监听ACCEPT事件
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port : " + port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		//5、多路复用器在线程无限循环内轮询准备就绪的key
		while(!stop) {
			try {
				//每1秒轮询一次
				selector.select(1000);
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				SelectionKey key = null;
				while(iterator.hasNext()) {
					key = iterator.next();
					iterator.remove();
					handleInput(key);
					if(key != null) {
						key.cancel();
						if(key.channel() != null) {
							key.channel().close();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(selector != null) {
			try {
				//多路复用器关闭后，所有注册在上面的channel和pipe等资源都会自动关闭
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			if(key.isAcceptable()) {
				//接受连接
				ServerSocketChannel channel = (ServerSocketChannel)key.channel();
				SocketChannel sc = channel.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			if(key.isReadable()) {
				//读取数据
				SocketChannel sc = (SocketChannel)key.channel();
				ByteBuffer readBuf = ByteBuffer.allocate(1024);
				int readLen = sc.read(readBuf);
				if(readLen > 0) {
					readBuf.flip();
					byte[] bytes = new byte[readBuf.remaining()];
					readBuf.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("The time server receive order : " + body);
					String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "Bad oreder";
					doWrite(sc, currentTime + "\r\n");
					sc.register(selector, SelectionKey.OP_WRITE);
				} else {
					key.cancel();
					sc.close();
				}
			}
		}
	}
	
	private void doWrite(SocketChannel channel, String response) throws IOException {
		if(null != response && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer buf = ByteBuffer.allocate(bytes.length);
			buf.put(bytes);
			buf.flip();
			channel.write(buf);
		}
	}
	
	public void stop() {
		this.stop = true;
	}
	
}
