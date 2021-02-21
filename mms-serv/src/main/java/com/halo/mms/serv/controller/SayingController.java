package com.halo.mms.serv.controller;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.serv.result.SayingInfo;
import com.halo.mms.serv.service.api.SayingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/mms/data")
public class SayingController {

    @Resource
    private SayingService sayingService;

    @RequestMapping("/saying/random")
    @ResponseBody
    public Object randomSaying() {
        return BaseResult.getSucc(SayingInfo.builder().content(sayingService.randSaying()).build());
    }
}
