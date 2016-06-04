package com.dang.pojo;

import java.io.Serializable;

public class User implements Serializable{
	//用户Id
	private int id;
	//用户邮箱
	private String email;
	//用户昵称
	private String nickname;
	//用户密码
	private String password;
	//用户权限
	private int userIntegral;
	//是否验证邮箱
	private boolean emailVerify;
	//邮箱验证码
	private String emailVerifyCode;
	private long lastLoginTime;
	private String lastLoginIp;
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserIntegral() {
		return userIntegral;
	}
	public void setUserIntegral(int userIntegral) {
		this.userIntegral = userIntegral;
	}
	public boolean isEmailVerify() {
		return emailVerify;
	}
	public void setEmailVerify(boolean emailVerify) {
		this.emailVerify = emailVerify;
	}
	public String getEmailVerifyCode() {
		return emailVerifyCode;
	}
	public void setEmialVerifyCode(String emailVerifyCode) {
		this.emailVerifyCode = emailVerifyCode;
	}
}
