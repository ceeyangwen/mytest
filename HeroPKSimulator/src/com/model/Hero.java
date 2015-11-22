package com.model;

//父类:英雄
public class Hero {
	
	//名字
	private String name;

	//现有生命值
	private int existLife;
	
	//生命值上限
	private int lifeLimit;
	
	//攻击力
	private int attack;
	
	//攻击间隔
	private double attackInterval;
	
	//晕眩时间,默认晕眩时间为0
	private double dazeInternal;
	
	//还需要等待多久才可攻击
	private double interval;
	
	public Hero(){
		this.setDazeInternal(0);
	}
	
	/**
	 * 攻击
	 * @param attackedHero 被攻击英雄
	 */
	public String attackHero(Hero attackedHero){
		return "";
	}
	
	/**
	 * 防御
	 * @param attackHero 攻击我的英雄
	 * @param damage 伤害值
	 */
	public String defend(Hero attackHero, int damage){
		return "";
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public double getDazeInternal() {
		return dazeInternal;
	}

	public void setDazeInternal(double dazeInternal) {
		this.dazeInternal = dazeInternal;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public double getAttackInterval() {
		return attackInterval;
	}

	public void setAttackInterval(double attackInterval) {
		this.attackInterval = attackInterval;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getExistLife() {
		return existLife;
	}

	public void setExistLife(int existLife) {
		this.existLife = existLife;
	}

	public int getLifeLimit() {
		return lifeLimit;
	}

	public void setLifeLimit(int lifeLimit) {
		this.lifeLimit = lifeLimit;
	}
	
}
