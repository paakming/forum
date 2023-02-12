package com.wbm.forum.controller;


import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('system:post:test')")
    @GetMapping(value = "/user/hello")
    public Result hello(){
        return new Result(ResultCode.CODE_200,null,"Hello World");
    }

    @PostMapping(value = "/login")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping(value = "/login/logout")
    public Result logout(){
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = "login-"+securityUser.getUser().getUid();
        redisUtils.delete(login);
        return Result.success(ResultCode.CODE_200,"退出成功",null);
    }

}
