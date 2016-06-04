package com.dang.actions.main;

import java.util.ArrayList;
import java.util.List;

import com.dang.actions.BaseAction;
import com.dang.dao.CategoryDAO;
import com.dang.dao.impl.JdbcCategoryDAO;
import com.dang.pojo.Category;

public class CategoryAction extends BaseAction{
	private List<Category> cats;
	public String execute(){
		CategoryDAO catDao = new JdbcCategoryDAO();
		try {
			List<Category> all = catDao.findAll();
			//将小说~~计算机给cats指定
			cats = findByParentId(all, 1);
			//为小说~~计算机指定subCats子类别集合
			for(Category c : cats){
				List<Category> subCats = findByParentId(all,c.getId());
				c.setSubCats(subCats);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	//从所有类别中查找指定parentId的类别信息
	public List<Category> findByParentId(List<Category> all,int parentId){
		List<Category> list=new ArrayList<Category>();
		for(Category c:all){
			if(c.getParentId()==parentId){
				list.add(c);
			}
		}
		return list;
	}
	public List<Category> getCats() {
		return cats;
	}
	public void setCats(List<Category> cats) {
		this.cats = cats;
	}
	
}
