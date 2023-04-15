package com.wbm.forum.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.dto.UserDTO;
import com.wbm.forum.entity.vo.UserVO;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.HashMap;
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
        if (StrUtil.isBlank(user.getPassword()) ){
            user.setPassword("000000");
        }
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
            redisUtils.hDelete("userMap",uid.toString());
            return Result.success(Code.SUCCESS.getCode(),"删除成功");
        }
        return Result.error(Code.ERROR_DELETE.getCode(),Code.ERROR_DELETE.getMsg());
    }
    @PostMapping(value = "/del/{ids}")
    public Result deleteUsers(@PathVariable List<Integer> ids){
        boolean b = userService.removeByIds(ids);
        if (b){
            ids.forEach(i -> redisUtils.hDelete("userMap",i.toString()));
            return Result.success(Code.SUCCESS.getCode(),"删除成功");
        }
        return Result.error(Code.ERROR_DELETE.getCode(),Code.ERROR_DELETE.getMsg());
    }
    @PutMapping
    public Result update(@RequestBody UserDTO userDTO){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickname,userDTO.getNickname());
        User one = userService.getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(one)&& !(one.getUid().equals(userDTO.getUid()))){
            throw new MyServiceException(Code.ERROR_UPDATE.getCode(),"昵称已存在");
        }
        User user = BeanCopyUtils.copyBean(userDTO, User.class);
        boolean res = userService.updateById(user);
        if (res){
            User byId = userService.getById(user.getUid());
            redisUtils.hset("userMap",byId.getUid().toString(),byId);
            return Result.success(Code.SUCCESS.getCode(), "修改成功",byId);
        }else {
            return Result.error(Code.ERROR_UPDATE.getCode(), Code.ERROR_UPDATE.getMsg());
        }
    }

    @PostMapping("/changPwd")
    public Result changePassword(@RequestBody  JSONObject jsonObject){
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");
        String repeatPassword = jsonObject.getString("repeatPassword");
        if (StrUtil.isBlank(oldPassword) || StrUtil.isBlank(newPassword) || StrUtil.isBlank(repeatPassword)){
            Result.error(Code.PASSWORD_UPDATE_FAIL.getCode(), Code.PASSWORD_UPDATE_FAIL.getMsg());
        }
        if (!newPassword.equals(repeatPassword)){
            return Result.error(Code.PASSWORD_TWICE_FAIL.getCode(), Code.PASSWORD_TWICE_FAIL.getMsg());
        }
        Boolean changePwd = userService.changePwd(oldPassword, newPassword, repeatPassword);
        if (changePwd){
            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String login = "login-"+securityUser.getUser().getUid();
            redisUtils.delete(login);
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
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(email) || StrUtil.isBlank(code)){
            return Result.error(Code.ERROR_UPDATE.getCode(), Code.ERROR_UPDATE.getMsg());
        }
        Boolean flag = userService.forgetPassword(username, password, email, code);
        if (flag){
            return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg());
        }
        return Result.error(Code.ERROR_UPDATE.getCode(), Code.ERROR_UPDATE.getMsg());
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if (StrUtil.isNotBlank(user.getUsername()) && StrUtil.isNotBlank(user.getPassword())
                && StrUtil.isNotBlank(user.getNickname())&&StrUtil.isNotBlank(user.getEmail())){
            Boolean register = userService.register(user);
            if (register){
                return Result.success(Code.SUCCESS.getCode(), "注册成功");
            }
        }
        return Result.error(Code.REGISTER_ERROR.getCode(), Code.REGISTER_ERROR.getMsg());
    }

    @PostMapping("/searchByTime")
    public Result searchByTime(@RequestBody JSONObject jsonObject,@PathParam("pageNum") Integer pageNum,@PathParam("pageSize") Integer pageSize){
        Date from = jsonObject.getDate("from");
        Date to = jsonObject.getDate("to");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(User::getCreateTime, from, to);
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), wrapper);
        List<User> list = page.getRecords();
        int total = (int) page.getTotal();
        List<UserVO> userVOS = BeanCopyUtils.copyBeanList(list, UserVO.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("user",userVOS);
        return Result.success(Code.SUCCESS.getCode(),"",map);
    }

    @PostMapping("/searchByUid")
    public Result searchByPid(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUid,user.getUid());
        List<User> list = userService.list(wrapper);
        List<UserVO> userVOS = BeanCopyUtils.copyBeanList(list, UserVO.class);
        return Result.success(Code.SUCCESS.getCode(),"",userVOS);
    }

}
