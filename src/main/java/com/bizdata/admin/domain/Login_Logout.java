package com.bizdata.admin.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.bizdata.commons.constant.LoginLogoutType;
import com.bizdata.commons.utils.BaseEntity;

/**
 * 登录登出日志实体类
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Entity
@Table(name = "admin_login_logout")
public class Login_Logout extends BaseEntity {
	/**
	 * 用户名
	 */
	@Column(nullable = false)
	private String username;

	/**
	 * ip地址
	 */
	@Column(nullable = false)
	private String ip;

	/**
	 * 日志内容
	 */
	@Column(nullable = false)
	private String content;

	/**
	 * 登录登出类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoginLogoutType type;

	/**
	 * 时间
	 */
	@Column(nullable = false)
	private Date date;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LoginLogoutType getType() {
		return type;
	}

	public void setType(LoginLogoutType type) {
		this.type = type;
	}
}
