package com.mooc.meetingfilm.backendfilm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.mooc.meetingfilm"})
@MapperScan(basePackages = {"com.mooc.meetingfilm.backendfilm.dao"})
@EnableDiscoveryClient
@SpringBootApplication
public class BackendFilmApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendFilmApplication.class, args);
	}

}
