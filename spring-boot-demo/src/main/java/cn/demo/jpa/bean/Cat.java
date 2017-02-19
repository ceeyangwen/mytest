package cn.demo.jpa.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**  
 * 
 * 使用Entity進行實體類的注解
 * 
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
@Entity
public class Cat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)//指定主键的生成策略，自增长
	private int id;//主键
	private String catName;//cat_name
	private int catAge;//cat_age
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public int getCatAge() {
		return catAge;
	}
	public void setCatAge(int catAge) {
		this.catAge = catAge;
	}

}
