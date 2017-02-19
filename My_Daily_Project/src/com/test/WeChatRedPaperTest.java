package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeChatRedPaperTest {
	
	//最小红包额度
	private static final int MIN_MONEY = 1;//最小额度，1分钱
	
	private static final int MAX_MONEY = 200 * 100;//最大额度，200块
	
	private boolean isRight(int money, int count) {
		double avg = money / count;
		if(avg < MIN_MONEY)
			return false;
		if(avg > MAX_MONEY)
			return false;
		return true;
	}
	
	//随机产生一个红包，产生红包之后，需要判断剩余的金额是否合法，如果不合法，重新产生随机金额
	private int random(int money, int minS, int maxS, int count) {
		//红包数量1个直接返回
		if(count == 1) {
			return money;
		}
		if(minS == maxS) {
			return minS;
		}
		int max = maxS > money ? money : maxS;
		//分配红包正确下，允许红包的最大值
		int maxY = money - (count - 1) * minS;
		//分配红包正确下，允许红包的最小值
		int minY = money - (count - 1) * maxS;
		//随机产生红包的最小值
		int min = minY > minS ? minY : minS;
		//随机产生红包的最大值
		max = maxY < max ? maxY : max;
		return (int)Math.rint(Math.random() * (max - min) + min);
	}
	
	//设定非最后一个红包最大金额
	private static final double TIMES = 2.1;
	
	//红包分配
	public List<Integer> splitRedPaper(int money, int count) {
		if(!isRight(money, count)) {
			return null;
		}
		List<Integer> list = new ArrayList<>(count);
		int max = (int)(money * TIMES / count);
		for(int i = 0; i < count; i++) {
			int one = random(money, MIN_MONEY, max, count - i);
			list.add(one);
			money -= one;
		}
		return list;
	}
	
	public static void main(String[] args) {
		WeChatRedPaperTest test = new WeChatRedPaperTest();
		System.out.println(Arrays.toString(test.splitRedPaper(20000, 100).toArray()));
	}
	
}
