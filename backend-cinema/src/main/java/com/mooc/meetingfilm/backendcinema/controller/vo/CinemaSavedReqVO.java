package com.mooc.meetingfilm.backendcinema.controller.vo;

import com.mooc.meetingfilm.common.vo.BaseRequestVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import lombok.Data;

/**
 * 用于接收前端传入的新增影院的信息：只要是接收前端传入的类都需要继承BaseRequestVO，重写其checkParam方法检查参数
 * @author wang
 * @create 2022-04-25
 */

@Data
public class CinemaSavedReqVO extends BaseRequestVO{

    private String brandId;
    private String areaId;
    private String hallTypeIds;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaTele;
    private String cinemaImgAddress;
    private String cinemaPrice;

    @Override
    public void checkParam() throws CommonServiceException {

    }

}
