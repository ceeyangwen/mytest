package com.netty.chapter12.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.netty.chapter12.NettyConstant;
import com.netty.chapter12.codec.NettyMessageDecoder;
import com.netty.chapter12.codec.NettyMessageEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**  
 * @author GavinCee  
 * @date 2016年10月16日  
 *
 */
public class NettyClient {
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	EventLoopGroup group = new NioEventLoopGroup();
			
	public void connect(String ip, int port) {
		try {
			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
						ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
						ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
						ch.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
						ch.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
					}
					
				});
			ChannelFuture future = boot.connect(new InetSocketAddress(ip, port), 
					new InetSocketAddress(NettyConstant.LOCAL_IP, NettyConstant.LOCAL_PORT)).sync();
			future.channel().closeFuture().sync();
		} catch(Exception e) {
			
		} finally {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
						connect(NettyConstant.REMOTE_IP, NettyConstant.PORT);//重连操作
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void main(String[] args) {
		new NettyClient().connect(NettyConstant.REMOTE_IP, NettyConstant.PORT);
	}

}
