package com.lucence.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 元字符： +、*、？、\s、\S、\d、\w、\W、.
 * 出现频率：{n}、{n,}、{n,m}
 * 定位符：^、$、\b、\B
 * 范围符：[]
 * 
 * @author GavinCee
 *
 */
public class RegxTest {
	
	public static String getFirstString(String dealStr, String regexStr, int n) {
		if(dealStr == null || regexStr == null || n < 0) {
			return "";
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()) {
			return matcher.group(n).trim();
		}
		return "";
	}
	
	public static List<String> getList(String dealStr, String regexStr, int n) {
		List<String> list = new ArrayList<>();
		if(dealStr == null || regexStr == null || n < 0) {
			return list;
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()) {
			list.add(matcher.group(n).trim());
		}
		return list;
	}
	
	public static List<String[]> getList(String dealStr, String regexStr, int[] array) {
		List<String[]> list = new ArrayList<>();
		if(dealStr == null || regexStr == null || array == null) {
			return list;
		}
		for(int i = 0; i < array.length; i++) {
			if(array[i] < 0) {
				return list;
			}
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while(matcher.find()) {
			String[] ss = new String[array.length];
			for(int i = 0; i < array.length; i++) {
				ss[i] = matcher.group(array[i]).trim();
			}
			list.add(ss);
		}
		return list;
	}
	
	public static void main(String[] args) {
		String dealStr = "ab1234asdy";
		String regex = "a(.*?)a";
		System.out.println(getFirstString(dealStr, regex, 1));
	}

}
