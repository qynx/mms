package com.halo.mms.serv.mq;

import com.github.davidmarquis.redisq.RedisMessageQueue;
import com.halo.mms.serv.configs.CommonConfig;

import java.util.HashMap;
import java.util.Map;

public class RedisMqQueue extends RedisMessageQueue {

    private static final Map<String, RedisMqQueue> BEANS = new HashMap<>();


    public static RedisMqQueue getQueue(String name) {
        if (null != BEANS.get(name)) {
            return BEANS.get(name);
        }

        RedisMqQueue redisMqQueue = new RedisMqQueue();
        redisMqQueue.setQueueName(name);
        redisMqQueue.setRedisOps(CommonConfig.redisOps);
        redisMqQueue.initialize();
        BEANS.put(name, redisMqQueue);
        return redisMqQueue;
    }
}
