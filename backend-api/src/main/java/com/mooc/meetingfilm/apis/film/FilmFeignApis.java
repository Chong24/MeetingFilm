package com.mooc.meetingfilm.apis.film;

import com.mooc.meetingfilm.apis.film.vo.DescribeFilmRespVO;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Film提供的公共接口服务：这样如果想调用这个方法，直接继承就可以，就不用在多个module中重复引用，造成代码重复；
 * 一般商业的接口jar包就是这样做的
 */
public interface FilmFeignApis {
    /**
     * @Description: 对外暴露的接口服务
     * @Param: [filmId]
     * @return: com.mooc.meetingfilm.utils.common.vo.BaseResponseVO
     * @Author: jiangzh
     * 为了泛型可以直接是我们查询的电影信息，可以将这个JavaBean注入到这个给module中，省去通过json格式拿到，还要解析成我们要的数据
     */
    @RequestMapping(value = "/films/{filmId}", method = RequestMethod.GET)
    BaseResponseVO<DescribeFilmRespVO> describeFilmById(@PathVariable("filmId") String filmId) throws CommonServiceException;
}
