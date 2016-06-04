package com.dang.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book extends Product{
	//定义book其他属性
	//作者
	private String author;
	//出版社
	private String publishing;
	//出版时间
	private long publishTime;
	//作者评价
	private String authorSummary;
	//追加
	public String getPublishDTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(publishTime));
	}
	public long getPublishTime() {
		return publishTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishing() {
		return publishing;
	}
	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}
	
	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}
	public String getAuthorSummary() {
		return authorSummary;
	}
	public void setAuthorSummary(String authorSummary) {
		this.authorSummary = authorSummary;
	}
}
