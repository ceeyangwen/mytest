package cn.gavin.model;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import cn.gavin.dao.UserDao;

/**  
 * @author GavinCee  
 * @date 2017年2月18日  
 *
 */
public class User {
	
	private String user;
	private String name;
	private String email;
	private String password;
	
	public User(String user, String name, String email, String password) {
		super();
		this.user = user;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public User(Result r) {
		this(r.getValue(UserDao.INFO_FAM, UserDao.USER_COL), 
				r.getValue(UserDao.INFO_FAM, UserDao.NAME_COL),
				r.getValue(UserDao.INFO_FAM, UserDao.EMAIL_COL),
				r.getValue(UserDao.INFO_FAM, UserDao.PASS_COL),
				r.getValue(UserDao.INFO_FAM, UserDao.TWEETS_COL) 
				== null?Bytes.toBytes(0l):r.getValue(UserDao.INFO_FAM, UserDao.TWEETS_COL));
	}
	
	public User(byte[] user, byte[] name, byte[] email, byte[] password, byte[] tweetCount) {
		this(Bytes.toString(user), 
				Bytes.toString(name),
				Bytes.toString(email),
				Bytes.toString(password));
		long count = Bytes.toLong(tweetCount);
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return String.format("<User: %s,%s,%s>", user, name, email);
	}

}
