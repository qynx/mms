package com.halo.mms.serv.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppContextUtil {

    private static ApplicationContext applicationContext;

    @Autowired
    public AppContextUtil(ApplicationContext applicationContext) {
        AppContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(Class<?> type) {
        return applicationContext.getBean(type);
    }

}
