package com.mooc.meetingfilm.apigwzuul.filters;

import com.alibaba.fastjson.JSONObject;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import com.mooc.meetingfilm.properties.JwtProperties;
import com.mooc.meetingfilm.util.JwtTokenUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : jiangzh
 * @program : com.mooc.meetingfilm.apigwzuul.filters
 * @description : jwt验证token有效性
 **/
@Slf4j
public class JWTFilter extends ZuulFilter {
    /**
     * @Description: Filter类型
     * @Param: []
     * @return: java.lang.String
     * @Author: jiangzh
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * @Description: filter的执行顺序
     * @Param: []
     * @return: int
     * @Author: jiangzh
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * @Description: 是否要拦截
     * @Param: []
     * @return: boolean
     * @Author: jiangzh
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * @Description: Filter的具体业务逻辑
     * @Param: []
     * @return: java.lang.Object
     * @Author: jiangzh
     */
    @Override
    public Object run() throws ZuulException {
        // JWT工具类
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        JwtProperties jwtProperties = JwtProperties.getJwtProperties();

        // ThreadLocal
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取当前请求和返回值
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        // 提前设置请求继续，如果失败则修改此内容
        ctx.setSendZuulResponse(true);  //将其设为false代表的意思是,这个请求最终不会被zuul转发到后端服务器,但是如果当前Filter后面还存在其他Filter,那么其他Filter仍然会被调用到
        ctx.setResponseStatusCode(200);

        // 判断是否是登陆，如果是登陆则不验证JWT
        if (request.getServletPath().endsWith("/" + jwtProperties.getAuthPath())) {
            return null;
        }

        // 1、验证Token有效性 -> 用户是否登录过   jwt的header的key为Authorization
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        // jwt的格式：Bearer header.payload.sign   一般是Bearer开头的
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    renderJson(ctx , response, BaseResponseVO.noLogin());
                }else{
                    // 2、解析出JWT中的payload -> userid - randomkey
                    String randomkey = jwtTokenUtil.getMd5KeyFromToken(authToken);
                    String userId = jwtTokenUtil.getUsernameFromToken(authToken);
                    // 3、是否需要验签,以及验签的算法

                    // 4、判断userid是否有效
                    // TODO
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                renderJson(ctx ,response, BaseResponseVO.noLogin());
            }
        } else {
            //header没有带Bearer字段
            renderJson(ctx ,response, BaseResponseVO.noLogin());
        }

        return null;
    }


    /**
     * 渲染json对象
     */
    public static void renderJson(RequestContext ctx, HttpServletResponse response, Object jsonObject) {
        // 设置终止请求
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(JSONObject.toJSONString(jsonObject));
    }
}

