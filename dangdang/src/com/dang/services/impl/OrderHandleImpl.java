package com.dang.services.impl;

import java.util.List;

import com.dang.dao.AddressDAO;
import com.dang.dao.OrderDAO;
import com.dang.dao.OrderItemDAO;
import com.dang.dao.impl.JdbcAddressDAOImpl;
import com.dang.dao.impl.JdbcOrderDAOImpl;
import com.dang.dao.impl.JdbcOrderItemDAOImpl;
import com.dang.pojo.Address;
import com.dang.pojo.Order;
import com.dang.pojo.OrderItem;
import com.dang.services.OrderHandle;

public class OrderHandleImpl implements OrderHandle{

	public int confirm(List<OrderItem> items, Order order,Address address) throws Exception {
		OrderDAO orderDao=new JdbcOrderDAOImpl();
		OrderItemDAO itemDao=new JdbcOrderItemDAOImpl();
		AddressDAO addressDao=new JdbcAddressDAOImpl();
		orderDao.addOrder(order);
		itemDao.addOrderItem(items);
		return addressDao.addAddress(address);
	}

}
