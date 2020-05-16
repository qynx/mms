package com.halo.mms.serv.configs;

import com.halo.mms.serv.configs.properties.PushProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PushConfig {

    @Autowired
    private PushProperties pushProperties;

    private final String appName = "mms";
}
