package com.dang.dao.impl;

import java.sql.PreparedStatement;

import com.dang.dao.OrderDAO;
import com.dang.pojo.Order;
import com.dang.utils.DBUtils;

public class JdbcOrderDAOImpl implements OrderDAO{
	public static final String add="insert into d_order(user_id," +
			"status,order_time,total_price,receive_name,full_address," +
			"postal_code,mobile,phone) values(?,?,?,?,?,?,?,?,?)";
	public void addOrder(Order order) throws Exception {
		PreparedStatement pst=DBUtils.getConnection().prepareStatement(add);
		pst.setInt(1, order.getAddress().getUser().getId());
		pst.setInt(2, order.getStatus());
		pst.setLong(3, order.getTime());
		pst.setDouble(4, order.getTotal());
		pst.setString(5, order.getAddress().getName());
		pst.setString(6, order.getAddress().getAddress());
		pst.setString(7, order.getAddress().getPostalCode());
		pst.setString(8, order.getAddress().getMobile());
		pst.setString(9, order.getAddress().getPhone());
		pst.executeUpdate();
	}
}
