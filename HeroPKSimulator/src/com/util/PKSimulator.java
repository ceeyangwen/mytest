package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.model.Hero;

//模拟英雄PK
public class PKSimulator implements Runnable{
	
	public PKSimulator(){}
	
	private List<Hero> heros = new ArrayList<Hero>(2);

	public List<Hero> getHeros() {
		return heros;
	}

	public void setHeros(List<Hero> heros) {
		this.heros = heros;
	}

	//PK的线程
	public void run() {
		do{
			//Debug2
			String result = "";//存储输出结果的字符串
			//比较两者的下次攻击的还需要的总间隔时间，总间隔时间等于正常间隔时间加上晕眩时间
			if(heros.get(0).getInterval() + heros.get(0).getDazeInternal() <= heros.get(1).getInterval() + heros.get(1).getDazeInternal()){
				//第一个英雄的间隔时间比较段，所以设置该英雄现在所需要的等待时间为0
				//而第二个英雄的剩余攻击间隔时间需要做判断
				//假如第二个英雄的晕眩时间仍大于第一个英雄的总间隔时间，则只需设置第二个英雄的剩余晕眩时间等于当前晕眩时间减去第一个英雄的总间隔时间
				//否则第二个英雄的晕眩时间不大于第一个英雄的总间隔时间，则第二个英雄的剩余晕眩时间等于0，间隔时间等于第二个英雄的总间隔时间减去第一个英雄的总间隔时间
				if(heros.get(1).getDazeInternal() - heros.get(0).getInterval() - heros.get(0).getDazeInternal() > 0)
				{
					heros.get(1).setDazeInternal(heros.get(1).getDazeInternal() - heros.get(0).getInterval() - heros.get(0).getDazeInternal());
				}else{
					heros.get(1).setDazeInternal(0);
					heros.get(1).setInterval(heros.get(1).getInterval() + heros.get(1).getDazeInternal() - heros.get(0).getInterval() - heros.get(0).getDazeInternal());
				}
				heros.get(0).setInterval(0);
			}else{
				if(heros.get(0).getDazeInternal() - heros.get(1).getInterval() - heros.get(1).getDazeInternal() > 0)
				{
					heros.get(0).setDazeInternal(heros.get(0).getDazeInternal() - heros.get(1).getInterval() - heros.get(1).getDazeInternal());
				}else{
					heros.get(0).setDazeInternal(0);
					heros.get(0).setInterval(heros.get(0).getInterval() + heros.get(0).getDazeInternal() - heros.get(1).getInterval() - heros.get(1).getDazeInternal());
				}
				heros.get(1).setInterval(0);
			}
			
			//Debug2
			//判断是否哪个英雄的间隔时间小于等于0并且生命值仍大于0，则可以进行攻击 
			if(heros.get(0).getInterval() <= 0 && heros.get(0).getExistLife() > 0){
				result = heros.get(0).attackHero(heros.get(1)) + "\n";
				//攻击完成后，该英雄的间隔需要重新设置成攻击间隔时间
				heros.get(0).setInterval(heros.get(0).getAttackInterval());
			}
			if(heros.get(1).getInterval() <= 0 && heros.get(1).getExistLife() > 0){
				result += heros.get(1).attackHero(heros.get(0)) + "\n";
				heros.get(1).setInterval(heros.get(1).getAttackInterval());
			}
			System.out.print(result);
		}while(heros.get(0).getExistLife() > 0 && heros.get(1).getExistLife() > 0);
	}

}
