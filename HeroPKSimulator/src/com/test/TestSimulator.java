package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.model.Hero;
import com.util.HeroFactory;
import com.util.PKSimulator;

//测试类
public class TestSimulator {

	public static void main(String[] args) {
		PKSimulator simulator = new PKSimulator();
		simulator.setHeros(getInputHeros());//获取输入的英雄
		if(null != simulator.getHeros() && simulator.getHeros().size() >= 2){
			Thread thread = new Thread(simulator);
			//启动模拟器
			thread.start();
		}
	}
	
	public static List<Hero> getInputHeros(){
		List<Hero> heros = new ArrayList<Hero>(2);
		try {
			Scanner scanner = new Scanner(System.in);
			String str = scanner.nextLine();//输入要创建英雄的字符串
			String[] herosStr = str.split(",");
			//创建英雄并添加到集合
			for(String temp : herosStr){
				heros.add(HeroFactory.createHero(temp));
			}
			return heros;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
