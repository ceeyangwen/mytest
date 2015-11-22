package com.model;

public class DH extends Hero{

	public DH() {
		super();
		super.setName("DH");//设定英雄名字
		super.setExistLife(600);//设定英雄现有血量
		super.setLifeLimit(600);//设定英雄血量上限
		super.setAttack(30);//设定英雄攻击力
		super.setAttackInterval(1);//设定英雄攻击间隔
		super.setInterval(1);//设定英雄现在需等待多久方可攻击
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
		//30%概率发动攻击技能 吸血 伤害转变为生命值
		if(random < 0.3){
			//加生命值不可以超过生命上限
			if(attackHeroOldLife + this.getAttack() >= this.getLifeLimit())
			{
				this.setExistLife(this.getLifeLimit());
			}else{
				this.setExistLife(attackHeroOldLife + this.getAttack());
			}
			resultStr.append(this.getName() + "发动攻击技能,");
		}else{
			resultStr.append(this.getName() + "未发动攻击技能,");
		}
		//防御
		resultStr.append(attackedHero.defend(this, this.getAttack()));
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
		//30%发动防御技能 闪避 避免伤害
		if(random < 0.3){
			return this.getName() + "发动防御技能,";
		}else{
			this.setExistLife(this.getExistLife() - damage);
			return this.getName() + "未发动防御技能,";
		}
	}

}
