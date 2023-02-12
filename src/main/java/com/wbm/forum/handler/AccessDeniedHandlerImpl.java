package com.wbm.forum.handler;

import cn.hutool.json.JSONUtil;
import com.wbm.forum.common.Result;
import com.wbm.forum.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：Ming
 * @Date: 2022/11/8 22:00
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Result result = new Result(HttpStatus.FORBIDDEN.value(),"权限不足","");
        //处理异常
        String json = JSONUtil.toJsonStr(result);
        WebUtils.renderString(httpServletResponse,json);
    }
}
