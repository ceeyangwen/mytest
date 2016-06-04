package com.dang.utils;

import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
//采用MD5算法加密
public class EncryptUtil {
	public static String md5Encrypt(String s){
		if("".equals(s)||s==null){
			return "";
		}
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[] bys=md.digest(s.getBytes());
			return Base64Encrypt(bys);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	//基于64个元字符将字节信息形成字符串
	public static String Base64Encrypt(byte[] bys) {
		BASE64Encoder base=new BASE64Encoder();
		String s=base.encode(bys);
		return s;
	}
	public static byte[] Base64Decrypt(String s) throws Exception{
		BASE64Decoder base=new BASE64Decoder();
		byte[] bys=base.decodeBuffer(s);
		return bys;
	}
}
