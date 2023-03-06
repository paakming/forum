package com.wbm.forum.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author：Ming
 * @Date: 2022/11/10 22:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PostVO {
    private Integer pid;

    private Integer uid;

    private String nickname;

    private String avatar;

    private String title;

    private String content;

    private String isTop;

    private String type;

    private Integer commentNum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
