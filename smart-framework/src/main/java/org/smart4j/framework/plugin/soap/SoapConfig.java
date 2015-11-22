package org.smart4j.framework.plugin.soap;

import org.smart4j.framework.helper.ConfigHelper;

/**
 * 从配置文件中获取相关属性
 * @author GavinCee
 *
 */
public class SoapConfig {

	public static boolean isLog() {
		return ConfigHelper.getBoolean(SoapConstant.LOG);
	}
	
}
