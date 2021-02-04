package com.halo.mms.serv.exception;

import lombok.Getter;

@Getter
public abstract class AbstractMmsException extends BusinessException {

    protected int httpCode;

    public AbstractMmsException(int code, String msg, int httpCode) {
        super(code, msg);
        this.httpCode = httpCode;
    }
}
