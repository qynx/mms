package com.halo.mms.repo.datasource;

import com.zaxxer.hikari.HikariDataSource;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MysqlDataSourceConfig {

    @Bean
    public DataSource mysqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/springboot?useSSL=false&serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("287830");
        return dataSource;
    }

    @Bean
    public EbeanServer ebeanServer(DataSource mysqlDataSource) {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setName("db");
        serverConfig.setDefaultServer(true);
        serverConfig.setDataSource(mysqlDataSource);
        return EbeanServerFactory.create(serverConfig);
    }

}
