package com.bizdata.framework.exception;

/**
 * jpa 查询方法传入参数条件异常基类
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public class JpaFindConditionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaFindConditionException() {
	}
	
	public JpaFindConditionException(String massage) {
		super(massage);
	}

}
