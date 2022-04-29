package com.mooc.meetingfilm.backendhall.conf;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 向spring容器中注入restTemplate组件
 * @author wang
 * @create 2022-04-25
 */
@Configuration
public class RestConf {

    //java自带的远程调用的方式，要注入到spring容器中
    @Bean
    //标了这个注解，代表restTemlate拥有熔断器的特性，会走熔断器的逻辑
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
