package com.halo.mms.serv.configs;

import com.github.davidmarquis.redisq.persistence.RedisOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CommonConfig {

    private static ApplicationContext staticContext;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        redisOps = applicationContext.getBean(RedisOps.class);
    }

    public static RedisOps redisOps;

}
