package com.wbm.forum.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wbm.forum.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wbm.forum.entity.vo.UserRoleVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Ming
* @description 针对表【role】的数据库操作Mapper
* @createDate 2023-03-13 17:31:13
* @Entity com.wbm.forum.entity.Role
*/
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    IPage<UserRoleVO> selectUserRole(IPage<?> page);
    //List<UserRoleVO> selectUserRole();
}




