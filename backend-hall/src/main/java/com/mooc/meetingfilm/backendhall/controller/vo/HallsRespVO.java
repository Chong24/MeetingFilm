package com.mooc.meetingfilm.backendhall.controller.vo;

import lombok.Data;

/**
 * @author wang
 * @create 2022-04-25
 */
@Data
public class HallsRespVO {

    private String cinemaName;
    private String hallName;
    private String filmName;
    private String hallTypeName;
    private String beginTime;
    private String endTime;
    private String filmPrice;

}
