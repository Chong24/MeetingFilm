package com.mooc.meetingfilm.backendshowconsumer.controller;

import com.mooc.meetingfilm.backendshowconsumer.feign.ProviderApi;
import com.mooc.meetingfilm.backendshowconsumer.service.ConsumerServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wang
 * @create 2022-04-21
 */

@Slf4j
@RestController
public class ConsumerController {

    @Resource
    private ProviderApi providerApi;

    @Autowired
    private ConsumerServiceAPI serviceAPI;

    @RequestMapping(value = "/sayhello/feign")
    public String sayHelloFeign(String message){
        System.out.println("feign message="+message);
        return providerApi.invokerProviderController(message);
    }

    @RequestMapping(value = "/sayhello")
    public String sayHello(String message){

        return serviceAPI.sayHello(message);
    }

}
