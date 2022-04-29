package com.mooc.meetingfilm.backendshowhystrix.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.Data;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * hystrix只有两种command形式的入口（HystrixObservableCommand），只能从这入口进才能享受hystrix带来的功能
 * @author : jiangzh
 * @program : com.imooc.hystrix.show.command
 * @description :
 **/
public class ObserveCommandDemo extends HystrixObservableCommand<String> {

    private String name;

    public ObserveCommandDemo(String name){
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ObserveCommandDemo"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("ObserveCommandKey")));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {

        System.err.println("current Thread: "+Thread.currentThread().getName());

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // 业务处理
                subscriber.onNext("action 1 , name="+name);
                subscriber.onNext("action 2 , name="+name);
                subscriber.onNext("action 3 , name="+name);

                // 业务处理结束
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ObserveCommandDemo{" +
                "name='" + name + '\'' +
                '}';
    }
}
