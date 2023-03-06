package com.wbm.forum.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.vo.UserVO;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.MenuService;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.JwtUtils;
import com.wbm.forum.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Ming
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-11-12 14:52:05
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (ObjectUtil.isNull(authentication)){
            throw new MyServiceException(Code.LOGIN_FAIL.getCode(),Code.LOGIN_FAIL.getMsg());
        }
        //获取认证用户
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        String uid = securityUser.getUser().getUid().toString();
        String jwt = JwtUtils.getJwtToken(uid);
        String str = JSON.toJSONString(securityUser);
        redisUtils.set("login-"+uid, str,60*24L);
        redisUtils.set("user"+uid,JSON.toJSONString(user));
        HashMap<String, Object> map = new HashMap<>();
//        UserVO userVO = BeanCopyUtils.copyBean(securityUser.getUser(), UserVO.class);
        map.put("user", securityUser.getUser());
        map.put("permission", securityUser.getPermission());
        map.put("path", securityUser.getPath());
        map.put("component", securityUser.getComponent());
        map.put("token",jwt);
        return Result.success(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(),map);
    }



    @Override
    public Map<String, Object> getAllUser(Integer pageNum, Integer pageSize) {
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum,pageSize),null);
        List<User> records = userPage.getRecords();
        int total = (int) userPage.getTotal();
        List<UserVO> userVOS = BeanCopyUtils.copyBeanList(records, UserVO.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userList", userVOS);
        map.put("total",total);
        return map;
    }

    @Override
    public Boolean UpdateUser(User user) {
        return save(user);
    }

    @Override
    public Boolean changePwd(String oldPassword, String newPassword, String repeatPassword) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = securityUser.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        //encoder.matches(”明文密码“,加密后的密码)
        boolean matches = encoder.matches(oldPassword,password);
        if (matches){
            String encode = encoder.encode(newPassword);
            User user = securityUser.getUser();
            user.setPassword(encode);
            int update = userMapper.updateById(user);
            if (1 == update){
                String login = "login-"+securityUser.getUser().getUid();
                redisUtils.delete(login);
                return true;
            }
        }else {
            throw new MyServiceException(ResultCode.CODE_500,"旧密码错误");
        }
        return false;
    }

    @Override
    public Boolean forgetPassword(String username, String password, String email, String code) {
        String redisCode = redisUtils.get(email);
        if (StrUtil.isBlank(redisCode)){
            throw new MyServiceException(Code.VERIFY_CODE_EXPIRED.getCode(),Code.VERIFY_CODE_EXPIRED.getMsg());
        }
        if (!redisCode.equals(code)){
            throw new MyServiceException(Code.VERIFICATION_FAIL.getCode(), Code.VERIFICATION_FAIL.getMsg());
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        User user = new User();
        user.setPassword(encode);
        queryWrapper
                .eq(User::getUsername,username)
                .eq(User::getEmail,email);
        boolean update = update(user, queryWrapper);
        if (update){
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(User user) {
        checkUsernameUnique(user.getUsername());
        checkNicknameUnique(user.getNickname());
        if (StrUtil.isBlank(user.getPassword())){
            user.setPassword(user.getUsername());
        }
        log.info("user---{}",user);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(user.getPassword());
        user.setPassword(encode);
        return save(user);
    }

    public Boolean checkUsernameUnique(String username){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(user)){
            return true;
        }else {
            throw new MyServiceException(Code.REGISTER_ERROR.getCode(), "用户名已存在");
        }
    }
    public Boolean checkNicknameUnique(String nickname){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,nickname);
        User user = userMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(user)){
            return true;
        }else {
            throw new MyServiceException(Code.REGISTER_ERROR.getCode(), "昵称已存在");
        }
    }
}




