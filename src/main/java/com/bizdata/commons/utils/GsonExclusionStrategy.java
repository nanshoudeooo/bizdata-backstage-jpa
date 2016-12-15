package com.bizdata.commons.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Gson排除策略，防止jpa多对多关系中出现循环引用解析
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class GsonExclusionStrategy implements ExclusionStrategy {

	public GsonExclusionStrategy() {

	}

	public GsonExclusionStrategy(String[] excludeFields, Class<?>[] excludeClasses) {
		this.excludeFields = excludeFields;
		this.excludeClasses = excludeClasses;
	}

	public GsonExclusionStrategy(String[] excludeFields) {
		this.excludeFields = excludeFields;
	}

	private String[] excludeFields;
	private Class<?>[] excludeClasses;

	public boolean shouldSkipClass(Class<?> clazz) {
		if (this.excludeClasses == null) {
			return false;
		}

		for (Class<?> excludeClass : excludeClasses) {
			if (excludeClass.getName().equals(clazz.getName())) {
				return true;
			}
		}

		return false;
	}

	public boolean shouldSkipField(FieldAttributes f) {
		if (this.excludeFields == null) {
			return false;
		}

		for (String field : this.excludeFields) {
			if (field.equals(f.getName())) {
				return true;
			}
		}

		return false;
	}

	public final String[] getExcludeFields() {
		return excludeFields;
	}

	public final Class<?>[] getExcludeClasses() {
		return excludeClasses;
	}

}
