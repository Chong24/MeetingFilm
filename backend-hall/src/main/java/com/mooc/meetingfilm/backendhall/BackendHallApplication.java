package com.mooc.meetingfilm.backendhall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.mooc.meetingfilm"})
@MapperScan(basePackages = {"com.mooc.meetingfilm.backendhall.dao"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class BackendHallApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendHallApplication.class, args);
	}

}
