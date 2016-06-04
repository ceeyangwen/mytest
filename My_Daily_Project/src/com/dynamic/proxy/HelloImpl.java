package com.dynamic.proxy;

public class HelloImpl implements Hello{
	
	private String infos1;
	
	private String infos2;

	@Override
	public String getInfos1() {
		return infos1;
	}

	@Override
	public String getInfos2() {
		return infos2;
	}

	@Override
	public void setInfo(String infos1, String infos2) {
		this.infos1 = infos1;
		this.infos2 = infos2;
	}

	@Override
	public void display() {
		System.out.println("infos1 : " + infos1 + ", infos2 : " + infos2);
	}
	
	

}
