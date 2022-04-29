package com.mooc.meetingfilm.backenduser.controller;

import com.mooc.meetingfilm.backenduser.controller.vo.LoginReqVO;
import com.mooc.meetingfilm.backenduser.service.UserServiceAPI;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import com.mooc.meetingfilm.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户模块的控制层
 * 因为标了@RestController注解，因此最终返回的都是JSON的格式，BaseResponseVO会转为JSON字符串
 * @author wang
 * @create 2022-04-19
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceAPI serviceAPI;

    /**
     * @RequestBody ： RequestBody 接收的是请求体里面的数据；而RequestParam接收的是key-value里面的参数
     *                后端@RequestBody注解对应的类在将HTTP的输入流(含请求体)装配到目标类(即:@RequestBody后面
     *                的类)时，会根据json字符串中的key来匹配对应实体类的属性，如果匹配一致且json中的该key对应的值
     *                符合(或可转换为)实体类的对应属性的类型要求时，会调用实体类的setter方法将值赋给该属性。
     * @param reqVO
     * @return
     * @throws CommonServiceException
     */
    @PostMapping("/login")
    public BaseResponseVO login(@RequestBody LoginReqVO reqVO) throws CommonServiceException{

        //数据验证：如果传入的参数为空，就会直接抛出我们自定义的异常，后面代码就不会执行下去
        reqVO.checkParam();

        //验证用户名和密码是否正确，如果没抛出异常，说明用户名和密码都是正确的
        String userId = serviceAPI.checkUserLogin(reqVO.getUsername(), reqVO.getPassword());

        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

        //随机一个字符串当盐，然后生成token，传给客户端；因为能走到这，说明登录成功了。
        String randomKey = jwtTokenUtil.getRandomKey();
        String token = jwtTokenUtil.generateToken(userId, randomKey);

        //服务器保存secret键和token，为了验证后面的登录
        Map<String, String> result = new HashMap<>();
        result.put("randomKey", randomKey);
        result.put("token", token);

        return BaseResponseVO.success(result);
    }


}
