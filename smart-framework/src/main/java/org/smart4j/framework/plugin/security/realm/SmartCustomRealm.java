package org.smart4j.framework.plugin.security.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.smart4j.framework.plugin.security.SecurityConfig;
import org.smart4j.framework.plugin.security.SecurityConstant;
import org.smart4j.framework.plugin.security.SmartSecurity;
import org.smart4j.framework.plugin.security.password.Md5CredentialsMatcher;

public class SmartCustomRealm extends AuthorizingRealm {
	
	private final SmartSecurity smartSecurity;
	
	public SmartCustomRealm(SmartSecurity smartSecurity) {
		this.smartSecurity = smartSecurity;
		super.setName(SecurityConstant.REALMS_CUSTOM);
		super.setCredentialsMatcher(new Md5CredentialsMatcher());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(null == principals) {
			throw new AuthenticationException("parameter principals is null");
		}
		//获取已经认证的用户名
		String username = (String)super.getAvailablePrincipal(principals);
		//获取角色集合
		Set<String> roleNameSet = smartSecurity.getRoleNameSet(username);
		//通过角色名获取权限集合
		Set<String> permissionSet = new HashSet<String>();
		if(roleNameSet != null && roleNameSet.size() > 0) {
			for(String roleName : roleNameSet) {
				Set<String> permissionNameSet = smartSecurity.getPermissionNameSet(roleName);
				permissionSet.addAll(permissionNameSet);
			}
		}
		
		//将角色集合与权限集合放入info中，便于后续授权操作
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roleNameSet);
		info.setStringPermissions(permissionSet);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if(null == token) {
			throw new AuthenticationException("parameter token is null");
		}
		//通过token获取从表单中提交过来的用户名
		String username = ((UsernamePasswordToken)token).getUsername();
		//通过security接口并根据用户名从数据库获取密码
		String password = smartSecurity.getPassword(username);
		//将用户名与密码放入info对象中，便于后续的认证操作
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
		info.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
		info.setCredentials(password);
		return info;
	}

}
