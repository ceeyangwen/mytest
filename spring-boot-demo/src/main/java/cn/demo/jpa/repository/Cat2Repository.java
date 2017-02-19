package cn.demo.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import cn.demo.jpa.bean.Cat;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
public interface Cat2Repository extends Repository<Cat, Integer> {
	
	/**
	 * 查询方法以get|find|read开头
	 * 条件属性用条件关键字连接，条件属性首字母大写
	 */
	//根据catName进行查询
	public Cat findByCatName(String catName);

	/**
	 * 如何编写jpql语句
	 * jpql语句和hql语句类似
	 */
	//标志是一个jpql语句
	@Query("from Cat where catName=:cn")
	public Cat findMyCatName(@Param("cn")String catName);
}
