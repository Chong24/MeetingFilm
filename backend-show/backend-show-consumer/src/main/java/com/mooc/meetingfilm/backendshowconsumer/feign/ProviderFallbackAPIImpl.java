package com.mooc.meetingfilm.backendshowconsumer.feign;

import org.springframework.stereotype.Service;

@Service
public class ProviderFallbackAPIImpl implements ProviderApi{
    //写降级业务逻辑
    @Override
    public String invokerProviderController(String message) {
        return "invokerProviderController fallback message="+message;
    }

}
