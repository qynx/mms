package com.halo.mms.serv.controller;

import com.github.davidmarquis.redisq.producer.DefaultMessageProducer;
import com.halo.mms.serv.mq.RedisMqConsumer;
import com.halo.mms.serv.mq.RedisMqProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class TestController {

    @Resource
    private DefaultMessageProducer<String> wxPushProducer;

    //@Bean
    public RedisMqProducer testPro() {
        return new RedisMqProducer("ttt-1");
    }

    //@Bean
    public RedisMqConsumer testCon() {
        return new RedisMqConsumer("ttt-1", message -> {
            System.out.println(message.getPayload());
        });
    }

    @Resource
    private RedisMqProducer testPro;

    @RequestMapping(value = "/test/api/mms/mq/produce", method = RequestMethod.GET)
    @ResponseBody
    public Object testSend() throws Exception {
       //wxPusherProducer.sendMsg("1");
        testPro.submit("ggggg");
        return "1";
    }
}
