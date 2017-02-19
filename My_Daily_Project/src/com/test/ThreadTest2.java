package com.test;

public class ThreadTest2 {
	
	public static void main(String[] args) {
		MyData data = new MyData();
		Runnable a = new AddRunnable(data);
		Runnable b = new DescRunnable(data);
		for(int i = 0; i < 2; i++) {
			new Thread(a).start();
			new Thread(b).start();
		}
		
	}

}

class MyData {
	
	private int j = 0;
	
	public synchronized void add() {
		j++;
		System.out.println("当前线程：" + Thread.currentThread().getName() + ",当前值" + j);
	}
	
	public synchronized void minus() {
		j--;
		System.out.println("当前线程：" + Thread.currentThread().getName() + ",当前值" + j);
	}
	
	public int getData() {
		return j;
	}
	
}

class AddRunnable implements Runnable {
	
	MyData mydata;
	
	public AddRunnable(MyData mydata) {
		this.mydata = mydata;
	}
	
	public void run() {
		mydata.add();
	}
	
}

class DescRunnable implements Runnable {
	
	MyData myData;
	
	public DescRunnable(MyData myData) {
		this.myData = myData;
	}

	@Override
	public void run() {
		myData.minus();
	}
	
}
