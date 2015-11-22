package org.smart4j.framework.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CodecUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

public class RequestHelper {

	//创建请求对象
	public static Param createParam(HttpServletRequest req) throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		formParamList.addAll(parseParameterNames(req));
		formParamList.addAll(parseInputStream(req));
		return new Param(formParamList);
	}
	
	private static List<FormParam> parseParameterNames(HttpServletRequest req) {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		Enumeration<String> paramNames = req.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String fieldName = paramNames.nextElement();
			String[] fieldValues = req.getParameterValues(fieldName);
			if(ArrayUtil.isNotEmpty(fieldValues)) {
				Object fieldValue = null;
				if(fieldValues.length ==1) {
					fieldValue = fieldValues[0];
				} else {
					StringBuilder strBuilder = new StringBuilder("");
					for(int i = 0; i< fieldValues.length; i++) {
						strBuilder.append(fieldValues[i]);
						if(i != fieldValues.length - 1) {
							strBuilder.append(StringUtil.SEPARATOR);
						}
					}
					fieldValue = strBuilder.toString();
				}
				formParamList.add(new FormParam(fieldName, fieldValue));
			}
		}
		return formParamList;
	}
	
	private static List<FormParam> parseInputStream(HttpServletRequest req) throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
		if(StringUtil.isNotEmpty(body)) {
			String[] kvs = body.split("&");
			if(ArrayUtil.isNotEmpty(kvs)) {
				for(String kv : kvs) {
					String[] arr = kv.split("=");
					if(ArrayUtil.isNotEmpty(arr) && arr.length == 2) {
						String fieldName = arr[0];
						String fieldValue = arr[1];
						formParamList.add(new FormParam(fieldName, fieldValue));
					}
				}
			}
		}
		return formParamList;
	}
}
