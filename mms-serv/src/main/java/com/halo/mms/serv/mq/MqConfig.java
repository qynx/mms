package com.halo.mms.serv.mq;

import com.github.davidmarquis.redisq.RedisMessageQueue;
import com.github.davidmarquis.redisq.consumer.MessageConsumer;
import com.github.davidmarquis.redisq.consumer.MessageListener;
import com.github.davidmarquis.redisq.serialization.PayloadSerializer;
import com.github.davidmarquis.redisq.serialization.StringPayloadSerializer;
import com.halo.mms.common.domain.WxSendMsg;
import com.halo.mms.out.WxPushService;
import com.halo.mms.serv.service.api.UserInfoService;
import com.halo.mms.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Configuration
public class MqConfig {

    @Resource
    private WxPushService wxPushService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisMessageQueue wxPushQueue;

    @Bean
    public PayloadSerializer payloadSerializer() {
        return new StringPayloadSerializer();
    }

    @Bean
    public MessageConsumer<String> wxPushConsumer() {
        MessageConsumer<String> messageConsumer = new MessageConsumer<>();
        messageConsumer.setQueue(wxPushQueue);
        messageConsumer.setConsumerId("1");
        MessageListener<String> messageListener = message -> {
            log.info("redis_consume: {}", message.getPayload());
            onWxPushMessage(message.getPayload());
        };
        messageConsumer.setMessageListener(messageListener);
        return messageConsumer;
    }

    private void onWxPushMessage(String msg) {
        WxSendMsg wxSendMsg = JsonUtil.fromJson(msg, WxSendMsg.class);
        String wxPusherId = Optional.ofNullable(wxSendMsg.getWxPusherId())
            .orElse(userInfoService.queryUser(wxSendMsg.getUid()).getWxPusherUid());
        wxPushService.push(wxSendMsg.getTitle(), wxSendMsg.getContent(), wxPusherId);
    }

    @PreDestroy
    public void destroy() {
        //this.testProducer.shutdown();
    }
}
