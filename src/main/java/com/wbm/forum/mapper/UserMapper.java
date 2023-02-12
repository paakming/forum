package com.wbm.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wbm.forum.entity.User;
import org.springframework.stereotype.Repository;

/**
* @author Ming
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-11-12 14:52:05
* @Entity com.ss.security.entity.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

}




