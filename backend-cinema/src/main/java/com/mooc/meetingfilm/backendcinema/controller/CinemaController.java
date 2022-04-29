package com.mooc.meetingfilm.backendcinema.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.mooc.meetingfilm.backendcinema.controller.vo.CinemaSavedReqVO;
import com.mooc.meetingfilm.backendcinema.controller.vo.DescribeCinemasRespVO;
import com.mooc.meetingfilm.backendcinema.service.CinemaServiceAPI;
import com.mooc.meetingfilm.common.vo.BasePageVO;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wang
 * @create 2022-04-25
 */
@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    @Resource
    private CinemaServiceAPI cinemaServiceAPI;

    @RequestMapping(value = "/cinema:add",method = RequestMethod.POST)
    public BaseResponseVO saveCinema(@RequestBody CinemaSavedReqVO cinemaSavedReqVO) throws CommonServiceException {

        // 数据验证
        cinemaSavedReqVO.checkParam();

        cinemaServiceAPI.saveCinema(cinemaSavedReqVO);

        return BaseResponseVO.success();
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public BaseResponseVO describeCinemas(BasePageVO basePageVO) throws CommonServiceException {

        // 检查入参
        basePageVO.checkParam();

        IPage<DescribeCinemasRespVO> describeCinemasRespVOIPage = cinemaServiceAPI.describeCinemas(basePageVO.getNowPage(), basePageVO.getPageSize());

        if(basePageVO.getNowPage() > 10000){
            throw new CommonServiceException(400,"nowPage太大了，不支持此处理");
        }

        // TODO 调用封装的分页返回方法, 就和新建一个map，把一些数据放进去，key-value对应差不多，只不过封装了一个公共的方法调用
        Map<String, Object> cinemas = descrbePageResult(describeCinemasRespVOIPage, "cinemas");

        return BaseResponseVO.success(cinemas);
    }

    // 获取分页对象的公共接口
    private Map<String,Object> descrbePageResult(IPage page, String title){
        //就是new 一个HashMap
        Map<String,Object> result = Maps.newHashMap();

        result.put("totalSize",page.getTotal());
        result.put("totalPages",page.getPages());
        result.put("pageSize",page.getSize());
        result.put("nowPage",page.getCurrent());

        //分页的数据
        result.put(title, page.getRecords());

        return result;
    }

}
