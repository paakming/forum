package com.wbm.forum.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.entity.Role;
import com.wbm.forum.entity.SecurityUser;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.UserRole;
import com.wbm.forum.mapper.MenuMapper;
import com.wbm.forum.service.RoleService;
import com.wbm.forum.mapper.RoleMapper;
import com.wbm.forum.service.UserRoleService;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Ming
* @description 针对表【role】的数据库操作Service实现
* @createDate 2023-03-13 17:31:13
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Boolean makeRoleForUser(User user) {
        UserRole userRole = new UserRole();
        userRole.setUid(user.getUid());
        //1:超级管理员；2：普通用户；3：管理员
        userRole.setRoleId(2);
        return userRoleService.save(userRole);
    }
}




