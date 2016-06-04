package com.test;

import java.util.Arrays;
import java.util.List;

public class StreamTest {
	
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(1,null,3,4,null,6);
		System.out.println(nums.stream().filter(num -> num != null).count());
	}

}
