package com.dang.services.impl;

import com.dang.dao.UserDAO;
import com.dang.dao.impl.JdbcUserDAO;
import com.dang.pojo.User;
import com.dang.services.UserService;
import com.dang.utils.Constant;
import com.dang.utils.EmailUtil;
import com.dang.utils.EncryptUtil;
import com.dang.utils.VerifyCodeUtil;

public class UserServiceImpl implements UserService{
	
	public void register(User user, String ip) throws Exception {
		UserDAO userDao=new JdbcUserDAO();
		String desPwd=EncryptUtil.md5Encrypt(user.getPassword());
		user.setPassword(desPwd);
		user.setUserIntegral(Constant.NORMAL);
		user.setEmailVerify(false);
		user.setLastLoginTime(System.currentTimeMillis());
		user.setLastLoginIp(ip);
		//写入d_user
		userDao.save(user);
		String code=VerifyCodeUtil.getVerifyCode(user.getId());
		String uuid=VerifyCodeUtil.getUUID(code);
		//将uuid给用户邮箱发送
		EmailUtil.sendEmail(user.getEmail(), uuid);
		System.out.println("给邮箱"+user.getEmail()+"发送了验证码"+uuid);
		user.setEmialVerifyCode(uuid);
		//System.out.println(user.getEmailVerifyCode());
		userDao.update(user);
	}

	public User login(String email, String password) throws Exception {
		UserDAO userDao=new JdbcUserDAO();
		User res=userDao.findUserByEmail(email);
		if(!res.getPassword().equals(EncryptUtil.md5Encrypt(password))){
			res=null;
		}
		return res;
	}

	public User valid(String email) throws Exception {
		UserDAO userDao=new JdbcUserDAO();
		User res=userDao.findUserByEmail(email);
		return res;
	}

	public void verify(User user) throws Exception {
		UserDAO userDao=new JdbcUserDAO();
		userDao.update(user);
	}
	
}
