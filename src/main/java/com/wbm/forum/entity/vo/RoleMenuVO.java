package com.wbm.forum.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author：Ming
 * @Date: 2023/3/13 19:24
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuVO {
    private Integer menuId;

    private String roleName;

    private Integer roleId;

    private String menuName;

    private String path;

    private String component;

    private String perm;
}
