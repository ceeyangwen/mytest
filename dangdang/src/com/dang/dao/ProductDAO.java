package com.dang.dao;

import java.util.List;

import com.dang.pojo.Book;
import com.dang.pojo.Product;

public interface ProductDAO {
	/**
	 * 查询最新上架产品集合
	 * @param topSize
	 * @return
	 * @throws Exception
	 */
	public List<Product> findNewProduct(int topSize) throws Exception;
	/**
	 * 根据类别查询图书信息
	 * @param catId 类别Id
	 * @param page 显示第几页
	 * @param pageSize 每页最多显示多少个
	 * @return 类别包含的图书
	 * @throws Exception
	 */
	public List<Book> findBookByCatId(int catId,int page,int pageSize) throws Exception;
	/**
	 * 根据商品id查找商品
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Product findProductById(int pid) throws Exception;
}
