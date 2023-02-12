package com.wbm.forum.handler;

import cn.hutool.json.JSONUtil;
import com.wbm.forum.common.Result;
import com.wbm.forum.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：Ming
 * @Date: 2022/11/8 21:19
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result result = new Result(HttpStatus.UNAUTHORIZED.value(),"用户认证失败，请登录","");
        //处理异常
        String json = JSONUtil.toJsonStr(result);
        WebUtils.renderString(httpServletResponse,json);
    }
}
