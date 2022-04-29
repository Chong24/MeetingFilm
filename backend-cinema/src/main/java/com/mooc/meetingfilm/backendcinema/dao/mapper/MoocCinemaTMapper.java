package com.mooc.meetingfilm.backendcinema.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.meetingfilm.backendcinema.controller.vo.DescribeCinemasRespVO;
import com.mooc.meetingfilm.backendcinema.dao.entity.MoocCinemaT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author wc
 * @since 2022-04-25
 */
public interface MoocCinemaTMapper extends BaseMapper<MoocCinemaT> {

    IPage<DescribeCinemasRespVO> describeCinemas(Page<DescribeCinemasRespVO> page);
}
