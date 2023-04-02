package com.wbm.forum.mapper;

import com.wbm.forum.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author Ming
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2023-03-12 22:39:14
* @Entity com.wbm.forum.entity.UserRole
*/
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




