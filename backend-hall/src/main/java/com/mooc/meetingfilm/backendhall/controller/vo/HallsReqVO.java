package com.mooc.meetingfilm.backendhall.controller.vo;

import com.mooc.meetingfilm.common.vo.BasePageVO;
import com.mooc.meetingfilm.common.vo.BaseRequestVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import lombok.Data;

/**
 * @author wang
 * @create 2022-04-25
 */
@Data
public class HallsReqVO extends BasePageVO {

    private String cinemaId;

    @Override
    public void checkParam() throws CommonServiceException {
        super.checkParam();
    }
}
