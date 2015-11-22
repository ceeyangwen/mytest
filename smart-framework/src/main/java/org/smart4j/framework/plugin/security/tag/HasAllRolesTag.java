package org.smart4j.framework.plugin.security.tag;

import java.util.Arrays;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

/**
 * 判断当前用户是否拥有其中所有的角色（逗号分隔，表示与的关系）
 * HasAnyPermissions:判断当前用户是否拥有其中某一种前线（逗号分隔，表示或的关系）
 * HasAllPermissions:判断当期用户是否拥有其中所有的权限（逗号分隔，表示与的关系）
 * @author GavinCee
 *
 */
public class HasAllRolesTag extends RoleTag{
	
	private static final String ROLE_NAMES_DELIMITER= ",";

	@Override
	protected boolean showTagBody(String roleNames) {
		boolean hasAllRole = false;
		Subject subject = getSubject();
		if(null != subject) {
			if(subject.hasAllRoles(Arrays.asList(roleNames.split(ROLE_NAMES_DELIMITER)))) {
				hasAllRole = true;
			}
		}
		return hasAllRole;
	}

}
