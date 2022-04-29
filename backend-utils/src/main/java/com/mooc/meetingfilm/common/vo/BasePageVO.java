package com.mooc.meetingfilm.common.vo;

import com.mooc.meetingfilm.exception.CommonServiceException;
import lombok.Data;

/**
 * 封装公共的分页
 * @author wang
 * @create 2022-04-19
 */
@Data
public class BasePageVO extends BaseRequestVO{

    private Integer nowPage = 1;
    private Integer pageSize = 10;

    @Override
    public void checkParam() throws CommonServiceException {

    }

}
