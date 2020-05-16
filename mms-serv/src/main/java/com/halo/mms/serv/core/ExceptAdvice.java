package com.halo.mms.serv.core;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(value = "com.halo.mms.serv.controller")
public class ExceptAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object busi(BusinessException except) {
        return BaseResult.build().buildCode(except.getCode()).buildMsg(except.getMsg());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object ille(IllegalArgumentException except) {
        return BaseResult.build().buildCode(400).buildMsg(except.getMessage());
    }

}
