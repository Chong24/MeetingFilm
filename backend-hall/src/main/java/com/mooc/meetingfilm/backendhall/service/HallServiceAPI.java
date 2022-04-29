package com.mooc.meetingfilm.backendhall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mooc.meetingfilm.backendhall.controller.vo.HallSavedReqVO;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsReqVO;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsRespVO;
import com.mooc.meetingfilm.exception.CommonServiceException;

/**
 * @author wang
 * @create 2022-04-25
 */
public interface HallServiceAPI {

    // 获取全部播放厅接口：hallsReqVO除了封装了分页信息，还封装了属性cinemaId
    IPage<HallsRespVO> describeHalls(HallsReqVO hallsReqVO) throws CommonServiceException;

    // 保存影厅信息
    void saveHall(HallSavedReqVO hallSavedReqVO) throws CommonServiceException;

}
