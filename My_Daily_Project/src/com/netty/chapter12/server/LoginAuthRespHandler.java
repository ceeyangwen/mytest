package com.netty.chapter12.server;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
	
	private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();
	private String[] whiteList = {"127.0.0.1", "192.168.1.113"};
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage nettyMsg = (NettyMessage)msg;
		
		//如果是握手请求消息
		if(nettyMsg.getHeader() != null && nettyMsg.getHeader().getType() == MessageType.LOGIN_REQ.getValue()) {
			String nodeIndex = ctx.channel().remoteAddress().toString();
			NettyMessage loginResp = null;
			if(nodeCheck.containsKey(nodeIndex)) {
				loginResp = buildMessage((byte)-1);
			} else {
				InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
				String ip = address.getAddress().getHostAddress();
				boolean isOk = false;
				for(String wip : whiteList) {
					if(wip.equals(ip)) {
						isOk = true;
						break;
					}
				}
				loginResp = isOk ? buildMessage((byte)0) : buildMessage((byte)-1);
				if(isOk) {
					nodeCheck.put(nodeIndex, true);
				}
				System.out.println("The Login Response is : " + loginResp + ",[body]" + loginResp.getBody());
				ctx.writeAndFlush(loginResp);
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		nodeCheck.remove(ctx.channel().remoteAddress().toString());//删除缓存
		ctx.close();
		ctx.fireExceptionCaught(cause);
	}
	
	private NettyMessage buildMessage(byte result) {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_RESP.getValue());
		msg.setHeader(header);
		msg.setBody(result);
		return msg;
	}

}
