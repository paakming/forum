package com.wbm.forum.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.dto.UserDTO;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @Author：Ming
 * @Date: 2023/2/25 14:44
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/all")
    public Result allUser(@PathParam("pageNum") Integer pageNum, @PathParam("pageSize") Integer pageSize){
        Map<String, Object> allUser = userService.getAllUser(pageNum, pageSize);
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(),allUser);
    }

    @PostMapping
    public Result addUser(@RequestBody User user){
        Boolean register = userService.register(user);
        if (register){
            return Result.success(Code.SUCCESS.getCode(),"添加成功");
        }
        return Result.error(Code.ERROR_ADD.getCode(),Code.ERROR_ADD.getMsg());
    }

    @DeleteMapping("/{uid}")
    public Result deleteUser(@PathVariable Integer uid){
        boolean b = userService.removeById(uid);
        if (b){
            return Result.success(Code.SUCCESS.getCode(),"删除成功");
        }
        return Result.error(Code.ERROR_DELETE.getCode(),Code.ERROR_DELETE.getMsg());
    }
    @PostMapping(value = "/del/{ids}")
    public Result deletePosts(@PathVariable List<Integer> ids){
        boolean b = userService.removeByIds(ids);
        if (b){
            return Result.success(Code.SUCCESS.getCode(),"删除成功");
        }
        return Result.error(Code.ERROR_DELETE.getCode(),Code.ERROR_DELETE.getMsg());
    }
    @PutMapping
    public Result update(@RequestBody UserDTO userDTO){
        log.info("user------{}",userDTO);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickname,userDTO.getNickname());
        User one = userService.getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(one)){
            throw new MyServiceException(Code.ERROR_UPDATE.getCode(),"昵称已存在");
        }
        User user = BeanCopyUtils.copyBean(userDTO, User.class);
        boolean res = userService.updateById(user);
        //返回更新后的数据
        if (res){
            return Result.success(Code.SUCCESS.getCode(), "修改成功");
        }else {
            return Result.error(Code.ERROR_UPDATE.getCode(), Code.ERROR_UPDATE.getMsg());
        }
    }
    @PostMapping("/avatar")
    public Result updateAvatar(@RequestBody String avatar){
        System.out.println(avatar);
        if (StrUtil.isBlank(avatar)){
            return Result.error(Code.ERROR.getCode(),"上传头像失败");
        }
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        User user = securityUser.getUser();
        Integer uid = user.getUid();
        user.setAvatar(avatar);
        queryWrapper.eq(User::getUid,uid);
        Boolean res = userService.update(user,queryWrapper);
        if (res){
            securityUser.getUser().setAvatar(user.getAvatar());
            redisUtils.set("login-"+uid,JSON.toJSONString(securityUser));
            redisUtils.set("user"+uid,JSON.toJSONString(securityUser.getUser()));
            return Result.success(Code.SUCCESS.getCode(), "修改成功",securityUser.getUser());
        }else {
            return Result.error(Code.ERROR_UPDATE.getCode(), Code.ERROR_UPDATE.getMsg());
        }
    }
    @PostMapping("/changPwd")
    //@RequestParam("oldPassword") Integer oldPassword, @RequestParam("newPassword") Integer newPassword, @RequestParam("repeatPassword") Integer repeatPassword
    public Result changePassword(@RequestBody  JSONObject jsonObject){
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");
        String repeatPassword = jsonObject.getString("repeatPassword");
        if (!newPassword.equals(repeatPassword)){
            return Result.error(Code.PASSWORD_TWICE_FAIL.getCode(), Code.PASSWORD_TWICE_FAIL.getMsg());
        }
        Boolean changePwd = userService.changePwd(oldPassword, newPassword, repeatPassword);
        if (changePwd){
            return Result.success(Code.SUCCESS.getCode(), "修改密码成功,请重新登录");
        }else {
            return Result.error(Code.PASSWORD_UPDATE_FAIL.getCode(), Code.PASSWORD_UPDATE_FAIL.getMsg());
        }
    }

    @PostMapping("/forget")
    public Result forgetPassword(@RequestBody JSONObject jsonObject){
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        String code = jsonObject.getString("code");
        Boolean flag = userService.forgetPassword(username, password, email, code);
        if (flag){
            return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg());
        }
        return Result.error(Code.ERROR_UPDATE.getCode(), Code.ERROR_UPDATE.getMsg());
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        Boolean register = userService.register(user);
        if (register){
            return Result.success(Code.SUCCESS.getCode(), "注册成功");
        }
        return Result.error(Code.REGISTER_ERROR.getCode(), Code.REGISTER_ERROR.getMsg());
    }
}
