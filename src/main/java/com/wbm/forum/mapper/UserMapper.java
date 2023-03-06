package com.wbm.forum.mapper;

import com.wbm.forum.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author Ming
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-03-06 14:01:02
* @Entity com.wbm.forum.entity.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

}




