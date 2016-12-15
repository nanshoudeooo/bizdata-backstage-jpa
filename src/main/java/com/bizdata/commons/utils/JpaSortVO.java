package com.bizdata.commons.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.bizdata.commons.sequence.First;
import com.bizdata.commons.sequence.Second;
import com.bizdata.framework.exception.SortConditionException;

/**
 * JPA排序工具类
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public class JpaSortVO {
	/**
	 * 排序字段<br>
	 * demo:<br>
	 * sidx=id,date
	 */
	@NotBlank(message = "排序字段不可以为空!", groups = First.class)
	private String sidx;

	/**
	 * 排序条件<br>
	 * demo:<br>
	 * sord=asc,desc
	 */
	@NotBlank(message = "排序条件不可以为空!", groups = Second.class)
	private String sord;

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * 根据传递过来参数封装排序条件
	 * 
	 * @return Sort
	 * @throws SortConditionException
	 */
	public Sort getCondition() throws SortConditionException {
		Sort sort = null;
		String[] sidxs = {};
		String[] sords = {};
		if (canSort()) {
			// 如果排序字段不为空,进行切分
			sidxs = sidx.split(",");
			// 如果排序条件不为空
			sords = sord.split(",");
			if (sidxs.length == sords.length) {
				// 如果对应,封装Sort
				List<Order> list = new ArrayList<>();
				for (int i = 0; i < sidxs.length; i++) {
					switch (sords[i]) {
					case "asc":
						list.add(new Order(Sort.Direction.ASC, sidxs[i]));
						break;
					case "desc":
						list.add(new Order(Sort.Direction.DESC, sidxs[i]));
						break;
					default:
						break;
					}
				}
				sort = new Sort(list);
			} else {
				// 如果排序字段与排序条件不对应
				throw new SortConditionException("排序字段必须与排序条件一一对应,如:(sid=name,age,sord=asc,desc)");
			}
		} else {
			// 如果没有传递排序条件
			return null;
		}
		return sort;
	}

	/**
	 * 是否可以执行排序
	 *
	 * @return boolean
	 */
	private boolean canSort() {
		return (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord));
	}

}
