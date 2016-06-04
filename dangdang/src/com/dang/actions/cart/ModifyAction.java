package com.dang.actions.cart;

import com.dang.actions.BaseAction;
import com.dang.services.Cart;
import com.dang.services.CartFactory;

public class ModifyAction extends BaseAction{
	private int pid;
	private int pnum;
	public String execute(){
		//获得购物车对象
		Cart cart=CartFactory.getCart(session);
		cart.update(pid, pnum);
		return "success";
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
}
