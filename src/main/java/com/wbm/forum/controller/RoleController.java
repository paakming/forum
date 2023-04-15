package com.wbm.forum.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbm.forum.common.Code;
import com.wbm.forum.common.Result;
import com.wbm.forum.entity.*;
import com.wbm.forum.entity.vo.RoleMenuVO;
import com.wbm.forum.entity.vo.UserRoleVO;
import com.wbm.forum.mapper.MenuMapper;
import com.wbm.forum.mapper.RoleMapper;
import com.wbm.forum.mapper.UserRoleMapper;
import com.wbm.forum.service.RoleMenuService;
import com.wbm.forum.service.RoleService;
import com.wbm.forum.service.UserRoleService;
import com.wbm.forum.service.UserService;
import com.wbm.forum.utils.BeanCopyUtils;
import com.wbm.forum.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2023/3/13 0:00
 */
@RestController
@RequestMapping("/role")
@PreAuthorize("hasAuthority('system:role:manage')")
public class RoleController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user")
    public Result makeRoleForUser(@RequestBody User user){
        Boolean role = roleService.makeRoleForUser(user);
        if (role){
            userService.updateById(user);
            redisUtils.delete("login"+user.getUid());
            User byId = userService.getById(user.getUid());
            List<String> permission = menuMapper.selectPermissionByUid(byId.getUid());
            List<String> path = menuMapper.selectPathByUid(byId.getUid())
                    .stream().filter(Objects::nonNull).collect(Collectors.toList());
            List<String> component = menuMapper.selectComponentByUid(byId.getUid())
                    .stream().filter(Objects::nonNull).collect(Collectors.toList());
            SecurityUser securityUser = new SecurityUser(byId,permission,path,component);
            redisUtils.set("login-"+user.getUid(), JSON.toJSONString(securityUser),60*24L);
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", securityUser.getUser());
            map.put("permission", securityUser.getPermission());
            map.put("path", securityUser.getPath());
            map.put("component", securityUser.getComponent());
            return Result.success(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg(),map);
        }else {
            return Result.error(Code.ERROR.getCode(),Code.ERROR.getMsg());
        }
    }

    @GetMapping("/userRole")
    public Result getAllRoleForUser(@PathParam("pageNum") Integer pageNum, @PathParam("pageSize") Integer pageSize){
        IPage<UserRoleVO> userRoleVOIPage = roleMapper.selectUserRole(new Page<>(pageNum, pageSize));
        int total = (int) userRoleVOIPage.getTotal();
        List<UserRoleVO> records = userRoleVOIPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("userRole", records);
        return Result.success(Code.SUCCESS.getCode(),"",map);
    }

    @DeleteMapping("/{roleId}/{menuId}")
    public Result del(@PathVariable Integer roleId,@PathVariable Integer menuId){
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,roleId)
                .eq(RoleMenu::getMenuId,menuId);
        boolean remove = roleMenuService.remove(wrapper);
        if (remove){
            return Result.success(Code.SUCCESS.getCode(),"");
        }else {
            return Result.error(Code.ERROR_DELETE.getCode(), Code.ERROR_DELETE.getMsg());
        }

    }
    @PutMapping("/{uid}/{roleId}")
    public Result changeRole(@PathVariable("uid") Integer uid,@PathVariable("roleId") Integer roleId){
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId).setUid(uid);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUid,uid);
        boolean update = userRoleService.saveOrUpdate(userRole, wrapper);
        if (update){
            return Result.success(Code.SUCCESS.getCode(),"修改成功");
        }else {
            return Result.error(Code.ERROR_UPDATE.getCode(), Code.ERROR_UPDATE.getMsg());
        }
    }

    @DeleteMapping("/{uid}")
    public Result delUserRole(@PathVariable("uid") Integer uid){
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUid,uid);
        boolean remove = userRoleService.remove(wrapper);
        if (remove){
            return Result.success(Code.SUCCESS.getCode(),"删除成功");
        }else {
            return Result.error(Code.ERROR_DELETE.getCode(), Code.ERROR_DELETE.getMsg());
        }

    }
}
