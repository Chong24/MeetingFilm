package com.mooc.meetingfilm.backendshowconsumer.ribbon.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

/**
 * 自定义负载均衡的规则：①、需要继承顶级父类AbstractLoadBalancerRule；②、重写其choose方法
 * @author wang
 * @create 2022-04-26
 */
public class MyRule extends AbstractLoadBalancerRule {



    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {

        //在这写自定义负载均衡的逻辑

        return null;
    }
}
