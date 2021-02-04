package com.halo.mms.serv.core;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.exception.AbstractMmsException;
import com.halo.mms.serv.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(value = {"com.halo.mms.serv.controller", "com.halo.mms.serv.core"})
public class ExceptAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object busi(BusinessException except) {
        log.error("", except);
        return BaseResult.build().buildCode(except.getCode()).buildMsg(except.getMsg());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object ille(IllegalArgumentException except) {
        log.error("", except);
        return BaseResult.build().buildCode(400).buildMsg(except.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object re(Exception e) {
        log.error("", e);
        return BaseResult.build().buildCode(500).buildMsg("服务繁忙");
    }

    @ExceptionHandler(AbstractMmsException.class)
    public Object ae(AbstractMmsException e) {
        log.error("", e);
        HttpStatus httpStatus = HttpStatus.valueOf(e.getHttpCode());
        return new ResponseEntity<>(BaseResult.build().buildCode(e.getCode()).buildMsg(e.getMsg()), httpStatus);
    }
}
