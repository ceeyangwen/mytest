package com.netty.chapter12.client;

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
public class LoginAuthReqHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLoginReq());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage nettyMsg = (NettyMessage)msg;
		if(nettyMsg.getHeader() != null && nettyMsg.getHeader().getType() == MessageType.LOGIN_RESP.getValue()) {
			byte loginRes = (byte)nettyMsg.getBody();
			if(loginRes != (byte)0) {
				//握手失败
				ctx.close();
			} else {
				System.out.println("Login is ok : " + nettyMsg);
				ctx.fireChannelRead(msg);
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	private NettyMessage buildLoginReq() {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_REQ.getValue());
		msg.setHeader(header);
		return msg;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}

}
