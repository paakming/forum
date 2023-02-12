package com.wbm.forum.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Comment implements Serializable {
    /**
     * 自增id评论id
     */
    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    /**
     * 帖子id
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 楼层
     */
    @TableField(value = "floor")
    private Integer floor;

    /**
     * 回复人
     */
    @TableField(value = "reply_id")
    private Integer replyId;

    /**
     * 被回复人
     */
    @TableField(value = "target_id")
    private Integer targetId;

    /**
     * 子评论标记
     */
    @TableField(value = "sub_id")
    private Integer subId;

    /**
     * 回复内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 回复时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "is_delete")
    private String isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}