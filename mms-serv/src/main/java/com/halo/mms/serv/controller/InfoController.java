package com.halo.mms.serv.controller;

import com.halo.mms.common.domain.WxSendMsg;
import com.halo.mms.repo.model.UserContactInfoDO;
import com.halo.mms.serv.core.SelContextHolder;
import com.halo.mms.serv.mq.KafkaMqProducer;
import com.halo.mms.serv.util.JsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/mms")
public class InfoController {

    @Resource
    private KafkaMqProducer wxPusherProducer;

    @RequestMapping(value = "/api/info", method = RequestMethod.GET)
    @ResponseBody
    public Object info() {
        return "1";
    }
}