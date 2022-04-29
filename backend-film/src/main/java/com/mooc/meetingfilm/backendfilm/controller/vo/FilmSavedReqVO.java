package com.mooc.meetingfilm.backendfilm.controller.vo;

import com.mooc.meetingfilm.common.vo.BaseRequestVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import lombok.Data;

/**
 * 保存电影信息的对象：用于接收前端form表单传入的电影信息，然后保存到数据库中
 * @author wang
 * @create 2022-04-21
 */
@Data
public class FilmSavedReqVO extends BaseRequestVO{

    private String filmStatus;
    private String filmName;
    private String filmEnName;
    private String mainImgAddress;
    private String filmScore;
    private String filmScorers;
    private String preSaleNum;
    private String boxOffice;
    private String filmTypeId;
    private String filmSourceId;
    private String filmCatIds;
    private String areaId;
    private String dateId;
    private String filmTime;
    private String directorId;
    private String actIds;      // actIds与RoleNames是不是能在数量上对应上
    private String roleNames;
    private String filmLength;
    private String biography;
    private String filmImgs;

    @Override
    public void checkParam() throws CommonServiceException {

    }

}
