package com.bizdata.commons.utils;

import java.io.IOException;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * 执行数据库脚本文件
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class ExecSqlUtils {

	/**
	 * 数据库驱动地址
	 */
	private String driverClassName;

	/**
	 * 数据库连接地址
	 */
	private String url;

	/**
	 * 数据库用户名
	 */
	private String username;

	/**
	 * 数据库密码
	 */
	private String password;

	/**
	 * 初始化脚本文件
	 */
	private String initScriptFile;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInitScriptFile() {
		return initScriptFile;
	}

	public void setInitScriptFile(String initScriptFile) {
		this.initScriptFile = initScriptFile;
	}

	/**
	 * 执行sql文件
	 * 
	 * @throws IOException
	 *
	 */
	public void execSqlFile() throws IOException {
		SQLExec sqlExec = new SQLExec();
		// 设置数据库参数
		sqlExec.setDriver(driverClassName);
		sqlExec.setUrl(url);
		sqlExec.setUserid(username);
		sqlExec.setPassword(password);

		sqlExec.setSrc(new ClassPathResource(initScriptFile).getFile());
		sqlExec.setPrint(true);
		sqlExec.setProject(new Project());
		sqlExec.execute();
	}
}
