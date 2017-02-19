package com.netty.chapter12.client;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.netty.chapter12.MessageType;
import com.netty.chapter12.struct.Header;
import com.netty.chapter12.struct.NettyMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**  
 * @author GavinCee  
 * @date 2016年10月16日  
 *
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {
	
	private volatile ScheduledFuture<?> heartBeat;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage nettyMsg = (NettyMessage)msg;
		if(nettyMsg.getHeader() != null && nettyMsg.getHeader().getType() == MessageType.LOGIN_RESP.getValue()) {
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		} else if(nettyMsg.getHeader() != null && nettyMsg.getHeader().getType() == MessageType.HEARTBEAT_RESP.getValue()) {
			System.out.println("Client Receive Server Heart Beat Message --> " + nettyMsg);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	private class HeartBeatTask implements Runnable {
		
		private final ChannelHandlerContext ctx;
		
		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			NettyMessage heartBeat = buildHeartBeat();
			System.out.println("Client Send Heart Beat Message To Server --> " + heartBeat);
			ctx.writeAndFlush(heartBeat);
		}
		
		private NettyMessage buildHeartBeat() {
			NettyMessage message = new NettyMessage();
			Header header = new Header();
			header.setType(MessageType.HEARTBEAT_REQ.getValue());
			message.setHeader(header);
			return message;
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if(heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}

}
