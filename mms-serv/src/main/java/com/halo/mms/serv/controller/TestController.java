package com.halo.mms.serv.controller;

import com.github.davidmarquis.redisq.producer.DefaultMessageProducer;
import com.halo.mms.common.mq.RedisMqProducer;
import com.halo.mms.common.service.NovelCrawlService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
public class TestController {

    @Resource
    private DefaultMessageProducer<String> wxPushProducer;
    @Resource
    private NovelCrawlService novelCrawlService;

    @RequestMapping("/test/api/mms/crawl/submit")
    @ResponseBody
    public Object crawlSubmit(HttpServletRequest servletRequest) {
        return novelCrawlService.start(servletRequest.getParameter("url"));
    }

    @RequestMapping(value = "/test/api/mms/mq/produce", method = RequestMethod.GET)
    @ResponseBody
    public Object testSend() throws Exception {
       //wxPusherProducer.sendMsg("1");
        return "1";
    }
}
