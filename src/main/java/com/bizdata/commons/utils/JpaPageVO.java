package com.bizdata.commons.utils;

import javax.validation.constraints.Min;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bizdata.framework.exception.PageConditionException;

/**
 * JPA分页工具类
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public class JpaPageVO {
	/**
	 * 当前页数,默认为1
	 */
	@Min(value = 1, message = "页数必须>=1")
	private int page = 1;

	/**
	 * 每页记录条数，目前默认为15
	 */
	// TODO:后面更改至配置文件中维护
	@Min(value = 1, message = "每页记录数必须>0")
	private int rows = 15;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 获取分页对象
	 * 
	 * @return 分页对象
	 * @throws PageConditionException
	 */
	public Pageable getPageable() throws PageConditionException {
		PageRequest pageRequest = null;
		if (checkParameter()) {
			pageRequest = new PageRequest(getPage() - 1, getRows());
		} else {
			throw new PageConditionException("分页规定参数:page>=1,rows>0,请检查分页参数！");
		}
		return pageRequest;
	}

	/**
	 * 获取分页对象(含排序)
	 * 
	 * @param sort
	 *            排序实体
	 * @return 分页对象
	 * @throws PageConditionException
	 */
	public Pageable getPageable(Sort sort) throws PageConditionException {
		PageRequest pageRequest = null;
		if (checkParameter()) {
			pageRequest = new PageRequest(getPage() - 1, getRows(), sort);
		} else {
			throw new PageConditionException("分页规定参数:page>=1,rows>0,请检查分页参数！");
		}
		return pageRequest;
	}

	/**
	 * 校验分页参数
	 * 
	 * @return boolean
	 */
	private boolean checkParameter() {
		if (getPage() - 1 < 0 || getRows() <= 0) {
			// 如果页数小于1或每页记录数小于等于0，返回失败
			return false;
		} else {
			// 否则返回成功
			return true;
		}
	}
}
