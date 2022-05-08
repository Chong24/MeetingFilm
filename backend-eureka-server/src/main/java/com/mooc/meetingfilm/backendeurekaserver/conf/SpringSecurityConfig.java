package com.mooc.meetingfilm.backendeurekaserver.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : jiangzh
 * @program : com.mooc.meetingfilm.eureka.conf
 * @description : SpringSecurity配置
 **/

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @Description: 对eureka注册的URL不进行CSRF防御
     * @Param: [http]
     * @return: void
     * @Author: jiangzh
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //忽略http的csrf问题，因为它会导致一些原有底层组件不好用
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
