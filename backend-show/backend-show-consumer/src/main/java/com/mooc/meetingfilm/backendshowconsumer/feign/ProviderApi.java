package com.mooc.meetingfilm.backendshowconsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//这个接口目的是为了访问Provider类的控制器方法，
// name写的一般是服务名，因为要调用提供服务Provider
// url写的是Provider的访问路径，但是就要写死，所以一般整合ribbon, 直接把name写成服务名，服务名代替hostname+port
// path参数是公共url的前缀路径，
// primary是多实现时指定优先级（类似于bean装配的适合的优先级）默认是true，
// configuration是自定义feign配置：这个自定义配置类不能在main主程序的扫描范围内，（因为不同的接口可能配置不一样，这是针对单个接口提供不同配置）
// Fallback和fallbackfactory是降级统一处理
//FrignClient或默认会根据注解为这个接口生成一个实现类，如果要自定义实现，需要将primary置为false，否则会有两个实现类，不知道加载哪个
@FeignClient(name = "hello-service-provider",
        primary = true,
        path = "/provider",
        fallbackFactory = FallbackFactory.class,
        fallback = ProviderFallbackAPIImpl.class
//        configuration = FeignHelloConf.class,
//        url = "http://localhost:7101"
)
public interface ProviderApi {

    //用和被调用的方法一样的请求路径，才能证明调用的是这个url下的sayhello方法
    //当调用了这个接口的这个方法，就会调用上面定义的http://localhost:7101/provider/sayhello的get方法。7101是被调用服务的端口
    //之所以支持springmvc，是因为有支持的contract的默认配置，如果修改相关的配置，可能就不支持了
    @RequestMapping(value = "/provider/sayhello",method = RequestMethod.GET)
    String invokerProviderController(@RequestParam("message") String message);

}
