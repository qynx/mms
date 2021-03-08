package com.halo.mms.common.config;

import com.github.davidmarquis.redisq.persistence.RedisOps;
import com.halo.mms.common.consts.MqQueueConst;
import com.halo.mms.common.mq.RedisMqProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMqConfig {

    public static final RedisOps redisOps = new RedisOps();

    @Bean
    public RedisOps redisOps() {
        return redisOps;
    }

    @Bean
    public RedisMqProducer crawlerListProducer() {
        return new RedisMqProducer(MqQueueConst.CHAPTER_LIST_TASK);
    }

    @Bean
    public RedisMqProducer crawlerContentProducer() {
        return new RedisMqProducer(MqQueueConst.CHAPTER_CONTENT_TASK);
    }

    @Bean
    public RedisMqProducer crawlerSaveContentProducer() {
        return new RedisMqProducer(MqQueueConst.CHAPTER_SAVE_TASK);
    }

}
