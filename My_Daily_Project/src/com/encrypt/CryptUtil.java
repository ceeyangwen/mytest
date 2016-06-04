package com.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加解密的公共类
 * @author GavinCee
 *
 */
public class CryptUtil {
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		key = get8(key);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		key = get8(key);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher.doFinal(data);
	}
	
	private static byte[] get8(byte[] key) {
		byte[] key1 = new byte[8];
		for(int i = 0; i < 8; i++) {
			key1[i] = key[i];
		}
		return key1;
	}
	
	public static String toHexString(byte[] data) {
		String s = "";
		for(int i = 0; i <data.length; i++) {
			s += Integer.toHexString(data[i] & 0xFF) + "-";
		}
		return s;
	}
	
	public static void main(String[] args) throws Exception{
		String data = "Hello World!!";
		String key = "secret key";
		byte[] encrys = encrypt(data.getBytes(), key.getBytes());
		byte[] decrys = decrypt(encrys, key.getBytes());
		System.out.println(new String(decrys));
	}

}
