package com.wbm.forum.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author：Ming
 * @Date: 2022/11/10 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentVO {

    private Integer cid;

    private Integer pid;

    private Integer floor;

    private Integer subId;

    private String avatar;

    private Integer replyId;

    private String replyName;

    private String targetName;

    private String content;

    private LocalDateTime createTime;

    private List<CommentVO> subComment;


}
