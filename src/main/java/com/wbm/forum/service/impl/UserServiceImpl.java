package com.wbm.forum.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.common.Result;
import com.wbm.forum.common.ResultCode;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.mapper.UserMapper;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.JwtUtils;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.HashMap;

/**
* @author Ming
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-11-12 14:52:05
*/
@Service
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
            throw new RuntimeException("登录失败");
        }
        //获取认证用户
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        String uid = securityUser.getUser().getUid().toString();
        String jwt = JwtUtils.getJwtToken(uid);
        redisUtils.set("login-"+uid, JSONUtil.toJsonStr(securityUser),60*24L);
        HashMap<String, String> map = new HashMap<>();
        map.put("user", JSONUtil.toJsonStr(securityUser.getUser()));
        map.put("permission", JSONUtil.toJsonStr(securityUser.getPermission()));
        map.put("token",jwt);
        return Result.success(ResultCode.CODE_200,"success",map);
    }

    @Override
    public User selectByUid(Integer uid) {
        return userMapper.selectById(uid);
    }
}




