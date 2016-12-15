package com.bizdata.commons.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * 自定义的多条件查询封装规格<br>
 * <br>
 * 
 * 目前只针对单表
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public class BizdataSpecification<T> implements Specification<T> {

	private List<Specification<T>> specifications = new ArrayList<>();

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (!specifications.isEmpty()) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for (Specification<T> temp : specifications) {
				predicates.add(temp.toPredicate(root, query, builder));
			}
			// 将所有条件用 and 联合起来
			if (predicates.size() > 0) {
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}
		return builder.conjunction();
	}

	/**
	 * 增加单个条件表达式(包括普通表达式，逻辑表达式)
	 * 
	 * @param criterion
	 */
	public void add(Specification<T> specification) {
		if (specification != null) {
			specifications.add(specification);
		}
	}
}
