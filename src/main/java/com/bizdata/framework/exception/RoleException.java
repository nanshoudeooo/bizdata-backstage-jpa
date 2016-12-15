package com.bizdata.framework.exception;

/**
 * 角色异常类
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class RoleException extends Exception {

	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 1L;

	public RoleException() {
	}

	public RoleException(String massage) {
		super(massage);
	}
}
