package com.mooc.meetingfilm.exception;

import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共异常处理：
 * @ControllerAdvice : 本质就是一个@Component，
 * 这个类是为那些声明了（@ExceptionHandler、@InitBinder 或 @ModelAttribute注解修饰的）方法的类而提供的专业化的@Component
 *
 * 说白了，就是aop思想的一种实现，你告诉我需要拦截规则，我帮你把他们拦下来,
 * 具体你想做更细致的拦截筛选和拦截之后的处理，
 * 你自己通过@ExceptionHandler、@InitBinder 或 @ModelAttribute这三个注解以及被其注解的方法来自定义。
 * @author wang
 * @create 2022-04-19
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    //代表这个异常处理器处理的是CommonServiceException异常。
    @ExceptionHandler(CommonServiceException.class)
    //会将BaseResponseVO对象转为JSON字符串返回给客户端
    @ResponseBody
    public BaseResponseVO serviceExceptionHandler(HttpServletRequest request, CommonServiceException e){

        //因为标了@slf4j注解，不在需要声明LoggerFactory才能使用logger
        log.error("CommonServiceException, code:{}, message", e.getCode(), e.getMessage());

        return BaseResponseVO.serviceException(e);
    }
}
