package com.mooc.meetingfilm.backendcinema;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

@EnableHystrix			//开启Hystrix
@EnableHystrixDashboard  //hystrix的监控功能
@ComponentScan(basePackages = {"com.mooc.meetingfilm"})
@MapperScan(basePackages = {"com.mooc.meetingfilm.backendcinema.dao"})
@EnableDiscoveryClient
@SpringBootApplication
public class BackendCinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendCinemaApplication.class, args);
	}

}
