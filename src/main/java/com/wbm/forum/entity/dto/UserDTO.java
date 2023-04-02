package com.wbm.forum.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.wbm.forum.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author：Ming
 * @Date: 2023/3/27 16:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO {
    private Integer uid;

    private String username;

    private String nickname;

    private String sex;

    private String phone;

    private String avatar;

    private String email;

    private String identity;

    @JSONField(format="yyyy-MM-dd ")
    private Date birthday;
}
