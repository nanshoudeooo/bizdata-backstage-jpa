package com.bizdata.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bizdata.commons.utils.BaseEntity;

/**
 * 角色资源关系表
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Entity
@Table(name = "admin_role_resource")
public class Role_Resource extends BaseEntity {
	@Column(nullable = false)
	private String roleid;

	@Column(nullable = false)
	private String resourceid;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

}
