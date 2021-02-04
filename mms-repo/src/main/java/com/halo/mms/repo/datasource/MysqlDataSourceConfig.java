package com.halo.mms.repo.datasource;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.halo.mms.common.config.RepoConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class MysqlDataSourceConfig {

    @Bean
    public DataSource mysqlDataSource(RepoConfig repoConfig) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/springboot?useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true");
        dataSource.setUsername(repoConfig.getSqlUserName());
        dataSource.setPassword(repoConfig.getSqlPwd());
        return dataSource;
    }

    //@Bean
    public EbeanServer ebeanServer(DataSource mysqlDataSource) {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setName("db");
        serverConfig.setDefaultServer(true);
        serverConfig.setDataSource(mysqlDataSource);
        return EbeanServerFactory.create(serverConfig);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.halo.mms.repo.mybatis.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("mybatisSqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();

        mybatisSqlSessionFactoryBean.setDataSource(dataSource);

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setMapperLocations(
            resourcePatternResolver.getResources("classpath*:plus/mapper/**/*Mapper.xml"));

        Interceptor[] interceptors = new Interceptor[1];
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor());
        interceptors[0] = mybatisPlusInterceptor;
        mybatisSqlSessionFactoryBean.setPlugins(interceptors);

        return mybatisSqlSessionFactoryBean;
    }

    public PaginationInnerInterceptor paginationInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        paginationInterceptor.setDbType(DbType.MYSQL);
        return paginationInterceptor;
    }

}
