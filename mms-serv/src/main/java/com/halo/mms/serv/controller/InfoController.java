package com.halo.mms.serv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/mms")
public class InfoController {

    @RequestMapping(value = "/api/info", method = RequestMethod.GET)
    @ResponseBody
    public Object info() {
        return "1";
    }
}