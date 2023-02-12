package com.wbm.forum.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：Ming
 * @Date: 2022/11/9 20:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Integer code, String msg, Object data){
        return new Result(code,msg,data);
    }
    public static Result error(Integer code, String msg, Object data){
        return new Result(code,msg,data);
    }

}
