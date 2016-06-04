package com.dang.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.dang.dao.ProductDAO;
import com.dang.dao.impl.JdbcProductDAO;
import com.dang.pojo.CartItem;
import com.dang.pojo.Product;
import com.dang.services.Cart;

public class CartBean implements Cart{
	private List<CartItem> items=new ArrayList<CartItem>();
	private List<CartItem> delItems=new ArrayList<CartItem>();
	
	public boolean buy(int pid) {
		//根据商品id,获取商品对象,封装成CartItem
		ProductDAO proDao=new JdbcProductDAO();
		Product pro=null;
		try {
			pro=proDao.findProductById(pid);
			for(int i=0;i<items.size();i++){
				if(items.get(i).getPro().getId()==pid){
					//已经购买过了
					return false;
				}
			}
			//没有购买过
			CartItem item=new CartItem();
			item.setPro(pro);
			item.setQty(1);
			items.add(item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void delete(int pid) {
		for(int i=0;i<items.size();i++){
			if(items.get(i).getPro().getId()==pid){
				CartItem item=items.remove(i);
				for(int j=0;j<delItems.size();j++){
					if(delItems.get(j).getPro().getId()==pid){
						delItems.get(i).setQty(delItems.get(i).getQty()+item.getQty());
						return;
					}
				}
				delItems.add(item);
			}
		}
	}

	public void recoyery(int pid) {
		for(int i=0;i<delItems.size();i++){
			if(delItems.get(i).getPro().getId()==pid){
				CartItem item=delItems.remove(i);
				for(int j=0;j<items.size();j++){
					if(items.get(j).getPro().getId()==pid){
						items.get(j).setQty(items.get(j).getQty()+item.getQty());
						return;
					}
				}
				items.add(item);
			}
		}
	}

	public void update(int pid, int pnum) {
		for(int i=0;i<items.size();i++){
			if(items.get(i).getPro().getId()==pid){
				items.get(i).setQty(pnum);
			}
		}
	}

	@Override
	public List<CartItem> getBuyList() {
		return items;
	}

	public List<CartItem> getDelList() {
		return delItems;
	}

	public double count() {
		double all=0;
		for(int i=0;i<items.size();i++){
			all+=items.get(i).getPro().getDangPrice()*items.get(i).getQty();
		}
		return all;
	}
	
	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public List<CartItem> getDelItems() {
		return delItems;
	}

	public void setDelItems(List<CartItem> delItems) {
		this.delItems = delItems;
	}
}
