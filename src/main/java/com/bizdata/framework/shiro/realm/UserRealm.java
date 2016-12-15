package com.bizdata.framework.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizdata.admin.domain.User;
import com.bizdata.admin.service.UserService;

/**
 * 用户认证授权相关，类似dataSource，负责用户账户验证，获取所持有的角色<br>
 * 继承AuthorizingRealm（授权），其继承了AuthenticatingRealm即身份验证），<br>
 * 而且也间接继承了CachingRealm（带有缓存实现）
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 返回对应用户所拥有的验证信息
	 *
	 * @param principals
	 *            PrincipalCollection
	 * @return AuthorizationInfo
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户名
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userService.findRoles(username));
		authorizationInfo.setStringPermissions(userService.findPermissions(username));
		return authorizationInfo;
	}

	/**
	 * 根据用户名，执行相关的验证操作
	 *
	 * @param token
	 *            验证token
	 * @return AuthenticationInfo
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();

		User user = null;
		try {
			user = userService.selectUserDetailByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		// 0:为未锁定，1:为锁定！如果为1，则返回账号锁定
		if (true == user.isLocked()) {
			throw new LockedAccountException(); // 帐号锁定
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		String salt = user.getCredentialsSalt();
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), // 用户名
				user.getPassword(), // 密码
				ByteSource.Util.bytes(salt), // 此处为盐值，salt=username+salt
				getName() // realm name
		);

		// 验证成功,设置用户名与session_id的映射
		UserNameSessionIdMap.put(user.getUsername(), SecurityUtils.getSubject().getSession().getId().toString());
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
