package com.dang.dao;

import java.util.List;

import com.dang.pojo.Category;

public interface CategoryDAO {
	public List<Category> findAll() throws Exception;
	public List<Category> findByParentId(int parentId) throws Exception;
}
