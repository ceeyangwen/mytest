package com.dang.actions.cart;

import com.dang.actions.BaseAction;
import com.dang.services.Cart;
import com.dang.services.CartFactory;

public class RecoveryAction extends BaseAction{
	private int pid;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String execute(){
		Cart cart=CartFactory.getCart(session);
		cart.recoyery(pid);
		return "success";
	}
}
