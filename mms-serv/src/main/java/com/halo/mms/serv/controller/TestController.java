package com.halo.mms.serv.controller;

import com.github.davidmarquis.redisq.producer.DefaultMessageProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class TestController {

    @Resource
    private DefaultMessageProducer<String> wxPushProducer;

    @RequestMapping(value = "/test/api/mms/mq/produce", method = RequestMethod.GET)
    @ResponseBody
    public Object testSend() throws Exception {
       //wxPusherProducer.sendMsg("1");
        wxPushProducer.submit("{}");
       return "1";
    }
}
