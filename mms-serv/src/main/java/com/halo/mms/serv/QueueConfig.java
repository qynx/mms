package com.halo.mms.serv;

import com.github.davidmarquis.redisq.RedisMessageQueue;
import com.github.davidmarquis.redisq.persistence.RedisOps;
import com.github.davidmarquis.redisq.producer.DefaultMessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public RedisOps redisOps() {
        return new RedisOps();
    }

    @Bean
    public RedisMessageQueue wxPushQueue() {
        RedisMessageQueue redisMessageQueue = new RedisMessageQueue();
        redisMessageQueue.setQueueName("wx-push");
        return redisMessageQueue;
    }

    @Bean
    public DefaultMessageProducer<String> wxPushProducer(RedisMessageQueue wxPushQueue) {
        DefaultMessageProducer<String> defaultMessageProducer = new DefaultMessageProducer<String>();
        defaultMessageProducer.setQueue(wxPushQueue);
        return defaultMessageProducer;
    }

}
