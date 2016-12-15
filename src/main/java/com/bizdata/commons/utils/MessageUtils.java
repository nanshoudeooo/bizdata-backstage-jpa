package com.bizdata.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 根据地域获取i18n中词条工具类
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Component
public class MessageUtils {
	@Autowired
	private MessageSource messageSource;

	/**
	 * 获取词条
	 *
	 * @param key
	 *            对应message.properties中key
	 * @return String类型message
	 */
	public String getMessage(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

}
