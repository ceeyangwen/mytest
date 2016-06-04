package com.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Executable;
import java.net.Socket;

import com.util.StringUtil;

public class ServerThread implements Runnable{
	
	private Socket client = null;
	
	public ServerThread(Socket client) {
		this.client = client;
	}
	
	//处理通信细节的静态方法，主要方便线程池服务器的调用
	public static void execute(Socket client) {
		try {
			PrintStream out = new PrintStream(client.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			boolean flag = true;
			while(flag) {
				String str = in.readLine();
				if(StringUtil.isEmpty(str)) {
					flag = false;
				} else {
					if("bye".equals(str)) {
						flag = false;
					} else {
						out.println("echo : " + str);
					}
				}
			}
			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		execute(client);
	}

}
