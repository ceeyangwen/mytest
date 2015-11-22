package org.smart4j.framework.plugin.security;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.smart4j.framework.plugin.security.realm.SmartCustomRealm;
import org.smart4j.framework.plugin.security.realm.SmartJdbcRealm;

public class SmartSecurityFilter extends ShiroFilter{

	@Override
	public void init() throws Exception {
		super.init();
		WebSecurityManager webSecurity = super.getSecurityManager();
		//设置realm，可同时支持多个realm，并按照先后顺序用逗号隔开
		setRealms(webSecurity);
		//设置cache，用于减少数据库查询次数
		setCache(webSecurity);
	}
	
	private void setRealms(WebSecurityManager webSecurity) {
		//读取smart.plugin.security.realms配置项
		String securityRealms = SecurityConfig.getRealms();
		if(securityRealms != null) {
			//根据逗号进行拆分
			String[] securityRealmArray = securityRealms.split(",");
			if(securityRealmArray.length > 0) {
				//是Realm具备唯一性与顺序性
				Set<Realm> realms = new LinkedHashSet<Realm>();
				for(String securityRealm : securityRealmArray) {
					if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
						//添加基于JDBC的Realm，需配置相关SQL查询语句
						addJdbcRealm(realms);
					} else if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
						//添加基于定制化的Realm，需实现SmartSecurity接口
						try {
							addCustomRealm(realms);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				RealmSecurityManager realmSecurityManager = (RealmSecurityManager)webSecurity;
				realmSecurityManager.setRealms(realms);
			}
		}
	}
	
	private void addJdbcRealm(Set<Realm> realms) {
		//添加自定义实现的基于JDBC的Realm
		SmartJdbcRealm jdbcRealm = new SmartJdbcRealm();
		realms.add(jdbcRealm);
	}
	
	private void addCustomRealm(Set<Realm> realms) throws ClassNotFoundException {
		//读物smart.plugin.security.custom.class配置项
		SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
		//添加自己实现的realm
		SmartCustomRealm customRealm = new SmartCustomRealm(smartSecurity);
		realms.add(customRealm);
	}
	
	private void setCache(WebSecurityManager webSecurity) {
		//读取smart.plugin.security.cache配置项
		if(SecurityConfig.isCacheable()) {
			CachingSecurityManager cacheSecurityManager = (CachingSecurityManager)webSecurity;
			//使用基于内存的cacheManager
			CacheManager cacheManager = new MemoryConstrainedCacheManager();
			cacheSecurityManager.setCacheManager(cacheManager);
		}
	}
	
}
