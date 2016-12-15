package com.bizdata.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录验证Controller
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

	/**
	 * 其实认证操作交由shiro authc拦截器进行登录，如果认证失败则抛出异常存入shiroLoginFailure属性中!<br>
	 * 因为login.jsp中，action不需要指定，所以即使认证失败仍然跳转自己，进行错误的展示。
	 *
	 * @param req
	 *            请求
	 * @param model
	 *            Model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/login")
	public ModelAndView showLoginForm(HttpServletRequest req, Model model) {
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
			error = "该账号被锁定";
		} else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
			error = "您输入错误次数太多";
		} else if (exceptionClassName != null) {
			error = "认证失败";
		}
		model.addAttribute("error", error);
		return new ModelAndView("admin_page/login", model.asMap());
	}

}
