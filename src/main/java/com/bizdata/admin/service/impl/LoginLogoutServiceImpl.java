package com.bizdata.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bizdata.admin.domain.Login_Logout;
import com.bizdata.admin.repository.LoginLogoutRepository;
import com.bizdata.admin.service.LoginLogoutService;
import com.bizdata.commons.utils.JpaPageVO;
import com.bizdata.commons.utils.JpaSortVO;
import com.bizdata.commons.utils.JqgridSearchVO;
import com.bizdata.framework.exception.JpaFindConditionException;

@Service
public class LoginLogoutServiceImpl implements LoginLogoutService {

	@Autowired
	private LoginLogoutRepository loginLogoutRepository;

	@Override
	public void log(Login_Logout loginLogout) {
		loginLogoutRepository.save(loginLogout);
	}

	@Override
	public Page<Login_Logout> findAllByPage(JpaPageVO pageVO, JpaSortVO sortVO, JqgridSearchVO jqgridSearchVO)
			throws JpaFindConditionException {
		return loginLogoutRepository.findAll(jqgridSearchVO.getSpecifications(),
				pageVO.getPageable(sortVO.getCondition()));
	}

}
