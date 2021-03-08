package com.halo.mms.common.exception;

import lombok.Getter;

@Getter
public class MmsException extends RuntimeException {

    private int code = 1000;

    public MmsException(String msg) {
        super(msg);
    }

    public MmsException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
