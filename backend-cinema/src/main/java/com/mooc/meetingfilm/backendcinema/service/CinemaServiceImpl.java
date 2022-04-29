package com.mooc.meetingfilm.backendcinema.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.meetingfilm.backendcinema.controller.vo.CinemaSavedReqVO;
import com.mooc.meetingfilm.backendcinema.controller.vo.DescribeCinemasRespVO;
import com.mooc.meetingfilm.backendcinema.dao.entity.MoocCinemaT;
import com.mooc.meetingfilm.backendcinema.dao.mapper.MoocCinemaTMapper;
import com.mooc.meetingfilm.exception.CommonServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @create 2022-04-25
 */
@Service
public class CinemaServiceImpl implements CinemaServiceAPI {

    @Resource
    private MoocCinemaTMapper cinemaTMapper;

    @Override
    public void saveCinema(CinemaSavedReqVO reqVO) throws CommonServiceException {
        MoocCinemaT cinema = new MoocCinemaT();

        cinema.setCinemaName(reqVO.getCinemaName());
        cinema.setCinemaPhone(reqVO.getCinemaTele());
        cinema.setBrandId(Integer.parseInt(reqVO.getBrandId()));
        cinema.setAreaId(Integer.parseInt(reqVO.getAreaId()));
        cinema.setHallIds(reqVO.getHallTypeIds());
        cinema.setImgAddress(reqVO.getCinemaImgAddress());
        cinema.setCinemaAddress(reqVO.getCinemaAddress());
        cinema.setMinimumPrice(Integer.parseInt(reqVO.getCinemaPrice()));

        cinemaTMapper.insert(cinema);
    }

    @Override
    public IPage<DescribeCinemasRespVO> describeCinemas(int nowPage, int pageSize) throws CommonServiceException {

        Page<DescribeCinemasRespVO> page = new Page<>(nowPage, pageSize);
        IPage<DescribeCinemasRespVO> iPage = cinemaTMapper.describeCinemas(page);

        return iPage;
    }
}
