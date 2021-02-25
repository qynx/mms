package com.halo.mms.serv.mq;

import com.github.davidmarquis.redisq.RedisMessageQueue;
import com.github.davidmarquis.redisq.producer.DefaultMessageProducer;

public class RedisMqProducer extends DefaultMessageProducer<String> {

    public RedisMqProducer(String queueName) {
        RedisMessageQueue redisMessageQueue = RedisMqQueue.getQueue(queueName);
        this.setQueue(redisMessageQueue);
    }
}