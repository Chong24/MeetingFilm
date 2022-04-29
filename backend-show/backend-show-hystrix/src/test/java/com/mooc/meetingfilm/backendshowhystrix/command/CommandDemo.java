package com.mooc.meetingfilm.backendshowhystrix.command;

import com.netflix.hystrix.*;
import lombok.Data;

/**
 * hystrix只有两种command形式的入口（HystrixCommand），只能从这入口进才能享受hystrix带来的功能
 * @author wang
 * @create 2022-04-27
 */

public class CommandDemo extends HystrixCommand<String> {

    private String name;

    public CommandDemo(String name){
        //GroupKey就是用来分组的，名字一样的分为一组，方便监控和报警，是hystrix唯一的必填项，也会是线程池的默认名称
        super(Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("CommandHelloWorld"))  //里面的参数决定hystrix内部的线程名currentThread-hystrix-CommandHelloWorld-1
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.defaultSetter()
                                        .withRequestCacheEnabled(false) // 请求缓存开关)
                                        // 切换线程池隔离还是信号量隔离
                                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
//                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                        // .withCircuitBreakerForceOpen(true) // 强制开启熔断器
                                        .withCircuitBreakerRequestVolumeThreshold(2) // 单位时间内的请求阈值
                                        .withCircuitBreakerErrorThresholdPercentage(50) // 当满足请求阈值时，超过50%则开启熔断
                        ).andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("MyThreadPool"))  //设置线程池的名字key
         .andThreadPoolPropertiesDefaults(
                 HystrixThreadPoolProperties.defaultSetter()
                    .withCoreSize(2)       //核心线程池数量
                    .withMaximumSize(3).withAllowMaximumSizeToDivergeFromCoreSize(true) //最大线程数量，还要设置允许才会生效
                    .withMaxQueueSize(2)    //等待队列，只有等待队列都满了才会开启新的线程
         )
        );

        this.name = name;
    }


    // 单次请求调用的业务方法
    @Override
    protected String run() throws Exception {
        String result = "CommandHelloWorld name : "+ name;

        //测试线程隔离的时候，如果每个线程都睡800ms，会导致超时
//        Thread.sleep(800l);

        if(name.startsWith("jiangzh")){
            int i = 6/0;
        }

        System.err.println(result+" , currentThread-"+Thread.currentThread().getName());

        return result;
    }

    //重写getCacheKey方法，根据Key判断是否有缓存：前提是开启了hystrix的缓存（在有参构造实例中配置hystrix的参数）
    @Override
    protected String getCacheKey() {
        return String.valueOf(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CommandDemo{" +
                "name='" + name + '\'' +
                '}';
    }

    /**
     * 快速失败的方法
     * @return
     */
    @Override
    protected String getFallback() {
        String result = "Fallback name : "+ name;

        System.err.println(result+" , currentThread-"+Thread.currentThread().getName());

        return result;
    }
}
