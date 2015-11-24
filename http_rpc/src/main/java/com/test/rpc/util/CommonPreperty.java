package com.test.rpc.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CommonPreperty {
	
	private static final Properties prop;
	
	private static final Logger logger = Logger.getLogger(CommonPreperty.class);
	
	static {
		prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("common.properties"));
		} catch (IOException e) {
			logger.error("初始化配置出错", e);
		}
	}
	
	public static int getIntProperty(String key) {
		return Integer.parseInt(prop.getProperty(key));
	}
	
	public static String getStringProperty(String key) {
		return prop.getProperty(key).toString();
	}

}
