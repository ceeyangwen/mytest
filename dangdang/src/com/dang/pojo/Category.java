package com.dang.pojo;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable{
	//一级项目编号
	private int id;
	//二级项目编号
	private int turn;
	//英文名字
	private String enName;
	//名字
	private String name;
	//描述
	private String description;
	//上一级Id
	private int parentId;
	//追加属性,存储子类别
	private List<Category> subCats;
	//追加属性，存储当前类别包含的产品数量
	private int pnum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public List<Category> getSubCats() {
		return subCats;
	}
	public void setSubCats(List<Category> subCats) {
		this.subCats = subCats;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
}
