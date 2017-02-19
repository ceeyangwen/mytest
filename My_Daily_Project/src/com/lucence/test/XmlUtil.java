package com.lucence.test;

import java.io.StringWriter;

import javax.management.remote.NotificationResult;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

public class XmlUtil {
	
	private static final String noResult = "<result>no result</result>";
	
	public static Document createFormString(String xml) {
		try {
			return DocumentHelper.parseText(xml);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String parseObject2XmlString(Object obj) {
		if(null == obj) {
			return noResult;
		}
		StringWriter sw = new StringWriter();
		JAXBContext context;
		Marshaller marshaller;
		try {
			context = JAXBContext.newInstance(obj.getClass());
			marshaller = context.createMarshaller();
			marshaller.marshal(obj, sw);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noResult;
	}

}
