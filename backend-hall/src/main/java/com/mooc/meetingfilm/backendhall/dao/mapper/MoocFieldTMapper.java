package com.mooc.meetingfilm.backendhall.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsReqVO;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsRespVO;
import com.mooc.meetingfilm.backendhall.dao.entity.MoocFieldT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author wc
 * @since 2022-04-25
 */
public interface MoocFieldTMapper extends BaseMapper<MoocFieldT> {

    //用Mybatis-plus自动分页，Paage类一定要写在第一个参数位置
    // ${ew.customSqlSegment}是写死的，如果用QueryWrapper来拼接where语句，就得这么写；ew是设置的QueryWrapper的参数名
    IPage<HallsRespVO> describeHalls(Page<HallsReqVO> page, @Param("ew") QueryWrapper queryWrapper);
}
