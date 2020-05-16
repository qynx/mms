package com.halo.mms.serv.exception;

public class CallException extends RuntimeException {

    private Integer code;

    public CallException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
