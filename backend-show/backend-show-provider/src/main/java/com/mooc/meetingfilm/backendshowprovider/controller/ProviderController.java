package com.mooc.meetingfilm.backendshowprovider.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供一个方法给消费者远程调用
 * @author wang
 * @create 2022-04-21
 */

@Slf4j
@RestController
public class ProviderController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/provider/sayhello")
    public String providerSayHello(String message){

        log.error("provider sayhello port:{}, message:{}",port,message);

        return "Provider sayhello port:"+port+" , message:"+message;
    }

}
