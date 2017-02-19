package com.netty.chapter10;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private final String url;
	
	private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
	
	private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[a-z0-9A-Z][-_a-zA-Z0-9\\.]*");
	
	
	public HttpFileServerHandler(String url) {
		this.url = url;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		if(!req.decoderResult().isSuccess()) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		if(req.method() != HttpMethod.GET) {
			sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
			return;
		}
		final String uri = req.uri();
		final String path = sanitizeUri(uri);
		if(path == null) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		File file = new File(path);
		if(file.isHidden() || !file.exists()) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		if(file.isDirectory()) {
			if(uri.endsWith("/")) {
				sendList(ctx, file);
			} else {
				sendRedirect(ctx, uri + "/");
			}
			return;
		}
		if(!file.isFile()) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (Exception e) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		long fileLen = raf.length();
		HttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		HttpHeaderUtil.setContentLength(res, fileLen);
		senContentTypeHeader(res, file);
		if(HttpHeaderUtil.isKeepAlive(req)) {
			res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderNames.KEEP_ALIVE);
		}
		ctx.write(res);
		ChannelFuture future = ctx.write(new ChunkedFile(raf, 0, fileLen, 8192), ctx.newProgressivePromise());
		future.addListener(new ChannelProgressiveFutureListener() {
			
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
				System.out.println("Transfer Completed");
			}
			
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
				if(total <= 0) {
					System.err.println("Transfer progress : " + progress);
				} else {
					System.out.println("Transfer progress : " + progress + "/" + total);
				}
			}
		});
		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if(HttpHeaderUtil.isKeepAlive(req)) {
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	private void sendList(ChannelHandlerContext ctx, File file) {
		FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
		StringBuffer strBuf = new StringBuffer();
		String dirPath = file.getPath();
		strBuf.append("<!DOCTYPE html>\r\n");
		strBuf.append("<html><head><title>");
		strBuf.append(dirPath);
		strBuf.append(" 目录：");
		strBuf.append("</title></head><body>\r\n");
		strBuf.append("<h3>");
		strBuf.append(dirPath).append(" 目录：");
		strBuf.append("</h3>\r\n");
		strBuf.append("<ul>");
		strBuf.append("<li>链接：<a href=\"../\">..</a></li>\r\n");
		for(File f : file.listFiles()) {
			if(f.isHidden() || !f.canRead()) {
				continue;
			}
			String name = f.getName();
			if(!ALLOWED_FILE_NAME.matcher(name).matches()) {
				continue;
			}
			strBuf.append("<li>链接：<a href=\"");
			strBuf.append(name);
			strBuf.append("\">");
			strBuf.append(name);
			strBuf.append("</a></li>\r\n");
		}
		strBuf.append("</ul></body></html>\r\n");
		ByteBuf buf = Unpooled.copiedBuffer(strBuf, CharsetUtil.UTF_8);
		res.content().writeBytes(buf);
		buf.release();
		ctx.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
	}
	
	private void sendRedirect(ChannelHandlerContext ctx, String newUri) {
		FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		res.headers().set(HttpHeaderNames.LOCATION, newUri);
		ctx.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if(ctx.channel().isActive()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String sanitizeUri(String uri) {
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			try {
				uri = URLDecoder.decode(uri, "iso-8859-1");
			} catch (UnsupportedEncodingException e1) {
				throw new Error();
			}
		}
		if(!uri.startsWith(url)) {
			return null;
		}
		if(!uri.startsWith("/")) {
			return null;
		}
		uri = uri.replace('/', File.separatorChar);
		if(uri.contains(File.separator + '.') ||
				uri.contains('.' + File.separator) || uri.startsWith(".")
				|| uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
			return null;
		}
		return "E:" + uri;
	}
	
	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response  = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, 
				Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	private void senContentTypeHeader(HttpResponse res, File file) {
		MimetypesFileTypeMap mimeType = new MimetypesFileTypeMap();
		res.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeType.getContentType(file));
	}

}
