package com.wbm.forum.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SubCommentVO implements Serializable {

    private Integer subId;

    private Integer rootId;

    private String avatar;

    private Integer replyId;

    private String replyName;

    private Integer targetId;

    private String targetName;

    private String content;

    private Integer likes;

    private String isLike;

    private LocalDateTime createTime;

}