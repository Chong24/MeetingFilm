package com.mooc.meetingfilm.exception;

import lombok.Data;

/**
 * 公共的业务逻辑错误：用一个JavaBean封装业务逻辑错误的信息，即自定义异常：希望返回JSON形式的状态码和错误信息
 * @author wang
 * @create 2022-04-19
 */

@Data
public class CommonServiceException extends Exception{

    private Integer code;
    private String message;

    public CommonServiceException(){

    }

    public CommonServiceException(int code, String message){
        this.code = code;
        this.message = message;
    }
}
