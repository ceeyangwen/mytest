package com.netty;

import com.t0814.MsgpackDecoder;
import com.t0814.MsgpackEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	
	public void connect(int port, String host) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						//exam 1
//						ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
//						ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
//						ch.pipeline().addLast(new StringDecoder());
						//exam 2
						ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
						ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
						ch.pipeline().addLast(new EchoClientHandler(1));
					}
				});
			//发起异步连接请求
			ChannelFuture future = boot.connect(host, port).sync();
			
			//等待客户端连接关闭
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new EchoClient().connect(8080, "127.0.0.1");
	}

}
