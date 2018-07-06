package com.anbang.qipai.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.dml.users.UserSessionsManager;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class QipaiTasksApplication {
	@Bean
	public UserSessionsManager userSessionsManager() {
		return new UserSessionsManager();
	}

	public static void main(String[] args) {
		SpringApplication.run(QipaiTasksApplication.class, args);
	}

}