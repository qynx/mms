package com.halo.mms.serv.exception;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(Integer code, String msg) {
        super(code, msg);
    }
}
