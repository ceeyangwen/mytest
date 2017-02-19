package cn.demo.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author GavinCee
 * @date 2017年2月19日
 *
 */
public class DemoModel {

	private int id;
	private String name;

	@JSONField(format = "yyyy-mm-dd hh:MM")
	private Date createDate;
	
	/**
	 * 不想返回该字段
	 * serialize：是否需要序列化
	 */
	@JSONField(serialize=false)
	private String mark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
}
