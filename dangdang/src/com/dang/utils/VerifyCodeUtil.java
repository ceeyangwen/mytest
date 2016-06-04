package com.dang.utils;

import java.util.UUID;

public class VerifyCodeUtil {
	public static String getVerifyCode(Integer id){
		UUID uuid=UUID.randomUUID();
		String s=uuid.toString()+"-"+id;
		//System.out.println(s);
		String es=EncryptUtil.Base64Encrypt(s.getBytes());
		return es;
	}
	public static String getUUID(String verifyCode){
		byte[] bys;
		try {
			bys=EncryptUtil.Base64Decrypt(verifyCode);
			String s=new String(bys);
			return s.substring(0,s.lastIndexOf("-"));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String getUserId(String verifyCode){
		byte[] bys;
		try {
			bys=EncryptUtil.Base64Decrypt(verifyCode);
			String s=new String(bys);
			return s.substring(s.lastIndexOf("-")+1);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
//	public static void main(String[] args) {
//		System.out.println(getUserId(""));
//		System.out.println(getVerifyCode(1));
//	}
}
