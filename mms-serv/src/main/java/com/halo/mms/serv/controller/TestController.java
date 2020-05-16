package com.halo.mms.serv.controller;

import com.halo.mms.serv.exception.BadRequestException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @RequestMapping("/**")
    @ResponseBody
    public Object all() {
        throw new BadRequestException(400, null);
    }
}
