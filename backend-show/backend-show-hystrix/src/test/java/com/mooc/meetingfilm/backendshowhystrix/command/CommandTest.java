package com.mooc.meetingfilm.backendshowhystrix.command;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wang
 * @create 2022-04-27
 */
public class CommandTest {

    //execute方法是同步执行的，且会返回入口run方法（自己的业务逻辑）返回的对象
    @Test
    public void executeTest(){
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo = new CommandDemo("execute");

        // 同步执行Command，会走hystrix逻辑，执行run方法就会直接跳过前面的熔断器、限流等逻辑
        String result = commandDemo.execute();

        long endTime = System.currentTimeMillis();
        System.out.println("result="+result+" , speeding="+(endTime - beginTime));
    }

    //queue方法是异步执行的，他不会返回run返回的对象，而是返回一个可调用的对象Future；但是Future可以调get方法获取返回的对象
    //future类是一种异步任务监视器，可以让提交者可以监视任务的执行，同时可以取消任务的执行，也可以获取任务返回结果
    @Test
    public void queueTest() throws ExecutionException, InterruptedException {
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo = new CommandDemo("queue");

        Future<String> queue = commandDemo.queue();

        long endTime = System.currentTimeMillis();

        System.out.println("future end , speeding="+(endTime-beginTime));

        //返回Future对象，和Future对象获取对象还需要时间
        long endTime2 = System.currentTimeMillis();

        System.out.println("result="+queue.get()+" , speeding="+(endTime2-beginTime));

    }

