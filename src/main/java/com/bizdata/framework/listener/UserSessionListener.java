package com.bizdata.framework.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizdata.admin.domain.Login_Logout;
import com.bizdata.admin.service.LoginLogoutService;
import com.bizdata.commons.constant.LoginLogoutType;

/**
 * 用户session监听，拓展自SessionListenerAdapter,用于超时机制记录
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class UserSessionListener extends SessionListenerAdapter {

	@Autowired
	private LoginLogoutService loginLogoutService;

	/**
	 * 当session超时，进行处理,写入用户为超时退出
	 *
	 * @param session
	 *            会话
	 */
	@Override
	public void onExpiration(Session session) {
		if (null != session.getAttribute("username") && null != session.getAttribute("ip")) {
			String username = session.getAttribute("username").toString();
			String ip = session.getAttribute("ip").toString();
			logTimeOut(username, ip);
		}
	}

	/**
	 * 执行session超时退出日志记录
	 *
	 * @param username
	 *            用户名
	 * @param ip
	 *            用户会话ip地址
	 */
	private void logTimeOut(String username, String ip) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 时间
		Date date = new Date();
		// 格式化时间
		String dateString = dateFormat.format(date);
		// 操作内容
		String content = username + " 于 " + dateString + " session超时退出系统 ";

		// 封装对象
		Login_Logout sysLoginLogout = new Login_Logout();
		sysLoginLogout.setUsername(username);
		sysLoginLogout.setContent(content);
		sysLoginLogout.setDate(date);
		sysLoginLogout.setType(LoginLogoutType.TIME_OUT);
		sysLoginLogout.setIp(ip);
		System.out.println(loginLogoutService);
		// 执行入库
		loginLogoutService.log(sysLoginLogout);
	}

}
