package com.wbm.forum.service;

import com.wbm.forum.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.entity.User;

/**
* @author Ming
* @description 针对表【role】的数据库操作Service
* @createDate 2023-03-13 17:31:13
*/
public interface RoleService extends IService<Role> {
    Boolean makeRoleForUser(User user);
}
