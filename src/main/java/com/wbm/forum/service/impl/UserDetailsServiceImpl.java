package com.wbm.forum.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbm.forum.common.Code;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.exception.MyServiceException;
import com.wbm.forum.mapper.MenuMapper;
import com.wbm.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2022/11/9 21:55
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtil.isNull(user)){
            throw new MyServiceException(Code.USER_NON_EXISTENT.getCode(),Code.USER_NON_EXISTENT.getMsg());
        }
        List<String> permission = menuMapper.selectPermissionByUid(user.getUid());
        List<String> path = menuMapper.selectPathByUid(user.getUid())
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> component = menuMapper.selectComponentByUid(user.getUid())
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        return new SecurityUser(user,permission,path,component);
    }
}
