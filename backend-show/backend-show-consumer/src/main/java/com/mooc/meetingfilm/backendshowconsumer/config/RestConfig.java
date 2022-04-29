package com.mooc.meetingfilm.backendshowconsumer.config;


import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wang
 * @create 2022-04-21
 */
@Configuration
public class RestConfig {

    //java自带的远程调用的方式，要注入到spring容器中
    @Bean
    //标了这个注解，代表restTemlate拥有熔断器的特性，会走熔断器的逻辑
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * @Description: 配置负载均衡规则：也可以自己自定义负载均衡规则，例如MyRule
     * @Param: []
     * @Author: jiangzh
     */
    @Bean
    public IRule iRule(){
        return new RoundRobinRule();
        //return new MyRule();
    }

    /**
     * 配置自定义的Eureka Server判断server是否存活，只有存活的server，才会负载均衡分到它去处理
     * @return
     */
    @Bean
    public IPing iPing(){
//        return new PingUrl(false,"/abc");
        return new NIWSDiscoveryPing();
    }

}
