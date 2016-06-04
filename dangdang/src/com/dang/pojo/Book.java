package com.dang.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book extends Product{
	//����book��������
	//����
	private String author;
	//������
	private String publishing;
	//����ʱ��
	private long publishTime;
	//��������
	private String authorSummary;
	//׷��
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
