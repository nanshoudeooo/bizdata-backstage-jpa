package com.bizdata.framework.extension.converter;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.bizdata.commons.constant.SearchOperator;

/**
 * 查询操作字符串(String)转换为对应枚举类型(SearchOperator)<br>
 * 注意后面泛型为<String,SearchOperator>,表示只有String转换为SearchOperator枚举类型时,才会调用该转换！
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class String2SearchOperatorConverter implements Converter<String, SearchOperator> {

	@Override
	public SearchOperator convert(String source) {
		String value = source.trim().toUpperCase();
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return SearchOperator.valueOf(value);
	}
}
