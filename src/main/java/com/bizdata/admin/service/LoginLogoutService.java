package com.bizdata.admin.service;

import org.springframework.data.domain.Page;

import com.bizdata.admin.domain.Login_Logout;
import com.bizdata.commons.utils.JpaPageVO;
import com.bizdata.commons.utils.JpaSortVO;
import com.bizdata.commons.utils.JqgridSearchVO;
import com.bizdata.framework.exception.JpaFindConditionException;

/**
 * 执行登录登出操作日志
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public interface LoginLogoutService {

	/**
	 * 执行日志记录
	 *
	 * @param loginLogout
	 *            日志对象
	 */
	public void log(Login_Logout loginLogout);

	/**
	 * 分页查询日志
	 *
	 * @return List<Login_Logout>
	 */
	public Page<Login_Logout> findAllByPage(JpaPageVO pageVO, JpaSortVO sortVO, JqgridSearchVO jqgridSearchVO)
			throws JpaFindConditionException;
}
