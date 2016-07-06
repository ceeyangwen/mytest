package com.test.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerImpl implements Server {
	
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workGroup = new NioEventLoopGroup();
	private ServerBootstrap bootStrap = new ServerBootstrap();
	private ChannelFuture channel;
	private String zkAddress;
	private String serverPath;
	private String currentServerPath;
	private ServerData data;
	
	private volatile boolean binded = false;
	private final ZkClient client;
	private final RegistProvider provider;
	
	private static final Integer SESSION_TIMEOUT = 10000;
	private static final Integer CONNECT_TIMEOUT = 10000;
	
	public String getZkAddress() {
		return zkAddress;
	}

	public String getServerPath() {
		return serverPath;
	}

	public ServerData getData() {
		return data;
	}
	

	public String getCurrentServerPath() {
		return currentServerPath;
	}

	public void setData(ServerData data) {
		this.data = data;
	}
	

	public ServerImpl(String zkAddress, String serverPath, ServerData data) {
		this.zkAddress = zkAddress;
		this.serverPath = serverPath;
		this.data = data;
		this.client = new ZkClient(this.zkAddress, SESSION_TIMEOUT, CONNECT_TIMEOUT, new SerializableSerializer());
		this.provider = new DefaultRegistProvider();
	}
	
	public void initRunning() throws Exception {
		String mePath = serverPath.concat("/").concat(data.getPort().toString());
		provider.regist(new ZookeeperRegistContext(mePath, client, data));
		currentServerPath = mePath;
	}

	public void bind() {
		if(binded) 
			return;
		System.out.println(data.getPort() + ":bingding... ...");
		try {
			initRunning();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		bootStrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 1024)
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				ChannelPipeline pipe = sc.pipeline();
				pipe.addLast(new ServerHandler(new DefaultBalanceUpdateProvider(currentServerPath, client)));
			}
		});
		try {
			channel = bootStrap.bind(data.getHost(),data.getPort()).sync();
			binded = true;
			System.out.println(data.getPort() + ":binded... ...");
			channel.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
