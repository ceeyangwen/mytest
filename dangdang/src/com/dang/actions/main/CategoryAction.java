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
			//��С˵~~�������catsָ��
			cats = findByParentId(all, 1);
			//ΪС˵~~�����ָ��subCats����𼯺�
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
	
	//����������в���ָ��parentId�������Ϣ
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
