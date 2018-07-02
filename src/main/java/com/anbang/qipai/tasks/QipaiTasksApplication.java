package com.anbang.qipai.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class QipaiTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(QipaiTasksApplication.class, args);
	}

}