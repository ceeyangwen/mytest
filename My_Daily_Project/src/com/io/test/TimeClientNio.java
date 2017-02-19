package com.io.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientNio {
	
	public static void main(String[] args) {
		int port = 8080;
		if(args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
			}
		}
		new Thread(new TimeClientHandler("127.0.0.1", port)).start();
	}

}

class TimeClientHandler implements Runnable {
	
	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop = false;
	
	public TimeClientHandler(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			//1、打开SocketChannel,设置非阻塞模式
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			doConnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		while(!stop) {
			try {
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
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		if(selector != null) {
			try {
				selector.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			SocketChannel sc = (SocketChannel)key.channel();
			if(key.isConnectable()) {
				if(sc.finishConnect()) {
					doWrite(sc);
					sc.register(selector, SelectionKey.OP_READ);
				} else {
					//连接失败，进程退出
					System.exit(1);
				}
			}
			
			if(key.isReadable()) {
				ByteBuffer buf = ByteBuffer.allocate(1024);
				int readLen = sc.read(buf);
				if(readLen > 0) {
					buf.flip();
					byte[] bytes = new byte[buf.remaining()];
					buf.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("Now is : " + body);
					this.stop = true;
				} else  {
					key.cancel();
					sc.close();
				}
			}
		}
	}
	
	private void doConnect() throws IOException {
		//如果直接连接成功，则注册到多路复用器上，发送请求信息，读应答
		if(socketChannel.connect(new InetSocketAddress(host, port))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);;
		} else {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	
	private void doWrite(SocketChannel channel) throws IOException {
		byte[] bytes = "QUERY TIME ORDER".getBytes();
		ByteBuffer buf = ByteBuffer.allocate(bytes.length);
		buf.put(bytes);
		buf.flip();
		channel.write(buf);
	}
	
}