package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
	
	public void bind(int port) throws Exception {
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
				protected void initChannel(SocketChannel ch) throws Exception {
					//exam 1
//					ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
//					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
//					ch.pipeline().addLast(new StringDecoder());
//					ch.pipeline().addLast(new EchoServerHandler());
					//exam 2
					ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new EchoServerHandlerByLength());
					//exam 3
//					ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
//					ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
//					ch.pipeline().addLast(new EchoServerHandler());
				};
				
			});
			
			//绑定端口，同步等待成功
			ChannelFuture future = boot.bind(port).sync();
			
			//等待服务器监听端口关闭
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8080;
		new EchoServer().bind(port);
	}

}
