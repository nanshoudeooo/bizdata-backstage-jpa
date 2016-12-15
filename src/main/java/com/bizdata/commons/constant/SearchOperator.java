package com.bizdata.commons.constant;

/**
 * 查询操作对应枚举
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public enum SearchOperator {

	/**
	 * 等于
	 */
	EQ("="), 
	
	/**
	 * 不等于
	 */
	NE("<>"), 
	
	/**
	 * 小于
	 */
	LT("<"), 
	
	/**
	 * 小于等于
	 */
	LE("<="), 
	
	/**
	 * 大于
	 */
	GT(">"), 
	
	/**
	 * 大于等于
	 */
	GE(">="), 
	
	/**
	 * 以指定字符开始
	 */
	BW("like val%"), 
	
	/**
	 * 不以指定字符开始
	 */
	BN("not like val%"), 
	
	/**
	 * 在指定范围内
	 */
	IN("in"), 
	
	/**
	 * 不在指定范围内
	 */
	NI("not in"), 
	
	/**
	 * 以指定字符结尾
	 */
	EW("like %val"), 
	
	/**
	 * 不以指定字符结尾
	 */
	EN("not like %val"),
	
	/**
	 * 包含指定字符
	 */
	CN("like %val%"), 
	
	/**
	 * 不包含指定字符
	 */
	NC("not like %val%"), 
	
	/**
	 * 为空
	 */
	NU("is null"), 
	
	/**
	 * 不为空
	 */
	NN("is not null");

	/**
	 * 描述
	 */
	private final String desc;

	private SearchOperator(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
