package com.bizdata.admin.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizdata.admin.domain.Login_Logout;
import com.bizdata.admin.service.LoginLogoutService;
import com.bizdata.commons.constant.LoginLogoutType;

/**
 * 安全退出操作
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/admin")
public class AdminLogoutController {

	@Autowired
	private LoginLogoutService loginLogoutService;

	/**
	 * 执行安全退出操作
	 *
	 * @param request
	 *            请求实体
	 * @param response
	 *            返回实体
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject.getPrincipal()) {
			// 执行入库操作
			loginLogoutService.log(logoutFormat(subject, request));
			if (subject.isAuthenticated() || subject.isRemembered()) {
				subject.logout();// session会销毁，在SessionListener监听session销毁，清理权限缓存
			}
		}
		// 执行跳转到登陆页
		response.sendRedirect(request.getContextPath() + "/admin/login");
	}

	/**
	 * 封装退出操作日志对象
	 *
	 * @param currentUser
	 *            当前用户名
	 * @param request
	 *            请求实体
	 * @return Login_Logout
	 */
	private Login_Logout logoutFormat(Subject currentUser, HttpServletRequest request) {
		// 用户名
		String username = currentUser.getPrincipal().toString();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 时间
		Date date = new Date();
		// 格式化时间
		String dateString = dateFormat.format(date);
		// 操作内容
		String content = username + " 于 " + dateString + " 安全退出系统 ";
		// 获取ip
		String ip = request.getRemoteAddr();
		// 封装对象
		Login_Logout sysLoginLogout = new Login_Logout();
		sysLoginLogout.setUsername(username);
		sysLoginLogout.setContent(content);
		sysLoginLogout.setDate(date);
		sysLoginLogout.setType(LoginLogoutType.LOGOUT);
		sysLoginLogout.setIp(ip);
		return sysLoginLogout;
	}
}
