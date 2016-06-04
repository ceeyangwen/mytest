package com.dang.services;

import java.util.List;

import com.dang.pojo.Address;
import com.dang.pojo.Order;
import com.dang.pojo.OrderItem;

public interface OrderHandle {
	public int confirm(List<OrderItem> items,Order order,Address address)throws Exception;
}
