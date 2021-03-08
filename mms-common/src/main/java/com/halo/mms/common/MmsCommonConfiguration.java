package com.halo.mms.common;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MmsCommonConfiguration {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

}
