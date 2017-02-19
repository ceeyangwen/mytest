package com.lucence.test;

import java.security.MessageDigest;

public class Encrypt {
	
	public static final String MD5 = "MD5";
	
	public static final String SHA1 = "SHA-1";
	
	public static final String SHA256 = "SHA-256";
	
	public static String encrypt(String str, String encName) {
		String res = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(encName);
			byte[] bytes = digest.digest(str.getBytes());
			StringBuffer buf = new StringBuffer();
			for(byte b : bytes) { 
				int bt = b&0xff;
				if(bt < 16) {
					buf.append(0);
				}
				buf.append(Integer.toHexString(bt));
			}
			res = buf.toString();
		} catch (Exception e) {
		}
		return res;
	}

}
