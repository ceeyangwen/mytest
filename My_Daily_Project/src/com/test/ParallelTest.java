package com.test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelTest {

	public static void main(String[] args) {
		long[] arrayOfLong = new long[20000];
		// 并行随机产生2万个数字
		Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt(1000000));
		//并行排序
		Arrays.parallelSort(arrayOfLong);
		//流输出
		Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
	}

}
