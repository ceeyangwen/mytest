package com.dang.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.dang.dao.UserDAO;
import com.dang.pojo.User;
import com.dang.utils.DBUtils;

public class JdbcUserDAO implements UserDAO{
	private static final String save="insert into d_user"+
		"(email,nickname,password,user_integral,is_email_verify," +
		"email_verify_code,last_login_time,last_login_ip) values(?,?,?,?,?,?,?,?)";
	
	private static final String update="update d_user set email=?," +
			"nickname=?,password=?,user_integral=?,is_email_verify=?" +
			",email_verify_code=?,last_login_time=?,last_login_ip=? where id=?";
	private static final String find="select * from d_user where email=?";
	public void save(User user) throws Exception {
		PreparedStatement pst=DBUtils.getConnection().prepareStatement(save,Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, user.getEmail());
		pst.setString(2, user.getNickname());
		pst.setString(3, user.getPassword());
		pst.setInt(4, user.getUserIntegral());
		String isVerify=user.isEmailVerify()?"Y":"N";
		pst.setString(5, isVerify);
		pst.setString(6, user.getEmailVerifyCode());
		pst.setLong(7, user.getLastLoginTime());
		pst.setString(8, user.getLastLoginIp());
		pst.executeUpdate();
		//获取自动增长的id;
		ResultSet rs=pst.getGeneratedKeys();
		rs.next();
		int id=rs.getInt(1);
		user.setId(id);
	}
	@Override
	public void update(User user) throws Exception {
		PreparedStatement pst = 
			DBUtils.getConnection()
			.prepareStatement(update);
		pst.setString(1, user.getEmail());
		pst.setString(2, user.getNickname());
		pst.setString(3, user.getPassword());
		pst.setInt(4, user.getUserIntegral());
		String isVerify =
				user.isEmailVerify()?"Y":"N";
		pst.setString(5, isVerify);
		pst.setString(6, user.getEmailVerifyCode());
		pst.setLong(7, user.getLastLoginTime());
		pst.setString(8, user.getLastLoginIp());
		pst.setInt(9, user.getId());
		pst.executeUpdate();
	}
	public User findUserByEmail(String email) throws Exception{
		User res=null;
		PreparedStatement pst = 
			DBUtils.getConnection()
			.prepareStatement(find);
		pst.setString(1, email);
		ResultSet rs=pst.executeQuery();
		if(rs.next()){
			res=new User();
			res.setId(rs.getInt("id"));
			res.setNickname(rs.getString("nickname"));
			res.setPassword(rs.getString("password"));
			res.setEmailVerify((rs.getString("is_email_verify").equals("Y")?true:false));
		}
		return res;
	}
}
