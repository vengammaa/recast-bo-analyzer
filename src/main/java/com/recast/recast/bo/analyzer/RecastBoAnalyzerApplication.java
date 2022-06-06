package com.recast.recast.bo.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableEurekaClient
public class RecastBoAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecastBoAnalyzerApplication.class, args);
	}

}
