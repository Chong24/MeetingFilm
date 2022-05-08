package com.mooc.meetingfilm.apigwzuul.fallbacks;

import com.alibaba.fastjson.JSONObject;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.exception.CommonServiceException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author : jiangzh
 * @program : com.mooc.meetingfilm.apigwzuul
 * @description : 自定义业务降级处理（要继承FallbackProvider），前提是yaml配置文件配置了hystrix的参数
 * 需要注入进去，否则不会生效。即如果想在网关设置熔断降级，只需要写一个组件注入进去即可，因为zuul默认集成了hystrix和ribbon。
 **/
//@Component
public class MyFallback implements FallbackProvider {

    /**
     * @Description: 针对哪一个路由进行降级（即调用哪个微服务）， return可以写 *
     * @Param: []
     * @return: java.lang.String
     * @Author: jiangzh
     */
    @Override
    public String getRoute() {
        return "film-service";
    }

    /**
     * @Description: 降级时处理方式，即返回什么给客户端
     * @Param: [route, cause]
     * @return: org.springframework.http.client.ClientHttpResponse
     * @Author: jiangzh
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            /**
             * @Description: 业务降级处理方式
             * @Param: []
             * @return: java.io.InputStream
             * @Author: jiangzh
             */
            @Override
            public InputStream getBody() throws IOException {
                BaseResponseVO responseVO
                        = BaseResponseVO.serviceException(
                        new CommonServiceException(404, "No Films!~"));
                String result = JSONObject.toJSONString(responseVO);
                return new ByteArrayInputStream(result.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}