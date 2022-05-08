package com.mooc.meetingfilm.apigwzuul.config;

import com.mooc.meetingfilm.apigwzuul.filters.CorsFilter;
import com.mooc.meetingfilm.apigwzuul.filters.JWTFilter;
import com.mooc.meetingfilm.apigwzuul.filters.MyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : jiangzh
 * @program : com.mooc.meetingfilm.apigwzuul.config
 * @description : zuul的配置类
 **/
@Configuration
public class ZuulConfig {

    //自定义filter
    @Bean
    public MyFilter initMyFilter(){
        return new MyFilter();
    }

    @Bean
    public JWTFilter initJWTFilter(){
        return new JWTFilter();
    }
}