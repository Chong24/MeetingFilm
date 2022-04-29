package com.mooc.meetingfilm.backenduser.dao;


import com.mooc.meetingfilm.backenduser.dao.entity.MoocBackendUserT;
import com.mooc.meetingfilm.backenduser.dao.mapper.MoocBackendUserTMapper;
import com.mooc.meetingfilm.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试是否连接上了数据库
 * @author wang
 * @create 2022-04-19
 */
@SpringBootTest
public class UserTest {

    @Resource
    private MoocBackendUserTMapper backendUser;

    /**
     * @Description: 添加后台运维用户功能
     * @Param: []
     * @return: void
     * @Author: jiangzh
     */
    @Test
    public void add() {
        MoocBackendUserT user = new MoocBackendUserT();
        user.setUserName("admin");
        user.setUserPwd(MD5Util.encrypt("admin123"));
        user.setUserPhone("18588888888");

        backendUser.insert(user);
    }

    @Test
    public void select() {
        // 查询列表
        List<MoocBackendUserT> moocBackendUserTS = backendUser.selectList(null);
        moocBackendUserTS.stream().forEach(
                System.out::println
        );
    }

}
