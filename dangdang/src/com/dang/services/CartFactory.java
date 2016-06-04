package com.dang.services;

import java.util.Map;

import com.dang.services.impl.CartBean;
import com.dang.utils.Constant;

public class CartFactory {
	public static Cart getCart(Map<String,Object> session){
		Cart cart=(Cart)session.get(Constant.CART);
		if(cart==null){
			cart=new CartBean();
			session.put(Constant.CART, cart);
		}
		return cart;
	}
}
