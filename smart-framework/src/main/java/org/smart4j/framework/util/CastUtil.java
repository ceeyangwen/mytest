package org.smart4j.framework.util;

/**
 * 类转化工具
 * @author GavinCee
 *
 */
public class CastUtil {

	public static String castString(Object obj) {
		return castString(obj, "");
	}
	
	public static String castString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}
	
	public static double castDouble(Object obj) {
		return castDouble(obj, 0);
	}
	
	public static double castDouble(Object obj, double defaultValue) {
		double result = defaultValue;
		if(obj != null) {
			String strValue = castString(obj);
			if(StringUtil.isNotEmpty(strValue)) {
				try {
					result = Double.parseDouble(strValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static int castInt(Object obj) {
		return CastUtil.castInt(obj, 0);
	}
	
	public static int castInt(Object obj, int defaultValue) {
		int result = defaultValue;
		if(obj != null) {
			String strValue = castString(obj);
			if(StringUtil.isNotEmpty(strValue)) {
				try {
					result = Integer.parseInt(strValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static long castLong(Object obj) {
		return CastUtil.castLong(obj, 0);
	}
	
	public static long castLong(Object obj, long defaultValue) {
		long result = defaultValue;
		if(obj != null) {
			String strValue = castString(obj);
			if(StringUtil.isNotEmpty(strValue)) {
				try {
					result = Long.parseLong(strValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static boolean castBollean(Object obj) {
		return CastUtil.castBollean(obj, false);
	}
	
	public static boolean castBollean(Object obj, boolean defaultValue) {
		boolean result = defaultValue;
		if(obj != null) {
			String strValue = castString(obj);
			if(StringUtil.isNotEmpty(strValue)) {
				try {
					result = Boolean.parseBoolean(strValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
}
