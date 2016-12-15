package com.bizdata.framework.extension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bizdata.commons.constant.BizdataResponseStatus;
import com.bizdata.commons.utils.MessageUtils;

/**
 * 自定义常用http错误状态处理Controller<br>
 * 拦截401,404,500错误，通过实现ErrorController取代原先的/error<br>
 * 并且实现html与json状态下的返回
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/error")
public class CustomErrorPageController implements ErrorController {

	@Autowired
	private MessageUtils messageUtils;

	/**
	 * 定义400 bad_request 的ModelAndView
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            返回
	 * @return error/400模板
	 */
	@RequestMapping(produces = "text/html", value = "400")
	public ModelAndView errorHtml400(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
		return new ModelAndView("error/400");
	}

	/**
	 * 定义400 bad_request 的JSON数据
	 * 
	 * @param request
	 *            请求
	 * @return json类型返回值
	 */
	@RequestMapping(value = "400")
	@ResponseBody
	public ResponseEntity<String> error400(HttpServletRequest request, Exception ex) {
		HttpStatus status = getStatus(request);
		String body = BizdataResponseStatus.COMMON_ERROR
				.getResult(messageUtils.getMessage("http.status.bad_request.content"));
		return new ResponseEntity<String>(body, status);
	}

	/**
	 * 定义401的ModelAndView
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            返回
	 * @return error/401模板
	 */
	@RequestMapping(produces = "text/html", value = "401")
	public ModelAndView errorHtml401(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
		return new ModelAndView("error/401");
	}

	/**
	 * 定义401的JSON数据
	 * 
	 * @param request
	 *            请求
	 * @return json类型返回值
	 */
	@RequestMapping(value = "401")
	@ResponseBody
	public ResponseEntity<String> error401(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		String body = BizdataResponseStatus.COMMON_ERROR
				.getResult(messageUtils.getMessage("http.status.not_authorized.content"));
		return new ResponseEntity<String>(body, status);
	}

	/**
	 * 定义404的ModelAndView
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            返回
	 * @return error/404模板
	 */
	@RequestMapping(produces = "text/html", value = "404")
	public ModelAndView errorHtml404(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
		return new ModelAndView("error/404");
	}

	/**
	 * 定义404的JSON数据
	 * 
	 * @param request
	 *            请求
	 * @return json类型返回值
	 */
	@RequestMapping(value = "404")
	@ResponseBody
	public ResponseEntity<String> error404(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		String body = BizdataResponseStatus.COMMON_ERROR
				.getResult(messageUtils.getMessage("http.status.not_found.content"));
		return new ResponseEntity<String>(body, status);
	}

	/**
	 * 定义500的ModelAndView
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            返回
	 * @return error/500模板
	 */
	@RequestMapping(produces = "text/html", value = "500")
	public ModelAndView errorHtml500(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
		return new ModelAndView("error/500");
	}

	/**
	 * 定义500的JSON数据
	 * 
	 * @param request
	 *            请求
	 * @return json类型返回值
	 */
	@RequestMapping(value = "500")
	@ResponseBody
	public ResponseEntity<String> error500(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		String body = BizdataResponseStatus.COMMON_ERROR
				.getResult(messageUtils.getMessage("http.status.server_error.content"));
		return new ResponseEntity<String>(body, status);
	}

	/**
	 * 获取错误编码
	 * 
	 * @param request
	 * @return HttpStatus
	 */
	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	/**
	 * 实现错误路径,暂时无用
	 * 
	 * @return String
	 */
	@Override
	public String getErrorPath() {
		return null;
	}
}
