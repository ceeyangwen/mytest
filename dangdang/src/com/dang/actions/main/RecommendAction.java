package com.dang.actions.main;

import java.util.List;

import com.dang.dao.ProductDAO;
import com.dang.dao.impl.JdbcProductDAO;
import com.dang.pojo.Book;

public class RecommendAction {
	private List<Book> recommend;
	public String execute(){
		ProductDAO productDao=new JdbcProductDAO();
		try {
			recommend=productDao.findBookByCatId(2, 1, 2);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	public List<Book> getRecommend() {
		return recommend;
	}

	public void setRecommend(List<Book> recommend) {
		this.recommend = recommend;
	}
	
}
