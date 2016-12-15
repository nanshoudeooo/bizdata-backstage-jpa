package com.bizdata.commons.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.bizdata.commons.constant.SearchOperator;

/**
 * 简单条件表达式<br>
 * <br>
 * 
 * 针对单子段进行数据库条件筛选操作
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public class SimpleExpression<T> implements Specification<T> {

	/**
	 * 查询属性名
	 */
	private String fieldName;

	/**
	 * 查询条件值
	 */
	private Object value;

	/**
	 * 查询计算符
	 */
	private SearchOperator operator;

	protected SimpleExpression(String fieldName, Object value, SearchOperator operator) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public SearchOperator getOperator() {
		return operator;
	}

	public void setOperator(SearchOperator operator) {
		this.operator = operator;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		switch (operator) {
		case EQ:
			// 等于
			return builder.equal(root.get(fieldName), value);
		case NE:
			// 不等于
			return builder.notEqual(root.get(fieldName), value);
		case LT:
			// 小于
			return builder.lessThan(root.get(fieldName), (Comparable) value);
		case LE:
			// 小于等于
			return builder.lessThanOrEqualTo(root.get(fieldName), (Comparable) value);
		case GT:
			// 大于
			return builder.greaterThan(root.get(fieldName), (Comparable) value);
		case GE:
			// 大于等于
			return builder.greaterThanOrEqualTo(root.get(fieldName), (Comparable) value);
		case BW:
			// 以指定字符开始
			return builder.like(root.get(fieldName), value + "%");
		case BN:
			// 不以指定字符开始
			return builder.notLike(root.get(fieldName), value + "%");
		case EW:
			// 以指定字符结尾
			return builder.like(root.get(fieldName), "%" + value);
		case EN:
			// 不以指定字符结尾
			return builder.notLike(root.get(fieldName), "%" + value);
		case CN:
			// 包含指定字符
			return builder.like(root.get(fieldName), "%" + value + "%");
		case NC:
			// 不包含指定字符
			return builder.notLike(root.get(fieldName), "%" + value + "%");
		case NU:
			// 为空
			return builder.isNull(root.get(fieldName));
		case NN:
			// 不为空
			return builder.isNotNull(root.get(fieldName));
		default:
			return null;
		}
	}
}
