package com.dang.actions.main;

import java.util.List;

import com.dang.actions.BaseAction;
import com.dang.dao.CategoryDAO;
import com.dang.dao.ProductDAO;
import com.dang.dao.impl.JdbcCategoryDAO;
import com.dang.dao.impl.JdbcProductDAO;
import com.dang.pojo.Book;
import com.dang.pojo.Category;

public class BookListAction extends BaseAction{
	//input
	private int parentId;//父类别id
	private int currentId;//当前类别id
	private int page=1;
	//output
	private List<Category> cats;//类别
	private List<Book> pros;//中间产品
	private int totalPnum=0;
	private int maxPage=1;//最大页数
	private int pageSize=3;
	public String execute(){
		CategoryDAO catDao=new JdbcCategoryDAO();
		try {
			//根据parentId获取左侧显示的类别信息
			cats=catDao.findByParentId(parentId);
			//计算全部产品数量
			for(Category c:cats){
				totalPnum+=c.getPnum();
				if(c.getId()==currentId){
					maxPage=(c.getPnum()%pageSize==0)?c.getPnum()/pageSize:(c.getPnum()/pageSize+1);
				}
			}
			//根据当前类别获取所包含的图书信息
			ProductDAO proDao=new JdbcProductDAO();
			pros=proDao.findBookByCatId(currentId, page, pageSize);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getCurrentId() {
		return currentId;
	}
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List<Category> getCats() {
		return cats;
	}
	public void setCats(List<Category> cats) {
		this.cats = cats;
	}
	public List<Book> getPros() {
		return pros;
	}
	public void setPros(List<Book> pros) {
		this.pros = pros;
	}
	public int getTotalPnum() {
		return totalPnum;
	}
	public void setTotalPnum(int totalPnum) {
		this.totalPnum = totalPnum;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
