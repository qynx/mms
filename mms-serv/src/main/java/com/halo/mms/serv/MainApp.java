package com.halo.mms.serv;

import com.halo.mms.repo.MmsRepoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource({"classpath:/dubbo/provider.xml"})
@PropertySource({"classpath:application.yml"})
@Import(value = {MmsRepoConfiguration.class})
public class MainApp {

    public static void main(String[] args) {
        // System.out.println(System.getProperties().getProperty("user.home"));
        SpringApplication.run(MainApp.class, args);
    }

}
