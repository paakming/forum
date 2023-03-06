package com.wbm.forum.exception;

import lombok.Getter;

@Getter
public class MyServiceException extends RuntimeException{
    private Integer code;

    public MyServiceException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
