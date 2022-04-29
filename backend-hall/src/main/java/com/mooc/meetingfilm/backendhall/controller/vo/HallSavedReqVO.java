package com.mooc.meetingfilm.backendhall.controller.vo;

import com.mooc.meetingfilm.common.vo.BaseRequestVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import lombok.Data;

/**
 * @author wang
 * @create 2022-04-25
 */
@Data
public class HallSavedReqVO extends BaseRequestVO {

    private String cinemaId;
    private String filmId;
    private String hallTypeId;
    private String beginTime;
    private String endTime;
    private String filmPrice;
    private String hallName;

    @Override
    public void checkParam() throws CommonServiceException {

    }
}
