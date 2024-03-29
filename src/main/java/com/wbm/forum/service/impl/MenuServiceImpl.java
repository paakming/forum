package com.wbm.forum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbm.forum.entity.Menu;
import com.wbm.forum.entity.vo.RoleMenuVO;
import com.wbm.forum.service.MenuService;
import com.wbm.forum.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author Ming
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2023-02-28 21:17:38
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public IPage<RoleMenuVO> selectMenuByRoleId(IPage<?> page, Integer roleId) {
        return menuMapper.selectMenuByRoleId(page,roleId);
    }
}




