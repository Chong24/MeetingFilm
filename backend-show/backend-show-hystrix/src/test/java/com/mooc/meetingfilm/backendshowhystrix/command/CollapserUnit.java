package com.mooc.meetingfilm.backendshowhystrix.command;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author : jiangzh
 * @program : com.imooc.hystrix.show.command
 * @description : 请求合并测试
 **/
public class CollapserUnit {

    @Test
    public void collapserTest() throws ExecutionException, InterruptedException {

        //也需要在同一个上下文中，所以还是要开启上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        // 构建请求 -> 主要优化点，多个服务调用的多次HTTP请求合并
        // 缺点：很少有机会对同一个服务进行多次HTTP调用，同时还要足够的"近"
        CommandCollapser c1 = new CommandCollapser(1);
        CommandCollapser c2 = new CommandCollapser(2);
        CommandCollapser c3 = new CommandCollapser(3);
        CommandCollapser c4 = new CommandCollapser(4);

        // 获取结果, 足够的近 -> 如果用Thread.sleep(你设置的时间间隔参数)，就不会请求合并，就会是不同线程运行
        Future<String> q1 = c1.queue();
        Future<String> q2 = c2.queue();
        Future<String> q3 = c3.queue();
        Future<String> q4 = c4.queue();

        String r1 = q1.get();
        String r2 = q2.get();
        String r3 = q3.get();
        String r4 = q4.get();

        // 打印
        System.err.println(r1+" , "+r2+" , "+r3+" , "+r4);

        //关闭上下文
        context.close();
    }


}
