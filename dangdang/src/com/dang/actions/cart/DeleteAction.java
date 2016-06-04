package com.dang.actions.cart;

import com.dang.actions.BaseAction;
import com.dang.services.Cart;
import com.dang.services.CartFactory;

public class DeleteAction extends BaseAction{
	private int pid;
	public String execute(){
		//获得购物车对象
		Cart cart=CartFactory.getCart(session);
		cart.delete(pid);
		return "success";
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
}
