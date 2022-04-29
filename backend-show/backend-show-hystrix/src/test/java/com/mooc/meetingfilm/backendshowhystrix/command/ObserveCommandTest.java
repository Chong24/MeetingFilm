package com.mooc.meetingfilm.backendshowhystrix.command;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

/**
 * @author : jiangzh
 * @program : com.imooc.hystrix.show.command
 * @description :
 **/
public class ObserveCommandTest {

    @Test
    public void observeTest() throws InterruptedException {
        long beginTime = System.currentTimeMillis();

        ObserveCommandDemo commandDemo = new ObserveCommandDemo("ObserveCommandTest-observe");

        Observable<String> observe = commandDemo.observe();

        //一个实例只能调用一次

        // 阻塞式调用
//        String result = observe.toBlocking().single();
//
//        long endTime = System.currentTimeMillis();
//        System.out.println("result="+result+" , speeding="+(endTime-beginTime));


        // 非阻塞式调用
        observe.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.err.println("ObserveCommandTest-observe , onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("ObserveCommandTest-observe , onError - throwable="+throwable);
            }

            @Override
            public void onNext(String result) {
                long endTime = System.currentTimeMillis();
                System.err.println("ObserveCommandTest-observe , onNext result="+result+" speend:"+(endTime - beginTime));
            }
        });

        Thread.sleep(1000l);
    }

}
