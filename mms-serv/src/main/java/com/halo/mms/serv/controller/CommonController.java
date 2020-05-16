package com.halo.mms.serv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mms")
public class CommonController {

    @GetMapping("/health")
    @ResponseBody
    public Object health() {
        return "true";
    }

}
