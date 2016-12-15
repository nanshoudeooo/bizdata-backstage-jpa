package com.bizdata.commons.constant;

/**
 * 资源类型
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public enum ResourceType {
	ROOT("资源根节点"), COLUMN("栏目"), MENU("菜单"), ACTION("动作");

	private final String info;

	private ResourceType(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
