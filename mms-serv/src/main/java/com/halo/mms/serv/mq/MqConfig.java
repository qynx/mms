package com.halo.mms.serv.mq;

import com.halo.mms.common.config.KafkaConfig;
import com.halo.mms.common.config.RepoConfig;
import com.halo.mms.common.domain.WxSendMsg;
import com.halo.mms.out.WxPushService;
import com.halo.mms.serv.service.api.UserInfoService;
import com.halo.mms.serv.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Configuration
public class MqConfig {

    @Resource
    private RepoConfig repoConfig;
    @Resource
    private WxPushService wxPushService;
    @Resource
    private UserInfoService userInfoService;

    private DefaultMQProducer testProducer;

    @Bean
    public DefaultMQProducer testProducer() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("test");
        System.out.println(repoConfig.getRocketMqAddr());
        defaultMQProducer.setNamesrvAddr(repoConfig.getRocketMqAddr());
        this.testProducer = defaultMQProducer;
        this.testProducer.start();
        return defaultMQProducer;
    }

    @Bean
    public KafkaMqProducer wxPusherProducer() {
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaConfig.setTopic("mms-wx_pusher");
        return new KafkaMqProducer(kafkaConfig);
    }

    @Bean
    public KafkaMqConsumer wxPusherConsumer() {
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaConfig.setTopic("mms-wx_pusher");
        kafkaConfig.setGroup("mms-wx_pusher");
        KafkaMqConsumer consumer = new KafkaMqConsumer(kafkaConfig);

        consumer.start(records -> {
            for (ConsumerRecord<String, String> record: records) {
                log.info("kafka_consume: {}", record.value());
                WxSendMsg wxSendMsg = JsonUtil.fromJson(record.value(), WxSendMsg.class);

                String wxPusherId = Optional.ofNullable(wxSendMsg.getWxPusherId())
                .orElse(userInfoService.queryUser(wxSendMsg.getUid()).getWxPusherUid());
                wxPushService.push(wxSendMsg.getTitle(), wxSendMsg.getContent(), wxPusherId);
            }
        });

        return consumer;
    }

    @PreDestroy
    public void destroy() {
        //this.testProducer.shutdown();

    }
}
