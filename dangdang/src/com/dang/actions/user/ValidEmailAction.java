package com.dang.actions.user;

import com.dang.actions.BaseAction;
import com.dang.pojo.User;
import com.dang.services.UserService;
import com.dang.services.impl.UserServiceImpl;

public class ValidEmailAction extends BaseAction{
	private String email;
	private boolean verify=false;
	private boolean flag=false;
	public String execute(){
		UserService service=new UserServiceImpl();
		try {
			User user=service.valid(email);
			if(user==null){
				flag=true;
			}else if(user.isEmailVerify()){
				verify=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public boolean isVerify() {
		return verify;
	}
	public void setVerify(boolean verify) {
		this.verify = verify;
	}
}
