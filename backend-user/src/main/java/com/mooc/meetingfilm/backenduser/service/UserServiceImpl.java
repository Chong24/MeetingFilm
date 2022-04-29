package com.mooc.meetingfilm.backenduser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mooc.meetingfilm.backenduser.dao.entity.MoocBackendUserT;
import com.mooc.meetingfilm.backenduser.dao.mapper.MoocBackendUserTMapper;
import com.mooc.meetingfilm.exception.CommonServiceException;
import com.mooc.meetingfilm.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户模块的接口实现类
 * @author wang
 * @create 2022-04-19
 */
@Service
public class UserServiceImpl implements UserServiceAPI {

    @Resource
    private MoocBackendUserTMapper userTMapper;

    @Override
    public String checkUserLogin(String username, String password) throws CommonServiceException {
        //根据用户名获取用户信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", username);

        //本来按照用户名查询只能取出唯一一个用户的，但是避免数据会出现问题，我们用list查，然后取第一个
        List<MoocBackendUserT> list = userTMapper.selectList(queryWrapper);
        MoocBackendUserT user = null;
        if (list != null && list.size() > 0){
            //1.8之后支持流的操作，一般都是对集合进行操作
            user = list.stream().findFirst().get();
        }else{
            throw new CommonServiceException(404, "用户名输入有误");
        }

        //验证密码是否正确【密码要做MD5加密，才能验证是否匹配】
        String encrypt = MD5Util.encrypt(password);

        if (!encrypt.equals(user.getUserPwd())){
            throw new CommonServiceException(500, "用户密码输入错误");
        }else{
            return user.getUuid() + "";
        }
    }
}
