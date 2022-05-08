package com.mooc.meetingfilm.apigwzuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 自定义Filter，要继承ZuulFilter
 */
@Slf4j
public class MyFilter extends ZuulFilter {
    //指定过滤器的类型：
    /**
     * 在Zuul中定义了4种标准的过滤器类型，这些过滤器类型对应于一个服务请求的典型生命周期。
     *
     * 1，·PRE过滤器：在请求被路由之前调用（请求被路由即分给微服务），可用来实现身份验证、在集群中选择请求的微服务、记录调试信息等。
     *
     * 2，·ROUTING过滤器：在调用目标服务之前被调用，通常可以用来处理一些动态路由。比如，A/B测试，在这里可以随机让部分用户访问指定版本的服务，
     * 然后通过用户体验数据的采集和分析来决定哪个版本更好。另外，还可以结合PRE过滤器实现不同版本服务之间的处理。
     *
     * 3，·POST过滤器：在目标微服务执行以后，所返回的结果在送回给客户端时被调用，
     * 我们可以利用该过滤器实现为响应添加标准的HTTP Header、数据采集、统计信息和指标、审计日志处理等。
     *
     * 4，·ERROR过滤器：该过滤器在处理请求过程中发生错误时被调用，可以使用该过滤器实现对异常、错误的统一处理，从而为客户端调用显示更加友好的界面。
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * @Description: filter的执行顺序，-3是最先执行的
     * @Param: []
     * @return: int
     * @Author: jiangzh
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * @Description: 是否要拦截，肯定要拦截，不拦截自定义过滤器干嘛
     * @Param: []
     * @return: boolean
     * @Author: jiangzh
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * @Description: Filter的具体业务逻辑
     * @Param: []
     * @return: java.lang.Object
     * @Author: jiangzh
     */
    @Override
    public Object run() throws ZuulException {
        // ThreadLocal，RequestContext是线程安全的，靠它过滤器实现相互通信
        RequestContext requestContext = RequestContext.getCurrentContext();
        //每个RequestContext都有一个HttpServletRequest
        HttpServletRequest request = requestContext.getRequest();

        //以打印请求头来作为业务逻辑
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headName = headerNames.nextElement();
            log.info("headName:{}, headValue:{}", headName, request.getHeader(headName));
        }

        return null;
    }
}
