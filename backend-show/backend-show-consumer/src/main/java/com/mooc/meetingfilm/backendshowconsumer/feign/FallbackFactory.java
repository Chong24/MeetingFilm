package com.mooc.meetingfilm.backendshowconsumer.feign;

import org.springframework.stereotype.Service;

@Service
public class FallbackFactory implements feign.hystrix.FallbackFactory {
    //create返回的就是我们想返回的内容，工厂模式
    @Override
    public ProviderApi create(Throwable throwable) {
        return new ProviderApi() {
            //写降级的业务逻辑
            @Override
            public String invokerProviderController(String message) {
                return "invokerProviderController FallbackFactory message="+message;
            }
        };
    }

}