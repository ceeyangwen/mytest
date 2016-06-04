package com.dang.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dang.dao.AddressDAO;
import com.dang.pojo.Address;
import com.dang.utils.DBUtils;

public class JdbcAddressDAOImpl implements AddressDAO{
	private static final String add="insert into d_receive_address(user_id,receive_name," +
			"full_address,postal_code,mobile,phone) values(?,?,?,?,?,?)";
	public int addAddress(Address address) throws Exception {
		PreparedStatement pst=DBUtils.getConnection().prepareStatement(add,	PreparedStatement.RETURN_GENERATED_KEYS);
		pst.setInt(1, address.getUser().getId());
		pst.setString(2, address.getName());
		pst.setString(3, address.getAddress());
		pst.setString(4, address.getPostalCode());
		pst.setString(5, address.getMobile());
		pst.setString(6, address.getPhone());
		pst.executeUpdate();
		ResultSet rs=pst.getGeneratedKeys();
		if(rs.next()){
			return rs.getInt(1);
		}else{
			return 1;
		}
	}
}
