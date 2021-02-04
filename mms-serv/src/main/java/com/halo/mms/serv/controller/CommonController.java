package com.halo.mms.serv.controller;

import com.halo.mms.serv.core.SelContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mms")
public class CommonController {

    @GetMapping("/health")
    @ResponseBody
    public Object health() {
        return "I am healthy";
    }

    @RequestMapping("/heart")
    @ResponseBody
    public Object heart() {
        return "1";
    }

}
