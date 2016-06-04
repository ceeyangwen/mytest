package com.dang.actions.cart;

import java.util.ArrayList;
import java.util.List;

import com.dang.actions.BaseAction;
import com.dang.pojo.CartItem;
import com.dang.services.Cart;
import com.dang.services.CartFactory;

public class ListAction extends BaseAction{
	private List<CartItem> items=new ArrayList<CartItem>();
	private List<CartItem> delItems=new ArrayList<CartItem>();
	private double allCount;
	private double saveCount;
	public String execute(){
		//获得购物车对象
		Cart cart=CartFactory.getCart(session);
		items=cart.getBuyList();
		delItems=cart.getDelList();
		allCount=cart.count();
		for(CartItem item:items){
			saveCount+=(item.getPro().getDangPrice()-item.getPro().getFixedPrice())*item.getQty();
		}
		return "success";
	}
	public String confirm(){
		Cart cart=CartFactory.getCart(session);
		items=cart.getBuyList();
		allCount=cart.count();
		return "success";
	}
	public List<CartItem> getDelItems() {
		return delItems;
	}
	public void setDelItems(List<CartItem> delItems) {
		this.delItems = delItems;
	}
	public double getAllCount() {
		return allCount;
	}
	public void setAllCount(double allCount) {
		this.allCount = allCount;
	}
	public double getSaveCount() {
		return saveCount;
	}
	public void setSaveCount(double saveCount) {
		this.saveCount = saveCount;
	}
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
}
