package com.mooc.meetingfilm.backenduser.controller.vo;

import com.mooc.meetingfilm.common.vo.BaseRequestVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import com.mooc.meetingfilm.util.ToolUtils;
import lombok.Data;

/**
 * 登录请求对象，因为页面输入表单，我们用的是一个JavaBean对象来接收，所以要与前台表单属性一一对应。
 * 因为我们抽象出来一个类，所有符合这个类的都需要实现其参数验证的方法，换句话说，需要参数验证的都需要继承这个抽象类，实现抽象方法
 * @author wang
 * @create 2022-04-19
 */

@Data
public class LoginReqVO extends BaseRequestVO{

    private String username;
    private String password;

    /**
     * 验证用户名和密码是否不为空
     */
    @Override
    public void checkParam() throws CommonServiceException {

        //当用户名或密码为空，则需要抛出异常
        if (ToolUtils.strIsNull(username) || ToolUtils.strIsNull(password)){

            //抛出的是我们自定义的异常
            throw new CommonServiceException(404, "用户名或密码不能为空");
        }
    }
}
