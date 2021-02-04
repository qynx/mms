package com.halo.mms.repo;

import com.halo.mms.common.MmsCommonConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@EntityScan(basePackages = {"com.halo.mms.repo.model"})
//@EnableJpaRepositories(basePackages = {"com.halo.mms.repo.repository"})
@ComponentScan
@Import(value = {MmsCommonConfiguration.class})
public class MmsRepoConfiguration {

}
