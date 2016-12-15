package com.bizdata.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bizdata.commons.utils.BaseEntity;

/**
 * 组织机构表
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Entity
@Table(name = "admin_organization")
public class Organization extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(updatable = false, nullable = false)
	private String parent;

	@Column(nullable = false)
	private Boolean expanded = true;

	@Column(nullable = false)
	private Boolean loaded = true;

	@Column(updatable = false, nullable = false)
	private int level;

	@Column(nullable = false)
	private Boolean isleaf = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getLoaded() {
		return loaded;
	}

	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}

	public Boolean getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Boolean isleaf) {
		this.isleaf = isleaf;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
