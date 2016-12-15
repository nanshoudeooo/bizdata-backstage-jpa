package com.bizdata.commons.constant;

import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gson.Gson;

/**
 * 接口返回状态通用枚举
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
public enum BizdataResponseStatus {
	/**
	 * 通用成功状态
	 */
	COMMON_SUCCESS(1, "请求处理成功"),

	/**
	 * 通用失败状态
	 */
	COMMON_ERROR(-1, "请求处理失败"),

	/**
	 * 通用参数验证失败状态
	 */
	COMMON_VALID_ERROR(-2, "请求参数验证失败");

	/**
	 * 状态码
	 */
	private final int code;

	/**
	 * 状态详细描述
	 */
	private final String description;

	private BizdataResponseStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	private Gson gson = new Gson();

	/**
	 * 获取接口执行反馈
	 * 
	 * @param message
	 *            设置状态返回消息
	 * @return String类型json数据
	 */
	public String getResult(String message) {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("status_code", this.code);
		result.put("status_description", this.description);
		result.put("message", message);
		return gson.toJson(result);
	}

	/**
	 * 获取接口执行反馈(包含数据返回)
	 * 
	 * @param message
	 *            设置状态返回消息
	 * @param data
	 *            数据
	 * @return String类型json数据
	 */
	public <T> String getResult(String message, T data) {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("status_code", this.code);
		result.put("status_description", this.description);
		result.put("message", message);
		result.put("data", data);
		return gson.toJson(result);
	}

}
