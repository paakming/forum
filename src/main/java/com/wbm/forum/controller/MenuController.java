package com.wbm.forum.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.Menu;
import com.wbm.forum.entity.Role;
import com.wbm.forum.entity.RoleMenu;
import com.wbm.forum.entity.User;
import com.wbm.forum.entity.vo.RoleMenuVO;
import com.wbm.forum.mapper.RoleMapper;
import com.wbm.forum.service.MenuService;
import com.wbm.forum.service.RoleMenuService;
import com.wbm.forum.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

/**
 * @Author：Ming
 * @Date: 2023/3/13 22:25
 */
@RestController
@RequestMapping("/menu")
@PreAuthorize("hasAuthority('system:menu:manage')")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping
    public Result getAllMenu(){
        List<Menu> list = menuService.list();
        return Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg(),list);
    }
    @GetMapping("/role/{roleId}/{pageNum}/{pageSize}")
    public Result getRoleForMenu(@PathVariable("roleId") Integer roleId, @PathVariable("pageNum") Integer pageNum,
                                 @PathVariable("pageSize") Integer pageSize){
        IPage<RoleMenuVO> roleMenuVOIPage = menuService.selectMenuByRoleId(new Page<>(pageNum, pageSize), roleId);
        int total = (int) roleMenuVOIPage.getTotal();
        List<RoleMenuVO> records = roleMenuVOIPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("roleMenu",records);
        return Result.success(Code.SUCCESS.getCode(),"",map);
    }

    @PostMapping("/add")
    public Result addRoleMenu(@RequestBody RoleMenu roleMenu){
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getMenuId,roleMenu.getMenuId());
        Menu one = menuService.getOne(wrapper);
        if (ObjectUtil.isNull(one)){
            return Result.error(Code.ERROR_ADD.getCode(), Code.ERROR_ADD.getMsg());
        }
        boolean save = roleMenuService.save(roleMenu);
        if (save){
            return Result.success(Code.SUCCESS.getCode(),"添加成功");
        }else {
            return Result.error(Code.ERROR_ADD.getCode(), Code.ERROR_ADD.getMsg());
        }

    }
}
