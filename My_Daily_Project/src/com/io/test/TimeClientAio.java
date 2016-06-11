package com.io.test;

public class TimeClientAio {
	
	public static void main(String[] args) {
		new Thread(new AsyncTimeClientHandler("127.0.0.1", 8080)).start();
	}

}
