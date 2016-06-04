package com.dang.services;

import java.util.List;

import com.dang.pojo.CartItem;

public interface Cart {
	/**
	 * 购买
	 * @param pid 商品id
	 * @return
	 */
	public boolean buy(int pid);
	/**
	 * 删除
	 * @param pid
	 */
	public void delete(int pid);
	/**
	 * 恢复
	 * @param pid
	 */
	public void recoyery(int pid);
	/**
	 * 更新数量
	 * @param pid 商品id
 	 * @param pnum 更新数量
	 */
	public void update(int pid,int pnum);
	//获取确认购买的商品列表
	public List<CartItem> getBuyList();
	//获取删除的商品列表
	public List<CartItem> getDelList();
	//统计确认购买商品的金额
	public double count();
}
