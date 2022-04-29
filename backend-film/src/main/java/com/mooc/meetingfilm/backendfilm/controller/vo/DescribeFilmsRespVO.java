package com.mooc.meetingfilm.backendfilm.controller.vo;

import lombok.Data;

/**
 * 封装查询影片列表接口返回对象：用于返回给前端显示
 * @author wang
 * @create 2022-04-21
 */
@Data
public class DescribeFilmsRespVO {

    private String filmId;
    private String filmStatus;
    private String filmName;
    private String filmEnName;
    private String filmScore;
    private String preSaleNum;
    private String boxOffice;
    private String filmTime;
    private String filmLength;
    private String mainImg;

}
