package com.bizdata.framework.extension;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizdata.commons.constant.BizdataResponseStatus;
import com.bizdata.framework.exception.JpaFindConditionException;

/**
 * 全局异常捕获统一处理
 * 
 * @version 1.0
 *
 * @author sdevil507
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理@valid注解标记参数验证错误异常
	 * 
	 * @param ex
	 * @return json类型提示
	 */
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public String processValidationError(BindException ex) {
		BindingResult result = ex.getBindingResult();
		return BizdataResponseStatus.COMMON_VALID_ERROR
				.getResult(result.getFieldError().getField() + "[" + result.getFieldError().getDefaultMessage() + "]");
	}

	@ExceptionHandler(JpaFindConditionException.class)
	@ResponseBody
	public String processJpaFindAllConditionException(Exception e) {
		return BizdataResponseStatus.COMMON_ERROR.getResult(e.getMessage());
	}
}
