package com.dang.actions.user;

import com.dang.actions.BaseAction;
import com.dang.pojo.User;
import com.dang.services.UserService;
import com.dang.services.impl.UserServiceImpl;
import com.dang.utils.Constant;

public class LoginAction extends BaseAction{
	private String email;
	private String password;
	//业务方法
	public String execute(){
		try {
			UserService service=new UserServiceImpl();
			User user=service.login(email, password);
			if(user!=null){
				session.put(Constant.USER, user);
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "fail";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
