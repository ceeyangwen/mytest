package com.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("127.0.0.1", 20006);
		client.setSoTimeout(10000);
		//获取键盘输入
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = new PrintStream(client.getOutputStream());
		BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
		boolean flag = true;
		while(flag) {
			System.out.println("输入信息：");
			String str = in.readLine();
			out.println(str);
			if("bye".equals(str)) {
				flag = false;
			} else {
				String echo = buf.readLine();
				System.out.println(echo);
			}
		}
		in.close();
		if(client != null) {
			client.close();//只关闭socket，其关联的输入输出流也会被关闭  
		}
	}

}
