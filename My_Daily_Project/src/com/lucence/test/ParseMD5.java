package com.lucence.test;

public class ParseMD5 extends Encrypt{

	public static String parseStrToMd5(String str) {
		return encrypt(str, MD5);
	}
	
	public static String parseStrToUpperMD5(String str) {
		return parseStrToMd5(str).toUpperCase();
	}
	
}
