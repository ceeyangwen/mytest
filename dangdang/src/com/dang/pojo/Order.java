package com.dang.pojo;

public class Order{
	//����order�Լ�������
	private int status;
	//�ܼ�
	private double total;
	//�¶���ʱ��
	private long time;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	//�ռ���Ϣ
	private Address address;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}
