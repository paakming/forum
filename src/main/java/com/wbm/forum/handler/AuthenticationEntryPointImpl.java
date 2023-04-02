package com.wbm.forum.handler;

import com.alibaba.fastjson.JSON;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：Ming
 * @Date: 2022/11/8 21:19
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) {
        //
        Result result ;
        if (authenticationException instanceof BadCredentialsException) {
            result = new Result(HttpStatus.UNAUTHORIZED.value(), authenticationException.getMessage());
        } else if (authenticationException instanceof InsufficientAuthenticationException) {
            result = new Result(HttpStatus.UNAUTHORIZED.value(), authenticationException.getMessage());
        } else {
            result = new Result(Code.LOGIN_FAIL.getCode(), "认证失败");
        }
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
