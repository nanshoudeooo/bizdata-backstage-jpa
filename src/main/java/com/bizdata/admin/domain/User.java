package com.bizdata.admin.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bizdata.commons.utils.BaseEntity;

/**
 * 用户信息实体类
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Entity
@Table(name = "admin_user")
public class User extends BaseEntity {
	/**
	 * 所属机构
	 */
	private String organizationId;

	/**
	 * 所属机构名称
	 */
	@Transient
	private String organizationName;

	/**
	 * 用户名
	 */
	@Column(nullable = false, unique = true, length = 40)
	private String username;

	/**
	 * email
	 */
	@Column(nullable = false)
	private String email;

	/**
	 * 密码
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * 加密密码的盐
	 */
	@Column(nullable = false, updatable = true)
	private String salt;

	/**
	 * 创建时间
	 */
	@Column(nullable = false, updatable = false)
	private Date createTime = new Date();

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 密码修改时间
	 */
	private Date passwordUpdateTime = new Date();

	/**
	 * 拥有的角色列表
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admin_user_role", joinColumns = {
			@JoinColumn(name = "userid", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "roleid", referencedColumnName = "id") })
	private Set<Role> roleList;

	/**
	 * 是否被锁定
	 */
	@Column(nullable = false)
	private boolean locked = true;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return salt;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", organizationId=" + organizationId + ", username='" + username + '\''
				+ ", password='" + password + '\'' + ", salt='" + salt + '\'' + ", roleList=" + getRoleList()
				+ ", locked=" + locked + '}';
	}

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime
	 *            the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the passwordUpdateTime
	 */
	public Date getPasswordUpdateTime() {
		return passwordUpdateTime;
	}

	/**
	 * @param passwordUpdateTime
	 *            the passwordUpdateTime to set
	 */
	public void setPasswordUpdateTime(Date passwordUpdateTime) {
		this.passwordUpdateTime = passwordUpdateTime;
	}

}
