package cn.gavin.dao;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import cn.gavin.model.User;

/**  
 * @author GavinCee  
 * @date 2017年2月18日  
 *
 */
public class UserDao {
	
	public static final byte[] TABLE_NAME = Bytes.toBytes("users");
	public static final byte[] INFO_FAM = Bytes.toBytes("infos");
	public static final byte[] USER_COL = Bytes.toBytes("user");
	public static final byte[] NAME_COL = Bytes.toBytes("name");
	public static final byte[] EMAIL_COL = Bytes.toBytes("email");
	public static final byte[] PASS_COL = Bytes.toBytes("password");
	public static final byte[] TWEETS_COL = Bytes.toBytes("tweet_count");
	
	private HTablePool pool;
	private HTable htable;
	
	public UserDao(HTablePool pool){
		this.pool = pool;
	}
	
	public UserDao(HTable htable){
		this.htable = htable;
	}
	
	private static Get mkGet(String user) {
		Get get = new Get(Bytes.toBytes(user));
		get.addFamily(INFO_FAM);
		return get;
	}
	
	private static Put mkPut(User u) {
		Put put = new Put(Bytes.toBytes(u.getUser()));
		put.add(INFO_FAM, USER_COL, Bytes.toBytes(u.getUser()));
		put.add(INFO_FAM, NAME_COL, Bytes.toBytes(u.getName()));
		put.add(INFO_FAM, EMAIL_COL, Bytes.toBytes(u.getEmail()));
		put.add(INFO_FAM, PASS_COL, Bytes.toBytes(u.getPassword()));
		return put;
	}
	
	private static Delete mkDel(String user) {
		Delete d = new Delete(Bytes.toBytes(user));
		return d;
	}

	public void addUser(String user, String name, String email, String password) throws IOException {
//		HTableInterface users = pool.getTable(TABLE_NAME);
		Put put = mkPut(new User(user, name, email, password));
		htable.put(put);
//		users.close();
	}
	
	public User getUser(String user) throws IOException {
		HTableInterface userInter = pool.getTable(TABLE_NAME);
		Get g = mkGet(user);
		Result result = userInter.get(g);
		if(result.isEmpty()) {
			return null;
		}
		User u = new User(result);
		return u;
	}
	
	public void deleteUser(String user) throws IOException {
		HTableInterface inter = pool.getTable(TABLE_NAME);
		Delete d = mkDel(user);
		inter.delete(d);
		inter.close();
	}
	
}
