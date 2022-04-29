package com.mooc.meetingfilm.backendfilm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.mooc.meetingfilm.backendfilm.controller.vo.DescribeActorsRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.DescribeFilmsRespVO;
import com.mooc.meetingfilm.backendfilm.controller.vo.FilmSavedReqVO;
import com.mooc.meetingfilm.backendfilm.service.FilmServiceAPI;
import com.mooc.meetingfilm.common.vo.BasePageVO;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * 影片模块表现层
 * @author wang
 * @create 2022-04-21
 */
@Slf4j
@RestController
@RequestMapping(value = "/films")
public class FilmController {

    @Autowired
    private FilmServiceAPI filmServiceAPI;

    // 获取演员列表
    @RequestMapping(value = "/actors",method = RequestMethod.GET)
    public BaseResponseVO describeActors(BasePageVO basePageVO) throws CommonServiceException {
        // 检查入参
        basePageVO.checkParam();

        // 调用逻辑层，获取返回参数
        IPage<DescribeActorsRespVO> results = filmServiceAPI.describeActors(basePageVO.getNowPage(),basePageVO.getPageSize());

        Map<String, Object> actors = descrbePageResult(results, "actors");

        //查询出的actors作为JSON字符串的data返回给前端
        return BaseResponseVO.success(actors);
    }

    // 获取电影列表
    @RequestMapping(value = "",method = RequestMethod.GET)
    public BaseResponseVO describeFilms(HttpServletRequest request, BasePageVO basePageVO) throws CommonServiceException {

        // Header信息都打印一下
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headName = headerNames.nextElement();
            log.error("describeFilms - headName:{}, headValue:{}", headName, request.getHeader(headName));
        }


        // 检查入参
        basePageVO.checkParam();

        // 调用逻辑层，获取返回参数
        IPage<DescribeFilmsRespVO> results = filmServiceAPI.describeFilms(basePageVO.getNowPage(),basePageVO.getPageSize());

        Map<String, Object> films = descrbePageResult(results, "films");

        return BaseResponseVO.success(films);
    }

    // 根据电影主键获取电影信息
    @RequestMapping(value = "/{filmId}",method = RequestMethod.GET)
    public BaseResponseVO describeFilmById(@PathVariable("filmId")String filmId) throws CommonServiceException {

        DescribeFilmsRespVO describeFilmRespVO = filmServiceAPI.describeFilmById(filmId);

        if(describeFilmRespVO == null){
            return BaseResponseVO.success();
        }else{
            return BaseResponseVO.success(describeFilmRespVO);
        }

    }

    // 根据电影编号获取电影信息
    @RequestMapping(value = "/film:add",method = RequestMethod.POST)
    public BaseResponseVO saveFilmInfo(@RequestBody FilmSavedReqVO filmSavedReqVO) throws CommonServiceException {

        filmServiceAPI.saveFilm(filmSavedReqVO);

        return BaseResponseVO.success();
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
