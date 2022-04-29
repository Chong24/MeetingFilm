package com.mooc.meetingfilm.backendshowconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author wang
 * @create 2022-04-21
 */
@Service
public class ConsumerServiceImpl implements ConsumerServiceAPI {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient eurekaClient;

    @Override
    public String sayHello(String message) {
//        //准备工作，这是写死的
//        String hostname = "localhost";
//        int port = 7101;
//        String uri = "/provider/sayhello?message="+message;
//        String url = "http://" + hostname + ":" + port + uri;

        //我们需要从Eureka的注册中心中拿到Eureka Provider提供服务的信息，传入服务名称，
//        ServiceInstance choose = eurekaClient.choose("hello-service-provider");
//
//        String hostname = choose.getHost();
//        int port = choose.getPort();
//        String uri = "/provider/sayhello?message="+message;
//
//        String url = "http://" + hostname + ":" + port + uri;
//
//        //远程调用
//        String result = restTemplate.getForObject(url, String.class);
//
//        return result;

        //如果有多个服务，要进行负载均衡，就要写服务客户端的名字hello-service-provider，而不能写hostname:port
        String uri = "/provider/sayhello?message="+message;

        // http://localhost:7101/provider/sayhello?message=hello
        String url = "http://hello-service-provider"+uri;

        // invoker provider test
        String result = restTemplate.getForObject(url, String.class);

        return result;
    }
}
