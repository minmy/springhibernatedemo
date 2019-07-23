package com.xinmy.core.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义认证入口点
 *
 * @author lijianxin
 */
public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * 构造方法
     *
     * @param loginUrl 登录页
     */
    public CustomAuthenticationEntryPoint(String loginUrl) {
        super(loginUrl);
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) {
        return super.determineUrlToUseForThisRequest(req, res, exception);
    }
}