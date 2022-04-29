package com.mooc.meetingfilm.backendshowhystrix.command;

import com.netflix.hystrix.*;
import org.assertj.core.util.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author : jiangzh
 * @program : com.imooc.hystrix.show.command
 * @description : 请求合并处理对象
 * 第一个参数：是批量请求返回的结果
 * 第二个参数：返回结果的泛型
 * 第三个参数：请求参数的泛型
 * 还是在run方法中写业务逻辑，即希望这些请求返回什么结果
 **/
public class CommandCollapser extends HystrixCollapser<List<String>, String , Integer> {

    private Integer id;

    //在构造器中用super设置父类中的参数
    public CommandCollapser(Integer id){
        super(Setter
                .withCollapserKey(HystrixCollapserKey.Factory.asKey("CommandCollapser"))
                .andCollapserPropertiesDefaults(
                        HystrixCollapserProperties.defaultSetter()
                        .withTimerDelayInMilliseconds(1000)     //设置多少时间间隔的请求才会被合并
                )
        );
        this.id = id;
    }
    /**
    * @Description: 获取请求参数
    * @Param: []
    * @return: java.lang.Integer
    * @Author: jiangzh
    */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
    * @Description: 批量业务处理
    * @Param: [collection]
    * @return: com.netflix.hystrix.HystrixCommand<java.util.List<java.lang.String>>
    * @Author: jiangzh
    */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collection) {
        //因为返回的是HystrixCommand类，因此还需要自定义一个类去实现它；有参构造器
        return new BatchCommand(collection);
    }

    /**
    * @Description: 批量处理结果与请求业务之间映射关系处理：就像@RequestMapping的作用
    * @Param: [strings, collection]
    * @return: void
    * @Author: jiangzh
     * 第一个参数就是返回的结果集
     * 第二个参数就是请求的集合，需要一一对应；将结果设置到这个请求的返回结果
    */
    @Override
    protected void mapResponseToRequests(List<String> strings, Collection<CollapsedRequest<String, Integer>> collection) {
        int counts = 0;
        Iterator<CollapsedRequest<String, Integer>> iterator = collection.iterator();
        while (iterator.hasNext()) {
            CollapsedRequest<String, Integer> response = iterator.next();

            String result = strings.get(counts++);

            response.setResponse(result);
        }
    }
}

//需要自己写一个命令类，在run方法中自定义业务逻辑
class BatchCommand extends HystrixCommand<List<String>>{

    private Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collection;

    public BatchCommand(Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collection){
        //GroupKey是必填项
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BatchCommand")));
        this.collection = collection;
    }

    @Override
    protected List<String> run() throws Exception {
        System.err.println("currentThread : "+Thread.currentThread().getName());
        List<String> result = Lists.newArrayList();

        Iterator<HystrixCollapser.CollapsedRequest<String, Integer>> iterator = collection.iterator();
        while (iterator.hasNext()) {
            HystrixCollapser.CollapsedRequest<String, Integer> request = iterator.next();

            Integer reqParam = request.getArgument();

            // 具体业务逻辑，根据请求返回自定义的结果
            result.add("Mooc req: "+ reqParam);
        }

        return result;
    }
}
