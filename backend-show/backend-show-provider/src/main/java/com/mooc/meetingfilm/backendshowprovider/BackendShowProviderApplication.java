package com.mooc.meetingfilm.backendshowprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//springcloud提供的注解，将一个微服务注册到例如Eureka的注册中心。使用其他的注册中心组件
//@EnableEurekaClient也能实现上述的功能，但是它只适用于Eureka。所以一般都用上面那个组件。
@EnableDiscoveryClient
@SpringBootApplication
public class BackendShowProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendShowProviderApplication.class, args);
	}

}