    @Test
    public void observeTest(){
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo = new CommandDemo("observe");

        // 阻塞式调用：获取run方法的返回值
        Observable<String> observe = commandDemo.observe();
        String result = observe.toBlocking().single();

        long endTime = System.currentTimeMillis();
        System.out.println("result="+result+" , speeding="+(endTime-beginTime));

        //如果把阻塞式调用注掉，就会发现控制台什么打印信息都没有，加上了阻塞式调用，所有的信息都会打印出来
        //原因：因为非阻塞式调用就相当于一个后台操作，如果前面没有一个耗时的操作，非阻塞式调用会随着main线程的
        //结束就直接结束了，而不会等待hystrix的线程执行这些打印操作。
        //类似于文件上传下载，如果不Thread.sleep()的话，可能主线程结束了，文件的线程也就会结束，就回下载失败。

        // 非阻塞式调用：初始化注册Subscriber对象，泛型就是run方法的泛型（即先执行完run方法再加载，hot处理）
        observe.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.err.println("observe , onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("observe , onError - throwable="+throwable);
            }

            //这个onNext的参数就是run方法的返回对象，注册到了onNext方法上
            @Override
            public void onNext(String result) {
                long endTime = System.currentTimeMillis();
                System.err.println("observe , onNext result="+result+" speend:"+(endTime - beginTime));
            }
        });
    }


    @Test
    public void toObserveTest() throws InterruptedException {
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo1 = new CommandDemo("toObservable1");

        //toObservable不能用多次，即一个Command实例，只能调用一次toObservable，否则回报错
        Observable<String> toObservable1 = commandDemo1.toObservable();

        // 阻塞式调用
        String result = toObservable1.toBlocking().single();

        long endTime = System.currentTimeMillis();
        System.out.println("result="+result+" , speeding="+(endTime-beginTime));

        CommandDemo commandDemo2 = new CommandDemo("toObservable2");
        Observable<String> toObservable2 = commandDemo2.toObservable();

        // 非阻塞式调用：先加载Subscriber对象，后执行run方法，是cold处理；
        toObservable2.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.err.println("toObservable , onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("toObservable , onError - throwable="+throwable);
            }

            //除了run方法之外，onNext也能写我们的业务逻辑
            @Override
            public void onNext(String result) {
                long endTime = System.currentTimeMillis();
                System.err.println("toObservable , onNext result="+result+" speend:"+(endTime - beginTime));
            }
        });

        //如果注掉这个sleep()函数，则会出现只会打印阻塞式调用返回的结果toObservable1的信息
        //如果延迟一段时间，则阻塞式调用和非阻塞式调用的返回结果都会打印出来
        //原因是：因为toObserve是冷处理，即先加载Subscriber对象，然后执行run方法，所以当它异步处理的时候，主线程toObserveTest
        //可能在其他线程执行run方法的时候就已经执行完了，然后就打印不出来了
        Thread.sleep(2000l);
    }

    /**
     * @Description: 演示请求缓存：①、开启hystrix的缓存（默认是开启的）；②、自定义命令实例的时候需要重写getCacheKey方法
     * ③、所有的请求都需要在同一个上下文中，需要开启请求上下文，然后在最后关闭。请求如果不在上下文中会报错
     * @Param: []
     * @return: void
     * @Author: jiangzh
     */
    @Test
    public void requestCache(){
        // 开启请求上下文，请求缓存和请求合并都需要在同一个上下文中，既然有开启，就需要关闭
        HystrixRequestContext requestContext = HystrixRequestContext.initializeContext();

        long beginTime = System.currentTimeMillis();

        CommandDemo c1 = new CommandDemo("c1");
        CommandDemo c2 = new CommandDemo("c2");
        //可以注意到这个c1的结果是立即返回的，并没有执行run方法的Thread.sleep(800l);说明没有走run方法，走的就是缓存
        CommandDemo c3 = new CommandDemo("c1");

        // 第一次请求
        String r1 = c1.execute();

        System.out.println("result="+r1+" , speeding="+(System.currentTimeMillis()-beginTime));

        // 第二次请求
        String r2 = c2.execute();

        System.out.println("result="+r2+" , speeding="+(System.currentTimeMillis()-beginTime));

        // 第三次请求
        String r3 = c3.execute();
        System.out.println("result="+r3+" , speeding="+(System.currentTimeMillis()-beginTime));

        // 请求上下文关闭
        requestContext.close();
    }

    /**
     * @Description: 演示线程池内容
     * @Param: []
     * @return: void
     * @Author: jiangzh
     */
    @Test
    public void threadTest() throws ExecutionException, InterruptedException {
        CommandDemo c1 = new CommandDemo("c1");
        CommandDemo c2 = new CommandDemo("c2");
        CommandDemo c3 = new CommandDemo("c3");
        CommandDemo c4 = new CommandDemo("c4");
        CommandDemo c5 = new CommandDemo("c5");

        Future<String> q1 = c1.queue();
        Future<String> q2 = c2.queue();
        Future<String> q3 = c3.queue();
        Future<String> q4 = c4.queue();
        Future<String> q5 = c5.queue();

        String r1 = q1.get();
        String r2 = q2.get();
        String r3 = q3.get();
        String r4 = q4.get();
        String r5 = q5.get();

        System.out.println(r1+","+r2+","+r3+","+r4+","+r5);

        // 1,2,3,4,5
        // core 1,2  max 1
        // queue 2

    }

    /**
     * @Description: 信号量隔离测试
     * @Param: []
     * @return: void
     * @Author: jiangzh
     */
    @Test
    public void semTest() throws InterruptedException {
        MyThread t1 = new MyThread("t1");
        MyThread t2 = new MyThread("t2");
        MyThread t3 = new MyThread("t3");
        MyThread t4 = new MyThread("t4");
        MyThread t5 = new MyThread("t5");


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        Thread.sleep(2000l);
    }

    /**
     * @Description: 熔断演示
     * @Param: []
     * @return: void
     * @Author: jiangzh
     */
    @Test
    public void CBTest() throws InterruptedException {
        // 正确 - 业务
        CommandDemo c1 = new CommandDemo("imooc");
        System.out.println(c1.execute());

        // 错误 - 业务
        CommandDemo c2 = new CommandDemo("jiangzh-1");
        System.out.println(c2.execute());

        // 正确 - 业务
        Thread.sleep(1000l);
        CommandDemo c3 = new CommandDemo("imooc");
        System.out.println(c3.execute());

        // 半熔断状态
        Thread.sleep(5000l);
        // 错误 - 业务
//        CommandDemo c4 = new CommandDemo("jiangzh-2");
//        System.out.println(c4.execute());

        // 正确 - 业务
//        CommandDemo c5 = new CommandDemo("imooc");
//        System.out.println(c5.execute());


        // 成功，半开启状态每隔5秒判断一次健康状况
        CommandDemo c6 = new CommandDemo("imooc");
        System.out.println(c6.execute());

    }
}


class MyThread extends Thread{

    private String name;

    public MyThread(String name){
        this.name = name;
    }

    @Override
    public void run() {
        CommandDemo commandDemo = new CommandDemo(name);
        System.out.println("commandDemo result="+commandDemo.execute());
    }
}