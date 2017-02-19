package cn.demo.jpa.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.demo.jpa.bean.Cat;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
@Repository
public class CatDao {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public Cat selectByCatName(String catName) {
		String sql = "select * from cat where cat_name=?";
		RowMapper<Cat> rowMapper = new BeanPropertyRowMapper<>(Cat.class);
		return jdbcTemplate.queryForObject(sql, new Object[]{catName}, rowMapper);
	}

}
