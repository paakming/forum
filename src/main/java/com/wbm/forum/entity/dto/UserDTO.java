package com.wbm.forum.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.wbm.forum.entity.Menu;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author：Ming
 * @Date: 2023/3/3 23:41
 */
@Data
public class UserDTO {

    private Integer uid;

    private String username;

    private String nickname;

    private String sex;

    private String phone;

    private String avatar;

    private String email;

    private String identity;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date birthday;

}
