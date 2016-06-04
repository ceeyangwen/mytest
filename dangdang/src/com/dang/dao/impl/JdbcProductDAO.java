package com.dang.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dang.dao.ProductDAO;
import com.dang.pojo.Book;
import com.dang.pojo.Product;
import com.dang.utils.DBUtils;

public class JdbcProductDAO implements ProductDAO{
	private static final String findNewProduct="select * from d_product " +
			"where has_deleted=0 order by add_time desc limit ?";
	private static final String findBookByCatId="select dp.*,db.* from d_category_product dcp" +
			" join d_product dp on(dcp.product_id=dp.id) join d_book db on(dp.id=db.id) where" +
			" dcp.cat_id=? and dp.has_deleted=0 limit ?,?";
	private static final String findProductById="select * from d_product where id=?";
	public List<Product> findNewProduct(int topSize) throws Exception {
		PreparedStatement pst = DBUtils.getConnection().prepareStatement(findNewProduct);
		pst.setInt(1, topSize);
		ResultSet rs = pst.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while(rs.next()){
			Product pro = new Product();
			pro.setId(rs.getInt("id"));
			pro.setProductName(rs.getString("product_name"));
			pro.setDescription(rs.getString("description"));
			pro.setDangPrice(rs.getDouble("dang_price"));
			pro.setFixedPrice(rs.getDouble("fixed_price"));
			pro.setProductPic(rs.getString("product_pic"));
			pro.setAddTime(rs.getLong("add_time"));
			pro.setKeywords(rs.getString("keywords"));
			pro.setHasDeleted(rs.getInt("has_deleted"));
			list.add(pro);
		}
		//DBUtils.closeConnection();
		return list;
	}

	public List<Book> findBookByCatId(int catId, int page, int pageSize)
			throws Exception {
		PreparedStatement pst = DBUtils.getConnection().prepareStatement(findBookByCatId);
		pst.setInt(1, catId);//设置查询条件catId
		int begin = (page-1)*pageSize;
		pst.setInt(2, begin);//设置抓取记录的起始索引
		pst.setInt(3, pageSize);//设置抓取记录的最大值
		ResultSet rs = pst.executeQuery();
		List<Book> list = new ArrayList<Book>();
		while(rs.next()){
			Book pro = new Book();
			pro.setId(rs.getInt("id"));
			pro.setProductName(rs.getString("product_name"));
			pro.setDescription(rs.getString("description"));
			pro.setDangPrice(rs.getDouble("dang_price"));
			pro.setFixedPrice(rs.getDouble("fixed_price"));
			pro.setProductPic(rs.getString("product_pic"));
			pro.setAddTime(rs.getLong("add_time"));
			pro.setKeywords(rs.getString("keywords"));
			pro.setHasDeleted(rs.getInt("has_deleted"));
			//设置d_book字段信息
			pro.setAuthor(rs.getString("author"));
			pro.setPublishing(rs.getString("publishing"));
			pro.setPublishTime(rs.getLong("publish_time"));
			pro.setAuthorSummary(rs.getString("author_summary"));
			//TODO 其他属性设置
			list.add(pro);
		}
		//DbUtils.closeConnection();
		return list;
	}

	public Product findProductById(int pid) throws Exception {
		PreparedStatement pst = DBUtils.getConnection().prepareStatement(findProductById);
		pst.setInt(1, pid);
		ResultSet rs = pst.executeQuery();
		Product pro = null;
		while(rs.next()){
			pro = new Product();
			pro.setId(rs.getInt("id"));
			pro.setProductName(rs.getString("product_name"));
			pro.setDescription(rs.getString("description"));
			pro.setDangPrice(rs.getDouble("dang_price"));
			pro.setFixedPrice(rs.getDouble("fixed_price"));
			pro.setProductPic(rs.getString("product_pic"));
			pro.setAddTime(rs.getLong("add_time"));
			pro.setKeywords(rs.getString("keywords"));
			pro.setHasDeleted(rs.getInt("has_deleted"));
		}
		//DBUtils.closeConnection();
		return pro;
	}

}
