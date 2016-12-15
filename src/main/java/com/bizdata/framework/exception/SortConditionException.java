package com.bizdata.framework.exception;

/**
 * Jqgrid转Jpa排序条件异常
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class SortConditionException extends JpaFindConditionException {

	private static final long serialVersionUID = -8916618508448805283L;

	public SortConditionException() {
	}

	public SortConditionException(String massage) {
		super(massage);
	}

}
