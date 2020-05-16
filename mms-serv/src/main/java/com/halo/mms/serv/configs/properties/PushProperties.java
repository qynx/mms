package com.halo.mms.serv.configs.properties;

import com.halo.mms.serv.configs.AbstractConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Map;

@Component
public class PushProperties extends AbstractConfigLoader {

    private static final String CONFIG_FILE_NAME = "push.yaml";

    private Object config;
    private String appToken;

    @Autowired
    public PushProperties() throws Exception {
        this.config = getConfig();
    }

    private Object getConfig() throws Exception {
        Object config = LoadYamlConfig(Paths.get(getConfigDir(), CONFIG_FILE_NAME));
        this.appToken = ((Map) config).get("app_token").toString();
        return config;
    }

    public String getAppToken() {
        return appToken;
    }
}
