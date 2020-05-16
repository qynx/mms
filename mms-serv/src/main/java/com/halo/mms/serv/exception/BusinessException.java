package com.halo.mms.serv.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private Integer code;
    private String msg;

    public BusinessException() {
        super();
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
