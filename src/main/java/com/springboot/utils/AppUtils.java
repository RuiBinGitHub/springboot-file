package com.springboot.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AppUtils {

	/** 获取request */
	public static HttpServletRequest getRequest() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
		return request;
	}

	/** 获取response */
	public static HttpServletResponse getResponse() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = ((ServletRequestAttributes) attributes).getResponse();
		return response;
	}

	/** 获取session */
	public static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession(true);
		return session;
	}

	/** 添加数据至session */
	public static void pushMap(String key, Object value) {
		HttpSession session = getSession();
		session.setAttribute(key, value);
	}

	/** 从session获取数据 */
	public static Object findMap(String key) {
		HttpSession session = getSession();
		return session.getAttribute(key);
	}

	/** 从session移除数据 */
	public static void removeMap(String key) {
		HttpSession session = getSession();
		session.removeAttribute(key);
	}
	
}
