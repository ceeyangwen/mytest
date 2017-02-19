package com.lucence.test;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import cpdetector.io.ASCIIDetector;
import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;
import cpdetector.io.ParsingDetector;
import cpdetector.io.UnicodeDetector;

/**
 * 编码检测
 * @author GavinCee
 *
 */
public class CharsetUtil {
	
	private static final CodepageDetectorProxy detector;
	
	static {
		detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		detector.add(JChardetFacade.getInstance());
	}

	public static String getStreamCharset(InputStream in, String defaultCharset) {
		if(in == null) {
			return defaultCharset;
		}
		try {
			Charset charset = detector.detectCodepage(in, in.available());
			if(null != charset) {
				return charset.name();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultCharset;
	}
	
	public static String getFileCharset(URL url, String defaultCharset) {
		if(url == null) {
			return defaultCharset;
		}
		try {
			Charset charset = detector.detectCodepage(url);
			if(null != charset) {
				return charset.name();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultCharset;
	}
	
}
