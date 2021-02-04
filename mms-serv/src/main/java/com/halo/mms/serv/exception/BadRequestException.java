package com.halo.mms.serv.exception;

public class BadRequestException extends AbstractMmsException {

    public BadRequestException(Integer code, String msg) {
        super(code, msg, 400);
    }
}
