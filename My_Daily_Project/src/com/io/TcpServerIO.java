package com.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TcpServerIO {
	
	public static void main(String[] args) {
		server();
	}
	
	public static void server() {
		ServerSocket serverSocket = null;
		InputStream in = null;
		try {
			serverSocket = new ServerSocket(8080);
			int size = 0;
			byte[] buf = new byte[1024];
			while(true) {
				Socket socket = serverSocket.accept();
				SocketAddress client = socket.getRemoteSocketAddress();
				System.out.println("client at " + client);
				in = socket.getInputStream();
				while((size = in.read(buf)) != -1) {
					byte[] tmp = new byte[size];
					System.arraycopy(buf, 0, tmp, 0, size);
					System.out.println(new String(tmp));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
