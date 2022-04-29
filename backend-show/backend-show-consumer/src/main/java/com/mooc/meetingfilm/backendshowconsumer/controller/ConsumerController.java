package com.mooc.meetingfilm.backendshowconsumer.controller;

import com.mooc.meetingfilm.backendshowconsumer.service.ConsumerServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang
 * @create 2022-04-21
 */

@Slf4j
@RestController
public class ConsumerController {

    @Autowired
    private ConsumerServiceAPI serviceAPI;

    @RequestMapping(value = "/sayhello")
    public String sayHello(String message){

        return serviceAPI.sayHello(message);
    }

}
