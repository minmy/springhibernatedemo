package com.xinmy.core.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义CSRF拦截地址匹配器
 * 
 * @author 杨海彬
 */
public class CustomCsrfMatcher implements RequestMatcher {

	/** 不拦截的请求方法 */
	private final Set<String> allowedMethods = new HashSet<String>(Arrays.asList("GET"));

	/** 不拦截的URL */
	private Set<String> allowedUrls = new HashSet<String>();

	/**
	 * 设置不拦截的URL
	 * 
	 * @param allowedUrls 不拦截的URL
	 */
	public void setAllowedUrls(Set<String> allowedUrls) {
		this.allowedUrls = allowedUrls;
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		if (this.allowedMethods.contains(request.getMethod())) {
			return false;
		} else {
			String url = request.getRequestURI().replaceFirst(request.getContextPath(), "");
			if (this.allowedUrls.contains(url)) {
				return false;
			}
		}
		return true;
	}
}