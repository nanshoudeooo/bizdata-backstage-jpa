package com.bizdata.commons.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.bizdata.commons.constant.SearchConjunction;

/**
 * 逻辑表达式<br>
 * <br>
 * 针对于AND操作与OR操作
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public class LogicalExpression<T> implements Specification<T> {
	private Specification<T>[] specifications; // 逻辑表达式中包含的表达式
	private SearchConjunction conjunction; // 搜索连词符

	public LogicalExpression(Specification<T>[] specifications, SearchConjunction conjunction) {
		this.specifications = specifications;
		this.conjunction = conjunction;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (int i = 0; i < this.specifications.length; i++) {
			predicates.add(this.specifications[i].toPredicate(root, query, builder));
		}
		switch (conjunction) {
		case OR:
			// 判断OR操作
			return builder.or(predicates.toArray(new Predicate[predicates.size()]));
		case AND:
			//判断AND操作
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		default:
			return null;
		}
	}
}
