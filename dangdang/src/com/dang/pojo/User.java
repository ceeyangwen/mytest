package com.dang.pojo;

import java.io.Serializable;

public class User implements Serializable{
	//�û�Id
	private int id;
	//�û�����
	private String email;
	//�û��ǳ�
	private String nickname;
	//�û�����
	private String password;
	//�û�Ȩ��
	private int userIntegral;
	//�Ƿ���֤����
	private boolean emailVerify;
	//������֤��
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
