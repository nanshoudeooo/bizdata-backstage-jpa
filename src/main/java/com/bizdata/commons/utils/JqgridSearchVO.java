package com.bizdata.commons.utils;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.bizdata.commons.constant.SearchOperator;
import com.bizdata.commons.search.SearchConditionAssembler;
import com.bizdata.framework.exception.SearchConditionException;

/**
 * Jqgrid传值搜索方式工具类
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public class JqgridSearchVO {
	/**
	 * 是否执行搜索
	 */
	@AssertTrue(message = "Jqgrid传值搜索方式,必须传递search=true,执行开启")
	private boolean search = false;

	/**
	 * 单条件查询条件值
	 */
	private String searchString;

	/**
	 * 单条件查询字段
	 */
	private String searchField;

	/**
	 * 单条件查询操作类型
	 */
	private SearchOperator searchOper;

	/**
	 * 多条件查询封装
	 */
	private String filters;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public SearchOperator getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(SearchOperator searchOper) {
		this.searchOper = searchOper;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	/**
	 * 获取条件封装
	 * 
	 * @return
	 * @throws SearchConditionException
	 */
	public <T> Specification<T> getSpecifications() throws SearchConditionException {
		Specification<T> specification = null;
		if (1==checkSearchType()) {
			// 单条件查询的封装
			specification = getSingleSearchSpecification();
		} else if(-1==checkSearchType()) {
			// 多条件查询的封装
			//TODO 多条件查询是否需要封装？？？
		}
		return specification;
	}

	/**
	 * 获取单条件查询条件
	 * 
	 * @return
	 */
	private <T> Specification<T> getSingleSearchSpecification() {
		SearchConditionAssembler<T> assembler = new SearchConditionAssembler<>();
		switch (searchOper) {
		case EQ:
			return assembler.eq(searchField, searchString);
		case NE:
			return assembler.ne(searchField, searchString);
		case LT:
			return assembler.lt(searchField, searchString);
		case LE:
			return assembler.le(searchField, searchString);
		case GT:
			return assembler.gt(searchField, searchString);
		case GE:
			return assembler.ge(searchField, searchString);
		case BW:
			return assembler.bw(searchField, searchString);
		case BN:
			return assembler.bn(searchField, searchString);
		case IN:
			//涉及到类型问题，不做关于jqgrid传递过来in参数的处理
			return null;
		case NI:
			//涉及到类型问题，不做关于jqgrid传递过来not in参数的处理
			return null;
		case EW:
			return assembler.ew(searchField, searchString);
		case EN:
			return assembler.en(searchField, searchString);
		case CN:
			return assembler.cn(searchField, searchString);
		case NC:
			return assembler.nc(searchField, searchString);
		case NU:
			return assembler.nu(searchField);
		case NN:
			return assembler.nn(searchField);
		default:
			return null;
		}
	}

	/**
	 * 判断搜索类型<br>
	 * <br>
	 * true:单条件查询,false:多条件查询
	 * 
	 * @return boolean
	 * @throws SearchConditionException
	 */
	private int checkSearchType() throws SearchConditionException {
		int state = 0;
		if (isSearch()) {
			// 如果搜索开启
			if (StringUtils.isNotBlank(getSearchString()) && StringUtils.isNotBlank(getSearchField())
					&& null != getSearchOper() && StringUtils.isBlank(getFilters())) {
				// 单条件查询
				state = 1;
			} else {
				// 多条件查询
				state = -1;
			}
		} 
		return state;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}
}
