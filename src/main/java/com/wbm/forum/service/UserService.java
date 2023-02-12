package com.wbm.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.User;

/**
* @author Ming
* @description 针对表【user】的数据库操作Service
* @createDate 2022-11-12 14:52:05
*/
public interface UserService extends IService<User> {
    Result login(User user);
    User selectByUid(Integer uid);
}
