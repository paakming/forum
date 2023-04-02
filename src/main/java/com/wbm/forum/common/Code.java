package com.wbm.forum.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author：Ming
 * @Date: 2023/3/4 23:24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Code {
    LOGIN_FAIL(401,"账号或密码错误"),
    SUCCESS(2000,"操作成功"),
    USER_NON_EXISTENT(4000,"用户不存在"),
    ERROR(5000,"操作失败"),
    ERROR_ADD(5000,"添加失败"),
    ERROR_UPDATE(5000,"修改失败"),
    ERROR_DELETE(5000,"删除失败"),
    ERROR_QUERY(5000,"查询失败"),
    VERIFY_CODE_SUCCESS(2000,"获取验证码成功"),
    VERIFY_CODE_FAIL(5000,"获取验证码失败"),
    VERIFY_CODE_EXPIRED(5000,"验证码已失效,请重新获取验证码"),
    VERIFICATION_FAIL(5000,"验证失败"),
    VERIFICATION_SUCCESS(2000,"验证成功"),
    PASSWORD_OLD_WRONG(5000,"旧密码错误"),
    PASSWORD_UPDATE_FAIL(5000,"修改密码失败"),
    PASSWORD_TWICE_FAIL(5000,"两次密码输入不一致"),
    REGISTER_ERROR(5000,"注册失败"),
    ;

    private int code;
    private String msg;

}
