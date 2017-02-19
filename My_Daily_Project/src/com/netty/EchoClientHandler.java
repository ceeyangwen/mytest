package com.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter {
	
	private int counter = 0;
	
	private int sendNumber;
	
	private static final String ECHO_REQ = "Hello World.$_";
	
	public EchoClientHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public EchoClientHandler(int sendNumber) {
		this.sendNumber = sendNumber;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		for(int i = 0; i < 10; i++) {
//			ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
//		}
		UserInfo[] infos = getUserInfos();
		for(UserInfo info : infos) {
			ctx.write(info);
		}
		ctx.flush();
	}
	
	private UserInfo[] getUserInfos() {
		UserInfo[] infos = new UserInfo[sendNumber];
		for(int i = 0; i < sendNumber; i++) {
			UserInfo info = new UserInfo();
			info.setAge(i);
			info.setName("ABCDEFG ---->" + i);
			infos[i] = info;
		}
		return infos;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("This is " + (++counter) + " times receive server " + msg);
		System.out.println("Client receive the message : " + msg);
		ctx.write(msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}

class UserInfo {
	
	private int age;
	
	private String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserInfo [age=" + age + ", name=" + name + "]";
	}
	
}
