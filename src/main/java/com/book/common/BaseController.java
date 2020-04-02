package com.book.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.book.api.entity.UserInfo;
import com.book.util.BookConstant;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpSession session;
	
	protected UserInfo getUser() {
		return (UserInfo)this.getSession().getAttribute(BookConstant.USER_SESSION_KEY);
	}

	protected void startPage() {
		this.startPage(10);
	}

	protected void startPage(int defaultPageSize) {
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageHelper.startPage(StringUtils.isBlank(pageNum) ? 1 : Integer.valueOf(pageNum),
		StringUtils.isBlank(pageSize) ? defaultPageSize : Integer.valueOf(pageSize));
	}

	protected HttpServletRequest getRequest() {
		return request;
	}

	protected String getParameter(String name) {
		return request.getParameter(name);
	}

	protected HttpServletResponse getResponse() {
		return response;
	}

	protected HttpSession getSession() {
		return session;
	}

	@ExceptionHandler(Exception.class)
	protected void handleException(Exception e) throws Exception {

		log.error(e.toString(), e);
		JsonResult result = JsonResult.fail(e instanceof LogicException ? e.getMessage() : "系统在处理您的请求时发生异常!");
		String jsonString = JSON.toJSONString(result);
		response.getWriter().write(jsonString);

	}

}
