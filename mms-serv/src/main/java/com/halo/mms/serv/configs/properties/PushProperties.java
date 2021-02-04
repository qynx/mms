package com.halo.mms.serv.configs.properties;

import com.halo.mms.common.config.AbstractConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class PushProperties extends AbstractConfigLoader {

    private static final String CONFIG_FILE_NAME = "repo.properties";

    private String appToken;

    @Autowired
    public PushProperties() throws Exception {
        Properties properties = loadProp(CONFIG_FILE_NAME);
        this.appToken = properties.getProperty("app_token");
    }

    public String getAppToken() {
        return appToken;
    }
}
