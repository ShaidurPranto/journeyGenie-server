package com.example.journeyGenie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JourneyGenieApplication {
	public static void main(String[] args) {
		SpringApplication.run(JourneyGenieApplication.class, args);
	}
}
