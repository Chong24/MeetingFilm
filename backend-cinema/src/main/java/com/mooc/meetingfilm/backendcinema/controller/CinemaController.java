package com.mooc.meetingfilm.backendcinema.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.mooc.meetingfilm.backendcinema.controller.vo.CinemaSavedReqVO;
import com.mooc.meetingfilm.backendcinema.controller.vo.DescribeCinemasRespVO;
import com.mooc.meetingfilm.backendcinema.service.CinemaServiceAPI;
import com.mooc.meetingfilm.common.vo.BasePageVO;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    /*
        fallback是业务处理的保底方案，尽可能保证这个保底方案一定能成功
     */
    public BaseResponseVO fallbackMethod(BasePageVO basePageVO) throws CommonServiceException{
        /*
            打发票， -》 没打印纸了， 换台机器或者下次再试
            -》 触发告警 -》 告知运维人员，打印发票业务挂了
         */
        // describeCinemas -》 获取影院列表 -> 在Redis中查询影院列表[redis挂了，或者数据没了] -> 获取超时

        // fallback里捕获到超时或者数据内容与分页数不一致

        // fallback就在数据库中查询真实的影院信息

        // 返回一定是成功，或者业务处理失败
        return BaseResponseVO.success();
    }

    //标注fallback的方法名，名字要一模一样
    @HystrixCommand(fallbackMethod = "fallbackMethod",
            //命令的配置信息
            commandProperties = {
                    //隔离策略：线程池
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    //批量处理请求中的各个请求之间的时间间隔阈值
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value= "1000"),
                    //请求阈值，10个请求超过百分之50失败了，就打开熔断器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            },
            //线程池的配置
            threadPoolProperties = {
                    //核心线程数
                    @HystrixProperty(name = "coreSize", value = "1"),
                    //最大等待队列大小
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
                    //保持活的时间，
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
                    //多少线程阻塞队列就停止访问
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
                    //阻止请求再访问，降级
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1500")
            },ignoreExceptions = CommonServiceException.class)  //忽略我们自定义的异常被fallback捕捉，因为自定义的异常是可以与前台交互的
    @RequestMapping(value = "",method = RequestMethod.GET)
    public BaseResponseVO describeCinemas(BasePageVO basePageVO) throws CommonServiceException {

        // 检查入参
        basePageVO.checkParam();

        IPage<DescribeCinemasRespVO> describeCinemasRespVOIPage = cinemaServiceAPI.describeCinemas(basePageVO.getNowPage(), basePageVO.getPageSize());

        if(basePageVO.getNowPage() > 10000){
            throw new CommonServiceException(400, "nowPage太大了，不支持此处理");
        }

        // 调用封装的分页返回方法, 就和新建一个map，把一些数据放进去，key-value对应差不多，只不过封装了一个公共的方法调用
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
