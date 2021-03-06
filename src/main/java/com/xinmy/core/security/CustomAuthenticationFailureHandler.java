package com.xinmy.core.security;

import com.xinmy.core.Environment;
import com.xinmy.core.exception.CustomAuthenticationException;
import com.xinmy.core.http.HttpRequestWrapper;
import com.xinmy.core.http.HttpResponseWrapper;
import com.xinmy.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录认证失败处理器
 *
 * @author 杨海彬
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) throws IOException, ServletException {
        HttpRequestWrapper request = new HttpRequestWrapper(req);
        HttpResponseWrapper response = new HttpResponseWrapper(res);
        String message = null;
        if (exception instanceof CustomAuthenticationException) {
            message = exception.getMessage();
        } else {
            LOGGER.error("登录异常，请求参数为[" + request + "]", exception);
            message = "登录失败，服务器内部错误，请稍后再试...";
        }
        if (request.isAjaxRequest()) {
            response.outputJson(false, message);
        } else {
            String loginUrl = request.getString("loginUrl");
            if (StringUtils.isEmpty(loginUrl)) {
                CustomAuthenticationEntryPoint customAuthenticationEntryPoint = Environment.getBean("customAuthenticationEntryPoint", CustomAuthenticationEntryPoint.class);
                loginUrl = request.getContextPath() + customAuthenticationEntryPoint.determineUrlToUseForThisRequest(request, response, exception);
            }
            loginUrl += (loginUrl.contains("?") ? "&" : "?") + "message=" + CommonUtils.encode(message);
            response.sendRedirect(loginUrl);
        }
    }
}