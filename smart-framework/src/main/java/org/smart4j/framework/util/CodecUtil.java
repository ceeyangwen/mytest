package org.smart4j.framework.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编解码操作工具类
 * @author GavinCee
 *
 */
public class CodecUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);
	
	//编码
	public static String encodeURL(String source) {
		String target = null;
		try {
			target = URLEncoder.encode(source, "UTF-8");
		} catch (Exception e) {
			LOGGER.error("encode url fail", e);
			throw new RuntimeException(e);
		}
		return target;
	}
	
	//解码
	public static String decodeURL(String source) {
		String target = null;
		try {
			target = URLDecoder.decode(source, "UTF-8");
		} catch (Exception e) {
			LOGGER.error("decode url fail", e);
			throw new RuntimeException(e);
		}
		return target;
	}
	
	public static String md5(String source) {
		return DigestUtils.md5Hex(source);
	}
}
