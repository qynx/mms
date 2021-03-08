package com.halo.mms.common.mq;

import com.github.davidmarquis.redisq.RedisMessageQueue;
import com.github.davidmarquis.redisq.producer.DefaultMessageProducer;
import com.halo.mms.common.mq.RedisMqQueue;
import com.halo.mms.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisMqProducer extends DefaultMessageProducer<String> {

    public RedisMqProducer(String queueName) {
        RedisMessageQueue redisMessageQueue = RedisMqQueue.getQueue(queueName);
        this.setQueue(redisMessageQueue);
    }

    public void send(Object obj) {
        if (null == obj) {
            throw new RuntimeException("param is null error");
        }
        log.info("{} send", JsonUtil.toJson(obj));
        submit(JsonUtil.toJson(obj));
    }
}