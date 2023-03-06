package com.wbm.forum.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.utils.JwtUtils;
import com.wbm.forum.utils.RedisUtils;
import com.wbm.forum.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：Ming
 * @Date: 2022/11/6 23:58
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtils redisUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)){
            filterChain.doFilter(request,response);
            return ;
        }
        String uid;
        //解析token
        //要异常处理
        Claims claims = null;
        try {
            claims = JwtUtils.parseJwtToken(token);
        }catch (JwtException e){
            Result result = new Result(HttpStatus.UNAUTHORIZED.value(),"请重新登录");
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }
        uid = (String) claims.get("uid");
        String key = "login-"+uid;
        String s = redisUtils.strGet(key);
        if (ObjectUtil.isNull(s)){
            Result result = new Result(HttpStatus.UNAUTHORIZED.value(),"请重新登录");
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }
        SecurityUser securityUser = JSON.parseObject(s,SecurityUser.class);
        if (ObjectUtil.isNull(securityUser)){
            Result result = new Result(HttpStatus.UNAUTHORIZED.value(),"请重新登录");
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(securityUser,null,securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
