package com.dang.services;

import com.dang.pojo.User;

public interface UserService {
	public void register(User user,String ip)throws Exception;
	public User login(String nickname,String password) throws Exception;
	public User valid(String email) throws Exception;
	public void verify(User user) throws Exception;
}
