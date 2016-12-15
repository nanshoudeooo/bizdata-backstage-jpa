package com.bizdata.admin.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bizdata.commons.utils.BaseEntity;

@Entity
@Table(name = "admin_role")
public class Role extends BaseEntity {
	private String role; // 角色标识 程序中判断使用,如"admin"
	private String description; // 角色描述,UI界面显示使用

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admin_role_resource", joinColumns = {
			@JoinColumn(name = "roleid", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "resourceid", referencedColumnName = "id") })
	private List<Resource> resourceList; // 拥有的资源

	/**
	 * 对应该角色的用户列表
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admin_user_role", joinColumns = {
			@JoinColumn(name = "roleid", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "userid", referencedColumnName = "id") })
	private Set<User> userList;

	public Role() {
	}

	public Role(String role, String description) {
		this.role = role;
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Role role = (Role) o;

		if (id != null ? !id.equals(role.id) : role.id != null)
			return false;

		return true;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Role{" + "id=" + id + ", role='" + role + '\'' + ", description='" + description + '\''
				+ ", resourceList=" + resourceList + '}';
	}

	public Set<User> getUserList() {
		return userList;
	}

	public void setUserList(Set<User> userList) {
		this.userList = userList;
	}

}
