package com.wbm.forum.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author：Ming
 * @Date: 2023/3/13 17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRoleVO {

    private int uid;

    private String username;

    private String roleName;

}
