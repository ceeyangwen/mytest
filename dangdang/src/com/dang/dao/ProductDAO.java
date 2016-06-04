package com.dang.dao;

import java.util.List;

import com.dang.pojo.Book;
import com.dang.pojo.Product;

public interface ProductDAO {
	/**
	 * ��ѯ�����ϼܲ�Ʒ����
	 * @param topSize
	 * @return
	 * @throws Exception
	 */
	public List<Product> findNewProduct(int topSize) throws Exception;
	/**
	 * ��������ѯͼ����Ϣ
	 * @param catId ���Id
	 * @param page ��ʾ�ڼ�ҳ
	 * @param pageSize ÿҳ�����ʾ���ٸ�
	 * @return ��������ͼ��
	 * @throws Exception
	 */
	public List<Book> findBookByCatId(int catId,int page,int pageSize) throws Exception;
	/**
	 * ������Ʒid������Ʒ
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Product findProductById(int pid) throws Exception;
}
