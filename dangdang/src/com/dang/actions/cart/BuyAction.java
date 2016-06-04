package com.dang.actions.cart;

import com.dang.actions.BaseAction;
import com.dang.services.Cart;
import com.dang.services.CartFactory;

public class BuyAction extends BaseAction{
	//所购买商品的id
	private int id;
	public String execute(){
		//获得购物车对象
		Cart cart=CartFactory.getCart(session);
		//调用购买方法
		cart.buy(id);
		return "success";
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
