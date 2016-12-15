package com.bizdata.admin.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bizdata.commons.utils.BaseEntity;

/**
 * 数据初始化表
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Entity
@Table(name = "admin_init")
public class Init extends BaseEntity {
	/**
	 * 是否已经初始化
	 */
	@Column(nullable = false)
	private boolean state = false;

	@Column(nullable = false)
	private Date date = new Date();

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
