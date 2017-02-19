package com.netty.chapter12.server;

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
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage nettyMsg = (NettyMessage)msg;
		//返回心跳应答
		if(nettyMsg.getHeader() != null && nettyMsg.getHeader().getType() == MessageType.HEARTBEAT_REQ.getValue()) {
			System.out.println("Receive Client Heart Beat Message : --> " + nettyMsg);
			NettyMessage heartBeat = buildHeartBeat();
			System.out.println("Send Heart Beat Response Message To Client : " + heartBeat);
			ctx.writeAndFlush(heartBeat);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	private NettyMessage buildHeartBeat() {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.HEARTBEAT_RESP.getValue());
		msg.setHeader(header);
		return msg;
	}

}
