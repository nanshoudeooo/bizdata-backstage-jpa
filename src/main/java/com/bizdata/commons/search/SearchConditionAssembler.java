package com.bizdata.commons.search;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bizdata.commons.constant.SearchConjunction;
import com.bizdata.commons.constant.SearchOperator;

/**
 * 查询条件组装器
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
@Component
public class SearchConditionAssembler<T> {
	
	/**
	 * 等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> eq(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.EQ);
	}

	/**
	 * 不等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> ne(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.NE);
	}

	/**
	 * 小于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> lt(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.LT);
	}

	/**
	 * 小于等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> le(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.LE);
	}

	/**
	 * 大于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> gt(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.GT);
	}

	/**
	 * 大于等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> ge(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.GE);
	}

	/**
	 * 以指定字符开始
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> bw(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.BW);
	}

	/**
	 * 不以指定字符开始
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> bn(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.BN);
	}

	/**
	 * 以指定字符结尾
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> ew(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.EW);
	}

	/**
	 * 不以指定字符结尾
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> en(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.EN);
	}

	/**
	 * 包含指定字符
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> cn(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.CN);
	}

	/**
	 * 不包含指定字符
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> nc(String fieldName, Object value) {
		if (StringUtils.isEmpty(value))
			return null;
		return new SimpleExpression<T>(fieldName, value, SearchOperator.NC);
	}

	/**
	 * 为空
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> nu(String fieldName) {
		if (StringUtils.isEmpty(fieldName))
			return null;
		return new SimpleExpression<T>(fieldName, null, SearchOperator.NU);
	}

	/**
	 * 不为空
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SimpleExpression<T> nn(String fieldName) {
		if (StringUtils.isEmpty(fieldName))
			return null;
		return new SimpleExpression<T>(fieldName, null, SearchOperator.NN);
	}

	/**
	 * 并且
	 * 
	 * @param criterions
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LogicalExpression<T> and(Specification... specifications) {
		return new LogicalExpression<T>(specifications, SearchConjunction.AND);
	}

	/**
	 * 或者
	 * 
	 * @param criterions
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LogicalExpression<T> or(Specification... criterions) {
		return new LogicalExpression<T>(criterions, SearchConjunction.OR);
	}

	/**
	 * 包含于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LogicalExpression<T> in(String fieldName, Collection value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		SimpleExpression[] ses = new SimpleExpression[value.size()];
		int i = 0;
		for (Object obj : value) {
			ses[i] = new SimpleExpression(fieldName, obj, SearchOperator.EQ);
			i++;
		}
		return new LogicalExpression<T>(ses, SearchConjunction.OR);
	}

	/**
	 * 不包含于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LogicalExpression<T> ni(String fieldName, Collection value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		SimpleExpression[] ses = new SimpleExpression[value.size()];
		int i = 0;
		for (Object obj : value) {
			ses[i] = new SimpleExpression(fieldName, obj, SearchOperator.NE);
			i++;
		}
		return new LogicalExpression<T>(ses, SearchConjunction.AND);
	}
}
