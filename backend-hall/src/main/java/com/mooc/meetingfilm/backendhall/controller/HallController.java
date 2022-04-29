package com.mooc.meetingfilm.backendhall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.mooc.meetingfilm.backendhall.controller.vo.HallSavedReqVO;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsReqVO;
import com.mooc.meetingfilm.backendhall.controller.vo.HallsRespVO;
import com.mooc.meetingfilm.backendhall.service.HallServiceAPI;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wang
 * @create 2022-04-25
 */
@RestController
@RequestMapping(value = "/halls")
public class HallController {

    @Autowired
    private HallServiceAPI hallServiceAPI;

    /**
     * @Description: 新增播放厅：播放的影片从film模块中拿，所以必须要拿film库中有的电影；影厅肯定只能播放已有的电影
     * @Param: [hallsReqVO]
     * @return: com.mooc.meetingfilm.utils.common.vo.BaseResponseVO
     * @Author: jiangzh
     */
    @RequestMapping(value = "/hall:add",method = RequestMethod.POST)
    public BaseResponseVO saveHall(@RequestBody HallSavedReqVO hallSavedReqVO) throws CommonServiceException {

        hallSavedReqVO.checkParam();

        hallServiceAPI.saveHall(hallSavedReqVO);

        return BaseResponseVO.success();
    }

    /**
     * @Description: 获取播放厅列表
     * @Param: [hallsReqVO]
     * @return: com.mooc.meetingfilm.utils.common.vo.BaseResponseVO
     * @Author: jiangzh
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public BaseResponseVO describeHalls(HallsReqVO hallsReqVO) throws CommonServiceException {

        hallsReqVO.checkParam();

        IPage<HallsRespVO> page = hallServiceAPI.describeHalls(hallsReqVO);

        Map<String, Object> halls = descrbePageResult(page, "halls");

        return BaseResponseVO.success(halls);
    }

    // 获取分页对象的公共接口
    private Map<String,Object> descrbePageResult(IPage page, String title){
        Map<String,Object> result = Maps.newHashMap();

        result.put("totalSize",page.getTotal());
        result.put("totalPages",page.getPages());
        result.put("pageSize",page.getSize());
        result.put("nowPage",page.getCurrent());

        result.put(title, page.getRecords());

        return result;
    }

}
