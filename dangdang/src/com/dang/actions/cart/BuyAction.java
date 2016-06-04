package com.dang.actions.cart;

import com.dang.actions.BaseAction;
import com.dang.services.Cart;
import com.dang.services.CartFactory;

public class BuyAction extends BaseAction{
	//��������Ʒ��id
	private int id;
	public String execute(){
		//��ù��ﳵ����
		Cart cart=CartFactory.getCart(session);
		//���ù��򷽷�
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
