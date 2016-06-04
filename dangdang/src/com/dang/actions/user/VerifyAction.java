package com.dang.actions.user;

import com.dang.actions.BaseAction;
import com.dang.pojo.User;
import com.dang.services.UserService;
import com.dang.services.impl.UserServiceImpl;
import com.dang.utils.Constant;

public class VerifyAction extends BaseAction{
	private String code;
	private User user;
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String execute(){
		UserService service=new UserServiceImpl();
		User user=(User)session.get(Constant.USER);
		try{
			if(user==null){
				user=new User();
				user=service.valid(email);
				session.put(Constant.USER, user);
			}
			user.setEmail(email);
			if(code!=null && code.equals(user.getEmailVerifyCode())){
				user.setEmailVerify(true);
				service.verify(user);
				return "success";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "fail";
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
