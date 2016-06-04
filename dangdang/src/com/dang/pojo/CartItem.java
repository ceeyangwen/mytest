package com.dang.pojo;

import java.io.Serializable;

public class CartItem implements Serializable{
	//购买的商品
	private Product pro;
	//购买数量
	private int qty;
	//是否购买
	private boolean buy=true;
	public Product getPro() {
		return pro;
	}
	public void setPro(Product pro) {
		this.pro = pro;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public boolean isBuy() {
		return buy;
	}
	public void setBuy(boolean buy) {
		this.buy = buy;
	}
}
