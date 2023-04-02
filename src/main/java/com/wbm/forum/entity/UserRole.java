package com.wbm.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName user_role
 */
@TableName(value ="user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRole implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "uid")
    private Integer uid;

    /**
     * 
     */
    @TableField(value = "role_id")
    private Integer roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}