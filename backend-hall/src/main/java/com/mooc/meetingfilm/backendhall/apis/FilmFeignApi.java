package com.mooc.meetingfilm.backendhall.apis;


import com.mooc.meetingfilm.apis.film.FilmFeignApis;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author : jiangzh
 * @program : com.mooc.meetingfilm.hall.apis
 * @description : film提供的接口服务,继承抽离出来的HTTP client服务
 * name的film-service是film模块的服务名，集合了ribbon的用法，标了@FeignClient注解，会自动实现一个代理实现类
 * 继承就已经继承了声明在FilmFeignApis的方法
 **/
@FeignClient(name = "film-service")
public interface FilmFeignApi extends FilmFeignApis {

}

