package org.smart4j.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.PropsUtil;

public final class DatabaseHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
	
	private static final QueryRunner QUERY_RUNNER;
	
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;
	
	private static final BasicDataSource DATA_SOURCE;
	
	static {
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		QUERY_RUNNER = new QueryRunner();
		
		Properties props = PropsUtil.loadProps("config.properties");
		String driver= props.getProperty("jdbc.driver");
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(driver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);
	}
	
	public static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		try {
			if(null == conn) {
				conn = DATA_SOURCE.getConnection();
			}
		} catch(SQLException e) {
			logger.error("execute sql error", e);
		} finally {
			CONNECTION_HOLDER.set(conn);
		}
		return conn;
	}
	
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object...params) {
		List<T> res = null;
		try {
			Connection conn = getConnection();
			res = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return res;
	}
	
	public static void closeConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	public static<T> T queryEntity(Class<T> entityClass, String sql, Object...params) {
		T entity;
		try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
		} catch (Exception e) {
			logger.error("query entity fail", e);
			throw new RuntimeException();
		} finally {
			closeConnection();
		}
		return entity;
	}
	
	public static<T> T queryEntity(Class<T> entityClass, Object...params) {
		T entity;
		try {
			Connection conn = getConnection();
			String sql = "SELECT * FROM " + getTblName(entityClass) + " id=?";
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
		} catch (Exception e) {
			logger.error("query entity fail", e);
			throw new RuntimeException();
		} finally {
			closeConnection();
		}
		return entity;
	}
	
	public static List<Map<String, Object>> executeQuery(String sql, Object...params) {
		List<Map<String, Object>> result = null;
		try {
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeConnection();
		}
		return result;
	}
	
	public static int executeUpdate(String sql, Object...params) {
		int rows = 0;
		try {
			Connection conn = getConnection();
			rows = QUERY_RUNNER.update(conn, sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeConnection();
		}
		return rows;
	}
	
	public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
		if(CollectionUtil.isEmpty(fieldMap)) {
			return false;
		}
		String sql = "INSERT INTO " + getTblName(entityClass);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for(String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append(", ");
			values.append("?,");
		}
		columns.replace(columns.lastIndexOf(","), columns.length(), ")");
		values.replace(values.lastIndexOf(","), values.length(), ")");
		sql += columns + " VALUES " + values;
		
		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}
	
	public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
		if(CollectionUtil.isEmpty(fieldMap)) {
			return false;
		}
		String sql = "UPDATE " + getTblName(entityClass) + " SET ";
		StringBuilder columns = new StringBuilder("(");
		for(String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append("=?, ");
		}
		
		columns.replace(columns.lastIndexOf(","), columns.length(), ")");
		sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";
		
		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		
		Object[] params = paramList.toArray();
		return executeUpdate(sql, params) == 1;
	}
	
	public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
		String sql = "DELETE FROM " + getTblName(entityClass) + " WHERE id=?";
		return executeUpdate(sql, id) == 1;
	}
	
	private static String getTblName(Class<?> entityClass) {
		return entityClass.getSimpleName();
	}
	
	//开启事务
	public static void beginTransaction() {
		Connection conn = getConnection();
		if(null != conn) {
			try {
				conn.setAutoCommit(false);
			} catch (Exception e) {
				logger.error("begin transaction fail", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
	}
	
	//提交事务
	public static void commitTransaction() {
		Connection conn = getConnection();
		if(null != conn) {
			try {
				conn.commit();
				conn.close();
			} catch (Exception e) {
				logger.error("commit transaction fail", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	//回滚事务
	public static void rollbackTransaction() {
		Connection conn = getConnection();
		if(null != conn) {
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
				logger.error("rollback transaction fail", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}

	public static DataSource getDataSource() {
		return DATA_SOURCE;
	}
}
