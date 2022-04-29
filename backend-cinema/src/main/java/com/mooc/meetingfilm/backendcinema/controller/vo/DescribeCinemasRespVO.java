package com.mooc.meetingfilm.backendcinema.controller.vo;

import lombok.Data;

/**
 * 封装的返回给前端数据的对象：影院信息
 * @author wang
 * @create 2022-04-25
 */
@Data
public class DescribeCinemasRespVO {

    private String brandId;
    private String areaId;
    private String hallTypeIds;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaTele;
    private String cinemaImgAddress;
    private String cinemaPrice;

}
