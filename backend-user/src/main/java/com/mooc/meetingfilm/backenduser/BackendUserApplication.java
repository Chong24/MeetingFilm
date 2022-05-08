package com.mooc.meetingfilm.backenduser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.mooc.meetingfilm"})
@SpringBootApplication
@MapperScan(basePackages = {"com.mooc.meetingfilm.backenduser.dao.mapper"})
public class BackendUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendUserApplication.class, args);
	}

}
