package com.mooc.meetingfilm.backendshowconsumer.feign;


import org.springframework.stereotype.Service;

//@Primary
@Service
public class ProviderAPIImpl implements ProviderApi{
    @Override
    public String invokerProviderController(String message) {
        return null;
    }

//    @Override
//    public String providerPost(String author, String providerId, UserModel userModel) {
//        return null;
//    }
}
