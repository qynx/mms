package com.halo.mms.common.config;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class RepoConfig extends AbstractConfigLoader {

    private static final String CONFIG_NAME = "repo.properties";

    private static final Properties prop = loadProp(CONFIG_NAME);

    public String getSqlPwd() {
        return prop.getProperty("mysql_password");
    }

    public String getSqlUserName() {
        return prop.getProperty("mysql_user_name");
    }

    public String getRocketMqAddr() {
        return prop.getProperty("rocket_mq_addr");
    }
}
