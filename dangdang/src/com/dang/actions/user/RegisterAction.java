package com.dang.actions.user;

import com.dang.actions.BaseAction;
import com.dang.pojo.User;
import com.dang.services.UserService;
import com.dang.services.impl.UserServiceImpl;
import com.dang.utils.Constant;

public class RegisterAction extends BaseAction{
	private User user;
	//业务方法
	public String execute(){
		try {
			session.put(Constant.USER, user);
			UserService service=new UserServiceImpl();
			String ip=httpRequest.getRemoteAddr();
			service.register(user, ip);
			return "verify";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
