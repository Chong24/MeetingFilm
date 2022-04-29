package com.mooc.meetingfilm.backendcinema.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mooc.meetingfilm.backendcinema.controller.vo.CinemaSavedReqVO;
import com.mooc.meetingfilm.backendcinema.controller.vo.DescribeCinemasRespVO;
import com.mooc.meetingfilm.exception.CommonServiceException;

/**
 * @author wang
 * @create 2022-04-25
 */
public interface CinemaServiceAPI {

    // 保存影院
    void saveCinema(CinemaSavedReqVO reqVO) throws CommonServiceException;

    // 获取影院列表：分页
    IPage<DescribeCinemasRespVO> describeCinemas(int nowPage, int pageSize) throws CommonServiceException;

}
