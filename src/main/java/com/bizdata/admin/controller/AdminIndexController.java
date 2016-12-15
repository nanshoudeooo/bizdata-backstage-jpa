package com.bizdata.admin.controller;

import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bizdata.admin.service.ResourceService;
import com.bizdata.admin.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private UserService userService;

	/**
	 * 返回默认的系统首页
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param page
	 *            String
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/")
	public ModelAndView index(HttpServletResponse response, String page) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		if (page == null) {
			modelAndView.setViewName("include/outer");
			return modelAndView;
		} else if ("index".equals(page)) {
			modelAndView.setViewName("admin_page/index");
			return modelAndView;
		} else {
			response.sendRedirect(page);
			return null;
		}
	}

	/**
	 * 查询当前用户权限系统菜单
	 *
	 * @return 菜单json数据
	 */
	@RequestMapping(value = "/getAllUserMenus")
	@ResponseBody
	public String getAllUserMenus() throws Exception {
		// 根据登录用户名获取权限
		Set<String> permissions = userService.findPermissions(SecurityUtils.getSubject().getPrincipal().toString());
		return resourceService.getUserMenus(permissions);
	}
}
