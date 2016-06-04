package com.dang.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public final class DBUtils {
	private static DataSource myDataSource=null;
	private static ThreadLocal<Connection> connLocal=new ThreadLocal<Connection>();
	private DBUtils(){
		
	}
	static{
		try {
			Properties prop=new Properties();
			InputStream is=DBUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
			prop.load(is);
			myDataSource=BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	public static DataSource getDataSource(){
		return myDataSource;
	}
	public static Connection getConnection() throws Exception{
		Connection conn=connLocal.get();
		if(conn==null){
			conn=myDataSource.getConnection();
			connLocal.set(conn);
		}
		return conn;
	}
	public static void closeConnection() throws Exception{
		Connection conn=connLocal.get();
		connLocal.set(null);
		if(conn!=null){
			conn.close();
		}
	}
}
