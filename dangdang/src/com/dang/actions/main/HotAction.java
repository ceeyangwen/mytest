package com.dang.actions.main;

import java.util.List;

import com.dang.dao.ProductDAO;
import com.dang.dao.impl.JdbcProductDAO;
import com.dang.pojo.Book;

public class HotAction {
	private	List<Book> hot;
	public String execute(){
		ProductDAO productDao=new JdbcProductDAO();
		try {
			hot=productDao.findBookByCatId(2, 3, 4);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	public List<Book> getHot() {
		return hot;
	}
	public void setHot(List<Book> hot) {
		this.hot = hot;
	}
}
