package cn.gavin.dao;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;

import cn.gavin.model.User;

/**  
 * @author GavinCee  
 * @date 2017年2月18日  
 *
 */
public class Test {
	
	public static Configuration configuration;  
	  
    static {  
        configuration = HBaseConfiguration.create();  
        configuration.set("hbase.zookeeper.quorum", "192.168.1.133");  
    } 
	
	public static void main(String[] args) throws IOException {
		User u = new User("gavin", "chenyw", "chenyw25@yeah.net", "123456");
		doSth("add", "gavin", u);
	}
	
	public static void doSth(String command, String arg, User newUser) throws IOException {
		HTablePool pool = new HTablePool(configuration, 3000);
		HTable table = new HTable(configuration, UserDao.TABLE_NAME);
//		UserDao dao = new UserDao(pool);
		UserDao dao = new UserDao(table);
		if("get".equals(command)) {
			System.out.println("Getting User");
			User u = dao.getUser(arg);
			System.out.println(u);
		}
		if("add".equals(command)) {
			System.out.println("Putting User");
			dao.addUser(newUser.getUser(), newUser.getName(), 
					newUser.getEmail(), newUser.getPassword());
			User u = dao.getUser(arg);
			System.out.println("add success");
		}
		pool.closeTablePool(UserDao.TABLE_NAME);
	}

}
