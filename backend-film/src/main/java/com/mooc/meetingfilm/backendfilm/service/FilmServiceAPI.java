package com.mooc.meetingfilm.backendfilm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mooc.meetingfilm.apis.film.vo.DescribeFilmRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.DescribeActorsRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.DescribeFilmsRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.FilmSavedReqVO;
import com.mooc.meetingfilm.exception.CommonServiceException;

/**
 * 影片逻辑层
 * @author wang
 * @create 2022-04-21
 */
public interface FilmServiceAPI {

    // 获取演员列表
    IPage<DescribeActorsRespVO> describeActors(int nowPage, int pageSize) throws CommonServiceException;

    // 获取电影列表
    IPage<DescribeFilmsRespVO> describeFilms(int nowPage, int pageSize) throws CommonServiceException;

    // 根据主键获取电影信息
    DescribeFilmRespVO describeFilmById(String filmId) throws CommonServiceException;

    // 保存电影信息
    void saveFilm(FilmSavedReqVO filmSavedReqVO) throws CommonServiceException;

}
