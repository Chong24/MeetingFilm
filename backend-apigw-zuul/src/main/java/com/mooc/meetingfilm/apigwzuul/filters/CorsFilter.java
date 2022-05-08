package com.mooc.meetingfilm.apigwzuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : jiangzh
 * @program : com.mooc.jiangzh.springcloud.filters.pre
 * @description : 解决跨域问题
 **/
@Component
public class CorsFilter extends ZuulFilter {

    //过滤器类型
    public String filterType() {
        return "pre";
    }

    //优先级
    public int filterOrder() {
        return 0;
    }

    //是否拦截
    public boolean shouldFilter() {
        return true;
    }

    //真正的业务逻辑
    public Object run() {
        //获取zuul网关的RequestContext
        RequestContext ctx = RequestContext.getCurrentContext();
        // 跨域：获取response
        HttpServletResponse response = ctx.getResponse();
        //设置允许哪些域名的资源共享
        response.addHeader("Access-Control-Allow-Origin", "*");
        //允许的方法
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
        //允许的请求头的key
        response.setHeader("Access-Control-Allow-Headers","DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range,Authorization");
        //允许的返回给前端的格式
        response.setContentType("application/json");
        //允许的字符集
        response.setCharacterEncoding("UTF-8");
//    response.setContentType("text/html;charset=UTF-8");

    /*
      跨域资源共享
        - 这是HTTP协议规定的安全策略
        - 配置资源共享的方式和目标方

        前端： node+vue -> admin.meetingfilm.com
        后端： springboot -> backend.meetingfilm.com
        这前后端的域名不同，资源无法共享

        -> 示例
        缺陷：如果出现跨域策略不足的情况，需要修改代码，重新部署
        -> 所以一般设置在Nginx
     */

        return null;
    }

}

