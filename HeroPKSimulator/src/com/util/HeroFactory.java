package com.util;

import com.model.Hero;

//工厂类。用于创建英雄
public class HeroFactory {
	
	//根据输入创建英雄方法
	public static Hero createHero(String heroName) {
		Hero hero = null;
		try {
			Class heroClass = Class.forName("com.model." + heroName);
			hero = (Hero) heroClass.newInstance();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return hero;
	}

}
