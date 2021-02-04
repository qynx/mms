package com.halo.mms.serv.controller;

import com.halo.mms.common.domain.WxSendMsg;
import com.halo.mms.serv.mq.KafkaMqProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource(name = "testProducer")
    private DefaultMQProducer mq;
    @Resource
    private KafkaMqProducer wxPusherProducer;

    @RequestMapping(value = "/api/mms/test/mq/produce", method = RequestMethod.GET)
    @ResponseBody
    public Object testSend() throws Exception {
       return "1";
    }
}
