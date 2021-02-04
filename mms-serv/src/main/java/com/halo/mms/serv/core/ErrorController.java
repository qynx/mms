package com.halo.mms.serv.core;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.exception.AbstractMmsException;
import com.halo.mms.serv.util.JsonUtil;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {

        Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (t instanceof AbstractMmsException) {
            AbstractMmsException a = (AbstractMmsException) t;
            BaseResult baseResult = new BaseResult().buildMsg(a.getMsg()).buildCode(a.getCode());
            return new ResponseEntity<>(JsonUtil.toMap(baseResult), HttpStatus.valueOf(a.getHttpCode()));
        }

        BaseResult baseResult = BaseResult.build().buildCode(500).buildMsg("系统错误");
        return new ResponseEntity<>(JsonUtil.toMap(baseResult), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
