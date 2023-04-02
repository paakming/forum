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
 * @TableName sub_comment
 */
@TableName(value ="sub_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SubComment implements Serializable {
    /**
     * 自增id
     */
    @TableId(value = "sub_id", type = IdType.AUTO)
    private Integer subId;

    /**
     * 从属评论的id
     */
    @TableField(value = "root_id")
    private Integer rootId;

    /**
     * 回复人的id
     */
    @TableField(value = "reply_id")
    private Integer replyId;

    /**
     * 被回复人的id
     */
    @TableField(value = "target_id")
    private Integer targetId;

    /**
     * 回复内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 0：未删除，1：删除
     */
    @TableField(value = "id_deleted")
    private String idDeleted;

    /**
     * 
     */
    @TableField(value = "likes")
    private Long likes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}