package com.halo.mms.repo.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class H2DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:file:~/.mms/db/halo");
        dataSource.setUsername("admin");
        dataSource.setPassword("123456");
        return dataSource;
    }
}
