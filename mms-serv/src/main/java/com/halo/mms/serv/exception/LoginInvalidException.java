package com.halo.mms.serv.exception;

public class LoginInvalidException extends AbstractMmsException {

    public LoginInvalidException(int code, String msg) {
        super(code, msg, 401);
    }
}
