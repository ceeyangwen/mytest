package com.guoqing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**  
 * @author GavinCee  
 * @date 2016年10月7日  
 *
 */
public class Forbidden {
	
	private static Forbidden forbidden = new Forbidden();
	
	//屏蔽词
	private HashSet<String> keyStr = new HashSet<>();
	
	private final static int maxLength = Character.MAX_VALUE;
	
	//屏蔽词长度
	private HashSet<Integer>[] keyLength = new HashSet[maxLength];
	
	public static Forbidden getForbidden() {
		return forbidden;
	}
	
	private Forbidden() {
		loadForbidden("forbidden.txt");
	}
	
	/**
	 * 输入的字符串通过屏蔽处理
	 * @param str
	 * @return
	 */
	public String read(String str) {
		if(str == null) {
			return null;
		}
		StringBuffer strBuf = new StringBuffer();
		int start = 0;
		for(int i = 0; i < str.length();) {
			int at = str.charAt(i);
			if(keyLength[at] == null) {
				i++;
				continue;
			} else {
				int ml = 0;
				for(Object obj : keyLength[at].toArray()) {
					int len = ((Integer)obj).intValue();
					if(i + len <= str.length()) {
						String s = str.substring(i, i+ len);
						if(keyStr.contains(s)) {
							//最大长度匹配
							ml = len > ml ? len : ml;
						}
					}
				}
				if(ml > 0) {
					strBuf.append(str.substring(start, i)).append("***");
					i += ml;
					start = i;
				} else {
					i++;
				}
			}
		}
		if(start < str.length()) {
			strBuf.append(str.substring(start));
		}
		return strBuf.toString();
	}
	
	/**
	 * 初始化屏蔽词
	 * 构建一个hashset用于存储所有的屏蔽词
	 * 构建长度为length的hashset<integer>数据，将加载的屏蔽中的第一个字符转换为Int值，即相关信息存储在数组中的位置
	 * 如
	 * @param path
	 */
	public void loadForbidden(String path) {
		File forbiddenFile = new File(path);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(forbiddenFile);
			InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader buffer = new BufferedReader(reader);
			String str;
			while((str = buffer.readLine()) != null) {
				str = str.trim();
				if(str.length() > 0) {
					keyStr.add(str);
					int i = str.charAt(0);
					if(keyLength[i] == null) {
						HashSet<Integer> a = new HashSet<>();
						a.add(str.length());
						keyLength[i] = a;
					} else {
						keyLength[i].add(str.length());
					}
				}
			}
			inputStream.close();
			buffer.close();
		} catch (Exception e) {
		}
		
	}

}
