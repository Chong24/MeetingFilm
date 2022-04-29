package com.mooc.meetingfilm.common.vo;

import com.mooc.meetingfilm.exception.CommonServiceException;
import lombok.Data;


/**
 * 封装返回给客户端的数据，起到一个公共返回给客户端的作用
 * 封装了返给客户端的所有可能
 * @author wang
 * @create 2022-04-19
 */
@Data
public class BaseResponseVO<M> {

    private Integer code;   // 业务编号
    private String message; // 异常信息
    private M data;         // 业务数据返回，因为不知道业务数据的具体类型，所以用泛型代替

    private BaseResponseVO(){

    }

    //成功无参数
    public static BaseResponseVO success(){
        BaseResponseVO response = new BaseResponseVO();
        response.setCode(200);
        response.setMessage("");
        return response;
    }

    //成功有参数 : 声明为泛型方法
    public static<M> BaseResponseVO success(M data){
        BaseResponseVO response = new BaseResponseVO();
        response.setCode(200);
        response.setMessage("");
        response.setData(data);
        return response;
    }

    //未登录异常
    public static<M> BaseResponseVO noLogin(){
        BaseResponseVO response = new BaseResponseVO();
        response.setCode(401);
        response.setMessage("请登录");
        return response;
    }

    //出现业务异常
    public static<M> BaseResponseVO serviceException(CommonServiceException e){
        BaseResponseVO response = new BaseResponseVO();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());
        return response;
    }
}
