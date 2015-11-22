package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 * @author GavinCee
 *
 */
public class JsonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	//将POJO转化为JSON
	public static <T> String toJson(T obj) {
		String json = null;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.error("convert POJO to JSON fail", e);
			throw new RuntimeException(e);
		}
		return json;
	}
	
	//将JSON转化为POJO
	public static <T> T fromJson(String json, Class<?> type) {
		T pojo = null;
		try {
			pojo = (T) OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			LOGGER.error("convert JSON to POJO fail", e);
			throw new RuntimeException(e);
		}
		return pojo;
	}
	
}
