package com.dang.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dang.dao.CategoryDAO;
import com.dang.pojo.Category;
import com.dang.utils.DBUtils;

public class JdbcCategoryDAO implements CategoryDAO{
	private static final String findAll="select * from d_category";
	private static final String findByParentId="select dc.*,count(dcp.product_id) as pnum" +
			" from d_category dc left outer join d_category_product dcp " +
			" on (dc.id=dcp.cat_id) where dc.parent_id=? group by dc.id;";
	
	public List<Category> findAll() throws Exception {
		PreparedStatement pst=DBUtils.getConnection().prepareStatement(findAll);
		ResultSet rs=pst.executeQuery();
		List<Category> list=new ArrayList<Category>();
		while(rs.next()){
			Category cat=new Category();
			cat.setId(rs.getInt("id"));
			cat.setEnName(rs.getString("en_name"));
			cat.setName(rs.getString("name"));
			cat.setTurn(rs.getInt("turn"));
			cat.setDescription(rs.getString("description"));
			cat.setParentId(rs.getInt("parent_id"));
			list.add(cat);
		}
//		DBUtils.closeConnection();
		return list;
	}

	public List<Category> findByParentId(int parentId) throws Exception {
		PreparedStatement pst=DBUtils.getConnection().prepareStatement(findByParentId);
		pst.setInt(1, parentId);
		ResultSet rs=pst.executeQuery();
		List<Category> list=new ArrayList<Category>();
		while(rs.next()){
			Category cat=new Category();
			cat.setId(rs.getInt("id"));
			cat.setEnName(rs.getString("en_name"));
			cat.setName(rs.getString("name"));
			cat.setTurn(rs.getInt("turn"));
			cat.setDescription(rs.getString("description"));
			cat.setParentId(rs.getInt("parent_id"));
			cat.setPnum(rs.getInt("pnum"));
			list.add(cat);
		}
//		DBUtils.closeConnection();
		return list;
	}

}
