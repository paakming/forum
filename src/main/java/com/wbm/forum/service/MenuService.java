package com.wbm.forum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wbm.forum.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbm.forum.entity.vo.RoleMenuVO;

import java.util.Map;

/**
* @author Ming
* @description 针对表【menu】的数据库操作Service
* @createDate 2023-02-28 21:17:38
*/
public interface MenuService extends IService<Menu> {
    IPage<RoleMenuVO> selectMenuByRoleId(IPage<?> page, Integer roleId);
}
