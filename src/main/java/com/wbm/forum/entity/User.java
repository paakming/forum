package com.wbm.forum.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * uid
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private String isDelete;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 绑定学号或工号
     */
    @TableField(value = "identity")
    private String identity;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 出生日期
     */
    @TableField(value = "birthday")
    @JSONField(format="yyyy-MM-dd")
    private Date birthday;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}