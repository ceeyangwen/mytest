package com.model;

//子类英雄：BM
public class BM extends Hero{

	public BM() {
		super();
		super.setName("BM");//设定英雄名字
		super.setExistLife(650);//设定英雄现有血量
		super.setLifeLimit(650);//设定英雄血量上限
		super.setAttack(40);//设定英雄攻击力
		super.setAttackInterval(1.5);//设定英雄攻击间隔
		super.setInterval(1.5);//设定英雄现在需等待多久方可攻击
	}
	
	/**
	 * 攻击
	 * @param attackedHero 被攻击英雄
	 */
	public String attackHero(Hero attackedHero){
		StringBuilder resultStr = new StringBuilder("");//返回攻击结果字符串
		int attackedHeroOldLife = attackedHero.getExistLife();//被攻击英雄原始血量
		int attackHeroOldLife = this.getExistLife();//攻击英雄原始血量
		resultStr.append(this.getName() + "攻击" + attackedHero.getName() + ",");
		double random = Math.random();
		//30%概率发动攻击技能 跳劈 伤害加倍
		if(random < 0.3){
			resultStr.append(this.getName() + "发动攻击技能,");
			//防御
			resultStr.append(attackedHero.defend(this, this.getAttack() * 2));
		}else{
			resultStr.append(this.getName() + "未发动攻击技能,");
			//防御
			resultStr.append(attackedHero.defend(this, this.getAttack()));
		}
		//有一方英雄血量小于0 则有人胜出
		if(attackedHero.getExistLife() <= 0)
		{
			resultStr.delete(0, resultStr.length()).append(this.getName() + "胜出");
		}else if(this.getExistLife() <= 0){
			resultStr.delete(0, resultStr.length()).append(attackedHero.getName() + "胜出");
		}else{
			resultStr.append(this.getName() + ":" + attackHeroOldLife + "->" + this.getExistLife() + ",");
			resultStr.append(attackedHero.getName() + ":" + attackedHeroOldLife + "->" + attackedHero.getExistLife());
		}
		return resultStr.toString();
	}
	
	/**
	 * 防御
	 * @param attackHero 攻击我的英雄
	 * @param damage 伤害值
	 */
	public String defend(Hero attackHero, int damage){
		double random = Math.random();
		this.setExistLife(this.getExistLife() - damage);
		//30%发动防御技能 反弹 攻击我的英雄也受同样伤害
		if(random < 0.3){
			attackHero.setExistLife(attackHero.getExistLife() - damage);
			return this.getName() + "发动防御技能,";
		}else{
			return this.getName() + "未发动防御技能,";
		}
	}
	
}
