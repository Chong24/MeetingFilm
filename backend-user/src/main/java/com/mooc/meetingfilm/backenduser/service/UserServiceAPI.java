package com.mooc.meetingfilm.backenduser.service;

import com.mooc.meetingfilm.exception.CommonServiceException;

/**
 * 用户登录模块的接口
 * @author wang
 * @create 2022-04-19
 */
public interface UserServiceAPI {

    String checkUserLogin(String username,String password) throws CommonServiceException;

}
