package com.mooc.meetingfilm.common.vo;

import com.mooc.meetingfilm.exception.CommonServiceException;

/**
 * 公共的请求对象：因为对于请求对象，都需要验证请求参数是否合理；
 * 因此，我们可以将参数验证方法抽象出来，生成一个抽象方法，供其他类继承实现
 * @author wang
 * @create 2022-04-19
 */
public abstract class BaseRequestVO {

    /**
     * 公共的参数验证的方法
     */
    public abstract void checkParam() throws CommonServiceException;
}
