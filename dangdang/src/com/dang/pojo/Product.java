package com.dang.pojo;

import java.io.Serializable;

public class Product implements Serializable{
	//��Ʒid
	private int id;
	//��Ʒ��
	private String productName;
	//��Ʒ����
	private String description;
	//���ʱ��
	private long addTime;
	//ԭ��
	private double fixedPrice;
	//������
	private double dangPrice;
	//�ؼ���
	private String keywords;
	//�Ƿ�ɾ��
	private int hasDeleted;
	//��ƷͼƬ
	private String productPic;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getAddTime() {
		return addTime;
	}
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}
	public double getFixedPrice() {
		return fixedPrice;
	}
	public void setFixedPrice(double fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	public double getDangPrice() {
		return dangPrice;
	}
	public void setDangPrice(double dangPrice) {
		this.dangPrice = dangPrice;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getHasDeleted() {
		return hasDeleted;
	}
	public void setHasDeleted(int hasDeleted) {
		this.hasDeleted = hasDeleted;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	
}
