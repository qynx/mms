package com.halo.mms.serv.mq;

import com.github.davidmarquis.redisq.consumer.MessageConsumer;
import com.github.davidmarquis.redisq.consumer.MessageListener;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisMqConsumer extends MessageConsumer<String> {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public RedisMqConsumer(String queueName) {
        setQueue(RedisMqQueue.getQueue(queueName));
        setConsumerId(String.valueOf(atomicInteger.getAndIncrement()));
    }

    public RedisMqConsumer(String queueName, MessageListener<String> messageListener) {
        setQueue(RedisMqQueue.getQueue(queueName));
        setConsumerId(String.valueOf(atomicInteger.getAndIncrement()));
        setMessageListener(messageListener);
    }
}
