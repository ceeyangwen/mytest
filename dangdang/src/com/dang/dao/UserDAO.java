package com.dang.dao;

import com.dang.pojo.User;

public interface UserDAO {
	public void save(User user) throws Exception;
	public void update(User user) throws Exception;
	public User findUserByEmail(String email) throws Exception;
}
