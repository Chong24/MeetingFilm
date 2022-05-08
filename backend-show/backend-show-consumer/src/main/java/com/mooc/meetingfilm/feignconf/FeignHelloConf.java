package com.mooc.meetingfilm.feignconf;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义feign配置类，不能再main方法扫描范围内
 */
@Configuration
public class FeignHelloConf {

    @Bean
    public Contract contract(){
        return new feign.Contract.Default();
    }

}