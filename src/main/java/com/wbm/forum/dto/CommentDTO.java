package com.wbm.forum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CommentDTO {

    private Integer cid;

    private Integer pid;

    private Integer floor;

    private Integer subId;

    private String avatar;

    private String replyName;

    private String targetName;

    private String content;

    @JsonFormat(pattern = " yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    private List<CommentDTO> subComment;


}
