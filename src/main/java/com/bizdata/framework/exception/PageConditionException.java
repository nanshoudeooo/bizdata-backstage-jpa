package com.bizdata.framework.exception;

/**
 * Jqgrid转Jpa排序条件异常
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class PageConditionException extends JpaFindConditionException {

	private static final long serialVersionUID = -8916618508448805283L;

	public PageConditionException() {
	}
	
	public PageConditionException(String massage) {
		super(massage);
	}

}
