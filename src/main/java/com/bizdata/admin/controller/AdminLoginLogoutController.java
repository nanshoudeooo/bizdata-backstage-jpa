package com.bizdata.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bizdata.admin.domain.Login_Logout;
import com.bizdata.admin.service.LoginLogoutService;
import com.bizdata.commons.utils.JpaPageVO;
import com.bizdata.commons.utils.JpaSortVO;
import com.bizdata.commons.utils.JqgridSearchVO;
import com.bizdata.framework.exception.JpaFindConditionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 系统菜单，登录登出日志展示Controller
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/admin/loginlogout")
public class AdminLoginLogoutController {
	@Autowired
	private LoginLogoutService loginLogoutService;

	/**
	 * 侧边栏打开角色管理路径 返回对应的list.jsp页面 对应在后台资源列表中填入的url
	 *
	 * @return ModelAndView
	 */
	@RequiresPermissions("sys:loginlogout:view")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		// 跳转到对应登录登出日志url
		return new ModelAndView("admin_page/login_logout_log/login_logout_log");
	}

	/**
	 * 异步获取登录登出日志列表信息
	 *
	 * @param jpaPageSortWhereCondition
	 * @return String
	 * @throws JpaFindConditionException
	 */
	@RequiresPermissions("sys:loginlogout:view")
	@RequestMapping(value = "/loginlogoutList", method = RequestMethod.GET)
	@ResponseBody
	public String loginlogoutList(JpaPageVO pageVO, JpaSortVO sortVO, JqgridSearchVO jqgridSearchVO)
			throws JpaFindConditionException {
		Map<String, Object> sysLoginLogoutMap = new HashMap<String, Object>();
		Page<Login_Logout> pageInfo = loginLogoutService.findAllByPage(pageVO, sortVO, jqgridSearchVO);
		sysLoginLogoutMap.put("rows", pageInfo.getContent());
		sysLoginLogoutMap.put("currentPage", pageVO.getPage());
		sysLoginLogoutMap.put("totalPageSize", pageInfo.getTotalPages());
		sysLoginLogoutMap.put("totalRecords", pageInfo.getTotalElements());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(sysLoginLogoutMap);
		return json;
	}
}
