package com.io.test;

public class TimeServerAio {
	
	public static void main(String[] args) {
		int port = 8080;
		if(args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
			}
		}
		
		AsyncTimeServerHandler handler = new AsyncTimeServerHandler(port);
		new Thread(handler, "AIO-Handler").start();
	}

}
