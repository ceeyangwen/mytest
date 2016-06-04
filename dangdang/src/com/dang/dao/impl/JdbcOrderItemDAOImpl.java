package com.dang.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;

import com.dang.dao.OrderItemDAO;
import com.dang.pojo.OrderItem;
import com.dang.utils.DBUtils;

public class JdbcOrderItemDAOImpl implements OrderItemDAO{
	private static final String add="insert into d_item(order_id,product_id," +
			"product_name,dang_price,product_num,amount) values(?,?,?,?,?,?)"; 
	public void addOrderItem(List<OrderItem> items) throws Exception {
		PreparedStatement pst=DBUtils.getConnection().prepareStatement(add);
		for(OrderItem item:items){
			pst.setInt(1, item.getOrder().getAddress().getId());
			pst.setInt(2, item.getPro().getId());
			pst.setString(3, item.getPro().getProductName());
			pst.setDouble(4, item.getPro().getDangPrice());
			pst.setInt(5, item.getPnum());
			pst.setDouble(6, item.getAmount());
			pst.executeUpdate();
		}
	}
}
