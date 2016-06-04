package com.dang.dao;

import java.util.List;

import com.dang.pojo.OrderItem;

public interface OrderItemDAO {
	public void addOrderItem(List<OrderItem> items) throws Exception;
}
