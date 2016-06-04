package com.dang.actions.main;

import java.util.List;

import com.dang.actions.BaseAction;
import com.dang.dao.ProductDAO;
import com.dang.dao.impl.JdbcProductDAO;
import com.dang.pojo.Product;

public class NewProductAction extends BaseAction{
	private List<Product> pros;
	private int topSize;
	public String execute(){
		ProductDAO proDao=new JdbcProductDAO();
		try {
			pros=proDao.findNewProduct(topSize);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	public List<Product> getPros() {
		return pros;
	}
	public void setPros(List<Product> pros) {
		this.pros = pros;
	}
	public int getTopSize() {
		return topSize;
	}
	public void setTopSize(int topSize) {
		this.topSize = topSize;
	}
	
}
