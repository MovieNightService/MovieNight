package com.movienight.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieNightChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieNightChatApplication.class, args);
	}
}
