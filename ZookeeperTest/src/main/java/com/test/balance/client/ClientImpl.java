package com.test.balance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.balance.server.ServerData;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientImpl implements Client {
	
	private final BalanceProvider<ServerData> provider;
	
	private EventLoopGroup group = null;
	
	private Channel channel = null;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public ClientImpl(BalanceProvider<ServerData> provider) {
		this.provider = provider;
	}

	public BalanceProvider<ServerData> getProvider() {
		return provider;
	}

	public void connect() throws Exception {
		ServerData data = provider.getBalanceItem();
		System.out.println("connect to " + data.getHost() + ":" + data.getPort() + ", it's balance:" + data.getBalance());
		group = new NioEventLoopGroup();
		Bootstrap boot = new Bootstrap();
		boot.group(group).channel(NioSocketChannel.class).
		handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel channel) throws Exception {
				ChannelPipeline pipe = channel.pipeline();
				pipe.addLast(new ClientHandler());
			}
		});
		ChannelFuture future = boot.connect(data.getHost(), data.getPort()).syncUninterruptibly();
		channel = future.channel();
		System.out.println("started success");
	}

	public void disconnect() throws Exception {
		if(null != channel) {
			channel.close().syncUninterruptibly();
			group.shutdownGracefully();
			group = null;
			log.debug("disconnect");
		}
	}

}
