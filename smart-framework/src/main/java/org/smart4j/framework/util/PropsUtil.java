package org.smart4j.framework.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性文件工具类
 * @author GavinCee
 *
 */
public class PropsUtil {

	private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);
	
	//加载属性文件
	public static Properties loadProps(String filename) {
		Properties props = null;
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			if(is == null) {
				throw new FileNotFoundException(filename + " file is not found!");
			}
			props = new Properties();
			props.load(is);
		} catch(Exception e) {
			logger.error("load properties file failure", e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e2) {
					logger.error("close input stream failure", e2);
				}
			}
			return props;
		}
	}
	
	public static String getString(Properties props, String key) {
		return getString(props, key, "");
	}
	
	public static String getString(Properties props, String key, String defaultValue) {
		String value = defaultValue;
		if(props.contains(key)) {
			value = props.getProperty(key);
		}
		return value;
	}
	
	public static int getInt(Properties props, String key) {
		return getInt(props, key, 0);
	}
	
	public static int getInt(Properties props, String key, int defaultValue) {
		int value = defaultValue;
		if(props.contains(key)) {
			value = CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}
	
	public static boolean getBoollean(Properties props, String key) {
		return getBoollean(props, key, false);
	}
	
	public static boolean getBoollean(Properties props, String key, boolean defaultValue) {
		boolean value = defaultValue;
		if(props.contains(key)) {
			value = CastUtil.castBollean(props.getProperty(key));
		}
		return value;
	}
}
