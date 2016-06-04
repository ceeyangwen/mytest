package com.dang.utils;

import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {
	public static void sendEmail(String emailAddr,String code){
		SimpleEmail email=new SimpleEmail();
		email.setHostName("smtp.mail.yahoo.com");
		email.setAuthentication("gavinchen104@yahoo.cn","123456789");
		email.setCharset("utf-8");
		try{
			email.addTo(emailAddr);
			email.setFrom("gavinchen104@yahoo.cn","当当网");
			email.setSubject("当当网验证码");
			email.setMsg(code);
			email.send();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
