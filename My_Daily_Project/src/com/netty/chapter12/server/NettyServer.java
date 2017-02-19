package com.netty.chapter12.server;

import com.netty.chapter12.NettyConstant;
import com.netty.chapter12.codec.NettyMessageDecoder;
import com.netty.chapter12.codec.NettyMessageEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**  
 * @author GavinCee  
 * @date 2016年10月16日  
 *
 */
public class NettyServer {
	
	public void bind() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
					ch.pipeline().addLast(new NettyMessageEncoder());
					ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
					ch.pipeline().addLast(new LoginAuthRespHandler());
					ch.pipeline().addLast("HeartBeatHandler", new HeartBeatRespHandler());
				}
			});
		ChannelFuture future = boot.bind(NettyConstant.REMOTE_IP, NettyConstant.PORT).sync();
		System.out.println("Netty Server Start Ok : " + NettyConstant.REMOTE_IP + ":" + NettyConstant.PORT);
		future.channel().closeFuture().sync();
	}
	
	public static void main(String[] args) throws InterruptedException {
		new NettyServer().bind();
	}

}
