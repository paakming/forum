package com.wbm.forum.entity;

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
import lombok.experimental.Accessors;

/**
 * 
 * @TableName post
 */
@TableName(value ="post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Post implements Serializable {
    /**
     * 自增id
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 楼主
     */
    @TableField(value = "uid")
    private Integer uid;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 发帖时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 是否置顶，1为置顶
     */
    @TableField(value = "is_top")
    private String isTop;

    /**
     * 是否删除，1为删除
     */
    @TableField(value = "is_delete")
    private String isDelete;

    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "likes")
    private Long likes;

    /**
     * 
     */
    @TableField(value = "views")
    private Long views;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}