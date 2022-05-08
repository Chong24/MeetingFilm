package com.mooc.meetingfilm.backendhall.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.meetingfilm.apis.film.vo.DescribeFilmRespVO;
import com.mooc.meetingfilm.backendhall.apis.FilmFeignApi;
import com.mooc.meetingfilm.backendhall.controller.vo.HallSavedReqVO;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsReqVO;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsRespVO;
import com.mooc.meetingfilm.backendhall.dao.entity.MoocFieldT;
import com.mooc.meetingfilm.backendhall.dao.entity.MoocHallFilmInfoT;
import com.mooc.meetingfilm.backendhall.dao.mapper.MoocFieldTMapper;
import com.mooc.meetingfilm.backendhall.dao.mapper.MoocHallFilmInfoTMapper;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import com.mooc.meetingfilm.util.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author wang
 * @create 2022-04-25
 */
@Service
public class HallServiceImpl implements HallServiceAPI {

    @Resource
    private MoocFieldTMapper fieldTMapper;

    @Resource
    private MoocHallFilmInfoTMapper hallFilmInfoTMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient eurekaClient;

    @Resource
    private FilmFeignApi filmFeignApi;

    @Override
    public IPage<HallsRespVO> describeHalls(HallsReqVO hallsReqVO) throws CommonServiceException {
        Page<HallsReqVO> page = new Page<>(hallsReqVO.getNowPage(),hallsReqVO.getPageSize());

        //QueryWrapper实际上就是一个条件拼接器，给你在sql语句的where拼接and和or，这就提供了很大的灵活性，
        //即可以从在映射文件写sql语句拼接，到现在用QueryWrapper对象拼接。
        QueryWrapper queryWrapper = new QueryWrapper();
        if(ToolUtils.strIsNotNul(hallsReqVO.getCinemaId())){
            queryWrapper.eq("cinema_id", hallsReqVO.getCinemaId());
        }

        IPage<HallsRespVO> result = fieldTMapper.describeHalls(page, queryWrapper);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHall(HallSavedReqVO reqVO) throws CommonServiceException {
        // 播放厅的列表数据
        MoocFieldT field = new MoocFieldT();

        field.setCinemaId(ToolUtils.str2Int(reqVO.getCinemaId()));
        field.setFilmId(ToolUtils.str2Int(reqVO.getFilmId()));
        field.setBeginTime(reqVO.getBeginTime());
        field.setEndTime(reqVO.getEndTime());
        field.setHallId(ToolUtils.str2Int(reqVO.getHallTypeId()));
        field.setHallName(reqVO.getHallName());
        field.setPrice(ToolUtils.str2Int(reqVO.getFilmPrice()));

        fieldTMapper.insert(field);

        // 播放厅对应的影片数据， 影片冗余数据， 我们可以直接调用影片模块的根据id查电影。consumer远程调用procider的方法
        MoocHallFilmInfoT hallFilmInfo = describeFilmInfo(reqVO.getFilmId());

        hallFilmInfoTMapper.insert(hallFilmInfo);
    }

    //没引用feign的做法
//    private MoocHallFilmInfoT describeFilmInfo(String filmId) throws CommonServiceException{
////        //得到Eureka Provider在Eureka Service中注册的信息
////        ServiceInstance choose = eurekaClient.choose("film-service");
////        //组织调用参数
////        String hostname = choose.getHost();
////        int port = choose.getPort();
////
////        String uri = "/films/" + filmId;
////
////        String url = "http://" + hostname + ":" + port + uri;
//
//        //ribbion + Eureka
//        String uri = "/films/" + filmId;
//        String url = "http://film-service" + uri;
//
//        //通过Java的远程调用工具restTemplate调用影片服务
//        JSONObject forObject = restTemplate.getForObject(url, JSONObject.class);
//
//        //解析返回值，返回的包含code, message, data，其实就是我们封装的baseResponseVO
//        JSONObject dataJson = forObject.getJSONObject("data");
//
//        //组织参数，将data中的数据封装到MoocHallFilmInfoT中返回，他们的参数都一致
//        MoocHallFilmInfoT hallFilmInfo = new MoocHallFilmInfoT();
//
//        hallFilmInfo.setFilmId(dataJson.getIntValue("filmId"));
//        hallFilmInfo.setFilmName(dataJson.getString("filmName"));
//        hallFilmInfo.setFilmLength(dataJson.getString("filmLength"));
//        hallFilmInfo.setFilmCats(dataJson.getString("filmCats"));
//        hallFilmInfo.setActors(dataJson.getString("actors"));
//        hallFilmInfo.setImgAddress(dataJson.getString("imgAddress"));
//
//        return hallFilmInfo;
//    }

    //引用feign的做法
    // 播放厅对应的影片数据， 影片冗余数据， 缓存里有一份
    private MoocHallFilmInfoT describeFilmInfo(String filmId) throws CommonServiceException{
        // 解析返回值
        BaseResponseVO<DescribeFilmRespVO> baseResponseVO = filmFeignApi.describeFilmById(filmId);
        DescribeFilmRespVO filmResult = baseResponseVO.getData();
        if(filmResult == null || ToolUtils.strIsNull(filmResult.getFilmId())){
            throw new CommonServiceException(404, "抱歉，未能找到对应的电影信息，filmId : "+filmId);
        }

        // 组织参数
        MoocHallFilmInfoT hallFilmInfo = new MoocHallFilmInfoT();

        hallFilmInfo.setFilmId(ToolUtils.str2Int(filmResult.getFilmId()));
        hallFilmInfo.setFilmName(filmResult.getFilmName());
        hallFilmInfo.setFilmLength(filmResult.getFilmLength());
        hallFilmInfo.setFilmCats(filmResult.getFilmCats());
        hallFilmInfo.setActors(filmResult.getActors());
        hallFilmInfo.setImgAddress(filmResult.getImgAddress());

        return hallFilmInfo;
    }
}
