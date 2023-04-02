package com.wbm.forum.controller;


import cn.hutool.core.util.StrUtil;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Ming
 * @Date: 2022/11/9 20:14
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping(value = "/login")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping(value = "/logout")
    public Result logout(){
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = "login-"+securityUser.getUser().getUid();
        redisUtils.delete(login);
        return Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg());
    }

}
